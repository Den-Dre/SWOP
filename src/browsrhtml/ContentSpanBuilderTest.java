package browsrhtml;

import domainmodel.*;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContentSpanBuilderTest {

    @Test
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
        assertNotNull(contentSpan);
        Table outerTable = ((Table) contentSpan);
        List<TableRow> outerRows = outerTable.getRows();
        assertEquals("HTML elements partially supported by Browsr:", ((TextSpan) outerRows.get(0).getCells().get(0).getContent()).getText());

        Table innerTable = (Table) outerRows.get(1).getCells().get(0).getContent();
        List<TableRow> innerRows = innerTable.getRows();

        List<TableCell> firstInnerRow = innerRows.get(0).getCells();
		assertEquals("a.html", ((HyperLink) firstInnerRow.get(0).getContent()).getHref());
		assertEquals("Hyperlink anchors", ((TextSpan) firstInnerRow.get(1).getContent()).getText());

		List<TableCell> secondInnerRow = innerRows.get(1).getCells();
		assertEquals("table.html", ((HyperLink) secondInnerRow.get(0).getContent()).getHref());
		assertEquals("Tables", ((TextSpan) secondInnerRow.get(1).getContent()).getText());

		List<TableCell> thirdInnerRow = innerRows.get(2).getCells();
		assertEquals("tr.html", ((HyperLink) thirdInnerRow.get(0).getContent()).getHref());
		assertEquals("Table rows", ((TextSpan) thirdInnerRow.get(1).getContent()).getText());

		List<TableCell> fourthInnerRow = innerRows.get(3).getCells();
		assertEquals("td.html", ((HyperLink) fourthInnerRow.get(0).getContent()).getHref());
		assertEquals("Table cells containing table data", ((TextSpan) fourthInnerRow.get(1).getContent()).getText());
    }
}
