package UserInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        assertEquals(2*textSize, doc.getContent().getMaxHeight());
        assertEquals((int) ((Math.max(text.length(), hrefText.length())+text2.length())*textSize*ratio), doc.getContent().getMaxWidth());

        // Click on link and retrieve href attribute
        assertEquals(href, doc.getContent().getHandleMouse(MouseEvent.MOUSE_CLICKED, 1,textSize+2, 1, MouseEvent.BUTTON1, 0));

        // Check x and y positions: can't be accessed without adding additional getters ..
    }
}