package cm.yowyob.auth.app.application.controllers;


import cm.yowyob.auth.app.application.utils.HttpHelper;
import cm.yowyob.auth.app.domain.exceptions.BadDeviceRequestException;
import cm.yowyob.auth.app.domain.exceptions.DeviceNotFoundException;
import cm.yowyob.auth.app.domain.exceptions.UnTrustedDeviceException;
import cm.yowyob.auth.app.domain.handlers.DeviceManager;
import cm.yowyob.auth.app.domain.model.code.FingerPrint;
import cm.yowyob.auth.app.domain.model.device.Device;
import cm.yowyob.auth.app.domain.model.user.UserId;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;


@RestController
@RequestMapping("/devices")
public class DeviceController {


    @Autowired
    DeviceManager deviceManager;


    @GetMapping("/my")
    public Set<Device> retrieveDevices(Authentication authentication) {

        UserId userId = UserId.of(authentication.getName());

        return deviceManager.retrieveDevices(userId);
    }


    @PutMapping("/{fingerPrint}/terminate")
    public Device terminateDeviceSession(Authentication authentication,
                                       @PathVariable String fingerPrint,
                                       HttpServletRequest httpServletRequest)
            throws DeviceNotFoundException, UnTrustedDeviceException {

        UUID deviceId = HttpHelper.getTenantId(httpServletRequest);
        UserId userId = UserId.of(authentication.getName());
        FingerPrint fingerPrint1 = new FingerPrint(fingerPrint);

        if (deviceId == null || userId == null)
            throw new IllegalArgumentException();

        return deviceManager.logout(deviceId, userId, fingerPrint1);

    }


    @PutMapping("/{fingerPrint}/lock")
    public Device lockDevice(Authentication authentication,
                             @PathVariable String fingerPrint,
                             HttpServletRequest httpServletRequest)
            throws DeviceNotFoundException, UnTrustedDeviceException, BadDeviceRequestException {

        UUID deviceId = HttpHelper.getTenantId(httpServletRequest);
        UserId userId = UserId.of(authentication.getName());
        FingerPrint fingerPrint1 = new FingerPrint(fingerPrint);

        if (deviceId == null || userId == null)
            throw new IllegalArgumentException();

        return deviceManager.lockByUser(deviceId, userId, fingerPrint1);
    }


    @DeleteMapping("/{fingerPrint}/delete")
    public void deleteDevice(Authentication authentication,
                             @PathVariable String fingerPrint,
                             HttpServletRequest httpServletRequest)
            throws DeviceNotFoundException, UnTrustedDeviceException, BadDeviceRequestException {

        UUID deviceId = HttpHelper.getTenantId(httpServletRequest);
        UserId userId = UserId.of(authentication.getName());
        FingerPrint fingerPrint1 = new FingerPrint(fingerPrint);

        if (deviceId == null || userId == null)
            throw new IllegalArgumentException();

        deviceManager.delete(deviceId, userId, fingerPrint1);

    }


}
