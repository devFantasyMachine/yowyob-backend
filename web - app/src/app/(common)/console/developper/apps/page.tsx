'use client'

import Tooltip from "@/components/Tooltip";
import { getDeveloperApps } from "@/services/app-service";
import { App } from "@/types/app";
import { ArrowDownRightIcon, ArrowRightIcon, ChevronRightIcon, PlusIcon } from "@heroicons/react/24/outline";
import { NextPage } from "next"
import { usePathname, useRouter } from "next/navigation";
import { useEffect, useState } from "react";



const _apps = [
    {
        icon: "",
        shortName: "letsgo-driver",
        longName: ""
    },
    {
        icon: "",
        shortName: "letsgo-poller",
        longName: ""
    },
    {
        icon: "",
        shortName: "rental-agency",
        longName: ""
    },
    {
        icon: "",
        shortName: "",
        longName: ""
    },
    {
        icon: "",
        shortName: "",
        longName: ""
    },
    {
        icon: "",
        shortName: "",
        longName: ""
    },
]

const DevelopperHomePage: NextPage = () => {

    const router = useRouter();
    const pathname = usePathname();

    const [apps, setApps] = useState<App[]>([]);


    useEffect(() => {

        getDeveloperApps()
            .then(apps => {

                setApps(apps)
            })

    }, [])



    return (

        <div className="flex flex-col justify-center h-full">

            <div className="absolute top-0 bg-indigo-500 h-1/3 w-full ">
            </div>

            <div className="p-6">

                <div className="mt-25 mb-5 flex flex-row gap-1 content-center items-center">

                    <span className="text-black text-4xl font-bold z-10">
                        Welcome Developper !
                    </span>
                </div>

                <div className="mt-2 mb-5 flex flex-row gap-1 content-center items-center ">

                    <a className="text-white text-3xl font-bold z-10">
                        Yowyob
                    </a>
                    <ChevronRightIcon className="w-6 h-6 z-10" />
                    <span className="text-gray-800 text-xl font-semibold underline p-2 z-10">
                        All APPS
                    </span>

                </div>


                {/*Applications*/}
                <div className="p-6 grid grid-flow-row lg:grid-cols-6 md:grid-cols-4 sm:grid-cols-1 flex-row gap-10 items-center">

                    {apps.map((item, index) => {

                        return (

                            <Tooltip key={index} message={"Open " + item.shortName}>
                                <button

                                    onClick={(e) => {
                                        e.stopPropagation();

                                        router.replace(pathname + "/" + item.id)
                                    }}

                                    className="w-[200px] h-[200px] shadow-lg bg-white rounded-xl">

                                    {item.shortName}

                                    <p>{item.name}</p>

                                </button>
                            </Tooltip>
                        )
                    })


                    }

                    <Tooltip message={"Add new app"}>
                        <button
                            onClick={(e) => {
                                e.stopPropagation();
                                router.replace("/console/developper/apps/add-app");
                            }}

                            className="z-30 w-[200px] h-[200px] shadow-lg bg-white rounded-xl flex content-center items-center justify-center">

                            {<PlusIcon className="w-10 h-10" />}
                        </button>
                    </Tooltip>





                </div>


                <div className="h-[200px]"></div>



                <div className="flex flex-row items-center justify-items-center justify-center">


                </div>

            </div>




        </div>
    )
}



export default DevelopperHomePage;

