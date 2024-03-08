package cm.yowyob.auth.app.infrastructure.services;


import cm.yowyob.auth.app.domain.model.tenant.Tenant;
import cm.yowyob.auth.app.domain.port.TenantService;
import cm.yowyob.auth.app.infrastructure.entities.TenantEntity;
import cm.yowyob.auth.app.infrastructure.repositories.OrganisationEntityRepository;
import cm.yowyob.auth.app.infrastructure.repositories.TenantEntityRepository;
import cm.yowyob.auth.app.mappers.TenantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class TenantServiceImpl implements TenantService {

    @Autowired
    TenantEntityRepository tenantEntityRepository;

    @Autowired
    OrganisationEntityRepository organisationEntityRepository;

    @Autowired
    TenantMapper tenantMapper;


    @Override
    public Tenant save(Tenant tenant) {

        TenantEntity entity = tenantMapper.toEntity(tenant);
        organisationEntityRepository.save(entity.getOrganisation());

        TenantEntity tenantEntity = tenantEntityRepository.save(entity);
        return tenantMapper.toObject(tenantEntity);
    }


    @Override
    public Optional<Tenant> getTenant(UUID tenantId) {

        return tenantEntityRepository.findById(tenantId)
                .map(tenantMapper::toObject);
    }


    @Override
    public Optional<Tenant> getTenantByIssuer(String shortName) {

        return tenantEntityRepository.findByIssuerIgnoreCase(shortName)
                .map(tenantMapper::toObject);
    }


}
