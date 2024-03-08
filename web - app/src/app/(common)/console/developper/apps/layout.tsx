'use client'


import { PropsWithChildren } from "react";



const Layout = ({ children }: PropsWithChildren) => {


    return (

        <main tabIndex={-1000} className="bg-transparent">
            {children}
        </main>

    )
}



export default Layout;

