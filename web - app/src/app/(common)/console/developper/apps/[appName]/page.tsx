'use client'

import { Card, CardContent, CardHeader } from "@/components/ui/card";
import { getCurrentApp } from "@/lib/stores/features/apps/appSlice";
import { useAppSelector } from "@/lib/stores/hooks";
import { BellAlertIcon, KeyIcon, UsersIcon } from "@heroicons/react/20/solid";
import { NextPage } from "next";
import Image from 'next/image'


const AppPage: NextPage = () => {

  const currentApp = useAppSelector(getCurrentApp);


  return (

    <div className="row">
      <main className="p-6">

        <div className="flex flex-row content-center justify-items-center items-center my-3">

          <Image
            className="mr-3 relative dark:drop-shadow-[0_0_0.3rem_#ffffff70] dark:invert"
            src="/avatar.png"
            alt="Next.js Logo"
            width={30}
            height={30}
            priority
          />
          <h2 className="text-2xl font-bold">{currentApp?.shortName} </h2>
        </div>


        <div className="grid grid-cols-4 gap-4 mb-8">
          <Card className="bg-white">
            <CardContent>
              <UsersIcon className="text-orange-400 w-9 h-9" />
              <p className="text-3xl font-semibold">112</p>
              <p>Users</p>
            </CardContent>
          </Card>
          <Card className="bg-white">
            <CardContent>
              <KeyIcon className="text-yellow-400 w-9 h-9" />
              <p className="text-3xl font-semibold">44</p>
              <p>Clients</p>
            </CardContent>
          </Card>
          <Card className="bg-white">
            <CardContent>
              <BellAlertIcon className="text-red-400 w-9 h-9" />
              <p className="text-3xl font-semibold">37</p>
              <p>Alerts</p>
            </CardContent>
          </Card>
          <Card className="bg-white">
            <CardContent>
              <GroupIcon className="text-red-400" />
              <p className="text-3xl font-semibold">218</p>
              <p>Employees</p>
            </CardContent>
          </Card>
        </div>

        <div className="grid grid-cols-2 gap-4">
          <Card className="bg-white">
            <CardHeader>
              Alert
            </CardHeader>
          </Card>
          <Card className="bg-white">
            <CardHeader>
              Recent Activities
            </CardHeader>
          </Card>
        </div>
      </main>
    </div>
  )
}



export default AppPage;






function BriefcaseIcon(props: any) {
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
      <rect width="20" height="14" x="2" y="7" rx="2" ry="2" />
      <path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16" />
    </svg>
  )
}


function ClipboardListIcon(props: any) {
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
      <rect width="8" height="4" x="8" y="2" rx="1" ry="1" />
      <path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2" />
      <path d="M12 11h4" />
      <path d="M12 16h4" />
      <path d="M8 11h.01" />
      <path d="M8 16h.01" />
    </svg>
  )
}


function CurrencyIcon(props: any) {
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
      <circle cx="12" cy="12" r="8" />
      <line x1="3" x2="6" y1="3" y2="6" />
      <line x1="21" x2="18" y1="3" y2="6" />
      <line x1="3" x2="6" y1="21" y2="18" />
      <line x1="21" x2="18" y1="21" y2="18" />
    </svg>
  )
}



function GroupIcon(props: any) {
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
      <path d="M3 7V5c0-1.1.9-2 2-2h2" />
      <path d="M17 3h2c1.1 0 2 .9 2 2v2" />
      <path d="M21 17v2c0 1.1-.9 2-2 2h-2" />
      <path d="M7 21H5c-1.1 0-2-.9-2-2v-2" />
      <rect width="7" height="5" x="7" y="7" rx="1" />
      <rect width="7" height="5" x="10" y="12" rx="1" />
    </svg>
  )
}
