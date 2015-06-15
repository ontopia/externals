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
 * <a href="http://www.isotopicmaps.org/sam/sam-model/#sect-topic-name">topic name item</a>.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 123 $ - $Date: 2009-10-01 17:17:03 +0000 (Thu, 01 Oct 2009) $
 */
public interface Name extends Typed, Scoped, Reifiable {

    /** 
     * Returns the {@link Topic} to which this name belongs.
     * 
     * @see org.tmapi.core.Construct#getParent()
     * 
     * @return The topic to which this name belongs.
     */
    public Topic getParent();

    /**
     * Returns the value of this name.
     *
     * @return A string representing the value of this name.
     */
    public String getValue();

    /**
     * Sets the value of this name.
     * 
     * The previous value is overridden.
     *
     * @param value The name string to be assigned to the name.
     * @throws ModelConstraintException If the the <tt>value</tt> is <tt>null</tt>.
     */
    public void setValue(String value) throws ModelConstraintException;

    /**
     * Returns the {@link Variant}s defined for this name.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     *
     * @return An unmodifable set of {@link Variant}s.
     */
    public Set<Variant> getVariants();

    /**
     * Creates a {@link Variant} of this topic name with the specified string
     * <tt>value</tt> and <tt>scope</tt>. 
     * <p>
     * The newly created {@link Variant} will have the datatype 
     * <a href="http://www.w3.org/TR/xmlschema-2/#string">xsd:string</a>.
     * </p>
     * <p>
     * The newly created {@link Variant} will contain all themes from the parent name 
     * and the themes specified in <tt>scope</tt>.
     * </p>
     * 
     * @param value The string value.
     * @param scope An array (length >= 1) of themes.
     * @return The newly created {@link Variant}.
     * @throws ModelConstraintException If the <tt>value</tt> is <tt>null</tt>,
     *          or the scope of the variant would not be a true superset of the 
     *          name's scope.
     */
    public Variant createVariant(String value, Topic... scope) throws ModelConstraintException;

    /**
     * Creates a {@link Variant} of this topic name with the specified string
     * <tt>value</tt> and <tt>scope</tt>. 
     * <p>
     * The newly created {@link Variant} will have the datatype 
     * <a href="http://www.w3.org/TR/xmlschema-2/#string">xsd:string</a>.
     * </p>
     * <p>
     * The newly created {@link Variant} will contain all themes from the parent name 
     * and the themes specified in <tt>scope</tt>.
     * </p>
     * 
     * @param value The string value.
     * @param scope A collection (size >= 1) of themes.
     * @return The newly created {@link Variant}.
     * @throws ModelConstraintException If the <tt>value</tt> is <tt>null</tt>,
     *          or the scope of the variant would not be a true superset of the 
     *          name's scope.
     */
    public Variant createVariant(String value, Collection<Topic> scope) throws ModelConstraintException;

    /**
     * Creates a {@link Variant} of this topic name with the specified IRI
     * <tt>value</tt> and <tt>scope</tt>. 
     * <p>
     * The newly created {@link Variant} will have the datatype 
     * <a href="http://www.w3.org/TR/xmlschema-2/#anyURI">xsd:anyURI</a>.
     * </p>
     * <p>
     * The newly created {@link Variant} will contain all themes from the parent name 
     * and the themes specified in <tt>scope</tt>.
     * </p>
     * 
     * @param value A locator which represents an IRI.
     * @param scope An array (length >= 1) of themes.
     * @return The newly created {@link Variant}.
     * @throws ModelConstraintException If the <tt>value</tt> is <tt>null</tt>,
     *          or the scope of the variant would not be a true superset of the 
     *          name's scope.
     */
    public Variant createVariant(Locator value, Topic... scope) throws ModelConstraintException;

    /**
     * Creates a {@link Variant} of this topic name with the specified IRI
     * <tt>value</tt> and <tt>scope</tt>. 
     * <p>
     * The newly created {@link Variant} will have the datatype 
     * <a href="http://www.w3.org/TR/xmlschema-2/#anyURI">xsd:anyURI</a>.
     * </p>
     * <p>
     * The newly created {@link Variant} will contain all themes from the parent name 
     * and the themes specified in <tt>scope</tt>.
     * </p>
     * 
     * @param value A locator which represents an IRI.
     * @param scope A collection (size >= 1) of themes.
     * @return The newly created {@link Variant}.
     * @throws ModelConstraintException If the <tt>value</tt> is <tt>null</tt>,
     *          or the scope of the variant would not be a true superset of the 
     *          name's scope.
     */
    public Variant createVariant(Locator value, Collection<Topic> scope) throws ModelConstraintException;

    /**
     * Creates a {@link Variant} of this topic name with the specified 
     * <tt>value</tt>, <tt>datatype</tt>, and <tt>scope</tt>. 
     * <p>
     * The newly created {@link Variant} will have the datatype specified by
     * <tt>datatype</tt>. 
     * </p>
     * <p>
     * The newly created {@link Variant} will contain all themes from the parent name 
     * and the themes specified in <tt>scope</tt>.
     * </p>
     * 
     * @param value A lexical string representation of the value.
     * @param datatype A locator indicating the datatype of the <tt>value</tt>.
     * @param scope An array (length >= 1) of themes.
     * @return The newly created {@link Variant}.
     * @throws ModelConstraintException If the <tt>value</tt> or <tt>datatype</tt>
     *          is <tt>null</tt>, or the scope of the variant would not be a 
     *          true superset of the name's scope.
     */
    public Variant createVariant(String value, Locator datatype, Topic... scope) throws ModelConstraintException;

    /**
     * Creates a {@link Variant} of this topic name with the specified 
     * <tt>value</tt>, <tt>datatype</tt>, and <tt>scope</tt>. 
     * <p>
     * The newly created {@link Variant} will have the datatype specified by
     * <tt>datatype</tt>. 
     * </p>
     * <p>
     * The newly created {@link Variant} will contain all themes from the parent name 
     * and the themes specified in <tt>scope</tt>.
     * </p>
     * 
     * @param value A lexical string representation of the value.
     * @param datatype A locator indicating the datatype of the <tt>value</tt>.
     * @param scope A collection (size >= 1) of themes.
     * @return The newly created {@link Variant}.
     * @throws ModelConstraintException If the <tt>value</tt> or <tt>datatype</tt>
     *          is <tt>null</tt>, or the scope of the variant would not be a 
     *          true superset of the name's scope.
     */
    public Variant createVariant(String value, Locator datatype,
            Collection<Topic> scope) throws ModelConstraintException;

}
