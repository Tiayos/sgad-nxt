import { useToast } from "primevue/usetoast";
import type { Sumilla } from "~/models/Sumilla.model";
import type { SedeProjection } from "~/models/projection/SedeProjection.model";
import { useAuthService } from "./useAuthService";
import { useArchivosStore } from "../store/useArchivosStore";

export const useSumillaService = () => {
    const config = useRuntimeConfig();
    const apiUrl = `${config.public.SGAD_SUMILLA}`;
    const toast = useToast();
    const authService = useAuthService();
    const store = useArchivosStore();

        // Fetch all sumillas
        const getAuth = async (): Promise<string> => {
            const {data, error} = await useLazyFetch<string>(`${apiUrl}/validateUser?sede`, await authService.getHeaders());
            if (error.value){
                store.error = error.value.statusCode!;
                throw createError({
                    ...error.value,
                    statusMessage: `Could not fetch data from ...... ${error.value.statusCode}`,
                });                
            }
            if(data.value){
                return data.value!;
            }
            return '';
        };

    // Fetch all sumillas
    const getSumillas = async (): Promise<Sumilla[]> => {
        try {
            const resp = await $fetch<Sumilla[]>(`${apiUrl}`, await authService.getHeaders());
            return resp;
        } catch (error) {
            throw new Error("Error fetching all sumillas");
        }
    };

     const getSumillasBySede = async (sede: number): Promise<Sumilla[]> => {
            const {data, error} = await useLazyFetch<Sumilla[]>(`${apiUrl}/findSumillasBySede?sede=${sede}`, await authService.getHeaders());
            if (error.value){
                store.error = error.value.statusCode!;
                throw createError({
                    ...error.value,
                    statusMessage: `Could not fetch data from ...... ${error.value.statusCode}`,
                });
            }
            return data.value ? data.value : [];
    };

    // Fetch sede by email
    const getSedeByEmail = async (email: string): Promise<SedeProjection> => {
        try {
            return await $fetch<SedeProjection>(`${apiUrl}/getSedeByEmail?email=${email}`, await authService.getHeaders());
        } catch (error) {
            throw new Error("Error fetching sede by email");
        }
    };

    // Fetch sumilla by numeroSumilla
    const getSumillaByNumeroSumilla = async (numeroSumilla: string): Promise<Sumilla | null> => {
        try {
            return await $fetch<Sumilla | null>(`${apiUrl}/findSumillaByNumSumilla?numeroSumilla=${numeroSumilla}`, await authService.getHeaders());
        } catch (error: any) {
            toast.add({
                severity: "error",
                detail: "No se encontró la sumilla",
                life: 2000,
            });
            return null;
        }
    };

    // Save a new sumilla
    const saveSumilla = async (sumilla: Sumilla, email: string): Promise<Sumilla> => {
        try {
            return await $fetch<Sumilla>(`${apiUrl}?email=${email}`, {
                method: "POST",
                body: sumilla,
                ...await authService.getHeaders(),
            });
        } catch (error: any) {
            return {} as Sumilla;
        }
    };

    // Save external sumilla
    const saveSumillaExterna = async (sumilla: Sumilla): Promise<Sumilla> => {
        try {
            return await $fetch<Sumilla>(`${apiUrl}/saveSumillaExt`, {
                method: "POST",
                body: sumilla,
                ...await authService.getHeaders(),
            });
        } catch (error: any) {
            return {} as Sumilla;
        }
    };

    // Edit an existing sumilla
    const editSumilla = async (sumilla: Sumilla, codigo: number) => {
        try {
            await $fetch(`${apiUrl}?codigo=${codigo}`, {
                method: "PUT",
                body: sumilla,
                ...await authService.getHeaders(),
            });
        } catch (error) {
            throw new Error("Error editing sumilla");
        }
    };

    // Delete an existing sumilla
    const deleteSumilla = async (codigo: number) => {
        try {
            await $fetch(`${apiUrl}?codigo=${codigo}`, {
                method: "DELETE",
                ...await authService.getHeaders(),
            });
        } catch (error) {
            throw error;
        }
    };

    return {
        getSumillas,
        getSumillasBySede,
        getSumillaByNumeroSumilla,
        saveSumilla,
        editSumilla,
        deleteSumilla,
        getSedeByEmail,
        saveSumillaExterna,
        getAuth
    };
};