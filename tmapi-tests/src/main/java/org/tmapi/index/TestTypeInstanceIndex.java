/*
 * The Topic Maps API (TMAPI) was created collectively by
 * the membership of the tmapi-discuss mailing list
 * <http://lists.sourceforge.net/mailman/listinfo/tmapi-discuss>,
 * is hereby released into the public domain; and comes with 
 * NO WARRANTY.
 * 
 * No one owns TMAPI: you may use it freely in both commercial and
 * non-commercial applications, bundle it with your software
 * distribution, include it on a CD-ROM, list the source code in a
 * book, mirror the documentation at your own web site, or use it in
 * any other way you see fit.
 */
package org.tmapi.index;

import org.tmapi.core.Association;
import org.tmapi.core.FeatureNotRecognizedException;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Role;
import org.tmapi.core.TMAPITestCase;
import org.tmapi.core.Topic;

/**
 * Tests against the {@link TypeInstanceIndex} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 126 $ - $Date: 2009-10-02 15:56:12 +0000 (Fri, 02 Oct 2009) $
 */
public class TestTypeInstanceIndex extends TMAPITestCase {

    private static final String _FEATURE_TYPE_INSTANCE_ASSOCIATIONS= "http://tmapi.org/features/type-instance-associations";
    private TypeInstanceIndex _typeInstanceIdx;

    public TestTypeInstanceIndex(String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see org.tmapi.core.TMAPITestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        _typeInstanceIdx = _tm.getIndex(TypeInstanceIndex.class);
        _typeInstanceIdx.open();
    }

    /* (non-Javadoc)
     * @see org.tmapi.core.TMAPITestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        _typeInstanceIdx.close();
        _typeInstanceIdx = null;
    }

    private void _updateIndex() {
        if (!_typeInstanceIdx.isAutoUpdated()) {
            _typeInstanceIdx.reindex();
        }
    }

    public void testTopic() throws FeatureNotRecognizedException {
        _updateIndex();
        assertTrue(_typeInstanceIdx.getTopics(null).isEmpty());
        assertTrue(_typeInstanceIdx.getTopicTypes().isEmpty());
        Topic topic = _tm.createTopic();
        _updateIndex();
        assertTrue(_typeInstanceIdx.getTopicTypes().isEmpty());
        assertEquals(1, _typeInstanceIdx.getTopics(null).size());
        assertTrue(_typeInstanceIdx.getTopics(null).contains(topic));
        Topic type1 = _tm.createTopic();
        Topic type2 = _tm.createTopic();
        _updateIndex();
        assertTrue(_typeInstanceIdx.getTopicTypes().isEmpty());
        assertEquals(3, _typeInstanceIdx.getTopics(null).size());
        assertTrue(_typeInstanceIdx.getTopics(null).contains(topic));
        assertTrue(_typeInstanceIdx.getTopics(null).contains(type1));
        assertTrue(_typeInstanceIdx.getTopics(null).contains(type2));
        assertTrue(_typeInstanceIdx.getTopics(new Topic[] {type1, type2}, false).isEmpty());
        assertTrue(_typeInstanceIdx.getTopics(new Topic[] {type1, type2}, true).isEmpty());
        // Topic with one type
        topic.addType(type1);
        _updateIndex();
        assertEquals(1, _typeInstanceIdx.getTopicTypes().size());
        assertTrue(_typeInstanceIdx.getTopicTypes().contains(type1));
        
        if (_sys.getFeature(_FEATURE_TYPE_INSTANCE_ASSOCIATIONS)) {
          assertEquals(5, _typeInstanceIdx.getTopics(null).size());
        }
        else {
            assertEquals(2, _typeInstanceIdx.getTopics(null).size());
        }
        
        assertFalse(_typeInstanceIdx.getTopics(null).contains(topic));
        assertTrue(_typeInstanceIdx.getTopics(null).contains(type1));
        assertTrue(_typeInstanceIdx.getTopics(null).contains(type2));
        assertEquals(1, _typeInstanceIdx.getTopics(type1).size());
        assertTrue(_typeInstanceIdx.getTopics(type1).contains(topic));
        assertEquals(1, _typeInstanceIdx.getTopics(new Topic[] {type1, type2}, false).size());
        assertTrue(_typeInstanceIdx.getTopics(new Topic[] {type1, type2}, false).contains(topic));
        assertTrue(_typeInstanceIdx.getTopics(new Topic[] {type1, type2}, true).isEmpty());
        // Topic with two types
        topic.addType(type2);
        _updateIndex();
        assertEquals(2, _typeInstanceIdx.getTopicTypes().size());
        assertTrue(_typeInstanceIdx.getTopicTypes().contains(type1));
        assertTrue(_typeInstanceIdx.getTopicTypes().contains(type2));
        
        if (_sys.getFeature(_FEATURE_TYPE_INSTANCE_ASSOCIATIONS)) {
            assertEquals(5, _typeInstanceIdx.getTopics(null).size());
        }
        else {
            assertEquals(2, _typeInstanceIdx.getTopics(null).size());
        }
        
        assertFalse(_typeInstanceIdx.getTopics(null).contains(topic));
        assertTrue(_typeInstanceIdx.getTopics(null).contains(type1));
        assertTrue(_typeInstanceIdx.getTopics(null).contains(type2));
        assertEquals(1, _typeInstanceIdx.getTopics(type1).size());
        assertTrue(_typeInstanceIdx.getTopics(type1).contains(topic));
        assertEquals(1, _typeInstanceIdx.getTopics(type2).size());
        assertTrue(_typeInstanceIdx.getTopics(type2).contains(topic));
        assertEquals(1, _typeInstanceIdx.getTopics(new Topic[] {type1, type2}, false).size());
        assertTrue(_typeInstanceIdx.getTopics(new Topic[] {type1, type2}, false).contains(topic));
        assertEquals(1, _typeInstanceIdx.getTopics(new Topic[] {type1, type2}, true).size());
        assertTrue(_typeInstanceIdx.getTopics(new Topic[] {type1, type2}, true).contains(topic));
        // Topic removal
        topic.remove();
        _updateIndex();
        assertEquals(0, _typeInstanceIdx.getTopicTypes().size());
        
        if (_sys.getFeature(_FEATURE_TYPE_INSTANCE_ASSOCIATIONS)) {
            assertEquals(5, _typeInstanceIdx.getTopics(null).size());
        }
        else {
            assertEquals(2, _typeInstanceIdx.getTopics(null).size());
        }
        
        assertTrue(_typeInstanceIdx.getTopics(null).contains(type1));
        assertTrue(_typeInstanceIdx.getTopics(null).contains(type2));
        assertTrue(_typeInstanceIdx.getTopics(type1).isEmpty());
        assertTrue(_typeInstanceIdx.getTopics(type2).isEmpty());
        assertEquals(0, _typeInstanceIdx.getTopics(new Topic[] {type1, type2}, false).size());
        assertEquals(0, _typeInstanceIdx.getTopics(new Topic[] {type1, type2}, true).size());
    }

    public void testAssociation() {
        final Topic type = createTopic();
        _updateIndex();
        assertTrue(_typeInstanceIdx.getAssociations(type).isEmpty());
        assertTrue(_typeInstanceIdx.getAssociationTypes().isEmpty());
        Association typed = createAssociation();
        _updateIndex();
        assertTrue(_typeInstanceIdx.getAssociations(type).isEmpty());
        assertFalse(_typeInstanceIdx.getAssociationTypes().contains(type));
        assertEquals(1, _typeInstanceIdx.getAssociationTypes().size());
        typed.setType(type);
        _updateIndex();
        assertFalse(_typeInstanceIdx.getAssociationTypes().isEmpty());
        assertEquals(1, _typeInstanceIdx.getAssociations(type).size());
        assertTrue(_typeInstanceIdx.getAssociations(type).contains(typed));
        assertTrue(_typeInstanceIdx.getAssociationTypes().contains(type));
        typed.setType(createTopic());
        assertFalse(_typeInstanceIdx.getAssociationTypes().contains(type));
        assertEquals(1, _typeInstanceIdx.getAssociationTypes().size());
        typed.setType(type);
        typed.remove();
        _updateIndex();
        assertTrue(_typeInstanceIdx.getAssociations(type).isEmpty());
        assertTrue(_typeInstanceIdx.getAssociationTypes().isEmpty());
    }

    public void testRole() {
        final Topic type = createTopic();
        _updateIndex();
        assertTrue(_typeInstanceIdx.getRoles(type).isEmpty());
        assertTrue(_typeInstanceIdx.getRoleTypes().isEmpty());
        final Association parent = createAssociation();
        Role typed = parent.createRole(createTopic(), createTopic());
        _updateIndex();
        assertEquals(1, _typeInstanceIdx.getRoleTypes().size());
        assertFalse(_typeInstanceIdx.getRoleTypes().contains(type));
        typed.setType(type);
        _updateIndex();
        assertEquals(1, _typeInstanceIdx.getRoleTypes().size());
        assertEquals(1, _typeInstanceIdx.getRoles(type).size());
        assertTrue(_typeInstanceIdx.getRoles(type).contains(typed));
        typed.setType(createTopic());
        assertEquals(1, _typeInstanceIdx.getRoleTypes().size());
        assertFalse(_typeInstanceIdx.getRoleTypes().contains(type));
        typed.setType(type);
        typed.remove();
        _updateIndex();
        assertTrue(_typeInstanceIdx.getRoles(type).isEmpty());
        assertTrue(_typeInstanceIdx.getRoleTypes().isEmpty());
        // The same test, but the parent is removed
        typed = parent.createRole(type, createTopic());
        _updateIndex();
        assertEquals(1, _typeInstanceIdx.getRoleTypes().size());
        assertEquals(1, _typeInstanceIdx.getRoles(type).size());
        assertTrue(_typeInstanceIdx.getRoles(type).contains(typed));
        parent.remove();
        _updateIndex();
        assertTrue(_typeInstanceIdx.getRoles(type).isEmpty());
        assertTrue(_typeInstanceIdx.getRoleTypes().isEmpty());
    }

    public void testOccurrence() throws Exception {
        final Topic type = createTopic();
        _updateIndex();
        assertTrue(_typeInstanceIdx.getOccurrences(type).isEmpty());
        assertTrue(_typeInstanceIdx.getOccurrenceTypes().isEmpty());
        final Topic parent = createTopic();
        Occurrence typed = parent.createOccurrence(createTopic(), "tinyTiM");
        _updateIndex();
        assertTrue(_typeInstanceIdx.getOccurrences(type).isEmpty());
        assertEquals(1, _typeInstanceIdx.getOccurrenceTypes().size());
        assertFalse(_typeInstanceIdx.getOccurrenceTypes().contains(type));
        typed.setType(type);
        _updateIndex();
        assertEquals(1, _typeInstanceIdx.getOccurrenceTypes().size());
        assertEquals(1, _typeInstanceIdx.getOccurrences(type).size());
        assertTrue(_typeInstanceIdx.getOccurrences(type).contains(typed));
        assertTrue(_typeInstanceIdx.getOccurrenceTypes().contains(type));
        typed.setType(createTopic());
        assertTrue(_typeInstanceIdx.getOccurrences(type).isEmpty());
        assertEquals(1, _typeInstanceIdx.getOccurrenceTypes().size());
        assertFalse(_typeInstanceIdx.getOccurrenceTypes().contains(type));
        typed.setType(type);
        typed.remove();
        _updateIndex();
        assertTrue(_typeInstanceIdx.getOccurrences(type).isEmpty());
        assertTrue(_typeInstanceIdx.getOccurrenceTypes().isEmpty());
    }

    public void testName() throws Exception {
        final Topic type = _tm.createTopic();
        _updateIndex();
        assertTrue(_typeInstanceIdx.getNames(type).isEmpty());
        assertTrue(_typeInstanceIdx.getNameTypes().isEmpty());
        final Topic parent = _tm.createTopic();
        Name typed = parent.createName("tinyTiM");
        _updateIndex();
        assertEquals(1, _typeInstanceIdx.getNameTypes().size());
        assertFalse(_typeInstanceIdx.getNameTypes().contains(type));
        assertEquals(0, _typeInstanceIdx.getNames(type).size());
        typed.setType(type);
        _updateIndex();
        assertFalse(_typeInstanceIdx.getNameTypes().isEmpty());
        assertEquals(1, _typeInstanceIdx.getNames(type).size());
        assertTrue(_typeInstanceIdx.getNames(type).contains(typed));
        assertTrue(_typeInstanceIdx.getNameTypes().contains(type));
        typed.setType(createTopic());
        assertEquals(0, _typeInstanceIdx.getNames(type).size());
        assertFalse(_typeInstanceIdx.getNameTypes().contains(type));
        assertEquals(1, _typeInstanceIdx.getNameTypes().size());
        typed.setType(type);
        typed.remove();
        _updateIndex();
        assertTrue(_typeInstanceIdx.getNames(type).isEmpty());
        assertTrue(_typeInstanceIdx.getNameTypes().isEmpty());
    }
}
