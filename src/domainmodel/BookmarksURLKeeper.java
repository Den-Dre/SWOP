package domainmodel;

import java.util.HashMap;

/**
 * A class that keeps track of the
 * domain layer knowledge of saved
 * bookmarks, such as the associated
 * URL, ...
 */
public class BookmarksURLKeeper {
    /**
     * Construct a new {@code BookmarksURLKeeper}
     * with an initially empty list of bookmarks.
     */
    public BookmarksURLKeeper() {
        this.bookmarkHrefs = new HashMap<>();
    }

    /**
     * Add a bookmark to the list of kept bookmarks.
     *
     * @param name: the name of the bookmark to be added.
     * @param href: the {@code href} value of the bookmark to be added.
     */
    public void addBookmarksHref(String name, String href) {
        this.bookmarkHrefs.put(name, href);
    }

    /**
     * Get the {@code href} attribute of the bookmark
     * corresponding to the given {@code name}.
     *
     * @param name:
     *           the name of the bookmark of which the {@code href} attribute is to be retrieved.
     * @return href:
     *           the {@code href} attribute associated to the bookmark which is identified by the given name.
     */
    public String getHrefFromBookmark(String name) {
        return this.bookmarkHrefs.get(name);
    }

    /**
     * A {@link HashMap} of {@code name}, {@code href} pairs
     * that keeps track of the currently bookmarks added up until now.
     */
    private final HashMap<String, String> bookmarkHrefs;
}
