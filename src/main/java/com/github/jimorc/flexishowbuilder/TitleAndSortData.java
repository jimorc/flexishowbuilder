package com.github.jimorc.flexishowbuilder;

/**
 * TitleAndSortData contains the data retrieved from TitleAndSortDialog.
*/
public class TitleAndSortData {
    private final String title;
    private final SortOrder order;
    private final boolean lastNameAsInitial;

    /**
     * Constructor.
     * @param title contents of the title input
     * @param order sort order
     * @param lastNameAsInitial display last name as initial?
     */
    TitleAndSortData(String title, SortOrder order, boolean lastNameAsInitial) {
        this.title = title;
        this.order = order;
        this.lastNameAsInitial = lastNameAsInitial;
    }

    public String getTitle() {
        return title;
    }

    public SortOrder getOrder() {
        return order;
    }

    public boolean isLastNameAsInitial() {
        return lastNameAsInitial;
    }
}
