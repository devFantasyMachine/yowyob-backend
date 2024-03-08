import type { NextRequest } from 'next/server'
import { NextResponse } from 'next/server'
import { DEVICE_ID_COOKIE_NAME, SESSION_COOKIE_NAME } from './constants/app'

type Middleware = (request: NextRequest) => NextResponse

// eslint-disable-next-line @typescript-eslint/no-unused-vars
const redirectIfAuthenticated: Middleware = (request) => {

  const authSession = request.cookies.get(SESSION_COOKIE_NAME)?.value;
  const deviceId = request.cookies.get(DEVICE_ID_COOKIE_NAME)?.value;

  if (authSession && deviceId) {
    return NextResponse.redirect(new URL('/account', request.url))
  }

  return NextResponse.next()
}


const authenticated: Middleware = (request) => {

  const authSession = request.cookies.get(SESSION_COOKIE_NAME)?.value;
  const deviceId = request.cookies.get(DEVICE_ID_COOKIE_NAME)?.value;

  if (!authSession || !deviceId) {

    const response = NextResponse.redirect(new URL('/login', request.url))
    response.cookies.set({
      name: 'redirectUrl',
      value: request.url,
    })
    return response
  }

  return NextResponse.next()

}

export default function middleware(request: NextRequest) {
  // Uncomment if you want to redirect if authenticated.
  if ([
    '/login',
    '/register',
  ].includes(request.nextUrl.pathname)) {
    return redirectIfAuthenticated(request)
  }

  if ([
    '/',
    '/account',
    '/oauth2',
    '/api/oauth',
  ].includes(request.nextUrl.pathname) || request.nextUrl.pathname.includes("/oauth2")
    || request.nextUrl.pathname.includes("/account")
    || request.nextUrl.pathname.includes("/console")) {
      
    return authenticated(request)
  }

  return NextResponse.next()
}
