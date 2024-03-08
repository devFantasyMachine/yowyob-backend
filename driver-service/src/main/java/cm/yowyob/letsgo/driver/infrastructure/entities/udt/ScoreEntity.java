package cm.yowyob.letsgo.driver.infrastructure.entities.udt;


import cm.yowyob.letsgo.driver.domain.model.resources.Score;
import cm.yowyob.letsgo.driver.domain.model.resources.ScoreLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;


@Data
@Builder
@AllArgsConstructor
@UserDefinedType("score")
public class ScoreEntity {


    @Column("start_count")
    private final Float startCount;

    @Column("score_level")
    private final Integer scoreLevel;

    public static ScoreEntity fromScore(Score score) {
        if (score  == null)
            return null;

        return new ScoreEntity(score.starCount(), score.level() == null ? ScoreLevel.BRONZE.ordinal() : score.level().ordinal());
    }

    public static Score toScore(ScoreEntity entity){

        if (entity == null) {
            return null;
        }

        return new Score(entity.startCount, ScoreLevel.values()[entity.scoreLevel]);
    }
}
