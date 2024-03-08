/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.utils.lang;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * This annotation is purely for documentation purposes and tells developers that a component
 * is part of sandbox code.
 */
@Target({ FIELD, METHOD, CONSTRUCTOR })
@Retention(RetentionPolicy.SOURCE)
public @interface Sandbox {
}
