package browsrhtml;

import java.io.IOException;
import java.io.Reader;

public class HtmlLexer {
	
	public static class LexerException extends RuntimeException {
		public LexerException(String message) {
			super(message);
		}
	}
	
	public enum TokenType {
		TEXT,
		OPEN_START_TAG,
		OPEN_END_TAG,
		CLOSE_TAG,
		IDENTIFIER,
		EQUALS,
		QUOTED_STRING,
		END_OF_FILE
	}
	
	private Reader reader;
	private int c;
	private boolean insideTag;
	private TokenType tokenType;
	private StringBuilder tokenValue = new StringBuilder();
	
	public String getTokenValue() { return tokenValue.toString(); }
	
	public HtmlLexer(Reader reader) {
		this.reader = reader;
		eatChar();
		eatToken();
	}
	
	private void error(String message) {
		throw new LexerException(message);
	}
	
	private void eatChar() {
		try {
			c = reader.read();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void appendChar() {
		tokenValue.append((char)c);
		eatChar();
	}
	
	public void eatToken() {
		tokenType = nextToken();
	}
	
	public TokenType getTokenType() { return tokenType; }
	
	private TokenType nextToken() {
		for (;;) {
			tokenValue.setLength(0);
			if (insideTag) {
				switch (c) {
				case ' ':
				case '\n':
				case '\r':
				case '\t':
					eatChar();
					continue;
				case -1:
					return TokenType.END_OF_FILE;
				case '>':
					eatChar();
					insideTag = false;
					return TokenType.CLOSE_TAG;
				case '=':
					eatChar();
					return TokenType.EQUALS;
				case '"':
					eatChar();
					for (;;) {
						switch (c) {
						case -1:
							error("End of file inside quoted string");
						case '"':
							eatChar();
							return TokenType.QUOTED_STRING;
						default:
							appendChar();
						}
					}
				default:
					for (;;) {
						appendChar();
						switch (c) {
						case -1:
						case ' ':
						case '\n':
						case '\r':
						case '\t':
						case '=':
						case '>':
							return TokenType.IDENTIFIER;
						}
					}
				}
			} else {
				switch (c) {
				case ' ':
				case '\n':
				case '\r':
				case '\t':
					eatChar();
					continue;
				case -1:
					return TokenType.END_OF_FILE;
				case '<':
					insideTag = true;
					eatChar();
					TokenType tt;
					if (c == '/') {
						eatChar();
						tt = TokenType.OPEN_END_TAG;
					} else
						tt = TokenType.OPEN_START_TAG;
					for (;;) {
						switch (c) {
						case -1:
						case ' ':
						case '\n':
						case '\r':
						case '\t':
						case '>':
							return tt;
						}
						appendChar();
					}
				default:
					for (;;) {
						appendChar();
						switch (c) {
						case -1:
						case ' ':
						case '\n':
						case '\r':
						case '\t':
						case '<':
							return TokenType.TEXT;
						}
					}
				}
			}
		}
	}

}
