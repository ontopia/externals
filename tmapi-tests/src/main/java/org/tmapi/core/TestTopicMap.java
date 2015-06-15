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
package org.tmapi.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.tmapi.index.Index;

/** 
 * Tests against the {@link TopicMap} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 64 $ - $Date: 2008-08-14 11:46:44 +0000 (Thu, 14 Aug 2008) $
 */
public class TestTopicMap extends TMAPITestCase {

    public TestTopicMap(String name) {
        super(name);
    }

    /**
     * Tests if {@link TopicMap#getParent()} returns null.
     */
    public void testParent() throws Exception {
        assertNull("A topic map has no parent", _tm.getParent());
    }

    public void testTopicCreationSubjectIdentifier() {
        final Locator loc = createLocator("http://www.example.org/");
        assertTrue(_tm.getTopics().isEmpty());
        final Topic topic = _tm.createTopicBySubjectIdentifier(loc);
        assertEquals(1, _tm.getTopics().size());
        assertTrue(_tm.getTopics().contains(topic));
        assertEquals(1, topic.getSubjectIdentifiers().size());
        assertTrue(topic.getItemIdentifiers().isEmpty());
        assertTrue(topic.getSubjectLocators().isEmpty());
        final Locator loc2 = topic.getSubjectIdentifiers().iterator().next();
        assertEquals(loc, loc2);
    }

    public void testTopicCreationSubjectIdentifierIllegal() {
        try {
            _tm.createTopicBySubjectIdentifier(null);
            fail("Subject identifier == null is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testTopicCreationSubjectLocator() {
        final Locator loc = createLocator("http://www.example.org/");
        assertTrue(_tm.getTopics().isEmpty());
        final Topic topic = _tm.createTopicBySubjectLocator(loc);
        assertEquals(1, _tm.getTopics().size());
        assertTrue(_tm.getTopics().contains(topic));
        assertEquals(1, topic.getSubjectLocators().size());
        assertTrue(topic.getItemIdentifiers().isEmpty());
        assertTrue(topic.getSubjectIdentifiers().isEmpty());
        final Locator loc2 = topic.getSubjectLocators().iterator().next();
        assertEquals(loc, loc2);
    }

    public void testTopicCreationSubjectLocatorIllegal() {
        try {
            _tm.createTopicBySubjectLocator(null);
            fail("Subject locator == null is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testTopicCreationItemIdentifier() {
        final Locator loc = createLocator("http://www.example.org/");
        assertTrue(_tm.getTopics().isEmpty());
        final Topic topic = _tm.createTopicByItemIdentifier(loc);
        assertEquals(1, _tm.getTopics().size());
        assertTrue(_tm.getTopics().contains(topic));
        assertEquals(1, topic.getItemIdentifiers().size());
        assertTrue(topic.getSubjectIdentifiers().isEmpty());
        assertTrue(topic.getSubjectLocators().isEmpty());
        final Locator loc2 = topic.getItemIdentifiers().iterator().next();
        assertEquals(loc, loc2);
    }

    public void testTopicCreationItemIdentifierIllegal() {
        try {
            _tm.createTopicByItemIdentifier(null);
            fail("item identifier == null is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testTopicCreationAutomagicItemIdentifier() {
        assertTrue(_tm.getTopics().isEmpty());
        final Topic topic = _tm.createTopic();
        assertEquals(1, _tm.getTopics().size());
        assertTrue(_tm.getTopics().contains(topic));
        assertEquals(1, topic.getItemIdentifiers().size());
        assertTrue(topic.getSubjectIdentifiers().isEmpty());
        assertTrue(topic.getSubjectLocators().isEmpty());
    }

    public void testTopicBySubjectIdentifier() {
        final Locator loc = createLocator("http://www.example.org/");
        Topic t = _tm.getTopicBySubjectIdentifier(loc);
        assertNull(t);
        final Topic topic = _tm.createTopicBySubjectIdentifier(loc);
        t = _tm.getTopicBySubjectIdentifier(loc);
        assertNotNull(t);
        assertEquals(topic, t);
        topic.remove();
        t = _tm.getTopicBySubjectIdentifier(loc);
        assertNull(t);
    }

    public void testTopicBySubjectLocator() {
        final Locator loc = createLocator("http://www.example.org/");
        Topic t = _tm.getTopicBySubjectLocator(loc);
        assertNull(t);
        final Topic topic = _tm.createTopicBySubjectLocator(loc);
        t = _tm.getTopicBySubjectLocator(loc);
        assertNotNull(t);
        assertEquals(topic, t);
        topic.remove();
        t = _tm.getTopicBySubjectLocator(loc);
        assertNull(t);
    }

    public void testAssociationCreationType() {
        final Topic type = createTopic();
        assertTrue(_tm.getAssociations().isEmpty());
        final Association assoc = _tm.createAssociation(type);
        assertEquals(1, _tm.getAssociations().size());
        assertTrue(_tm.getAssociations().contains(assoc));
        assertTrue(assoc.getRoles().isEmpty());
        assertEquals(type, assoc.getType());
        assertTrue(assoc.getScope().isEmpty());
    }

    public void testAssociationCreationTypeScopeCollection() {
        final Topic type = createTopic();
        final Topic theme = createTopic();
        assertTrue(_tm.getAssociations().isEmpty());
        final Association assoc = _tm.createAssociation(type, Collections.singleton(theme));
        assertEquals(1, _tm.getAssociations().size());
        assertTrue(_tm.getAssociations().contains(assoc));
        assertTrue(assoc.getRoles().isEmpty());
        assertEquals(type, assoc.getType());
        assertEquals(1, assoc.getScope().size());
        assertTrue(assoc.getScope().contains(theme));
    }

    public void testAssociationCreationTypeScopeArray() {
        final Topic type = createTopic();
        final Topic theme = createTopic();
        final Topic theme2 = createTopic();
        assertTrue(_tm.getAssociations().isEmpty());
        final Association assoc = _tm.createAssociation(type, theme, theme2);
        assertEquals(1, _tm.getAssociations().size());
        assertTrue(_tm.getAssociations().contains(assoc));
        assertTrue(assoc.getRoles().isEmpty());
        assertEquals(type, assoc.getType());
        assertEquals(2, assoc.getScope().size());
        assertTrue(assoc.getScope().contains(theme));
        assertTrue(assoc.getScope().contains(theme2));
    }

    public void testAssociationCreationIllegalTypeScopeArray() {
        try {
            _tm.createAssociation(null);
            fail("Creating an association with type == null is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testAssociationCreationIllegalTypeScopeCollection() {
        try {
            _tm.createAssociation(null, Arrays.asList(createTopic()));
            fail("Creating an association with type == null is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testAssociationCreationIllegalNullCollectionScope() {
        try {
            _tm.createAssociation(createTopic(), (Topic[])null);
            fail("Creating an association with scope == null is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testAssociationCreationIllegalNullArrayScope() {
        try {
            _tm.createAssociation(createTopic(), (Collection<Topic>)null);
            fail("Creating an association with scope == (Collection) null is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testGetIndex() {
        try {
            _tm.getIndex(BogusIndex.class);
            fail("Exception expected for an unknown index");
        }
        catch (UnsupportedOperationException ex) {
            // noop.
        }
    }

    /**
     * Test index. 
     */
    private static class BogusIndex implements Index {
        public void close() {}
        public boolean isAutoUpdated() { return false; }
        public boolean isOpen() { return false; }
        public void open() { }
        public void reindex() {}
    }
}
