"use client";
import Accordion from "@/components/Accordion";
import CheckboxCustom from "@/components/Checkbox";


import { ChevronDownIcon, PencilIcon } from "@heroicons/react/24/outline";
import Image from "next/image";
import Link from "next/link";
import { ChangeEvent, useRef, useState } from "react";
import { useAppDispatch, useAppSelector } from "@/lib/stores/hooks";
import { getUser, setUser, setUserProfile } from "@/lib/stores/features/users/userSlice";
import { Profile } from "@/types/user";
import { updateProfile } from "@/services/user-service";


const Page = () => {

	const userState = useAppSelector(getUser);
	const dispatch = useAppDispatch();

	const [profile, setProfile] = useState<Profile>(userState?.profile || {});

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


	const handleUpdateProfile = () => {

		updateProfile(profile)
			.then(profile => {

				dispatch(setUserProfile(profile))
			})
			.catch(err => {

			})

	}



	return (
		<>
			<div className="bg-white p-4 sm:p-6 md:p-8 mb-6 rounded-2xl shadow-lg">
				<Accordion
					buttonContent={(open) => (
						<div className="rounded-2xl flex justify-between items-center">
							<h4 className="text-2xl font-semibold">Basic Info </h4>
							<ChevronDownIcon
								className={`w-5 h-5 sm:w-6 sm:h-6 duration-300 ${open ? "rotate-180" : ""
									}`}
							/>
						</div>
					)}
					initialOpen={true}>

					<div className="pt-4 lg:pt-6">
						<div>



							{/* 							<div className="relative mx-auto ms-md-0 mb-6">
								<div className="avatar-upload__edit">
									<input
										type="file"
										id="imageUpload"
										accept=".png, .jpg, .jpeg"
										className="hidden"
										ref={inputFileRef}
										onChange={handleFileChange}
									/>
									<label
										htmlFor="imageUpload"
										className="avatar-upload__label"></label>
								</div>
								<div className="relative w-[180px] h-[180px]">
									<Image
										onClick={handleImageClick}
										width={180}
										height={180}
										className="rounded-full border-[6px] border-[#F5F5FE] shadow-md"
										src={imageSrc}
										alt="avatar"
									/>
									<span className="w-8 h-8 absolute cursor-pointer text-primary top-4 right-4 hover:bg-primary duration-300 hover:text-white rounded-full bg-white flex justify-center items-center border border-primary">
										<PencilIcon className="w-5 h-5" />
									</span>
								</div>

							</div>
 */}
							<form action="#" className="grid grid-cols-12 gap-4 bg-gray-100 p-6 rounded-xl">

								<div className="col-span-full">
									<label htmlFor="photo" className="block text-sm font-medium leading-6 text-gray-900">Photo</label>
									<div className="mt-2 flex items-center gap-x-3">
										<svg className="h-12 w-12 text-gray-300" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
											<path fill-rule="evenodd" d="M18.685 19.097A9.723 9.723 0 0021.75 12c0-5.385-4.365-9.75-9.75-9.75S2.25 6.615 2.25 12a9.723 9.723 0 003.065 7.097A9.716 9.716 0 0012 21.75a9.716 9.716 0 006.685-2.653zm-12.54-1.285A7.486 7.486 0 0112 15a7.486 7.486 0 015.855 2.812A8.224 8.224 0 0112 20.25a8.224 8.224 0 01-5.855-2.438zM15.75 9a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0z" clip-rule="evenodd" />
										</svg>
										<button type="button" className="rounded-md bg-white px-2.5 py-1.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">Change</button>
									</div>
								</div>

								<div className="col-span-full">
									<label htmlFor="cover-photo" className="block text-sm font-medium leading-6 text-gray-900">Cover photo</label>
									<div className="mt-2 flex justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10">
										<div className="text-center">
											<svg className="mx-auto h-12 w-12 text-gray-300" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
												<path fill-rule="evenodd" d="M1.5 6a2.25 2.25 0 012.25-2.25h16.5A2.25 2.25 0 0122.5 6v12a2.25 2.25 0 01-2.25 2.25H3.75A2.25 2.25 0 011.5 18V6zM3 16.06V18c0 .414.336.75.75.75h16.5A.75.75 0 0021 18v-1.94l-2.69-2.689a1.5 1.5 0 00-2.12 0l-.88.879.97.97a.75.75 0 11-1.06 1.06l-5.16-5.159a1.5 1.5 0 00-2.12 0L3 16.061zm10.125-7.81a1.125 1.125 0 112.25 0 1.125 1.125 0 01-2.25 0z" clip-rule="evenodd" />
											</svg>
											<div className="mt-4 flex text-sm leading-6 text-gray-600">
												<label htmlFor="file-upload" className="relative cursor-pointer rounded-md bg-white font-semibold text-indigo-600 focus-within:outline-none focus-within:ring-2 focus-within:ring-indigo-600 focus-within:ring-offset-2 hover:text-indigo-500">
													<span>Upload a file</span>
													<input id="file-upload" name="file-upload" type="file" className="sr-only" />
												</label>
												<p className="pl-1">or drag and drop</p>
											</div>
											<p className="text-xs leading-5 text-gray-600">PNG, JPG, GIF up to 10MB</p>
										</div>
									</div>
								</div>

								<div className="col-span-12 lg:col-span-6">
									<label
										htmlFor="first-name"
										className="block text-sm font-medium leading-6 text-gray-900">
										First name
									</label>
									<input
										type="text"
										id="first-name"
										className="border w-full focus:outline-none py-2 px-4 rounded-lg"
										placeholder="Enter name"
										value={profile?.firstName}
										onChange={(e) => {

											e.preventDefault();
											setProfile({ ...profile, "firstName": e.target.value })

										}}
									/>
								</div>
								<div className="col-span-12 lg:col-span-6">
									<label
										htmlFor="last-name"
										className="block text-sm font-medium leading-6 text-gray-900">
										Last name
									</label>
									<input
										type="text"
										id="last-name"
										className="border w-full focus:outline-none py-2 px-4 rounded-lg"
										placeholder="Enter last name"
										value={profile.lastName}
										onChange={(e) => {

											e.preventDefault();
											setProfile({ ...profile, "lastName": e.target.value })

										}}
									/>
								</div>
								<div className="col-span-12 lg:col-span-6">
									<label
										htmlFor="birthdate"
										className="block text-sm font-medium leading-6 text-gray-900">
										Birthdate
									</label>
									<input
										type="date"
										id="birthdate"
										value={profile.birthdate}
										className="border w-full focus:outline-none py-2 px-4 rounded-lg"
										placeholder="Enter name"
										onChange={(e) => {

											e.preventDefault();
											setProfile({ ...profile, "birthdate": e.target.value })

										}}

									/>
								</div>
								<div className="col-span-12 lg:col-span-6">
									<label className="block text-sm font-medium leading-6 text-gray-900">
										Gender
									</label>
									<ul className="flex flex-wrap pt-2 items-center gap-6">
										<li>
											<div className="flex items-center gap-2">
												<input
													className="accent-[var(--primary)] scale-125"
													type="radio"
													name="gender"
													id="male"
													checked={profile.gender == "male"}
													onChange={(e) => {

														e.preventDefault();
														setProfile({ ...profile, "gender": "male" })

													}}
												/>
												<label
													className="inline-block font-medium cursor-pointer clr-neutral-500"
													htmlFor="male">
													Male
												</label>
											</div>
										</li>
										<li>
											<div className="flex items-center gap-2">
												<input
													className="accent-[var(--primary)] scale-125"
													type="radio"
													name="gender"
													id="female"
													checked={profile.gender == "female"}
													onChange={(e) => {

														e.preventDefault();
														setProfile({ ...profile, "gender": "female" })

													}}
												/>
												<label
													className="inline-block font-medium cursor-pointer clr-neutral-500"
													htmlFor="female">
													Female
												</label>
											</div>
										</li>
									</ul>
								</div>

								<div className="col-span-12">
									<label className="block text-sm font-medium leading-6 text-gray-900">
										About Me
									</label>
									<textarea
										onChange={(e) => {

											e.preventDefault();
											setProfile({ ...profile, "about": e.target.value })
										}}

										rows={3}
										value={profile.about}
										placeholder="Write your bio"
										className="border w-full focus:outline-none py-2 px-4 rounded-xl"></textarea>
								</div>
								<div className="col-span-12">
									<div className="flex items-center gap-6 flex-wrap">
										<button

											onClick={(e) => {

												e.preventDefault();
												handleUpdateProfile();
											}}

											className="py-2 px-4 rounded-full bg-indigo-500 text-white hover:bg-indigo-400 hover:text-white font-semibold">
											Save Changes
										</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</Accordion>
			</div>
			<div className="bg-white p-4 sm:p-6 md:p-8 mb-6 rounded-2xl shadow-lg">
				<Accordion
					buttonContent={(open) => (
						<div className="rounded-2xl flex justify-between items-center">
							<h3 className="h3">Address </h3>
							<ChevronDownIcon
								className={`w-5 h-5 sm:w-6 sm:h-6 duration-300 ${open ? "rotate-180" : ""
									}`}
							/>
						</div>
					)}
					initialOpen={true}>
					<div className="pt-4 lg:pt-6">
						<form action="#" className="grid grid-cols-12 gap-4 border-b border-gray-900/10 pb-12">

							 
								<div className="sm:col-span-3">
									<label htmlFor="country" className="block text-sm font-medium leading-6 text-gray-900">Country</label>
									<div className="mt-2">
										<select id="country" name="country" autoComplete="country-name" className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:max-w-xs sm:text-sm sm:leading-6">
											<option>United States</option>
											<option>Canada</option>
											<option>Mexico</option>
										</select>
									</div>
								</div>

								<div className="sm:col-span-2 sm:col-start-1">
									<label htmlFor="city" className="block text-sm font-medium leading-6 text-gray-900">City</label>
									<div className="mt-2">
										<input type="text" name="city" id="city" autoComplete="address-level2" className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
									</div>
								</div>

								<div className="sm:col-span-2">
									<label htmlFor="region" className="block text-sm font-medium leading-6 text-gray-900">State / Province</label>
									<div className="mt-2">
										<input type="text" name="region" id="region" autoComplete="address-level1" className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
									</div>
								</div>

								<div className="sm:col-span-2">
									<label htmlFor="postal-code" className="block text-sm font-medium leading-6 text-gray-900">ZIP / Postal code</label>
									<div className="mt-2">
										<input type="text" name="postal-code" id="postal-code" autoComplete="postal-code" className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
									</div>
								</div>

								<div className="col-span-full">
									<label htmlFor="street-address" className="block text-sm font-medium leading-6 text-gray-900">Street address</label>
									<div className="mt-2">
										<input type="text" name="street-address" id="street-address" autoComplete="street-address" className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
									</div>
								</div>

 
 

							<div className="col-span-12">
								<div className="flex items-center gap-6 flex-wrap">
									<Link
										href="#"
										className="link inline-block py-2 px-4 rounded-full bg-primary text-white :bg-primary-400 hover:text-white font-semibold">
										Save Changes
									</Link> 
								</div>
							</div>
						</form>
					</div>
				</Accordion >
			</div >
		</>
	);
};

export default Page;
