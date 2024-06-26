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
                { charset: 'utf-8' },
                { name: 'viewport', content: 'width=device-width, initial-scale=1' },
            ]
        }
    },
    modules: [
        '@pinia/nuxt',
        '@ups-dev/freya-nuxt',
        '@vee-validate/nuxt',
        '@sidebase/nuxt-auth'
    ],

    veeValidate:{
        autoImports: true
    },

    auth: {
        globalAppMiddleware: true,
        baseURL: process.env.AUTH_ORIGIN, // Utilizar la variable de entorno para la URL base
        provider: {
          type: 'authjs',
          defaultProvider: 'keycloak',
          addDefaultCallbackUrl: process.env.NEXTAUTH_URL, // Utilizar la variable de entorno
        //   addDefaultCallbackUrl: true,
        },
      },

   
    pinia: {
        autoImports: ['defineStore', 'storeToRefs'],
    },
    runtimeConfig: {
        public: {
            assets:{
                images: '/images'
            },
           
        //* pruebas local
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
        SGAD_SUMILLA: 'https://sgad-svc.ups.edu.ec/sumilla',
        SGAD_BITACORA: 'https://sgad-svc.ups.edu.ec/bitacora',
        SGAD_PERSONA: 'https://sgad-svc.ups.edu.ec/persona',
        SGAD_TRANSFERENCIA: 'https://sgad-svc.ups.edu.ec/transferencia',
        SGAD_EVENTO: 'https://sgad-svc.ups.edu.ec/eventoBitacora',
        SGAD_EMAIL: 'https://sgad-svc.ups.edu.ec/email',


        }
    },

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
    //*comentar local
    // nitro: {
    //     devProxy: {
    //         '/sgad-nuxt': {
    //             changeOrigin: true,
    //             target: 'http://localhost:8080/sgad/api/v1',
    //         },
    //     },
    // }
})
