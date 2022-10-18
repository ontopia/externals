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
 * Tests merging of topics.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 87 $ - $Date: 2008-11-12 13:15:50 +0000 (Wed, 12 Nov 2008) $
 */
public class TestTopicMerge extends TMAPITestCase {

    /**
     * Tests if no exception a.mergeIn(a) is accepted.
     * Must have no side effects.
     */
    @Test
    public void testTopicMergeNoop() {
        Locator sid = createLocator("http://www.tmapi.org/test/me");
        Topic topic = _tm.createTopicBySubjectIdentifier(sid);
        Assert.assertEquals(1, _tm.getTopics().size());
        Assert.assertEquals(topic, _tm.getTopicBySubjectIdentifier(sid));
        topic.mergeIn(topic);
        Assert.assertEquals(1, _tm.getTopics().size());
        Assert.assertEquals(topic, _tm.getTopicBySubjectIdentifier(sid));
    }

    /**
     *Tests if the types are merged too
     */
    @Test
    public void testTypesMerged() {
        Topic t1 = createTopic();
        Topic t2 = createTopic();
        Topic t3 = createTopic();
        t2.addType(t3);
        Assert.assertTrue(t2.getTypes().contains(t3));
        Assert.assertTrue(t1.getTypes().isEmpty());
        t1.mergeIn(t2);
        Assert.assertTrue("topic must have a type now", t1.getTypes().contains(t3));
    }

    /**
     * If topics reify different Topic Maps constructs they cannot be merged.
     */
    @Test
    public void testReifiedClash() {
        final Topic topic1 = createTopic();
        final Topic topic2 = createTopic();
        final Association assoc1 = createAssociation();
        final Association assoc2 = createAssociation();
        assoc1.setReifier(topic1);
        assoc2.setReifier(topic2);
        Assert.assertEquals(topic1, assoc1.getReifier());
        Assert.assertEquals(topic2, assoc2.getReifier());
        try {
            topic1.mergeIn(topic2);
            Assert.fail("The topics reify different Topic Maps constructs and cannot be merged");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    /**
     * Tests if a topic overtakes all roles played of the other topic.
     */
    @Test
    public void testRolePlaying() {
        final Topic topic1 = createTopic();
        final Topic topic2 = createTopic();
        final Association assoc = createAssociation();
        final Role role = assoc.createRole(createTopic(), topic2);
        Assert.assertEquals(4, _tm.getTopics().size());
        Assert.assertFalse(topic1.getRolesPlayed().contains(role));
        Assert.assertTrue(topic2.getRolesPlayed().contains(role));
        topic1.mergeIn(topic2);
        Assert.assertEquals(3, _tm.getTopics().size());
        Assert.assertTrue(topic1.getRolesPlayed().contains(role));
    }

    /**
     * Tests if the subject identifiers are overtaken.
     */
    @Test
    public void testIdentitySubjectIdentifier() {
        Locator sid1 = _tm.createLocator("http://psi.exmaple.org/sid-1");
        Locator sid2 = _tm.createLocator("http://psi.exmaple.org/sid-2");
        Topic topic1 = _tm.createTopicBySubjectIdentifier(sid1);
        Topic topic2 = _tm.createTopicBySubjectIdentifier(sid2);
        Assert.assertTrue(topic1.getSubjectIdentifiers().contains(sid1));
        Assert.assertFalse(topic1.getSubjectIdentifiers().contains(sid2));
        Assert.assertFalse(topic2.getSubjectIdentifiers().contains(sid1));
        Assert.assertTrue(topic2.getSubjectIdentifiers().contains(sid2));
        topic1.mergeIn(topic2);
        Assert.assertEquals(2, topic1.getSubjectIdentifiers().size());
        Assert.assertTrue(topic1.getSubjectIdentifiers().contains(sid1));
        Assert.assertTrue(topic1.getSubjectIdentifiers().contains(sid2));
    }

    /**
     * Tests if the subject locator are overtaken.
     */
    @Test
    public void testIdentitySubjectLocator() {
        final Locator slo1 = _tm.createLocator("http://tinytim.sf.net");
        final Locator slo2 = _tm.createLocator("http://tinytim.sourceforge.net");
        final Topic topic1 = _tm.createTopicBySubjectLocator(slo1);
        final Topic topic2 = _tm.createTopicBySubjectLocator(slo2);
        Assert.assertTrue(topic1.getSubjectLocators().contains(slo1));
        Assert.assertFalse(topic1.getSubjectLocators().contains(slo2));
        Assert.assertFalse(topic2.getSubjectLocators().contains(slo1));
        Assert.assertTrue(topic2.getSubjectLocators().contains(slo2));
        topic1.mergeIn(topic2);
        Assert.assertEquals(2, topic1.getSubjectLocators().size());
        Assert.assertTrue(topic1.getSubjectLocators().contains(slo1));
        Assert.assertTrue(topic1.getSubjectLocators().contains(slo2));
    }

    /**
     * Tests if the item identifiers are overtaken.
     */
    @Test
    public void testIdentityItemIdentifier() {
        final Locator iid1 = _tm.createLocator("http://tinytim.sf.net/test#1");
        final Locator iid2 = _tm.createLocator("http://tinytim.sf.net/test#2");
        final Topic topic1 = _tm.createTopicByItemIdentifier(iid1);
        final Topic topic2 = _tm.createTopicByItemIdentifier(iid2);
        Assert.assertTrue(topic1.getItemIdentifiers().contains(iid1));
        Assert.assertFalse(topic1.getItemIdentifiers().contains(iid2));
        Assert.assertFalse(topic2.getItemIdentifiers().contains(iid1));
        Assert.assertTrue(topic2.getItemIdentifiers().contains(iid2));
        topic1.mergeIn(topic2);
        Assert.assertEquals(2, topic1.getItemIdentifiers().size());
        Assert.assertTrue(topic1.getItemIdentifiers().contains(iid1));
        Assert.assertTrue(topic1.getItemIdentifiers().contains(iid2));
    }

    /**
     * Tests if merging detects duplicates and that the reifier is kept.
     */
    @Test
    public void testDuplicateDetectionReifier() {
        Topic topic1 = _tm.createTopic();
        Topic topic2 = _tm.createTopic();
        Topic reifier = _tm.createTopic();
        Topic type = _tm.createTopic();
        Name name1 = topic1.createName(type, "TMAPI");
        Name name2 = topic2.createName(type, "TMAPI");
        Assert.assertEquals(4, _tm.getTopics().size());
        name1.setReifier(reifier);
        Assert.assertEquals(reifier, name1.getReifier());
        Assert.assertEquals(1, topic1.getNames().size());
        Assert.assertTrue(topic1.getNames().contains(name1));
        Assert.assertEquals(1, topic2.getNames().size());
        Assert.assertTrue(topic2.getNames().contains(name2));
        topic1.mergeIn(topic2);
        Assert.assertEquals(3, _tm.getTopics().size());
        Assert.assertEquals(1, topic1.getNames().size());
        Name name = (Name) topic1.getNames().iterator().next();
        Assert.assertEquals(reifier, name.getReifier());
    }

    /**
     * Tests if merging detects duplicates and merges the reifiers of the
     * duplicates.
     */
    @Test
    public void testDuplicateDetectionReifierMerge() {
        Topic topic1 = _tm.createTopic();
        Topic topic2 = _tm.createTopic();
        Topic reifier1 = _tm.createTopic();
        Topic reifier2 = _tm.createTopic();
        Topic type = _tm.createTopic();
        Name name1 = topic1.createName(type, "TMAPI");
        Name name2 = topic2.createName(type, "TMAPI");
        Assert.assertEquals(5, _tm.getTopics().size());
        name1.setReifier(reifier1);
        name2.setReifier(reifier2);
        Assert.assertEquals(reifier1, name1.getReifier());
        Assert.assertEquals(reifier2, name2.getReifier());
        Assert.assertEquals(1, topic1.getNames().size());
        Assert.assertTrue(topic1.getNames().contains(name1));
        Assert.assertEquals(1, topic2.getNames().size());
        Assert.assertTrue(topic2.getNames().contains(name2));
        topic1.mergeIn(topic2);
        Assert.assertEquals(3, _tm.getTopics().size());
        Assert.assertEquals(1, topic1.getNames().size());
        Name name = (Name) topic1.getNames().iterator().next();
        Topic reifier = null;
        for (Topic topic: _tm.getTopics()) {
            if (!topic.equals(topic1) && !topic.equals(type)) {
                reifier = topic;
                break;
            }
        }
        Assert.assertEquals(reifier, name.getReifier());
    }

    /**
     * Tests if merging detects duplicate associations.
     */
    @Test
    public void testDuplicateSuppressionAssociation() {
        Topic topic1 = createTopic();
        Topic topic2 = createTopic();
        Topic roleType = createTopic();
        Topic type = createTopic();
        Association assoc1 =  _tm.createAssociation(type);
        Association assoc2 = _tm.createAssociation(type);
        Role role1 = assoc1.createRole(roleType, topic1);
        Role role2 = assoc2.createRole(roleType, topic2);
        Assert.assertEquals(4, _tm.getTopics().size());
        Assert.assertEquals(2, _tm.getAssociations().size());
        Assert.assertTrue(topic1.getRolesPlayed().contains(role1));
        Assert.assertTrue(topic2.getRolesPlayed().contains(role2));
        Assert.assertEquals(1, topic1.getRolesPlayed().size());
        Assert.assertEquals(1, topic2.getRolesPlayed().size());
        topic1.mergeIn(topic2);
        Assert.assertEquals(3, _tm.getTopics().size());
        Assert.assertEquals(1, _tm.getAssociations().size());
        Role role = (Role) topic1.getRolesPlayed().iterator().next();
        Assert.assertEquals(roleType, role.getType());
    }

    /**
     * Tests if merging detects duplicate names.
     */
    @Test
    public void testDuplicateSuppressionName() {
        Topic topic1 = _tm.createTopic();
        Topic topic2 = _tm.createTopic();
        Name name1 = topic1.createName("TMAPI");
        Name name2 = topic2.createName("TMAPI");
        Name name3 = topic2.createName("tiny Topic Maps engine");
        Assert.assertEquals(1, topic1.getNames().size());
        Assert.assertTrue(topic1.getNames().contains(name1));
        Assert.assertEquals(2, topic2.getNames().size());
        Assert.assertTrue(topic2.getNames().contains(name2));
        Assert.assertTrue(topic2.getNames().contains(name3));
        topic1.mergeIn(topic2);
        Assert.assertEquals(2, topic1.getNames().size());
    }

    /**
     * Tests if merging detects duplicate names and moves the variants.
     */
    @Test
    public void testDuplicateSuppressionName2() {
        Topic topic1 = _tm.createTopic();
        Topic topic2 = _tm.createTopic();
        Name name1 = topic1.createName("TMAPI");
        Name name2 = topic2.createName("TMAPI");
        Variant var = name2.createVariant("tiny", createTopic());
        Assert.assertEquals(1, topic1.getNames().size());
        Assert.assertTrue(topic1.getNames().contains(name1));
        Assert.assertEquals(0, name1.getVariants().size()); 
        Assert.assertEquals(1, topic2.getNames().size());
        Assert.assertTrue(topic2.getNames().contains(name2));
        Assert.assertEquals(1, name2.getVariants().size());
        Assert.assertTrue(name2.getVariants().contains(var));
        topic1.mergeIn(topic2);
        Assert.assertEquals(1, topic1.getNames().size());
        Name tmpName = (Name) topic1.getNames().iterator().next();
        Assert.assertEquals(1, tmpName.getVariants().size());
        Variant tmpVar = (Variant) tmpName.getVariants().iterator().next();
        Assert.assertEquals("tiny", tmpVar.getValue());
    }

    /**
     * Tests if merging detects duplicate names and sets the item 
     * identifier to the union of both names.
     */
    @Test
    public void testDuplicateSuppressionNameMoveItemIdentifiers() {
        Topic topic1 = _tm.createTopic();
        Topic topic2 = _tm.createTopic();
        Locator iid1 = _tm.createLocator("http://example.org/iid-1");
        Locator iid2 = _tm.createLocator("http://example.org/iid-2");
        Name name1 = topic1.createName("TMAPI");
        Name name2 = topic2.createName("TMAPI");
        name1.addItemIdentifier(iid1);
        name2.addItemIdentifier(iid2);
        Assert.assertTrue(name1.getItemIdentifiers().contains(iid1));
        Assert.assertTrue(name2.getItemIdentifiers().contains(iid2));
        Assert.assertEquals(1, topic1.getNames().size());
        Assert.assertTrue(topic1.getNames().contains(name1));
        Assert.assertEquals(1, topic2.getNames().size());
        Assert.assertTrue(topic2.getNames().contains(name2));
        topic1.mergeIn(topic2);
        Assert.assertEquals(1, topic1.getNames().size());
        Name name = (Name) topic1.getNames().iterator().next();
        Assert.assertEquals(2, name.getItemIdentifiers().size());
        Assert.assertTrue(name.getItemIdentifiers().contains(iid1));
        Assert.assertTrue(name.getItemIdentifiers().contains(iid2));
        Assert.assertEquals("TMAPI", name.getValue());
    }

    /**
     * Tests if merging detects duplicate occurrences.
     */
    @Test
    public void testDuplicateSuppressionOccurrence() {
        Topic topic1 = _tm.createTopic();
        Topic topic2 = _tm.createTopic();
        final Topic occType = createTopic();
        Occurrence occ1 = topic1.createOccurrence(occType, "TMAPI");
        Occurrence occ2 = topic2.createOccurrence(occType, "TMAPI");
        Occurrence occ3 = topic2.createOccurrence(occType, "tiny Topic Maps engine");
        Assert.assertEquals(1, topic1.getOccurrences().size());
        Assert.assertTrue(topic1.getOccurrences().contains(occ1));
        Assert.assertEquals(2, topic2.getOccurrences().size());
        Assert.assertTrue(topic2.getOccurrences().contains(occ2));
        Assert.assertTrue(topic2.getOccurrences().contains(occ3));
        topic1.mergeIn(topic2);
        Assert.assertEquals(2, topic1.getOccurrences().size());
    }

    /**
     * Tests if merging detects duplicate occurrences and sets the item 
     * identifier to the union of both occurrences.
     */
    @Test
    public void testDuplicateSuppressionOccurrenceMoveItemIdentifiers() {
        Topic topic1 = _tm.createTopic();
        Topic topic2 = _tm.createTopic();
        Locator iid1 = _tm.createLocator("http://example.org/iid-1");
        Locator iid2 = _tm.createLocator("http://example.org/iid-2");
        final Topic occType = createTopic();
        Occurrence occ1 = topic1.createOccurrence(occType, "TMAPI");
        occ1.addItemIdentifier(iid1);
        Assert.assertTrue(occ1.getItemIdentifiers().contains(iid1));
        Occurrence occ2 = topic2.createOccurrence(occType, "TMAPI");
        occ2.addItemIdentifier(iid2);
        Assert.assertTrue(occ2.getItemIdentifiers().contains(iid2));
        Assert.assertEquals(1, topic1.getOccurrences().size());
        Assert.assertTrue(topic1.getOccurrences().contains(occ1));
        Assert.assertEquals(1, topic2.getOccurrences().size());
        Assert.assertTrue(topic2.getOccurrences().contains(occ2));
        topic1.mergeIn(topic2);
        Assert.assertEquals(1, topic1.getOccurrences().size());
        Occurrence occ = (Occurrence) topic1.getOccurrences().iterator().next();
        Assert.assertEquals(2, occ.getItemIdentifiers().size());
        Assert.assertTrue(occ.getItemIdentifiers().contains(iid1));
        Assert.assertTrue(occ.getItemIdentifiers().contains(iid2));
        Assert.assertEquals("TMAPI", occ.getValue());
    }

}
