package userinterface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.swing.SwingUtilities;

/**
 * A class to represent a hyperlink in the UI layer.
 */
public class UITextHyperlink extends DocumentCell {
    /**
     * Construct a new {@code UITextHyperlink}.
     *
     * @param x        : x position on the window
     * @param y        : y position of the window
     * @param width    : the width of this {@code UIHyperlink}
     * @param link_size: The height of this {@code UIHyperlink}
     * @param text     : The value of the href attribute of this {@code UIHyperlink}
     * @throws IllegalDimensionException : When negative dimensions are provided.
     */
    public UITextHyperlink(int x, int y, int width, int link_size, String text) throws IllegalDimensionException {
        super(x, y, width, link_size);
        
        // initialize the text attribute
        this.text = text;
        
        // initialize the text attribute as an AttributedString
        this.attributedText = new AttributedString(text);
        this.attributedText.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        textHeight = link_size;
        
        // TODO: Decide whether the following two calls can be omitted in this class's constructor:
        updateSizes();
        setWidth(getWidth());
    }

    /**
     * Renders the link onto the window.
     * -> Update information about the dimensions of the string
     * -> Set the color
     * -> Underline the text
     *
     * @param g: The graphics that will be updated
     */
    @Override
    public void Render(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
        metrics = g2d.getFontMetrics(hyperlinkFont);
        
        updateSizes();
        
        g2d.setColor(Color.BLUE);
        g2d.drawString(attributedText.getIterator(), getxPos(), getyPos() + textHeight);
       
        
        // Draw a rectangle around the text for debugging purposes
        g2d.draw(new Rectangle2D.Double(getxPos(), getyPos(), textWidth, getHeight()));
             
    }
        
    
    /**
     * Update the {@code textWidth} based on this {@code UIHyperlink} {@code href} attribute.
     */
    private void updateSizes() {
    	// still use the old method when metrics is yet to be defined
    	if (metrics == null) {
    		textWidth = (int) (textHeight*(text.length())*heightToWidthRatio);
    		return;
    	}
    	
    	// width is calculated based on the AttributedString variant of 
    	// the text attribute to account for being underlined!
        TextLayout textLayout = new TextLayout( 
                attributedText.getIterator(), 
                metrics.getFontRenderContext() 
        );
        Rectangle2D.Float textBounds = ( Rectangle2D.Float ) textLayout.getBounds();
        
        textWidth = (int) textBounds.getWidth();
    }

    /**
     * Input: a mouse click
     * Output: String
     * -> The method only checks if the click was on itself, other checks have been done upstream
     * -> Returns the href if this {@code UIHyperlink} was pressed, else "".
     *
     * @param id: The type of mouse action
     * @param x: The x coordinate of the mouse action.
     * @param y: The y coordinate of the mouse action.
     * @param clickCount: The number of times the mouse has clicked.
     * @param button: The mouse button that was clicked.
     * @param modifier: Possible other keys that were pressed during this mouse action.
     * @return returnMessage: a {@link ReturnMessage} of the {@link ReturnMessage.Type} {@code text} based on
     *                        the text attribute of this {@code UITextHyperlink} if this {@code UITextHyperlink}
     *                        was clicked, a {@link ReturnMessage} of the {@link ReturnMessage.Type} {@code Empty} otherwise.
     */
    @Override
    public ReturnMessage getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        if (id == MouseEvent.MOUSE_RELEASED) {
            if (wasClicked(x, y))
                return new ReturnMessage(ReturnMessage.Type.Hyperlink, getText());
        }
        return new ReturnMessage(ReturnMessage.Type.Empty);
    }

    /**
     * Returns the max height of this {@code UIHyperlink}, which is the width of the string.
     */
    @Override
    public int getMaxHeight() {
        return textHeight; // the +3 can be deleted but what it does is account for the extra height from the underlining.
    }

    /**
     * Returns the max width of this {@code UIHyperlink}, which is the width of the string
     */
    @Override
    public int getWidth() {
        return textWidth;
    }
  
    /**
     * Retrieve the text displayed in this UIHyperlink
     *
     * @return text:
     *              The text displayed in this UIHyperlink.
     */
    public String getText() {
        return this.text;
    }

    // =========== Contents of this UIHyperlink =============
    /**
     * A AttributedString variable to denote the text value of this UIHyperlink.
     */
    private final String text;
    
    private final AttributedString attributedText;

    // ============== Dimension variables ====================
    /**
     * An integer variable to denote the height of the text of this UIHyperlink.
     */
    private final int textHeight;
    /**
     * An integer variable to denote the width of the text of this UIHyperlink.
     */
    private int textWidth;

    // ============== Font variables ===========================
    /**
     * A variable to denote the {@link Font} of the text of this UIHyperlink
     */
    private final Font hyperlinkFont = new Font(Font.SANS_SERIF, Font.PLAIN, getHeight());
    /**
     * A variable to denote the {@link FontMetrics} of the text of this UIHyperlink.
     */
    private FontMetrics metrics;
    
    /**
     * A variable to denote a magic multiplier to return a more accurate textWidth of the text of this UIHyperlink
     */
    private final double magicMultiplier = 1.256;
}

