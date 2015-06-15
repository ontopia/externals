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
 * Indicates that a {@link Construct} is reifiable.
 * 
 * Every Topic Maps construct that is not a {@link Topic} is reifiable.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 62 $ - $Date: 2008-08-08 12:00:50 +0000 (Fri, 08 Aug 2008) $
 */
public interface Reifiable extends Construct {

    /**
     * Returns the reifier of this construct.
     * 
     * @return The topic that reifies this construct or
     *          <tt>null</tt> if this construct is not reified.
     */
    public Topic getReifier();

    /**
     * Sets the reifier of this construct.
     *
     * The specified <tt>reifier</tt> MUST NOT reify another information item.
     *
     * @param reifier The topic that should reify this construct or <tt>null</tt>
     *          if an existing reifier should be removed.
     * @throws ModelConstraintException If the specified <tt>reifier</tt> 
     *          reifies another construct.
     */
    public void setReifier(Topic reifier) throws ModelConstraintException;

}
