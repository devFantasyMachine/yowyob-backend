"use client"


import { PropsWithChildren, useEffect, useState } from 'react';  
import { useAppDispatch, useAppSelector } from '@/lib/stores/hooks'; 

import { ObjectUtils } from '@/utils/object-utils';
import Loader from '@/components/common/Loader';
import { getCurrentApp, setCurrentApp } from '@/lib/stores/features/apps/appSlice';
import { fetchAppById } from '@/services/app-service';
import { useParams } from 'next/navigation';




export default function TenantLayout({ children }: PropsWithChildren) {

	const params = useParams();
	const app = params["app"] as string

	const dispatch = useAppDispatch();
	const currentApp = useAppSelector(getCurrentApp);

	const [loading, setLoading] = useState(true);

	const loadApp = async () => {

		if (ObjectUtils.isUndefined(currentApp) || currentApp!.id != app) {

			return await fetchAppById(app)
				.then(app => {

					dispatch(setCurrentApp(app));
					setLoading(false);
				})
				.catch(() => {

					setTimeout(() => {

						//retry
						loadApp();

					}, 2000);

				})
		}
		else {

			setLoading(false);
		}

	}



	useEffect(() => {

		loadApp();

	}, [])


	return (
		<div className="">
			{loading ? (
				<Loader timeout={20000} message='Check your connexion' />
			) : (

				<main className='flex-grow flex-1'>
					{children}
				</main>
			)}
		</div>
	);

}



