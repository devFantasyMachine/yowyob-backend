'use client'

import Tooltip from "@/components/Tooltip"
import { getUser } from "@/lib/stores/features/users/userSlice"
import { useAppSelector } from "@/lib/stores/hooks"
import { NextPage } from "next"
import { usePathname, useRouter } from "next/navigation"




const apps = [
    {
        icon: "",
        shortName: "Yowyob Shopping",
        longName: ""
    },
    {
        icon: "",
        shortName: "Yowyob Pooler",
        longName: ""
    },
    {
        icon: "",
        shortName: "Yowyob Driver",
        longName: ""
    },
    {
        icon: "",
        shortName: "Yowyob Syndicat",
        longName: ""
    },
    {
        icon: "",
        shortName: "Yowyob Invoice",
        longName: ""
    },
    {
        icon: "",
        shortName: "Yowyob Dev",
        longName: ""
    },
]



const utilitiesApps = [
    {
        icon: "",
        shortName: "Contacts",
        longName: ""
    },
    {
        icon: "",
        shortName: "Agenda",
        longName: ""
    },
    {
        icon: "",
        shortName: "Photos",
        longName: ""
    },
    {
        icon: "",
        shortName: "Yowyob Meet",
        longName: ""
    },
    {
        icon: "",
        shortName: "Yowyob Maps",
        longName: ""
    },
    {
        icon: "",
        shortName: "Notes",
        longName: ""
    },
    {
        icon: "",
        shortName: "Notes",
        longName: ""
    },
]


const AccountPage: NextPage = () => {

    const router = useRouter();
    const pathname = usePathname();

    const userState = useAppSelector(getUser);


    return (

        <div className="relative w-full h-full">

            <div className="my-10 mx-5 flex flex-col">
                <span className="text-white text-3xl font-bold">
                    Welcome !
                </span>
                {userState &&
                    <h4 className="text-2xl font-semibold">
                        {`${userState.profile.firstName ? userState.profile.firstName : ""} 
                        ${userState.profile.lastName ? userState.profile.lastName : ""}`}
                    </h4>
                }
            </div>

            <div className=" flex flex-col justify-center mx-5">

                <div className="flex flex-row p-1 mb-4 border-b border-gray-200">
                    <span className="text-black text-lg font-semibold">
                        Business Apps
                    </span>
                </div>

                {/*Applications*/}
                <div className="gap-10 flex flex-wrap  items-center">

                    {apps.map((item, index) => {

                        return (

                            <Tooltip key={index} message={"Open " + item.shortName}>
                                <button

                                    onClick={(e) => {
                                        e.stopPropagation();

                                        window.open("https://www.yowyob.com");
                                    }}

                                    className="w-[120px] h-[120px] shadow-lg bg-white rounded-xl p-2 transition ease-in-out delay-150 hover:-translate-y-1 hover:scale-110 hover:bg-indigo-500 duration-300">

                                    <span className="text-center text-xl font-semibold">
                                        {item.shortName}
                                    </span>

                                </button>
                            </Tooltip>
                        )
                    })


                    }




                </div>


                <div className="flex flex-row p-1 my-4 mt-8 border-b-2 border-gray-200">
                    <span className="text-black text-lg font-semibold">
                        Utilities Apps
                    </span>
                </div>

                {/*Applications*/}
                <div className="gap-10 flex flex-wrap  items-center">

                    {utilitiesApps.map((item, index) => {

                        return (

                            <Tooltip key={index} message={"Open " + item.shortName}>
                                <button

                                    onClick={(e) => {
                                        e.stopPropagation();

                                        window.open("https://www.yowyob.com");
                                    }}

                                    className="w-[120px] h-[120px] shadow-lg bg-white rounded-xl p-2 transition ease-in-out delay-150 hover:-translate-y-1 hover:scale-110 hover:bg-indigo-500 duration-300">

                                    <span className="text-center text-xl font-semibold">
                                        {item.shortName}
                                    </span>

                                </button>
                            </Tooltip>
                        )
                    })


                    }




                </div>


            </div>


        </div>
    )
}



export default AccountPage;

