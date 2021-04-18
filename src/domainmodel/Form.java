package domainmodel;

public class Form extends ContentSpan {
	
	private String action;
	private ContentSpan content;
	
	public Form(String action, ContentSpan content) {
		System.out.println("domain: FORM made");
		System.out.println("action: " + action);
		System.out.println("content: " + content);
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
