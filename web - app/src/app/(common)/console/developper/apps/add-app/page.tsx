"use client"


import { NextPage } from 'next'
import { faEnvelope, faUser } from '@fortawesome/free-regular-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faLock } from '@fortawesome/free-solid-svg-icons'

import Image from "next/image";
import { useRouter } from 'next/navigation'
import { SyntheticEvent, useState } from 'react'
import { deleteCookie, getCookie } from 'cookies-next'
import axios from 'axios'
import { Label } from '@/components/ui/label'
import { CheckboxCustom, Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import { PencilIcon } from '@heroicons/react/24/outline'
import { GeneralAppInfo, createApp } from '@/services/app-service'


const RegisterAppPage: NextPage = () => {
    const router = useRouter()
    const [submitting, setSubmitting] = useState(false);


    const [info, setInfo] = useState<GeneralAppInfo>({});
    const [error, setError] = useState<string | undefined>(undefined);



    const registerApp = async () => {


        setSubmitting(true);
        setError(undefined);

        createApp(info)
            .then(app => {

                console.log(app);
                router.replace("/console/developper/apps/" + app.id);
            })
            .catch((err: Error) => {

                setSubmitting(false);
                setError(err.message);
            })

    }


    return (
        <section className="relative py-20  items-center justify-center ">

            <div className="absolute top-0 bg-indigo-500 h-1/3 w-full ">
            </div>

            <div className="z-20 flex flex-col px-6 py-8 mx-auto lg:py-0 items-center justify-center ">

                <div className="z-20 mt-30 mx-auto lg:min-w-[700px] max-w-4xl space-y-8 p-6 bg-white dark:bg-black rounded-lg shadow-sm">

                    <div className="space-y-2 text-center">
                        <h1 className="text-4xl font-bold">Create App</h1>
                        <p className="text-gray-500 dark:text-gray-400">Please fill out the information below to register app.</p>
                    </div>

                    <div className="space-y-6 bg-slate-200 p-6 rounded-2xl">

                        <div className="relative mx-auto ms-md-0 mb-6">
                            <div className="avatar-upload__edit">
                                <input
                                    type="file"
                                    id="imageUpload"
                                    accept=".png, .jpg, .jpeg"
                                    className="hidden"
                                />
                                <label
                                    htmlFor="imageUpload"
                                    className="avatar-upload__label">Icon</label>
                            </div>
                            <div className="w-full flex content-center align-middle items-center justify-center justify-items-center h-[180px]">
                                <div className="relative w-[180px] h-[180px]">
                                    <Image
                                        width={180}
                                        height={180}
                                        className="rounded-full border-[6px] border-[#F5F5FE] shadow-md"
                                        alt=""
                                        src={''} />
                                    <span className="w-8 h-8 absolute cursor-pointer text-primary top-4 right-4 hover:bg-primary duration-300 hover:text-white rounded-full bg-white flex justify-center items-center border border-primary">
                                        <PencilIcon className="w-5 h-5" />
                                    </span>
                                </div>
                            </div>
                        </div>


                        <div className="space-y-2">
                            <Label htmlFor="username">Short Name</Label>
                            <Input
                                onChange={(value) => setInfo({ ...info, "shortName": value })}

                                id="username" placeholder="short-name" required />
                        </div>

                        <div className="space-y-2">
                            <Label htmlFor="name">Name</Label>
                            <Input
                                onChange={(value) => setInfo({ ...info, "name": value })}
                                id="name" placeholder="App" required />
                        </div>

                        <div className="space-y-2">
                            <Label htmlFor="home-page">Home Page</Label>
                            <Input
                                onChange={(value) => setInfo({ ...info, "homePage": value })}
                                id="home-page" placeholder="http://localhost:3000" required />
                        </div>


                        <div className="col-span-12">
                            <label className="block mb-2 font-medium clr-neutral-500">
                                Description
                            </label>
                            <textarea
                                onChange={(e) => {
                                    e.preventDefault();

                                    setInfo({ ...info, "description": e.target.value })
                                }}
                                rows={3}
                                placeholder="Write description"
                                className="border w-full focus:outline-none py-3 px-6 rounded-2xl"></textarea>
                        </div>

                        <div className="space-y-2">
                            <Label htmlFor="support">Support Email</Label>
                            <Input
                                onChange={(value) => setInfo({ ...info, "support": value })}
                                id="support" placeholder="support@app.com" required />
                        </div>

                        <div className="space-y-2">
                            <Label htmlFor="term-of-use">Term Of Use</Label>
                            <Input
                                onChange={(value) => setInfo({ ...info, "termOfUseLink": value })}
                                id="term-of-use" placeholder="http://localhost:3000/terms" required />
                        </div>

                        <div className="space-y-2">
                            <Label htmlFor="privacy">Privacy Policy</Label>
                            <Input

                                onChange={(value) => setInfo({ ...info, "privacyPolicyLink": value })}
                                id="privacy" placeholder="http://localhost:3000/privacy" required />
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
                                e.preventDefault();
                                registerApp();
                            }}

                            className="lg:w-1/2 self-center text-white font-semibold
                            bg-indigo-600 hover:bg-indigo-700 focus:ring-4 
                            focus:outline-none focus:ring-primary-300 
                             rounded-lg text-sm px-5 py-2.5 text-center dark:bg-indigo-600
                              dark:hover:bg-indigo-700 dark:focus:ring-indigo-800">
                            Create an application
                        </button>

                    </div>
                </div>

            </div>
        </section>
    )
}

export default RegisterAppPage













