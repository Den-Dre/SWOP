package userinterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import browsrhtml.ContentSpanBuilder;
import domainlayer.ContentSpan;
import domainlayer.Document;
import domainlayer.UIController;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ContentFrame:")
class ContentFrameTest {
    private ContentFrame contentFrame1;
    private ContentFrame contentFrame2;
    private final int doc2Xpos = 10;
    private final int doc2Ypos = 15;

    @BeforeEach
    void setUp() {
        contentFrame1 = new ContentFrame(0,0,100,100);
        contentFrame2 = new ContentFrame(doc2Xpos,doc2Ypos,150,500);
    }

    @Test
    @DisplayName("Handles resizes")
    void handleResize() {
        // Make cf1 smaller
        int newWidth1 = 20;
        int newHeight1 = 20;
        contentFrame1.handleResize(newWidth1,newHeight1);
        assertEquals(newHeight1, contentFrame1.getHeight());
        assertEquals(newWidth1, contentFrame1.getWidth());
        // Make cf2 larger
        int newWidth2 = 600;
        int newHeight2 = 1000;
        contentFrame2.handleResize(newWidth2,newHeight2);
        assertEquals(newHeight2-doc2Ypos + 15, contentFrame2.getHeight());
        assertEquals(newWidth2-doc2Xpos + 10, contentFrame2.getWidth());
        // Make the window smaller than the xpos and ypos of cf2
//        int newWidth3 = 5;
//        int newHeight3 = 3;
//        contentFrame2.handleResize(newWidth3,newHeight3);
//        assertEquals(newHeight2-doc2Ypos, contentFrame2.getHeight());
//        assertEquals(newWidth2-doc2Xpos, contentFrame2.getWidth());
        // -> makes no longer sense. 
    }

    @Test
    @DisplayName("Can set the width and height")
    void initWidthHeightPos() {
        // ====== Setup =======
        int x = 0;
        int y = 0;
        int width = 20;
        int textSize = 15;
        String text = "oefentext";
        String text2 = "aa";
        String href = "/wiki/Java";
        String hrefText = "Java";
        ContentFrame doc = new ContentFrame(x,y,500,500);

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
        DocumentCell content = ((VerticalScrollBarDecorator) ((DocumentCellDecorator) doc.getContent()).getContent()).cellToBeDecorated;
        assertEquals(2*(textSize+ table.verticalOffset), content.getMaxHeight());
        assertEquals((int) ((Math.max(text.length(), hrefText.length())+text2.length())*textSize*ratio) + 2*table.horizontalOffset, content.getMaxWidth());

        // Click on link and retrieve href attribute
        assertEquals(href, doc.getContent().getHandleMouse(MouseEvent.MOUSE_RELEASED, 1,textSize+6, 1, MouseEvent.BUTTON1, 0).getContent());

        // Check x and y positions: can't be accessed without adding additional getters ..
    }
    
    @Test
    @DisplayName("Can translate a contentspan to a documentcell")
    void handleTranslate() {
    	// ======== Setup ===========
    	UIController ctrl1 = new UIController(); 
    	int id1 = ctrl1.addPaneDocument();
    	UIController ctrl2 = new UIController();
		int id2 = ctrl2.addPaneDocument();
		UIController ctrl3 = new UIController();
		int id3 = ctrl3.addPaneDocument();
		UIController ctrl4 = new UIController();
		int id4 = ctrl4.addPaneDocument();
		
		// setup root of pane structure
		Pane rootPane1 = new LeafPane(contentFrame1, ctrl4);
		ctrl4.setCurrentDocument(rootPane1.getId());
		
		// a valid userinterface.Browsr document
    	ContentSpan content1 = ContentSpanBuilder.buildContentSpan("""
    			<a href="a.html">a</a>
    			"""); // only a HyperLink
    	ctrl1.getDocument(id1).changeContentSpan(content1);
    	contentFrame1.setController(ctrl1);
    	ctrl1.setCurrentDocument(id1);
    	contentFrame1.setId(id1);
    	contentFrame1.contentChanged(); // would throw an exception if translation failed
    	
    	System.out.println("celly: " + contentFrame1.getContent());
    	
    	assertTrue(((DocumentCellDecorator) contentFrame1.getContent()).getContentWithoutScrollbars() instanceof UIHyperlink);
    	assertEquals(((UIHyperlink) ((DocumentCellDecorator) contentFrame1.getContent()).getContentWithoutScrollbars()).getText(), "a");
    	
		ContentSpan content2 = ContentSpanBuilder.buildContentSpan("""
				<table>
				  <tr><td>HTML elements partially supported by UserInterface.Browsr:
				</table>
				"""); // only a Table
    	ctrl2.getDocument(id2).changeContentSpan(content2);
    	contentFrame1.setController(ctrl2);
    	ctrl2.setCurrentDocument(id2);
    	contentFrame1.contentChanged(); // would throw an exception if translation failed
		// content is translated into a UITable, and text field is extracted
		assertTrue(((VerticalScrollBarDecorator) (((HorizontalScrollBarDecorator) contentFrame1.getContent()).getContent())).getContent() instanceof UITable);
		UITable table = (UITable) ((VerticalScrollBarDecorator) (((HorizontalScrollBarDecorator) contentFrame1.getContent()).getContent())).getContent();
    	assertEquals(table.getContent().size(), 1); // this table only contains one element
    	assertEquals(table.getContent().get(0).get(0).getClass(), UITextField.class); // and this is a UITextField
    	assertEquals(((UITextField) table.getContent().get(0).get(0)).getText(), "HTML elements partially supported by UserInterface.Browsr:");
    	// the UITextField contains what we expect
    	
		ContentSpan content3 = ContentSpanBuilder.buildContentSpan("""
				  HTML elements partially supported by UserInterface.Browsr:
				"""); // only a piece of Text
    	ctrl3.getDocument(id3).changeContentSpan(content3);
    	contentFrame1.setController(ctrl3);
    	ctrl3.setCurrentDocument(id3);
    	contentFrame1.contentChanged(); // would throw an exception if translation failed
		assertTrue(((DocumentCellDecorator) contentFrame1.getContent()).getContentWithoutScrollbars() instanceof UITextField);
		assertEquals(((UITextField) ((DocumentCellDecorator) contentFrame1.getContent()).getContentWithoutScrollbars()).getText(), "HTML elements partially supported by UserInterface.Browsr:");
    	
        // not a valid userinterface.Browsr document (yoinked from https://www.w3schools.com/html/tryit.asp?filename=tryhtml_basic_document)
		assertThrows(Exception.class, () -> ContentSpanBuilder.buildContentSpan("""
				<!DOCTYPE html>
				<html>
				<body>
				
				<h1>My First Heading</h1>
				
				<p>My first paragraph.</p>
				
				</body>
				</html>
				"""));
		
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
		

		ctrl4.getDocument(id4).changeContentSpan(content4);
		contentFrame1.setController(ctrl4);
		ctrl4.setCurrentDocument(id4);
		contentFrame1.contentChanged();
		assertTrue(((DocumentCellDecorator) contentFrame1.getContent()).getContentWithoutScrollbars() instanceof UIForm);
		UIActionForm form = (UIActionForm) ((DocumentCellDecorator) contentFrame1.getContent()).getContentWithoutScrollbars();
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
		assertEquals(table3.getContent().get(0).get(1).getNamesAndValues().get(0), "starts_with=");
		assertTrue(table3.getContent().get(1).get(0) instanceof UITextField);
		assertEquals(((UITextField) table3.getContent().get(1).get(0)).getText(), "Max. results:");
		assertTrue(table3.getContent().get(1).get(1) instanceof UITextInputField);
		assertEquals(table3.getContent().get(1).get(1).getNamesAndValues().get(0), "max_nb_results=");
    }
}