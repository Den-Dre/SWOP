package userinterface;

/**
 * An enumeration of the possible
 * states of visible windows at
 * this time in this {@link userinterface.Browsr}.
 */
public enum BrowsrLayout {
    /**
     * A constant to denote the regular layout of
     * a {@link Browsr} consisting of a {@link AddressBar},
     * {@link BookmarksBar} and a {@link DocumentArea}.
     */
    REGULAR,

    /**
     * A constant to denote the layout of a {@link Browsr}
     * when a bookmark is being saved, consisting of a
     * {@link UIForm} and two {@link UIButton}'s.
     */
    BOOKMARKS_DIALOG,

    /**
     * A constant to denote the layout of a {@link Browsr}
     * when a page is being saved, consisting of a
     * {@link UIForm} and two {@link UIButton}'s.
     */
    SAVE_DIALOG
}
