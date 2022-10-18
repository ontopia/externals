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
 * Tests against the {@link Reifiable} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 97 $ - $Date: 2009-02-06 16:25:36 +0000 (Fri, 06 Feb 2009) $
 */
public class TestReifiable extends TMAPITestCase {

    /**
     * Tests setting / getting the reifier for the <tt>reifiable</tt>.
     *
     * @param reifiable The reifiable to run the tests against.
     */
    protected void _testReification(final Reifiable reifiable) {
        Assert.assertNull("Unexpected reifier property", reifiable.getReifier());
        final Topic reifier = createTopic();
        Assert.assertNull(reifier.getReified());
        reifiable.setReifier(reifier);
        Assert.assertEquals("Unexpected reifier property", reifier, reifiable.getReifier());
        Assert.assertEquals("Unexpected reified property", reifiable, reifier.getReified());
        reifiable.setReifier(null);
        Assert.assertNull("Reifier should be null", reifiable.getReifier());
        Assert.assertNull("Reified should be null", reifier.getReified());
        reifiable.setReifier(reifier);
        Assert.assertEquals("Unexpected reifier property", reifier, reifiable.getReifier());
        Assert.assertEquals("Unexpected reified property", reifiable, reifier.getReified());
        try {
            // Assigning the *same* reifier is allowed, the TM processor MUST NOT
            // raise an exception
            reifiable.setReifier(reifier);
        }
        catch (ModelConstraintException ex) {
            Assert.fail("Unexpected exception while setting the reifier to the same value");
        }
    }

    /**
     * Tests if a reifier collision (the reifier is alredy assigned to another
     * construct) is detected.
     *
     * @param reifiable The reifiable to run the tests against.
     */
    protected void _testReificationCollision(final Reifiable reifiable) {
        Assert.assertNull("Unexpected reifier property", reifiable.getReifier());
        final Topic reifier = createTopic();
        Assert.assertNull(reifier.getReified());
        final Reifiable otherReifiable = createAssociation();
        otherReifiable.setReifier(reifier);
        Assert.assertEquals("Expected a reifier property", reifier, otherReifiable.getReifier());
        Assert.assertEquals("Unexpected reified property", otherReifiable, reifier.getReified());
        try {
            reifiable.setReifier(reifier);
            Assert.fail("The reifier reifies already another construct");
        }
        catch (ModelConstraintException ex) {
            Assert.assertEquals(reifiable, ex.getReporter());
        }
        otherReifiable.setReifier(null);
        Assert.assertNull("Reifier property should be null", otherReifiable.getReifier());
        Assert.assertNull("Reified property should be null", reifier.getReified());
        reifiable.setReifier(reifier);
        Assert.assertEquals("Reifier property should have been changed", reifier, reifiable.getReifier());
        Assert.assertEquals("Reified property should have been changed", reifiable, reifier.getReified());
    }

    @Test
    public void testTopicMap() {
        _testReification(_tm);
    }

    @Test
    public void testTopicMapReifierCollision() {
        _testReificationCollision(_tm);
    }

    @Test
    public void testAssociation() {
        _testReification(createAssociation());
    }

    @Test
    public void testAssociationReifierCollision() {
        _testReificationCollision(createAssociation());
    }

    @Test
    public void testRole() {
        _testReification(createRole());
    }

    @Test
    public void testRoleReifierCollision() {
        _testReificationCollision(createRole());
    }

    @Test
    public void testOccurrence() {
        _testReification(createOccurrence());
    }

    @Test
    public void testOccurrenceReifierCollision() {
        _testReificationCollision(createOccurrence());
    }

    @Test
    public void testName() {
        _testReification(createName());
    }

    @Test
    public void testNameReifierCollision() {
        _testReificationCollision(createName());
    }

    @Test
    public void testVariant() {
        _testReification(createVariant());
    }

    @Test
    public void testVariantReifierCollision() {
        _testReificationCollision(createVariant());
    }
}
