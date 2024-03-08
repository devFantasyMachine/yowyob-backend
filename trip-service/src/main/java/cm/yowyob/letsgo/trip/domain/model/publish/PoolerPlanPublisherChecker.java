package cm.yowyob.letsgo.trip.domain.model.publish;

import cm.yowyob.letsgo.trip.domain.model.plan.core.PoolerPlan;

public class PoolerPlanPublisherChecker implements PublisherChecker<PoolerPlan>{

    @Override
    public CheckerResponse doCheck(PoolerPlan tripPlan, SharedPolicy sharedPolicy) {

        CheckerResponse.CheckerResponseBuilder builder =
                new CheckerResponse.CheckerResponseBuilder();

        if (sharedPolicy == null || sharedPolicy.policyLevel() == null || sharedPolicy.subscriberType() == null) {
            builder.addCheckerProblem(CheckerProblem.NO_SHARED_POLICY);
        }

        if (tripPlan.isPublished()) {
            builder.addCheckerProblem(CheckerProblem.ALREADY_PUBLISHED);
        }

        return builder.build();
    }

}
