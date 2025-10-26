package com.github.jimorc.flexishowbuilder;

/**
 * TitleAndSortData contains the data retrieved from TitleAndSortDialog.
*/
public class TitleAndSortData {
    private final String title;
    private SortOrder order;
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

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("TitleAndSortData:");
        sb.append("\n   title: " + title);
        sb.append("\n   sortOrder: " + order);
        sb.append("\n   lastNameAsInitial: " + isLastNameAsInitial() + "\n");
        return sb.toString();
    }
}
