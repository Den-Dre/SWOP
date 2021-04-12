package domainmodel;

import java.util.HashMap;

public class BookmarksURLKeeper {
    public BookmarksURLKeeper() {
        this.bookmarkHrefs = new HashMap<>();
    }

    public void addBookmarksHref(String name, String href) {
        this.bookmarkHrefs.put(name, href);
    }

    public String getHrefFromBookmark(String name) {
        return this.bookmarkHrefs.get(name);
    }

    private final HashMap<String, String> bookmarkHrefs;
}
