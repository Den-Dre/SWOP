package domainmodel;

public class TextInputField extends ContentSpan {
	private String name;
	
	public TextInputField(String name) {
		System.out.println("domain: TextInput made");
		System.out.println("name: " + name);
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
