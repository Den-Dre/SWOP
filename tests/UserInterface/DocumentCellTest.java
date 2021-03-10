package UserInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DocumentCell")
class DocumentCellTest {

    int x = 0;
    int y = 0;
    int width = 100;
    int height = 100;
    private DocumentCell cell;

    @BeforeEach
    void setup() throws Exception {
        cell = new DocumentCell(x,y,width,height);
    }

    @Test
    @DisplayName("can determine if it was clicked")
    void wasClicked() {
        // no click on the cell
        assertFalse(cell.wasClicked(10,110));
        assertFalse(cell.wasClicked(110,10));
        // click on the cell
        assertTrue(cell.wasClicked(10,10));
    }
}