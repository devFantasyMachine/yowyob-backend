/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.schedule;


public enum IntervalUnit {

    DAY("DAY"), HOURS("HOURS"), SECOND("SECOND");

    private final String value;

    IntervalUnit(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }



    public Long toIntervalInSecond(Long repeatInterval){

        if (this == IntervalUnit.HOURS ){

            return repeatInterval * 3600000;
        }
        else if (this == IntervalUnit.DAY ){

            return repeatInterval * 86400000;
        }
        else {

            return repeatInterval;
        }

    }
    
}

