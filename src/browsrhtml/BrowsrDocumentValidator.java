package browsrhtml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;

import browsrhtml.HtmlLexer.TokenType;

public class BrowsrDocumentValidator {
	
	HtmlLexer lexer;
	
	BrowsrDocumentValidator(Reader reader) {
		lexer = new HtmlLexer(reader);
	}
	
	void eatToken() { lexer.eatToken(); }
	
	void fail() {
		throw new RuntimeException("The given document is not a valid Browsr document.");
	}
	
	void assertTrue(boolean b) {
		if (!b)
			fail();
	}
	
	void assertToken(TokenType tokenType) {
		assertTrue(lexer.getTokenType() == tokenType);
	}
	
	void assertTokenValue(String value) {
		assertTrue(lexer.getTokenValue().equals(value));
	}
	
	String consumeToken(TokenType tokenType) {
		assertToken(tokenType);
		String result = lexer.getTokenValue();
		eatToken();
		return result;
	}
	
	void consumeToken(TokenType tokenType, String tokenValue) {
		assertToken(tokenType);
		assertTokenValue(tokenValue);
		eatToken();
	}
	
	void consumeOpenStartTag(String tagName) {
		consumeToken(TokenType.OPEN_START_TAG, tagName);
	}
	
	String consumeAttribute(String attributeName) {
		consumeToken(TokenType.IDENTIFIER, attributeName);
		consumeToken(TokenType.EQUALS);
		return consumeToken(TokenType.QUOTED_STRING);
	}
	
	void consumeBrowsrDocument() {
		consumeContentSpan();
		assertToken(TokenType.END_OF_FILE);
	}
	
	String consumeTextSpan() {
		// When interpreting a text span as a single text string,
		// whitespace between text tokens is replaced by a single space character.
		StringBuilder text = new StringBuilder();
		boolean first = true;
		while (lexer.getTokenType() == TokenType.TEXT) {
			if (first)
				first = false;
			else
				text.append(' ');
			text.append(lexer.getTokenValue());
			eatToken();
		}
		return text.toString();
	}
	
	void consumeEndTag(String tagName) {
		consumeToken(TokenType.OPEN_END_TAG, tagName);
		consumeToken(TokenType.CLOSE_TAG);
	}
	
	void consumeHyperlink() {
		consumeOpenStartTag("a");
		consumeAttribute("href");
		consumeToken(TokenType.CLOSE_TAG);
		consumeTextSpan();
		consumeEndTag("a");
	}
	
	void consumeStartTag(String tagName) {
		consumeOpenStartTag(tagName);
		consumeToken(TokenType.CLOSE_TAG);
	}
	
	void consumeTableCell() {
		consumeStartTag("td");
		consumeContentSpan();
	}
	
	void consumeTableRow() {
		consumeStartTag("tr");
		while (lexer.getTokenType() == TokenType.OPEN_START_TAG && lexer.getTokenValue().equals("td"))
			consumeTableCell();
	}
	
	void consumeTable() {
		consumeStartTag("table");
		while (lexer.getTokenType() == TokenType.OPEN_START_TAG && lexer.getTokenValue().equals("tr"))
			consumeTableRow();
		consumeEndTag("table");
	}
	
	void consumeContentSpan() {
		switch (lexer.getTokenType()) {
		case TEXT -> consumeTextSpan();
		case OPEN_START_TAG -> {
			switch (lexer.getTokenValue()) {
			case "a" -> consumeHyperlink();
			case "table" -> consumeTable();
			default -> fail();
			}
		}
		default -> fail();
		}
	}
	
	public static void assertIsValidBrowsrDocument(Reader reader) {
		new BrowsrDocumentValidator(reader).consumeBrowsrDocument();
	}
	
	public static void assertIsValidBrowsrDocument(String document) {
		new BrowsrDocumentValidator(new StringReader(document)).consumeBrowsrDocument();
	}
	
	public static void assertIsValidBrowsrDocument(URL url) throws IOException {
		new BrowsrDocumentValidator(new BufferedReader(new InputStreamReader(url.openStream()))).consumeBrowsrDocument();
	}
	
	

}
