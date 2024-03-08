"use client"

import Loader from "@/components/common/Loader";



export default function Loading() {

    return (
        <div className="flex h-screen w-screen justify-center items-center flex-col">
            <Loader />
        </div>
    );

}


