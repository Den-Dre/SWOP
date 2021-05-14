package userinterface;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domainlayer.ContentSpan;
import domainlayer.TextSpan;
import domainlayer.UIController;

import javax.print.Doc;

/**
 * A class with tests for the "Enter URL" use-case. These tests are far from complete and only meant to 
 * check all the boxes in the assignment. Bigger and more elaborate tests can be found in the tests 
 * directory of the source code. There are nonetheless still tests which can only be found in these 
 * documents.
 * @author jakob
 */
class UrlScenTest {

    private AddressBar bar;
    private Pane doc;
    private UIController ctrl;
    private int offset = 5;
    private final char undefChar = KeyEvent.CHAR_UNDEFINED;
    private final int mouseClick = MouseEvent.MOUSE_RELEASED;
    private final int keyPress = KeyEvent.KEY_PRESSED;
    private final int leftMouse = MouseEvent.BUTTON1;
    private final int shiftModifier = KeyEvent.SHIFT_DOWN_MASK;
    
    private Browsr browsr = null;
    
    private final int badURL[] = {65,65,65,65};
    
    @BeforeEach
    public void init() throws Exception {
        bar = new AddressBar(offset,offset,500,50, offset);
        
        browsr = new Browsr("browsr");
        
        bar = browsr.getAddressBar();
        doc = browsr.getDocumentArea();
        
		ctrl = doc.getController();

    }
	
	// ===== main success scenario =====
	
	// 1
	@Test
	@DisplayName("user clicks the Address bar")
	void clickAddressbar() {
        bar.toggleFocus(true);
        assertTrue(bar.hasFocus);	
    }
	
	// 2
	@Test
	@DisplayName("AddressBar gets focus, shows Address bar content as selected")
	void hasFocusAndSelects() {
        String url = "helloworld.com";
        bar.changeURLto(url);
        // Click on bar and check if it has focus
        bar.toggleFocus(true);

        // => 2 * shift-left + backspace
        for (int i = 0; i < 2; i++)
            bar.handleKey(keyPress, KeyEvent.VK_LEFT, undefChar, shiftModifier);
        bar.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar,0);
        assertEquals("helloworld.c", bar.getURL());
        // => 2 * left + 2 * shift-right + backspace
        for (int i = 0; i < 2; i++)
            bar.handleKey(keyPress, KeyEvent.VK_LEFT, undefChar, 0);
        for (int i = 0; i < 2; i++)
            bar.handleKey(keyPress, KeyEvent.VK_RIGHT, undefChar, shiftModifier);
        bar.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar,0);
        assertEquals("helloworld", bar.getURL());
        // => shift-home + backspace
        bar.handleKey(keyPress, KeyEvent.VK_HOME, undefChar, shiftModifier);
        bar.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar,0);
        assertEquals("", bar.getURL());
        // set url back to a string
        bar.changeURLto("test");
        bar.toggleFocus(true);
        // press home
        bar.handleKey(keyPress, KeyEvent.VK_HOME, undefChar, 0);
        // => shift-end + backspace 
        bar.handleKey(keyPress, KeyEvent.VK_END,undefChar, shiftModifier);
        bar.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar,0);
        assertEquals("", bar.getURL());
	}
	
	// 3 and 4
	
	@Test
    @DisplayName("Handles simple backspace and delete operation")
    void handleBackspace(){
        String url = "helloworld.com";
        bar.changeURLto(url);
        // Click on bar and check if it has focus
        bar.handleMouse(mouseClick, 10,10, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        // Press Right (all the text is selected when clicking to gain focus, we want no text to be selected for this test)
        bar.handleKey(keyPress, KeyEvent.VK_RIGHT, undefChar, 0);
        // Press backspace
        bar.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar, 0);
        assertEquals("helloworld.co", bar.getURL());

        //Press left
        bar.handleKey(keyPress, KeyEvent.VK_LEFT, undefChar, 0);
        // Press backspace again
        bar.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar,0);
        assertEquals("helloworld.o", bar.getURL());
        // Press delete
        bar.handleKey(keyPress, KeyEvent.VK_DELETE, undefChar,0);
        assertEquals("helloworld.", bar.getURL());
    }

    @Test
    @DisplayName("Deletes all text with Backspace after the bar gains focus from mouseclick")
    void handleBackSpaceAfterClick() {
        String url = "helloworld.com";
        bar.changeURLto(url);
        // Click on bar and check if it has focus
        bar.handleMouse(mouseClick, 10,10, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        // Press backspace
        bar.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar, 0);
        assertEquals("", bar.getURL());
    }

    @Test
    @DisplayName("Deletes all text with Delete after the bar gains focus from mouseclick")
    void handleDeleteAfterClick() {
        String url = "helloworld.com";
        bar.changeURLto(url);
        // Click on bar and check if it has focus
        bar.handleMouse(mouseClick, 10,10, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        // Press backspace
        bar.handleKey(keyPress, KeyEvent.VK_DELETE, undefChar, 0);
        assertEquals("", bar.getURL());
    }

    @Test
    @DisplayName("Can handle End, Home, Left and Right + typing at insertion point")
    void handleEndHomeLeftRight() {
        String url = "helloworld.com";
        bar.changeURLto(url);
        // Click on bar and check if it has focus
        bar.handleMouse(mouseClick, 10,10, 1,leftMouse,0);
        assertTrue(bar.hasFocus);

        // => Press Home + enter letter
        bar.handleKey(keyPress, KeyEvent.VK_HOME, undefChar, 0);
        bar.handleKey(keyPress, 65, 'a', 0);
        assertEquals("ahelloworld.com", bar.getURL());
        // => Press End + enter letter
        bar.handleKey(keyPress, KeyEvent.VK_END, undefChar, 0);
        bar.handleKey(keyPress, 65, 'a', 0);
        assertEquals("ahelloworld.coma", bar.getURL());
        // => Press left + enter letter
        bar.handleKey(keyPress, KeyEvent.VK_LEFT, undefChar, 0);
        bar.handleKey(keyPress, 66, 'b',0);
        assertEquals("ahelloworld.comba", bar.getURL());
        // => Press Right + enter letter
        bar.handleKey(keyPress, KeyEvent.VK_RIGHT, undefChar, 0);
        bar.handleKey(keyPress, 66, 'b',0);
        assertEquals("ahelloworld.combab", bar.getURL());
    }

	// 5.1
	@Test
	@DisplayName("user presses Enter")
	void pressEnter() {
        String url = "helloworld.com";
        bar.changeURLto(url);
        // Click on bar and check if it has focus
        bar.handleMouse(mouseClick, 10,10, 1,leftMouse,0);
        assertTrue(bar.hasFocus);

        // => Press Home + enter letter
        bar.handleKey(keyPress, KeyEvent.VK_HOME, undefChar, 0);
        bar.handleKey(keyPress, 65, 'a', 0);
        assertEquals("ahelloworld.com", bar.getURL());	
    }
	
	// 5.2
	@Test
	@DisplayName("user clicks outside AddressBar")
	void clickOutsideBar() {
        assertFalse(bar.hasFocus);
        // click inside UserInterface.AbstractFrame
        bar.handleMouse(mouseClick, 10,15,1, leftMouse,0);
        assertTrue(bar.hasFocus);
        // click outside addressbar
        bar.handleMouse(mouseClick, 0,0,1,leftMouse,0);
        assertFalse(bar.hasFocus);
        bar.handleMouse(mouseClick, 6,500,1,leftMouse,0);
        assertFalse(bar.hasFocus);
        // click on the edge
        bar.handleMouse(mouseClick, 105,20,1,leftMouse,0);
        assertTrue(bar.hasFocus);	}
	
	// 6
	@Test
	@DisplayName("document gets loaded and shown")
	void loadAndShowDoc() {
		bar.setURL("https://people.cs.kuleuven.be/bart.jacobs/browsrtest.html");
		bar.toggleFocus(true);
		browsr.handleKeyEvent(keyPress, KeyEvent.VK_ENTER, undefChar, 0);
		
		// something must be visible, contents have changed!
        DocumentCell table = ((DocumentCellDecorator) doc.getContent()).getContentWithoutScrollbars();
        assertTrue(table instanceof UITable);
		assertEquals(((UITextField) ((UITable) table).getContent().get(0).get(0)).getText(),
				"HTML elements partially supported by Browsr:");
	}
	
	// ===== Extensions =====
	
	// 5a.1
	@Test
	@DisplayName("user presses Escape to cancel editing, contents get reverted to before focus")
	void cancelEditEscape() {
        String first = "helloworld.com";
        String second = "loremipsum.be";
        bar.changeURLto(first);
        bar.handleMouse(mouseClick, 10,10, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        assertEquals(first, bar.getURL());

        bar.handleKey(keyPress, KeyEvent.VK_ESCAPE, undefChar, 0);

        // Is the url reset to the url before editing?
        assertFalse(bar.hasFocus);
        assertEquals("", bar.getURL());

        // Set URL to to first then Enter then to second and escape. The url should be first.
        bar.changeURLto(first);
        bar.handleMouse(mouseClick, 10,10, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        assertEquals(first, bar.getURL());
        // Press enter
        bar.handleKey(keyPress, KeyEvent.VK_ENTER, undefChar, 0);
        assertEquals(first, bar.getURL());
        assertFalse(bar.hasFocus);

        // Click on bar again to gain focus
        bar.changeURLto(second);
        bar.handleMouse(mouseClick, 10,10, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        // url is changed to second url
        assertEquals(second, bar.getURL());
        // but operation is cancelled with Escape
        bar.handleKey(keyPress, KeyEvent.VK_ESCAPE, undefChar, 0);
        // bar should have no focus and contain the first url
        assertFalse(bar.hasFocus);
        assertEquals(first, bar.getURL());	}
	
	// 6a.1
	@Test
	@DisplayName("URL is malformed, shows error document")
    void malformedURL() {
        String malformedURL = "ww.www.test.com";
        UIController controller = new UIController();
        controller.loadDocument(malformedURL);

        // Verify contents of returned URL
        ContentSpan contentSpan = controller.getContentSpan();
        TextSpan textSpan = (TextSpan) contentSpan;
        assertEquals("Error: malformed URL.", textSpan.getText());
    }
	
//	// 6a.1.2
//	@Test
//	@DisplayName("Loading fails, shows error document")
//	void loadingFailed() {
//		fail("");
//	}
//	
//	// 6a.1.3
//	@Test
//	@DisplayName("Parsing the document fails, shows error document")
//	void docParsingFails() {
//		fail("");
//	}

	
}