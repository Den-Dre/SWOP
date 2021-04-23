package browsrhtml;

import domainlayer.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * A class to handle the task of receiving
 * tokens of {@link BrowsrDocumentValidator} and
 * converting them to {@link ContentSpan}'s.
 */
public class ContentSpanBuilder extends BrowsrDocumentValidator {

    /**
     * Initialise this ContentSpanBuilder with the given reader
     *
     * @param reader:
     *               The {@link Reader} this ContentSpanBuilder is initialised with.
     */
    public ContentSpanBuilder(Reader reader) {
        super(reader);
        parsedForm = false;
    }

    /**
     * Convert the given HTML code into a {@link ContentSpan}.
     *
     * @param document:
     *                The HTML code to be converted
     * @return contentSpan:
     *                The {@link ContentSpan} corresponding to the given HTML code.
     */
    public static ContentSpan buildContentSpan(String document) {
        return new ContentSpanBuilder(new StringReader(document)).getContentSpan();
    }

    /**
     * Convert the given URL into a {@link ContentSpan}.
     *
     * @param url:
     *                The url referring to the page that needs to be converted.
     * @return contentSpan:
     *                The {@link ContentSpan} corresponding to the given URL.
     * @throws IOException: When the given {@code URL} can't be fetched.
     */
    public static ContentSpan buildContentSpan(URL url) throws IOException {
        return new ContentSpanBuilder(new BufferedReader(new InputStreamReader(url.openStream()))).getContentSpan();
    }

    /**
     * Retrieve the {@link ContentSpan} that corresponds to the
     * next parsed ContentSpan.
     *
     * @return contentSpan:
     *                  The converted {@link ContentSpan}.
     */
    ContentSpan getContentSpan() {
        switch (lexer.getTokenType()) {
            case TEXT -> { return new TextSpan(consumeTextSpan()); }
            case OPEN_START_TAG -> {
                switch (lexer.getTokenValue()) {
                    case "a" -> { return getHyperlink(); }
                    case "table" -> { return getTable(); }
                    case "form" -> {
                    	if (!parsedForm)
                    		return getForm();  
                    	else 
                    		throw new HtmlLexer.LexerException("Illegal parse: nested Form detected");
                    	}
                    case "input" -> { return getInput(); } 
                    default -> fail();
                }
            }
            default -> fail();
        }
        throw new HtmlLexer.LexerException("Illegal parse: expected TextSpan, HyperLink, Table, form or input.");
    }
    
    /**
     * Retrieve the {@link ContentSpan} that corresponds to the 
     * next parsed {@link TextInputField} or {@link SubmitButton} (only two allowed input types)
     * @return ContentSpan:
     * 					The converted {@link ContentSpan}
     */
	ContentSpan getInput() {
        consumeOpenStartTag("input");
        String type = consumeAttribute("type");
        switch (type) {
        	case "text" -> { 
        		String name = consumeAttribute("name");
        		consumeToken(HtmlLexer.TokenType.CLOSE_TAG);
        		return new TextInputField(name);
        	}
        	case "submit" -> { 
        		consumeToken(HtmlLexer.TokenType.CLOSE_TAG);
        		return new SubmitButton(); 
        	}
        	default -> fail();
        }
        throw new HtmlLexer.LexerException("Illegal parse: expected input types are text and submit.");
    }
    
    /**
     * Retrieve the {@link ContentSpan} that corresponds to 
     * the next parsed {@link Form}
     * @return contentSpan: 
     * 					The converted {@link ContentSpan}, itself cannot be a form
     */
    Form getForm() {
    	parsedForm = true;
    	consumeOpenStartTag("form");
    	String action = consumeAttribute("action");		// extract the action of the form
    	consumeToken(HtmlLexer.TokenType.CLOSE_TAG);	
    	ContentSpan content = getContentSpan();
    	consumeEndTag("form");
    	return new Form(action, content);
    }
    
    /**
     * Retrieve the {@link ContentSpan} that corresponds to
     * the next parsed {@link HyperLink}.
     *
     * @return contentSpan:
     *                  The converted {@link ContentSpan}.
     */
    HyperLink getHyperlink() {
        consumeOpenStartTag("a");
        String href = consumeAttribute("href");
        consumeToken(HtmlLexer.TokenType.CLOSE_TAG);
        String text = consumeTextSpan();
        consumeEndTag("a");
        return new HyperLink(href, new TextSpan(text));
    }

    /**
     * Retrieve the {@link ContentSpan} that corresponds to
     * the next parsed {@link Table}.
     *
     * @return contentSpan:
     *                  The converted {@link ContentSpan}.
     */
    Table getTable() {
        Table table = new Table(new ArrayList<>());
        consumeStartTag("table");
        while (lexer.getTokenType() == HtmlLexer.TokenType.OPEN_START_TAG && lexer.getTokenValue().equals("tr"))
            table.addTableRow(getTableRow());
        consumeEndTag("table");
        return table;
    }

    /**
     * Retrieve the {@link ContentSpan} that corresponds to
     * the next parsed {@link TableRow}.
     *
     * @return contentSpan:
     *                  The converted {@link ContentSpan}.
     */
    TableRow getTableRow() {
        TableRow tableRow = new TableRow(new ArrayList<>());
        consumeStartTag("tr");
        while (lexer.getTokenType() == HtmlLexer.TokenType.OPEN_START_TAG && lexer.getTokenValue().equals("td"))
            tableRow.addTableCell(getTableCell());
        return tableRow;
    }

    /**
     * Retrieve the {@link ContentSpan} that corresponds to
     * the next parsed {@link TableCell}.
     *
     * @return contentSpan:
     *                  The converted {@link ContentSpan}.
     */
    TableCell getTableCell() {
        consumeStartTag("td");
        return new TableCell(getContentSpan());
    }
    
    /**
     * Boolean used as reference to if {@link Form} is already parsed
     */
    private boolean parsedForm;
}
