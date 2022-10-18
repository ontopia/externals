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
 * Tests if the TMDM item identifier constraint is respected.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 149 $ - $Date: 2010-02-26 19:10:12 +0000 (Fri, 26 Feb 2010) $
 */
public class TestItemIdentifierConstraint extends TMAPITestCase {

    /**
     * The item identifier constraint test.
     *
     * @param tmo The Topic Maps construct to test.
     */
    private void _testConstraint(final Construct tmo) {
        Assert.assertTrue(tmo.getItemIdentifiers().isEmpty());
        final Locator iid = createLocator("http://sf.net/projects/tinytim");
        final Locator iid2 = createLocator("http://sf.net/projects/tinytim2");
        final Association assoc = createAssociation();
        assoc.addItemIdentifier(iid);
        Assert.assertFalse(tmo.getItemIdentifiers().contains(iid));
        try {
            tmo.addItemIdentifier(iid);
            Assert.fail("Topic Maps constructs with the same item identifier are not allowed");
        }
        catch (IdentityConstraintException ex) {
            Assert.assertEquals(tmo, ex.getReporter());
            Assert.assertEquals(assoc, ex.getExisting());
            Assert.assertEquals(iid, ex.getLocator());
        }
        tmo.addItemIdentifier(iid2);
        Assert.assertTrue(tmo.getItemIdentifiers().contains(iid2));
        tmo.removeItemIdentifier(iid2);
        assoc.removeItemIdentifier(iid);
        Assert.assertFalse(assoc.getItemIdentifiers().contains(iid));
        tmo.addItemIdentifier(iid);
        Assert.assertTrue(tmo.getItemIdentifiers().contains(iid));
        if (!(tmo instanceof TopicMap)) {
            // Removal should 'free' the item identifier
            tmo.remove();
            assoc.addItemIdentifier(iid);
            Assert.assertTrue(assoc.getItemIdentifiers().contains(iid));
        }
    }

    /**
     * Tests against a topic map.
     */
    @Test
    public void testTopicMap() {
        _testConstraint(_tm);
    }

    /**
     * Tests against a topic.
     */
    @Test
    public void testTopic() {
        _testConstraint(_tm.createTopicBySubjectIdentifier(createLocator("http://psi.example.org/test-this-topic-please")));
    }

    /**
     * Tests against an association.
     */
    @Test
    public void testAssociation() {
        _testConstraint(createAssociation());
    }

    /**
     * Tests against a role.
     */
    @Test
    public void testRole() {
        _testConstraint(createRole());
    }

    /**
     * Tests against an occurrence.
     */
    @Test
    public void testOccurrence() {
        _testConstraint(createOccurrence());
    }

    /**
     * Tests against a name.
     */
    @Test
    public void testName() {
        _testConstraint(createName());
    }

    /**
     * Tests against a variant.
     */
    @Test
    public void testVariant() {
        _testConstraint(createVariant());
    }

}
