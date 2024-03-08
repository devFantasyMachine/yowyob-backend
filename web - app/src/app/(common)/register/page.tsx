"use client"


import { NextPage } from 'next'
import { faEnvelope, faUser } from '@fortawesome/free-regular-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faLock } from '@fortawesome/free-solid-svg-icons'


import { useRouter } from 'next/navigation'
import { SyntheticEvent, useEffect, useState } from 'react'
import { deleteCookie, getCookie } from 'cookies-next'
import axios from 'axios'
import { Label } from '@/components/ui/label'
import { CheckboxCustom, Input, InputPassword } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import { useAppDispatch, useAppSelector } from '@/lib/stores/hooks'
import { getTenant } from '@/lib/stores/features/tenants/tenantSlice'

import { registerByEmail } from '@/services/useRegistration'
import { RegistrationRequest } from '@/types/registration'
import axiosAPIClient from '@/config/http-client'



const Register: NextPage = () => {


    const router = useRouter();

    const dispatch = useAppDispatch();
    const tenantState = useAppSelector(getTenant);

    const [registration, setRegistration] = useState<RegistrationRequest>({});


    const [isSuccess, setIsSuccess] = useState(false);
    const [submitting, setSubmitting] = useState(false);



    const getRedirect = () => {
        const redirect = getCookie('redirect')
        if (redirect) {
            deleteCookie('redirect')
            return redirect.toString()
        }

        return '/'
    }


    const register = async (e: SyntheticEvent) => {

        e.stopPropagation();
        e.preventDefault();

        setSubmitting(true);

        console.log(registration);

        registerByEmail(registration)
            .then(res => {

                setSubmitting(false);
                setIsSuccess(true);

                console.log(res);

                if (res.verificationId) {

                    if (!tenantState?.tenantRegistrationConfig.useMagicLink) {

                        setTimeout(() => {

                            router.push("/email-verification?verificationId=" + res.verificationId);

                        }, 5000);
                    }
                }
                else {

                    //login

                }
            })
            .catch((err: Error) => {

                console.log("error " + err.message);
                setSubmitting(false);
            })


    }



    return (
        <section className="bg-indigo-500 dark:bg-neutral-900 py-20">
            <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto lg:py-0">

                <a href="#" className="flex items-center mb-6 text-2xl font-semibold text-neutral-900 dark:text-white">
                    Yowyob
                </a>

                {isSuccess == false ?

                    (
                        <div className="mx-auto max-w-lg space-y-8 p-6 bg-white dark:bg-black rounded-lg shadow-sm">
                            <div className="space-y-2 text-center">
                                <h1 className="text-4xl font-bold">Create Account</h1>
                                <p className="text-gray-500 dark:text-gray-400">Please fill out the information below to register.</p>
                            </div>

                            <div className="space-y-6">

                                {tenantState?.tenantRegistrationConfig.firstName.required &&
                                    <div className="grid sm:grid-cols-1 md:grid-cols-2 gap-4">
                                        <div className="space-y-2">
                                            <Label htmlFor="first-name">First Name</Label>
                                            <Input id="first-name" placeholder="First Name" required />
                                        </div>
                                        <div className="space-y-2">
                                            <Label htmlFor="last-name">Last Name</Label>
                                            <Input id="last-name" placeholder="Last Name" required />
                                        </div>
                                    </div>
                                }

                                {tenantState?.tenantRegistrationConfig.phoneNumber.required &&
                                    <div className="space-y-2">
                                        <Label htmlFor="username">Username</Label>
                                        <Input id="username" placeholder="Username" required />
                                    </div>
                                }

                                {tenantState?.tenantRegistrationConfig.birthdate.required &&
                                    <div className="space-y-2">
                                        <Label htmlFor="birthdate">Birthdate</Label>
                                        <Input id="birthdate" required type="date" />
                                    </div>
                                }

                                {tenantState?.tenantRegistrationConfig.gender.required &&
                                    <div className="space-y-2 ">
                                        <div className="">
                                            <Label htmlFor="first-name">Gender</Label>
                                        </div>
                                        <div className="grid grid-flow-row gap-4">
                                            <div className="flex flex-row gap-4">
                                                <CheckboxCustom label='Male' />
                                                <CheckboxCustom label='Female' />
                                            </div>
                                        </div>
                                    </div>
                                }

                                {tenantState?.tenantRegistrationConfig.email.required &&
                                    <div className="space-y-2">
                                        <Label htmlFor="email">Email</Label>
                                        <Input id="email" required type="email"
                                            onChange={(value) => setRegistration({ ...registration, "email": value })} />
                                    </div>
                                }

                                {tenantState?.tenantRegistrationConfig.phoneNumber.required &&
                                    <div className="space-y-2">

                                        <Label htmlFor="phone">Phone</Label>
                                        <Input id="phone" required type="phone" />

                                        <div className="flex items-center">
                                            <button id="dropdown-phone-button" data-dropdown-toggle="dropdown-phone" className="flex-shrink-0 z-10 inline-flex items-center py-2.5 px-4 text-sm font-medium text-center text-gray-900 bg-gray-100 border border-gray-300 rounded-s-lg hover:bg-gray-200 focus:ring-4 focus:outline-none focus:ring-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:focus:ring-gray-700 dark:text-white dark:border-gray-600" type="button">
                                                <svg fill="none" aria-hidden="true" className="h-4 w-4 me-2" viewBox="0 0 20 15"><rect width="19.6" height="14" y=".5" fill="#fff" rx="2" /><mask id="a" width="20" height="15" x="0" y="0" maskUnits="userSpaceOnUse"><rect width="19.6" height="14" y=".5" fill="#fff" rx="2" /></mask><g mask="url(#a)"><path fill="#D02F44" fill-rule="evenodd" d="M19.6.5H0v.933h19.6V.5zm0 1.867H0V3.3h19.6v-.933zM0 4.233h19.6v.934H0v-.934zM19.6 6.1H0v.933h19.6V6.1zM0 7.967h19.6V8.9H0v-.933zm19.6 1.866H0v.934h19.6v-.934zM0 11.7h19.6v.933H0V11.7zm19.6 1.867H0v.933h19.6v-.933z" clip-rule="evenodd" /><path fill="#46467F" d="M0 .5h8.4v6.533H0z" /><g filter="url(#filter0_d_343_121520)"><path fill="url(#paint0_linear_343_121520)" fill-rule="evenodd" d="M1.867 1.9a.467.467 0 11-.934 0 .467.467 0 01.934 0zm1.866 0a.467.467 0 11-.933 0 .467.467 0 01.933 0zm1.4.467a.467.467 0 100-.934.467.467 0 000 .934zM7.467 1.9a.467.467 0 11-.934 0 .467.467 0 01.934 0zM2.333 3.3a.467.467 0 100-.933.467.467 0 000 .933zm2.334-.467a.467.467 0 11-.934 0 .467.467 0 01.934 0zm1.4.467a.467.467 0 100-.933.467.467 0 000 .933zm1.4.467a.467.467 0 11-.934 0 .467.467 0 01.934 0zm-2.334.466a.467.467 0 100-.933.467.467 0 000 .933zm-1.4-.466a.467.467 0 11-.933 0 .467.467 0 01.933 0zM1.4 4.233a.467.467 0 100-.933.467.467 0 000 .933zm1.4.467a.467.467 0 11-.933 0 .467.467 0 01.933 0zm1.4.467a.467.467 0 100-.934.467.467 0 000 .934zM6.533 4.7a.467.467 0 11-.933 0 .467.467 0 01.933 0zM7 6.1a.467.467 0 100-.933.467.467 0 000 .933zm-1.4-.467a.467.467 0 11-.933 0 .467.467 0 01.933 0zM3.267 6.1a.467.467 0 100-.933.467.467 0 000 .933zm-1.4-.467a.467.467 0 11-.934 0 .467.467 0 01.934 0z" clip-rule="evenodd" /></g></g><defs><linearGradient id="paint0_linear_343_121520" x1=".933" x2=".933" y1="1.433" y2="6.1" gradientUnits="userSpaceOnUse"><stop stop-color="#fff" /><stop offset="1" stop-color="#F0F0F0" /></linearGradient><filter id="filter0_d_343_121520" width="6.533" height="5.667" x=".933" y="1.433" color-interpolation-filters="sRGB" filterUnits="userSpaceOnUse"><feFlood flood-opacity="0" result="BackgroundImageFix" /><feColorMatrix in="SourceAlpha" result="hardAlpha" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0" /><feOffset dy="1" /><feColorMatrix values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.06 0" /><feBlend in2="BackgroundImageFix" result="effect1_dropShadow_343_121520" /><feBlend in="SourceGraphic" in2="effect1_dropShadow_343_121520" result="shape" /></filter></defs></svg>
                                                +1 <svg className="w-2.5 h-2.5 ms-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6"><path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" /></svg>
                                            </button>
                                            <div id="dropdown-phone" className="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-52 dark:bg-gray-700">
                                                <ul className="py-2 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="dropdown-phone-button">
                                                    <li>
                                                        <button type="button" className="inline-flex w-full px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:text-gray-200 dark:hover:bg-gray-600 dark:hover:text-white" role="menuitem">
                                                            <div className="inline-flex items-center">
                                                                <svg className="h-4 w-4 me-2" fill="none" viewBox="0 0 20 15"><rect width="19.6" height="14" y=".5" fill="#fff" rx="2" /><mask id="a"  width="20" height="15" x="0" y="0" maskUnits="userSpaceOnUse"><rect width="19.6" height="14" y=".5" fill="#fff" rx="2" /></mask><g mask="url(#a)"><path fill="#0A17A7" d="M0 .5h19.6v14H0z" /><path fill="#fff" fill-rule="evenodd" d="M-.898-.842L7.467 4.8V-.433h4.667V4.8l8.364-5.642L21.542.706l-6.614 4.46H19.6v4.667h-4.672l6.614 4.46-1.044 1.549-8.365-5.642v5.233H7.467V10.2l-8.365 5.642-1.043-1.548 6.613-4.46H0V5.166h4.672L-1.941.706-.898-.842z" clip-rule="evenodd" /><path stroke="#DB1F35" stroke-linecap="round" stroke-width=".667" d="M13.067 4.933L21.933-.9M14.009 10.088l7.947 5.357M5.604 4.917L-2.686-.67M6.503 10.024l-9.189 6.093" /><path fill="#E6273E" fill-rule="evenodd" d="M0 8.9h8.4v5.6h2.8V8.9h8.4V6.1h-8.4V.5H8.4v5.6H0v2.8z" clip-rule="evenodd" /></g></svg>
                                                                United Kingdom (+44)
                                                            </div>
                                                        </button>
                                                    </li>

                                                </ul>
                                            </div>
                                            <label htmlFor="phone-input" className="mb-2 text-sm font-medium text-gray-900 sr-only dark:text-white">Phone number:</label>
                                            <div className="relative w-full">
                                                <input type="text" id="phone-input" aria-describedby="helper-text-explanation" className="block p-2.5 w-full z-20 text-sm text-gray-900 bg-gray-50 rounded-e-lg border-s-0 border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-s-gray-700  dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:border-blue-500" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" placeholder="123-456-7890" required />
                                            </div>
                                        </div>


                                    </div>
                                }


                                <div className="space-y-2">
                                    <Label htmlFor="password">Password</Label>
                                    <InputPassword id="password" required
                                        onChange={(value) => setRegistration({ ...registration, "password": value })} />
                                </div>

                                <div className="space-y-2">
                                    <Label htmlFor="confirm-password">Confirm Password</Label>
                                    <InputPassword id="confirm-password" required />
                                </div>

                                <div className="flex items-start">
                                    <div className="flex items-center h-5">
                                        <input id="terms" aria-describedby="terms" type="checkbox" className="w-4 h-4 border border-neutral-300 rounded bg-neutral-50 focus:ring-3 focus:ring-indigo-300 dark:bg-neutral-700 dark:border-neutral-600 dark:focus:ring-primary-600 dark:ring-offset-neutral-800" required />
                                    </div>
                                    <div className="ml-3 text-sm">
                                        <label htmlFor="terms" className="font-light text-neutral-500 dark:text-neutral-300">I accept the <a className="font-medium text-indigo-600 hover:underline dark:text-primary-500" href="/terms">Terms and Conditions</a></label>
                                    </div>
                                </div>
                                <button

                                    onClick={register}
                                    className="w-full text-white bg-indigo-600
               hover:bg-indigo-700 focus:ring-4 focus:outline-none
                focus:ring-indigo-300 font-medium rounded-lg text-sm 
                px-5 py-2.5 text-center dark:bg-indigo-600
                 dark:hover:bg-indigo-700
                  dark:focus:ring-indigo-800">Create an account</button>
                                <p className="text-sm font-light text-neutral-500 dark:text-neutral-400">
                                    Already have an account? <a href="/login" className="font-medium text-indigo-600 hover:underline dark:text-primary-500">Login here</a>
                                </p>

                            </div>
                        </div>
                    )

                    :

                    (
                        <div className="items-center justify-center flex flex-col">
                            <div className="items-center justify-center flex flex-row">
                                <CheckIcon className="w-20 h-20 text-green-500" />
                                <p className="text-green-500 text-3xl">Sucess registration </p>
                            </div>

                            {
                                tenantState?.tenantRegistrationConfig.verifyEmail.required &&
                                tenantState?.tenantRegistrationConfig.useMagicLink &&
                                <p className="text-black text-xl">Please check your email for email verification.</p>
                            }

                        </div>
                    )

                }

                {
                    submitting &&
                    <div className="m-10 p-6 flex justify-center items-center content-center">
                        <div className="h-16 w-16 animate-spin rounded-full border-4 border-solid border-primary border-t-transparent"></div>
                    </div>
                }

            </div>
        </section>
    )
}



function CheckIcon(props: any) {
    return (
        <svg
            {...props}
            xmlns="http://www.w3.org/2000/svg"
            width="24"
            height="24"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="2"
            strokeLinecap="round"
            strokeLinejoin="round"
        >
            <polyline points="20 6 9 17 4 12" />
        </svg>
    )
}

export default Register
