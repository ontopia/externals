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
 * Exception thrown when a {@link TopicMapSystemFactory} instance
 * cannot be instantiated through the method 
 * {@link TopicMapSystemFactory#newInstance()}.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 62 $ - $Date: 2008-08-08 12:00:50 +0000 (Fri, 08 Aug 2008) $
 */
public class FactoryConfigurationException extends TMAPIException {

    private static final long serialVersionUID = -6197383571197389854L;

    /**
     * Constructs a new throwable with the specified detail message. 
     *
     * @param message The detail message. This message is saved for later 
     *                  retrieval by the {@link #getMessage()} method.
     */
    public FactoryConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception that wraps another exception.
     *
     * @param cause The throwable which caused this exception to be thrown.
     *              This value is saved for later retrieval by the 
     *              {@link #getCause()} method.
     */
    public FactoryConfigurationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new throwable with the specified detail message. 
     *
     * @param message The detail message. This message is saved for later 
     *                  retrieval by the {@link #getMessage()} method. 
     * @param cause The throwable which caused this exception to be thrown.
     *              This value is saved for later retrieval by the 
     *              {@link #getCause()} method.
     */
    public FactoryConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}
