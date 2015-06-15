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
 * Base interface for all Topic Maps constructs.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 141 $ - $Date: 2010-01-17 21:22:27 +0000 (Sun, 17 Jan 2010) $
 */
public interface Construct {

    /**
     * Returns the parent of this construct.
     * 
     * This method returns <tt>null</tt> iff this construct is a {@link TopicMap}
     * instance.
     *
     * @return The parent of this construct or <tt>null</tt> iff the construct
     *          is an instance of {@link TopicMap}.
     */
    public Construct getParent();

    /**
     * Returns the {@link TopicMap} instance to which this Topic Maps construct 
     * belongs.
     * 
     * A {@link TopicMap} instance returns itself.
     *
     * @return The topic map instance to which this construct belongs.
     */
    public TopicMap getTopicMap();

    /**
     * Returns the identifier of this construct.
     * 
     * This property has no representation in the Topic Maps - Data Model. 
     * <p>
     * The ID can be anything, so long as no other {@link Construct} in the 
     * same topic map has the same ID.
     * </p>
     *
     * @return An identifier which identifies this construct uniquely within
     *          a topic map.
     */
    public String getId();

    /**
     * Returns the item identifiers of this Topic Maps construct.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     *
     * @return An unmodifiable set of {@link Locator}s representing the 
     *          item identifiers.
     */
    public Set<Locator> getItemIdentifiers();

    /**
     * Adds an item identifier.
     * 
     * It is not allowed to have two {@link Construct}s in the same 
     * {@link TopicMap} with the same item identifier. 
     * If the two objects are {@link Topic}s, then they must be merged. 
     * If at least one of the two objects is not a {@link Topic}, an
     * {@link IdentityConstraintException} must be reported. 
     *
     * @param itemIdentifier The item identifier to be added; must not be <tt>null</tt>.
     * @throws IdentityConstraintException If another construct has an item
     *          identifier which is equal to <tt>itemIdentifier</tt>.
     * @throws ModelConstraintException If the item identifier is <tt>null</tt>.
     */
    public void addItemIdentifier(Locator itemIdentifier) throws ModelConstraintException;

    /**
     * Removes an item identifier.
     *
     * @param itemIdentifier The item identifier to be removed from this construct, 
     *         if present (<tt>null</tt> is ignored).
     */
    public void removeItemIdentifier(Locator itemIdentifier);

    /**
     * Deletes this construct from its parent container.
     * 
     * After invocation of this method, the construct is in an undefined state and
     * must not be used further.
     */
    public void remove();

    /**
     * Returns <tt>true</tt> if the <tt>other</tt> object is equal to this one. 
     * 
     * Equality must be the result of comparing the identity (<tt>this == other</tt>) 
     * of the two objects. 
     * 
     * Note: This equality test does not reflect any equality rule according
     * to the <a href="http://www.isotopicmaps.org/sam/sam-model/">Topic Maps - Data Model (TMDM)</a>
     * by intention.
     *
     * @param other The object to compare this object against.
     * @return <tt>(this == other)</tt>
     */
    public boolean equals(Object other);

    /**
     * Returns the hash code value.
     * 
     * The returned hash code is equal to the hash code of <tt>System.identityHashCode(this)</tt>.
     *
     * @return <tt>System.identityHashCode(this)</tt>
     */
    public int hashCode();

}
