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
 * Indicates that a Topic Maps construct is typed.
 * 
 * {@link Association}s, {@link Role}s, {@link Occurrence}s, and 
 * {@link Name}s are typed.
 *
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 64 $ - $Date: 2008-08-14 11:46:44 +0000 (Thu, 14 Aug 2008) $
 */
public interface Typed extends Construct {

    /**
     * Returns the type of this construct.
     *
     * @return The topic that represents the type.
     */
    public Topic getType();

    /**
     * Sets the type of this construct.
     * 
     * Any previous type is overridden.
     * 
     * @param type The topic that should define the nature of this construct;
     *              MUST NOT be <tt>null</tt>.
     * @throws ModelConstraintException If the <tt>type</tt> is <tt>null</tt>.
     */
    public void setType(Topic type);

}
