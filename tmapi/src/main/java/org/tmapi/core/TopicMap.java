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

import java.util.Collection;
import java.util.Set;

import org.tmapi.index.Index;

/** 
 * Represents a 
 * <a href="http://www.isotopicmaps.org/sam/sam-model/#d0e657">topic map item</a>.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 173 $ - $Date: 2010-03-10 13:02:21 +0000 (Wed, 10 Mar 2010) $
 */
public interface TopicMap extends Reifiable {

    /**
     * Returns <tt>null</tt>.
     * 
     * @return <tt>null</tt> since topic maps do not have a parent.
     */
    public Construct getParent();

    /**
     * Returns all {@link Topic}s contained in this topic map.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     *
     * @return An unmodifiable set of {@link Topic}s.
     */
    public Set<Topic> getTopics();

    /**
     * Returns the {@link Locator} that was used to create the topic map.
     * <p>
     * Note: The returned locator represents the storage address of the topic map
     * and implies no further semantics.
     * </p>
     * 
     * @see org.tmapi.core.TopicMapSystem#createTopicMap(org.tmapi.core.Locator)
     * @see org.tmapi.core.TopicMapSystem#getTopicMap(org.tmapi.core.Locator)
     *
     * @return A {@link Locator}, never {@code null}.
     */
    public Locator getLocator();
    
    /**
     * Returns all {@link Association}s contained in this topic map.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     *
     * @return An unmodifiable set of {@link Association}s.
     */
    public Set<Association> getAssociations();

    /**
     * Returns a topic by its subject identifier.
     * <p>
     * If no topic with the specified subject identifier exists, this method
     * returns <tt>null</tt>.
     * </p>
     * 
     * @param subjectIdentifier The subject identifier of the topic to be returned.
     * @return A topic with the specified subject identifier or <tt>null</tt>
     *          if no such topic exists in the topic map.
     */
    public Topic getTopicBySubjectIdentifier(Locator subjectIdentifier);

    /**
     * Returns a topic by its subject locator.
     * <p>
     * If no topic with the specified subject locator exists, this method
     * returns <tt>null</tt>.
     * </p>
     * 
     * @param subjectLocator The subject locator of the topic to be returned.
     * @return A topic with the specified subject locator or <tt>null</tt>
     *          if no such topic exists in the topic map.
     */
    public Topic getTopicBySubjectLocator(Locator subjectLocator);

    /**
     * Returns a {@link Construct} by its item identifier.
     *
     * @param itemIdentifier The item identifier of the construct to be returned.
     * @return A construct with the specified item identifier or <tt>null</tt>
     *          if no such construct exists in the topic map.
     */
    public Construct getConstructByItemIdentifier(Locator itemIdentifier);

    /**
     * Returns a {@link Construct} by its (system specific) identifier.
     *
     * @param id The identifier of the construct to be returned.
     * @return The construct with the specified id or <tt>null</tt> if such a 
     *          construct is unknown.
     */
    public Construct getConstructById(String id);

    /**
     * Returns a {@link Locator} instance representing the specified IRI 
     * <tt>reference</tt>.
     * 
     * The specified IRI <tt>reference</tt> is assumed to be absolute.
     *
     * @param reference A string which uses the IRI notation.
     * @return A {@link Locator} representing the IRI <tt>reference</tt>.
     * @throws IllegalArgumentException If <tt>reference</tt> is <tt>null</tt>.
     * @throws MalformedIRIException If the provided string cannot be used to create a valid locator.
     */
    public Locator createLocator(String reference) throws MalformedIRIException;

    /**
     * Returns a {@link Topic} instance with the specified subject identifier.
     * <p>
     * This method returns either an existing {@link Topic} or creates a new
     * {@link Topic} instance with the specified subject identifier.
     * </p>
     * <p>
     * If a topic with the specified subject identifier exists in the topic map,
     * that topic is returned. If a topic with an item identifier equals to
     * the specified subject identifier exists, the specified subject identifier
     * is added to that topic and the topic is returned.
     * If neither a topic with the specified subject identifier nor with an
     * item identifier equals to the subject identifier exists, a topic with
     * the subject identifier is created.
     * </p>
     * 
     * @param subjectIdentifier The subject identifier the topic should contain.
     * @return A {@link Topic} instance with the specified subject identifier.
     * @throws ModelConstraintException If the subject identifier <tt>subjectIdentifier</tt> is <tt>null</tt>.
     */
    public Topic createTopicBySubjectIdentifier(Locator subjectIdentifier) throws ModelConstraintException;

    /**
     * Returns a {@link Topic} instance with the specified subject locator.
     * <p>
     * This method returns either an existing {@link Topic} or creates a new
     * {@link Topic} instance with the specified subject locator.
     * </p>
     * 
     * @param subjectLocator The subject locator the topic should contain.
     * @return A {@link Topic} instance with the specified subject locator.
     * @throws ModelConstraintException If the subject locator <tt>subjectLocator</tt> is <tt>null</tt>.
     */
    public Topic createTopicBySubjectLocator(Locator subjectLocator) throws ModelConstraintException;

    /**
     * Returns a {@link Topic} instance with the specified item identifier.
     * <p>
     * This method returns either an existing {@link Topic} or creates a new
     * {@link Topic} instance with the specified item identifier.
     * </p>
     * <p>
     * If a topic with the specified item identifier exists in the topic map,
     * that topic is returned. If a topic with a subject identifier equals to
     * the specified item identifier exists, the specified item identifier
     * is added to that topic and the topic is returned.
     * If neither a topic with the specified item identifier nor with a
     * subject identifier equals to the subject identifier exists, a topic with
     * the item identifier is created.
     * </p>
     *
     * @param itemIdentifier The item identifier the topic should contain.
     * @return A {@link Topic} instance with the specified item identifier.
     * @throws ModelConstraintException If the item identifier <tt>itemIdentifier</tt> is <tt>null</tt>.
     * @throws IdentityConstraintException If an other {@link Construct} with the
     *              specified item identifier exists which is not a {@link Topic}. 
     */
    public Topic createTopicByItemIdentifier(Locator itemIdentifier) throws IdentityConstraintException, ModelConstraintException;

    /**
     * Returns a {@link Topic} instance with an automatically generated item 
     * identifier.
     * <p>
     * This method returns never an existing {@link Topic} but creates a 
     * new one with an automatically generated item identifier.
     * How that item identifier is generated depends on the implementation.
     * </p>
     *
     * @return The newly created {@link Topic} instance with an automatically
     *          generated item identifier.
     */
    public Topic createTopic();

    /**
     * Creates an {@link Association} in this topic map with the specified 
     * <tt>type</tt> and <tt>scope</tt>. 
     *
     * @param type The association type, MUST NOT be <tt>null</tt>.
     * @param scope An optional array of themes, must not be <tt>null</tt>. If
     *              the array's length is <tt>0</tt>, the association will be
     *              in the unconstrained scope.
     * @return The newly created {@link Association}.
     * @throws ModelConstraintException If either the <tt>type</tt> or 
     *              <tt>scope</tt> is <tt>null</tt>.
     */
    public Association createAssociation(Topic type, Topic... scope) throws ModelConstraintException;

    /**
     * Creates an {@link Association} in this topic map with the specified 
     * <tt>type</tt> and <tt>scope</tt>. 
     *
     * @param type The association type, MUST NOT be <tt>null</tt>.
     * @param scope A collection of themes or <tt>null</tt> if the association
     *              should be in the unconstrained scope.
     * @return The newly created {@link Association}.
     * @throws ModelConstraintException If either the <tt>type</tt> or 
     *              <tt>scope</tt> is <tt>null</tt>.
     */
    public Association createAssociation(Topic type, Collection<Topic> scope) throws ModelConstraintException;

    /**
     * Closes use of this topic map instance. 
     * <p>
     * This method should be invoked by the application once it is finished 
     * using this topic map instance.
     * </p>
     * <p>
     * Implementations may release any resources required for the 
     * <tt>TopicMap</tt> instance or any of the {@link Construct} instances 
     * contained by this instance.
     * </p>
     */
    public void close();

    /**
     * Merges the topic map <tt>other</tt> into this topic map.
     * <p>
     * All {@link Topic}s and {@link Association}s and all of their contents in
     * <tt>other</tt> will be added to this topic map. 
     * </p>
     * <p>
     * All information items in <tt>other</tt> will be merged into this 
     * topic map as defined by the
     * <a href="http://www.isotopicmaps.org/sam/sam-model/#sect-merging">Topic Maps - Data Model (TMDM) merging rules</a>. 
     * </p>
     * <p>
     * The merge process will not modify <tt>other</tt> in any way.
     * </p>
     * <p>
     * If <tt>this.equals(other)</tt> no changes are made to the topic map.
     * </p>
     * 
     * @param other The topic map to be merged with this topic map instance; 
     *        must not be <tt>null</tt>.
     * @throws ModelConstraintException If <tt>other</tt> is <tt>null</tt>.
     */
    public void mergeIn(TopicMap other) throws ModelConstraintException;

    /**
     * Returns the specified index.
     *
     * @param indexInterface The index to return.
     * @return An index.
     * @throws UnsupportedOperationException If the implementation does not
     *          support indices or if the specified index is unsupported.
     */
    public <I extends Index> I getIndex(Class<I> indexInterface);

}
