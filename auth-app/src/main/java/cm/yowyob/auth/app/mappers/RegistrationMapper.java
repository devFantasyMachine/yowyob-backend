/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.mappers;


import cm.yowyob.auth.app.domain.model.registration.Registration;
import cm.yowyob.auth.app.infrastructure.entities.RegistrationEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class RegistrationMapper extends Mapper<Registration, RegistrationEntity> {


    @Override
    public Registration toObject(RegistrationEntity registrationEntity) {

        if (registrationEntity == null)
            return null;


        return new Registration(
                registrationEntity.getId(),
                registrationEntity.getTenantId(),
                registrationEntity.getIssueAt(),
                registrationEntity.getExpireAt(),
                registrationEntity.getUsed(),
                registrationEntity.getAccepted(),
                registrationEntity.getAcceptedAt(),
                registrationEntity.getUsername(),
                registrationEntity.getEncodedPassword(),
                registrationEntity.getEmail(),
                registrationEntity.getPhone(),
                registrationEntity.getFirstName(),
                registrationEntity.getLastName(),
                registrationEntity.getGender(),
                registrationEntity.getBirthdate(),
                registrationEntity.getTimezone() == null ? null : ZoneId.of(registrationEntity.getTimezone()),
                registrationEntity.getUserAgent(),
                registrationEntity.getIpAddress(),
                registrationEntity.getRegistrationCode(),
                registrationEntity.getLoginMethod(),
                registrationEntity.getRedirectUri(),
                registrationEntity.getRequireActions());
    }


    @Override
    public RegistrationEntity toEntity(Registration registration) {

        if (registration == null) {
            return null;
        }

        return RegistrationEntity.builder()
                .id(registration.getId())
                .tenantId(registration.getTenantId())
                .userAgent(registration.getUserAgent())
                .registrationCode(registration.getRegistrationCode())
                .issueAt(registration.getIssueAt())
                .expireAt(registration.getExpireAt())
                .used(registration.getUsed())
                .acceptedAt(registration.getAcceptedAt())
                .encodedPassword(registration.getEncodedPassword())
                .ipAddress(registration.getIpAddress())
                .firstName(registration.getFirstName())
                .lastName(registration.getLastName())
                .loginMethod(registration.getLoginMethod())
                .accepted(registration.getAccepted())
                .email(registration.getEmail())
                .phone(registration.getPhone())
                .username(registration.getUsername())
                .gender(registration.getGender())
                .birthdate(registration.getBirthdate())
                .timezone(registration.getTimezone() == null ? null: registration.getTimezone().getId())
                .redirectUri(registration.getRedirectUri())
                .requireActions(registration.getRequireActions())
                .build();
    }



}
