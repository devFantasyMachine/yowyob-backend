import { Address } from "./tenant"


export interface Profile {

    "version"?: number,
    "firstName"?: string,
    "lastName"?: string,
    "avatar"?: string,
    "about"?: string,
    "picture"?: string,
    "gender"?: string,
    "locale"?: string,
    "timezone"?: string,
    "birthdate"?: string,
    "contacts"?: Array<Contact>,
    "address"?: Array<Address>

}

export interface Contact {

    value: string,
    isVerified: boolean,
    isVerifiedAt: string

}


export default interface User {

    userId: string,
    username: string,
    email: string,
    phone: string,
    profile: Profile
}

