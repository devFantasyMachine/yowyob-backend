/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.search;

import java.util.SortedSet;

public interface FilterApplier {

    SortedSet<SearchResult> apply(FilterContext filterContext, SortedSet<SearchResult> initialResults);
}

