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

import org.tmapi.core.Association;
import org.tmapi.core.Construct;
import org.tmapi.core.IdentityConstraintException;
import org.tmapi.core.Locator;
import org.tmapi.core.TopicMap;

/**
 * Tests if the TMDM item identifier constraint is respected.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 149 $ - $Date: 2010-02-26 19:10:12 +0000 (Fri, 26 Feb 2010) $
 */
public class TestItemIdentifierConstraint extends TMAPITestCase {

    public TestItemIdentifierConstraint(String name) {
        super(name);
    }

    /**
     * The item identifier constraint test.
     *
     * @param tmo The Topic Maps construct to test.
     */
    private void _testConstraint(final Construct tmo) {
        assertTrue(tmo.getItemIdentifiers().isEmpty());
        final Locator iid = createLocator("http://sf.net/projects/tinytim");
        final Locator iid2 = createLocator("http://sf.net/projects/tinytim2");
        final Association assoc = createAssociation();
        assoc.addItemIdentifier(iid);
        assertFalse(tmo.getItemIdentifiers().contains(iid));
        try {
            tmo.addItemIdentifier(iid);
            fail("Topic Maps constructs with the same item identifier are not allowed");
        }
        catch (IdentityConstraintException ex) {
            assertEquals(tmo, ex.getReporter());
            assertEquals(assoc, ex.getExisting());
            assertEquals(iid, ex.getLocator());
        }
        tmo.addItemIdentifier(iid2);
        assertTrue(tmo.getItemIdentifiers().contains(iid2));
        tmo.removeItemIdentifier(iid2);
        assoc.removeItemIdentifier(iid);
        assertFalse(assoc.getItemIdentifiers().contains(iid));
        tmo.addItemIdentifier(iid);
        assertTrue(tmo.getItemIdentifiers().contains(iid));
        if (!(tmo instanceof TopicMap)) {
            // Removal should 'free' the item identifier
            tmo.remove();
            assoc.addItemIdentifier(iid);
            assertTrue(assoc.getItemIdentifiers().contains(iid));
        }
    }

    /**
     * Tests against a topic map.
     */
    public void testTopicMap() {
        _testConstraint(_tm);
    }

    /**
     * Tests against a topic.
     */
    public void testTopic() {
        _testConstraint(_tm.createTopicBySubjectIdentifier(createLocator("http://psi.example.org/test-this-topic-please")));
    }

    /**
     * Tests against an association.
     */
    public void testAssociation() {
        _testConstraint(createAssociation());
    }

    /**
     * Tests against a role.
     */
    public void testRole() {
        _testConstraint(createRole());
    }

    /**
     * Tests against an occurrence.
     */
    public void testOccurrence() {
        _testConstraint(createOccurrence());
    }

    /**
     * Tests against a name.
     */
    public void testName() {
        _testConstraint(createName());
    }

    /**
     * Tests against a variant.
     */
    public void testVariant() {
        _testConstraint(createVariant());
    }

}
