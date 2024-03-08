/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.exceptions;

import lombok.Getter;

@Getter
public class NoSuchSearchResultException extends Exception {

    private final Integer lastPos;

    public NoSuchSearchResultException(Integer lastPos) {
        this.lastPos = lastPos;
    }
}
