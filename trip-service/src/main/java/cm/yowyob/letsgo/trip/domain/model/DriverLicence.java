/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model;

import lombok.NonNull;

import java.time.Instant;
import java.util.Set;


public record DriverLicence(String licenceUId,
                            Boolean isVerified,
                            Instant issueAt,
                            Instant expireAt,
                            String identityProvider,
                            Set<String> docs) {


    public static DriverLicence NONE = new DriverLicence("", null, null, null, "", Set.of());


    @NonNull
    public static DriverLicenceBuilder builder(String licenceUId) {
        return new DriverLicenceBuilder(licenceUId);
    }

    public static class DriverLicenceBuilder {
        String licenceUId;
        Boolean isVerified = false;
        Instant issueAt;
        Instant expireAt;
        String identityProvider;
        Set<String> docs;

        DriverLicenceBuilder(String licenceUId) {
            this.licenceUId = licenceUId;
        }

        public DriverLicenceBuilder issueAt(Instant issueAt)  {
            this.issueAt = issueAt;
            return this;
        }

        public DriverLicenceBuilder docs(Set<String> docs)  {
            this.docs = docs;
            return this;
        }

        public DriverLicenceBuilder isVerified(Boolean isVerified)  {
            this.isVerified = isVerified;
            return this;
        }

        public DriverLicenceBuilder identityProvider(String identityProvider)  {
            this.identityProvider = identityProvider;
            return this;
        }
        public DriverLicenceBuilder expireAt(Instant expireAt)  {
            this.expireAt = expireAt;
            return this;
        }


        public DriverLicence build() {
            return new DriverLicence(licenceUId, isVerified, issueAt, expireAt, identityProvider, docs);
        }

        public String toString() {
            return "DriverLicence.DriverLicenceBuilder()";
        }
    }
}
