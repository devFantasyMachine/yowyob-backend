import { useAppSelector } from "@/lib/stores/hooks";

import { Tenant } from "@/types/tenant";
import { getAuthState } from "@/lib/stores/features/auth/authSlice";
import { getUser } from "@/lib/stores/features/users/userSlice";
import ProfileDropdown from "../ProfileDropdown";
import LangDropdown from "../LangDropdown";
import NotificationDropdown from "../NotificationDropdown";
import { usePathname } from "next/navigation";
import User from "@/types/user";




export const TenantHeader = ({ tenant, user }: { tenant?: Tenant, user?: User }) => {
 
    const path = usePathname();

    return (
        <header className="absolute top-0 left-0 w-full">
            <nav className="bg-white border-gray-200 px-4 lg:px-6 py-2.5 dark:bg-gray-800">
                <div className="flex flex-wrap justify-between items-center mx-auto max-w-screen-xl">
                    <a href="https://flowbite.com" className="flex items-center">
                        <img src="img/app-img.jpg" className="mr-3 h-6 sm:h-9" alt="Tenant Logo" />
                        <span className="self-center text-xl font-semibold whitespace-nowrap dark:text-white">{tenant?.organisation.shortName || "Yowyob"}</span>
                    </a>
                    <div className="flex items-center lg:order-2">
                        <header className="px-2 md:px-4 xl:ml-60 flex gap-2 justify-between ">
                            <div className="flex gap-2 items-center order-1 lg:order-2">
                                <LangDropdown />
                                <NotificationDropdown />
                                <ProfileDropdown user={user} onProfileClick={function (): void {

                                }} />
                            </div>
                        </header>
                    </div>
                </div>
            </nav>
        </header>
    );


}

