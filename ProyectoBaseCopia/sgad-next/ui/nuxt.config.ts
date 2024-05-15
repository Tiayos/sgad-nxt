// https://nuxt.com/docs/api/configuration/nuxt-config

export default defineNuxtConfig({
    srcDir: 'src/',
    
    app: {
        head: {
            title: 'SCJ',
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
        // baseURL: process.env.AUTH_ORIGIN, // Utilizar la variable de entorno para la URL base
        provider: {
          type: 'authjs',
          defaultProvider: 'keycloak',
        //   addDefaultCallbackUrl: process.env.NEXTAUTH_URL, // Utilizar la variable de entorno
          addDefaultCallbackUrl: true,
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

        //    SCJ_CASOS_FORMULARIO: '',
        //    SCJ_DERIVACIONES: '',
        //    SCJ_CAMPOS_FORMULARIO: '',
        //    SCJ_PATROCINIOS: '',
        //    SCJ_PERSONA: '',
        //    SCJ_AUTH: '',
        //    SCJ_ADMIN_USERS: '',
        //    SCJ_REPORT: '',
        //    SCJ_USUARIOS: ''
           
        //* pruebas local
           SCJ_CASOS_FORMULARIO: '/scj/casosFormularios',
           SCJ_DERIVACIONES: '/scj/derivaciones',
           SCJ_CAMPOS_FORMULARIO:'/scj/formulario',
           SCJ_PATROCINIOS:'/scj/patrocinios',
           SCJ_PERSONA:'/scj/persona',
           SCJ_AUTH:'/scjAuth/auth/getToken',
           SCJ_ADMIN_USERS:'/scjAuth/usersManagment',
           SCJ_REPORT:'/scj/report',
           SCJ_USUARIOS: '/scj/usuariosAsignados'
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
    nitro: {
        devProxy: {
            '/scj': {
                changeOrigin: true,
                target: 'http://localhost:8080/scj/api/v1',
            },
            '/scjAuth': {
                changeOrigin: true,
                target: 'http://localhost:8080/scj/api/v1',
            },
        },
    }
})
