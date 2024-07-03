// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  srcDir: 'src/',

  app: {
    head: {
      title: 'SGAD-NEXT',
      htmlAttrs: {
        lang: 'es'
      },
      meta: [
        {charset: 'utf-8'},
        {name: 'viewport', content: 'width=device-width, initial-scale=1'},
      ]
    }
  },

  devtools: { enabled: true },
  compatibilityDate: '2024-07-03',
  modules: [
    '@pinia/nuxt',
    '@ups-dev/freya-nuxt',
    '@vee-validate/nuxt',
    '@sidebase/nuxt-auth'

  ],

  css: [
    'primevue/resources/primevue.css',
    'primeicons/primeicons.css',
  ],
  build: {
    transpile: [
      'lodash',
      'primevue',
      '@vee-validate/rules',
    ]
  },

  auth: {
    globalAppMiddleware: true,
    baseURL: process.env.AUTH_ORIGIN, // Utilizar la variable de entorno para la URL base -- comentar en local
    provider: {
      type: 'authjs',
      defaultProvider: 'keycloak',
      addDefaultCallbackUrl: process.env.NEXTAUTH_URL, // Utilizar la variable de entorno -- comentar en local
      // addDefaultCallbackUrl: true,
    },
  },

})