import type {BitacoraExternos} from "~/models/BitacoraExternos.model";

export const useBitacoraExternaService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_BITACORA_EXTERNO}`
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

    const getAllBitacorasExternos = async(): Promise<BitacoraExternos[]> => {
        try {
            const resp = await $fetch<BitacoraExternos[]>(`${apiUrl}`, await getHeaders())
            return resp;
        } catch (error) {
            throw new Error("Error al consultar las bitacoras externas");
        }
    }

    const getAllBitacorasExternosBySede = async (sede: number): Promise<BitacoraExternos[]> => {
        try {
          const resp = await $fetch<BitacoraExternos[]>(`${apiUrl}/getDocumentosExternosBySede?sede=${sede}`, await getHeaders());
      
          // Ordenar el array para que los documentos en estado "EN TRÁMITE" (estado: 4) aparezcan primero
          return resp.sort((a, b) => {
            if (a.estado === 4) return -1;  // Si el estado es "EN TRÁMITE", se coloca primero
            if (b.estado === 4) return 1;
            return a.estado - b.estado;  // Mantener el orden original del resto
          });
        } catch (error) {
          throw new Error("Error al consultar las bitacoras externas");
        }
      };

    const getAllBitacorasExternosByPerCodigo= async(perCodigo:number): Promise<BitacoraExternos[]> => {
        try {
            const resp = await $fetch<BitacoraExternos[]>(`${apiUrl}/getDocumentosElectronicosByPerCodigo?perCodigoDestinatario=${perCodigo}`, await getHeaders())
            return resp;
        } catch (error) {
            throw new Error("Error al consultar las bitacoras externas");
        }
    }

    const getBitacorasById = async(codigo:number): Promise<BitacoraExternos> => {
        try {
            const resp = await $fetch<BitacoraExternos>(`${apiUrl}/findById?codigo=${codigo}`, await getHeaders())
            return resp;
        } catch (error) {
            throw new Error("Error al consultar la bitacora segun ID");
        }
    }
    
    const getBitacorasElectronicasBySumilla = async(numSumilla:string, codigo:string): Promise<BitacoraExternos> => {
        try {
            return await $fetch<BitacoraExternos>(`${apiUrl}/findBitacoraElectronicaBySumilla?numSumilla=${numSumilla}&codigo=${codigo}`, await getHeaders())
        } catch (error) {
            throw new Error("Error al consultar la bitacora segun ID");
        }
    }

    const saveBitacoraExterna = async(bitacoraExterna:BitacoraExternos):Promise<BitacoraExternos>=>{
        try {
           return await $fetch<BitacoraExternos>(`${apiUrl}`,
            {
                method: 'POST',
                body:  bitacoraExterna,
                ...await getHeaders(),
            })
        } catch (error) {
            throw new Error("Error al guardar las bitacora externa");
        }
    }

    const editBitacoraExterna = async(bitacoraExt:BitacoraExternos, codigo:number, accion:number, sede:number)=>{
        try {
            await $fetch(`${apiUrl}?codigo=${codigo}&accion=${accion}&sede=${sede}`,
            {
                method: 'PUT',
                body:  bitacoraExt,
                ...await getHeaders(),
            })
        } catch (error) {
            throw new Error("Error al editar la bitacora externa");
        }
    }
    const editBitacoraElectronica = async(bitacoraExt:BitacoraExternos)=>{
        try {
            await $fetch(`${apiUrl}/updateBitElectronica`,
            {
                method: 'PUT',
                body:  bitacoraExt,
                ...await getHeaders(),
            })
        } catch (error) {
            throw new Error("Error al editar la bitacora externa");
        }
    }

    const deleteBitacoraExternos = async(codigo: Number)=>{
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
        getAllBitacorasExternos,
        getAllBitacorasExternosBySede,
        getBitacorasById,
        saveBitacoraExterna,
        editBitacoraExterna,
        deleteBitacoraExternos,
        getAllBitacorasExternosByPerCodigo,
        editBitacoraElectronica,
        getBitacorasElectronicasBySumilla
    }
}
