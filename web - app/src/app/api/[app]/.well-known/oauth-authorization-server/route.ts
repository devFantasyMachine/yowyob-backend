import { NextResponse, NextRequest } from 'next/server';


export async function GET(req: NextRequest) {

    const app = req.nextUrl.pathname.substring(5, req.nextUrl.pathname.indexOf('/', 5));

    return NextResponse.json(

        {
            issuer: `http://localhost:3000/${app}`,
            "authorization_endpoint": "http://localhost:8090/oauth2/authorize",
            "token_endpoint": "http://localhost:8090/oauth2/token",
            "token_endpoint_auth_methods_supported": [
                "client_secret_basic",
                "client_secret_post",
                "client_secret_jwt",
                "private_key_jwt"
            ],
            "jwks_uri": "http://localhost:8090/oauth2/jwks",
            "response_types_supported": [
                "code"
            ],
            "grant_types_supported": [
                "authorization_code",
                "client_credentials",
                "refresh_token"
            ],
            "revocation_endpoint": "http://localhost:8090/oauth2/revoke",
            "revocation_endpoint_auth_methods_supported": [
                "client_secret_basic",
                "client_secret_post",
                "client_secret_jwt",
                "private_key_jwt"
            ],
            "introspection_endpoint": "http://localhost:8090/oauth2/introspect",
            "introspection_endpoint_auth_methods_supported": [
                "client_secret_basic",
                "client_secret_post",
                "client_secret_jwt",
                "private_key_jwt"
            ],
            "code_challenge_methods_supported": [
                "S256"
            ]
        }

    )






}







