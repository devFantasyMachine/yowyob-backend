"use client";

import { usePathname } from "next/navigation";
import Image from "next/image";
import Link from "next/link";
import {
	AdjustmentsHorizontalIcon,
	BellAlertIcon,
	ChatBubbleLeftRightIcon,
	CheckIcon,
	CreditCardIcon,
	HeartIcon,
	ShieldCheckIcon,
	UserCircleIcon,
	UserGroupIcon,
	WindowIcon,
} from "@heroicons/react/24/outline";
import { TenantHeader } from "@/components/tenant/tenant-header";
import React, { ChangeEvent, useEffect, useRef, useState } from "react";
import classNames from "classnames";
import { useAppDispatch, useAppSelector } from "@/lib/stores/hooks";
import { getUser, setUser } from "@/lib/stores/features/users/userSlice";
import { ObjectUtils } from "@/utils/object-utils";
import { fetchUser } from "@/services/user-service";
import Loader from "@/components/common/Loader";


interface NavItemProp {

	selected: boolean;
	label: string;
	icon: React.ReactNode,
	href: string,
	suffix?: React.ReactNode,

}

const NavItem: React.FC<NavItemProp> = ({ selected, label, icon, href, suffix }) => {

	return (
		<li className="relative container items-center float-left transition ease-in-out delay-150 hover:-translate-y-1 hover:scale-105 duration-300">
			{selected &&
				<div className="absolute top-[-5px] left-[-10px] w-10/12
       bg-indigo-700 opacity-10 h-[35px] 
        rounded-full"></div>
			}

			<Link href={href}
				className={`link flex items-center gap-2 clr-neutral-500 hover:text-primary ${selected && "text-primary font-bold"
					}`}>
				{icon}
				<span className="block font-medium"> {label} </span>
				{suffix}
			</Link>

		</li>
	)

}


export default function RootLayout({ children, }: { children: React.ReactNode; }) {

	const path = usePathname();
	const inputFileRef = useRef<HTMLInputElement>(null);
	const [imageSrc, setImageSrc] = useState<string>("/img/team-1.jpg");

	const handleImageClick = () => {
		if (inputFileRef.current) {
			inputFileRef.current.click();
		}
	};

	const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
		const files = event.target.files;
		if (files && files.length > 0) {
			const reader = new FileReader();
			reader.onload = () => {
				if (reader.result) {
					setImageSrc(reader.result.toString());
				}
			};
			reader.readAsDataURL(files[0]);
		}
	};

	const convertToTitleCase = (str: string) => {
		// Remove leading slash
		str = str.slice(1);

		// Split the string into words
		const words = str.split("-");

		// Capitalize the first letter of each word
		const capitalizedWords = words.map(
			(word) => word.charAt(0).toUpperCase() + word.slice(1)
		);

		// Join the words with a space
		const result = capitalizedWords.join(" ");

		return result;
	};

	const heading = convertToTitleCase(path);
	const [open, setOpen] = useState<boolean>(true);

	const dispatch = useAppDispatch();
	const userState = useAppSelector(getUser);


	const [loading, setLoading] = useState(true);

	const loadUser = async () => {

		if (ObjectUtils.isUndefined(userState)) {

			return await fetchUser()
				.then(user => {

					dispatch(setUser(user));
					console.log(user)
					setLoading(false);
				})
				.catch(() => {

					setTimeout(() => {

						//retry
						loadUser();

					}, 10000);

				})
		}
		else {
			setLoading(false);
		}

	}

	useEffect(() => {

		loadUser();

	}, [])


	if (loading) {

		return (
			<Loader />
		)
	}


	return (
		<>
			<TenantHeader user={userState} />
			<div className="py-[30px] lg:py-[60px] bg-indigo-700 px-3">
			</div>

			<div className="pb-[30px] lg:pb-[60px] pt-0 relative z-[1] px-3">
				<span className="w-full h-[7.5rem] absolute start-0 end-0 top-0 z-[-1] bg-indigo-700"></span>
				<div className="container">

					<div className="grid grid-cols-12 gap-4 lg:gap-6">

						<div
							style={{ zIndex: 2 }}
							className={classNames({
								"col-span-12 md:col-span-5 lg:col-span-4 xl:col-span-3": true, // layout 
								"md:sticky md:top-16 md:z-0 z-20": true, // positioning
								"md:h-[calc(100vh_-_64px)] h-full  ": true, // for height and width
								"transition-transform .3s ease-in-out md:-translate-x-0": true, //animations
								"-translate-x-full ": !open, //hide sidebar to the left when closed
							})}
						>
							<div className="p-3 sm:p-4 lg:p-6 rounded-2xl bg-white shadow-lg">

								<div className="w-32 h-32 border border-[var(--primary)] rounded-full bg-white p-4 grid place-content-center relative mx-auto mb-10 transition ease-in-out delay-150  hover:-translate-y-1 hover:scale-110 duration-300">
									<Image
										width={96}
										height={96}
										src="/img/team-1.jpg"
										alt="image"
										className="rounded-full"
									/>
									<div className="w-8 h-8 grid place-content-center rounded-full border-2 white text-white bg-primary absolute bottom-0 right-0">
										<CheckIcon className="w-5 h-5" />
									</div>
								</div>

								<div className="text-center mb-10">
									<h4 className="text-2xl font-semibold">{userState?.username}</h4>
									<p className="mb-0"> {userState?.email} </p>
								</div>
								<div className="mb-10">
									<ul className="flex flex-col gap-3">

										<NavItem
											selected={path === "/account"}
											label={"Applications"}
											icon={<WindowIcon className="w-5 h-5" />}
											href={"/account"} />

										<NavItem
											selected={path.includes("/account/userinfo")}
											label={"Personal info"}
											icon={<UserCircleIcon className="w-5 h-5" />}
											href={"/account/userinfo"} />

										<NavItem
											selected={path.includes("/account/user-notification")}
											label={"Inbox      "}
											icon={<BellAlertIcon className="w-5 h-5" />}
											href={"/account/user-notification"}
											suffix={
												<span className="grid place-content-center w-6 h-6 rounded-full bg-[var(--secondary-500)] text-white text-sm">
													1
												</span>} />

										<NavItem
											selected={path === "/account/user-preferences"}
											label={"Preferences"}
											icon={<AdjustmentsHorizontalIcon className="w-5 h-5" />}
											href={"/account/user-preference"} />


										<NavItem
											selected={path.includes("/account/user-payment")}
											label={"Payment & Subscription"}
											icon={<CreditCardIcon className="w-5 h-5" />}
											href={"/account/user-payment"} />

										<NavItem
											selected={path.includes("/account/user-security")}
											label={"Security & Privacy"}
											icon={<ShieldCheckIcon className="w-5 h-5" />}
											href={"/account/user-security"} />


										<li>
											<Link
												href="/help-center"
												className={`border-neutral-500 border-t pt-2 link flex items-center gap-2 clr-neutral-500 hover:text-primary ${path === "/user-team" && "text-primary"
													}`}>
												<HeartIcon className="w-5 h-5" />
												<span className="block font-medium"> Help </span>
											</Link>
										</li>

									</ul>
								</div>
							</div>
						</div>

						<div
							style={{ zIndex: 2 }}
							className="col-span-12 md:col-span-7 lg:col-span-8 xl:col-span-9">
							{children}
						</div>
					</div>
				</div>
			</div>

		</>
	);
}
