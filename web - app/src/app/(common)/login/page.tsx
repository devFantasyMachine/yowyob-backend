"use client"

import { getTenant } from '@/lib/stores/features/tenants/tenantSlice';
import { useParams, useRouter, useSearchParams } from 'next/navigation'
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useAppDispatch } from '@/lib/stores/hooks';


import { Label } from "@/components/ui/label"
import { Input, InputPassword } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { ChromeIcon, FacebookIcon, MailboxIcon, PhoneIcon, TwitterIcon, UserIcon } from '@/components/ui/icon';
import Link from 'next/link';
import { AuthenticationResult, LoginRequest, login } from '@/services/auth-service';
import { getRedirect } from '@/hooks/useCookie';
import { setCookie } from 'cookies-next';
import { DEVICE_ID_COOKIE_NAME, SESSION_COOKIE_NAME } from '@/constants/app';
import { setUser } from '@/lib/stores/features/users/userSlice';
import useAuth from '@/hooks/useAuth';




export default function LoginPage() {

	const tenantState = useSelector(getTenant);

	const router = useRouter();

	const [authResult, setAuthResult] = useAuth();



	const queryParams = useSearchParams();
	const id = queryParams.get('client_id');
	const challenge = queryParams.get('redirect_uri');


	const [loginRequest, setLoginRequest] = useState<LoginRequest>({});
	const [error, setError] = useState<string | undefined>(undefined);
	const [submitting, setSubmitting] = useState(false);



	const handleLogin = () => {

		// validate invitation and check required action
		setSubmitting(true);
		setError(undefined);

		login(loginRequest)
			.then((result) => {

				console.log(result);
				setAuthResult(result);

				setTimeout(() => {

					router.push(getRedirect());

				}, 1000);

			})
			.catch((err: Error) => {

				setError(err.message);
				setSubmitting(false);
			})


	}



	return (

		<section className="bg-indigo-500  dark:bg-neutral-900 py-20">

			<div className="flex flex-col items-center justify-center px-6 py-8 mx-auto lg:py-0">

				<div className="z-50 mx-auto max-w-lg space-y-8 p-6 bg-white dark:bg-black rounded-lg shadow-sm">
					<div className="space-y-2 text-center">
						<h1 className="text-4xl font-bold">Login</h1>
						<p className="text-gray-500 dark:text-gray-400">Please login to continue.</p>
					</div>

					<div className="space-y-6">

						<div className="space-y-2">
							<Label htmlFor="username">Email</Label>
							<Input id="username" type='email' placeholder="Email" required
								onChange={(value) => setLoginRequest({ ...loginRequest, "username": value })}
							/>
						</div>

						<div className="space-y-2">
							<Label htmlFor="password">Password</Label>
							<InputPassword id="password" required
								onChange={(value) => setLoginRequest({ ...loginRequest, "password": value })}
							/>
						</div>

						{error &&

							<div className="bg-red-100 border border-red-400 text-red-700 px-3 py-3 rounded relative" role="alert">
								<span className="block sm:inline">{` ${error}`}</span>
								<span className="absolute top-0 bottom-0 right-0 px-3 py-2">
									<svg
										onClick={(e) => {
											e.stopPropagation();
											setError(undefined);
										}}
										className="fill-current h-6 w-6 text-red-500" role="button" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"><title>Close</title><path d="M14.348 14.849a1.2 1.2 0 0 1-1.697 0L10 11.819l-2.651 3.029a1.2 1.2 0 1 1-1.697-1.697l2.758-3.15-2.759-3.152a1.2 1.2 0 1 1 1.697-1.697L10 8.183l2.651-3.031a1.2 1.2 0 1 1 1.697 1.697l-2.758 3.152 2.758 3.15a1.2 1.2 0 0 1 0 1.698z" /></svg>
								</span>
							</div>
						}

						<button
							disabled={submitting}
							onClick={(e) => {
								e.stopPropagation();
								handleLogin();
							}}

							className="w-full inline-flex items-center justify-center text-white bg-indigo-600 hover:bg-indigo-700 focus:ring-4 focus:outline-none focus:ring-indigo-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-indigo-600 dark:hover:bg-indigo-700 dark:focus:ring-indigo-800">

							{submitting &&

								<svg
									aria-hidden="true" role="status" className="inline w-4 h-4 me-3 text-white animate-spin" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
									<path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="#E5E7EB" />
									<path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentColor" />
								</svg>

							}
							{`${submitting ? "Loading..." : "Login"}`}

						</button>


						<p className="text-sm font-light text-neutral-500 dark:text-neutral-400">
							Don't have an account ? <a href="/register" className="ml-1 font-medium text-indigo-600 hover:underline dark:text-indigo-500">Register here</a>
						</p>

						<div className="text-center">
							<h2 className="text-xl font-bold">Or</h2>
							<div className="flex justify-center gap-4 mt-4">
								<Button className="flex items-center gap-2 bg-neutral-100 hover:bg-neutral-100" variant="outline">
									<ChromeIcon className="w-5 h-5 " /> 
									Google{"\n                  "}
								</Button>
								<Button className="flex items-center gap-2 bg-neutral-100 hover:bg-neutral-100" variant="outline">
									<FacebookIcon className="w-5 h-5" />
									Facebook{"\n                  "}
								</Button>
								<Button className="flex items-center gap-2 bg-neutral-100 hover:bg-neutral-100" variant="outline">
									<TwitterIcon className="w-5 h-5" />
									Twitter{"\n                  "}
								</Button>
							</div>
						</div>

					</div>
				</div>

			</div>
		</section >


	);

}



