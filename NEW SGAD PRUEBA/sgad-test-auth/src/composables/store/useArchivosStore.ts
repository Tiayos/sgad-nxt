import { createPinia, defineStore } from 'pinia';
import { FilterMatchMode } from "primevue/api";
import type {Bitacora} from "~/models/Bitacora.model";
import type {EventoBitacora} from "~/models/EventoBitacora.model";

export const useArchivosStore = defineStore('useArchivosStore', () => {
    const disabledMenu = ref<boolean>(true);
    const bitacorasList = ref<Bitacora[]>([]);
    const eventosBitacorasList = ref<EventoBitacora[]>([]);
    const eventoBitacora = ref<EventoBitacora>({} as EventoBitacora);
    const error = ref<number>(0);

    const filtersSumillaBitacora = ref({
        global: { value: "", matchMode: FilterMatchMode.CONTAINS },
    });

    const appRoles = ref<string[]>([]);

    return {
        eventosBitacorasList,
        eventoBitacora,
        disabledMenu,
        bitacorasList,
        filtersSumillaBitacora,
        error,
        appRoles
    };

})