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
    private final boolean sortSlidesByTitleNumber;

    /**
     * Constructor.
     * @param slideSize the slide size
     * @param createStartEndSlides whether to create start and end slides
     * @param startTitle the start title
     * @param endTitle the end title
     * @param order the sort order
     * @param generatePersonSlides whether to generate person slides
     * @param sortSlidesByTitleNumber whether to sort slides by title number
     */
    TitleAndSortData(final SlideSize slideSize, final boolean createStartEndSlides,
            final String startTitle, final String endTitle, final SortOrder order,
            final boolean generatePersonSlides, final boolean sortSlidesByTitleNumber) {
        this.slideSize = slideSize;
        this.createStartEndSlides = createStartEndSlides;
        this.startTitle = startTitle;
        this.endTitle = endTitle;
        this.order = order;
        this.generatePersonSlides = generatePersonSlides;
        this.sortSlidesByTitleNumber = sortSlidesByTitleNumber;
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

    public boolean getSortSlidesByTitleNumber() {
        return sortSlidesByTitleNumber;
    }

    @Override
    public String toString() {
        SlideSize sSize = getSlideSize();
        StringBuffer sb = new StringBuffer();
        sb.append("TitleAndSortData:");
        sb.append("\n    slide size:");
        sb.append("\n        width:" + sSize.getWidth());
        sb.append("\n        height:" + sSize.getHeight());
        sb.append("\n    create start and end slides: "
            + getCreateStartEndSlides());
        sb.append("\n    start title: " + getStartTitle());
        sb.append("\n    end title: " + getEndTitle());
        sb.append("\n    sort order: " + getOrder());
        sb.append("\n    generate person slides: " + getGeneratePersonSlides());
        sb.append("\n    sort slides by title number: " + getSortSlidesByTitleNumber());
        return sb.toString();
    }
}
