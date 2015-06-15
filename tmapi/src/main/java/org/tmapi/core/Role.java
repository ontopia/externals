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
 * Represents an 
 * <a href="http://www.isotopicmaps.org/sam/sam-model/#sect-assoc-role">association role item</a>.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 102 $ - $Date: 2009-03-16 21:41:32 +0000 (Mon, 16 Mar 2009) $
 */
public interface Role extends Reifiable, Typed {

    /** 
     * Returns the {@link Association} to which this role belongs.
     * 
     * @see org.tmapi.core.Construct#getParent()
     * 
     * @return The association to which this role belongs.
     */
    public Association getParent();

    /**
     * Returns the topic playing this role.
     *
     * @return The role player.
     */
    public Topic getPlayer();

    /**
     * Sets the role player.
     * 
     * Any previous role player will be overridden by <tt>player</tt>.
     *
     * @param player The topic which should play this role.
     * @throws ModelConstraintException If the <tt>player</tt> is <tt>null</tt>.
     */
    public void setPlayer(Topic player);
}
