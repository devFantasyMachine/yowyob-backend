
export interface RegistrationRequest {

    username?: string,
    password?: string,
    email?: string,
    phoneNumber?: string,
    firstName?: string,
    lastName?: string,
    gender?: string,
    birthdate?: string,
    timezone?: string
}

export interface RegistrationResponse {

    isSuccess: boolean,
    errors: Array<string>,
    verificationId?: string,
}



