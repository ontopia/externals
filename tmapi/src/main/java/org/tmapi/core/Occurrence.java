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
 * <a href="http://www.isotopicmaps.org/sam/sam-model/#sect-occurrence">occurrence item</a>.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 62 $ - $Date: 2008-08-08 12:00:50 +0000 (Fri, 08 Aug 2008) $
 */
public interface Occurrence extends Typed, DatatypeAware {

    /** 
     * Returns the {@link Topic} to which this occurrence belongs.
     * 
     * @see org.tmapi.core.Construct#getParent()
     * 
     * @return The topic to which this occurrence belongs.
     */
    public Topic getParent();

}
