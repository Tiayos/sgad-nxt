import { createPinia, defineStore } from 'pinia';
import { FilterMatchMode } from "primevue/api";
import type {Bitacora} from "~/models/Bitacora.model";
import type {EventoBitacora} from "~/models/EventoBitacora.model";
import {useSessionStorage} from "~/utils/useSessionStorage";

export const useArchivosStore = defineStore('useArchivosStore', () => {
    const disabledMenu = ref<boolean>(true);
    const bitacorasList = ref<Bitacora[]>([]);
    const eventosBitacorasList = ref<EventoBitacora[]>([]);
    const eventoBitacora = ref<EventoBitacora>({} as EventoBitacora);
    const { data: access_token } = useSessionStorage<string>("access_token");
    const { data: refresh_token } = useSessionStorage<string>("refresh_token");

    const filtersSumillaBitacora = ref({
        global: { value: "", matchMode: FilterMatchMode.CONTAINS },
    });

     // Función para guardar los tokens en sessionStorage
     const setTokens = (accessToken: string, refreshToken: string) => {
        access_token.value = accessToken;
        refresh_token.value = refreshToken; 
    };

    // Función para obtener los tokens de sessionStorage
    const getTokens = () => {
        return {
            access_token: sessionStorage.getItem('access_token'),
            refresh_token: sessionStorage.getItem('refresh_token'),
        };
    };

    return {
        eventosBitacorasList,
        eventoBitacora,
        disabledMenu,
        bitacorasList,
        filtersSumillaBitacora,
        setTokens,
        getTokens,
    };

})