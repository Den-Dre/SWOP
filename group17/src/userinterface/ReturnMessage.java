package userinterface;

import java.util.ArrayList;

/**
 * A class to represent a {@code ReturnMessage}.
 *
 * <p>This is a message that's returned by
 * classes in the UI-layer upon being
 * clicked. Different classes return
 * different types of return messages,
 * such that classes higher up in the
 * hierarchy can differentiate between
 * the different elements and decide what
 * action needs to be taken when a user clicks
 * a particular area of the application.</p>
 */
public class ReturnMessage {
    /**
     * Create a new ReturnMessage with the given parameters.
     *
     * @param type: the type of the new ReturnMessage.
     * @param content: the content of the new ReturnMessage.
     */
    public ReturnMessage(Type type, String content) {
        this.type = type;
        this.content = content;
    }

    /**
     * Create a new ReturnMessage with the given type.
     * 
     * The content will be set to the String "".
     * @param type: The desired type of the new ReturnMessage.
     */
    public ReturnMessage(Type type) {
        this.type = type;
        this.content = "";
    }
    
    /**
     * Create a new ReturnMessage with the given parameters
     * 
     * @param type: the desired type of the new ReturnMessage
     * @param content: the content of the new ReturnMessage
     * @param contentList: the list of contents of the new ReturnMessage
     */
    public ReturnMessage(Type type, String content, ArrayList<String> contentList) {
        this.type = type;
        this.content = content;
        this.contentList = contentList;
    }

    /**
     * An enumeration of the possible types of ReturnMessages.
     */
    enum Type {
        Empty,
        Hyperlink,
        Button,
        Form
    }

    /**
     * Get the content of this ReturnMessage.
     *
     * @return String: the content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Get the type of this ReturnMessage.
     *
     * @return enum Type: the type.
     */
    public Type getType() {
        return type;
    }
    
    /**
     * Get the list of contents of this ReturnMessage
     * 
     * @return ArrayList<String> contentList: 
     * 			the list of contents
     */
    public ArrayList<String> getContentList() {
        return contentList;
    }
    
    /**
     * {@link} String holding the content of this ReturnMessage 
     */
    private final String content;
    
    /**
     * {@link Type} holding the type of this ReturnMessage
     */
    private final Type type;
    
    /**
     * {@link ArrayList} holding 
     * the contents of this ReturnMessage
     */
    private ArrayList<String> contentList;
}
