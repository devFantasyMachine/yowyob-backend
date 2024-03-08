import { axiosAPIAuthorizedClient } from "@/config/http-client";
import { Device } from "@/types/device";
import User, { Profile } from "@/types/user";
import { AxiosError } from "axios";







interface ApiError {

    error?: string,
    message?: string
}


export const getUserDevices: () => Promise<Array<Device>> = async () => {

    return await axiosAPIAuthorizedClient.get<Array<Device>>("/devices/my")
        .then(response => response.data)
        .catch((err: AxiosError<ApiError>) => {

            if (err.response) {

                console.log(err.response?.data);
                throw new Error("" + err.response?.data.error);

            }

            console.log(err.response);
            throw new Error("NETWORK_ERROR");
        })
}


export const updateProfile: (profile: Profile) => Promise<Profile> = async (profile) => {

    return await axiosAPIAuthorizedClient.put<Profile>("/users/me/profile", profile)
        .then(response => response.data)
        .catch((err: AxiosError<ApiError>) => {

            if (err.response) {

                console.log(err.response?.data);
                throw new Error("" + err.response?.data.error);

            }

            console.log(err.response);
            throw new Error("NETWORK_ERROR");
        })
}












