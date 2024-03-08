/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model;

import java.security.SecureRandom;
import java.util.Objects;

public class DefaultTripPlanIdentifierGenerator implements LetsgoIdentifierGenerator<DefaultTripPlanContext> {

    static final SecureRandom SECURE_RANDOM = new SecureRandom();
    public static int generateRandomInt() {
        return 100 + SECURE_RANDOM.nextInt(900);
    }

    @Override
    public String generate(DefaultTripPlanContext context) {

        String part1;
        if (context.getPlannerCode() != null){
            part1 = context.getPlannerCode();
        }
        else {
            part1 = "T";
        }

        String part2 = String.valueOf(Objects.requireNonNull(context.getContextDatetime()).toEpochSecond());
        String part3 = String.valueOf(generateRandomInt());

        return String.join("-", part1, part2, part3).toUpperCase();
    }
}
