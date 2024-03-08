/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model;

public interface LetsgoIdentifierGenerator<C extends LetsgoContext> {

    String generate(C context);
}
