import { createPinia, defineStore } from 'pinia';
import { FilterMatchMode } from "primevue/api";
import type {Bitacora} from "~/models/Bitacora.model";
import type {EventoBitacora} from "~/models/EventoBitacora.model";

export const useArchivosStore = defineStore('useArchivosStore', () => {
    const disabledMenu = ref<boolean>(true);
    const bitacorasList = ref<Bitacora[]>([]);
    const eventosBitacorasList = ref<EventoBitacora[]>([]);
    const eventoBitacora = ref<EventoBitacora>({} as EventoBitacora);

    const filtersSumillaBitacora = ref({
        global: { value: "", matchMode: FilterMatchMode.CONTAINS },
    });


    return {
        eventosBitacorasList,
        eventoBitacora,
        disabledMenu,
        bitacorasList,
        filtersSumillaBitacora,
    };

})