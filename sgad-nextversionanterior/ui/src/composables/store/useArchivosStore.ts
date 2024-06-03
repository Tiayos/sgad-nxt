import { Bitacora } from 'models/Bitacora.model';
import { createPinia, defineStore } from 'pinia';
import { FilterMatchMode } from "primevue/api";

export const useArchivosStore = defineStore('useArchivosStore', () => {
    const disabledMenu = ref<boolean>(true);
    const bitacorasList = ref<Bitacora[]>([]);
    const filtersSumillaBitacora = ref({
        global: { value: "", matchMode: FilterMatchMode.CONTAINS },
      });
    return {
        disabledMenu,
        bitacorasList,
        filtersSumillaBitacora
    }

})