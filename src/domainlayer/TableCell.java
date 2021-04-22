package domainlayer;

/**
 * A class to represent TableCell's in the domain layer.
 */
public class TableCell {
    /**
     * The content that is placed in this TableCell.
     * This can either recursively express a structure of
     * {@link Table}'s, or be a single {@link TextSpan}.
     */
    private final ContentSpan content;

    /**
     * Initialise this TableCell with the given content.
     *
     * @param content:
     *              The content this TableCell should contain.
     */
    public TableCell(ContentSpan content) {
        this.content = content;
    }

    /**
     * Retrieve the content this TableCell contains.
     *
     * @return content:
     *              The content this TableCell contains.
     */
    public ContentSpan getContent() {
        return content;
    }
}
