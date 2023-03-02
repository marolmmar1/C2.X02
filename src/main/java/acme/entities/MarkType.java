
package acme.entities;

public enum MarkType {

	Aplus("A+"), A("A"), B("B"), C("C"), F("F"), Fminus("F-");


	private String name;


	MarkType(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
