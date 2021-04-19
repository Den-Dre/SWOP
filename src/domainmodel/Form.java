package domainmodel;

public class Form extends ContentSpan {
	
	private String action;
	private ContentSpan content;
	
	public Form(String action, ContentSpan content) {
		this.action = action;
		this.content = content;
	} 
	
	public String getAction() {
		return this.action;
	}

	public ContentSpan getContent() {
		return content;
	}
}
