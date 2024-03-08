/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.exceptions;

import cm.yowyob.letsgo.trip.domain.model.publish.CheckerProblem;
import lombok.Getter;

import java.util.Set;

@Getter
public class UnPublishableTripException extends Exception {

    final Set<CheckerProblem> checkerProblems;

    public UnPublishableTripException(Set<CheckerProblem> checkerProblems) {
        super();
        this.checkerProblems = checkerProblems;
    }
}
