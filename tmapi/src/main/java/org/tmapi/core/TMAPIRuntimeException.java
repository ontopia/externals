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
 * Instances of this exception class should be thrown in cases where there 
 * is an error in the underlying topic map processing system or when integrity 
 * constraints are violated.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev:$ - $Date:$
 */
public class TMAPIRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -5272967154641022529L;

    /**
     * Constructs a new throwable with the specified detail message. 
     *
     * @param message The detail message. This message is saved for later 
     *                  retrieval by the {@link #getMessage()} method. 
     * @param cause The throwable which caused this exception to be thrown.
     *              This value is saved for later retrieval by the 
     *              {@link #getCause()} method.
     */
    public TMAPIRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new throwable with the specified detail message. 
     *
     * @param message The detail message. This message is saved for later 
     *                  retrieval by the {@link #getMessage()} method.
     */
    public TMAPIRuntimeException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception that wraps another exception.
     *
     * @param cause The throwable which caused this exception to be thrown.
     *              This value is saved for later retrieval by the 
     *              {@link #getCause()} method.
     */
    public TMAPIRuntimeException(Throwable cause) {
        super(cause);
    }

}
