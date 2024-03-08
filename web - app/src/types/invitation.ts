import { Contact, Profile } from "./user";


export type InvitationType = "VALIDATE_ADMIN_ACCOUNT" | "VALIDATE_DEVELOPER_ACCOUNT"


export default interface Invitation {

    sender: {email?: string, phone?: string, username?: string, profile: Profile},
    target: {email?: string, phone?: string, username?: string, profile: Profile},
    group: any,
    issueAt: string,
    subject: InvitationType,
    contact: Contact

}



