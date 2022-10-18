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
import org.junit.Assert;
import org.junit.Test;
import org.tmapi.index.Index;

/** 
 * Tests against the {@link TopicMap} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 64 $ - $Date: 2008-08-14 11:46:44 +0000 (Thu, 14 Aug 2008) $
 */
public class TestTopicMap extends TMAPITestCase {

    /**
     * Tests if {@link TopicMap#getParent()} returns null.
     */
    @Test
    public void testParent() throws Exception {
        Assert.assertNull("A topic map has no parent", _tm.getParent());
    }

    @Test
    public void testTopicCreationSubjectIdentifier() {
        final Locator loc = createLocator("http://www.example.org/");
        Assert.assertTrue(_tm.getTopics().isEmpty());
        final Topic topic = _tm.createTopicBySubjectIdentifier(loc);
        Assert.assertEquals(1, _tm.getTopics().size());
        Assert.assertTrue(_tm.getTopics().contains(topic));
        Assert.assertEquals(1, topic.getSubjectIdentifiers().size());
        Assert.assertTrue(topic.getItemIdentifiers().isEmpty());
        Assert.assertTrue(topic.getSubjectLocators().isEmpty());
        final Locator loc2 = topic.getSubjectIdentifiers().iterator().next();
        Assert.assertEquals(loc, loc2);
    }

    @Test
    public void testTopicCreationSubjectIdentifierIllegal() {
        try {
            _tm.createTopicBySubjectIdentifier(null);
            Assert.fail("Subject identifier == null is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testTopicCreationSubjectLocator() {
        final Locator loc = createLocator("http://www.example.org/");
        Assert.assertTrue(_tm.getTopics().isEmpty());
        final Topic topic = _tm.createTopicBySubjectLocator(loc);
        Assert.assertEquals(1, _tm.getTopics().size());
        Assert.assertTrue(_tm.getTopics().contains(topic));
        Assert.assertEquals(1, topic.getSubjectLocators().size());
        Assert.assertTrue(topic.getItemIdentifiers().isEmpty());
        Assert.assertTrue(topic.getSubjectIdentifiers().isEmpty());
        final Locator loc2 = topic.getSubjectLocators().iterator().next();
        Assert.assertEquals(loc, loc2);
    }

    @Test
    public void testTopicCreationSubjectLocatorIllegal() {
        try {
            _tm.createTopicBySubjectLocator(null);
            Assert.fail("Subject locator == null is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testTopicCreationItemIdentifier() {
        final Locator loc = createLocator("http://www.example.org/");
        Assert.assertTrue(_tm.getTopics().isEmpty());
        final Topic topic = _tm.createTopicByItemIdentifier(loc);
        Assert.assertEquals(1, _tm.getTopics().size());
        Assert.assertTrue(_tm.getTopics().contains(topic));
        Assert.assertEquals(1, topic.getItemIdentifiers().size());
        Assert.assertTrue(topic.getSubjectIdentifiers().isEmpty());
        Assert.assertTrue(topic.getSubjectLocators().isEmpty());
        final Locator loc2 = topic.getItemIdentifiers().iterator().next();
        Assert.assertEquals(loc, loc2);
    }

    @Test
    public void testTopicCreationItemIdentifierIllegal() {
        try {
            _tm.createTopicByItemIdentifier(null);
            Assert.fail("item identifier == null is illegal");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testTopicCreationAutomagicItemIdentifier() {
        Assert.assertTrue(_tm.getTopics().isEmpty());
        final Topic topic = _tm.createTopic();
        Assert.assertEquals(1, _tm.getTopics().size());
        Assert.assertTrue(_tm.getTopics().contains(topic));
        Assert.assertEquals(1, topic.getItemIdentifiers().size());
        Assert.assertTrue(topic.getSubjectIdentifiers().isEmpty());
        Assert.assertTrue(topic.getSubjectLocators().isEmpty());
    }

    @Test
    public void testTopicBySubjectIdentifier() {
        final Locator loc = createLocator("http://www.example.org/");
        Topic t = _tm.getTopicBySubjectIdentifier(loc);
        Assert.assertNull(t);
        final Topic topic = _tm.createTopicBySubjectIdentifier(loc);
        t = _tm.getTopicBySubjectIdentifier(loc);
        Assert.assertNotNull(t);
        Assert.assertEquals(topic, t);
        topic.remove();
        t = _tm.getTopicBySubjectIdentifier(loc);
        Assert.assertNull(t);
    }

    @Test
    public void testTopicBySubjectLocator() {
        final Locator loc = createLocator("http://www.example.org/");
        Topic t = _tm.getTopicBySubjectLocator(loc);
        Assert.assertNull(t);
        final Topic topic = _tm.createTopicBySubjectLocator(loc);
        t = _tm.getTopicBySubjectLocator(loc);
        Assert.assertNotNull(t);
        Assert.assertEquals(topic, t);
        topic.remove();
        t = _tm.getTopicBySubjectLocator(loc);
        Assert.assertNull(t);
    }

    @Test
    public void testAssociationCreationType() {
        final Topic type = createTopic();
        Assert.assertTrue(_tm.getAssociations().isEmpty());
        final Association assoc = _tm.createAssociation(type);
        Assert.assertEquals(1, _tm.getAssociations().size());
        Assert.assertTrue(_tm.getAssociations().contains(assoc));
        Assert.assertTrue(assoc.getRoles().isEmpty());
        Assert.assertEquals(type, assoc.getType());
        Assert.assertTrue(assoc.getScope().isEmpty());
    }

    @Test
    public void testAssociationCreationTypeScopeCollection() {
        final Topic type = createTopic();
        final Topic theme = createTopic();
        Assert.assertTrue(_tm.getAssociations().isEmpty());
        final Association assoc = _tm.createAssociation(type, Collections.singleton(theme));
        Assert.assertEquals(1, _tm.getAssociations().size());
        Assert.assertTrue(_tm.getAssociations().contains(assoc));
        Assert.assertTrue(assoc.getRoles().isEmpty());
        Assert.assertEquals(type, assoc.getType());
        Assert.assertEquals(1, assoc.getScope().size());
        Assert.assertTrue(assoc.getScope().contains(theme));
    }

    @Test
    public void testAssociationCreationTypeScopeArray() {
        final Topic type = createTopic();
        final Topic theme = createTopic();
        final Topic theme2 = createTopic();
        Assert.assertTrue(_tm.getAssociations().isEmpty());
        final Association assoc = _tm.createAssociation(type, theme, theme2);
        Assert.assertEquals(1, _tm.getAssociations().size());
        Assert.assertTrue(_tm.getAssociations().contains(assoc));
        Assert.assertTrue(assoc.getRoles().isEmpty());
        Assert.assertEquals(type, assoc.getType());
        Assert.assertEquals(2, assoc.getScope().size());
        Assert.assertTrue(assoc.getScope().contains(theme));
        Assert.assertTrue(assoc.getScope().contains(theme2));
    }

    @Test
    public void testAssociationCreationIllegalTypeScopeArray() {
        try {
            _tm.createAssociation(null);
            Assert.fail("Creating an association with type == null is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testAssociationCreationIllegalTypeScopeCollection() {
        try {
            _tm.createAssociation(null, Arrays.asList(createTopic()));
            Assert.fail("Creating an association with type == null is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testAssociationCreationIllegalNullCollectionScope() {
        try {
            _tm.createAssociation(createTopic(), (Topic[])null);
            Assert.fail("Creating an association with scope == null is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testAssociationCreationIllegalNullArrayScope() {
        try {
            _tm.createAssociation(createTopic(), (Collection<Topic>)null);
            Assert.fail("Creating an association with scope == (Collection) null is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testGetIndex() {
        try {
            _tm.getIndex(BogusIndex.class);
            Assert.fail("Exception expected for an unknown index");
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
