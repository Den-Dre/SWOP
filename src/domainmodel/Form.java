package domainmodel;

/**
 * A class to represent an abstract form.
 */
public class Form extends ContentSpan {
	/**
	 * A {@link String} to hold the action of this Form
	 */
	private String action;
	
	/**
	 * A {@link ContentSpan} to hold the content of this Form
	 */
	private ContentSpan content;
	
    /**
     * Initialize a new Form given an action and content
     * @param action: 
     * 		A {@link String} representing the action of this Form
     * @param content:
     * 		A {@link ContentSpan} representing the content of this Form
     */
	public Form(String action, ContentSpan content) {
		this.action = action;
		this.content = content;
	} 
	
	/**
	 * Retrieve the action this Form.
	 * 
	 * @return action: 
	 * 			The action of this Form
	 */
	public String getAction() {
		return this.action;
	}

	/**
	 * Retrieve the content of this Form.
	 * 
	 * @return name: 
	 * 			The name of this Form
	 */
	public ContentSpan getContent() {
		return content;
	}
}
