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
 * Thrown to indicate that a malformed IRI has occurred.
 * 
 * @author TMAPI <a href="http://www.tmapi.org">tmapi.org</a>
 * @version $Rev: 97 $ - $Date: 2009-02-06 16:25:36 +0000 (Fri, 06 Feb 2009) $
 */
public class MalformedIRIException extends TMAPIRuntimeException {

    private static final long serialVersionUID = 2126925008251115110L;

    /**
     * Constructs a <tt>MalformedIRIException</tt> with the specified detail 
     * message. 
     *
     * @param message The detail message.
     */
    public MalformedIRIException(String message) {
        super(message);
    }

}
