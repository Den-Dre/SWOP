package domainmodel;

public class HyperLink extends ContentSpan {
    private String href;
    private TextSpan textSpan;

    public HyperLink(String href, TextSpan text) {
        this.href = href;
        this.textSpan = text;
    }

    public String getHref() {
        return href;
    }

    public TextSpan getTextSpan() {
        return textSpan;
    }
}
