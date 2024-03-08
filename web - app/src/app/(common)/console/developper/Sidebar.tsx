import React, { useRef, useState } from "react";
import classNames from "classnames";
import Link from "next/link";
import Image from "next/image";
import { defaultNavItems } from "./defaultNavItems";
import { useOnClickOutside } from "usehooks-ts";


import Logo from "../../../public/img/logo.png";
import { ChevronDownIcon, HomeIcon, WindowIcon, ServerIcon, PlusIcon } from "@heroicons/react/24/outline";
import AnimateHeight from "react-animate-height";
import { usePathname } from "next/navigation";
import { useAppSelector } from "@/lib/stores/hooks";
import { getCurrentApp } from "@/lib/stores/features/apps/appSlice";


// define a NavItem prop
export type NavItem = {
    label: string;
    href?: string;
    icon: React.ReactNode;
    submenus?: { title: string, url: string, icon?: React.ReactNode }[]
};

// add NavItem prop to component prop
type Props = {
    open: boolean;
    navItems?: NavItem[];
    setOpen(open: boolean): void;
};
const Sidebar = ({ open, navItems = defaultNavItems, setOpen }: Props) => {

    const path = usePathname();
	const currentApp = useAppSelector(getCurrentApp);
    

    const ref = useRef<HTMLDivElement>(null);
    useOnClickOutside(ref, (e) => {
        setOpen(false);
    });

    const [appopened, setAppOpened] = useState<boolean>(false);



    return (
        <div
            className={classNames({
                "flex flex-col shadow-md fixed md:sticky top-0": true, // layout
                "bg-indigo-700 text-zinc-50": true, // colors
                "md:w-full md:sticky md:top-16 md:z-0 top-0 z-20 fixed": true, // positioning
                "md:h-[calc(100vh_-_64px)] h-full w-[300px]": true, // for height and width
                "transition-transform .3s ease-in-out md:-translate-x-0": true, //animations
                "-translate-x-full ": !open, //hide sidebar to the left when closed
            })}
            ref={ref}
        >

           <div className="mx-4 my-4 md:top-16">

                <Link href={"#"}

                    className={`hover:bg-indigo-900 rounded-xl bg-indigo-600 transition-colors duration-300 justify-between px-5 py-3  hover:text-white flex w-full items-center ${appopened && "bg-primary text-white"
                        }`}>

                    <span className="flex gap-2 items-center">
                        {currentApp?.shortName}
                    </span>

                    <WindowIcon onClick={() =>
                        setAppOpened(!appopened)}
                        className={`w-5 h-5 duration-300 ${appopened && "rotate-180"}`} />

                </Link>

                <AnimateHeight
                    duration={300}
                    height={appopened ? "auto" : 0}>
                    <ul className={` mt-1 rounded-xl flex flex-col gap-1 bg-indigo-600`}>
                        <li>
                            <Link
                                href={"/console/developper/apps"}
                                className={`flex gap-2 items-center pl-4 pr-1 py-3 hover:bg-indigo-800 duration-300 rounded-md ${"admin" == path && "bg-violet-200"
                                    }`}>
                                {<HomeIcon className="w-6 h-6" />}
                                {"All Apps"}
                            </Link>
                        </li>
                        <li>
                            <Link
                                href={"/console/developper/apps/add-app"}
                                className={`flex gap-2 items-center pl-4 pr-1 py-3 hover:bg-indigo-800 duration-300 rounded-md ${"admin" == path && "bg-violet-200"
                                    }`}>
                                {<PlusIcon className="w-6 h-6" />}
                                {"Add App"}
                            </Link>
                        </li>
                    </ul>
                </AnimateHeight>

            </div>

            <nav className="">




                {/* nav items */}
                <ul className="py-2 flex flex-col gap-2">
                    {navItems.map((item, index) => {

                        const [opened, setOpened] = useState<boolean>(false);
                        const [path, setPath] = useState<null | string>(null);

                        return (
                            <li key={index}>
                                {item.href ? (
                                    <Link key={index} href={`/console/developper/apps/${currentApp?.id}${item.href}`}>
                                        <li
                                            className={classNames({
                                                "text-indigo-100 hover:bg-indigo-900": true, //colors
                                                "flex gap-4 items-center ": true, //layout
                                                "transition-colors duration-300": true, //animation
                                                "rounded-md p-2 mx-2": true, //self style
                                            })}
                                        >
                                            {item.icon} {item.label}
                                        </li>
                                    </Link>
                                ) : (
                                    <button onClick={() =>
                                        setOpened(!opened)
                                    }
                                        className={`hover:bg-indigo-900 transition-colors duration-300 justify-between px-5 py-3  hover:text-white rounded-md flex w-full items-center ${opened && "bg-primary text-white"
                                            }`}>
                                        <span className="flex gap-2 items-center">
                                            {item.icon}
                                            {item.label}
                                        </span>
                                        {item.submenus && (
                                            <ChevronDownIcon
                                                className={`w-5 h-5 duration-300 ${opened && "rotate-180"
                                                    }`}
                                            />
                                        )}
                                    </button>
                                )}
                                {item.submenus && (
                                    <AnimateHeight
                                        duration={300}
                                        height={opened ? "auto" : 0}>
                                        <ul className={`mx-3 mt-1 rounded-xl flex flex-col gap-1 bg-indigo-600`}>
                                            {item.submenus.map((item) => (
                                                <li key={item.title}>
                                                    <Link
                                                        href={`/console/developper/apps/${currentApp?.id}${item.url}`}
                                                        className={`flex gap-2 items-center pl-4 pr-1 py-3 hover:bg-violet-200 duration-300 rounded-md ${item.url == path && "bg-violet-200"
                                                            }`}>
                                                        {item.icon}
                                                        {item.title}
                                                    </Link>
                                                </li>
                                            ))}
                                        </ul>
                                    </AnimateHeight>
                                )}
                            </li>


                        );
                    })}
                </ul>
            </nav>


            <div className="h-[800px] w-full"></div>

        </div>
    );
};
export default Sidebar;
