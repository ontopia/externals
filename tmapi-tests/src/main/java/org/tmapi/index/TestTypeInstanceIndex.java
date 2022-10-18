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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        _typeInstanceIdx = _tm.getIndex(TypeInstanceIndex.class);
        _typeInstanceIdx.open();
    }

    @After
    @Override
    public void tearDown() throws Exception {
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
        Assert.assertTrue(_typeInstanceIdx.getTopics(null).isEmpty());
        Assert.assertTrue(_typeInstanceIdx.getTopicTypes().isEmpty());
        Topic topic = _tm.createTopic();
        _updateIndex();
        Assert.assertTrue(_typeInstanceIdx.getTopicTypes().isEmpty());
        Assert.assertEquals(1, _typeInstanceIdx.getTopics(null).size());
        Assert.assertTrue(_typeInstanceIdx.getTopics(null).contains(topic));
        Topic type1 = _tm.createTopic();
        Topic type2 = _tm.createTopic();
        _updateIndex();
        Assert.assertTrue(_typeInstanceIdx.getTopicTypes().isEmpty());
        Assert.assertEquals(3, _typeInstanceIdx.getTopics(null).size());
        Assert.assertTrue(_typeInstanceIdx.getTopics(null).contains(topic));
        Assert.assertTrue(_typeInstanceIdx.getTopics(null).contains(type1));
        Assert.assertTrue(_typeInstanceIdx.getTopics(null).contains(type2));
        Assert.assertTrue(_typeInstanceIdx.getTopics(new Topic[] {type1, type2}, false).isEmpty());
        Assert.assertTrue(_typeInstanceIdx.getTopics(new Topic[] {type1, type2}, true).isEmpty());
        // Topic with one type
        topic.addType(type1);
        _updateIndex();
        Assert.assertEquals(1, _typeInstanceIdx.getTopicTypes().size());
        Assert.assertTrue(_typeInstanceIdx.getTopicTypes().contains(type1));
        
        if (_sys.getFeature(_FEATURE_TYPE_INSTANCE_ASSOCIATIONS)) {
          Assert.assertEquals(5, _typeInstanceIdx.getTopics(null).size());
        }
        else {
            Assert.assertEquals(2, _typeInstanceIdx.getTopics(null).size());
        }
        
        Assert.assertFalse(_typeInstanceIdx.getTopics(null).contains(topic));
        Assert.assertTrue(_typeInstanceIdx.getTopics(null).contains(type1));
        Assert.assertTrue(_typeInstanceIdx.getTopics(null).contains(type2));
        Assert.assertEquals(1, _typeInstanceIdx.getTopics(type1).size());
        Assert.assertTrue(_typeInstanceIdx.getTopics(type1).contains(topic));
        Assert.assertEquals(1, _typeInstanceIdx.getTopics(new Topic[] {type1, type2}, false).size());
        Assert.assertTrue(_typeInstanceIdx.getTopics(new Topic[] {type1, type2}, false).contains(topic));
        Assert.assertTrue(_typeInstanceIdx.getTopics(new Topic[] {type1, type2}, true).isEmpty());
        // Topic with two types
        topic.addType(type2);
        _updateIndex();
        Assert.assertEquals(2, _typeInstanceIdx.getTopicTypes().size());
        Assert.assertTrue(_typeInstanceIdx.getTopicTypes().contains(type1));
        Assert.assertTrue(_typeInstanceIdx.getTopicTypes().contains(type2));
        
        if (_sys.getFeature(_FEATURE_TYPE_INSTANCE_ASSOCIATIONS)) {
            Assert.assertEquals(5, _typeInstanceIdx.getTopics(null).size());
        }
        else {
            Assert.assertEquals(2, _typeInstanceIdx.getTopics(null).size());
        }
        
        Assert.assertFalse(_typeInstanceIdx.getTopics(null).contains(topic));
        Assert.assertTrue(_typeInstanceIdx.getTopics(null).contains(type1));
        Assert.assertTrue(_typeInstanceIdx.getTopics(null).contains(type2));
        Assert.assertEquals(1, _typeInstanceIdx.getTopics(type1).size());
        Assert.assertTrue(_typeInstanceIdx.getTopics(type1).contains(topic));
        Assert.assertEquals(1, _typeInstanceIdx.getTopics(type2).size());
        Assert.assertTrue(_typeInstanceIdx.getTopics(type2).contains(topic));
        Assert.assertEquals(1, _typeInstanceIdx.getTopics(new Topic[] {type1, type2}, false).size());
        Assert.assertTrue(_typeInstanceIdx.getTopics(new Topic[] {type1, type2}, false).contains(topic));
        Assert.assertEquals(1, _typeInstanceIdx.getTopics(new Topic[] {type1, type2}, true).size());
        Assert.assertTrue(_typeInstanceIdx.getTopics(new Topic[] {type1, type2}, true).contains(topic));
        // Topic removal
        topic.remove();
        _updateIndex();
        Assert.assertEquals(0, _typeInstanceIdx.getTopicTypes().size());
        
        if (_sys.getFeature(_FEATURE_TYPE_INSTANCE_ASSOCIATIONS)) {
            Assert.assertEquals(5, _typeInstanceIdx.getTopics(null).size());
        }
        else {
            Assert.assertEquals(2, _typeInstanceIdx.getTopics(null).size());
        }
        
        Assert.assertTrue(_typeInstanceIdx.getTopics(null).contains(type1));
        Assert.assertTrue(_typeInstanceIdx.getTopics(null).contains(type2));
        Assert.assertTrue(_typeInstanceIdx.getTopics(type1).isEmpty());
        Assert.assertTrue(_typeInstanceIdx.getTopics(type2).isEmpty());
        Assert.assertEquals(0, _typeInstanceIdx.getTopics(new Topic[] {type1, type2}, false).size());
        Assert.assertEquals(0, _typeInstanceIdx.getTopics(new Topic[] {type1, type2}, true).size());
    }

    public void testAssociation() {
        final Topic type = createTopic();
        _updateIndex();
        Assert.assertTrue(_typeInstanceIdx.getAssociations(type).isEmpty());
        Assert.assertTrue(_typeInstanceIdx.getAssociationTypes().isEmpty());
        Association typed = createAssociation();
        _updateIndex();
        Assert.assertTrue(_typeInstanceIdx.getAssociations(type).isEmpty());
        Assert.assertFalse(_typeInstanceIdx.getAssociationTypes().contains(type));
        Assert.assertEquals(1, _typeInstanceIdx.getAssociationTypes().size());
        typed.setType(type);
        _updateIndex();
        Assert.assertFalse(_typeInstanceIdx.getAssociationTypes().isEmpty());
        Assert.assertEquals(1, _typeInstanceIdx.getAssociations(type).size());
        Assert.assertTrue(_typeInstanceIdx.getAssociations(type).contains(typed));
        Assert.assertTrue(_typeInstanceIdx.getAssociationTypes().contains(type));
        typed.setType(createTopic());
        Assert.assertFalse(_typeInstanceIdx.getAssociationTypes().contains(type));
        Assert.assertEquals(1, _typeInstanceIdx.getAssociationTypes().size());
        typed.setType(type);
        typed.remove();
        _updateIndex();
        Assert.assertTrue(_typeInstanceIdx.getAssociations(type).isEmpty());
        Assert.assertTrue(_typeInstanceIdx.getAssociationTypes().isEmpty());
    }

    public void testRole() {
        final Topic type = createTopic();
        _updateIndex();
        Assert.assertTrue(_typeInstanceIdx.getRoles(type).isEmpty());
        Assert.assertTrue(_typeInstanceIdx.getRoleTypes().isEmpty());
        final Association parent = createAssociation();
        Role typed = parent.createRole(createTopic(), createTopic());
        _updateIndex();
        Assert.assertEquals(1, _typeInstanceIdx.getRoleTypes().size());
        Assert.assertFalse(_typeInstanceIdx.getRoleTypes().contains(type));
        typed.setType(type);
        _updateIndex();
        Assert.assertEquals(1, _typeInstanceIdx.getRoleTypes().size());
        Assert.assertEquals(1, _typeInstanceIdx.getRoles(type).size());
        Assert.assertTrue(_typeInstanceIdx.getRoles(type).contains(typed));
        typed.setType(createTopic());
        Assert.assertEquals(1, _typeInstanceIdx.getRoleTypes().size());
        Assert.assertFalse(_typeInstanceIdx.getRoleTypes().contains(type));
        typed.setType(type);
        typed.remove();
        _updateIndex();
        Assert.assertTrue(_typeInstanceIdx.getRoles(type).isEmpty());
        Assert.assertTrue(_typeInstanceIdx.getRoleTypes().isEmpty());
        // The same test, but the parent is removed
        typed = parent.createRole(type, createTopic());
        _updateIndex();
        Assert.assertEquals(1, _typeInstanceIdx.getRoleTypes().size());
        Assert.assertEquals(1, _typeInstanceIdx.getRoles(type).size());
        Assert.assertTrue(_typeInstanceIdx.getRoles(type).contains(typed));
        parent.remove();
        _updateIndex();
        Assert.assertTrue(_typeInstanceIdx.getRoles(type).isEmpty());
        Assert.assertTrue(_typeInstanceIdx.getRoleTypes().isEmpty());
    }

    public void testOccurrence() throws Exception {
        final Topic type = createTopic();
        _updateIndex();
        Assert.assertTrue(_typeInstanceIdx.getOccurrences(type).isEmpty());
        Assert.assertTrue(_typeInstanceIdx.getOccurrenceTypes().isEmpty());
        final Topic parent = createTopic();
        Occurrence typed = parent.createOccurrence(createTopic(), "tinyTiM");
        _updateIndex();
        Assert.assertTrue(_typeInstanceIdx.getOccurrences(type).isEmpty());
        Assert.assertEquals(1, _typeInstanceIdx.getOccurrenceTypes().size());
        Assert.assertFalse(_typeInstanceIdx.getOccurrenceTypes().contains(type));
        typed.setType(type);
        _updateIndex();
        Assert.assertEquals(1, _typeInstanceIdx.getOccurrenceTypes().size());
        Assert.assertEquals(1, _typeInstanceIdx.getOccurrences(type).size());
        Assert.assertTrue(_typeInstanceIdx.getOccurrences(type).contains(typed));
        Assert.assertTrue(_typeInstanceIdx.getOccurrenceTypes().contains(type));
        typed.setType(createTopic());
        Assert.assertTrue(_typeInstanceIdx.getOccurrences(type).isEmpty());
        Assert.assertEquals(1, _typeInstanceIdx.getOccurrenceTypes().size());
        Assert.assertFalse(_typeInstanceIdx.getOccurrenceTypes().contains(type));
        typed.setType(type);
        typed.remove();
        _updateIndex();
        Assert.assertTrue(_typeInstanceIdx.getOccurrences(type).isEmpty());
        Assert.assertTrue(_typeInstanceIdx.getOccurrenceTypes().isEmpty());
    }

    public void testName() throws Exception {
        final Topic type = _tm.createTopic();
        _updateIndex();
        Assert.assertTrue(_typeInstanceIdx.getNames(type).isEmpty());
        Assert.assertTrue(_typeInstanceIdx.getNameTypes().isEmpty());
        final Topic parent = _tm.createTopic();
        Name typed = parent.createName("tinyTiM");
        _updateIndex();
        Assert.assertEquals(1, _typeInstanceIdx.getNameTypes().size());
        Assert.assertFalse(_typeInstanceIdx.getNameTypes().contains(type));
        Assert.assertEquals(0, _typeInstanceIdx.getNames(type).size());
        typed.setType(type);
        _updateIndex();
        Assert.assertFalse(_typeInstanceIdx.getNameTypes().isEmpty());
        Assert.assertEquals(1, _typeInstanceIdx.getNames(type).size());
        Assert.assertTrue(_typeInstanceIdx.getNames(type).contains(typed));
        Assert.assertTrue(_typeInstanceIdx.getNameTypes().contains(type));
        typed.setType(createTopic());
        Assert.assertEquals(0, _typeInstanceIdx.getNames(type).size());
        Assert.assertFalse(_typeInstanceIdx.getNameTypes().contains(type));
        Assert.assertEquals(1, _typeInstanceIdx.getNameTypes().size());
        typed.setType(type);
        typed.remove();
        _updateIndex();
        Assert.assertTrue(_typeInstanceIdx.getNames(type).isEmpty());
        Assert.assertTrue(_typeInstanceIdx.getNameTypes().isEmpty());
    }
}
