package UserInterface;

public class ReturnMessage {
    public ReturnMessage(Type type, String content) {
        this.type = type;
        this.content = content;
    }

    public ReturnMessage(Type type) {
        this.type = type;
        this.content = "";
    }

    enum Type {
        Empty,
        Hyperlink,
        Button,
        Form
    }

    public String getContent() {
        return content;
    }

    public Type getType() {
        return type;
    }

    private final String content;
    private final Type type;
}
