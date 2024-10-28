import { NuxtAuthHandler } from '#auth'
import KeycloakProvider from "next-auth/providers/keycloak";

export default NuxtAuthHandler({
    secret: process.env.AUTH_SECRET,
    // pages: {
    //   // Change the default behavior to use `/login` as the path for the sign-in page
    //   signIn: '/',
    // },

    session: {
        strategy: "jwt",
    },

    providers: [
        //datos en el .env
        KeycloakProvider.default ({
            clientId: process.env.KEYCLOAK_ID,
            clientSecret: process.env.KEYCLOAK_SECRET,
            issuer: process.env.KEYCLOAK_ISSUER,
            //revisar cuando se actualice la libreria nuxt-auth
            // checks: ['none']
        })
    ],

    //quitar cuando se vaya a subir a produccion
    debug: true,
    callbacks: {
        async jwt({ token, account }) {
            if (account) {
                token.id_token = account.id_token
                token.access_token = account.access_token //***
                token.refresh_token = account.refresh_token; // Store refresh token here
                token.provider = account.provider
            }
            return token
        },
        async session({session,token}){
            session.access_token = token.access_token
            session.refresh_token = token.refresh_token; // Make refresh token available in the session
            return session
        },
        //solucion temporal hasta que se corrijan error en nuxt-auth
        //authConfig.globalAppMiddleware?.addDefaultCallbackUrl por authConfig.providers?.addDefaultCallbackUrl
        //   async redirect({ url, baseUrl }) {
        //     const redirectUrl = url.startsWith('/') ? new URL(baseUrl, url).toString() : url;
        //     console.log(`[next-auth] Redirecting to "${redirectUrl}" (resolved from url "${url}" and baseUrl "${baseUrl}")`);
        //    return redirectUrl;
        // },

    },
    events: {
        async signOut({ token }) {
            if (token.provider === "keycloak") {
                const issuerUrl = process.env.KEYCLOAK_ISSUER
                const logOutUrl = new URL(`${issuerUrl}/protocol/openid-connect/logout`)
                logOutUrl.searchParams.set("id_token_hint", token.id_token)
                await fetch(logOutUrl);
            }
        },
    }
})

