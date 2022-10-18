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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if merging situations are detected.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 66 $ - $Date: 2008-08-20 11:26:30 +0000 (Wed, 20 Aug 2008) $
 */
public abstract class AbstractTestTopicMergeDetection extends TMAPITestCase {

    private boolean _automerge;

    protected abstract boolean getAutomergeEnabled();

    /* (non-Javadoc)
     * @see org.tmapi.core.TMAPITestCase#setUp()
     */
    @Before
    @Override
    public void setUp() throws Exception {
        TopicMapSystemFactory factory = TopicMapSystemFactory.newInstance();
        //bad hack to copy all System.Properties to factory
        for (Object obj: System.getProperties().keySet()) {
            String key = (String) obj;
            factory.setProperty(key, System.getProperty(key));
        }
        try {
            factory.setFeature("http://tmapi.org/features/automerge", getAutomergeEnabled());
            _automerge = getAutomergeEnabled();
        }
        catch (Exception ex) {
            _automerge = !getAutomergeEnabled();
        }
        _sys = factory.newTopicMapSystem();
        removeAllMaps(); // Seems to be unnecessary, but who knows
        _defaultLocator = _sys.createLocator(_DEFAULT_ADDRESS);
        _tm = _sys.createTopicMap(_defaultLocator);
    }

    /**
     * Tests if adding a duplicate subject identifier is detected.
     */
    @Test
    public void testExistingSubjectIdentifier() {
        Topic topic1 = _tm.createTopic();
        Topic topic2 = _tm.createTopic();
        Assert.assertEquals(2, _tm.getTopics().size());
        Locator loc = _tm.createLocator("http://sf.net/projects/tinytim");
        topic1.addSubjectIdentifier(loc);
        Assert.assertTrue(topic1.getSubjectIdentifiers().contains(loc));
        Assert.assertEquals(topic1, _tm.getTopicBySubjectIdentifier(loc));
        try {
            topic2.addSubjectIdentifier(loc);
            if (!_automerge) {
                Assert.fail("The duplicate subject identifier '" + loc + "' is not detected");
            }
            else {
                Assert.assertEquals(1, _tm.getTopics().size());
            }
        }
        catch (IdentityConstraintException ex) {
            if (_automerge) {
                Assert.fail("Expected that the duplicate subject identifier causes a transparent merge");
            }
            Assert.assertEquals(2, _tm.getTopics().size());
            Assert.assertEquals(2, _tm.getTopics().size());
            Assert.assertTrue(topic1.getSubjectIdentifiers().contains(loc));
            Assert.assertFalse(topic2.getSubjectIdentifiers().contains(loc));
        }
    }

    /**
     * Tests if adding a duplicate subject identifier on the SAME topic is ignored.
     */
    @Test
    public void testExistingSubjectIdentifierLegal() {
        Topic topic1 = _tm.createTopic();
        Locator loc = _tm.createLocator("http://sf.net/projects/tinytim");
        topic1.addSubjectIdentifier(loc);
        Assert.assertEquals(1, topic1.getSubjectIdentifiers().size());
        Assert.assertTrue(topic1.getSubjectIdentifiers().contains(loc));
        Assert.assertEquals(topic1, _tm.getTopicBySubjectIdentifier(loc));
        topic1.addSubjectIdentifier(loc);
        Assert.assertEquals(1, topic1.getSubjectIdentifiers().size());
    }

    /**
     * Tests if adding a duplicate subject locator is detected.
     */
    @Test
    public void testExistingSubjectLocator() {
        Topic topic1 = _tm.createTopic();
        Topic topic2 = _tm.createTopic();
        Assert.assertEquals(2, _tm.getTopics().size());
        Locator loc = _tm.createLocator("http://sf.net/projects/tinytim");
        topic1.addSubjectLocator(loc);
        Assert.assertTrue(topic1.getSubjectLocators().contains(loc));
        Assert.assertEquals(topic1, _tm.getTopicBySubjectLocator(loc));
        try {
            topic2.addSubjectLocator(loc);
            if (!_automerge) {
                Assert.fail("The duplicate subject locator '" + loc + "' is not detected");
            }
            else {
                Assert.assertEquals(1, _tm.getTopics().size());
            }
        }
        catch (IdentityConstraintException ex) {
            if (_automerge) {
                Assert.fail("Expected that the duplicate subject locator causes a transparent merge");
            }
            Assert.assertEquals(2, _tm.getTopics().size());
            Assert.assertEquals(2, _tm.getTopics().size());
            Assert.assertTrue(topic1.getSubjectLocators().contains(loc));
            Assert.assertFalse(topic2.getSubjectLocators().contains(loc));
        }
    }

    /**
     * Tests if adding a duplicate subject locator at the SAME topic is ignored.
     */
    @Test
    public void testExistingSubjectLocatorLegal() {
        Topic topic1 = _tm.createTopic();
        Locator loc = _tm.createLocator("http://sf.net/projects/tinytim");
        topic1.addSubjectLocator(loc);
        Assert.assertEquals(1, topic1.getSubjectLocators().size());
        Assert.assertTrue(topic1.getSubjectLocators().contains(loc));
        Assert.assertEquals(topic1, _tm.getTopicBySubjectLocator(loc));
        topic1.addSubjectLocator(loc);
        Assert.assertEquals(1, topic1.getSubjectLocators().size());
    }

    /**
     * Tests if adding an item identifier equals to a subject identifier is detected.
     */
    @Test
    public void testExistingSubjectIdentifierAddItemIdentifier() {
        Topic topic1 = _tm.createTopic();
        Topic topic2 = _tm.createTopic();
        Assert.assertEquals(2, _tm.getTopics().size());
        Locator loc = _tm.createLocator("http://sf.net/projects/tinytim");
        topic1.addSubjectIdentifier(loc);
        Assert.assertTrue(topic1.getSubjectIdentifiers().contains(loc));
        Assert.assertEquals(topic1, _tm.getTopicBySubjectIdentifier(loc));
        try {
            topic2.addItemIdentifier(loc);
            if (!_automerge) {
                Assert.fail("A topic with a subject identifier equals to the item identifier '" + loc + "' exists.");
            }
            else {
                Assert.assertEquals(1, _tm.getTopics().size());
            }
        }
        catch (IdentityConstraintException ex) {
            if (_automerge) {
                Assert.fail("Expected that the duplicate item identifier causes a transparent merge");
            }
            Assert.assertEquals(2, _tm.getTopics().size());
            Assert.assertTrue(topic1.getSubjectIdentifiers().contains(loc));
            Assert.assertFalse(topic2.getItemIdentifiers().contains(loc));
        }
    }

    /**
     * Tests if adding an item identifier equals to a subject identifier
     * on the SAME topic is accepted
     */
    @Test
    public void testExistingSubjectIdentifierAddItemIdentifierLegal() {
        final Locator loc = _tm.createLocator("http://sf.net/projects/tinytim");
        final Topic topic1 = _tm.createTopicBySubjectIdentifier(loc);
        Assert.assertEquals(1, topic1.getSubjectIdentifiers().size());
        Assert.assertEquals(0, topic1.getItemIdentifiers().size());
        Assert.assertTrue(topic1.getSubjectIdentifiers().contains(loc));
        Assert.assertEquals(topic1, _tm.getTopicBySubjectIdentifier(loc));
        Assert.assertNull(_tm.getConstructByItemIdentifier(loc));
        topic1.addItemIdentifier(loc);
        Assert.assertEquals(1, topic1.getSubjectIdentifiers().size());
        Assert.assertEquals(1, topic1.getItemIdentifiers().size());
        Assert.assertTrue(topic1.getSubjectIdentifiers().contains(loc));
        Assert.assertTrue(topic1.getItemIdentifiers().contains(loc));
        Assert.assertEquals(topic1, _tm.getTopicBySubjectIdentifier(loc));
        Assert.assertEquals(topic1, _tm.getConstructByItemIdentifier(loc));
    }

    /**
     * Tests if adding a subject identifier equals to an item identifier is detected.
     */
    @Test
    public void testExistingItemIdentifierAddSubjectIdentifier() {
        Topic topic1 = _tm.createTopic();
        Topic topic2 = _tm.createTopic();
        Assert.assertEquals(2, _tm.getTopics().size());
        Locator loc = _tm.createLocator("http://sf.net/projects/tinytim");
        topic1.addItemIdentifier(loc);
        Assert.assertTrue(topic1.getItemIdentifiers().contains(loc));
        Assert.assertEquals(topic1, _tm.getConstructByItemIdentifier(loc));
        try {
            topic2.addSubjectIdentifier(loc);
            if (!_automerge) {
                Assert.fail("A topic with an item identifier equals to the subject identifier '" + loc + "' exists.");
            }
            else {
                Assert.assertEquals(1, _tm.getTopics().size());
            }
        }
        catch (IdentityConstraintException ex) {
            if (_automerge) {
                Assert.fail("Expected a transparent merge for a topic with an item identifier equals to the subject identifier '" + loc + "'.");
            }
            Assert.assertEquals(2, _tm.getTopics().size());
            Assert.assertTrue(topic1.getItemIdentifiers().contains(loc));
            Assert.assertFalse(topic2.getSubjectIdentifiers().contains(loc));
        }
    }

    /**
     * Tests if adding a subject identifier equals to an item identifier 
     * on the SAME topic is accepted
     */
    @Test
    public void testExistingItemIdentifierAddSubjectIdentifierLegal() {
        final Locator loc = _tm.createLocator("http://sf.net/projects/tinytim");
        final Topic topic1 = _tm.createTopicByItemIdentifier(loc);
        Assert.assertEquals(1, topic1.getItemIdentifiers().size());
        Assert.assertEquals(0, topic1.getSubjectIdentifiers().size());
        Assert.assertTrue(topic1.getItemIdentifiers().contains(loc));
        Assert.assertEquals(topic1, _tm.getConstructByItemIdentifier(loc));
        Assert.assertNull(_tm.getTopicBySubjectIdentifier(loc));
        topic1.addSubjectIdentifier(loc);
        Assert.assertEquals(1, topic1.getSubjectIdentifiers().size());
        Assert.assertEquals(1, topic1.getItemIdentifiers().size());
        Assert.assertTrue(topic1.getSubjectIdentifiers().contains(loc));
        Assert.assertTrue(topic1.getItemIdentifiers().contains(loc));
        Assert.assertEquals(topic1, _tm.getTopicBySubjectIdentifier(loc));
        Assert.assertEquals(topic1, _tm.getConstructByItemIdentifier(loc));
    }
}
