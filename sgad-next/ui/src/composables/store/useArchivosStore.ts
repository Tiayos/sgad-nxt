import { createPinia, defineStore } from 'pinia';
import { useToast } from "primevue/usetoast";

export const useArchivosStore = defineStore('useArchivosStore', () => {
    const disabledMenu = ref<boolean>(true);

    

    return {
        disabledMenu,
    }

})