// https://nuxt.com/docs/api/configuration/nuxt-config

export default defineNuxtConfig({
    srcDir: 'src/',
    
    app: {
        head: {
            title: 'SGAD',
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

    auth: {
        globalAppMiddleware: true,
        baseURL: process.env.AUTH_ORIGIN, // Utilizar la variable de entorno para la URL base -- comentar en local
        provider: {
          type: 'authjs',
          defaultProvider: 'keycloak',
          addDefaultCallbackUrl: process.env.NEXTAUTH_URL, // Utilizar la variable de entorno -- comentar en local
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

            SGAD_SUMILLA: '',
            SGAD_BITACORA: '',
            SGAD_PERSONA: '',
            SGAD_TRANSFERENCIA: '',
            SGAD_EVENTO: '',
            SGAD_EMAIL: '',
           
        //    SCJ_CASOS_FORMULARIO: 'https://scjback.ups.edu.ec/scj/api/v1/casosFormularios',
        //    SCJ_DERIVACIONES: 'https://scjback.ups.edu.ec/scj/api/v1/derivaciones',
        //    SCJ_CAMPOS_FORMULARIO:'https://scjback.ups.edu.ec/scj/api/v1/formulario',
        //    SCJ_PATROCINIOS:'https://scjback.ups.edu.ec/scj/api/v1/patrocinios',
        //    SCJ_PERSONA:'https://scjback.ups.edu.ec/scj/api/v1/persona',
        //    SCJ_AUTH:'https://scjback.ups.edu.ec/scj/auth/getToken',
        //    SCJ_ADMIN_USERS:'https://scjback.ups.edu.ec/scj/usersManagment',
        //    SCJ_REPORT:'https://scjback.ups.edu.ec/scj/api/v1/report',

        //* pruebas local
        //    SCJ_CASOS_FORMULARIO: '/scj/casosFormularios',
        //    SCJ_DERIVACIONES: '/scj/derivaciones',
        //    SCJ_CAMPOS_FORMULARIO:'/scj/formulario',
        //    SCJ_PATROCINIOS:'/scj/patrocinios',
        //    SCJ_PERSONA:'/scj/persona',
        //    SCJ_AUTH:'/scjAuth/auth/getToken',
        //    SCJ_ADMIN_USERS:'/scjAuth/usersManagment',
        //    SCJ_REPORT:'/scj/report',
        //    SCJ_USUARIOS: '/scj/usuariosAsignados'
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
