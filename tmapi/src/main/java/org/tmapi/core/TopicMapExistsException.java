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
 * Exception thrown when an attempt is made to create a new {@link TopicMap}
 * under a storage address (an IRI) that is already assigned to another 
 * {@link TopicMap} in the same {@link TopicMapSystem}.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 62 $ - $Date: 2008-08-08 12:00:50 +0000 (Fri, 08 Aug 2008) $
 */
public class TopicMapExistsException extends TMAPIException {

    private static final long serialVersionUID = -1721148856137546269L;

    /**
     * Constructs a new <tt>TopcMapExistsException</tt> with the specified 
     * detail message.
     *
     * @param message The detail message. This message is saved for later 
     *                  retrieval by the {@link #getMessage()} method.
     */
    public TopicMapExistsException(String message) {
        super(message);
    }

}
