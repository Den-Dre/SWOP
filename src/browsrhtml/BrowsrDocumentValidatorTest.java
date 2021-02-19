package browsrhtml;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;

class BrowsrDocumentValidatorTest {

	@Test
	void testWithString() {
		BrowsrDocumentValidator.assertIsValidBrowsrDocument("""
				<table>
				  <tr><td>HTML elements partially supported by Browsr:
				  <tr><td>
				    <table>
				      <tr><td><a href="a.html">a</a><td>Hyperlink anchors
				      <tr><td><a href="table.html">table</a><td>Tables
				      <tr><td><a href="tr.html">tr</a><td>Table rows
				      <tr><td><a href="td.html">td</a><td>Table cells containing table data
				    </table>
				</table>
				""");
	}
	
	@Test
	void testWithURL() throws MalformedURLException, IOException {
		BrowsrDocumentValidator.assertIsValidBrowsrDocument(new URL(new URL("https://people.cs.kuleuven.be/~bart.jacobs/index.html"), "browsrtest.html"));
	}

}
