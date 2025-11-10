package com.github.jimorc.flexishowbuilder;

/**
 * IPLFieldWidths keeps track of the maximum widths of each field in an IPLColumn.
 */
public class IPLFieldWidths {
    private double imageFileNameWidth;
    private double imageTitleWidth;
    private double personFullNameWidth;
    private double personFirstNameWidth;
    private double personLastNameWidth;

    /**
     * Constructor - creates an IPLFieldWidths object with all widths initialized to zero.
     */
    IPLFieldWidths() {
        imageFileNameWidth = 0;
        imageTitleWidth = 0;
        personFullNameWidth = 0;
        personFirstNameWidth = 0;
        personLastNameWidth = 0;
    }

    /**
     * Retrieves the image file name width.
     * @return the image file name width.
     */
    public double getImageFileNameWidth() {
        return imageFileNameWidth;
    }

    /**
     * Sets the maximum image file name width if the new width is larger.
     * @param width the new width to consider.
     */
    public void setMaxImageFileNameWidth(double width) {
        if (width > imageFileNameWidth) {
            imageFileNameWidth = width;
        }
    }

    /**
     * Retrieves the image title width.
     * @return the image title width.
     */
    public double getImageTitleWidth() {
        return imageTitleWidth;
    }

    /**
     * Sets the maximum image title width if the new width is larger.
     * @param width the new width to consider.
     */
    public void setMaxImageTitleWidth(double width) {
        if (width > imageTitleWidth) {
            imageTitleWidth = width;
        }
    }

    /**
     * Retrieves the person full name width.
     * @return the person full name width.
     */
    public double getPersonFullNameWidth() {
        return personFullNameWidth;
    }

    /**
     * Sets the maximum person full name width if the new width is larger.
     * @param width the new width to consider.
     */
    public void setMaxPersonFullNameWidth(double width) {
        if (width > personFullNameWidth) {
            personFullNameWidth = width;
        }
    }

    /**
     * Retrieves the person first name width.
     * @return the person first name width.
     */
    public double getPersonFirstNameWidth() {
        return personFirstNameWidth;
    }

    /**
     * Sets the maximum person first name width if the new width is larger.
     * @param width the new width to consider.
     */
    public void setMaxPersonFirstNameWidth(double width) {
        if (width > personFirstNameWidth) {
            personFirstNameWidth = width;
        }
    }

    /**
     * Retrieves the person last name width.
     * @return the person last name width.
     */
    public double getPersonLastNameWidth() {
        return personLastNameWidth;
    }

    /**
     * Sets the maximum person last name width if the new width is larger.
     * @param width the new width to consider.
     */
    public void setMaxPersonLastNameWidth(double width) {
        if (width > personLastNameWidth) {
            personLastNameWidth = width;
        }
    }
}
