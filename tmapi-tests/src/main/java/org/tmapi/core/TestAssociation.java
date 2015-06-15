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
 * Tests against the {@link Association} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 107 $ - $Date: 2009-06-18 18:42:06 +0000 (Thu, 18 Jun 2009) $
 */
public class TestAssociation extends TMAPITestCase {

    public TestAssociation(String name) {
        super(name);
    }

    /**
     * Tests the parent/child relationship between topic map and association.
     * @throws Exception If an error occurs.
     */
    public void testParent() throws Exception {
        final TopicMap parent = createTopicMap("http://www.tmapi.org/test/assoc/parent");
        assertTrue("Expected new topic maps to be created with no associations",
                    parent.getAssociations().isEmpty());
        final Association assoc = parent.createAssociation(parent.createTopic());
        assertEquals("Unexpected association parent after creation",
                parent, assoc.getParent());
        assertEquals("Expected association set size to increment for topic map",
                    1, parent.getAssociations().size());
        assertTrue("Association is not part of getAssociations()",
                    parent.getAssociations().contains(assoc));
        assoc.remove();
        assertTrue("Expected association set size to decrement for topic map.",
                    parent.getAssociations().isEmpty());
    }

    /**
     * Test the creation of roles.
     */
    public void testRoleCreation() {
        final Association assoc = createAssociation();
        assertTrue("Expected no roles in a newly created association",
                    assoc.getRoles().isEmpty());
        final Topic roleType = createTopic();
        final Topic player = createTopic();
        assertEquals(0, player.getRolesPlayed().size());
        final Role role = assoc.createRole(roleType, player);
        assertEquals("Unexpected role type", roleType, role.getType());
        assertEquals("Unexpected role player", player, role.getPlayer());
        assertEquals(1, player.getRolesPlayed().size());
        assertTrue(player.getRolesPlayed().contains(role));
    }

    /**
     * Tests {@link Association#getRoleTypes()}
     */
    public void testRoleTypes() {
        final Association assoc = createAssociation();
        final Topic type1 = createTopic();
        final Topic type2 = createTopic();
        assertTrue(assoc.getRoleTypes().isEmpty());
        final Role role1 = assoc.createRole(type1, createTopic());
        assertEquals(1, assoc.getRoleTypes().size());
        assertTrue(assoc.getRoleTypes().contains(type1));
        final Role role2 = assoc.createRole(type2, createTopic());
        assertEquals(2, assoc.getRoleTypes().size());
        assertTrue(assoc.getRoleTypes().contains(type1));
        assertTrue(assoc.getRoleTypes().contains(type2));
        final Role role3 = assoc.createRole(type2, createTopic());
        assertEquals(2, assoc.getRoleTypes().size());
        assertTrue(assoc.getRoleTypes().contains(type1));
        assertTrue(assoc.getRoleTypes().contains(type2));
        role3.remove();
        assertEquals(2, assoc.getRoleTypes().size());
        assertTrue(assoc.getRoleTypes().contains(type1));
        assertTrue(assoc.getRoleTypes().contains(type2));
        role2.remove();
        assertEquals(1, assoc.getRoleTypes().size());
        assertTrue(assoc.getRoleTypes().contains(type1));
        assertFalse(assoc.getRoleTypes().contains(type2));
        role1.remove();
        assertEquals(0, assoc.getRoleTypes().size());
    }

    /**
     * Tests {@link Association#getRoles(Topic)}
     */
    public void testRoleFilter() {
        final Association assoc = createAssociation();
        final Topic type1 = createTopic();
        final Topic type2 = createTopic();
        final Topic unusedType = createTopic();
        assertTrue(assoc.getRoles(type1).isEmpty());
        assertTrue(assoc.getRoles(type2).isEmpty());
        assertTrue(assoc.getRoles(unusedType).isEmpty());
        final Role role1 = assoc.createRole(type1, createTopic());
        assertEquals(1, assoc.getRoles(type1).size());
        assertTrue(assoc.getRoles(type1).contains(role1));
        assertTrue(assoc.getRoles(type2).isEmpty());
        assertTrue(assoc.getRoles(unusedType).isEmpty());
        final Role role2 = assoc.createRole(type2, createTopic());
        assertEquals(1, assoc.getRoles(type2).size());
        assertTrue(assoc.getRoles(type2).contains(role2));
        final Role role3 = assoc.createRole(type2, createTopic());
        assertEquals(2, assoc.getRoles(type2).size());
        assertTrue(assoc.getRoles(type2).contains(role2));
        assertTrue(assoc.getRoles(type2).contains(role3));
        assertTrue(assoc.getRoles(unusedType).isEmpty());
        role3.remove();
        assertEquals(1, assoc.getRoles(type2).size());
        assertTrue(assoc.getRoles(type2).contains(role2));
        role2.remove();
        assertEquals(0, assoc.getRoles(type2).size());
        role1.remove();
        assertEquals(0, assoc.getRoles(type1).size());
        assertEquals(0, assoc.getRoles(unusedType).size());
    }

    public void testRoleFilterIllegal() {
        final Association assoc = createAssociation();
        try {
            assoc.getRoles(null);
            fail("getRoles(null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    public void testRoleCreationInvalidPlayer() {
        final Association assoc = createAssociation();
        assertTrue("Expected no roles in a newly created association",
                    assoc.getRoles().isEmpty());
        try {
            assoc.createRole(createTopic(), null);
            fail("Role creation where player is null shouldn't be allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    public void testRoleCreationInvalidType() {
        final Association assoc = createAssociation();
        assertTrue("Expected no roles in a newly created association",
                    assoc.getRoles().isEmpty());
        try {
            assoc.createRole(null, createTopic());
            fail("Role creation where type is null shouldn't be allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }
}
