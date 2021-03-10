package domainmodel;

import java.io.*;
import java.net.URL;

public class UIController {

    private Document document;
        
    public UIController(URL url) {
    	System.out.println("UIController obj made...");
        this.document = new Document(url);
    }

    protected Serializable loadDocument(String document) throws IOException {
        return this.document.getSerializedContentSpan(document);
    }
    
    // TODO doc    
    public ContentSpan getContent() {
    	return this.document.getContent();
    }
    
    public Document getContents() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("documentStream.txt"));
        Document doc = (Document) in.readObject();
        in.close();
        return doc;
    }
}
