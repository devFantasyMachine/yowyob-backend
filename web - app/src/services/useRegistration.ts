import axiosAPIClient from "@/config/http-client";
import Invitation from "@/types/invitation";
import { RegistrationRequest, RegistrationResponse } from "@/types/registration";
import { AxiosError } from "axios";

interface ApiError {

    error?: string,
    message?: string
}



export const fetchInvitation: (id: string) => Promise<Invitation> = async (id: string) => {

    return await axiosAPIClient.get<Invitation>("/invitations/" + id)
        .then(response => response.data)
        .catch((err: AxiosError<ApiError>) => { 

            if(err.response){
                throw new Error(err.response.data.error);
            }

            throw new Error("NETWORK_ERROR");
        })

}



export const registerByEmail: (request: RegistrationRequest) => Promise<RegistrationResponse> = async (request: RegistrationRequest) => {

    return await axiosAPIClient.post<RegistrationResponse>('/registrations/email', request)
        .then(response => response.data)
        .then(res => {

            if (res.isSuccess){

                return res;
            }

            throw new Error(res.errors.join(" "))
        })
        .catch((err: AxiosError<ApiError>) => { 

            if(err.response){
                throw new Error(err.response.data.error);
            }

            throw new Error("NETWORK_ERROR");
        })

}


export const registerByUsername: (request: RegistrationRequest) => Promise<RegistrationResponse> = async (request: RegistrationRequest) => {

    return await axiosAPIClient.post<RegistrationResponse>('/registrations/username', request)
        .then(response => response.data)
        .catch((err: AxiosError<ApiError>) => { 

            if(err.response){
                throw new Error(err.response.data.error);
            }

            throw new Error("NETWORK_ERROR");
        })

}


export const registerByPhone: (request: RegistrationRequest) => Promise<RegistrationResponse> = async (request: RegistrationRequest) => {

    return await axiosAPIClient.post<RegistrationResponse>('/registrations/phone-password', request)
        .then(response => response.data)
        .catch((err: AxiosError<ApiError>) => { 

            if(err.response){
                throw new Error(err.response.data.error);
            }

            throw new Error("NETWORK_ERROR");
        })

}



