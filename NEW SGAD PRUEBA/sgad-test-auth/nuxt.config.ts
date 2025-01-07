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

  hooks: {
    'render:errorMiddleware'(app) {
      app.use((err: any, req: any, res: any, next: any) => {
        console.error('Captured error:', err); // Log detallado en el servidor
        
        // Enviar un mensaje de error al cliente
        res.writeHead(500, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ statusCode: 500, message: 'Ha ocurrido un error en el servidor.' }));
      });
    },
  },

  modules: [
    '@pinia/nuxt',
    '@ups-dev/freya-nuxt',
    '@vee-validate/nuxt',
    '@sidebase/nuxt-auth',
  ],


  auth: {
    globalAppMiddleware: true,
    baseURL: process.env.AUTH_ORIGIN, // comentar en local
    provider: {
      type: 'authjs',
      defaultProvider: 'keycloak',
      addDefaultCallbackUrl: process.env.NEXTAUTH_URL, //comentar en local
      // addDefaultCallbackUrl: true,
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

      //* pruebas servidor enlaces directos
      SGAD_SUMILLA: 'https://sgadpsvc.ups.edu.ec/sgad/api/v1/sumilla',
      SGAD_BITACORA: 'https://sgadpsvc.ups.edu.ec/sgad/api/v1/bitacora',
      SGAD_PERSONA: 'https://sgadpsvc.ups.edu.ec/sgad/api/v1/persona',
      SGAD_TRANSFERENCIA: 'https://sgadpsvc.ups.edu.ec/sgad/api/v1/transferencia',
      SGAD_EVENTO: 'https://sgadpsvc.ups.edu.ec/sgad/api/v1/eventoBitacora',
      SGAD_EMAIL: 'https://sgadpsvc.ups.edu.ec/sgad/api/v1/email',
      SGAD_BITACORA_EXTERNO: 'https://sgadpsvc.ups.edu.ec/sgad/api/v1/bitacoraExternos',
      SGAD_DOCUMENTOS_EXTERNOS: 'https://sgadpsvc.ups.edu.ec/sgad/api/v1/documentosExternos',

      //?PRODUCCIÓN
      // SGAD_SUMILLA: 'https://sgadpsvc.ups.edu.ec/sgad/api/v1/sumilla',
      // SGAD_BITACORA: 'https://sgadpsvc.ups.edu.ec/sgad/api/v1/bitacora',
      // SGAD_PERSONA: 'https://sgadpsvc.ups.edu.ec/sgad/api/v1/persona',
      // SGAD_TRANSFERENCIA: 'https://sgadpsvc.ups.edu.ec/sgad/api/v1/transferencia',
      // SGAD_EVENTO: 'https://sgadpsvc.ups.edu.ec/sgad/api/v1/eventoBitacora',
      // SGAD_EMAIL: 'https://sgadpsvc.ups.edu.ec/sgad/api/v1/email',
      // SGAD_BITACORA_EXTERNO: 'https://sgadpsvc.ups.edu.ec/sgad/api/v1/bitacoraExternos',
      // SGAD_DOCUMENTOS_EXTERNOS: 'https://sgadpsvc.ups.edu.ec/sgad/api/v1/documentosExternos',

      // * pruebas local
        //  SGAD_SUMILLA: '/sgad-nuxt/sumilla',
        //  SGAD_BITACORA: '/sgad-nuxt/bitacora',
        //  SGAD_PERSONA: '/sgad-nuxt/persona',
        //  SGAD_TRANSFERENCIA: '/sgad-nuxt/transferencia',
        //  SGAD_EVENTO: '/sgad-nuxt/eventoBitacora',
        //  SGAD_EMAIL: '/sgad-nuxt/email',
        //  SGAD_BITACORA_EXTERNO: '/sgad-nuxt/bitacoraExternos',
        //  SGAD_DOCUMENTOS_EXTERNOS: '/sgad-nuxt/documentosExternos',

        //  PORT:3000,
        //  KEYCLOAK_ISSUER:'https://auth.ups.edu.ec/realms/dev',
        //  KEYCLOAK_ID:'sgad-next-dev',
        //  KEYCLOAK_SECRET:'evVRjt1nt1CUldvfGEDOeEvx5kMXRZtq',
        //  AUTH_ORIGIN:'http://localhost:3000/',
        //  AUTH_SECRET:12345,
        //  NEXTAUTH_URL:'http://localhost:3000/',

         PORT:3000,
         KEYCLOAK_ISSUER:'https://auth.ups.edu.ec/realms/dev',
         KEYCLOAK_ID:'sgad-produccion',
         KEYCLOAK_SECRET:'IoYr2A0YPLSQ0U11GAvMJ2MAxCSsK7Yx',
         AUTH_ORIGIN:'https://sgad.ups.edu.ec/',
         AUTH_SECRET:12345,
         NEXTAUTH_URL:'https://sgad.ups.edu.ec/',
    }
  },

  css: [
    'primevue/resources/primevue.css',
    'primeicons/primeicons.css',
    '@ups-dev/freya-primevue/dist/esm/style.css',
  ],

  // // *comentar local
  // nitro: {
  //     devProxy: {
  //         '/sgad-nuxt': {
  //             changeOrigin: true,
  //             target: 'http://localhost:8080/sgad/api/v1',
  //         },
  //     },
  // },

  build: {
    transpile: [
      // 'lodash',
      'primevue',
      '@vee-validate/rules',
      'vue-demi',
    ]
  },

  compatibilityDate: '2024-07-02'
})