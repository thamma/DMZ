package me.thamma.DMZ.custom;

public class MyEnchantment {

	private MyEnchantmentType e;
	private int l;

	public MyEnchantment(MyEnchantmentType arg0, int arg1) {
		this.e = arg0;
		this.l = arg1;
	}

	@Override
	public String toString() {
		return "" + this.getType().getColor() + (this.getLevel() >= 0 ? "+" : "-") + this.getLevel() + " "
				+ this.getType().getDisplayName();
	}

	public MyEnchantmentType getType() {
		return this.e;
	}

	public int getLevel() {
		return this.l;
	}

}