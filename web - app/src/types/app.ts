import { Contact } from "./user";



export interface App {

    id: string,
    active: boolean,
    shortName: string,
    name: string,
    icon: string,
    description: string,
    homePage: string,
    privacyPolicyLink: string,
    termOfUseLink: string,
    support: Contact,
 
    availableCountriesCodes: string[],
    isCountryBased: boolean,
    familyConfig: FamilyConfig,
    scopes: Scope[],
}


interface Scope {

    name: string,
    scopeDesc: string;
}

interface FamilyConfig {

    enabled: boolean,
    allowChildRegistrations: boolean,
    deleteOrphanedAccounts: boolean,
    deleteOrphanedAccountsDays: number,
    maximumChildAge: number;
    minimumOwnerAge: number;
}

