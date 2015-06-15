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
 * Tests against the {@link Typed} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 64 $ - $Date: 2008-08-14 11:46:44 +0000 (Thu, 14 Aug 2008) $
 */
public class TestTyped extends TMAPITestCase {

    public TestTyped(String name) {
        super(name);
    }

    protected void _testTyped(final Typed typed) {
        final Topic oldType = typed.getType();
        assertNotNull("Unexpected null type", oldType);
        final Topic type = createTopic();
        typed.setType(type);
        assertEquals("Expecting another type", type, typed.getType());
        typed.setType(oldType);
        assertEquals("Expecting the previous type", oldType, typed.getType());
        try {
            typed.setType(null);
            fail("Setting the type to null should be disallowed.");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    /**
     * Tests against an association.
     */
    public void testAssociation() {
        _testTyped(createAssociation());
    }

    /**
     * Tests against a role.
     */
    public void testRole() {
        _testTyped(createRole());
    }

    /**
     * Tests against an occurrence.
     */
    public void testOccurrence() {
        _testTyped(createOccurrence());
    }

    /**
     * Tests against a name.
     */
    public void testName() {
        _testTyped(createName());
    }

}
