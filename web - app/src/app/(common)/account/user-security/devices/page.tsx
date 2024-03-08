"use client";

 
import Accordion from "@/components/Accordion";
import {
	ChevronDownIcon,
	ComputerDesktopIcon,
	DevicePhoneMobileIcon,
} from "@heroicons/react/24/outline";
import CustomSwitch from "@/components/Switch";
import Alert from "@/components/Alert";
import { useEffect, useState } from "react";
import { getUserDevices } from "@/services/device-service";
import { Device } from "@/types/device";
import { formattedDate } from "@/utils/string-utils"; 
import { DeviceCard } from "@/components/DeviceCard";








const page = () => {


	const [selected, setSelected] = useState(1);

	const [devices, setDevices] = useState<Device[]>([]);


	useEffect(() => {

		getUserDevices()
			.then(devices => {

				setDevices(devices);
			})

	}, [])



 
	return (
		<div className="flex flex-col ">

			<div className="mb-6 flex flex-col gap-4">
				<span className="block mb-2 text-xl font-medium clr-neutral-500">Device history</span>

				<div className="pt-4 lg:pt-6">
					<ul className="flex flex-col gap-6">

						{
							devices.map((device, index) => (

								<li key={index} className="bg-gray-100 rounded-2xl p-6">
									<Accordion
										buttonContent={(open) => <DeviceCard device={device}/>}
										initialOpen={false}>

										<div className="w-full pt-4 lg:pt-6 flex flex-wrap gap-3 items-end justify-end justify-items-end">

											<button

												className="bg-danger text-white font-semibold focus:ring-4 focus:outline-none focus:ring-indigo-300 rounded-lg text-sm px-5 py-2.5 text-center dark:bg-indigo-600 dark:hover:bg-indigo-700 dark:focus:ring-indigo-800">
												Delete
											</button>
											<button

												className="bg-danger text-white font-semibold focus:ring-4 focus:outline-none focus:ring-indigo-300 rounded-lg text-sm px-5 py-2.5 text-center dark:bg-indigo-600 dark:hover:bg-indigo-700 dark:focus:ring-indigo-800">
												{`${device.locked ? "UnLock" : "Lock"}`}
											</button>

											{
												device.enabled && 
												<button
													className="bg-warning text-white font-semibold focus:ring-4 focus:outline-none focus:ring-indigo-300 rounded-lg text-sm px-5 py-2.5 text-center dark:bg-indigo-600 dark:hover:bg-indigo-700 dark:focus:ring-indigo-800">
													Log out
												</button>
											}

											{
												!device.isTrusted &&
												<button

													className="bg-warning text-white font-semibold focus:ring-4 focus:outline-none focus:ring-indigo-300 rounded-lg text-sm px-5 py-2.5 text-center dark:bg-indigo-600 dark:hover:bg-indigo-700 dark:focus:ring-indigo-800">
													Trust
												</button>
											}


										</div>

									</Accordion>
								</li>

							))
						}

					</ul>
				</div>


			</div>








		</div>
	);
};

export default page;
