package domainmodel;

/**
 * A class in the domain layer to represent the
 * {@link ContentSpan}'s text fields that are just text fields.
 */
public class TextSpan extends ContentSpan {
    /**
     * The text contained in this TextSpan.
     */
    private final String text;

    /**
     * Initialise a TextSpan with the given string of text.
     *
     * @param text:
     *            the text that this TextSpan should contain.
     */
    public TextSpan(String text) {
        this.text = text;
    }

    /**
     * Retrieve the text that is contained within this TextSpan.
     *
     * @return text:
     *              the text contained in this TextSpan.
     */
    public String getText() {
        return text;
    }
}
