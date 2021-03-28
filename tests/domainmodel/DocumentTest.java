package domainmodel;

import UserInterface.Frame;
import UserInterface.IllegalDimensionException;
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
            doc.saveDocument();
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Test
    @DisplayName("Save invalid Document test")
    public void testInvalidSaveDocument(){
        doc.changeContentSpan(new TextSpan("Welcome to Browsr!"));
        Exception exception = assertThrows(Exception.class, () -> {
            doc.saveDocument();
        });
    }
}
