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

/**
 * Represents a 
 * <a href="http://www.isotopicmaps.org/sam/sam-model/#d0e739">topic item</a>.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 123 $ - $Date: 2009-10-01 17:17:03 +0000 (Thu, 01 Oct 2009) $
 */
public interface Topic extends Construct {

    /** 
     * Returns the {@link TopicMap} to which this topic belongs.
     * 
     * @see org.tmapi.core.Construct#getParent()
     * 
     * @return The topic map to which this topic belongs.
     */
    public TopicMap getParent();

    /**
     * Adds an item identifier to this topic.
     * <p>
     * If adding the specified item identifier would make this topic
     * represent the same subject as another topic and the feature 
     * "automerge" (http://tmapi.org/features/automerge) is disabled,  
     * an {@link IdentityConstraintException} is thrown.
     * </p>
     * 
     * @see org.tmapi.core.Construct#addItemIdentifier(org.tmapi.core.Locator)
     */
    public void addItemIdentifier(Locator itemIdentifier);

    /**
     * Returns the subject identifiers assigned to this topic.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     *
     * @return An unmodifiable set of {@link Locator}s representing the 
     *          subject identifiers.
     */
    public Set<Locator> getSubjectIdentifiers();

    /**
     * Adds a subject identifier to this topic.
     * <p>
     * If adding the specified subject identifier would make this topic
     * represent the same subject as another topic and the feature 
     * "automerge" (http://tmapi.org/features/automerge/) is disabled,
     * an {@link IdentityConstraintException} is thrown.
     * </p>
     * 
     * @param subjectIdentifier The subject identifier to be added; must not be <tt>null</tt>.
     * @throws IdentityConstraintException If the feature "automerge" is
     *          disabled and adding the subject identifier would make this 
     *          topic represent the same subject as another topic.
     * @throws ModelConstraintException If the subject identifier is <tt>null</tt>.
     */
    public void addSubjectIdentifier(Locator subjectIdentifier) throws IdentityConstraintException, ModelConstraintException;

    /**
     * Removes a subject identifier from this topic.
     *
     * @param subjectIdentifier The subject identifier to be removed from this topic, 
     *         if present (<tt>null</tt> is ignored).
     */
    public void removeSubjectIdentifier(Locator subjectIdentifier);

    /**
     * Returns the subject locators assigned to this topic.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     *
     * @return An unmodifiable set of {@link Locator}s representing the 
     *          subject locators.
     */
    public Set<Locator> getSubjectLocators();

    /**
     * Adds a subject locator to this topic.
     * <p>
     * If adding the specified subject locator would make this topic
     * represent the same subject as another topic and the feature 
     * "automerge" (http://tmapi.org/features/automerge/) is disabled,
     * an {@link IdentityConstraintException} is thrown.
     * </p>
     * 
     * @param subjectLocator The subject locator to be added; must not be <tt>null</tt>.
     * @throws IdentityConstraintException If the feature "automerge" is
     *          disabled and adding the subject locator would make this 
     *          topic represent the same subject as another topic.
     * @throws ModelConstraintException If the subject locator is <tt>null</tt>.
     */
    public void addSubjectLocator(Locator subjectLocator) throws IdentityConstraintException, ModelConstraintException;

    /**
     * Removes a subject locator from this topic.
     *
     * @param subjectLocator The subject locator to be removed from this topic, 
     *         if present (<tt>null</tt> is ignored).
     */
    public void removeSubjectLocator(Locator subjectLocator);

    /**
     * Returns the names of this topic.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @return An unmodifable set of {@link Name}s belonging to this topic.
     */
    public Set<Name> getNames();

    /**
     * Returns the {@link Name}s of this topic where the name type is 
     * <tt>type</tt>.
     * <p>
     * This method returns the same result as the following code:
     * <pre>
     *      Set&lt;Name&gt; names = new HashSet&lt;Name&gt;();
     *      for (Name name: topic.getNames()) {
     *          if (name.getType().equals(type)) {
     *              names.add(name);
     *          }
     *      }
     * </pre>
     * </p>
     * The return value may be empty but must never be <tt>null</tt>. 
     * 
     * @param type The type of the {@link Name}s to be returned; 
     *              must not be <tt>null</tt>.
     * @return An unmodifiable set of {@link Name}s with the specified 
     *          <tt>type</tt>.
     * @throws IllegalArgumentException If the <tt>type</tt> is <tt>null</tt>.
     */
    public Set<Name> getNames(Topic type);

    /**
     * Creates a {@link Name} for this topic with the specified <tt>type</tt>,
     * <tt>value</tt>, and <tt>scope</tt>. 
     *
     * @param type The name type; MUST NOT be <tt>null</tt>.
     * @param value The string value of the name; MUST NOT be <tt>null</tt>.
     * @param scope An optional array of themes, must not be <tt>null</tt>. If
     *              the array's length is <tt>0</tt>, the name will be
     *              in the unconstrained scope.
     * @return The newly created {@link Name}.
     * @throws ModelConstraintException If either the <tt>type</tt>, the 
     *              <tt>value</tt>, or <tt>scope</tt> is <tt>null</tt>.
     */
    public Name createName(Topic type, String value, Topic... scope) throws ModelConstraintException;

    /**
     * Creates a {@link Name} for this topic with the specified <tt>type</tt>,
     * <tt>value</tt>, and <tt>scope</tt>. 
     *
     * @param type The name type; MUST NOT be <tt>null</tt>.
     * @param value The string value of the name; MUST NOT be <tt>null</tt>.
     * @param scope A collection of themes. The collection may be empty if the name 
     *              should be in the unconstrained scope.
     * @return The newly created {@link Name}.
     * @throws ModelConstraintException If either the <tt>type</tt>, the 
     *              <tt>value</tt>, or <tt>scope</tt> is <tt>null</tt>.
     */
    public Name createName(Topic type, String value, Collection<Topic> scope) throws ModelConstraintException;

    /**
     * Creates a {@link Name} for this topic with the specified <tt>value</tt>, 
     * and <tt>scope</tt>.
     * <p>
     * The created {@link Name} will have the default name type
     * (a {@link Topic} with the subject identifier 
     * <a href="http://psi.topicmaps.org/iso13250/model/topic-name">http://psi.topicmaps.org/iso13250/model/topic-name</a>). 
     * </p>
     * 
     * @param value The string value of the name; MUST NOT be <tt>null</tt>.
     * @param scope An optional array of themes, must not be <tt>null</tt>. If
     *              the array's length is <tt>0</tt>, the name will be
     *              in the unconstrained scope.
     * @return The newly created {@link Name}.
     * @throws ModelConstraintException If either the <tt>value</tt>, or 
     *              <tt>scope</tt> is <tt>null</tt>.
     */
    public Name createName(String value, Topic... scope) throws ModelConstraintException;

    /**
     * Creates a {@link Name} for this topic with the specified <tt>value</tt>, 
     * and <tt>scope</tt>.
     * <p>
     * The created {@link Name} will have the default name type
     * (a {@link Topic} with the subject identifier 
     * <a href="http://psi.topicmaps.org/iso13250/model/topic-name">http://psi.topicmaps.org/iso13250/model/topic-name</a>). 
     * </p>
     * 
     * @param value The string value of the name; MUST NOT be <tt>null</tt>.
     * @param scope A collection of themes. The collection may be empty if the name 
     *              should be in the unconstrained scope.
     * @return The newly created {@link Name}.
     * @throws ModelConstraintException If either the <tt>value</tt>, or 
     *              <tt>scope</tt> is <tt>null</tt>.
     */
    public Name createName(String value, Collection<Topic> scope) throws ModelConstraintException;

    /**
     * Returns the {@link Occurrence}s of this topic.
     *
     * The return value may be empty but must never be <tt>null</tt>.
     *
     * @return An unmodifable set of {@link Occurrence}s belonging to this topic.
     */
    public Set<Occurrence> getOccurrences();

    /**
     * Returns the {@link Occurrence}s of this topic where the occurrence type 
     * is <tt>type</tt>.
     * <p>
     * This method returns the same result as the following code:
     * <pre>
     *      Set&lt;Occurrence&gt; occs = new HashSet&lt;Occurrence&gt;();
     *      for (Occurrence occ: topic.getOccurrences()) {
     *          if (occ.getType().equals(type)) {
     *              occs.add(occ);
     *          }
     *      }
     * </pre>
     * </p>
     * The return value may be empty but must never be <tt>null</tt>.
     *
     * @param type The type of the {@link Occurrence}s to be returned;
     *              must not be <tt>null</tt>.
     * @return An unmodifiable set of {@link Occurrence}s with the 
     *          specified <tt>type</tt>.
     * @throws IllegalArgumentException If the <tt>type</tt> is <tt>null</tt>.
     */
    public Set<Occurrence> getOccurrences(Topic type);

    /**
     * Creates an {@link Occurrence} for this topic with the specified 
     * <tt>type</tt>, string <tt>value</tt>, and <tt>scope</tt>.
     * <p>
     * The newly created {@link Occurrence} will have the datatype 
     * <a href="http://www.w3.org/TR/xmlschema-2/#string">xsd:string</a>.
     * </p>
     * 
     * @param type The occurrence type; MUST NOT be <tt>null</tt>.
     * @param value The string value of the occurrence.
     * @param scope An optional array of themes, must not be <tt>null</tt>. If
     *              the array's length is <tt>0</tt>, the occurrence will be
     *              in the unconstrained scope.
     * @return The newly created {@link Occurrence}.
     * @throws ModelConstraintException If either the <tt>type</tt>, the 
     *              <tt>value</tt>, or <tt>scope</tt> is <tt>null</tt>.
     */
    public Occurrence createOccurrence(Topic type, String value, Topic... scope) throws ModelConstraintException;

    /**
     * Creates an {@link Occurrence} for this topic with the specified 
     * <tt>type</tt>, string <tt>value</tt>, and <tt>scope</tt>.
     * <p>
     * The newly created {@link Occurrence} will have the datatype 
     * <a href="http://www.w3.org/TR/xmlschema-2/#string">xsd:string</a>.
     * </p>
     * 
     * @param type The occurrence type; MUST NOT be <tt>null</tt>.
     * @param value The string value of the occurrence.
     * @param scope A collection of themes. The collection may be empty if the occurrence 
     *              should be in the unconstrained scope.
     * @return The newly created {@link Occurrence}.
     * @throws ModelConstraintException If either the <tt>type</tt>, the 
     *              <tt>value</tt>, or <tt>scope</tt> is <tt>null</tt>.
     */
    public Occurrence createOccurrence(Topic type, String value,
            Collection<Topic> scope) throws ModelConstraintException;

    /**
     * Creates an {@link Occurrence} for this topic with the specified 
     * <tt>type</tt>, IRI <tt>value</tt>, and <tt>scope</tt>.
     * <p>
     * The newly created {@link Occurrence} will have the datatype 
     * <a href="http://www.w3.org/TR/xmlschema-2/#anyURI">xsd:anyURI</a>.
     * </p>
     * 
     * @param type The occurrence type; MUST NOT be <tt>null</tt>.
     * @param value A locator which represents an IRI.
     * @param scope An optional array of themes, must not be <tt>null</tt>. If
     *              the array's length is <tt>0</tt>, the occurrence will be
     *              in the unconstrained scope.
     * @return The newly created {@link Occurrence}.
     * @throws ModelConstraintException If either the <tt>type</tt>, the 
     *              <tt>value</tt>, or <tt>scope</tt> is <tt>null</tt>.
     */
    public Occurrence createOccurrence(Topic type, Locator value,
            Topic... scope) throws ModelConstraintException;

    /**
     * Creates an {@link Occurrence} for this topic with the specified 
     * <tt>type</tt>, IRI <tt>value</tt>, and <tt>scope</tt>.
     * <p>
     * The newly created {@link Occurrence} will have the datatype 
     * <a href="http://www.w3.org/TR/xmlschema-2/#anyURI">xsd:anyURI</a>.
     * </p>
     * 
     * @param type The occurrence type; MUST NOT be <tt>null</tt>.
     * @param value A locator which represents an IRI.
     * @param scope A collection of themes. The collection may be empty if the occurrence 
     *              should be in the unconstrained scope.
     * @return The newly created {@link Occurrence}.
     * @throws ModelConstraintException If either the <tt>type</tt>, the 
     *              <tt>value</tt>, or <tt>scope</tt> is <tt>null</tt>.
     */
    public Occurrence createOccurrence(Topic type, Locator value,
            Collection<Topic> scope) throws ModelConstraintException;

    /**
     * Creates an {@link Occurrence} for this topic with the specified 
     * <tt>type</tt>, <tt>value</tt>, <tt>datatype</tt>, and <tt>scope</tt>.
     * <p>
     * The newly created {@link Occurrence} will have the datatype specified 
     * by <tt>datatype</tt>. 
     * </p>
     * 
     * @param type The occurrence type; MUST NOT be <tt>null</tt>.
     * @param value A lexical string representation of the value.
     * @param datatype A locator indicating the datatype of the <tt>value</tt>.
     * @param scope An optional array of themes, must not be <tt>null</tt>. If
     *              the array's length is <tt>0</tt>, the occurrence will be
     *              in the unconstrained scope.
     * @return The newly created {@link Occurrence}.
     * @throws ModelConstraintException If either the <tt>type</tt>, the 
     *              <tt>value</tt>, the <tt>datatype</tt> or <tt>scope</tt> is 
     *              <tt>null</tt>.
     */
    public Occurrence createOccurrence(Topic type, String value,
            Locator datatype, Topic... scope) throws ModelConstraintException;

    /**
     * Creates an {@link Occurrence} for this topic with the specified 
     * <tt>type</tt>, <tt>value</tt>, <tt>datatype</tt>, and <tt>scope</tt>.
     * <p>
     * The newly created {@link Occurrence} will have the datatype specified 
     * by <tt>datatype</tt>. 
     * </p>
     * 
     * @param type The occurrence type; MUST NOT be <tt>null</tt>.
     * @param value A lexical string representation of the value.
     * @param datatype A locator indicating the datatype of the <tt>value</tt>.
     * @param scope A collection of themes. The collection may be empty if the occurrence 
     *              should be in the unconstrained scope.
     * @return The newly created {@link Occurrence}.
     * @throws ModelConstraintException If either the <tt>type</tt>, the 
     *              <tt>value</tt>, the <tt>datatype</tt> or <tt>scope</tt> is 
     *              <tt>null</tt>.
     */
    public Occurrence createOccurrence(Topic type, String value,
            Locator datatype, Collection<Topic> scope) throws ModelConstraintException;

    /**
     * Returns the roles played by this topic.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     *
     * @return An unmodifiable set of {@link Role}s played by this topic.
     */
    public Set<Role> getRolesPlayed();

    /**
     * Returns the roles played by this topic where the role type is
     * <tt>type</tt>.
     * <p>
     * This method returns the same result as the following code:
     * <pre>
     *      Set&lt;Role&gt; roles = new HashSet&lt;Role&gt;();
     *      for (Role role: topic.getRolesPlayed()) {
     *          if (role.getType().equals(type)) {
     *              roles.add(role);
     *          }
     *      }
     * </pre>
     * </p>
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     *
     * @param type The type of the {@link Role}s to be returned; must not 
     *              be <tt>null</tt>.
     * @return An unmodifiable set of {@link Role}s with the specified <tt>type</tt>.
     * @throws IllegalArgumentException If the <tt>type</tt> is <tt>null</tt>.
     */
    public Set<Role> getRolesPlayed(Topic type);

    /**
     * Returns the {@link Role}s played by this topic where the role type is
     * <tt>type</tt> and the {@link Association} type is <tt>assocType</tt>.
     * <p>
     * This method returns the same result as the following code:
     * <pre>
     *      Set&lt;Role&gt; roles = new HashSet&lt;Role&gt;();
     *      for (Role role: topic.getRolesPlayed(type)) {
     *          if (role.getParent().getType().equals(assocType)) {
     *              roles.add(role);
     *          }
     *      }
     * </pre>
     * </p>
     * The return value may be empty but must never be <tt>null</tt>.
     *
     * @param type The type of the {@link Role}s to be returned;
     *              must not be <tt>null</tt>.
     * @param assocType The type of the {@link Association} from which the
     *                  returned roles must be part of; must not be <tt>null</tt>.
     * @return An unmodifiable set of {@link Role}s with the specified <tt>type</tt>
     *          which are part of {@link Association}s with the specified 
     *          <tt>assocType</tt>.
     * @throws IllegalArgumentException If the <tt>type</tt> or <tt>assocType</tt>
     *              is <tt>null</tt>.
     */
    public Set<Role> getRolesPlayed(Topic type, Topic assocType);

    /**
     * Returns the types of which this topic is an instance of.
     * <p>
     * This method may return only those types which where added by
     * {@link #addType(Topic)} and may ignore 
     * <a href="http://www.isotopicmaps.org/sam/sam-model/#sect-types">type-instance</a> 
     * relationships which are modelled as association.
     * </p>
     * The return value may be empty but must never be <tt>null</tt>.
     *
     * @return An unmodifiable set of {@link Topic}s.
     */
    public Set<Topic> getTypes();

    /**
     * Adds a type to this topic.
     * <p>
     * Implementations may or may not create an association for types added
     * by this method. In any case, every type which was added by this method 
     * must be returned by the {@link #getTypes()} method.
     * </p>
     * 
     * @param type The type of which this topic should become an instance of;
     *              must not be <tt>null</tt>.
     * @throws ModelConstraintException If the type is <tt>null</tt>.
     */
    public void addType(Topic type) throws ModelConstraintException;

    /**
     * Removes a type from this topic.
     *
     * @param type The type to be removed from this topic, if present 
     *         (<tt>null</tt> is ignored).
     */
    public void removeType(Topic type);

    /**
     * Returns the {@link Construct} which is reified by this topic.
     *
     * @return The {@link Reifiable} that is reified by this topic or 
     *          <tt>null</tt> if this topic does not reify a statement.
     */
    public Reifiable getReified();

    /**
     * Merges another topic into this topic.
     * <p>
     * Merging a topic into this topic causes this topic to gain all 
     * of the characteristics of the other topic and to replace the other 
     * topic wherever it is used as type, theme, or reifier. 
     * After this method completes, <tt>other</tt> will have been removed from 
     * the {@link TopicMap}.
     * </p>
     * <p>
     * If <tt>this.equals(other)</tt> no changes are made to the topic.
     * </p>
     * <p>
     * NOTE: The other topic MUST belong to the same {@link TopicMap} instance 
     * as this topic! 
     * </p>
     * 
     * @param other The topic to be merged into this topic; must not be <tt>null</tt>.
     * @throws ModelConstraintException If <tt>other</tt> is <tt>null</tt>.
     */
    public void mergeIn(Topic other) throws ModelConstraintException;

    /**
     * Removes this topic from the containing {@link TopicMap} instance.
     * <p>
     * This method throws a {@link TopicInUseException} if the topic plays
     * a {@link Role}, is used as type of a {@link Typed} construct, or if
     * it is used as theme for a {@link Scoped} construct, or if it reifies a 
     * {@link Reifiable}.
     * </p>
     * 
     * @see org.tmapi.core.Construct#remove()
     * 
     * @throws TopicInUseException If the topic plays a {@link Role} or it
     *              is used as type, in a scope, or as reifier.
     */
    public void remove() throws TopicInUseException;

}
