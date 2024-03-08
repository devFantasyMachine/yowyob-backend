package cm.yowyob.auth.app.mappers;

import cm.yowyob.auth.app.domain.model.application.Scope;
import cm.yowyob.auth.app.infrastructure.entities.ScopeEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;
import org.springframework.stereotype.Component;


@Component
public class ScopeMapper extends Mapper<Scope, ScopeEntity> {


    @Override
    public Scope toObject(ScopeEntity entity) {
        return entity == null ? null : Scope.builder()
                .scopeDesc(entity.getScopeDesc())
                .createdAt(entity.getCreatedAt())
                .name(entity.getName())
                .appId(entity.getAppId())
                .build();
    }


    @Override
    public ScopeEntity toEntity(Scope object) {
        return object == null ? null : ScopeEntity.builder()
                .scopeDesc(object.getScopeDesc())
                .createdAt(object.getCreatedAt())
                .name(object.getName())
                .appId(object.getAppId())
                .build();
    }



}
