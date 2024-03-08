package cm.yowyob.auth.app.domain.model.device;


import java.util.Objects;


public record UserAgent(String family,
                        String devicePlatform,
                        String deviceOs,
                        String osVersion,
                        String value) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAgent userAgent)) return false;
        return Objects.equals(family.trim(), userAgent.family.trim())
                && Objects.equals(devicePlatform, userAgent.devicePlatform)
                && Objects.equals(deviceOs.trim(), userAgent.deviceOs.trim())
                && Objects.equals(osVersion.trim(), userAgent.osVersion.trim())
                && Objects.equals(value.trim(), userAgent.value.trim());
    }


}
