package me.thamma.DMZ.Chunkys;

/**
 * Created by pc on 14.06.2015.
 */
public enum Attribute {

    Mobspawn("false");


    private String def;

    Attribute(String def) {
        this.def = def;
    }

    public String getDefaultValue() {
        return this.def;
    }
}
