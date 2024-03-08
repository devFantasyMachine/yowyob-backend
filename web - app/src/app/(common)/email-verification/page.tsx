"use client"

import { CardHeader, CardContent, Card } from "@/components/ui/card"
import { Label } from "@/components/ui/label"
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import Link from "next/link"
import { useRouter, useSearchParams } from "next/navigation" 
import { useEffect, useState } from "react"
import { verifyEmail } from "@/services/auth-service"
import { setCookie } from "cookies-next"
import { getRedirect } from "@/hooks/useCookie"
import { DEVICE_ID_COOKIE_NAME, SESSION_COOKIE_NAME } from "@/constants/app"
import useAuth from "@/hooks/useAuth"
import { ShieldCheckIcon } from "@heroicons/react/20/solid"
 



export default function Component() {

    const router = useRouter();

    const [authResult, setAuthResult] = useAuth();

    const queryParams = useSearchParams();
    const verificationId = queryParams.get('verificationId');
    const token = queryParams.get('token');


    const isMagicLink = token != null && token.length > 0;

    const [isSuccess, setIsSuccess] = useState(false);
    const [isError, setIsError] = useState(false);
    const [submitting, setSubmitting] = useState(false);

    const [code, setCode] = useState("");


    const handleVerify = ({ verificationId, verificationCode }: { verificationId: string, verificationCode: string }) => {

        setSubmitting(true);

        verifyEmail({
            verificationCode: verificationCode,
            verificationId: verificationId
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
            .catch(() => {

                setSubmitting(false);
                setIsError(true);
            })

    }
 

    useEffect(() => {

        if (isMagicLink) {

            handleVerify(
                {
                    verificationId: token.split(".")[0],
                    verificationCode: token.split(".")[1]
                }
            )
        }


    }, [])



    return (
        <>
            <main className="bg-indigo-500 dark:bg-neutral-900 py-20 min-h-screen px-4 sm:px-6 lg:px-8">
                
                <div className="max-w-md mx-auto ">
                    <Card className="mx-auto max-w-[350px] space-y-6 bg-white">
                        <CardHeader>
                            <h1 className="text-3xl font-bold text-center">Email Verification</h1>
                        </CardHeader>
                        {isMagicLink ?

                            (<p className="text-neutral-500">Link</p>)

                            :

                            (
                                <CardContent>
                                    <div className="space-y-4">
                                        <div className="space-y-2">
                                            <Label htmlFor="verification-code">Verification Code</Label>
                                            <Input onChange={setCode} id="verification-code" placeholder="Enter your verification code" required />
                                        </div>
                                        <Button

                                            onClick={() => {

                                                handleVerify({
                                                    verificationId: verificationId!,
                                                    verificationCode: code
                                                });
                                            }}

                                            className="w-full bg-black text-white" type="submit">
                                            Submit Code
                                        </Button>
                                        <div className="text-center">
                                            <p className="text-gray-500 dark:text-gray-400">Didn't receive the code?</p>
                                            <Link className="underline text-blue-500" href="#">
                                                Resend Verification Code
                                            </Link>
                                        </div>
                                    </div>
                                </CardContent>
                            )

                        }
                    </Card>
                    {submitting &&

                        <div className="mt-4 flex flex-col items-center justify-center gap-10 bg-transparent">

                            <div className="h-16 w-16 animate-spin rounded-full border-4 border-solid border-primary border-t-transparent"></div>
                            <div className="flex flex-col gap-2">
                                <span className="text-3xl font-bold ">Verifying Email...</span>
                                <span className="text-md text-center text-neutral-600">Please wait</span>
                            </div>
                        </div>

                    }
                    {isSuccess && <Card className="mx-auto max-w-[350px] space-y-6 mt-6">
                        <CardContent>
                            <div className="flex flex-row gap-4">
                                <ShieldCheckIcon className="w-6 h-6 text-green-500" />
                                <p className="text-green-500">You have successfully verified your account.</p>
                            </div>
                        </CardContent>
                    </Card>}

                    {isError && <Card className="mx-auto max-w-[350px] space-y-6 mt-6">
                        <CardContent>
                            <div className="flex flex-row gap-4">
                                <BugIcon className="w-6 h-6 text-red-500" />
                                <p className="text-red-500">Invalid. Please try again.</p>
                            </div>
                        </CardContent>
                    </Card>}
                </div>
            </main>
        </>
    )
}

function BugIcon(props: any) {
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
            <path d="m8 2 1.88 1.88" />
            <path d="M14.12 3.88 16 2" />
            <path d="M9 7.13v-1a3.003 3.003 0 1 1 6 0v1" />
            <path d="M12 20c-3.3 0-6-2.7-6-6v-3a4 4 0 0 1 4-4h4a4 4 0 0 1 4 4v3c0 3.3-2.7 6-6 6" />
            <path d="M12 20v-9" />
            <path d="M6.53 9C4.6 8.8 3 7.1 3 5" />
            <path d="M6 13H2" />
            <path d="M3 21c0-2.1 1.7-3.9 3.8-4" />
            <path d="M20.97 5c0 2.1-1.6 3.8-3.5 4" />
            <path d="M22 13h-4" />
            <path d="M17.2 17c2.1.1 3.8 1.9 3.8 4" />
        </svg>
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
