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
import org.tmapi.core.Role;
import org.tmapi.core.Topic;

/**
 * Index for type-instance relationships between {@link Topic}s 
 * and for {@link org.tmapi.core.Typed} Topic Maps constructs.
 * <p>
 * This index provides access to {@link Topic}s used in 
 * <a href="http://www.isotopicmaps.org/sam/sam-model/#sect-types">type-instance</a> 
 * relationships or as type of a {@link org.tmapi.core.Typed} construct.
 * Further, the retrieval of {@link Association}s, {@link Role}s, 
 * {@link Occurrence}s, and {@link Name}s by their <tt>type</tt> property is 
 * supported.
 * </p>
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 168 $ - $Date: 2010-03-05 20:37:40 +0000 (Fri, 05 Mar 2010) $
 */
public interface TypeInstanceIndex extends Index {

    /**
     * Returns the topics which are an instance of the specified <tt>type</tt> or
     * all topics which have are not an instance of another topic (iff {@code type} is {@code null}).
     * <p>
     * Note: Implementations may return only those topics whose <tt>types</tt>
     * property contains the type and may ignore 
     * <a href="http://www.isotopicmaps.org/sam/sam-model/#sect-types">type-instance</a> 
     * relationships which are modelled as association.
     * Further, <a href="http://www.isotopicmaps.org/sam/sam-model/#sect-subtypes">supertype-subtype</a>
     * relationships may also be ignored.
     * </p>
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param type The type of the {@link Topic}s to be returned or {@code null} to 
     *              return all topics which are not an instance of another topic.
     * @return An unmodifiable collection of {@link Topic}s.
     */
    public Collection<Topic> getTopics(Topic type);

    /**
     * Returns the topics in the topic map whose type property equals
     * one of those <tt>types</tt> at least.
     * <p>
     * Note: Implementations may return only those topics whose <tt>types</tt>
     * property contains the type and may ignore 
     * <a href="http://www.isotopicmaps.org/sam/sam-model/#sect-types">type-instance</a> 
     * relationships which are modelled as association.
     * Further, <a href="http://www.isotopicmaps.org/sam/sam-model/#sect-subtypes">supertype-subtype</a>
     * relationships may also be ignored.
     * </p>
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param types Types of the {@link Topic}s to be returned.
     * @param matchAll If <tt>true</tt>, a topic must be an instance of
     *              all <tt>types</tt>, if <tt>false</tt> the topic must be
     *              an instance of one type at least.
     * @return An unmodifiable collection of {@link Topic}s.
     */
    public Collection<Topic> getTopics(Topic[] types, boolean matchAll);

    /**
     * Returns the topics in topic map which are used as type in an 
     * "type-instance"-relationship.
     * <p>
     * Note: Implementations may return only those topics which are member
     * of the <tt>types</tt> property of other topics and may ignore
     * <a href="http://www.isotopicmaps.org/sam/sam-model/#sect-types">type-instance</a> 
     * relationships which are modelled as association.
     * Further, <a href="http://www.isotopicmaps.org/sam/sam-model/#sect-subtypes">supertype-subtype</a>
     * relationships may also be ignored.
     * </p>
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @return An unmodifiable collection of {@link Topic}s.
     */
    public Collection<Topic> getTopicTypes();

    /**
     * Returns the associations in the topic map whose type property equals 
     * <tt>type</tt>.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param type The type of the {@link Association}s to be returned.
     * @return An unmodifiable collection of {@link Association}s.
     */
    public Collection<Association> getAssociations(Topic type);

    /**
     * Returns the topics in the topic map used in the type property of 
     * {@link Association}s.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @return An unmodifiable collection of {@link org.tmapi.core.Topic}s.
     */
    public Collection<Topic> getAssociationTypes();

    /**
     * Returns the roles in the topic map whose type property equals 
     * <tt>type</tt>.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param type The type of the {@link Role}s to be returned.
     * @return An unmodifiable collection of {@link Role}s.
     */
    public Collection<Role> getRoles(Topic type);

    /**
     * Returns the topics in the topic map used in the type property of 
     * {@link Role}s.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @return An unmodifiable collection of {@link Topic}s.
     */
    public Collection<Topic> getRoleTypes();

    /**
     * Returns the occurrences in the topic map whose type property equals 
     * <tt>type</tt>.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param type The type of the {@link Occurrence}s to be returned.
     * @return An unmodifiable collection of {@link Occurrence}s.
     */
    public Collection<Occurrence> getOccurrences(Topic type);

    /**
     * Returns the topics in the topic map used in the type property of 
     * {@link Occurrence}s.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @return An unmodifiable collection of {@link Topic}s.
     */
    public Collection<Topic> getOccurrenceTypes();

    /**
     * Returns the topic names in the topic map whose type property equals 
     * <tt>type</tt>.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param type The type of the {@link Name}s to be returned.
     * @return An unmodifiable collection of {@link Name}s.
     */
    public Collection<Name> getNames(Topic type);

    /**
     * Returns the topics in the topic map used in the type property of 
     * {@link Name}s.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @return An unmodifiable collection of {@link Topic}s.
     */
    public Collection<Topic> getNameTypes();

}
