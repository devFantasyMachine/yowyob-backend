import { ReactNode } from "react";


export default function Tooltip({ message, children }: { message: string, children: ReactNode }) {
    return (
        <div className="group relative flex">
            {children}
            <span className="z-50 absolute bottom-[-50px] scale-0 transition-all rounded bg-indigo-700 p-2 text-xs text-white group-hover:scale-100">{message}</span>
        </div>
    )
}

