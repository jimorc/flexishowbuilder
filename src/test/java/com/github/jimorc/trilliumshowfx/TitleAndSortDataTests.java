package com.github.jimorc.trilliumshowfx;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TitleAndSortDataTests contains tests for the TitleAndSortData class.
 */
public class TitleAndSortDataTests {
    @Test
    public void testToString() {
        final int width = 1400;
        final int height = 1050;
        TitleAndSortData data = new TitleAndSortData(new SlideSize(width, height),
            true, "Start", "End",
            SortOrder.AlphabeticalByFullName, true, true);
        String expected = "TitleAndSortData:\n"
            + "    slide size:\n"
            + "        width:1400\n"
            + "        height:1050\n"
            + "    create start and end slides: true\n"
            + "    start title: Start\n"
            + "    end title: End\n"
            + "    sort order: AlphabeticalByFullName\n"
            + "    generate person slides: true\n"
            + "    sort slides by title number: true";
        assertEquals(expected, data.toString());
    }
}
