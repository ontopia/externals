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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests merging of topic maps.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 87 $ - $Date: 2008-11-12 13:15:50 +0000 (Wed, 12 Nov 2008) $
 */
public class TestTopicMapMerge extends TMAPITestCase {

    private static final String _TM2_BASE = "http://www.sf.net/projects/tinytim/tm-2";

    private TopicMap _tm2;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        _tm2 = createTopicMap(_TM2_BASE);
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        _tm2 = null;
    }

    /**
     * Tests if no exception a.mergeIn(a) is accepted.
     * Must have no side effects.
     * 
     * @throws Exception In case of an error.
     */
    @Test
    public void testTopicMergeNoop() throws Exception {
        Locator loc = createLocator("http://www.tmapi.org/test/tm-merge-noop");
        TopicMap tm = _sys.createTopicMap(loc);
        Assert.assertEquals(tm, _sys.getTopicMap(loc));
        tm.mergeIn(_sys.getTopicMap(loc));
        Assert.assertEquals(tm, _sys.getTopicMap(loc));
    }

    /**
     * Tests merging of topics by equal item identifiers.
     */
    @Test
    public void testMergeByItemIdentifier() {
        final String ref = "http://sf.net/projects/tinytim/loc";
        Locator iidA = _tm.createLocator(ref);
        Topic topicA = _tm.createTopicByItemIdentifier(iidA);
        Locator iidB = _tm2.createLocator(ref);
        Topic topicB = _tm2.createTopicByItemIdentifier(iidB);
        Assert.assertEquals(1, _tm.getTopics().size());
        Assert.assertEquals(1, _tm2.getTopics().size());

        _tm.mergeIn(_tm2);
        Assert.assertEquals(1, _tm.getTopics().size());
        Assert.assertEquals(topicA, _tm.getConstructByItemIdentifier(iidA));
        Assert.assertEquals(1, topicA.getItemIdentifiers().size());
        Assert.assertEquals(iidA, topicA.getItemIdentifiers().iterator().next());
        Assert.assertEquals(0, topicA.getSubjectIdentifiers().size());
        Assert.assertEquals(0, topicA.getSubjectLocators().size());

        // MergeIn must not have any side effects on tm2
        Assert.assertEquals(1, _tm2.getTopics().size());
        Assert.assertEquals(topicB, _tm2.getConstructByItemIdentifier(iidB));
        Assert.assertEquals(1, topicB.getItemIdentifiers().size());
        Assert.assertEquals(iidB, topicB.getItemIdentifiers().iterator().next());
        Assert.assertEquals(0, topicB.getSubjectIdentifiers().size());
        Assert.assertEquals(0, topicB.getSubjectLocators().size());
    }

    /**
     * Tests merging of topics by equal subject identifiers.
     */
    @Test
    public void testMergeBySubjectIdentifier() {
        final String ref = "http://sf.net/projects/tinytim/loc";
        Locator sidA = _tm.createLocator(ref);
        Topic topicA = _tm.createTopicBySubjectIdentifier(sidA);
        Locator sidB = _tm2.createLocator(ref);
        Topic topicB = _tm2.createTopicBySubjectIdentifier(sidB);
        Assert.assertEquals(1, _tm.getTopics().size());
        Assert.assertEquals(1, _tm2.getTopics().size());

        _tm.mergeIn(_tm2);
        Assert.assertEquals(1, _tm.getTopics().size());
        Assert.assertEquals(topicA, _tm.getTopicBySubjectIdentifier(sidA));
        Assert.assertEquals(1, topicA.getSubjectIdentifiers().size());
        Assert.assertEquals(sidA, topicA.getSubjectIdentifiers().iterator().next());
        Assert.assertEquals(0, topicA.getItemIdentifiers().size());
        Assert.assertEquals(0, topicA.getSubjectLocators().size());

        // MergeIn must not have any side effects on tm2
        Assert.assertEquals(1, _tm2.getTopics().size());
        Assert.assertEquals(topicB, _tm2.getTopicBySubjectIdentifier(sidB));
        Assert.assertEquals(1, topicB.getSubjectIdentifiers().size());
        Assert.assertEquals(sidB, topicB.getSubjectIdentifiers().iterator().next());
        Assert.assertEquals(0, topicB.getItemIdentifiers().size());
        Assert.assertEquals(0, topicB.getSubjectLocators().size());
    }

    /**
     * Tests merging of topics by equal subject locators.
     */
    @Test
    public void testMergeBySubjectLocator() {
        final String ref = "http://sf.net/projects/tinytim/loc";
        Locator sloA = _tm.createLocator(ref);
        Topic topicA = _tm.createTopicBySubjectLocator(sloA);
        Locator sloB = _tm2.createLocator(ref);
        Topic topicB = _tm2.createTopicBySubjectLocator(sloB);
        Assert.assertEquals(1, _tm.getTopics().size());
        Assert.assertEquals(1, _tm2.getTopics().size());

        _tm.mergeIn(_tm2);
        Assert.assertEquals(1, _tm.getTopics().size());
        Assert.assertEquals(topicA, _tm.getTopicBySubjectLocator(sloA));
        Assert.assertEquals(1, topicA.getSubjectLocators().size());
        Assert.assertEquals(sloA, topicA.getSubjectLocators().iterator().next());
        Assert.assertEquals(0, topicA.getItemIdentifiers().size());
        Assert.assertEquals(0, topicA.getSubjectIdentifiers().size());

        // MergeIn must not have any side effects on tm2
        Assert.assertEquals(1, _tm2.getTopics().size());
        Assert.assertEquals(topicB, _tm2.getTopicBySubjectLocator(sloB));
        Assert.assertEquals(1, topicB.getSubjectLocators().size());
        Assert.assertEquals(sloB, topicB.getSubjectLocators().iterator().next());
        Assert.assertEquals(0, topicB.getItemIdentifiers().size());
        Assert.assertEquals(0, topicB.getSubjectIdentifiers().size());
    }

    /**
     * Tests merging of topics by existing topic with item identifier equals
     * to a topic's subject identifier from the other map.
     */
    @Test
    public void testMergeItemIdentifierEqSubjectIdentifier() {
        final String ref = "http://sf.net/projects/tinytim/loc";
        Locator locA = _tm.createLocator(ref);
        Topic topicA = _tm.createTopicByItemIdentifier(locA);
        Locator locB = _tm2.createLocator(ref);
        Topic topicB = _tm2.createTopicBySubjectIdentifier(locB);
        Assert.assertEquals(1, _tm.getTopics().size());
        Assert.assertEquals(1, _tm2.getTopics().size());
        Assert.assertEquals(topicA, _tm.getConstructByItemIdentifier(locA));
        Assert.assertNull(_tm.getTopicBySubjectIdentifier(locA));

        _tm.mergeIn(_tm2);
        Assert.assertEquals(1, _tm.getTopics().size());
        Assert.assertEquals(topicA, _tm.getConstructByItemIdentifier(locA));
        Assert.assertEquals(topicA, _tm.getTopicBySubjectIdentifier(locA));
        Assert.assertEquals(1, topicA.getSubjectIdentifiers().size());
        Assert.assertEquals(locA, topicA.getSubjectIdentifiers().iterator().next());
        Assert.assertEquals(1, topicA.getItemIdentifiers().size());
        Assert.assertEquals(locA, topicA.getItemIdentifiers().iterator().next());
        Assert.assertEquals(0, topicA.getSubjectLocators().size());

        // No side effects on tm2
        Assert.assertEquals(1, _tm2.getTopics().size());
        Assert.assertNull(_tm2.getConstructByItemIdentifier(locB));
        Assert.assertEquals(topicB, _tm2.getTopicBySubjectIdentifier(locB));
        Assert.assertEquals(1, topicB.getSubjectIdentifiers().size());
        Assert.assertEquals(locB, topicB.getSubjectIdentifiers().iterator().next());
        Assert.assertEquals(0, topicB.getItemIdentifiers().size());
        Assert.assertEquals(0, topicA.getSubjectLocators().size());
    }

    /**
     * Tests merging of topics by existing topic with subject identifier equals
     * to a topic's item identifier from the other map.
     */
    @Test
    public void testMergeSubjectIdentifierEqItemIdentifier() {
        final String ref = "http://sf.net/projects/tinytim/loc";
        Locator locA = _tm.createLocator(ref);
        Topic topicA = _tm.createTopicBySubjectIdentifier(locA);
        Locator locB = _tm2.createLocator(ref);
        Topic topicB = _tm2.createTopicByItemIdentifier(locB);
        Assert.assertEquals(1, _tm.getTopics().size());
        Assert.assertEquals(1, _tm2.getTopics().size());
        Assert.assertNull(_tm.getConstructByItemIdentifier(locA));
        Assert.assertEquals(topicA, _tm.getTopicBySubjectIdentifier(locA));
        
        _tm.mergeIn(_tm2);
        Assert.assertEquals(1, _tm.getTopics().size());
        Assert.assertEquals(topicA, _tm.getConstructByItemIdentifier(locA));
        Assert.assertEquals(topicA, _tm.getTopicBySubjectIdentifier(locA));
        Assert.assertEquals(1, topicA.getSubjectIdentifiers().size());
        Assert.assertEquals(locA, topicA.getSubjectIdentifiers().iterator().next());
        Assert.assertEquals(1, topicA.getItemIdentifiers().size());
        Assert.assertEquals(locA, topicA.getItemIdentifiers().iterator().next());
        Assert.assertEquals(0, topicA.getSubjectLocators().size());
        
        // No side effects on tm2
        Assert.assertEquals(1, _tm2.getTopics().size());
        Assert.assertNull(_tm2.getTopicBySubjectIdentifier(locB));
        Assert.assertEquals(topicB, _tm2.getConstructByItemIdentifier(locB));
        Assert.assertEquals(1, topicB.getItemIdentifiers().size());
        Assert.assertEquals(locB, topicB.getItemIdentifiers().iterator().next());
        Assert.assertEquals(0, topicB.getSubjectIdentifiers().size());
        Assert.assertEquals(0, topicA.getSubjectLocators().size());
    }

    /**
     * Tests if topics are added to a topic map from another topic map.
     */
    @Test
    public void testAddTopicsFromOtherMap() {
        final String refA = "http://www.tmapi.org/#iid-A";
        final String refB = "http://www.tmapi.org/#iid-B";
        final Locator locA = _tm.createLocator(refA);
        final Topic topicA = _tm.createTopicByItemIdentifier(locA);
        final Locator locB = _tm2.createLocator(refB);
        final Topic topicB = _tm2.createTopicByItemIdentifier(locB);
        // Check tm 
        Assert.assertEquals(1, _tm.getTopics().size());
        Assert.assertEquals(topicA, _tm.getConstructByItemIdentifier(locA));
        Assert.assertNull(_tm.getConstructByItemIdentifier(locB));
        // Check tm2
        Assert.assertEquals(1, _tm2.getTopics().size());
        Assert.assertEquals(topicB, _tm2.getConstructByItemIdentifier(locB));
        Assert.assertNull(_tm2.getConstructByItemIdentifier(locA));
        _tm.mergeIn(_tm2);
        Assert.assertEquals(2, _tm.getTopics().size());
        // Check if topicA is unchanged
        Assert.assertEquals(topicA, _tm.getConstructByItemIdentifier(locA));
        Assert.assertEquals(1, topicA.getItemIdentifiers().size());
        Assert.assertEquals(locA, topicA.getItemIdentifiers().iterator().next());
        Assert.assertEquals(0, topicA.getSubjectIdentifiers().size());
        Assert.assertEquals(0, topicA.getSubjectLocators().size());
        // Check the new topic (which is topicB in tm2)
        final Topic newTopic = (Topic) _tm.getConstructByItemIdentifier(locB);
        Assert.assertNotNull(newTopic);
        Assert.assertEquals(1, newTopic.getItemIdentifiers().size());
        Assert.assertEquals(locB, newTopic.getItemIdentifiers().iterator().next());
        Assert.assertEquals(0, newTopic.getSubjectIdentifiers().size());
        Assert.assertEquals(0, newTopic.getSubjectLocators().size());
    }

}
