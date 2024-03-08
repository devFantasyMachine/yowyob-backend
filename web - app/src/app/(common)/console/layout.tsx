"use client";

import { PropsWithChildren } from "react";
 

const Layout = (props: PropsWithChildren) => {

	return (
		<div className=""> 
			{props.children}
		</div>
	);

};

export default Layout;

