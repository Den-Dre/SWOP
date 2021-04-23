package domainlayer;

import java.io.Serializable;

/**
 * A class to represent a superordinate,
 * abstract object that represents the contents
 * that are on a certain webpage.
 *
 * A ContentSpan can be specialised by a
 * {@link Table}, a {@link HyperLink} or
 * a {@link TextSpan}.
 */
public class ContentSpan implements Serializable {
    /**
     * A default constructor to initialise a ContentSpan.
     */
    public ContentSpan() {}
}
