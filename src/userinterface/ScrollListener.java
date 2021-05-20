package userinterface;

/**
 * An interface for ScrollListener.
 * 
 * This interface should be implemented by
 * all classes that should be notified when
 * a linked scroll bar (through {@link DocumentCellDecorator})
 * is moved.
 */
public interface ScrollListener {
    /**
     * Define what the class that implements
     * this  {@link ScrollListener} Interface
     * should do when a horizontal scrollbar is moved.
     */
	void horizontalScrolled();
	
    /**
     * Define what the class that implements
     * this  {@link ScrollListener} Interface
     * should do when a vertical scrollbar is moved.
     */
    void verticalScrolled();
}
