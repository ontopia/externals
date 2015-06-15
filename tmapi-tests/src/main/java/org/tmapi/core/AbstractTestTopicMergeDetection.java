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

/**
 * Tests if merging situations are detected.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 66 $ - $Date: 2008-08-20 11:26:30 +0000 (Wed, 20 Aug 2008) $
 */
public abstract class AbstractTestTopicMergeDetection extends TMAPITestCase {

    private boolean _automerge;

    public AbstractTestTopicMergeDetection(String name) {
        super(name);
    }

    protected abstract boolean getAutomergeEnabled();

    /* (non-Javadoc)
     * @see org.tmapi.core.TMAPITestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
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
    public void testExistingSubjectIdentifier() {
        Topic topic1 = _tm.createTopic();
        Topic topic2 = _tm.createTopic();
        assertEquals(2, _tm.getTopics().size());
        Locator loc = _tm.createLocator("http://sf.net/projects/tinytim");
        topic1.addSubjectIdentifier(loc);
        assertTrue(topic1.getSubjectIdentifiers().contains(loc));
        assertEquals(topic1, _tm.getTopicBySubjectIdentifier(loc));
        try {
            topic2.addSubjectIdentifier(loc);
            if (!_automerge) {
                fail("The duplicate subject identifier '" + loc + "' is not detected");
            }
            else {
                assertEquals(1, _tm.getTopics().size());
            }
        }
        catch (IdentityConstraintException ex) {
            if (_automerge) {
                fail("Expected that the duplicate subject identifier causes a transparent merge");
            }
            assertEquals(2, _tm.getTopics().size());
            assertEquals(2, _tm.getTopics().size());
            assertTrue(topic1.getSubjectIdentifiers().contains(loc));
            assertFalse(topic2.getSubjectIdentifiers().contains(loc));
        }
    }

    /**
     * Tests if adding a duplicate subject identifier on the SAME topic is ignored.
     */
    public void testExistingSubjectIdentifierLegal() {
        Topic topic1 = _tm.createTopic();
        Locator loc = _tm.createLocator("http://sf.net/projects/tinytim");
        topic1.addSubjectIdentifier(loc);
        assertEquals(1, topic1.getSubjectIdentifiers().size());
        assertTrue(topic1.getSubjectIdentifiers().contains(loc));
        assertEquals(topic1, _tm.getTopicBySubjectIdentifier(loc));
        topic1.addSubjectIdentifier(loc);
        assertEquals(1, topic1.getSubjectIdentifiers().size());
    }

    /**
     * Tests if adding a duplicate subject locator is detected.
     */
    public void testExistingSubjectLocator() {
        Topic topic1 = _tm.createTopic();
        Topic topic2 = _tm.createTopic();
        assertEquals(2, _tm.getTopics().size());
        Locator loc = _tm.createLocator("http://sf.net/projects/tinytim");
        topic1.addSubjectLocator(loc);
        assertTrue(topic1.getSubjectLocators().contains(loc));
        assertEquals(topic1, _tm.getTopicBySubjectLocator(loc));
        try {
            topic2.addSubjectLocator(loc);
            if (!_automerge) {
                fail("The duplicate subject locator '" + loc + "' is not detected");
            }
            else {
                assertEquals(1, _tm.getTopics().size());
            }
        }
        catch (IdentityConstraintException ex) {
            if (_automerge) {
                fail("Expected that the duplicate subject locator causes a transparent merge");
            }
            assertEquals(2, _tm.getTopics().size());
            assertEquals(2, _tm.getTopics().size());
            assertTrue(topic1.getSubjectLocators().contains(loc));
            assertFalse(topic2.getSubjectLocators().contains(loc));
        }
    }

    /**
     * Tests if adding a duplicate subject locator at the SAME topic is ignored.
     */
    public void testExistingSubjectLocatorLegal() {
        Topic topic1 = _tm.createTopic();
        Locator loc = _tm.createLocator("http://sf.net/projects/tinytim");
        topic1.addSubjectLocator(loc);
        assertEquals(1, topic1.getSubjectLocators().size());
        assertTrue(topic1.getSubjectLocators().contains(loc));
        assertEquals(topic1, _tm.getTopicBySubjectLocator(loc));
        topic1.addSubjectLocator(loc);
        assertEquals(1, topic1.getSubjectLocators().size());
    }

    /**
     * Tests if adding an item identifier equals to a subject identifier is detected.
     */
    public void testExistingSubjectIdentifierAddItemIdentifier() {
        Topic topic1 = _tm.createTopic();
        Topic topic2 = _tm.createTopic();
        assertEquals(2, _tm.getTopics().size());
        Locator loc = _tm.createLocator("http://sf.net/projects/tinytim");
        topic1.addSubjectIdentifier(loc);
        assertTrue(topic1.getSubjectIdentifiers().contains(loc));
        assertEquals(topic1, _tm.getTopicBySubjectIdentifier(loc));
        try {
            topic2.addItemIdentifier(loc);
            if (!_automerge) {
                fail("A topic with a subject identifier equals to the item identifier '" + loc + "' exists.");
            }
            else {
                assertEquals(1, _tm.getTopics().size());
            }
        }
        catch (IdentityConstraintException ex) {
            if (_automerge) {
                fail("Expected that the duplicate item identifier causes a transparent merge");
            }
            assertEquals(2, _tm.getTopics().size());
            assertTrue(topic1.getSubjectIdentifiers().contains(loc));
            assertFalse(topic2.getItemIdentifiers().contains(loc));
        }
    }

    /**
     * Tests if adding an item identifier equals to a subject identifier
     * on the SAME topic is accepted
     */
    public void testExistingSubjectIdentifierAddItemIdentifierLegal() {
        final Locator loc = _tm.createLocator("http://sf.net/projects/tinytim");
        final Topic topic1 = _tm.createTopicBySubjectIdentifier(loc);
        assertEquals(1, topic1.getSubjectIdentifiers().size());
        assertEquals(0, topic1.getItemIdentifiers().size());
        assertTrue(topic1.getSubjectIdentifiers().contains(loc));
        assertEquals(topic1, _tm.getTopicBySubjectIdentifier(loc));
        assertNull(_tm.getConstructByItemIdentifier(loc));
        topic1.addItemIdentifier(loc);
        assertEquals(1, topic1.getSubjectIdentifiers().size());
        assertEquals(1, topic1.getItemIdentifiers().size());
        assertTrue(topic1.getSubjectIdentifiers().contains(loc));
        assertTrue(topic1.getItemIdentifiers().contains(loc));
        assertEquals(topic1, _tm.getTopicBySubjectIdentifier(loc));
        assertEquals(topic1, _tm.getConstructByItemIdentifier(loc));
    }

    /**
     * Tests if adding a subject identifier equals to an item identifier is detected.
     */
    public void testExistingItemIdentifierAddSubjectIdentifier() {
        Topic topic1 = _tm.createTopic();
        Topic topic2 = _tm.createTopic();
        assertEquals(2, _tm.getTopics().size());
        Locator loc = _tm.createLocator("http://sf.net/projects/tinytim");
        topic1.addItemIdentifier(loc);
        assertTrue(topic1.getItemIdentifiers().contains(loc));
        assertEquals(topic1, _tm.getConstructByItemIdentifier(loc));
        try {
            topic2.addSubjectIdentifier(loc);
            if (!_automerge) {
                fail("A topic with an item identifier equals to the subject identifier '" + loc + "' exists.");
            }
            else {
                assertEquals(1, _tm.getTopics().size());
            }
        }
        catch (IdentityConstraintException ex) {
            if (_automerge) {
                fail("Expected a transparent merge for a topic with an item identifier equals to the subject identifier '" + loc + "'.");
            }
            assertEquals(2, _tm.getTopics().size());
            assertTrue(topic1.getItemIdentifiers().contains(loc));
            assertFalse(topic2.getSubjectIdentifiers().contains(loc));
        }
    }

    /**
     * Tests if adding a subject identifier equals to an item identifier 
     * on the SAME topic is accepted
     */
    public void testExistingItemIdentifierAddSubjectIdentifierLegal() {
        final Locator loc = _tm.createLocator("http://sf.net/projects/tinytim");
        final Topic topic1 = _tm.createTopicByItemIdentifier(loc);
        assertEquals(1, topic1.getItemIdentifiers().size());
        assertEquals(0, topic1.getSubjectIdentifiers().size());
        assertTrue(topic1.getItemIdentifiers().contains(loc));
        assertEquals(topic1, _tm.getConstructByItemIdentifier(loc));
        assertNull(_tm.getTopicBySubjectIdentifier(loc));
        topic1.addSubjectIdentifier(loc);
        assertEquals(1, topic1.getSubjectIdentifiers().size());
        assertEquals(1, topic1.getItemIdentifiers().size());
        assertTrue(topic1.getSubjectIdentifiers().contains(loc));
        assertTrue(topic1.getItemIdentifiers().contains(loc));
        assertEquals(topic1, _tm.getTopicBySubjectIdentifier(loc));
        assertEquals(topic1, _tm.getConstructByItemIdentifier(loc));
    }
}
