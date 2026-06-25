package com.github.jimorc.trilliumshowfx;

/**
 * sortOrder defines possible sort orders for InputCSV lines.
*/
public enum SortOrder {
    /**
     * Don't sort - use the image order in the CSV file.
     */
    DontSort("Don't Sort", "Do not sort - use the order in the CSV file.",
        "Slides remain in the order they appear in the CSV file.\n"
        + "No person slides will be created."),
    /**
     * As Is - sort by person in the order they first appear in the CSV file.
     */
    AsIs("As Is",
        "No sorting - use the order in the CSV file. All images for each person are grouped together.",
        "Slides will be sorted by person in the order they first appear in the CSV file.\n"
        + "All images for each person are grouped together.\n"
        + "Person slides will be created for each person."),
    /**
     * Alphabetical by Full Name.
     */
    AlphabeticalByFullName("Alphabetical by Full Name",
        "Sort in alphabetical order by full name.",
        "Slides will be sorted in alphabetical order by full name.\n"
        + "Person slides will be created for each person."),
    /**
     * Alphabetical by Last Name then First Name.
     */
    AlphabeticalByLastNameThenFirstName("Alphabetical by Last Name then First Name",
        "Sort in alphabetical order by last name then first name.",
        "Slides will be sorted in alphabetical order by last name then first name.\n"
        + "Person slides will be created for each person."),
    /**
     * Alphabetical by Full Name (Reverse).
     */
    AlphabeticalByFullNameReverse("Alphabetical by Full Name (Reverse)",
        "Sort in reverse alphabetical order by full name.",
        "Slides will be sorted in reverse alphabetical order by full name.\n"
        + "Person slides will be created for each person."),
    /**
     * Alphabetical by Last Name then First Name (Reverse).
     */
    AlphabeticalByLastNameThenFirstNameReverse("Alphabetical by Last Name then First Name (Reverse)",
        "Sort in reverse alphabetical order by last name then first name.",
        "Slides will be sorted in reverse alphabetical order by last name then first name.\n"
        + "Person slides will be created for each person.");

    private String buttonLabel;
    private String tooltipText;
    private String sortOrderLabel;

    SortOrder(String label, String tooltipText, String sortOrderLabel) {
        this.buttonLabel = label;
        this.tooltipText = tooltipText;
        this.sortOrderLabel = sortOrderLabel;
    }

    public String getButtonLabel() {
        return buttonLabel;
    }

    public String getTooltipText() {
        return tooltipText;
    }

    public String getSortOrderLabel() {
        return sortOrderLabel;
    }
}

