package UserInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DocumentArea:")
class DocumentAreaTest {
    private DocumentArea documentArea1;
    private DocumentArea documentArea2;
    private final int doc2Xpos = 10;
    private final int doc2Ypos = 15;

    @BeforeEach
    void setUp() {
        documentArea1 = new DocumentArea(0,0,100,100);
        documentArea2 = new DocumentArea(doc2Xpos,doc2Ypos,150,500);
    }

    @Test
    @DisplayName("handles resizes")
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
    void handleMouse() {
    }
}