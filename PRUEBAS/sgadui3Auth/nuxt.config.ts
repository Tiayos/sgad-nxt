// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  modules: ['@sidebase/nuxt-auth'],
  auth: {
    globalAppMiddleware: true,
    // baseURL: process.env.AUTH_ORIGIN, // Utilizar la variable de entorno para la URL base
    provider: {
      type: 'authjs',
      defaultProvider: 'keycloak',
    //   addDefaultCallbackUrl: process.env.NEXTAUTH_URL, // Utilizar la variable de entorno
      addDefaultCallbackUrl: true,
    },
  },
})
