import { axiosAPIAuthorizedClient } from "@/config/http-client"
import { App } from "@/types/app"
import { Tenant } from "@/types/tenant"
import { AxiosError } from "axios"


interface ApiError {

    error?: string,
    message?: string
}


export const fetchAppById: (id: string) => Promise<App> = async (id: string) => {

    return await axiosAPIAuthorizedClient.get<App>("/apps/" + id)
        .then(response => response.data)
        .catch((err: AxiosError<ApiError>) => {

            console.log(err.response?.data);
            throw new Error("" + err.response?.data.error);
        })
}


export interface GeneralAppInfo {

    shortName?: string,
    name?: string,
    icon?: string,
    description?: string,
    homePage?: string,
    privacyPolicyLink?: string,
    termOfUseLink?: string,
    support?: string,

}


export const createApp: (id: GeneralAppInfo) => Promise<App> = async (info: GeneralAppInfo) => {

    return await axiosAPIAuthorizedClient.post<App>("/apps", info)
        .then(response => response.data)
        .catch((err: AxiosError<ApiError>) => {

            console.log(err.response?.data);
            throw new Error("" + err.response?.data.error);
        })
}


export const getDeveloperApps: () => Promise<Array<App>> = async () => {

    return await axiosAPIAuthorizedClient.get<Array<App>>("/apps/developer")
        .then(response => response.data)
        .catch((err: AxiosError<ApiError>) => {

            console.log(err.response?.data);
            throw new Error("" + err.response?.data.error);
        })
}

