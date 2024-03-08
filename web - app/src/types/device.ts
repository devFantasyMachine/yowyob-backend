import { Address } from "./tenant"


interface FingerPrint {

    value: string
}

interface UserAgent {
    "family": string,
    "devicePlatform": string,
    "deviceOs": string,
    "osVersion": string,
    "value": string
}

export interface Device {

    deviceFingerPrint: FingerPrint,
    userId: string,
    deviceId: string,
    lastIp: string,
    isTrusted: boolean,
    locked: boolean,
    "deleted": boolean,
    "enabled": boolean,
    "createdAt": string,
    "lastSeen": string,
    deviceType: string,
    "deviceName": string,
    "deviceModel": string,
    "deviceManufacturer": string,
    lastLoggedIn: string,
    "userAgent": UserAgent,
    "sessionEndAt": string,
    "lockedTtl": string,
    location?: Address
}


 