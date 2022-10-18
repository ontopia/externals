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
import org.junit.Test;

/**
 * Tests if the engine respects the constraint if a {@link Topic} is removable
 * or not.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 97 $ - $Date: 2009-02-06 16:25:36 +0000 (Fri, 06 Feb 2009) $
 */
public class TestTopicRemovableConstraint extends TMAPITestCase {

    /**
     * Tests if the topic removable constraint is respected if a topic 
     * is used as type.
     *
     * @param typed A typed construct.
     */
    private void _testTyped(Typed typed) {
        final int topicCount = _tm.getTopics().size();
        final Topic oldType = typed.getType();
        final Topic topic = createTopic();
        Assert.assertEquals(topicCount+1, _tm.getTopics().size());
        typed.setType(topic);
        try {
            topic.remove();
            Assert.fail("The topic is used as type");
        }
        catch (TopicInUseException ex) {
            Assert.assertEquals(topic, ex.getReporter());
        }
        Assert.assertEquals(topicCount+1, _tm.getTopics().size());
        typed.setType(oldType);
        topic.remove();
        Assert.assertEquals(topicCount, _tm.getTopics().size());
    }

    /**
     * Tests if the topic removable constraint is respected if a topic 
     * is used as theme.
     *
     * @param scoped A scoped construct.
     */
    private void _testScoped(Scoped scoped) {
        final int topicCount = _tm.getTopics().size();
        final Topic topic = createTopic();
        Assert.assertEquals(topicCount+1, _tm.getTopics().size());
        scoped.addTheme(topic);
        try {
            topic.remove();
            Assert.fail("The topic is used as theme");
        }
        catch (TopicInUseException ex) {
            Assert.assertEquals(topic, ex.getReporter());
        }
        Assert.assertEquals(topicCount+1, _tm.getTopics().size());
        scoped.removeTheme(topic);
        topic.remove();
        Assert.assertEquals(topicCount, _tm.getTopics().size());
    }

    /**
     * Tests if the topic removable constraint is respected if a topic
     * is used as reifier.
     *
     * @param reifiable A {@link Reifiable} that is not reified.
     */
    private void _testReifiable(Reifiable reifiable) {
        Assert.assertNull(reifiable.getReifier());
        final int topicCount = _tm.getTopics().size();
        final Topic topic = createTopic();
        Assert.assertEquals(topicCount+1, _tm.getTopics().size());
        reifiable.setReifier(topic);
        try {
            topic.remove();
            Assert.fail("The topic is used as reifier");
        }
        catch (TopicInUseException ex) {
            Assert.assertEquals(topic, ex.getReporter());
        }
        Assert.assertEquals(topicCount+1, _tm.getTopics().size());
        reifiable.setReifier(null);
        topic.remove();
        Assert.assertEquals(topicCount, _tm.getTopics().size());
    }

    /**
     * Topic map reifier test.
     */
    @Test
    public void testUsedAsTopicMapReifier() {
        _testReifiable(_tm);
    }

    /**
     * Association type test.
     */
    @Test
    public void testUsedAsAssociationType() {
        _testTyped(createAssociation());
    }

    /**
     * Association theme test.
     */
    @Test
    public void testUsedAsAssociationTheme() {
        _testScoped(createAssociation());
    }

    /**
     * Association reifier test.
     */
    @Test
    public void testUsedAsAssociationReifier() {
        _testReifiable(createAssociation());
    }

    /**
     * Role type test.
     */
    @Test
    public void testUsedAsRoleType() {
        _testTyped(createRole());
    }

    /**
     * Role reifier test.
     */
    @Test
    public void testUsedAsRoleReifier() {
        _testReifiable(createRole());
    }

    /**
     * Occurrence type test.
     */
    @Test
    public void testUsedAsOccurrenceType() {
        _testTyped(createOccurrence());
    }

    /**
     * Occurrence theme test.
     */
    @Test
    public void testUsedAsOccurrenceTheme() {
        _testScoped(createOccurrence());
    }

    /**
     * Occurrence reifier test.
     */
    @Test
    public void testUsedAsOccurrenceReifier() {
        _testReifiable(createOccurrence());
    }

    /**
     * Name type test.
     */
    @Test
    public void testUsedAsNameType() {
        _testTyped(createName());
    }

    /**
     * Name theme test.
     */
    @Test
    public void testUsedAsNameTheme() {
        _testScoped(createName());
    }

    /**
     * Name reifier test.
     */
    @Test
    public void testUsedAsNameReifier() {
        _testReifiable(createName());
    }

    /**
     * Variant theme test.
     */
    @Test
    public void testUsedAsVariantTheme() {
        _testScoped(createVariant());
    }

    /**
     * Variant reifier test.
     */
    @Test
    public void testUsedAsVariantReifier() {
        _testReifiable(createVariant());
    }

    /**
     * Tests if the removable constraint is respected if a topic is 
     * used as topic type.
     */
    @Test
    public void testUsedAsTopicType() {
        Topic topic = createTopic();
        Topic topic2 = createTopic();
        Assert.assertEquals(2, _tm.getTopics().size());
        topic2.addType(topic);
        try {
            topic.remove();
            Assert.fail("The topic is used as topic type");
        }
        catch (TopicInUseException ex) {
            Assert.assertEquals(topic, ex.getReporter());
        }
        Assert.assertEquals(2, _tm.getTopics().size());
        topic2.removeType(topic);
        topic.remove();
        Assert.assertEquals(1, _tm.getTopics().size());
    }

    /**
     * Tests if the removable constraint is respected if a topic is 
     * used as player.
     */
    @Test
    public void testUsedAsPlayer() {
        Topic topic = createTopic();
        Assert.assertEquals(1, _tm.getTopics().size());
        topic.remove();
        Assert.assertEquals(0, _tm.getTopics().size());
        topic = createTopic();
        Assert.assertEquals(1, _tm.getTopics().size());
        Association assoc = createAssociation();
        Assert.assertEquals(2, _tm.getTopics().size());
        Role role = assoc.createRole(_tm.createTopic(), topic);
        Assert.assertEquals(3, _tm.getTopics().size());
        try {
            topic.remove();
            Assert.fail("The topic is used as player");
        }
        catch (TopicInUseException ex) {
            Assert.assertEquals(topic, ex.getReporter());
        }
        role.setPlayer(createTopic());
        Assert.assertEquals(4, _tm.getTopics().size());
        topic.remove();
        Assert.assertEquals(3, _tm.getTopics().size());
    }

}
