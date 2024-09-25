import { useToast } from "primevue/usetoast";
import type { Sumilla } from "~/models/Sumilla.model";
import type { SedeProjection } from "~/models/projection/SedeProjection.model";
import { useAuthService } from "./useAuthService";

export const useSumillaService = () => {
    const config = useRuntimeConfig();
    const apiUrl = `${config.public.SGAD_SUMILLA}`;
    const toast = useToast();
    const authService = useAuthService();

    // Fetch all sumillas
    const getSumillas = async (): Promise<Sumilla[]> => {
        try {
            const resp = await $fetch<Sumilla[]>(`${apiUrl}`, await authService.getHeaders());
            return resp;
        } catch (error) {
            throw new Error("Error fetching all sumillas");
        }
    };

    // Fetch sumillas by sede
    const getSumillasBySede = async (sede: number): Promise<Sumilla[]> => {
        try {
            const resp = await $fetch<Sumilla[]>(`${apiUrl}/findSumillasBySede?sede=${sede}`, await authService.getHeaders());
            return resp;
        } catch (error:any) {
            throw new Error("Error fetching sumillas by sede", error);
        }
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
    };
};