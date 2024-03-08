/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.publish;




import java.util.HashSet;
import java.util.Set;




public record CheckerResponse(Set<CheckerProblem> checkerProblems) {


    public boolean isNonPublishable() {

        return !checkerProblems.isEmpty();
    }


    public static class CheckerResponseBuilder {

        private final Set<CheckerProblem> checkerProblems;

        public CheckerResponseBuilder() {
            this.checkerProblems = new HashSet<>();
        }

        public void addCheckerProblem(CheckerProblem checkerProblem) {
            this.checkerProblems.add(checkerProblem);
        }

        public CheckerResponse build() {

            return new CheckerResponse(checkerProblems);
        }

        public String toString() {
            return "CheckerResponse.CheckerResponseBuilder(checkerProblems=" + this.checkerProblems + ")";
        }
    }
}
