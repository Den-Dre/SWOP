import java.awt.*;
import java.util.ArrayList;

public class UITable extends DocumentCell{
    public UITable(int x, int y, int width, int height, Object[] rows) {
        super(x, y, width, height);
        initRows(rows);
    }

    @Override
    public void Render(Graphics g) {
//        for (Object row : rows){
//            //row.Render();
//        }
    }

    @Override
    public String getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        String result = "";
//        for (Object row : rows) {
//            /*
//            result = row.getHandleMouse(id, x, y, clickCount, button, modifier);
//            if (!result.equals("")) break;
//             */
//        }
        return result;
    }

    private void initRows(Object[] rows) {
        //this.rows = rows;
    }

    private ArrayList<ArrayList<DocumentCell>> grid;

}
