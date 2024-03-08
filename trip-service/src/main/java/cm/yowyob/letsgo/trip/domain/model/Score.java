package cm.yowyob.letsgo.trip.domain.model;


import java.util.Objects;

public record Score(Float starCount, ScoreLevel level) {

    public static final Score INITIAL = new Score(0.0F, ScoreLevel.BRONZE);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Score score)) return false;
        return starCount.equals(score.starCount) && level == score.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(starCount, level);
    }

    @Override
    public String toString() {
        return "Score{" +
                "starCount=" + starCount +
                ", level=" + level +
                '}';
    }

}
