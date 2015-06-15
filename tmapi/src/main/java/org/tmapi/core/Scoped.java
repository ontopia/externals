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
 * Indicates that a statement (Topic Maps construct) has a scope.
 *  
 * {@link Association}s, {@link Occurrence}s, {@link Name}s, and 
 * {@link Variant}s are scoped.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 124 $ - $Date: 2009-10-01 17:21:27 +0000 (Thu, 01 Oct 2009) $
 */
public interface Scoped extends Construct {

    /**
     * Returns the topics which define the scope.
     * 
     * An empty set represents the unconstrained scope.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     *
     * @return An unmodifiable set of {@link Topic}s which define the scope.
     */
    public Set<Topic> getScope();

    /**
     * Adds a topic to the scope.
     *
     * @param theme The topic which should be added to the scope.
     * @throws ModelConstraintException If the <tt>theme</tt> is <tt>null</tt>.
     */
    public void addTheme(Topic theme) throws ModelConstraintException;

    /**
     * Removes a topic from the scope.
     *
     * @param theme The topic which should be removed from the scope.
     */
    public void removeTheme(Topic theme);
}
