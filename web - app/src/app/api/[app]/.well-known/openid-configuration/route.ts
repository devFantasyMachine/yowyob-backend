import { NextRequest, NextResponse } from 'next/server';


export async function GET(req: NextRequest) {

    const app = req.nextUrl.pathname.substring(5, req.nextUrl.pathname.indexOf('/', 5))

    return NextResponse.json(

        {
            issuer: `http://192.168.43.194:3000/${app}`,
            authorization_endpoint: `http://192.168.43.194:3000/${app}/oauth2/authorize`,
            "token_endpoint": "http://192.168.43.194:3090/oauth2/token",
            "token_endpoint_auth_methods_supported": [
                "client_secret_basic",
                "client_secret_post",
                "client_secret_jwt",
                "private_key_jwt"
            ],
            "jwks_uri": "http://192.168.43.194:3090/oauth2/jwks",
            "userinfo_endpoint": "http://192.168.43.194:3090/userinfo",
            "response_types_supported": [
                "code"
            ],
            "grant_types_supported": [
                "authorization_code",
                "client_credentials",
                "refresh_token"
            ],
            "revocation_endpoint": "http://192.168.43.194:3090/oauth2/revoke",
            "revocation_endpoint_auth_methods_supported": [
                "client_secret_basic",
                "client_secret_post",
                "client_secret_jwt",
                "private_key_jwt"
            ],
            "introspection_endpoint": "http://192.168.43.194:3090/oauth2/introspect",
            "introspection_endpoint_auth_methods_supported": [
                "client_secret_basic",
                "client_secret_post",
                "client_secret_jwt",
                "private_key_jwt"
            ],
            "subject_types_supported": [
                "public"
            ],
            "id_token_signing_alg_values_supported": [
                "RS256"
            ],
            "scopes_supported": [
                "openid"
            ]
        }
    )


}





