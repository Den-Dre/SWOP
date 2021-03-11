package domainmodel;

/**
 * A class to represent Hyperlinks in the domain layer.
 */
public class HyperLink extends ContentSpan {
    private String href;
    private TextSpan textSpan;

    /**
     * Create a HyperLink with the given parameters.
     * @param href:
     *            the href attribute of this HyperLink.
     * @param text:
     *            the TextSpan associated with this HyperLink.
     *
     */
    public HyperLink(String href, TextSpan text) {
        this.href = href;
        this.textSpan = text;
    }

    /**
     * Get the href string of this HyperLink.
     *
     * @return href:
     *              the href attribute of this HyperLink.
     */
    public String getHref() {
        return href;
    }

    /**
     * Get the {@link TextSpan} associated with this HyperLink.
     *
     * @return textSpan:
     *              the {@link TextSpan} associated with this HyperLink.
     */
    public TextSpan getTextSpan() {
        return textSpan;
    }
}
