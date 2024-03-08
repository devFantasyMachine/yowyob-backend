
import { Device } from "@/types/device";
import Accordion from "@/components/Accordion";
import {
    ChevronDownIcon,
    ComputerDesktopIcon,
    DevicePhoneMobileIcon,
} from "@heroicons/react/24/outline";


import { formattedDate } from "@/utils/string-utils";
import { ShieldCheckIcon } from "@heroicons/react/20/solid";




export const DeviceCard = ({ device }: { device: Device }) => {


    return (
        <div className="rounded-2xl flex justify-between items-center">
            <div className="flex flex-wrap items-center justify-between gap-4">

                <div className="flex gap-6 ">
                    <div className="shrink-0 gap-2 flex flex-col items-center">

                        {
                            device.deviceType === "MOBILE" ? 
                            (<DevicePhoneMobileIcon className="w-8 h-8" />)
                            :
                            (<ComputerDesktopIcon className="w-8 h-8" />)
                        }

                        {device.isTrusted && <ShieldCheckIcon color="#FFA70B" className="w-7 h-7" />}

                    </div>

                    <div className="flex-grow items-start text-start flex flex-col">

                        <p className="font-semibold text-md text-gray-900 mb-1 truncate">
                            {`${device.deviceFingerPrint.value}`}
                        </p>

                        <p className="font-medium mb-1 text-sm truncate">
                            {`${device.userAgent.value}`}
                        </p>

                        <span className="font-semibold mb-1 text-sm ">
                            {`${device.deviceType} - ${device.userAgent.deviceOs} ${device.userAgent.osVersion}`}
                        </span>

                        <h5 className="font-medium mb-1"> {`${device.deviceName} ${formattedDate(new Date(device.createdAt))}`}</h5>
                        <ul className="flex flex-wrap items-center gap-2">
                            <li>
                                <span className="inline-block text-sm">
                                    {`IP : ${device.lastIp}`}
                                </span>
                            </li>
                            <li>
                                <span className="inline-block text-sm">
                                    {`Last Seen : ${formattedDate(new Date(device.lastSeen))}`}
                                </span>
                            </li>
                            <li>
                                {device.enabled ?
                                    (
                                        <span className="inline-block uppercase text-sm font-bold text-green-600">
                                            active
                                        </span>
                                    )
                                    :
                                    (
                                        <span className="inline-block uppercase text-sm font-bold text-red-600">
                                            deactive
                                        </span>
                                    )}

                            </li>

                        </ul>

                    </div>
                </div>
            </div>
        </div>
    );


}




