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

import java.math.BigInteger;
import java.math.BigDecimal;

/**
 * Common base interface for {@link Occurrence}s and {@link Variant}s.
 * <p>
 * Some convenience methods for a subset of 
 * <a href="http://www.w3.org/TR/xmlschema-2/">XML Schema Part 2: Datatypes</a>
 * are supported.
 * </p>
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 124 $ - $Date: 2009-10-01 17:21:27 +0000 (Thu, 01 Oct 2009) $
 */
public interface DatatypeAware extends Reifiable, Scoped {

    /**
     * Returns the {@link Locator} identifying the datatype of
     * the value.
     *
     * @return The datatype of this construct (never <tt>null</tt>).
     */
    public Locator getDatatype();

    /**
     * Returns the lexical representation of the value.
     * <p>
     * For the datatype <a href="http://www.w3.org/TR/xmlschema-2/#string">xsd:string</a>
     * the string itself is returned. For the datatype 
     * <a href="http://www.w3.org/TR/xmlschema-2/#anyURI">xsd:anyURI</a>
     * the {@link Locator#getReference()} is returned.
     * </p>
     * 
     * @return The lexical representation of the value (never <tt>null</tt>).
     */
    public String getValue();

    /**
     * Sets the string value.
     * 
     * This method sets the datatype implicitly to
     * <a href="http://www.w3.org/TR/xmlschema-2/#string">xsd:string</a>.
     *
     * @param value The string value.
     * @throws ModelConstraintException In case the <tt>value</tt> is <tt>null</tt>.
     */
    public void setValue(String value) throws ModelConstraintException;

    /**
     * Sets the IRI value.
     * <p>
     * This method sets the datatype implicitly to
     * <a href="http://www.w3.org/TR/xmlschema-2/#anyURI">xsd:anyURI</a>.
     * </p>
     * 
     * @param value The IRI value.
     * @throws ModelConstraintException In case the <tt>value</tt> is <tt>null</tt>.
     */
    public void setValue(Locator value) throws ModelConstraintException;

    /**
     * Sets the string value and the datatype.
     *
     * @param value The string value.
     * @param datatype The value's datatype.
     * @throws ModelConstraintException In case the <tt>value</tt> or <tt>datatype</tt> 
     *              is <tt>null</tt>.
     */
    public void setValue(String value, Locator datatype) throws ModelConstraintException;

    /**
     * Sets the decimal value.
     * <p>
     * This method sets the datatype implicitly to
     * <a href="http://www.w3.org/TR/xmlschema-2/#decimal">xsd:decimal</a>.
     * </p>
     * 
     * @param value The decimal value.
     * @throws ModelConstraintException In case the <tt>value</tt> is <tt>null</tt>.
     */
    public void setValue(BigDecimal value) throws ModelConstraintException;

    /**
     * Sets the integer value.
     * <p>
     * This method sets the datatype implicitly to
     * <a href="http://www.w3.org/TR/xmlschema-2/#integer">xsd:integer</a>.
     * </p>
     * 
     * @param value The integer value.
     * @throws ModelConstraintException In case the <tt>value</tt> is <tt>null</tt>.
     */
    public void setValue(BigInteger value) throws ModelConstraintException;

    /**
     * Sets the long value.
     * <p>
     * This method sets the datatype implicitly to
     * <a href="http://www.w3.org/TR/xmlschema-2/#long">xsd:long</a>.
     * </p>
     * 
     * @param value The integer value.
     */
    public void setValue(long value);

    /**
     * Sets the float value.
     * <p>
     * This method sets the datatype implicitly to
     * <a href="http://www.w3.org/TR/xmlschema-2/#float">xsd:float</a>.
     * </p>
     * 
     * @param value The float value.
     */
    public void setValue(float value);

    /**
     * Sets the int value.
     * <p>
     * This method sets the datatype implicitly to
     * <a href="http://www.w3.org/TR/xmlschema-2/#int">xsd:int</a>.
     * </p>
     * 
     * @param value The int value.
     */
    public void setValue(int value);

    /**
     * Returns the <tt>int</tt> representation of the value.
     *
     * @return An <tt>int</tt> representation of the value.
     * @throws NumberFormatException If the value cannot be represented as 
     *          a <tt>int</tt>.
     */
    public int intValue();

    /**
     * Returns the {@link BigInteger} representation of the value.
     *
     * @return A {@link BigInteger} representation of the value.
     * @throws NumberFormatException If the value cannot be represented as 
     *          a {@link BigInteger} instance.
     */
    public BigInteger integerValue();

    /**
     * Returns the <tt>float</tt> representation of the value.
     *
     * @return A <tt>float</tt> representation of the value.
     * @throws NumberFormatException If the value cannot be represented as 
     *          a <tt>float</tt>.
     */
    public float floatValue();

    /**
     * Returns the {@link BigDecimal} representation of the value.
     *
     * @return A {@link BigDecimal} representation of the value.
     * @throws NumberFormatException If the value cannot be represented as 
     *          a {@link BigDecimal} instance.
     */
    public BigDecimal decimalValue();

    /**
     * Returns the <tt>long</tt> representation of the value.
     *
     * @return A <tt>long</tt> representation of the value.
     * @throws NumberFormatException If the value cannot be represented as 
     *          a <tt>long</tt>.
     */
    public long longValue();

    /**
     * Returns the {@link Locator} representation of the value.
     *
     * @return A {@link Locator} representation of the value.
     * @throws IllegalArgumentException If the value cannot be represented as 
     *          a {@link Locator} instance.
     */
    public Locator locatorValue();

}
