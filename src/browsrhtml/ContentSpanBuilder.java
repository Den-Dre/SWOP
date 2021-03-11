package browsrhtml;

import domainmodel.*;

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
                    default -> fail();
                }
            }
            default -> fail();
        }
        throw new HtmlLexer.LexerException("Illegal parse: expected TextSpan, HyperLink or Table.");
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
}
