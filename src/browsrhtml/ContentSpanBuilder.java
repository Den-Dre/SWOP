package browsrhtml;

import browsrhtml.BrowsrDocumentValidator;
import domainmodel.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class ContentSpanBuilder extends BrowsrDocumentValidator {

    public ContentSpanBuilder(Reader reader) {
        super(reader);
    }

    public static ContentSpan buildContentSpan(String document) {
        return new ContentSpanBuilder(new StringReader(document)).getContentSpan();
    }

    public static ContentSpan buildContentSpan(URL url) throws IOException {
        return new ContentSpanBuilder(new BufferedReader(new InputStreamReader(url.openStream()))).getContentSpan();
    }

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

    HyperLink getHyperlink() {
        consumeOpenStartTag("a");
        String href = consumeAttribute("href");
        consumeToken(HtmlLexer.TokenType.CLOSE_TAG);
        String text = consumeTextSpan();
        consumeEndTag("a");
        return new HyperLink(href, new TextSpan(text));
    }

    Table getTable() {
        Table table = new Table(new ArrayList<>());
        consumeStartTag("table");
        while (lexer.getTokenType() == HtmlLexer.TokenType.OPEN_START_TAG && lexer.getTokenValue().equals("tr"))
            table.addTableRow(getTableRow());
        consumeEndTag("table");
        return table;
    }

    TableRow getTableRow() {
        TableRow tableRow = new TableRow(new ArrayList<>());
        consumeStartTag("tr");
        while (lexer.getTokenType() == HtmlLexer.TokenType.OPEN_START_TAG && lexer.getTokenValue().equals("td"))
            tableRow.addTableCell(getTableCell());
        return tableRow;
    }

    TableCell getTableCell() {
        consumeStartTag("td");
        return new TableCell(getContentSpan());
    }
}
