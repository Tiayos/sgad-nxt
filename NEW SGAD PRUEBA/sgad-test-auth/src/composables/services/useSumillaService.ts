import { useToast } from "primevue/usetoast";
import type {Sumilla} from "~/models/Sumilla.model";
import type {SedeProjection} from "~/models/projection/SedeProjection.model";

export const useSumillaService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_SUMILLA}`
    const toast = useToast();
    const { data } = useAuth();

    const getHeaders = () => {
        return {
            headers: {
                Authorization: `Bearer ${data.access_token}`,
            },
        };
    };

    const getSumillas = async(): Promise<Sumilla[]> => {
        try {
            const resp = await $fetch<Sumilla[]>(`${apiUrl}`, getHeaders())
            return resp;
        } catch (error) {
            throw new Error("Error al consultar las sumillas");
        }
    }

    const getSumillasBySede = async(sede: number): Promise<Sumilla[]> => {
        try {
            const resp = await $fetch<Sumilla[]>(`${apiUrl}/findSumillasBySede?sede=${sede}`, getHeaders())
            return resp;
        } catch (error) {
            throw new Error("Error al consultar las sumillas");
        }
    }

    const getSedeByEmail = async(email:string): Promise<SedeProjection> => {
        try {
            return await $fetch<SedeProjection>(`${apiUrl}/getSedeByEmail?email=${email}`, getHeaders())
        } catch (error) {
            throw new Error("Error al consultar las sumillas");
        }
    }

    const getSumillaByNumeroSumilla = async(numeroSumilla:string): Promise<Sumilla|null> => {
        try {
            return await $fetch<Sumilla|null>(`${apiUrl}/findSumillaByNumSumilla?numeroSumilla=${numeroSumilla}`, getHeaders())
        } catch (error:any) {
            toast.add({
                severity: "error",
                detail: `No se encontró la sumilla`,
                life: 2000,
              });
            return null;
        }
    }

    const saveSumilla = async (sumilla: Sumilla, email: string): Promise<Sumilla> => {
        try {
            return await $fetch<Sumilla>(`${apiUrl}?email=${email}`, {
                method: "POST",
                body: sumilla,
                ...getHeaders(), // Add headers with token
            });
        } catch (error: any) {
            return {} as Sumilla;
        }
    };

    const saveSumillaExterna = async (sumilla: Sumilla): Promise<Sumilla> => {
        try {
            return await $fetch<Sumilla>(`${apiUrl}/saveSumillaExt`, {
                method: "POST",
                body: sumilla,
                ...getHeaders(), // Add headers with token
            });
        } catch (error: any) {
            return {} as Sumilla;
        }
    };

    const editSumilla = async (sumilla: Sumilla, codigo: number) => {
        try {
            await $fetch(`${apiUrl}?codigo=${codigo}`, {
                method: "PUT",
                body: sumilla,
                ...getHeaders(), // Add headers with token
            });
        } catch (error) {
            throw new Error("Error al editar la bitacora");
        }
    };

    const deleteSumilla = async (codigo: number) => {
        try {
            await $fetch(`${apiUrl}?codigo=${codigo}`, {
                method: "DELETE",
                ...getHeaders(), // Add headers with token
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
        saveSumillaExterna
    }
}
