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
 * Tests against the {@link Reifiable} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 97 $ - $Date: 2009-02-06 16:25:36 +0000 (Fri, 06 Feb 2009) $
 */
public class TestReifiable extends TMAPITestCase {

    public TestReifiable(String name) {
        super(name);
    }

    /**
     * Tests setting / getting the reifier for the <tt>reifiable</tt>.
     *
     * @param reifiable The reifiable to run the tests against.
     */
    protected void _testReification(final Reifiable reifiable) {
        assertNull("Unexpected reifier property", reifiable.getReifier());
        final Topic reifier = createTopic();
        assertNull(reifier.getReified());
        reifiable.setReifier(reifier);
        assertEquals("Unexpected reifier property", reifier, reifiable.getReifier());
        assertEquals("Unexpected reified property", reifiable, reifier.getReified());
        reifiable.setReifier(null);
        assertNull("Reifier should be null", reifiable.getReifier());
        assertNull("Reified should be null", reifier.getReified());
        reifiable.setReifier(reifier);
        assertEquals("Unexpected reifier property", reifier, reifiable.getReifier());
        assertEquals("Unexpected reified property", reifiable, reifier.getReified());
        try {
            // Assigning the *same* reifier is allowed, the TM processor MUST NOT
            // raise an exception
            reifiable.setReifier(reifier);
        }
        catch (ModelConstraintException ex) {
            fail("Unexpected exception while setting the reifier to the same value");
        }
    }

    /**
     * Tests if a reifier collision (the reifier is alredy assigned to another
     * construct) is detected.
     *
     * @param reifiable The reifiable to run the tests against.
     */
    protected void _testReificationCollision(final Reifiable reifiable) {
        assertNull("Unexpected reifier property", reifiable.getReifier());
        final Topic reifier = createTopic();
        assertNull(reifier.getReified());
        final Reifiable otherReifiable = createAssociation();
        otherReifiable.setReifier(reifier);
        assertEquals("Expected a reifier property", reifier, otherReifiable.getReifier());
        assertEquals("Unexpected reified property", otherReifiable, reifier.getReified());
        try {
            reifiable.setReifier(reifier);
            fail("The reifier reifies already another construct");
        }
        catch (ModelConstraintException ex) {
            assertEquals(reifiable, ex.getReporter());
        }
        otherReifiable.setReifier(null);
        assertNull("Reifier property should be null", otherReifiable.getReifier());
        assertNull("Reified property should be null", reifier.getReified());
        reifiable.setReifier(reifier);
        assertEquals("Reifier property should have been changed", reifier, reifiable.getReifier());
        assertEquals("Reified property should have been changed", reifiable, reifier.getReified());
    }

    public void testTopicMap() {
        _testReification(_tm);
    }

    public void testTopicMapReifierCollision() {
        _testReificationCollision(_tm);
    }

    public void testAssociation() {
        _testReification(createAssociation());
    }

    public void testAssociationReifierCollision() {
        _testReificationCollision(createAssociation());
    }

    public void testRole() {
        _testReification(createRole());
    }

    public void testRoleReifierCollision() {
        _testReificationCollision(createRole());
    }

    public void testOccurrence() {
        _testReification(createOccurrence());
    }

    public void testOccurrenceReifierCollision() {
        _testReificationCollision(createOccurrence());
    }

    public void testName() {
        _testReification(createName());
    }

    public void testNameReifierCollision() {
        _testReificationCollision(createName());
    }

    public void testVariant() {
        _testReification(createVariant());
    }

    public void testVariantReifierCollision() {
        _testReificationCollision(createVariant());
    }
}
