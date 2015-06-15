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
 * Represents a 
 * <a href="http://www.isotopicmaps.org/sam/sam-model/#sect-variant">variant item</a>.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 64 $ - $Date: 2008-08-14 11:46:44 +0000 (Thu, 14 Aug 2008) $
 */
public interface Variant extends DatatypeAware {

    /** 
     * Returns the {@link Name} to which this variant belongs.
     * 
     * @see org.tmapi.core.Construct#getParent()
     * 
     * @return The topic name to which this variant belongs.
     */
    public Name getParent();

    /**
     * Returns the scope of this variant.
     * 
     * The returned scope is a true superset of the parent's scope.
     * 
     * @return An unmodifiable set of {@link Topic}s which define the scope.
     */
    public Set<Topic> getScope();

}
