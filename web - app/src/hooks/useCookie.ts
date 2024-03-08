
import { deleteCookie, getCookie, setCookie } from "cookies-next";



export const getRedirect = () => {

    const redirect = getCookie('redirectUrl')
    if (redirect) {
        deleteCookie('redirectUrl')
        return redirect.toString()
    }

    return '/account'
}





