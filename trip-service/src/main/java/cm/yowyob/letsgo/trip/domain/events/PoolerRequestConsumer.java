/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.events;

import cm.yowyob.letsgo.trip.domain.events.core.LetsgoEventConsumer;
import cm.yowyob.letsgo.trip.domain.exceptions.NoSuchSearchResultException;
import cm.yowyob.letsgo.trip.domain.managers.SearchManager;
import cm.yowyob.letsgo.trip.domain.model.search.SearchResult;
import lombok.AllArgsConstructor;

import java.util.List;


@AllArgsConstructor
public class PoolerRequestConsumer implements LetsgoEventConsumer<PoolerRequestEvent> {

    SearchManager searchManager;
    NotificationEventPublisher notificationEventPublisher;

    @Override
    public boolean consume(PoolerRequestEvent letsgoEvent) {

        try {


            List<SearchResult> searchResults =
                    searchManager.searchPublishedTripForUser(letsgoEvent.userId, letsgoEvent.getSearchContext());



        } catch (NoSuchSearchResultException e) {
            e.printStackTrace();
            notificationEventPublisher.publish(new NotificationEvent());
        }

        return true;
    }
}
