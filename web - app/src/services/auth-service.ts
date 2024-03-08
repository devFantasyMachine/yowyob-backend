import axiosAPIClient from "@/config/http-client";
import User from "@/types/user";
import { AxiosError } from "axios";



interface VerifyEmailRequest{

    verificationId: string,
    verificationCode: string;

}

interface ResetPasswordRequest{

    verificationId: string,
    password: string;

}

export interface LoginRequest{

    username?: string,
    password?: string;

}

export interface AuthenticationResult {

    user: User;
    deviceId: string;
    sessionToken: string;
    rememberToken: string;
    sessionTokenExpireAt: string;
    rememberTokenExpireAt: string;
    sessionTokenIssuedAt: string;
    redirectUri: string;
    verificationId: string;
    requireAction: string;

}

interface ApiError {

    error?: string,
    message?: string
}



export const verifyEmail: (request: VerifyEmailRequest) => Promise<AuthenticationResult> = async (request: VerifyEmailRequest) => {

    return await axiosAPIClient.post<AuthenticationResult>('/auth/verify-email', request)
        .then(response => response.data)
        .catch((err: AxiosError<ApiError>) => { 

            if(err.response){
                throw new Error(err.response.data.error);
            }

            throw new Error("NETWORK_ERROR");
        })

}

export const resetPassword: (request: ResetPasswordRequest) => Promise<AuthenticationResult> = async (request: ResetPasswordRequest) => {

    return await axiosAPIClient.post<AuthenticationResult>('/auth/reset-password', request)
        .then(response => response.data)
        .catch((err: AxiosError<ApiError>) => { 

            if(err.response){
                throw new Error(err.response.data.error);
            }

            throw new Error("NETWORK_ERROR");
        })

}


export const login: (request: LoginRequest) => Promise<AuthenticationResult> = async (request: LoginRequest) => {

    return await axiosAPIClient.post<AuthenticationResult>('/auth/login', request)
        .then(response => response.data)
        .catch((err: AxiosError<ApiError>) => { 

            if(err.response){
                throw new Error(err.response.data.error);
            }

            throw new Error("NETWORK_ERROR");
        })

}



export const acceptInvitation: (token: string) => Promise<AuthenticationResult> = async (token: string) => {

    return await axiosAPIClient.post<AuthenticationResult>('/auth/invitation/accept?token=' + token)
        .then(response => response.data)
        .catch((err: AxiosError<ApiError>) => { 

            if(err.response){
                throw new Error(err.response.data.error);
            }

            throw new Error("NETWORK_ERROR");
        })

}













