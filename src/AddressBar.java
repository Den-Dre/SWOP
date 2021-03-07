import domainmodel.UrlListener;

import java.awt.*;
import java.net.URL;

public class AddressBar extends Frame implements UrlListener {
    public AddressBar(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void Render(Graphics g) {
        g.setColor(this.getBackgroundColor());
        if (this.hasFocus){ g.setColor(this.highlightColor); }
        g.fillRect(this.getxPos(), this.getyPos(), this.getWidth(), this.getHeight());

        this.printURL(g);
        g.setColor(this.getBackgroundColor());
    }

    private void printURL(Graphics g){
        g.setColor(textColor);
        g.setFont(font);
        g.drawString(this.getURL(), this.URLStart, this.getHeight()-10);
    }

    @Override
    public void handleMouse(int id, int x, int y, int clickCount) {
        if (this.wasClicked(x,y)) {
            this.toggleFocus(true);
            // Something else to do when clicked on addressbar?
        }
        else {
            this.toggleFocus(false);
            // what else to do when clicked out of addressbar?
        }
    }

    private boolean wasClicked(int x, int y){
        return x >= this.getxPos() && x <= (this.getxPos() + this.getWidth()) && y >= this.getyPos() && y <= (this.getyPos() + this.getHeight());
    }

    @Override
    public void handleKey(int id, int keyCode, char keyChar) {
        if (!this.hasFocus) return;
        if (id != 401) return;
        switch (keyCode){
            case 27: System.out.println("Escape pressed");
            case 32: System.out.println("Space pressed");
            case 10: System.out.println("Enter pressed");
            default: System.out.println(keyChar);
        }
    }

    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        this.setWidth(newWindowWidth);
    }

    // TODO check when controller is present

    /**
     * Signifies when the URL of a document has changed
     */
    public void URLChanged(java.net.URL aUrl) {
        // String newUrl = this.uiController.getUrl();
        this.URL = aUrl.toString(); // for testing
    }

    public void contentChanged() {

    }


    //overzichtje van keys:
    /*
    id: 400==key_typed,401==key_pressed
    keyCode:
        Esc=27
        Space=32
        Backspace=8
        Enter=10
        Shift=16
        Left, up, right, down=37.38.39.40
     */

    //Two URL's to be able to 'rollback' the old url if editing is cancelled.
    // URL is the variable to be edited. URLCopy gets updated after it is certain the edited url is final.
    private String URL = "https://helloworld.com";
    private String URLCopy = URL;

    private Color highlightColor = Color.BLUE;
    private Color textColor = Color.BLACK;
    private int URLStart = this.getxPos()+5;
    private int Cursor = 0;
    Font font = new Font(Font.DIALOG_INPUT, Font.PLAIN, this.getHeight()-10);
    FontMetrics metrics;

    public String getURL() {
        return URL;
    }
}
