import { defineStore } from 'pinia';
import { useToast } from "primevue/usetoast";

export const useArchivosStore = defineStore('useArchivosStore', () => {
    const toast = useToast(); // * PrimeVue - toast
    const disabledMenu = ref<boolean>(true);

    return {
        disabledMenu,
      
    }

})