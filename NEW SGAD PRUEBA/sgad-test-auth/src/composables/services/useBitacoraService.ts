import type {Bitacora} from "~/models/Bitacora.model";
import type {DocumentoBitacora} from "~/models/DocumentoBitacora.model";

export const useBitacoraService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_BITACORA}`
    let isRefreshing = false;
    const { data, signOut } = useAuth();

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

    const getBitacoras = async(): Promise<Bitacora[]> => {
        try {
            const resp = await $fetch<Bitacora[]>(`${apiUrl}`, await getHeaders())
            return resp;
        } catch (error) {
            throw new Error("Error al consultar las bitacoras");
        }
    }

    const getBitacorasBySede = async(sede:number): Promise<Bitacora[]> => {
        try {
            const resp = await $fetch<Bitacora[]>(`${apiUrl}/getBitacorasBySede?sede=${sede}`, await getHeaders())
            return resp;
        } catch (error) {
            throw new Error("Error al consultar las bitacoras");
        }
    }

    const getBitacorasByFechaAndEstado = async(fechaInicio:string, fechaFin:string, resPerCodigo:number): Promise<Bitacora[]> => {
        try {
            const resp = await $fetch<Bitacora[]>(`${apiUrl}/getBitacorasByFechasAndEstado?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}&resPerCodigo=${resPerCodigo}`, await getHeaders())
            return resp;
        } catch (error) {
            throw new Error("Error al consultar las bitacoras");
        }
    }

    const getBitacoraByNumSumilla = async(numSumilla:string): Promise<Bitacora> => {
        try {
            return await $fetch<Bitacora>(`${apiUrl}/getBitacoraByNumSumilla?numSumilla=${numSumilla}`, await getHeaders())
        } catch (error) {
            throw new Error("Error al consultar las bitacoras");
        }
    }

    const getDocumentosByBitCodigo = async(bitCodigo:number): Promise<DocumentoBitacora[]> => {
        try {
            return await $fetch<DocumentoBitacora[]>(`${apiUrl}/getDocumentosByBitCodigo?bitCodigo=${bitCodigo}`, await getHeaders())
        } catch (error) {
            throw new Error("Error al consultar los documentos");
        }
    }

    const getBitacorasByPerCodigoDestinatario = async(perCodigoDestinatario:number): Promise<Bitacora[]> => {
        try {
            return await $fetch<Bitacora[]>(`${apiUrl}/getBitacorasByDestinatario?perCodigoDestinatario=${perCodigoDestinatario}`,await getHeaders())
        } catch (error) {
            throw new Error("Error al consultar las bitacoras");
        }
    }

    const saveBitacora = async(bitacora:Bitacora):Promise<Bitacora>=>{
        try {
           return await $fetch<Bitacora>(`${apiUrl}`,
            {
                method: 'POST',
                body:  bitacora,
                ...await getHeaders(),
            })
        } catch (error) {
            throw new Error("Error al guardar las bitacoras");
        }
    }

    const saveDocumentoBitacora = async(documentoBitacora:DocumentoBitacora)=>{
        try {
            await $fetch(`${apiUrl}/saveDocumentos`,
            {
                method: 'POST',
                body:  documentoBitacora,
                ...await getHeaders(),
            })
        } catch (error) {
            console.log(error);
        }
    }

    const editBitacora = async(bitacora:Bitacora, codigo:number)=>{
        try {
            await $fetch(`${apiUrl}?codigo=${codigo}`,
            {
                method: 'PUT',
                body:  bitacora,
                ...await getHeaders(),
            })
        } catch (error) {
            throw new Error("Error al editar la bitacora");
        }
    }

    const editEstadoEnvioBitacora = async(bitacora:Bitacora)=>{
        try {
            await $fetch(`${apiUrl}/updateEstadoEnvioDestinatario`,
            {
                method: 'PUT',
                body:  bitacora,
                ...await getHeaders(),
            })
        } catch (error) {
            throw new Error("Error al editar la bitacora");
        }
    }

    const deleteBitacora = async(codigo: Number)=>{
        try {
            await $fetch(`${apiUrl}?codigo=${codigo}`,{
                method: 'DELETE',
                ...await getHeaders(),

            })
        } catch (error) {
            throw error;
        }
    }

    const deleteBitacoraByNumSumilla = async(sumCodigo: number)=>{
        try {
            await $fetch(`${apiUrl}/deleteBitacoraBySumCodigo?sumCodigo=${sumCodigo}`,{
                method: 'DELETE',
                ...await getHeaders(),

            })
        } catch (error) {
            throw error;
        }
    }

    const deleteDocumentosByBitCodigo = async(bitCodigo: number)=>{
        try {
            await $fetch(`${apiUrl}/deleteDocumentos?bitCodigo=${bitCodigo}`,{
                method: 'DELETE',
                ...await getHeaders(),

            })
        } catch (error) {
            throw error;
        }
    }

    return {
        getBitacoras,
        getBitacorasBySede,
        getBitacorasByFechaAndEstado,
        getBitacoraByNumSumilla,
        saveBitacora,
        editBitacora,
        editEstadoEnvioBitacora,
        deleteBitacora,
        deleteBitacoraByNumSumilla,
        getBitacorasByPerCodigoDestinatario,
        saveDocumentoBitacora,
        getDocumentosByBitCodigo,
        deleteDocumentosByBitCodigo

    }
}
