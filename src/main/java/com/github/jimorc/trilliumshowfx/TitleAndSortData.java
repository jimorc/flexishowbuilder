package com.github.jimorc.trilliumshowfx;

/**
 * TitleAndSortData contains the data retrieved from TitleAndSortDialog.
*/
public class TitleAndSortData {
    private final SlideSize slideSize;
    private final boolean createStartEndSlides;
    private final String startTitle;
    private final String endTitle;
    private final SortOrder order;
    private final boolean generatePersonSlides;

    /**
     * Constructor.
     * @param title contents of the title input
     * @param order sort order
     * @param lastNameAsInitial display last name as initial?
     */
    TitleAndSortData(final SlideSize slideSize, final boolean createStartEndSlides,
            final String startTitle, final String endTitle, final SortOrder order,
            final boolean generatePersonSlides) {
        this.slideSize = slideSize;
        this.createStartEndSlides = createStartEndSlides;
        this.startTitle = startTitle;
        this.endTitle = endTitle;
        this.order = order;
        this.generatePersonSlides = generatePersonSlides;
    }

    public SlideSize getSlideSize() {
        return slideSize;
    }

    public boolean getCreateStartEndSlides() {
        return createStartEndSlides;
    }

    public String getStartTitle() {
        return startTitle;
    }

    public String getEndTitle() {
        return endTitle;
    }

    public SortOrder getOrder() {
        return order;
    }

    public boolean getGeneratePersonSlides() {
        return generatePersonSlides;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("TitleAndSortData:");
        sb.append("\n    slide size:");
        sb.append("\n    create start and end slides: "
            + (createStartEndSlides ? "true" : "false"));
        sb.append("\n        width:" + slideSize.getWidth());
        sb.append("\n        height:" + slideSize.getHeight());
        sb.append("\n    start title: " + startTitle);
        sb.append("\n    end title:" + endTitle);
        sb.append("\n    sortOrder: " + order);
        sb.append("\n    generate person slides: " + (generatePersonSlides ? "true" : "false"));
        return sb.toString();
    }
}
