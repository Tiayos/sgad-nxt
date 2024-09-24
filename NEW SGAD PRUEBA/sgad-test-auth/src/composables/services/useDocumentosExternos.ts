import type {DocumentosExternos} from "~/models/DocumentosExternos.model";

export const useDocumentosExternosService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_DOCUMENTOS_EXTERNOS}`
    let isRefreshing = false;
    const { data } = useAuth();

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
                throw new Error("Failed to refresh the token");
            }
        } catch (error) {
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

    const getAllDocumentosExternos = async(): Promise<DocumentosExternos[]> => {
        try {
            return await $fetch<DocumentosExternos[]>(`${apiUrl}`, await getHeaders())
        } catch (error) {
            throw new Error("Error al consultar los documentos externos");
        }
    }

    const getDocumentoExternoById = async(codigo:number): Promise<DocumentosExternos> => {
        try {
            return await $fetch<DocumentosExternos>(`${apiUrl}/getDocumentById?codigo=${codigo}`, await getHeaders())
        } catch (error) {
            throw new Error("Error al consultar la bitacora externa segun ID");
        }
    }

    const getDocumentoExternoByBidCodigo = async(codigo:number): Promise<DocumentosExternos[]> => {
        try {
            return await $fetch<DocumentosExternos[]>(`${apiUrl}/getDocumentsByBidCodigo?bidCodigo=${codigo}`, await getHeaders())
        } catch (error) {
            throw new Error("Error al consultar la bitacora externa segun ID");
        }
    }

    const getDocumentoExternoByBidCodigoRecibidos = async(codigo:number): Promise<DocumentosExternos[]> => {
        try {
            return await $fetch<DocumentosExternos[]>(`${apiUrl}/getDocumentsByBidCodigoRecibidos?bidCodigo=${codigo}`, await getHeaders())
        } catch (error) {
            throw new Error("Error al consultar la bitacora externa segun ID");
        }
    }

    const getDocumentoExternoByBidCodigoRespuesta = async(codigo:number): Promise<DocumentosExternos[]> => {
        try {
            return await $fetch<DocumentosExternos[]>(`${apiUrl}/getDocumentsByBidCodigoRespuesta?bidCodigo=${codigo}`, await getHeaders())
        } catch (error) {
            throw new Error("Error al consultar la bitacora externa segun ID");
        }
    }

    const getDocumentoElectronicoByCodigoAleatorio = async(numSumilla:string, codigoDocumento:string): Promise<DocumentosExternos[]> => {
        try {
            return await $fetch<DocumentosExternos[]>(`${apiUrl}/obtenerDocumentosElectronicosPorCodigoConsulta?numSumilla=${numSumilla}&codDocumento=${codigoDocumento}`, await getHeaders())
        } catch (error) {
            throw new Error("Error al consultar la bitacora externa segun codigo Consulta");
        }
    }

    const saveDocumentoExterno = async(documentoExterno:DocumentosExternos):Promise<DocumentosExternos>=>{
        try {
           return await $fetch<DocumentosExternos>(`${apiUrl}`,
            {
                method: 'POST',
                body:  documentoExterno,
                ...await getHeaders(),

            })
        } catch (error) {
            throw new Error("Error al guardar los documentos externos");
        }
    }

    const deleteDocumentoExterno = async(codigo: Number)=>{
        try {
            await $fetch(`${apiUrl}?codigo=${codigo}`,{
                method: 'DELETE',
                ...await getHeaders(),

            })
        } catch (error) {
            throw error;
        }
    }

    return {
        getAllDocumentosExternos,
        getDocumentoExternoById,
        saveDocumentoExterno,
        deleteDocumentoExterno,
        getDocumentoExternoByBidCodigo,
        getDocumentoExternoByBidCodigoRecibidos,
        getDocumentoExternoByBidCodigoRespuesta,
        getDocumentoElectronicoByCodigoAleatorio
    }
}
