package domainlayer;

/**
 * A class to represent an abstract text input field (e.g. in a form)
 */
public class TextInputField extends ContentSpan {
	/**
	 * A {@link String} to hold the name of this TextInputField
	 */
	private String name;
	
    /**
     * Initialize a new TextInputField given a name
     * @param name: A {@link String} representing the name of this TextInputField
     */
	public TextInputField(String name) {
		this.name = name;
	}
	
	/**
	 * Retrieve the name of this TextInputField.
	 * 
	 * @return name: 
	 * 			The name of this TextInputField
	 */
	public String getName() {
		return name;
	}
}
