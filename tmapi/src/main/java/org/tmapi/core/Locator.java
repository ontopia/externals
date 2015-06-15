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

/** 
 * Immutable representation of an IRI.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 122 $ - $Date: 2009-10-01 17:08:29 +0000 (Thu, 01 Oct 2009) $
 */
public interface Locator {

    /**
     * Returns the IRI
     *
     * @return A lexical representation of the IRI.
     */
    public String getReference();

    /**
     * Returns the external form of the IRI.
     * 
     * Any special character will be escaped using the escaping conventions of
     * <a href="http://www.ietf.org/rfc/rfc3987.txt">RFC 3987</a> 
     *
     * @return A string representation of this locator suitable for output 
     *          or passing to APIs which will parse the locator anew.  
     */
    public String toExternalForm();

    /**
     * Resolves the <tt>reference</tt> against this locator. The returned
     * <tt>Locator</tt> represents an absolute IRI.
     * 
     * @param reference The reference which should be resolved against this locator.
     * @return A locator representing an absolute IRI.
     * @throws IllegalArgumentException If <tt>reference</tt> is <tt>null</tt>.
     * @throws MalformedIRIException If the provided string cannot be resolved against this locator.
     */
    public Locator resolve(String reference) throws MalformedIRIException;

    /**
     * Returns <tt>true</tt> if the <tt>other</tt> object is equal to this one. 
     *
     * @param other The object to compare this object against.
     * @return <tt>(other instanceof Locator && this.getReference().equals(((Locator)other).getReference()))</tt>
     */
    public boolean equals(Object other);

    /**
     * Returns a hash code value.
     * 
     * The returned hash code is equal to the hash code of the {@link #getReference()}
     * property.
     *
     * @return <tt>this.getReference().hashCode()</tt>
     */
    public int hashCode();

}
