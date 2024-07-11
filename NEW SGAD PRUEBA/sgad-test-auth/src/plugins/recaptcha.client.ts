import { install } from "vue3-recaptcha-v2";

export default defineNuxtPlugin((nuxtApp) => {
    nuxtApp.vueApp.use(install, {
        sitekey: "6LcN-h8UAAAAAIg9fdv0v5FFQEPOfkdzfyWCkLof",
        cnDomains: false,
    });
});