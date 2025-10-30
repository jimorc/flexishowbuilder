package com.github.jimorc.flexishowbuilder;

/**
 * MutableBoolean class represents a boolean value that can be mutated in a method
 * containing the boolean as a parameter.
 */
public class MutableBoolean {
    private boolean value;

    /**
     * Constructor.
     * @param value the initial value for the object
     */
    public MutableBoolean(boolean value) {
        this.value = value;
    }

    /**
     * Retrieve the object's value.
     * @return the value
     */
    public boolean getValue() {
        return value;
    }

    /**
     * Set value to the parameter value.
     * @param val the value to set the object to.
     */
    public void setValue(boolean val) {
        value = val;
    }
}
