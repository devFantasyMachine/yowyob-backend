"use client"

import { getTenant } from '@/lib/stores/features/tenants/tenantSlice';
import { useParams, useRouter, useSearchParams } from 'next/navigation'
import { Suspense, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import Image from 'next/image'
import { fetchInvitation } from '@/services/useRegistration';
import Invitation from '@/types/invitation';
import { useAppDispatch } from '@/lib/stores/hooks';
import { acceptInvitation } from '@/services/auth-service';
import { setCookie } from 'cookies-next';
import { getRedirect } from '@/hooks/useCookie';
import { DEVICE_ID_COOKIE_NAME, SESSION_COOKIE_NAME } from '@/constants/app';
import { StringUtils } from '@/utils/string-utils';
import useAuth from '@/hooks/useAuth';
import { Card, CardContent } from '@/components/ui/card';
import { BugIcon, CheckIcon } from '@/components/ui/icon';

interface InvitationPage {
    params: { tenantShortName: string }
}




export default function InvitationPage({ params }: InvitationPage) {

    const tenantState = useSelector(getTenant);
    const dispatch = useAppDispatch();

    const [authResult, setAuthResult] = useAuth();

    const router = useRouter();

    const queryParams = useSearchParams();
    const id = queryParams.get('token');

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | undefined>(undefined);
    const [isAccepted, setIsAccepted] = useState(false);
    const [invitation, setInvitation] = useState<Invitation | undefined>(undefined);


    const handleAcceptInvitation = () => {

        setLoading(true)

        acceptInvitation(id!)
            .then((result) => {

                console.log(result);
                setLoading(false);
                setIsAccepted(true);

                if (result.requireAction) {

                    if (result.requireAction == "RESET_PASSWORD") {

                        setTimeout(() => {

                            router.replace("reset-password?token=" + result.verificationId);

                        }, 10000);

                    }

                }
                else {

                    setAuthResult(result);

                    setTimeout(() => {

                        router.push(getRedirect());

                    }, 10000);

                }

            })
            .catch((err: Error) => {
                setError(err.message);
                console.log(err.message);
            })
    }


    useEffect(() => {

        // validate invitation and check required action
        setLoading(true);

        fetchInvitation(id!)
            .then(_invitation => {

                setInvitation(_invitation);
                console.log(_invitation);
            })
            .catch((err: Error) => {
                setError(err.message);
                console.log(err.message);
            })

        setLoading(false)

    }, [])



    return (
        <section className='py-10 items-center justify-center flex flex-col bg-slate-200 h-full'>
            {invitation &&

                <div className="block items-center rounded-lg bg-white text-center shadow-lg dark:bg-neutral-700">

                    <div className="p-6">

                        <div className='px-7'>

                            <div className="container items-center flex flex-col mb-4">
                                <h5
                                    className="mb-2 text-2xl font-bold leading-tight text-neutral-800 dark:text-neutral-50">
                                    Invitation
                                </h5>
                                <p className="text-gray-500 dark:text-gray-400">Accept invitation before to continue.</p>

                            </div>

                            <p className="text-xl flex-col text-center items-center max-w-sm text-neutral-600 dark:text-neutral-200 flex">

                                <text className='font-semibold text-indigo-600 mr-2'>{invitation?.contact.value} </text>
                                you are invited to join {" "}

                                <text className='font-semibold px-2'>{tenantState?.organisation.name}</text>
                                admin's group by
                                <text className='font-bold px-2 text-xl'>{invitation?.sender.email} </text>

                                <text className='font-bold px-2 text-xl'>{StringUtils.formattedDate(new Date(invitation?.issueAt))} </text>
                            </p>

                        </div>

                        <div className='mb-2 items-center justify-center flex flex-col'>
                            <Image
                                className="justify-center dark:drop-shadow-[0_0_0.3rem_#ffffff70] dark:invert"
                                src="/invite.png"
                                alt="Next.js Logo"
                                width={200}
                                height={25}
                                priority
                            />

                        </div>

                        {loading &&

                            <div className="mt-4 flex flex-col items-center justify-center gap-10 bg-transparent">

                                <div className="h-16 w-16 animate-spin rounded-full border-4 border-solid border-primary border-t-transparent"></div>
                                <div className="flex flex-col gap-2"> 
                                    <span className="text-md text-center text-neutral-600">Please wait</span>
                                </div>
                            </div>
                        }

                        {isAccepted && <Card className="mx-auto max-w-[350px] space-y-6 mt-6">
                            <CardContent>
                                <div className="flex flex-row gap-4">
                                    <CheckIcon className="w-6 h-6 text-green-500" />
                                    <p className="text-green-500">Your invitation has been verified successfully!</p>
                                </div>
                            </CardContent>
                        </Card>}

                        {error && <Card className="mx-auto max-w-[350px] space-y-6 mt-6">
                            <CardContent>
                                <div className="flex flex-row gap-4">
                                    <BugIcon className="w-6 h-6 text-red-500" />
                                    <p className="text-red-500">Invalid.</p>
                                </div>
                            </CardContent>
                        </Card>}

                        {isAccepted == false && loading == false &&

                            <>
                                <button onClick={() => handleAcceptInvitation()}
                                    type="button"
                                    className="mb-2 bg-indigo-500 block w-full rounded bg-primary-700 px-6 pb-2 pt-2.5 text-xs font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]">
                                    Accept Invitation
                                </button>

                                <button onClick={() => 2}
                                    type="button"
                                    className="mb-2 bg-red-500 block w-full rounded bg-primary-700 px-6 pb-2 pt-2.5 text-xs font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]">
                                    Refuse Invitation
                                </button>
                            </>
                        }

                    </div>

                </div>
            }

            {
                !invitation &&

                <div role="status" className="max-w-sm p-4 border border-gray-200 rounded shadow animate-pulse md:p-6 dark:border-gray-700">
                    <div className="flex items-center justify-center h-48 mb-4 bg-gray-300 rounded dark:bg-gray-700">
                        <svg className="w-10 h-10 text-gray-200 dark:text-gray-600" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 16 20">
                            <path d="M14.066 0H7v5a2 2 0 0 1-2 2H0v11a1.97 1.97 0 0 0 1.934 2h12.132A1.97 1.97 0 0 0 16 18V2a1.97 1.97 0 0 0-1.934-2ZM10.5 6a1.5 1.5 0 1 1 0 2.999A1.5 1.5 0 0 1 10.5 6Zm2.221 10.515a1 1 0 0 1-.858.485h-8a1 1 0 0 1-.9-1.43L5.6 10.039a.978.978 0 0 1 .936-.57 1 1 0 0 1 .9.632l1.181 2.981.541-1a.945.945 0 0 1 .883-.522 1 1 0 0 1 .879.529l1.832 3.438a1 1 0 0 1-.031.988Z" />
                            <path d="M5 5V.13a2.96 2.96 0 0 0-1.293.749L.879 3.707A2.98 2.98 0 0 0 .13 5H5Z" />
                        </svg>
                    </div>
                    <div className="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-48 mb-4"></div>
                    <div className="h-2 bg-gray-200 rounded-full dark:bg-gray-700 mb-2.5"></div>
                    <div className="h-2 bg-gray-200 rounded-full dark:bg-gray-700 mb-2.5"></div>
                    <div className="h-2 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                    <div className="flex items-center mt-4">
                        <svg className="w-10 h-10 me-3 text-gray-200 dark:text-gray-700" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                            <path d="M10 0a10 10 0 1 0 10 10A10.011 10.011 0 0 0 10 0Zm0 5a3 3 0 1 1 0 6 3 3 0 0 1 0-6Zm0 13a8.949 8.949 0 0 1-4.951-1.488A3.987 3.987 0 0 1 9 13h2a3.987 3.987 0 0 1 3.951 3.512A8.949 8.949 0 0 1 10 18Z" />
                        </svg>
                        <div>
                            <div className="h-2.5 bg-gray-200 rounded-full dark:bg-gray-700 w-32 mb-2"></div>
                            <div className="w-48 h-2 bg-gray-200 rounded-full dark:bg-gray-700"></div>
                        </div>
                    </div>
                    <span className="sr-only">Loading...</span>
                </div>
            }

            {error &&

                <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
                    <strong className="font-bold">Error</strong>
                    <span className="block sm:inline">{` ${error}`}</span>
                    <span className="absolute top-0 bottom-0 right-0 px-4 py-3">
                        <svg className="fill-current h-6 w-6 text-red-500" role="button" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"><title>Close</title><path d="M14.348 14.849a1.2 1.2 0 0 1-1.697 0L10 11.819l-2.651 3.029a1.2 1.2 0 1 1-1.697-1.697l2.758-3.15-2.759-3.152a1.2 1.2 0 1 1 1.697-1.697L10 8.183l2.651-3.031a1.2 1.2 0 1 1 1.697 1.697l-2.758 3.152 2.758 3.15a1.2 1.2 0 0 1 0 1.698z" /></svg>
                    </span>
                </div>
            }

        </section>

    );

}




