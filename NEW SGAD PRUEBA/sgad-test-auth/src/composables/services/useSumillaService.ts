import { useToast } from "primevue/usetoast";
import type { Sumilla } from "~/models/Sumilla.model";
import type { SedeProjection } from "~/models/projection/SedeProjection.model";
import {useArchivosStore} from "~/composables/store/useArchivosStore";

export const useSumillaService = () => {
    const config = useRuntimeConfig();
    const apiUrl = `${config.public.SGAD_SUMILLA}`;
    const toast = useToast();
    const { data } = useAuth();
    const archivosStore = useArchivosStore();

    const refreshAccessToken = async () => {
        try {
            const refreshToken = archivosStore.refreshToken; // Obtén el refresh token desde el store
            if (!refreshToken) {
                throw new Error("No refresh token available");
            }
    
            const tokenUrl = `${config.public.KEYCLOAK_ISSUER}/protocol/openid-connect/token`;
            const resp = await $fetch(tokenUrl, {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: new URLSearchParams({
                    grant_type: "refresh_token",
                    client_id: config.public.KEYCLOAK_ID as string,
                    client_secret: config.public.KEYCLOAK_SECRET as string,
                    refresh_token: refreshToken,
                }),
            });
    
            if (resp.access_token) {
                // Actualiza los tokens en el store
                archivosStore.setTokens(resp.access_token, resp.refresh_token);
                console.log("Token refreshed successfully");
            } else {
                throw new Error("Failed to refresh the token");
            }
        } catch (error) {
            console.error("Token refresh failed", error);
            throw error;
        }
    };
    
    // Check if the token has expired
    const isTokenExpired = (token: string) => {
        const tokenPayload = JSON.parse(atob(token.split(".")[1]));
        const expirationTime = tokenPayload.exp * 1000;
        return Date.now() > expirationTime;
    };
    
    // Get headers with access token, refresh if needed
    const getHeaders = async () => {
        let token = data?.value?.access_token;
    
        if (!token) {
            throw new Error("No access token available");
        }
    
        // Refresh the token if it's expired
        if (isTokenExpired(token)) {
            console.log("Token expired, refreshing...");
            await refreshAccessToken();
            token = data?.value?.access_token; // Update with new token
        }
    
        return {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };
    }

    // Fetch all sumillas
    const getSumillas = async (): Promise<Sumilla[]> => {
        try {
            const resp = await $fetch<Sumilla[]>(`${apiUrl}`, await getHeaders());
            return resp;
        } catch (error) {
            throw new Error("Error fetching sumillas");
        }
    };

    // Fetch sumillas by sede
    const getSumillasBySede = async (sede: number): Promise<Sumilla[]> => {
        try {
            const resp = await $fetch<Sumilla[]>(`${apiUrl}/findSumillasBySede?sede=${sede}`, await getHeaders());
            return resp;
        } catch (error) {
            throw new Error("Error fetching sumillas by sede");
        }
    };

    // Fetch sede by email
    const getSedeByEmail = async (email: string): Promise<SedeProjection> => {
        try {
            return await $fetch<SedeProjection>(`${apiUrl}/getSedeByEmail?email=${email}`, await getHeaders());
        } catch (error) {
            throw new Error("Error fetching sede by email");
        }
    };

    // Fetch sumilla by numeroSumilla
    const getSumillaByNumeroSumilla = async (numeroSumilla: string): Promise<Sumilla | null> => {
        try {
            return await $fetch<Sumilla | null>(`${apiUrl}/findSumillaByNumSumilla?numeroSumilla=${numeroSumilla}`, await getHeaders());
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
                ...await getHeaders(),
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
                ...await getHeaders(),
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
                ...await getHeaders(),
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
                ...await getHeaders(),
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