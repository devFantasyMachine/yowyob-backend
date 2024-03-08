import axios, { AxiosInstance } from "axios";
import { API_BASE_URL } from "./api-config";
import { getCookie } from "cookies-next";
import { DEVICE_ID_COOKIE_NAME, SESSION_COOKIE_NAME } from "@/constants/app";



const axiosAPIClient: AxiosInstance = axios.create({
    baseURL: API_BASE_URL,
});

axiosAPIClient.interceptors.request.use(
    async (config) => {

        config.headers["X-Tenant-Id"] = "00000000-0000-0000-0000-000000000000"
        config.headers["X-Auth-Key"] = `MzYzY2E2NDAtYjFmZi00YmU0LWJkZjAtZTM4NWVkNGU5Nzc4f6s07u8OFhhDNJSuSP4qM9z6dnM8XdgfdimjsgjZxZgdZ3y501eSJlUM50wYzlFFCJk=`;
        config.headers["User-Agent"] = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/119.0"
        //window.navigator.userAgent

        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);




export  const axiosAPIAuthorizedClient: AxiosInstance = axios.create({
    baseURL: API_BASE_URL,
});

axiosAPIAuthorizedClient.interceptors.request.use(
    async (config) => {

        config.headers["X-Tenant-Id"] = "00000000-0000-0000-0000-000000000000"
        config.headers["X-Auth-Key"] = `MzYzY2E2NDAtYjFmZi00YmU0LWJkZjAtZTM4NWVkNGU5Nzc4f6s07u8OFhhDNJSuSP4qM9z6dnM8XdgfdimjsgjZxZgdZ3y501eSJlUM50wYzlFFCJk=`;
        config.headers["User-Agent"] = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/119.0"
        // window.navigator.userAgent

        config.headers["X-Device-Id"] = getCookie(DEVICE_ID_COOKIE_NAME);
        config.headers["X-Session-Token"] = getCookie(SESSION_COOKIE_NAME);

        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);


export default axiosAPIClient;

