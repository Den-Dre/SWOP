package domainlayer;

import userinterface.BookmarksBar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookmarksURLKeeperTest {

    private BookmarksBar bookmarksBar;
    private UIController controller;

    @BeforeEach
    void setup(){
        int bookmarksBarOffset = 5;
        int addressBarHeight = 20;
        int bookmarksBarHeight = 20;

        bookmarksBar = new BookmarksBar(bookmarksBarOffset, addressBarHeight + 2 * bookmarksBarOffset, 100, bookmarksBarHeight, bookmarksBarOffset);
        controller = new UIController();
        // Couple the UIController to the BookmarksBar
        bookmarksBar.setUIController(controller);
    }
    
    @Test
    @DisplayName("Can add bookmark")
    void addBookmarkTest() {
        final String tableUrl = "https://people.cs.kuleuven.be/bart.jacobs/browsrtest.html";
        final String badUrl = "www.fout.be";
        bookmarksBar.addBookmark("Table", tableUrl);
        assertEquals(tableUrl, controller.getURLFromBookmark("Table"));
        bookmarksBar.addBookmark("Malformed", badUrl);
        assertEquals(badUrl, controller.getURLFromBookmark("Malformed"));
        // Check if bookmark that was added first is still present:
        assertEquals(tableUrl, controller.getURLFromBookmark("Table"));
    }

}
