package com.github.jimorc.flexishowbuilder;

import java.util.ArrayList;

/**
 * The CSVLine class stores each field of a CSV line.
 */
public class CSVLine {
    private String[] fields;
    private ArrayList<Exception> exceptions;

    /**
     * Constructor - creates an empty CSVLine object.
     */
    public CSVLine() {
        fields = new String[0];
        exceptions = new ArrayList<Exception>(0);
    }

    /**
     * Returns the number of fields in the object.
     * @return the number of fields in the CSV line that was used to create the
     * CSVLine object.
     */
    public int length() {
        return fields.length;
    }

    /**
     * Returns the field specified by the argument.
     * @param index - the index of the field to return
     * @return the field specified by the index argument
     * @throws ArrayIndexOutOfBoundsException if the index is negative, or
     * greater than the number of fields.
     */
    public String field(int index) {
        return fields[index];
    }

    /**
     * Adds a field to the end of the CSVLine.
     * @param field
     */
    public void addField(String field) {
        String[] newFields = new String[fields.length + 1];
        for (int i = 0; i < fields.length; i++) {
            newFields[i] = fields[i];
        }
        newFields[fields.length] = field;
        fields = newFields;
    }

    /**
     * Adds multiple fields to the end of the CSVLine.
     * @param newFields - the fields to add
     */
    public void addFields(String[] newFields) {
        for (String field : newFields) {
            addField(field);
        }
    }

    /**
     * toString returns a string representation of the CSVLine.
     * @return String representing the contents of the CSVLine object.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < fields.length; i++) {
            sb.append(fields[i]);
            if (i < fields.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * Sets an exception associated with this CSVLine.
     * @param e - the exception to set
     */
    public void setException(Exception e) {
        this.exceptions.add(e);
    }

    /**
     * Returns the exceptions associated with this CSVLine.
     * @return the exceptions associated with this CSVLine. There may be none.
     */
    public ArrayList<Exception> getExceptions() {
        return this.exceptions;
    }
}
