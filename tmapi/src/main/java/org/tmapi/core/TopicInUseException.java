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
 * Thrown when an attempt is made to remove a {@link Topic} which is being used
 * as a type, as a reifier, or as a role player in an association, or in a scope. 
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 97 $ - $Date: 2009-02-06 16:25:36 +0000 (Fri, 06 Feb 2009) $
 */
public class TopicInUseException extends ModelConstraintException {

    private static final long serialVersionUID = 486617548358575845L;

    /**
     * Creates a new <tt>TopicInUseException</tt> with the specified
     * message.
     *
     * @param topic The topic which is not removable.
     * @param msg The detail message.
     */
    public TopicInUseException(Topic topic, String msg) {
        super(topic, msg);
    }

    /* (non-Javadoc)
     * @see org.tmapi.core.ModelConstraintException#getReporter()
     */
    @Override
    public Topic getReporter() {
        return (Topic) super.getReporter();
    }

}
