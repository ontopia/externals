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
 * This exception is used to report 
 * <a href="http://www.isotopicmaps.org/sam/sam-model/">Topic Maps - Data Model</a> 
 * constraint violations. 
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 33 $ - $Date: 2008-08-02 16:42:29 +0200 (Sa, 02 Aug 2008) $
 */
public class ModelConstraintException extends TMAPIRuntimeException {

    private static final long serialVersionUID = 7038106692343162395L;

    private final Construct _reporter;

    /**
     * Creates a new <tt>ModelConstraintException</tt> with the specified
     * message.
     * 
     * @param reporter The construct which has thrown this exception.
     * @param msg The detail message.
     */
    public ModelConstraintException(Construct reporter, String msg) {
        super(msg);
        _reporter = reporter;
    }

    /**
     * Returns the {@link Construct} which has thrown the exception.
     *
     * @return The construct which has thrown the exception.
     */
    public Construct getReporter() {
        return _reporter;
    }

}
