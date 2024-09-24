import { useToast } from "primevue/usetoast";
import type { Sumilla } from "~/models/Sumilla.model";
import type { SedeProjection } from "~/models/projection/SedeProjection.model";

export const useSumillaService = () => {
    const config = useRuntimeConfig();
    const apiUrl = `${config.public.SGAD_SUMILLA}`;
    const toast = useToast();
    const { data, signOut } = useAuth();
    let isRefreshing = false;

    const tokens = reactive({
        accessToken: data?.value?.access_token,
        refreshToken: data?.value?.refresh_token,
    });

    const refreshAccessToken = async () => {
        try {
            const refreshToken = tokens.refreshToken; // Usa el token desde el objeto reactivo
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
                tokens.accessToken = resp.access_token; // Actualiza el token en el objeto reactivo
                tokens.refreshToken = resp.refresh_token; // Actualiza el refresh token también
    
                console.log(tokens.accessToken, "Token refreshed successfully");
            } else {
                signOut();
                throw new Error("Failed to refresh the token");
            }
        } catch (error) {
            signOut();
            console.error("Token refresh failed", error);
            throw error;
        }
    };
    
    // Check if the token has expired
    const isTokenExpired = (token: string) => {
        try {
            const tokenPayload = JSON.parse(atob(token.split(".")[1]));
            const expirationTime = tokenPayload.exp * 1000; // Convertir a milisegundos
            return Date.now() > expirationTime;
        } catch (error) {
            console.error("Error parsing token payload", error);
            return true; // Si no se puede leer el token, lo tratamos como expirado
        }
    };
    
    let refreshSubscribers: Array<(newToken: string) => void> = [];
    
    const onAccessTokenRefreshed = (newToken: string) => {
        refreshSubscribers.forEach((callback) => callback(newToken));
        refreshSubscribers = [];
    };
    
    const addRefreshSubscriber = (callback: (newToken: string) => void) => {
        refreshSubscribers.push(callback);
    };
    
    const getHeaders = async (): Promise<{ headers: Record<string, string> }> => {
    
        if (!tokens.accessToken) {
            throw new Error("No access token available");
        }
    
        if (isTokenExpired(tokens.accessToken)) {
            if (!isRefreshing) {
                isRefreshing = true;
                await refreshAccessToken();
                isRefreshing = false;
                onAccessTokenRefreshed(tokens.accessToken);
            } else {
                return new Promise((resolve) => {
                    addRefreshSubscriber((newToken: string) => {
                        resolve({
                            headers: {
                                Authorization: `Bearer ${newToken}`,
                            },
                        });
                    });
                });
            }
        }
    
        return {
            headers: {
                Authorization: `Bearer ${tokens.accessToken}`,
            },
        };
    };
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