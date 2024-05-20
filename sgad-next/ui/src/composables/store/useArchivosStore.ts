import { Bitacora } from 'models/Bitacora.model';
import { createPinia, defineStore } from 'pinia';
import { useToast } from "primevue/usetoast";

export const useArchivosStore = defineStore('useArchivosStore', () => {
    const disabledMenu = ref<boolean>(true);
    const bitacorasList = ref<Bitacora[]>([]);

    return {
        disabledMenu,
        bitacorasList
    }

})