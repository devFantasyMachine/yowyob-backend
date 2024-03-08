package cm.yowyob.auth.app.domain.model.device;

import lombok.Getter;

@Getter
public enum DeviceType {

    CAMERA("camera"),
    CAR_BROWSER("car browser"),
    CONSOLE("console"),
    DESKTOP("desktop"),
    FEATURE_PHONE("feature phone"),
    PORTABLE_MEDIA_PLAYER("portable media player"),
    PHABLET("phablet"),
    SMARTPHONE("smartphone"),
    TV("tv"),
    TABLET("tablet");

    /**
     * Device name.
     */
    private final String deviceName;

    DeviceType(String deviceName) {
        this.deviceName = deviceName;
    }

}
