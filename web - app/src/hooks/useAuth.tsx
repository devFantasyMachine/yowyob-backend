"use client";
import { useEffect, useState } from "react";
import { AuthenticationResult } from "@/services/auth-service"; 
import { DEVICE_ID_COOKIE_NAME, SESSION_COOKIE_NAME } from "@/constants/app";
import { setCookie } from "cookies-next"; 

type SetValue<T> = T | ((val: T) => T);

function useAuth () : [AuthenticationResult | undefined, (value: SetValue<AuthenticationResult | undefined>) => void] {

    const [authenticaationResult, setAuthenticationResult]
        = useState<AuthenticationResult | undefined>(undefined);
    
    useEffect(() => {

        if (authenticaationResult) {

            setCookie(SESSION_COOKIE_NAME, authenticaationResult.sessionToken);
            setCookie(DEVICE_ID_COOKIE_NAME, authenticaationResult.deviceId);
        }

    }, [authenticaationResult]);

    return [authenticaationResult, setAuthenticationResult];
};

export default useAuth;

