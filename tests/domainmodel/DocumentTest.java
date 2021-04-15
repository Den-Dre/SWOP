package domainmodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DocumentTest {

    private Document doc;

    @BeforeEach
    public void setUp(){
        doc = new Document();
    }

    @Test
    @DisplayName("Save Document test")
    public void testSaveDocument() {
        doc.setUrlString("https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html");
        try {
            doc.saveDocument("test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Save invalid Document test")
    public void testInvalidSaveDocument(){
        doc.changeContentSpan(new TextSpan("Welcome to Browsr!"));
        assertThrows(Exception.class, () -> doc.saveDocument("test"));
    }
}
