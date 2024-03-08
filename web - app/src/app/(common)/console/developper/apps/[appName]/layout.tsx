"use client"


import { PropsWithChildren, useEffect, useState } from 'react';
import { useAppDispatch, useAppSelector } from '@/lib/stores/hooks';

import { ObjectUtils } from '@/utils/object-utils';
import Loader from '@/components/common/Loader';
import { getCurrentApp, setCurrentApp } from '@/lib/stores/features/apps/appSlice';
import { fetchAppById } from '@/services/app-service';
import { useParams } from 'next/navigation';




export default function AppLayout({ children }: PropsWithChildren) {

	const params = useParams();

	const app = params["appName"] as string

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

				<div className='flex-grow flex-1 flex flex-col'>

					<div className='shadow-default border items-center flex flex-row h-10 p-4 m-4 rounded-lg bg-slate-200'>
						<span>Users</span>
					</div>

					<main className='flex-grow'>
						{children}
					</main>
				</div>
			)}
		</div>
	);

}



