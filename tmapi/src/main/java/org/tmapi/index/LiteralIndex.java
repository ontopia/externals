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

import org.tmapi.core.Locator;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Variant;

/**
 * Index for literal values stored in a topic map.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 65 $ - $Date: 2008-08-19 11:14:19 +0000 (Tue, 19 Aug 2008) $
 */
public interface LiteralIndex extends Index {

    /**
     * Returns the {@link Occurrence}s in the topic map whose value property 
     * matches <tt>value</tt> and whose datatype property is 
     * <a href="http://www.w3.org/TR/xmlschema-2/#string">xsd:string</a>.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param value The value of the {@link Occurrence}s to be returned.
     * @return An unmodifiable collection of {@link Occurrence}s.
     * @throws IllegalArgumentException If the value is <tt>null</tt>.
     */
    public Collection<Occurrence> getOccurrences(String value);

    /**
     * Returns the {@link Occurrence}s in the topic map whose value property 
     * matches the IRI represented by <tt>value</tt>.
     * <p>
     * Those {@link Occurrence}s which have a datatype equal to 
     * <a href="http://www.w3.org/TR/xmlschema-2/#anyURI">xsd:anyURI</a>
     * and their value property is equal to {@link Locator#getReference()}
     * are returned.
     * </p>
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param value The value of the {@link Occurrence}s to be returned.
     * @return An unmodifiable collection of {@link Occurrence}s.
     * @throws IllegalArgumentException If the value is <tt>null</tt>.
     */
    public Collection<Occurrence> getOccurrences(Locator value);

    /**
     * Returns the {@link Occurrence}s in the topic map whose value property 
     * matches <tt>value</tt> and whose datatye is <tt>datatype</tt>.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param value The value of the {@link Occurrence}s to be returned.
     * @param datatype The datatype of the {@link Occurrence}s to be returned.
     * @return An unmodifiable collection of {@link Occurrence}s.
     * @throws IllegalArgumentException If the value or datatype is <tt>null</tt>.
     */
    public Collection<Occurrence> getOccurrences(String value, Locator datatype);

    /**
     * Returns the {@link Variant}s in the topic map whose value property
     * matches <tt>value</tt> and whose datatype property is 
     * <a href="http://www.w3.org/TR/xmlschema-2/#string">xsd:string</a>.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     *  
     * @param value The value of the {@link Variant}s to be returned.
     * @return An unmodifiable collection of {@link Variant}s.
     * @throws IllegalArgumentException If the value is <tt>null</tt>.
     */
    public Collection<Variant> getVariants(String value);

    /**
     * Returns the {@link Variant}s in the topic map whose value property 
     * matches the IRI represented by <tt>value</tt>.
     * <p>
     * Those {@link Variant}s which have a datatype equal to 
     * <a href="http://www.w3.org/TR/xmlschema-2/#anyURI">xsd:anyURI</a>
     * and their value property is equal to {@link Locator#getReference()}
     * are returned.
     * </p>
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param value The value of the {@link Variant}s to be returned.
     * @return An unmodifiable collection of {@link Variant}s.
     * @throws IllegalArgumentException If the value is <tt>null</tt>.
     */
    public Collection<Variant> getVariants(Locator value);

    /**
     * Returns the {@link Variant}s in the topic map whose value property 
     * matches <tt>value</tt> and whose datatye is <tt>datatype</tt>.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param value The value of the {@link Variant}s to be returned.
     * @param datatype The datatype of the {@link Variant}s to be returned.
     * @return An unmodifiable collection of {@link Variant}s.
     * @throws IllegalArgumentException If the value or datatype is <tt>null</tt>.
     */
    public Collection<Variant> getVariants(String value, Locator datatype);

    /**
     * Retrieves the topic names in the topic map which have a value equal to 
     * <tt>value</tt>.
     * 
     * The return value may be empty but must never be <tt>null</tt>.
     * 
     * @param value The value of the {@link Name}s to be returned.
     * @return An unmodifiable collection of {@link Name}s.
     * @throws IllegalArgumentException If the value is <tt>null</tt>.
     */
    public Collection<Name> getNames(String value);

}
