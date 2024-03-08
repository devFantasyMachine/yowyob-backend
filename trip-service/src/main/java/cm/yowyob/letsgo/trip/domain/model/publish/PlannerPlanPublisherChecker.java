/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.publish;

import cm.yowyob.letsgo.trip.domain.model.plan.core.PlannerPlan;

public class PlannerPlanPublisherChecker implements PublisherChecker<PlannerPlan> {

    @Override
    public CheckerResponse doCheck(PlannerPlan tripPlan, SharedPolicy sharedPolicy) {


        CheckerResponse.CheckerResponseBuilder builder =
                new CheckerResponse.CheckerResponseBuilder();

        if (tripPlan.isPublished()) {
            builder.addCheckerProblem(CheckerProblem.ALREADY_PUBLISHED);
            return builder.build();
        }

        if (sharedPolicy.policyLevel() != SharedPolicyLevel.PUBLIC)
            builder.addCheckerProblem(CheckerProblem.NON_PUBLIC_PLAN);

        if (tripPlan.getSeatCost() == null) {
            builder.addCheckerProblem(CheckerProblem.NO_STANDARD_COST);
        }

        if (tripPlan.getPaymentSetting() == null) {
            builder.addCheckerProblem(CheckerProblem.NO_PAYMENT_SETTING);
        }


        if (tripPlan.getSeatCount() == null || tripPlan.getSeatCount() <= 1)
            builder.addCheckerProblem(CheckerProblem.NO_PASSENGER_PLACE);

        if (tripPlan.getAllDrivers() == null || tripPlan.getAllDrivers().isEmpty())
            builder.addCheckerProblem(CheckerProblem.NO_DRIVER);

        if (tripPlan.getAllVehicles() == null || tripPlan.getAllVehicles().isEmpty())
            builder.addCheckerProblem(CheckerProblem.NO_VEHICLE);


        if (tripPlan.getReservationPolicy() == null){

            builder.addCheckerProblem(CheckerProblem.NO_RESERVATION_POLICY);
        }

        if (tripPlan.getConfirmationPolicy() == null){

            builder.addCheckerProblem(CheckerProblem.NO_CONFIRMATION_POLICY);
        }

        if (tripPlan.getCancellationPolicy() == null){

            builder.addCheckerProblem(CheckerProblem.NO_CANCELLATION_POLICY);
        }


        return builder.build();
    }
}
