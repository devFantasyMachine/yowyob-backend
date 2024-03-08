"use client";

import iconGoogle from "../../../../../public/img/icon-google.png";

import iconDropbox from "../../../../../public/img/icon-dropbox.png";
import iconSlack from "../../../../../public/img/icon-slack.png";
import iconMailChimp from "../../../../../public/img/icon-mailchimp.png";
import iconJira from "../../../../../public/img/icon-jira.png";
import Image from "next/image";
import Link from "next/link";
import Accordion from "@/components/Accordion";
import {
	ChevronDownIcon,
	ComputerDesktopIcon,
	DevicePhoneMobileIcon,
} from "@heroicons/react/24/outline";
import CustomSwitch from "@/components/Switch";
import Alert from "@/components/Alert";
import { useState } from "react";



import { useAppDispatch, useAppSelector } from "@/lib/stores/hooks";
import { getUser, setUser } from "@/lib/stores/features/users/userSlice";







const page = () => {


	const dispatch = useAppDispatch();
	const userState = useAppSelector(getUser);


	return (
		<div className="flex flex-col mt-2">

			<Alert txt={" Change a few things up and try submitting again."} />

			<div className="mb-6 flex flex-col gap-4">
				<Accordion
					buttonContent={(open) => (
						<div className="rounded-2xl p-2 flex justify-between items-center bg-slate-100">
							<span className="block mb-2 text-md font-medium clr-neutral-500">
							Change Password 
								</span>
							<ChevronDownIcon
								className={`w-5 h-5 sm:w-6 sm:h-6 duration-300 ${open ? "rotate-180" : ""
									}`}
							/>
						</div>
					)}
					initialOpen={false}>
					<div className="p-4 lg:pt-6">
						
						<form action="#" className="grid grid-cols-12 gap-4">
							<div className="col-span-12">
								<label
									htmlFor="current-password-setup"
									className="block mb-2 font-medium clr-neutral-500">
									Current password :
								</label>
								<input
									type="password"
									id="current-password-setup"
									className="w-full focus:outline-none border py-3 px-6 rounded-lg"
									placeholder="Enter current password"
								/>
							</div>

							<div className="col-span-12 lg:col-span-6">
								<label
									htmlFor="new-password"
									className="block mb-2 font-medium clr-neutral-500">
									New password :
								</label>
								<input
									type="password"
									id="new-password"
									className="w-full focus:outline-none border py-3 px-6 rounded-lg"
									placeholder="Enter new password"
								/>
							</div>
							<div className="col-span-12 lg:col-span-6">
								<label
									htmlFor="confirm-password"
									className="block mb-2 font-medium clr-neutral-500">
									Confirm password :
								</label>
								<input
									type="password"
									id="confirm-password"
									className="w-full focus:outline-none border py-3 px-6 rounded-lg"
									placeholder="Confirm your new password"
								/>
							</div>
							<div className="col-span-12">
								<h5 className="font-medium mb-4"> Password requirements : </h5>
								<ul className=" list-disc pl-4 gap-3">
									<li> Minimum 8 characters long - the more, the better </li>
									<li> At least one lowercase character </li>
									<li> At least one uppercase character </li>
									<li>At least one number, symbol, or whitespace character</li>
								</ul>
							</div>
							<div className="col-span-12">
								<div className="flex items-center gap-6 flex-wrap">
									<Link
										href="#"
										className="link inline-block py-3 px-6 rounded-full bg-primary text-white :bg-primary-400 hover:text-white font-semibold">
										Update Password
									</Link>
								</div>
							</div>
						</form>
					</div>
				</Accordion>

				<Accordion
					buttonContent={(open) => (
						<div className="rounded-2xl p-4 flex justify-between items-center bg-slate-100">
							<span className="block mb-2 text-lg font-medium clr-neutral-500">Email</span>
							<ChevronDownIcon
								className={`w-5 h-5 sm:w-6 sm:h-6 duration-300 ${open ? "rotate-180" : ""
									}`}
							/>
						</div>
					)}
					initialOpen={false}>
					<div className="p-4 lg:pt-6">

						<span className="block mb-2 text-md font-medium clr-neutral-500">Current Email </span>
						{userState?.email}
						<form action="#" className="grid grid-cols-12 gap-4">

							<div className="col-span-12 lg:col-span-6">
								<label
									htmlFor="new-password"
									className="block mb-2 font-medium clr-neutral-500">
									Email :
								</label>
								<input
									type="password"
									id="new-password"
									className="w-full focus:outline-none border py-3 px-6 rounded-lg"
									placeholder="Enter new password"

								/>
							</div>
							<div className="col-span-12">
								<div className="flex items-center gap-6 flex-wrap">
									<Link
										href="#"
										className="link inline-block py-3 px-6 rounded-full bg-primary text-white :bg-primary-400 hover:text-white font-semibold">
										Change Email
									</Link>
								</div>
							</div>
						</form>
					</div>
				</Accordion>

				<Accordion
					buttonContent={(open) => (
						<div className="rounded-2xl p-4 flex justify-between items-center bg-slate-100">
							<span className="block mb-2 text-lg font-medium clr-neutral-500">Phone</span>
							<ChevronDownIcon
								className={`w-5 h-5 sm:w-6 sm:h-6 duration-300 ${open ? "rotate-180" : ""
									}`}
							/>
						</div>
					)}
					initialOpen={false}>
					<div className="p-4 lg:pt-6">

						<span className="block mb-2 text-md font-medium clr-neutral-500">Current Phone </span>
						{userState?.phone}
						<form action="#" className="grid grid-cols-12 gap-4">

							<div className="col-span-12 lg:col-span-6">
								<label
									htmlFor="new-password"
									className="block mb-2 font-medium clr-neutral-500">
									Phone :
								</label>
								<input
									type="password"
									id="new-password"
									className="w-full focus:outline-none border py-3 px-6 rounded-lg"
									placeholder="Enter new password"

								/>
							</div>
							<div className="col-span-12">
								<div className="flex items-center gap-6 flex-wrap">
									<Link
										href="#"
										className="link inline-block py-3 px-6 rounded-full bg-primary text-white :bg-primary-400 hover:text-white font-semibold">
										Change Email
									</Link>
								</div>
							</div>
						</form>
					</div>
				</Accordion>

			</div>

		</div>
	);
};

export default page;
