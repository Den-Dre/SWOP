package UserInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sun.net.httpserver.Authenticator.Success;

import browsrhtml.ContentSpanBuilder;
import domainmodel.ContentSpan;
import domainmodel.Document;
import domainmodel.UIController;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DocumentArea:")
class DocumentAreaTest {
    private DocumentArea documentArea1;
    private DocumentArea documentArea2;
    private final int doc2Xpos = 10;
    private final int doc2Ypos = 15;

    @BeforeEach
    void setUp() throws Exception {
        documentArea1 = new DocumentArea(0,0,100,100);
        documentArea2 = new DocumentArea(doc2Xpos,doc2Ypos,150,500);
    }

    @Test
    @DisplayName("Handles resizes")
    void handleResize() {
        // Make doc1 smaller
        int newWidth1 = 20;
        int newHeight1 = 20;
        documentArea1.handleResize(newWidth1,newHeight1);
        assertEquals(newHeight1,documentArea1.getHeight());
        assertEquals(newWidth1, documentArea1.getWidth());
        // Make doc2 larger
        int newWidth2 = 600;
        int newHeight2 = 1000;
        documentArea2.handleResize(newWidth2,newHeight2);
        assertEquals(newHeight2-doc2Ypos,documentArea2.getHeight());
        assertEquals(newWidth2-doc2Xpos, documentArea2.getWidth());
        // Make the window smaller than the xpos and ypos of doc2
        int newWidth3 = 5;
        int newHeight3 = 3;
        documentArea2.handleResize(newWidth3,newHeight3);
        assertEquals(newHeight2-doc2Ypos,documentArea2.getHeight());
        assertEquals(newWidth2-doc2Xpos, documentArea2.getWidth());
    }

    @Test
    @DisplayName("Can set the width and height")
    void initWidthHeightPos() throws Exception {
        // ====== Setup =======
        int x = 0;
        int y = 0;
        int width = 20;
        int textSize = 15;
        String text = "oefentext";
        String text2 = "aa";
        String href = "/wiki/Java";
        String hrefText = "Java";
        DocumentArea doc = new DocumentArea(x,y,500,500);

        // Texfields and hyperlink
        UITextField textField3 = new UITextField(x, y, width, textSize, text);
        UITextField textField4 = new UITextField(x, y, width, textSize, text2);
        UIHyperlink link2 = new UIHyperlink(x,y, width, textSize, href,hrefText);
        ArrayList<DocumentCell> row = new ArrayList<>();
        ArrayList<DocumentCell> row2 = new ArrayList<>();
        ArrayList<ArrayList<DocumentCell>> tableContents = new ArrayList<>();
        row.add(textField3);
        row.add(textField4);
        row2.add(link2);
        tableContents.add(row);
        tableContents.add(row2);
        UITable table = new UITable(x,y,width, width, tableContents);
        /*
        table layout:
        ----------------
        | text | text2 |
        | link |       |
        ----------------
         */
        doc.setContent(table);
        // =====================
        // The width and height of the table should be the width and height of the cells combined.
        // The height of a cell is the size of the text. The width is the
        double ratio = doc.getContent().getHeightToWidthRatio();
        assertEquals(2*(textSize+ table.verticalOffset), doc.getContent().getMaxHeight());
        assertEquals((int) ((Math.max(text.length(), hrefText.length())+text2.length())*textSize*ratio), doc.getContent().getMaxWidth());

        // Click on link and retrieve href attribute
        assertEquals(href, doc.getContent().getHandleMouse(MouseEvent.MOUSE_PRESSED, 1,textSize+6, 1, MouseEvent.BUTTON1, 0));

        // Check x and y positions: can't be accessed without adding additional getters ..
    }
    
    @Test
    @DisplayName("Can translate a contentspan to a documentcell")
    void handleTranslate() throws Exception {
    	// ======== Setup ===========
    	UIController ctrl1 = new UIController(); 
    	Document doc1 = new Document();
    	UIController ctrl2 = new UIController(); 
    	Document doc2 = new Document();
    	UIController ctrl3 = new UIController(); 
    	Document doc3 = new Document();
    	UIController ctrl4 = new UIController(); 
    	Document doc4 = new Document();
    	
    	// a valid Browsr document
    	ContentSpan content1 = ContentSpanBuilder.buildContentSpan("""
    			<a href="a.html">a</a>
    			"""); // only a HyperLink
    	doc1.changeContentSpan(content1);
    	documentArea1.setController(ctrl1);
    	ctrl1.setDocument(doc1);
    	documentArea1.contentChanged(); // would throw an exception if translation failed
    	assertEquals(documentArea1.getContent().getClass(), UIHyperlink.class);
    	assertEquals(((UIHyperlink) documentArea1.getContent()).getText(), "a");
    	
		ContentSpan content2 = ContentSpanBuilder.buildContentSpan("""
				<table>
				  <tr><td>HTML elements partially supported by Browsr:
				</table>
				"""); // only a Table
    	doc2.changeContentSpan(content2);
    	documentArea1.setController(ctrl2);
    	ctrl2.setDocument(doc2);	
    	documentArea1.contentChanged(); // would throw an exception if translation failed
    	assertEquals(documentArea1.getContent().getClass(), UITable.class); // content is translated into a UITable
    	UITable table = (UITable) documentArea1.getContent(); 
    	assertEquals(table.getContent().size(), 1); // this table only contains one element
    	assertEquals(table.getContent().get(0).get(0).getClass(), UITextField.class); // and this is a UITextField
    	assertEquals(((UITextField) table.getContent().get(0).get(0)).getText(), "HTML elements partially supported by Browsr:");
    	// the UITextField contains what we expect
    	
		ContentSpan content3 = ContentSpanBuilder.buildContentSpan("""
				  HTML elements partially supported by Browsr:
				"""); // only a piece of Text
    	doc3.changeContentSpan(content3);
    	documentArea1.setController(ctrl3);
    	ctrl3.setDocument(doc3);
    	documentArea1.contentChanged(); // would throw an exception if translation failed
    	assertEquals(documentArea1.getContent().getClass(), UITextField.class);	
    	assertEquals(((UITextField) documentArea1.getContent()).getText(), "HTML elements partially supported by Browsr:");
    	
        // not a valid Browsr document (yoinked from https://www.w3schools.com/html/tryit.asp?filename=tryhtml_basic_document)
		assertThrows(Exception.class, () -> {
			ContentSpanBuilder.buildContentSpan("""
					<!DOCTYPE html>
					<html>
					<body>
					
					<h1>My First Heading</h1>
					
					<p>My first paragraph.</p>
					
					</body>
					</html>
					""");
        });
		
		ContentSpan content4 = ContentSpanBuilder.buildContentSpan("""
				<form action="browsrformactiontest.php">
					<table>
						<tr><td>List words from the Woordenlijst Nederlandse Taal
						<tr><td>
							<table>
								<tr>
									<td>Starts with:
									<td><input type="text" name="starts_with">
								<tr>
									<td>Max. results:
									<td><input type="text" name="max_nb_results">
							</table>
						<tr><td><input type="submit">
					</table>
				</form>
				""");
		
		doc4.changeContentSpan(content4);
		documentArea1.setController(ctrl4);
		ctrl4.setDocument(doc4);
		documentArea1.contentChanged();
		assertTrue(documentArea1.getContent() instanceof UIForm);
		UIForm form = (UIForm) documentArea1.getContent();
		assertEquals(form.getAction(), "browsrformactiontest.php");
		assertTrue(form.getFormContent() instanceof UITable);
		UITable table2 = (UITable) form.getFormContent();
		assertTrue(table2.getContent().get(0).get(0) instanceof UITextField);
		assertEquals(((UITextField) table2.getContent().get(0).get(0)).getText(), "List words from the Woordenlijst Nederlandse Taal");
		assertTrue(table2.getContent().get(1).get(0) instanceof UITable);
		assertTrue(table2.getContent().get(2).get(0) instanceof UIButton);
		UITable table3 = (UITable) table2.getContent().get(1).get(0);
		assertTrue(table3.getContent().get(0).get(0) instanceof UITextField);
		assertEquals(((UITextField) table3.getContent().get(0).get(0)).getText(), "Starts with:");
		assertTrue(table3.getContent().get(0).get(1) instanceof UITextInputField);
		assertEquals(((UITextInputField) table3.getContent().get(0).get(1)).getNamesAndValues().get(0), "starts_with=");
		assertTrue(table3.getContent().get(1).get(0) instanceof UITextField);
		assertEquals(((UITextField) table3.getContent().get(1).get(0)).getText(), "Max. results:");
		assertTrue(table3.getContent().get(1).get(1) instanceof UITextInputField);
		assertEquals(((UITextInputField) table3.getContent().get(1).get(1)).getNamesAndValues().get(0), "max_nb_results=");

		
    }
}