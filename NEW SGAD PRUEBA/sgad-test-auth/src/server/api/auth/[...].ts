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
            issuer: process.env.KEYCLOAK_ISSUER,
            clientId: process.env.KEYCLOAK_ID,
            clientSecret: process.env.KEYCLOAK_SECRET,
            scope: 'openid email profile',
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
                token.access_token = account.access_token
                token.provider = account.provider
            }
            return token
        },
        async session({session,token}){
            session.access_token = token.access_token
            return session
        },
    },
    events: {
        async signOut({ token }) {
            console.log("TOKEN: "+token.id_token);
            if (token.provider === "keycloak") {
                const issuerUrl = process.env.KEYCLOAK_ISSUER
                const logOutUrl = new URL(`${issuerUrl}/protocol/openid-connect/logout`)
                logOutUrl.searchParams.set("id_token_hint", token.id_token)
                await fetch(logOutUrl);
            }
        },
    }
})

