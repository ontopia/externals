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
 * Tests against the {@link Role} interface.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 * @version $Rev: 64 $ - $Date: 2008-08-14 11:46:44 +0000 (Thu, 14 Aug 2008) $
 */
public class TestRole extends TMAPITestCase {

    /**
     * Tests the parent/child relationship between role and association.
     */
    @Test
    public void testParent() {
        final Association parent = createAssociation();
        Assert.assertTrue("Expected no roles in a newly created association",
                    parent.getRoles().isEmpty());
        final Role role = parent.createRole(createTopic(), createTopic());
        Assert.assertEquals("Unexpected role parent after creation",
                    parent, role.getParent());
        Assert.assertEquals("Expected role set size to increment for association",
                    1, parent.getRoles().size());
        Assert.assertTrue("Role is not part of getRoles()",
                    parent.getRoles().contains(role));
        role.remove();
        Assert.assertTrue("Expected role set size to decrement for association.",
                    parent.getRoles().isEmpty());
    }

    @Test
    public void testRolePlayerSetGet() {
        final Association assoc = createAssociation();
        Assert.assertTrue("Expected no roles in a newly created association",
                    assoc.getRoles().isEmpty());
        final Topic roleType = createTopic();
        final Topic player = createTopic();
        final Role role = assoc.createRole(roleType, player);
        Assert.assertEquals("Unexpected role type", roleType, role.getType());
        Assert.assertEquals("Unexpected role player", player, role.getPlayer());
        Assert.assertTrue("Role is not reported in getRolesPlayed()",
                    player.getRolesPlayed().contains(role));
        final Topic player2 = createTopic();
        role.setPlayer(player2);
        Assert.assertEquals("Unexpected role player after setting to player2",
                        player2, role.getPlayer());
        Assert.assertTrue("Role is not reported in getRolesPlayed()",
                    player2.getRolesPlayed().contains(role));
        Assert.assertTrue("'player' should not play the role anymore",
                    player.getRolesPlayed().isEmpty());
        role.setPlayer(player);
        Assert.assertEquals("Unexpected role player after setting to 'player'",
                player, role.getPlayer());
    }

    @Test
    public void testIllegalPlayer() {
        Role role = createRole();
        try {
            role.setPlayer(null);
            Assert.fail("Setting the role player to null is not allowed");
        }
        catch (ModelConstraintException ex) {
            // noop.
        }
    }
}
