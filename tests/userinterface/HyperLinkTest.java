package userinterface;

import domainlayer.ContentSpan;
import domainlayer.TextSpan;
import domainlayer.UIController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

class HyperLinkTest{
	
    private AddressBar bar;
    private Pane doc;
    private UIController ctrl;

    private UIHyperlink link1;
    private UIHyperlink link2;
    private final String href1 = "/wiki/SWOP";
    private final String href2 = "heeeel lange href!!!!!";

    @BeforeEach
    void setUp() {

        Browsr browsr = new Browsr("browsr");
        
        bar = browsr.getAddressBar();
        doc = browsr.getDocumentArea();
        
		ctrl = doc.getController();

		
        int height1 = 10;
        String text1 = "klik hier voor swop";
        link1 = new UIHyperlink(0,0,0, height1, href1, text1);

        int height2 = 100;
        String text2 = "kort.";
        link2 = new UIHyperlink(10,15,0, height2, href2, text2);

        UIHyperlink goodLink = new UIHyperlink(10, 10, 0, height1, "browsrtest.html", text1);
    }
	// ===== main success scenario =====
	
	// 1
	@Test
	@DisplayName("The user clicks a hyperlink in the document area")
	void clickHyperlink() {
        // Not possible to calculate the actual with by hand, depends on the font etc.
        assertFalse(link1.isCalculateActualWidth());
        assertFalse(link2.isCalculateActualWidth());
        // LINK 1
        int link1Width = link1.getWidth();
        // Click not on link1
        int mouseClick = MouseEvent.MOUSE_RELEASED;
        int leftMouse = MouseEvent.BUTTON1;
        ReturnMessage result = link1.getHandleMouse(mouseClick,link1Width+1, 5, 0, leftMouse, 0);
        assertEquals("", result.getContent());
        // Click on link1
        ReturnMessage result2 = link1.getHandleMouse(mouseClick,link1Width-1, 5, 0, leftMouse, 0);
        assertEquals(this.href1, result2.getContent());
        
        // LINK 2
        int link2Width = link2.getWidth();
        // Click not on link2
        ReturnMessage result3 = link2.getHandleMouse(mouseClick,link2Width+1+10, 17, 0, leftMouse, 0);
        assertEquals("", result3.getContent());
        // Click on link2
        ReturnMessage result4 = link2.getHandleMouse(mouseClick,link2Width-1+10, 17, 0, leftMouse, 0);
        assertEquals(this.href2, result4.getContent());
	}
	
	// 2.1
	@Test
	@DisplayName("Compose full URL")
	void composeFullURL() {
		// browsr is already initialised with prof. Jacobs' index.html url
		String href = "browsrtest.html";
		ctrl.getCurrentDocument().setUrlString("https://people.cs.kuleuven.be/bart.jacobs/index.html");
		// compose url with href and load the document
        // id should be 0 as there's only one pane loaded
		ctrl.loadDocumentFromHref(0, href);
		
		// we traveled to this new url, hence the url in the bar has changed
		assertEquals(bar.getURL(), "https://people.cs.kuleuven.be/bart.jacobs/browsrtest.html");
		
	}
	
	// 2.2
	@Test
	@DisplayName("Load the document and show it")
	void loadAndShowDoc() {
		// browsr is already initialised with prof. Jacobs' index.html url
		String href = "browsrtest.html";
		ctrl.getCurrentDocument().setUrlString("https://people.cs.kuleuven.be/bart.jacobs/index.html");
		// compose url with href and load the document
		ctrl.loadDocumentFromHref(0, href);
		
		UITable content = (UITable) ((DocumentCellDecorator) doc.getContent().getContent()).getContentWithoutScrollbars();
        UITextField text = (UITextField) content.getContent().get(0).get(0);
		// check if document has changed
        assertEquals(text.getText(),
				"HTML elements partially supported by Browsr:");
	}
	
	// 2.3
	@Test
	@DisplayName("Update Address bar to show full URL")
	void updateAddressBar() {
		// browsr is already initialised with prof. Jacobs' index.html url
		String href = "browsrtest.html";
		ctrl.getCurrentDocument().setUrlString("https://people.cs.kuleuven.be/bart.jacobs/index.html");
		// compose url with href and load the document
		ctrl.loadDocumentFromHref(0, href);
		
		// check Addressbar
		assertEquals(bar.getURL(), 
				"https://people.cs.kuleuven.be/bart.jacobs/browsrtest.html");
	}
	
	// ===== Extensions =====
	 
	// 2a.1.1 
	@Test
	@DisplayName("URL is malformed, shows error document")
    void malformedURL() {
        String malformedURL = "ww.www.test.com";
        UIController controller = new UIController();
        
        // setup the pane tree
        controller.addPaneDocument();
        
        controller.loadDocument(0, malformedURL);

        // Verify contents of returned URL
        ContentSpan contentSpan = controller.getContentSpan(0);
        TextSpan textSpan = (TextSpan) contentSpan;
        assertEquals("Error: malformed URL.", textSpan.getText());
    }
	
//	// 2a.1.2
//	@Test
//	@DisplayName("Loading fails, shows error document")
//	void loadingFailed() {
//		fail("");
//	}
//	
//	// 2a.1.3
//	@Test
//	@DisplayName("Parsing the document fails, shows error document")
//	void docParsingFails() {
//		fail("");
//	}
	
	
}