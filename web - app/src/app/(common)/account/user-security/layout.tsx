"use client";
import { usePathname } from "next/navigation";
import Image from "next/image";
import Link from "next/link";
import React, { useState } from "react";




interface TabItemProp {

  selected: boolean;
  title: string,
  onTap: () => void;
  href: string;
  alert?: boolean

}


const TabItem: React.FC<TabItemProp> = ({ title, selected, href, alert, onTap }) => {

  return (

    <button className="me-2 relative inline-flex w-fit" onClick={(e) => { e.stopPropagation(); onTap() }}>

      {alert && <div className="z-20 absolute bottom-auto
       left-auto right-0 top-5 inline-block 
       -translate-y-1/2 translate-x-2/4 rotate-0
        skew-x-0 skew-y-0 scale-x-100 scale-y-100
         rounded-full bg-red-600 p-1.5 text-xs">
      </div>
      }
      <a href={href} className={`inline-block p-4 border-b-2
       border-transparent rounded-t-lg
        hover:text-gray-600
         hover:border-gray-300
          dark:hover:text-gray-300
          ${selected && "text-blue-600 border-b-2 border-blue-600 rounded-t-lg dark:text-blue-500 dark:border-blue-500"}`} >{title}</a>

    </button>
  )

}


interface TabProp {

  selected: number;
  setSelected: (n: number) => void;

}


const Tab: React.FC<TabProp> = ({ selected, setSelected }) => {

  return (
    <div className="text-sm font-medium text-center text-gray-500 border-b border-gray-200 dark:text-gray-400 dark:border-gray-700">
      <ul className="flex flex-wrap -mb-px">

        <TabItem
          selected={selected == 1}
          title={"Passwords"}
          onTap={() => setSelected(1)}
          href={"/account/user-security"} />

        <TabItem
          selected={selected == 2}
          title={"Multifactor"}
          onTap={() => setSelected(2)}
          href={"/account/user-security/multifactor-auth"} />

        <TabItem
          selected={selected == 3}
          title={"Recovery"}
          onTap={() => setSelected(3)}
          href={"/account/user-security/recovery-settings"} />

        <TabItem
          selected={selected == 4}
          title={"Devices"}
          onTap={() => setSelected(4)}
          href={"/account/user-security/devices"} />

        <TabItem
          selected={selected == 5}
          title={"Apps"}
          onTap={() => setSelected(5)}
          href={""} />

        <TabItem
          selected={selected == 6}
          title={"Privacy"}
          onTap={() => setSelected(6)}
          alert
          href={"/account/user-security/privacy"} />


      </ul>
    </div>
  )

}




export default function RootLayout({ children, }: { children: React.ReactNode; }) {


  const [selected, setSelected] = useState(1);
  const path = usePathname();


  return (
    <>
      <div className="flex flex-col">
        <div className="bg-white p-4 sm:p-6 md:p-8 mb-6 rounded-2xl shadow-lg flex flex-col gap-4">
          <div className="text-sm font-medium text-center text-gray-500 border-b border-gray-200 dark:text-gray-400 dark:border-gray-700">
            <ul className="flex flex-wrap -mb-px">

              <TabItem
                selected={path === "/account/user-security"}
                title={"Basic"}
                onTap={() => setSelected(1)}
                href={"/account/user-security"} />

              <TabItem
                selected={path === "/account/user-security/multifactor-auth"}
                title={"Multifactor"}
                onTap={() => setSelected(2)}
                href={"/account/user-security/multifactor-auth"} />

              <TabItem
                selected={path === "/account/user-security/recovery-settings"}
                title={"Recovery"}
                onTap={() => setSelected(3)}
                href={"/account/user-security/recovery-settings"} />

              <TabItem
                selected={path === "/account/user-security/devices"}
                title={"Devices"}
                onTap={() => setSelected(4)}
                href={"/account/user-security/devices"} />

              <TabItem
                selected={path === "/account/user-security/connected-accounts"}
                title={"Apps"}
                onTap={() => setSelected(5)}
                href={""} />

              <TabItem
                selected={path === "/account/user-security/privacy"}
                title={"Privacy"}
                onTap={() => setSelected(6)}
                alert
                href={"/account/user-security/privacy"} />

              <TabItem
                selected={path === "/account/user-security/privacy"}
                title={"Advanced"}
                onTap={() => setSelected(6)}
                alert
                href={"/account/user-security/privacy"} />

            </ul>
          </div>
          {children}
        </div>
      </div>
    </>
  );
}


