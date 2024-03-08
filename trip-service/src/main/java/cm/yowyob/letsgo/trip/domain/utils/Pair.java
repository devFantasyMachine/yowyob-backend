/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.utils;

import java.util.Objects;

public record Pair<L, R>(L left, R right) {

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }


    public static <L, R> Pair<L, R> of(L left,R right) {

        return new Pair<>(Objects.requireNonNull(left), Objects.requireNonNull(right));
    }

    public static <L, R> Pair<L, R> ofNullable(L left,R right) {

        return new Pair<>(left, right);
    }
}
