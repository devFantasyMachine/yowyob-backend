"use client";

import useAuth from "@/hooks/useAuth";
import { getRedirect } from "@/hooks/useCookie";
import { AuthenticationResult, resetPassword } from "@/services/auth-service";
import { useRouter, useSearchParams } from "next/navigation";
import { useState } from "react";





const Page = () => {

    const queryParams = useSearchParams();
    const id = queryParams.get('token');

    const router = useRouter();

    const [authResult, setAuthResult] = useAuth();

    const [isSuccess, setIsSuccess] = useState(false);
    const [error, setError] = useState<string | undefined>(undefined);
    const [submitting, setSubmitting] = useState(false);

    const [password, setPassword] = useState<string | undefined>(undefined);


    const handleResetPassword = () => {

        setSubmitting(true);
        setError(undefined);

        resetPassword({
            password: password!,
            verificationId: id!
        })
            .then((result) => {

                console.log(result);
                setAuthResult(result);

                setTimeout(() => {

                    router.push(getRedirect());

                }, 1000);


                setSubmitting(false);
                setIsSuccess(true);

            })
            .catch((err: Error) => {

                setSubmitting(false);
                setError(err.message);
            })

    }




    return (
        <section className="bg-slate-200 dark:bg-gray-900">
            <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">

                <div className="w-full p-6 bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md dark:bg-gray-800 dark:border-gray-700 sm:p-8">
                    <h2 className="mb-1 text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
                        Change Password
                    </h2>
                    <form className="mt-4 space-y-4 lg:mt-5 md:space-y-5" action="#">
                        <div>
                            <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">New Password</label>
                            <input

                                onChange={(e) => {
                                    e.preventDefault();

                                    setPassword(e.target.value);
                                }}

                                type="password" name="password" id="password" placeholder="••••••••" className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required />
                        </div>
                        <div>
                            <label htmlFor="confirm-password" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Confirm password</label>
                            <input type="confirm-password" name="confirm-password" id="confirm-password" placeholder="••••••••" className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required />
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

                            onClick={(e) => {
                                e.stopPropagation();
                                handleResetPassword();
                            }}

                            className="w-full text-white bg-indigo-600 hover:bg-indigo-700 focus:ring-4 focus:outline-none focus:ring-indigo-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-indigo-600 dark:hover:bg-indigo-700 dark:focus:ring-indigo-800">Reset passwod</button>
                    </form>
                </div>
            </div>
        </section>
    );
};



export default Page;


 