package browsrhtml;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import browsrhtml.HtmlLexer.TokenType;

class HtmlLexerTest {

	HtmlLexer lexer;
	
	void assertToken(TokenType tokenType, String tokenValue) throws IOException {
		assertEquals(tokenType, lexer.getTokenType());
		assertEquals(tokenValue, lexer.getTokenValue());
		lexer.eatToken();
	}
	
	@Test
	void test() throws IOException {
		String input = """
				<a>
				  <b x="foo"><cc xx="foo" yy="baz">Some text</c></b>
				</a>
				""";
		lexer = new HtmlLexer(new StringReader(input));
		assertToken(TokenType.OPEN_START_TAG, "a");
		assertToken(TokenType.CLOSE_TAG, "");
		assertToken(TokenType.OPEN_START_TAG, "b");
		assertToken(TokenType.IDENTIFIER, "x");
		assertToken(TokenType.EQUALS, "");
		assertToken(TokenType.QUOTED_STRING, "foo");
		assertToken(TokenType.CLOSE_TAG, "");
		assertToken(TokenType.OPEN_START_TAG, "cc");
		assertToken(TokenType.IDENTIFIER, "xx");
		assertToken(TokenType.EQUALS, "");
		assertToken(TokenType.QUOTED_STRING, "foo");
		assertToken(TokenType.IDENTIFIER, "yy");
		assertToken(TokenType.EQUALS, "");
		assertToken(TokenType.QUOTED_STRING, "baz");
		assertToken(TokenType.CLOSE_TAG, "");
		assertToken(TokenType.TEXT, "Some");
		assertToken(TokenType.TEXT, "text");
		assertToken(TokenType.OPEN_END_TAG, "c");
		assertToken(TokenType.CLOSE_TAG, "");
		assertToken(TokenType.OPEN_END_TAG, "b");
		assertToken(TokenType.CLOSE_TAG, "");
		assertToken(TokenType.OPEN_END_TAG, "a");
		assertToken(TokenType.CLOSE_TAG, "");
		assertToken(TokenType.END_OF_FILE, "");
	}

}
