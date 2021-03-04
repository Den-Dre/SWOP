package domainmodel;

public class HyperLink extends ContentSpan {
    private String href;
    private TextSpan text;

    public HyperLink(String href, TextSpan text) {
        this.href = href;
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public TextSpan getText() {
        return text;
    }
}
