package cm.yowyob.letsgo.driver.mappers;

import cm.yowyob.letsgo.driver.domain.model.driver.Certificate;
import cm.yowyob.letsgo.driver.infrastructure.entities.udt.CertificateEntity;

public class CertificateMapper extends Mapper<Certificate, CertificateEntity>{

    @Override
    public Certificate toObject(CertificateEntity entity) {
        return entity == null ? null : Certificate.builder()
                .certificateLink(entity.getCertificateLink())
                .certifiedAt(entity.getCertifiedAt())
                .syndicatId(entity.getSyndicatId())
                .build();
    }

    @Override
    public CertificateEntity toEntity(Certificate object) {
        return object == null ? null : CertificateEntity.builder()
                .certificateLink(object.getCertificateLink())
                .certifiedAt(object.getCertifiedAt())
                .syndicatId(object.getSyndicatId())
                .build();
    }
}
