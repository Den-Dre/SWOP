package UserInterface;

import domainmodel.ContentSpan;
import domainmodel.TextSpan;
import domainmodel.UIController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class HyperLinkTest{
	
    private AddressBar bar;
    private DocumentArea doc;
    private UIController ctrl;

    private UIHyperlink link1;
    private UIHyperlink link2;
    private final String href1 = "/wiki/SWOP";
    private final String href2 = "heeeel lange href!!!!!";

    @BeforeEach
    void setUp() throws Exception {

        Browsr browsr = new Browsr("browsr");
        
        bar = browsr.getAddressBar();
        doc = browsr.getDocumentArea();
        
		ctrl = doc.controller;

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
        int mouseClick = MouseEvent.MOUSE_CLICKED;
        int leftMouse = MouseEvent.BUTTON1;
        String result = link1.getHandleMouse(mouseClick,link1Width+1, 5, 0, leftMouse, 0);
        assertEquals("", result);
        // Click on link1
        String result2 = link1.getHandleMouse(mouseClick,link1Width-1, 5, 0, leftMouse, 0);
        assertEquals(this.href1, result2);
        
        // LINK 2
        int link2Width = link2.getWidth();
        // Click not on link2
        String result3 = link2.getHandleMouse(mouseClick,link2Width+1+10, 17, 0, leftMouse, 0);
        assertEquals("", result3);
        // Click on link2
        String result4 = link2.getHandleMouse(mouseClick,link2Width-1+10, 17, 0, leftMouse, 0);
        assertEquals(this.href2, result4);
	}
	
	// 2.1
	@Test
	@DisplayName("Compose full URL")
	void composeFullURL() {
		// browsr is already initialised with prof. Jacobs' index.html url
		String href = "browsrtest.html";
		ctrl.getDocument().setUrlString("https://people.cs.kuleuven.be/bart.jacobs/index.html");
		// compose url with href and load the document
		ctrl.loadDocumentFromHref(href);
		
		// we traveled to this new url, hence the url in the bar has changed
		assertEquals(bar.getURL().toString(), "https://people.cs.kuleuven.be/bart.jacobs/browsrtest.html");
		
	}
	
	// 2.2
	@Test
	@DisplayName("Load the document and show it")
	void loadAndShowDoc() {
		// browsr is already initialised with prof. Jacobs' index.html url
		String href = "browsrtest.html";
		ctrl.getDocument().setUrlString("https://people.cs.kuleuven.be/bart.jacobs/index.html");
		// compose url with href and load the document
		ctrl.loadDocumentFromHref(href);
		
		// check if document has changed
		assertEquals(doc.getContent().getClass(), UITable.class);
		assertEquals(((UITextField) ((UITable) doc.getContent()).getContent().get(0).get(0)).getText(),
				"HTML elements partially supported by Browsr:");
	}
	
	// 2.3
	@Test
	@DisplayName("Update Address bar to show full URL")
	void updateAddressBar() {
		// browsr is already initialised with prof. Jacobs' index.html url
		String href = "browsrtest.html";
		ctrl.getDocument().setUrlString("https://people.cs.kuleuven.be/bart.jacobs/index.html");
		// compose url with href and load the document
		ctrl.loadDocumentFromHref(href);
		
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
        controller.loadDocument(malformedURL);

        // Verify contents of returned URL
        ContentSpan contentSpan = controller.getContentSpan();
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