"use client";

import { PropsWithChildren, useEffect, useState } from "react";

import Navbar from "./Navbar";
import Sidebar from "./Sidebar";
import { usePathname, useRouter } from "next/navigation";



const Layout = (props: PropsWithChildren) => {

	const path = usePathname();
	const router = useRouter();
	const [showSidebar, setShowSidebar] = useState(true);


	useEffect(() => {

		if (path === "/console/developper") {

			router.replace(path + '/apps')
		}

	}, []);


	if (path === "/console/developper"
		|| path === "/console/developper/apps/add-app"
		|| path === "/console/developper/apps") {

		return <div className="min-h-screen bg-zinc-100">
			<div className="bg-white shadow-sm">
				<Navbar onMenuButtonClick={() => setShowSidebar((prev) => !prev)} />
			</div>
			<div className="">
				{props.children}
			</div>
		</div>
	}



	return (
		<div className="grid min-h-screen grid-rows-1 bg-zinc-100">
			<div className="bg-slate-200 shadow-sm z-10">
				<Navbar onMenuButtonClick={() => setShowSidebar((prev) => !prev)} />
			</div>
			<div className="grid grid-cols-5 md:grid-cols-10 lg:grid-cols-12 ">
				<div className="md:col-span-3 ">
					<Sidebar open={showSidebar} setOpen={setShowSidebar} />
				</div>

				<div className="col-span-5  md:col-span-7  lg:col-span-9">
					{props.children}
				</div>

			</div>
		</div>
	);

};


export default Layout;

