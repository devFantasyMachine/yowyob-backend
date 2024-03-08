/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.search;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;


@Data
@Builder
public class SearchResult implements Comparable<SearchResult> {

    private Integer score;

    @Override
    public int compareTo(@NonNull SearchResult o) {
        return score.compareTo(o.score);
    }
}
