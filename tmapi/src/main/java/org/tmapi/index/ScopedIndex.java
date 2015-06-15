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
package org.tmapi.index;

import java.util.Collection;

import org.tmapi.core.Association;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Topic;
import org.tmapi.core.Variant;

/**
 * Index for {@link org.tmapi.core.Scoped} statements and their scope.
 * 
 * This index provides access to {@link Association}s, {@link Occurrence}s,
 * {@link Name}s, and {@link Variant}s by their scope property and to
 * {@link Topic}s which are used as theme in a scope.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 65 $ - $Date: 2008-08-19 11:14:19 +0000 (Tue, 19 Aug 2008) $
 */
public interface ScopedIndex extends Index {

    /**
     * Returns the {@link Association}s in the topic map whose scope property 
     * contains the specified <tt>theme</tt>.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param theme The {@link Topic} which must be part of the scope. If
     *              it is <tt>null</tt> all {@link Association}s
     *              in the unconstrained scope are returned.
     * @return An unmodifiable collection of {@link Association}s.
     */
    public Collection<Association> getAssociations(Topic theme);

    /**
     * Returns the {@link Association}s in the topic map whose scope property 
     * equals one of those <tt>themes</tt> at least.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param themes Scope of the {@link Association}s to be returned.
     * @param matchAll If <tt>true</tt> the scope property of an association 
     *                  must match all <tt>themes</tt>, if <tt>false</tt> one 
     *                  theme must be matched at least.
     * @return An unmodifiable collection of {@link Association}s.
     * @throws IllegalArgumentException If <tt>themes</tt> is <tt>null</tt>.
     */
    public Collection<Association> getAssociations(Topic[] themes,
            boolean matchAll);

    /**
     * Returns the topics in the topic map used in the scope property of 
     * {@link Association}s.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @return An unmodifiable collection of {@link Topic}s.
     */
    public Collection<Topic> getAssociationThemes();

    /**
     * Returns the {@link Occurrence}s in the topic map whose scope property 
     * contains the specified <tt>theme</tt>.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param theme The {@link Topic} which must be part of the scope. If
     *              it is <tt>null</tt> all {@link Occurrence}s
     *              in the unconstrained scope are returned.
     * @return An unmodifiable collection of {@link org.tmapi.core.Occurrence}s.
     */
    public Collection<Occurrence> getOccurrences(Topic theme);

    /**
     * Returns the {@link Occurrence}s in the topic map whose scope property 
     * equals one of those <tt>themes</tt> at least.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param themes Scope of the {@link Occurrence}s to be returned.
     * @param matchAll If <tt>true</tt> the scope property of an occurrence 
     *                  must match all <tt>themes</tt>, if <tt>false</tt> one 
     *                  theme must be matched at least.
     * @return An unmodifiable collection of {@link Occurrence}s.
     * @throws IllegalArgumentException If <tt>themes</tt> is <tt>null</tt>.
     */
    public Collection<Occurrence> getOccurrences(Topic[] themes,
            boolean matchAll);

    /**
     * Returns the topics in the topic map used in the scope property of 
     * {@link Occurrence}s.
     * 
     * The return value may be empty but must never be <tt>null</tt>. 
     * 
     * @return An unmodifiable collection of {@link Topic}s.
     */
    public Collection<Topic> getOccurrenceThemes();

    /**
     * Returns the {@link Name}s in the topic map whose scope property 
     * contains the specified <tt>theme</tt>.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param theme The {@link Topic} which must be part of the scope. If
     *              it is <tt>null</tt> all {@link Name}s
     *              in the unconstrained scope are returned.
     * @return An unmodifiable collection of {@link Name}s.
     */
    public Collection<Name> getNames(Topic theme);

    /**
     * Returns the {@link Name}s in the topic map whose scope property 
     * equals one of those <tt>themes</tt> at least.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param themes Scope of the {@link Name}s to be returned.
     * @param matchAll If <tt>true</tt> the scope property of a name 
     *                  must match all <tt>themes</tt>, if <tt>false</tt> one 
     *                  theme must be matched at least.
     * @return An unmodifiable collection of {@link Name}s.
     * @throws IllegalArgumentException If <tt>themes</tt> is <tt>null</tt>.
     */
    public Collection<Name> getNames(Topic[] themes, boolean matchAll);

    /**
     * Returns the topics in the topic map used in the scope property of 
     * {@link Name}s.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @return An unmodifiable collection of {@link Topic}s.
     */
    public Collection<Topic> getNameThemes();

    /**
     * Returns the {@link Variant}s in the topic map whose scope property 
     * contains the specified <tt>theme</tt>.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param theme The {@link Topic} which must be part of the scope. This
     *              must not be <tt>null</tt>.
     * @return An unmodifiable collection of {@link Variant}s.
     * @throws IllegalArgumentException If <tt>theme</tt> is <tt>null</tt>.
     */
    public Collection<Variant> getVariants(Topic theme);

    /**
     * Returns the {@link Variant}s in the topic map whose scope property 
     * equals one of those <tt>themes</tt> at least.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param themes Scope of the {@link Variant}s to be returned.
     * @param matchAll If <tt>true</tt> the scope property of a variant 
     *                  must match all <tt>themes</tt>, if <tt>false</tt> one 
     *                  theme must be matched at least.
     * @return An unmodifiable collection of {@link Variant}s.
     * @throws IllegalArgumentException If <tt>themes</tt> is <tt>null</tt>.
     */
    public Collection<Variant> getVariants(Topic[] themes, boolean matchAll);

    /**
     * Returns the topics in the topic map used in the scope property of 
     * {@link Variant}s.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @return An unmodifiable collection of {@link Topic}s.
     */
    public Collection<Topic> getVariantThemes();

}
