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

  modules: [
      '@pinia/nuxt',
      '@ups-dev/freya-nuxt',
      '@vee-validate/nuxt',
      '@sidebase/nuxt-auth'
  ],

  veeValidate: {
      autoImports: true
  },

  auth: {
      globalAppMiddleware: {
          isEnabled: true,
      },
      baseURL: `${process.env.AUTH_ORIGIN}/api/auth`,
      provider: {
          type: 'authjs',
          defaultProvider: 'keycloak',
          addDefaultCallbackUrl: true,
      },
  },

  pinia: {
      autoImports: ['defineStore', 'storeToRefs'],
  },

  runtimeConfig: {
      public: {
          assets: {
              images: '/images'
          },

          // * pruebas local
          //    SGAD_SUMILLA: '/sgad-nuxt/sumilla',
          //    SGAD_BITACORA: '/sgad-nuxt/bitacora',
          //    SGAD_PERSONA: '/sgad-nuxt/persona',
          //    SGAD_TRANSFERENCIA: '/sgad-nuxt/transferencia',
          //    SGAD_EVENTO: '/sgad-nuxt/eventoBitacora',
          //    SGAD_EMAIL: '/sgad-nuxt/email',

          // //* pruebas servidor
          // SGAD_SUMILLA: '',
          // SGAD_BITACORA: '',
          // SGAD_PERSONA: '',
          // SGAD_TRANSFERENCIA: '',
          // SGAD_EVENTO: '',
          // SGAD_EMAIL: '',

          //* pruebas servidor enlaces directos
          SGAD_SUMILLA: 'https://sgadsvc.ups.edu.ec/sgad/api/v1/sumilla',
          SGAD_BITACORA: 'https://sgadsvc.ups.edu.ec/sgad/api/v1/bitacora',
          SGAD_PERSONA: 'https://sgadsvc.ups.edu.ec/sgad/api/v1/persona',
          SGAD_TRANSFERENCIA: 'https://sgadsvc.ups.edu.ec/sgad/api/v1/transferencia',
          SGAD_EVENTO: 'https://sgadsvc.ups.edu.ec/sgad/api/v1/eventoBitacora',
          SGAD_EMAIL: 'https://sgadsvc.ups.edu.ec/sgad/api/v1/email',

      }
  },

  css: [
      'primevue/resources/primevue.css',
      'primeicons/primeicons.css',
      '@ups-dev/freya-primevue/dist/esm/style.css',
  ],

  // *comentar local
  // nitro: {
  //     devProxy: {
  //         '/sgad-nuxt': {
  //             changeOrigin: true,
  //             target: 'http://localhost:8080/sgad/api/v1',
  //         },
  //     },
  // }
  build: {
      transpile: [
          // 'lodash',
          'primevue',
          '@vee-validate/rules',
          'vue-demi',
      ]
  },

  compatibilityDate: '2024-08-05'
})