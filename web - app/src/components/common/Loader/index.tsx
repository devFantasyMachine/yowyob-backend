import { BugIcon } from "@/components/ui/icon";
import React, { useEffect } from "react";



interface LoaderProps {

	timeout?: number,
	message?: string,
	inner?: any

}


const Loader: React.FC<LoaderProps> = ({ timeout, message }) => {

	const [wait, setWait] = React.useState(true);

	useEffect(() => {

		if (timeout) {

			setTimeout(() => {

				setWait(false);

			}, timeout);
		}

	}, []);


	return (
		<div className="flex flex-col h-screen w-screen items-center justify-center gap-10 bg-white">

			{wait ?
				(
					<>
						<div className="h-16 w-16 animate-spin rounded-full border-4 border-solid border-primary border-t-transparent"></div>
						<div className="flex flex-col gap-2">
							<span className="text-3xl font-bold ">Loading...</span>
							<span className="text-md text-center text-neutral-600">Please wait</span>
						</div>
					</>
				)
				:
				(
					<div className="flex flex-row gap-4 items-center content-center justify-center ">
						<BugIcon className="w-10 h-10 text-red-500" />
						<p className="text-red-500">{message}</p>
					</div>
				)
			}

		</div>
	);
};


export default Loader;
