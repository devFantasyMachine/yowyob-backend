

interface Requirable {
    required: boolean;
}


export interface TenantRegistrationConfig {

    username: Requirable,
    birthdate: Requirable,
    firstName: Requirable,
    lastName: Requirable,
    phoneNumber: Requirable,
    email: Requirable,
    gender: Requirable,
    captcha: Requirable,

    useMagicLink: boolean,
    verifyEmail: Requirable,
    verifyEmailWhenChanged: Requirable,
    methods: Array<string>;
}

export interface Contact {

    value: string, 
}



export interface Address {

    country: string,
    city: string,
    timezone: string,
    street: string,
    latitude: string,
    longitude: string,
    region: string,
    zipcode: string,
    addAt: string
}


export interface Organisation {

    shortName: string,
    name: string,
    icon: string,
    description: string,
    privacyPolicyUrl: string,
    termOfUseUrl: string, 
    location: Address 
}

export interface Tenant {

    tenantRegistrationConfig: TenantRegistrationConfig,
    isEnabled: boolean,
    createdAt: string,
    organisation: Organisation,

}




export interface TenantConfig {

    "client-api-key": string,
    "name": string,
}


