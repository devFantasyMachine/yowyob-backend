/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.schedule;







public interface ScheduledCronEntity extends ScheduledEntity  {

    default Boolean getIsCronJob(){return true;}

    String getCronExpression();

}


