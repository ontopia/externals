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

import java.util.Set;

/** 
 * Represents an 
 * <a href="http://www.isotopicmaps.org/sam/sam-model/#sect-association">association item</a>.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 123 $ - $Date: 2009-10-01 17:17:03 +0000 (Thu, 01 Oct 2009) $
 */
public interface Association extends Reifiable, Typed, Scoped {

    /** 
     * Returns the topic map to which this association belongs.
     * 
     * @see org.tmapi.core.Construct#getParent()
     * 
     * @return The topic map to which this association belongs.
     */
    public TopicMap getParent();

    /**
     * Returns the roles participating in this association.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @return An unmodifiable set of {@link Role}s.
     */
    public Set<Role> getRoles();

    /**
     * Returns the role types participating in this association.
     * <p>
     * This method returns the same result as the following code:
     * <pre>
     *      Set&lt;Topic&gt; types = new HashSet&lt;Topic&gt;();
     *      for (Role role: assoc.getRoles()) {
     *          types.add(role.getType());
     *      }
     * </pre>
     * </p>
     * The return value may be empty but must never be <tt>null</tt>.
     *
     * @return An unmodifiable set of role types.
     */
    public Set<Topic> getRoleTypes();

    /**
     * Returns all roles with the specified <tt>type</tt>.
     * <p>
     * This method returns the same result as the following code:
     * <pre>
     *      Set&lt;Role&gt; roles = new HashSet&lt;Role&gt;();
     *      for (Role role: assoc.getRoles()) {
     *          if (role.getType().equals(type)) {
     *              roles.add(role);
     *          }
     *      }
     * </pre>
     * </p>
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param type The type of the {@link Role} instances to be returned, 
     *              must not be <tt>null</tt>.
     * @return An unmodifiable (maybe empty) set of roles with the specified
     *          <tt>type</tt> property.
     * @throws IllegalArgumentException In case the <tt>type</tt> is <tt>null</tt>.
     */
    public Set<Role> getRoles(Topic type);

    /**
     * Creates a new {@link Role} representing a role in this association. 
     * 
     * @param type The role type; MUST NOT be <tt>null</tt>.
     * @param player The role player; MUST NOT be <tt>null</tt>.
     * @return A newly created association role.
     * @throws ModelConstraintException In case the role <tt>type</tt> or 
     *              <tt>player</tt> is <tt>null</tt>.
     */
    public Role createRole(Topic type, Topic player) throws ModelConstraintException;

}
