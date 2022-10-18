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
 * Tests against the {@link Association} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 107 $ - $Date: 2009-06-18 18:42:06 +0000 (Thu, 18 Jun 2009) $
 */
public class TestAssociation extends TMAPITestCase {

    /**
     * Tests the parent/child relationship between topic map and association.
     * @throws Exception If an error occurs.
     */
    @Test
    public void testParent() throws Exception {
        final TopicMap parent = createTopicMap("http://www.tmapi.org/test/assoc/parent");
        Assert.assertTrue("Expected new topic maps to be created with no associations",
                    parent.getAssociations().isEmpty());
        final Association assoc = parent.createAssociation(parent.createTopic());
        Assert.assertEquals("Unexpected association parent after creation",
                parent, assoc.getParent());
        Assert.assertEquals("Expected association set size to increment for topic map",
                    1, parent.getAssociations().size());
        Assert.assertTrue("Association is not part of getAssociations()",
                    parent.getAssociations().contains(assoc));
        assoc.remove();
        Assert.assertTrue("Expected association set size to decrement for topic map.",
                    parent.getAssociations().isEmpty());
    }

    /**
     * Test the creation of roles.
     */
    @Test
    public void testRoleCreation() {
        final Association assoc = createAssociation();
        Assert.assertTrue("Expected no roles in a newly created association",
                    assoc.getRoles().isEmpty());
        final Topic roleType = createTopic();
        final Topic player = createTopic();
        Assert.assertEquals(0, player.getRolesPlayed().size());
        final Role role = assoc.createRole(roleType, player);
        Assert.assertEquals("Unexpected role type", roleType, role.getType());
        Assert.assertEquals("Unexpected role player", player, role.getPlayer());
        Assert.assertEquals(1, player.getRolesPlayed().size());
        Assert.assertTrue(player.getRolesPlayed().contains(role));
    }

    /**
     * Tests {@link Association#getRoleTypes()}
     */
    @Test
    public void testRoleTypes() {
        final Association assoc = createAssociation();
        final Topic type1 = createTopic();
        final Topic type2 = createTopic();
        Assert.assertTrue(assoc.getRoleTypes().isEmpty());
        final Role role1 = assoc.createRole(type1, createTopic());
        Assert.assertEquals(1, assoc.getRoleTypes().size());
        Assert.assertTrue(assoc.getRoleTypes().contains(type1));
        final Role role2 = assoc.createRole(type2, createTopic());
        Assert.assertEquals(2, assoc.getRoleTypes().size());
        Assert.assertTrue(assoc.getRoleTypes().contains(type1));
        Assert.assertTrue(assoc.getRoleTypes().contains(type2));
        final Role role3 = assoc.createRole(type2, createTopic());
        Assert.assertEquals(2, assoc.getRoleTypes().size());
        Assert.assertTrue(assoc.getRoleTypes().contains(type1));
        Assert.assertTrue(assoc.getRoleTypes().contains(type2));
        role3.remove();
        Assert.assertEquals(2, assoc.getRoleTypes().size());
        Assert.assertTrue(assoc.getRoleTypes().contains(type1));
        Assert.assertTrue(assoc.getRoleTypes().contains(type2));
        role2.remove();
        Assert.assertEquals(1, assoc.getRoleTypes().size());
        Assert.assertTrue(assoc.getRoleTypes().contains(type1));
        Assert.assertFalse(assoc.getRoleTypes().contains(type2));
        role1.remove();
        Assert.assertEquals(0, assoc.getRoleTypes().size());
    }

    /**
     * Tests {@link Association#getRoles(Topic)}
     */
    @Test
    public void testRoleFilter() {
        final Association assoc = createAssociation();
        final Topic type1 = createTopic();
        final Topic type2 = createTopic();
        final Topic unusedType = createTopic();
        Assert.assertTrue(assoc.getRoles(type1).isEmpty());
        Assert.assertTrue(assoc.getRoles(type2).isEmpty());
        Assert.assertTrue(assoc.getRoles(unusedType).isEmpty());
        final Role role1 = assoc.createRole(type1, createTopic());
        Assert.assertEquals(1, assoc.getRoles(type1).size());
        Assert.assertTrue(assoc.getRoles(type1).contains(role1));
        Assert.assertTrue(assoc.getRoles(type2).isEmpty());
        Assert.assertTrue(assoc.getRoles(unusedType).isEmpty());
        final Role role2 = assoc.createRole(type2, createTopic());
        Assert.assertEquals(1, assoc.getRoles(type2).size());
        Assert.assertTrue(assoc.getRoles(type2).contains(role2));
        final Role role3 = assoc.createRole(type2, createTopic());
        Assert.assertEquals(2, assoc.getRoles(type2).size());
        Assert.assertTrue(assoc.getRoles(type2).contains(role2));
        Assert.assertTrue(assoc.getRoles(type2).contains(role3));
        Assert.assertTrue(assoc.getRoles(unusedType).isEmpty());
        role3.remove();
        Assert.assertEquals(1, assoc.getRoles(type2).size());
        Assert.assertTrue(assoc.getRoles(type2).contains(role2));
        role2.remove();
        Assert.assertEquals(0, assoc.getRoles(type2).size());
        role1.remove();
        Assert.assertEquals(0, assoc.getRoles(type1).size());
        Assert.assertEquals(0, assoc.getRoles(unusedType).size());
    }

    @Test
    public void testRoleFilterIllegal() {
        final Association assoc = createAssociation();
        try {
            assoc.getRoles(null);
            Assert.fail("getRoles(null) is illegal");
        }
        catch (IllegalArgumentException ex) {
            // noop.
        }
    }

    @Test
    public void testRoleCreationInvalidPlayer() {
        final Association assoc = createAssociation();
        Assert.assertTrue("Expected no roles in a newly created association",
                    assoc.getRoles().isEmpty());
        try {
            assoc.createRole(createTopic(), null);
            Assert.fail("Role creation where player is null shouldn't be allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }

    @Test
    public void testRoleCreationInvalidType() {
        final Association assoc = createAssociation();
        Assert.assertTrue("Expected no roles in a newly created association",
                    assoc.getRoles().isEmpty());
        try {
            assoc.createRole(null, createTopic());
            Assert.fail("Role creation where type is null shouldn't be allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }
}
