package cm.yowyob.auth.app.infrastructure.services;

import cm.yowyob.auth.app.domain.model.application.Application;
import cm.yowyob.auth.app.domain.model.application.ApplicationRole;
import cm.yowyob.auth.app.domain.model.application.Scope;
import cm.yowyob.auth.app.domain.port.ApplicationService;
import cm.yowyob.auth.app.infrastructure.entities.ApplicationEntity;
import cm.yowyob.auth.app.infrastructure.entities.ApplicationRoleEntity;
import cm.yowyob.auth.app.infrastructure.repositories.ApplicationEntityRepository;
import cm.yowyob.auth.app.infrastructure.repositories.ApplicationRoleEntityRepository;
import cm.yowyob.auth.app.infrastructure.repositories.ScopeEntityRepository;
import cm.yowyob.auth.app.mappers.ApplicationMapper;
import cm.yowyob.auth.app.mappers.ApplicationRoleMapper;
import cm.yowyob.auth.app.mappers.ScopeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ApplicationServiceImpl implements ApplicationService {


    @Autowired
    ApplicationEntityRepository applicationEntityRepository;

    @Autowired
    ApplicationRoleEntityRepository applicationRoleEntityRepository;

    @Autowired
    ScopeEntityRepository scopeEntityRepository;


    @Autowired
    ApplicationMapper applicationMapper;

    @Autowired
    ApplicationRoleMapper applicationRoleMapper;

    @Autowired
    ScopeMapper scopeMapper;



    @Override
    public Application save(Application app) {

        ApplicationEntity applicationEntity
                = applicationEntityRepository.save(applicationMapper.toEntity(app));
        return applicationMapper.toObject(applicationEntity);
    }

    @Override
    public Scope save(Scope role) {
        return scopeMapper.toObject(scopeEntityRepository.save(scopeMapper.toEntity(role)));
    }


    @Override
    public ApplicationRole save(ApplicationRole role) {

        ApplicationRoleEntity roleEntity = applicationRoleEntityRepository.save(applicationRoleMapper.toEntity(role));
        return applicationRoleMapper.toObject(roleEntity);
    }



    @Override
    public Set<ApplicationRole> saveAll(Collection<ApplicationRole> applicationRoles) {

        List<ApplicationRoleEntity> applicationRoleEntities
                = applicationRoleEntityRepository.saveAll(applicationRoleMapper.toEntities(applicationRoles));
        return applicationRoleMapper.toObjectSet(applicationRoleEntities);
    }


    @Override
    public Optional<Application> getById(UUID appId) {

        return applicationEntityRepository.findById(appId)
                .map(applicationMapper::toObject);
    }

    @Override
    public List<Application> getAllByIds(List<UUID> apps) {

        return applicationMapper.toObjects(applicationEntityRepository.findAllById(apps));
    }


}
