package me.thamma.DMZ.chunky;

/**
 * Created by pc on 14.06.2015.
 */
public enum Attribute {

	Mobspawn("false"), Level("1"), Invincible("false"), Name(""), SubTitle(""), Outpost("true");

	private String def;

	Attribute(String def) {
		this.def = def;
	}

	public String getDefaultValue() {
		return this.def;
	}
}
