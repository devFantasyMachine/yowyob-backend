import axiosAPIClient from "@/config/http-client"
import { App } from "@/types/app"
import { Tenant } from "@/types/tenant"
import { AxiosError } from "axios"


interface ApiError {

    error?: string,
    message?: string
}


export const fetchTenant: () => Promise<Tenant> = async () => {

    return await axiosAPIClient.get<Tenant>("/tenants/me")
        .then(response => response.data)
        .catch((err: AxiosError<ApiError>)=> {

            console.log(err.response?.data);
            throw new Error("" + err.response?.data.error);            
        })
}


 
export const fetchTenantById: (id: string) => Promise<Tenant> = async (id: string) => {

    return await axiosAPIClient.get<Tenant>("/tenants/" + id)
        .then(response => response.data)
        .catch((err: AxiosError<ApiError>)=> {

            console.log(err.response?.data);
            throw new Error("" + err.response?.data.error);            
        })
}



