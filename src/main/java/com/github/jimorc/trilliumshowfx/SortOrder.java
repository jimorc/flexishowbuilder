package com.github.jimorc.trilliumshowfx;

/**
 * sortOrder defines possible sort orders for InputCSV lines.
*/
public enum SortOrder {
    /**
     * DontSort denotes no sorting of InputCSV lines. The order of lines is as they appear in the CSV file.
     */
    DontSort,
    /**
     * AsIs denotes sorting in the order than full names first appear in CSV file.
     */
    AsIs,
    /**
     * AlphabeticalByFullName denotes sorting in alphabetical order by full name.
     */
    AlphabeticalByFullName,
    /**
     * AlphabeticalByLastNameThenFirstName denotes sorting in alphabetical order by last name then first name.
     */
    AlphabeticalByLastNameThenFirstName,
    /**
     * AlphabeticalByFullNameReverse denotes sorting in reverse alphabetical order by full name.
     */
    AlphabeticalByFullNameReverse,
    /**
     * AlphabeticalByLastNameThenFirstNameReverse denotes sorting in reverse alphabetical order by last
     * name then first name.
     */
    AlphabeticalByLastNameThenFirstNameReverse
}
