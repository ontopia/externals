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
 * Tests if the engine respects the constraint if a {@link Topic} is removable
 * or not.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 97 $ - $Date: 2009-02-06 16:25:36 +0000 (Fri, 06 Feb 2009) $
 */
public class TestTopicRemovableConstraint extends TMAPITestCase {

    public TestTopicRemovableConstraint(String name) {
        super(name);
    }

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
        assertEquals(topicCount+1, _tm.getTopics().size());
        typed.setType(topic);
        try {
            topic.remove();
            fail("The topic is used as type");
        }
        catch (TopicInUseException ex) {
            assertEquals(topic, ex.getReporter());
        }
        assertEquals(topicCount+1, _tm.getTopics().size());
        typed.setType(oldType);
        topic.remove();
        assertEquals(topicCount, _tm.getTopics().size());
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
        assertEquals(topicCount+1, _tm.getTopics().size());
        scoped.addTheme(topic);
        try {
            topic.remove();
            fail("The topic is used as theme");
        }
        catch (TopicInUseException ex) {
            assertEquals(topic, ex.getReporter());
        }
        assertEquals(topicCount+1, _tm.getTopics().size());
        scoped.removeTheme(topic);
        topic.remove();
        assertEquals(topicCount, _tm.getTopics().size());
    }

    /**
     * Tests if the topic removable constraint is respected if a topic
     * is used as reifier.
     *
     * @param reifiable A {@link Reifiable} that is not reified.
     */
    private void _testReifiable(Reifiable reifiable) {
        assertNull(reifiable.getReifier());
        final int topicCount = _tm.getTopics().size();
        final Topic topic = createTopic();
        assertEquals(topicCount+1, _tm.getTopics().size());
        reifiable.setReifier(topic);
        try {
            topic.remove();
            fail("The topic is used as reifier");
        }
        catch (TopicInUseException ex) {
            assertEquals(topic, ex.getReporter());
        }
        assertEquals(topicCount+1, _tm.getTopics().size());
        reifiable.setReifier(null);
        topic.remove();
        assertEquals(topicCount, _tm.getTopics().size());
    }

    /**
     * Topic map reifier test.
     */
    public void testUsedAsTopicMapReifier() {
        _testReifiable(_tm);
    }

    /**
     * Association type test.
     */
    public void testUsedAsAssociationType() {
        _testTyped(createAssociation());
    }

    /**
     * Association theme test.
     */
    public void testUsedAsAssociationTheme() {
        _testScoped(createAssociation());
    }

    /**
     * Association reifier test.
     */
    public void testUsedAsAssociationReifier() {
        _testReifiable(createAssociation());
    }

    /**
     * Role type test.
     */
    public void testUsedAsRoleType() {
        _testTyped(createRole());
    }

    /**
     * Role reifier test.
     */
    public void testUsedAsRoleReifier() {
        _testReifiable(createRole());
    }

    /**
     * Occurrence type test.
     */
    public void testUsedAsOccurrenceType() {
        _testTyped(createOccurrence());
    }

    /**
     * Occurrence theme test.
     */
    public void testUsedAsOccurrenceTheme() {
        _testScoped(createOccurrence());
    }

    /**
     * Occurrence reifier test.
     */
    public void testUsedAsOccurrenceReifier() {
        _testReifiable(createOccurrence());
    }

    /**
     * Name type test.
     */
    public void testUsedAsNameType() {
        _testTyped(createName());
    }

    /**
     * Name theme test.
     */
    public void testUsedAsNameTheme() {
        _testScoped(createName());
    }

    /**
     * Name reifier test.
     */
    public void testUsedAsNameReifier() {
        _testReifiable(createName());
    }

    /**
     * Variant theme test.
     */
    public void testUsedAsVariantTheme() {
        _testScoped(createVariant());
    }

    /**
     * Variant reifier test.
     */
    public void testUsedAsVariantReifier() {
        _testReifiable(createVariant());
    }

    /**
     * Tests if the removable constraint is respected if a topic is 
     * used as topic type.
     */
    public void testUsedAsTopicType() {
        Topic topic = createTopic();
        Topic topic2 = createTopic();
        assertEquals(2, _tm.getTopics().size());
        topic2.addType(topic);
        try {
            topic.remove();
            fail("The topic is used as topic type");
        }
        catch (TopicInUseException ex) {
            assertEquals(topic, ex.getReporter());
        }
        assertEquals(2, _tm.getTopics().size());
        topic2.removeType(topic);
        topic.remove();
        assertEquals(1, _tm.getTopics().size());
    }

    /**
     * Tests if the removable constraint is respected if a topic is 
     * used as player.
     */
    public void testUsedAsPlayer() {
        Topic topic = createTopic();
        assertEquals(1, _tm.getTopics().size());
        topic.remove();
        assertEquals(0, _tm.getTopics().size());
        topic = createTopic();
        assertEquals(1, _tm.getTopics().size());
        Association assoc = createAssociation();
        assertEquals(2, _tm.getTopics().size());
        Role role = assoc.createRole(_tm.createTopic(), topic);
        assertEquals(3, _tm.getTopics().size());
        try {
            topic.remove();
            fail("The topic is used as player");
        }
        catch (TopicInUseException ex) {
            assertEquals(topic, ex.getReporter());
        }
        role.setPlayer(createTopic());
        assertEquals(4, _tm.getTopics().size());
        topic.remove();
        assertEquals(3, _tm.getTopics().size());
    }

}
