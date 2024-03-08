// components/layout/Navbar.tsx
import React from "react";
import { Bars3Icon } from "@heroicons/react/24/outline";
import classNames from "classnames"; 
import LangDropdown from "@/components/LangDropdown";
import NotificationDropdown from "@/components/NotificationDropdown";
import ProfileDropdown from "@/components/ProfileDropdown";
import { useRouter } from "next/navigation";


type Props = {
  onMenuButtonClick(): void;
};

const Navbar = (props: Props) => {

  const router = useRouter();

  return (
    <nav
      className={classNames({
        "z-50": true,
        "bg-white text-zinc-500": true, // colors
        "flex items-center": true, // layout
        "w-full fixed px-4 shadow-sm h-16": true, //positioning & styling
      })}
    >
      <a href="https://flowbite.com" className="flex items-center">
        <img src="https://flowbite.com/docs/images/logo.svg" className="mr-3 h-6 sm:h-9" alt="Flowbite Logo" />
        <span className="self-center text-xl font-semibold whitespace-nowrap dark:text-white">{"Yowyob"}</span>
      </a>
      
      <div className="flex-grow">
      </div> {/** spacer */}

      <header className="px-4 md:px-8 xl:ml-60 flex gap-2 justify-between ">
        <div className="flex gap-2 items-center order-1 lg:order-2">
          <LangDropdown />
          <NotificationDropdown />
          <ProfileDropdown onProfileClick={function (): void {

            router.replace("/account")
          }} />
        </div>
      </header>


      <button className="md:hidden" onClick={props.onMenuButtonClick}>
        <Bars3Icon className="h-6 w-6" />
      </button>
    </nav>
  );
};
export default Navbar;

