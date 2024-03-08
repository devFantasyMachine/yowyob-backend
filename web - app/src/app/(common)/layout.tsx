"use client"

import './../globals.css'
import { PropsWithChildren, useEffect, useState } from 'react';
import { Footer } from '@/components/tenant/tenant-footer';
import { getTenant, setTenant } from '@/lib/stores/features/tenants/tenantSlice';
import { useAppDispatch, useAppSelector } from '@/lib/stores/hooks';
import { fetchTenant } from '@/services/tenant-service';

import { ObjectUtils } from '@/utils/object-utils';
import Loader from '@/components/common/Loader'; 




export default function TenantLayout({ children }: PropsWithChildren) {

	const dispatch = useAppDispatch();
	const tenantState = useAppSelector(getTenant); 

	const [loading, setLoading] = useState(true);

	const loadTenant = async () => {

		if (ObjectUtils.isUndefined(tenantState)) {

			return await fetchTenant()
				.then(tenant => {

					dispatch(setTenant(tenant));
					setLoading(false);
				})
				.catch(() => {

					setTimeout(() => {

						//retry
						loadTenant();

					}, 2000);

				})
		}
		else {

			setLoading(false);
		}

	}

	

	useEffect(() => {

		loadTenant();

	}, [])


	return (
		<div className="">
			{loading ? (
				<Loader timeout={120000} message='Check your connexion' />
			) : (

				<div className="flex flex-col justify-between"> 
					<div className="flex-grow flex-1">
						<main className='flex-grow flex-1'>
							{children}
						</main>
					</div>
					<Footer tenant={tenantState} />
				</div>
			)}
		</div>
	);

}



