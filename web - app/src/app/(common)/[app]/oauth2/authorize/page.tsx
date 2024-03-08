"use client"


import axiosAPIClient, { axiosAPIAuthorizedClient } from "@/config/http-client";
import { SESSION_COOKIE_NAME } from "@/constants/app";
import { getCurrentApp } from "@/lib/stores/features/apps/appSlice";
import { useAppSelector } from "@/lib/stores/hooks";
import { AxiosError } from "axios";
import { getCookie, setCookie } from "cookies-next";
import Link from "next/link"
import { RedirectType, permanentRedirect, redirect, useRouter, useSearchParams } from "next/navigation"
import React, { useEffect, useState } from "react";


interface AuthorizeRequest {
    scope: Array<string> | null | undefined,
    response_type: string | null,
    state: string | null,
    redirect_uri: string | null,
    client_id: string | null,
    nonce: string | null,
}


interface ScopeWithDescription {

    scope: string,
    description: string
}

interface ConsentRequest {

    scope: Array<ScopeWithDescription>,
    previouslyApprovedScopes: Array<ScopeWithDescription>,
    state: string,
    userId: string,
    clientId: string,
    redirect_uri: string

}



export default function Component() {

    const router = useRouter();
    const queryParams = useSearchParams();

	const currentApp = useAppSelector(getCurrentApp);



    const [requireConsent, setRequireConsent] = useState(false);

    const [redirectUri, setRedirectUri] = useState<string | undefined>(undefined);
    const [error, setError] = useState<string | undefined>(undefined);

    const [consentRequest, setConsentRequest] = useState<ConsentRequest | undefined>();

    const request: AuthorizeRequest = {
        scope: queryParams.get('scope')?.split(" "),
        response_type: queryParams.get('response_type'),
        state: queryParams.get('state'),
        redirect_uri: queryParams.get('redirect_uri'),
        client_id: queryParams.get('client_id'),
        nonce: queryParams.get('nonce'),
    }

    const getQuery = (request: AuthorizeRequest) => {

        let query = "scope=" + request.scope?.join(" ");

        Object.keys(request)
            .filter((key) => key !== "scope")
            .forEach((key) => {
                query = query + "&" + key + "=" + Object.create(request)[key];
            });
        return query;
    }


    const handleConsent = () => {

        axiosAPIAuthorizedClient.post("/oauth2/authorize?client_id=" + consentRequest?.clientId + "&scope=profile&state=" + consentRequest?.state)
            .then(res => res.data)
            .then(res => {

                console.log(res.redirect_uri);
                setRedirectUri(res.redirect_uri); 
            })
            .catch((err: AxiosError) => {

                console.log(err)
            })

    }


    useEffect(() => {
 
        if(redirectUri){

            permanentRedirect(redirectUri, RedirectType.replace);
        }

    }, [redirectUri]);
    


    useEffect(() => {

        // consent is post request without response_type : client_id, state, scope,
        //axiosAPIAuthorizedClient.post("/oauth", request)
        axiosAPIAuthorizedClient.get<ConsentRequest>("/oauth2/authorize?" + getQuery(request))
            .then(res => res.data)
            .then(res => {

                if (res.redirect_uri) {

                    console.log(res.redirect_uri)
                    setRedirectUri(res.redirect_uri);
                }
                else {

                    setRequireConsent(true);
                    setConsentRequest(res);
                }

            })
            .catch((err: AxiosError) => {

                console.log(err.response)
            })


    }, [])



    return (
        <>
            <main className="bg-white dark:bg-black min-h-screen py-12 px-4 sm:px-6 lg:px-8">


                <div className="relative z-10" aria-labelledby="modal-title" role="dialog" aria-modal="true">

                    <div className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"></div>

                    <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
                        <div className="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">

                            {requireConsent && <div className="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">

                                <div className="bg-white px-4 pb-4 pt-5 sm:p-6 sm:pb-4">
                                    <div className="sm:flex sm:items-start">
                                        <div className="mx-auto flex h-12 w-12 flex-shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:h-10 sm:w-10">
                                            <svg className="h-6 w-6 text-red-600" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" aria-hidden="true">
                                                <path strokeLinecap="round" strokeLinejoin="round" d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126zM12 15.75h.007v.008H12v-.008z" />
                                            </svg>
                                        </div>
                                        <div className="mt-3 text-center sm:ml-4 sm:mt-0 sm:text-left">
                                            <h3 className="text-base font-semibold leading-6 text-gray-900" id="modal-title">Yowyob Auth</h3>
                                            <div className="mt-2">
                                                <p className="text-sm text-gray-500">Are you sure you want to deactivate your account? All of your data will be permanently removed. This action cannot be undone.</p>

                                                {
                                                    consentRequest &&
                                                    <>

                                                        {
                                                            consentRequest.scope && consentRequest.scope.map((scope, index) => (

                                                                <div key={index} className="my-4 flex flex-col mb-4 gap-2">

                                                                    <div className="flex items-center gap-2">
                                                                        <input id="default-checkbox" type="checkbox" value="" className="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600" />
                                                                        <label htmlFor="default-checkbox" className="ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">{scope.scope}</label>
                                                                    </div>

                                                                    <span className="text-base font-semibold text-gray-900">{scope.description}</span>

                                                                </div>
                                                            ))
                                                        }

                                                    </>
                                                }




                                            </div>
                                        </div>
                                    </div>
                                </div>


                                <div className="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                                    <button onClick={(e) => {

                                        e.stopPropagation();
                                        e.preventDefault();

                                        handleConsent()

                                    }}
                                        type="button"
                                        className="inline-flex w-full justify-center rounded-md bg-green-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-green-500 sm:ml-3 sm:w-auto">Confirm</button>
                                    <button type="button" className="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto">Cancel</button>
                                </div>
                            </div>
                            }
                        </div>
                    </div>
                </div>

            </main>
        </>
    )


}

