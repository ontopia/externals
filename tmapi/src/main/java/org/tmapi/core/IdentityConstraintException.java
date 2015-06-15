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
 * This exception is used to report identity constraint violations.
 * 
 * Assigning an item identifier, a subject identifier, or a subject locator
 * to different objects causes an <tt>IdentityConstraintException</tt> to be
 * thrown.
 * 
 * @author <a href="http://tmapi.org/">The TMAPI Project</a>
 * @version $Rev: 33 $ - $Date: 2008-08-02 16:42:29 +0200 (Sa, 02 Aug 2008) $
 */
public class IdentityConstraintException extends ModelConstraintException {

    private static final long serialVersionUID = -8239221052536586370L;

    private final Construct _existing;
    private final Locator _locator;

    /**
     * Creates a new <tt>IdentityConstraintException</tt> with the specified
     * message.
     * 
     * @param reporter The construct to which the identity should have been 
     *          assigned to. In case a factory method has thrown this 
     *          exception, the construct which provides the factory method.
     * @param existing The construct which has the same identity.
     * @param locator The locator representing the identity.
     * @param msg The detail message.
     */
    public IdentityConstraintException(Construct reporter, Construct existing, 
            Locator locator, String msg) {
        super(reporter, msg);
        _existing = existing;
        _locator = locator;
    }

    /**
     * Returns the {@link Construct} which already has the identity represented
     * by the locator {@link #getLocator()}.
     *
     * @return The existing construct.
     */
    public Construct getExisting() {
        return _existing;
    }

    /**
     * Returns the locator representing the identity that caused the exception.
     *
     * @return A locator representing the identity which has caused the exception.
     */
    public Locator getLocator() {
        return _locator;
    }
}
