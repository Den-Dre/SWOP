import java.awt.*;

public class AddressBar extends Frame{
    public AddressBar(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void Render(Graphics g) {
        setFontMetrics(g);
        g.setColor(this.getBackgroundColor());
        if (this.hasFocus){ g.setColor(this.highlightColor); }
        g.fillRect(this.getxPos(), this.getyPos(), this.getWidth(), this.getHeight());

        g.setColor(Color.BLACK);
        g.drawRect(this.getxPos(), this.getyPos(), this.getWidth(), this.getHeight());

        this.printURL(g);
        this.printCursor(g);
        g.setColor(this.getBackgroundColor());
    }

    private void printURL(Graphics g){
        g.setColor(textColor);
        g.setFont(font);
        g.drawString(this.getURL(), this.URLStart, this.getyPos()+this.getHeight()-(this.getHeight()/6));
    }

    private void printCursor(Graphics g) {
        g.fillRect(this.cursorPos[0], this.cursorPos[1], this.cursorDimensions[0], this.cursorDimensions[1]);
    }

    private void setFontMetrics(Graphics g) {
        this.metrics = g.getFontMetrics(font);
        this.cursorPos = new int[] {metrics.stringWidth(this.getURL().substring(0,this.cursor))+this.getxPos()+(URLStart/2), this.getyPos()};
    }

    @Override
    public void handleMouse(int id, int x, int y, int clickCount) {
        if (id != 500) return;
        if (this.wasClicked(x,y)) {
            this.toggleFocus(true);
            // Something else to do when clicked on addressbar?
        }
        else {
            this.toggleFocus(false);
            // what else to do when clicked out of addressbar?
            // go to this.getURL()
        }
    }

    private boolean wasClicked(int x, int y){
        return x >= this.getxPos() && x <= (this.getxPos() + this.getWidth()) && y >= this.getyPos() && y <= (this.getyPos() + this.getHeight());
    }

    @Override
    public void handleKey(int id, int keyCode, char keyChar) {
        if (!this.hasFocus) return;
        if (id != 401) return;
        switch (keyCode) {
            case 27 -> handleEscape();
            case 10 -> handleEnter();
            case 8 -> handleBackSpace();
            case 35 -> moveCursor(this.getURL().length());
            case 36 -> moveCursor(-this.getURL().length());
            case 37 -> moveCursor(-1);
            case 39 -> moveCursor(1);
            default -> handleNoSpecialKey(id, keyCode, keyChar);
        }
    }

    private void handleEscape() {
        this.toggleFocus(false);
        this.setURL(this.getOldUrl());
    }

    private void handleEnter() {
        this.toggleFocus(false);
        this.updateCopyUrl();
        // navigate to this.getURL()
    }

    private void handleBackSpace() {
        StringBuilder newUrl = new StringBuilder(this.getURL());
        int deleteindex = newUrl.length()-1;
        if (deleteindex >= 0)
            newUrl.deleteCharAt(deleteindex); // <- has to be at cursor location not at end
        this.setURL(newUrl.toString());
    }

    private void handleNoSpecialKey(int id, int keyCode, char keyChar) {
        if ((keyCode >= 44 && keyCode <= 111) || (keyCode >= 512 && keyCode <= 523)) {
            StringBuilder newUrl = new StringBuilder(this.getURL());
            newUrl.insert(newUrl.length(), keyChar);
            this.setURL(newUrl.toString());
            this.moveCursor(1);
        }
    }

    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        this.setWidth(newWindowWidth-10);
    }

    //Two URL's to be able to 'rollback' the old url if editing is cancelled.
    // URL is the variable to be edited. URLCopy gets updated after it is certain the edited url is final.
    private String URL = "";
    private String URLCopy = URL;

    private Color highlightColor = Color.BLUE;
    private Color textColor = Color.BLACK;
    private int URLStart = this.getxPos()+5;

    private int cursor = 0;
    private int[] cursorDimensions = new int[] {2,this.getHeight()};
    private int[] cursorPos = new int[] {this.getxPos() + (URLStart/2), this.getyPos()};

    Font font = new Font(Font.DIALOG_INPUT, Font.PLAIN, this.getHeight());
    FontMetrics metrics;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    private String getOldUrl() {
        return this.URLCopy;
    }

    private void updateCopyUrl() {
        this.URLCopy = this.URL;
    }

    // move the cursor delta index positions in the url
    // it cannot move further than the length of the url (both left and right)
    private void moveCursor(int delta) {
        int urllength = this.getURL().length();
        int newCursor = this.cursor + delta;
        if (newCursor > urllength) newCursor = urllength;
        if (newCursor < 0) newCursor = 0;
        this.cursor = newCursor;
    }

//    private int[] getCursorPos() {
//    }
}
