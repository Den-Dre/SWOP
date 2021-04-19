package domainmodel;

public class TextInputField extends ContentSpan {
	private String name;
	
	public TextInputField(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
