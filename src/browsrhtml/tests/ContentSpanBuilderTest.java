package browsrhtml.tests;

import browsrhtml.ContentSpanBuilder;
import domainmodel.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContentSpanBuilderTest {

    @Test
	@DisplayName("Load document based on given HTML code")
    void testWithString() {
        ContentSpan contentSpan = ContentSpanBuilder.buildContentSpan("""
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
        verifyContents(contentSpan);
    }

    @Test
	@DisplayName("Load document based on given URL")
	void testWithURL() throws IOException {
		URL url = new URL(new URL("https://people.cs.kuleuven.be/~bart.jacobs/index.html"), "browsrtest.html");
    	ContentSpan contentSpan = ContentSpanBuilder.buildContentSpan(url);
    	verifyContents(contentSpan);
	}
    
    @Test
    @DisplayName("Load document based on given URL containing a form!")
    void testFormWithURL() throws IOException {
    	URL url = new URL("file:/home/jakob/formTest.html");
    	ContentSpan contentSpan = ContentSpanBuilder.buildContentSpan(url);
    	verifyContentsForm(contentSpan);
    }

	public static void verifyContents(ContentSpan contentSpan) {
		assertNotNull(contentSpan);
		Table outerTable = ((Table) contentSpan);
		List<TableRow> outerRows = outerTable.getRows();
		assertEquals("HTML elements partially supported by Browsr:", ((TextSpan) outerRows.get(0).getCells().get(0).getContent()).getText());

		Table innerTable = (Table) outerRows.get(1).getCells().get(0).getContent();
		List<TableRow> innerRows = innerTable.getRows();

		List<TableCell> firstInnerRow = innerRows.get(0).getCells();
		assertEquals("a.html", ((HyperLink) firstInnerRow.get(0).getContent()).getHref());
		assertEquals("a", ((HyperLink) firstInnerRow.get(0).getContent()).getTextSpan().getText());
		assertEquals("Hyperlink anchors", ((TextSpan) firstInnerRow.get(1).getContent()).getText());

		List<TableCell> secondInnerRow = innerRows.get(1).getCells();
		assertEquals("table.html", ((HyperLink) secondInnerRow.get(0).getContent()).getHref());
		assertEquals("table", ((HyperLink) secondInnerRow.get(0).getContent()).getTextSpan().getText());
		assertEquals("Tables", ((TextSpan) secondInnerRow.get(1).getContent()).getText());

		List<TableCell> thirdInnerRow = innerRows.get(2).getCells();
		assertEquals("tr.html", ((HyperLink) thirdInnerRow.get(0).getContent()).getHref());
		assertEquals("tr", ((HyperLink) thirdInnerRow.get(0).getContent()).getTextSpan().getText());
		assertEquals("Table rows", ((TextSpan) thirdInnerRow.get(1).getContent()).getText());

		List<TableCell> fourthInnerRow = innerRows.get(3).getCells();
		assertEquals("td.html", ((HyperLink) fourthInnerRow.get(0).getContent()).getHref());
		assertEquals("td", ((HyperLink) fourthInnerRow.get(0).getContent()).getTextSpan().getText());
		assertEquals("Table cells containing table data", ((TextSpan) fourthInnerRow.get(1).getContent()).getText());
	}
	
	public static void verifyContentsForm(ContentSpan contentSpan) {
		assertNotNull(contentSpan);
		Form outerForm = ((Form) contentSpan);
		assertEquals("browsrformactiontest.php", outerForm.getAction());
		
		ContentSpan content = outerForm.getContent();
		assertTrue(content instanceof Table);
		List<TableRow> outerRows = ((Table) content).getRows();
		assertEquals("List words from the Woordenlijst Nederlandse Taal", ((TextSpan) outerRows.get(0).getCells().get(0).getContent()).getText());
		
		Table innerTable = (Table) outerRows.get(1).getCells().get(0).getContent();
		List<TableRow> innerRows = innerTable.getRows();
		
		List<TableCell> firstInnerRow = innerRows.get(0).getCells();
		assertEquals("Starts with:", ((TextSpan) firstInnerRow.get(0).getContent()).getText());
		assertEquals("starts_with", ((TextInputField) firstInnerRow.get(1).getContent()).getName());
		
		List<TableCell> secondInnerRow = innerRows.get(1).getCells();
		assertEquals("Max. results:", ((TextSpan) secondInnerRow.get(0).getContent()).getText());
		assertEquals("max_nb_results", ((TextInputField) secondInnerRow.get(1).getContent()).getName());
		
		SubmitButton button = (SubmitButton) outerRows.get(2).getCells().get(0).getContent();
		
	}
}
