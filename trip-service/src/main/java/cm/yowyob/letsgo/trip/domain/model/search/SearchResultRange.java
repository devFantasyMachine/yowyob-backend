/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.search;


import lombok.Data;
import lombok.NonNull;

@Data
public class SearchResultRange {

    final Integer beginPos;
    final Integer endPos;


    public static final SearchResultRange DEFAULT_RANGE = new SearchResultRange(0, Integer.MAX_VALUE);

    public SearchResultRange(@NonNull Integer beginPos, @NonNull Integer endPos) {

        if (beginPos >= endPos)
            throw new IllegalStateException("beginPos can't be greater than endPos");

        this.beginPos = beginPos;
        this.endPos = endPos;
    }

    public int length() {
        return endPos - beginPos;
    }

    public SearchResultRange endWith(int endPos) {
        return new SearchResultRange(beginPos, endPos);
    }


}
