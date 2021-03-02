package domainmodel;

public class HyperLink extends ContentSpan {
    private TextSpan href;

    public HyperLink(TextSpan href) {
        this.href = href;
    }
}
