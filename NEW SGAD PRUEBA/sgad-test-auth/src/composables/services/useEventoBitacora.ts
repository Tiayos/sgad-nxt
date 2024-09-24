import type {EventoBitacora} from "~/models/EventoBitacora.model";
import type {Estado} from "~/models/Estado.model";

export const useEventoBitacora = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_EVENTO}`
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

    const getEventoBitacoraService = async(bitCodigo:number): Promise<EventoBitacora> => {
        try {
            return await $fetch<EventoBitacora>(`${apiUrl}/getEventosCodBitacoraVigente?bitCodigo=${bitCodigo}`, await getHeaders())
        } catch (error) {
            throw new Error("Error al consultar los eventos");
        }
    }

    const getAllEventosVigentesByPerCodigo = async(perCodigo:number): Promise<EventoBitacora[]> => {
        try {
            return await $fetch<EventoBitacora[]>(`${apiUrl}/getEventosByPerCodigo?perCodigo=${perCodigo}`, await getHeaders())
        } catch (error) {
            throw new Error("Error al consultar los eventos");
        }
    }

    const getAllEventosByBitCodigo = async (bitCodigo: number): Promise<EventoBitacora[]> => {
        try {
            const resp = await $fetch<EventoBitacora[]>(`${apiUrl}/getAllEventos?bitCodigo=${bitCodigo}`, await getHeaders());
            console.log(resp);
            const updatedResp = resp.map(evento => {
                if (evento.estado.codigo === 7) {
                    evento.estado.estado_descripcion = 'Reasignado';
                }
                return evento;
            });
    
            return updatedResp;
        } catch (error) {
            throw new Error("Error al consultar los eventos");
        }
    };
    
    const getAllEstados= async(): Promise<Estado[]> => {
        try {
            const estados = await $fetch<Estado[]>(`${apiUrl}/getAllEstados`, await getHeaders());
            // Filtrar los estados por los códigos especificados
            const filteredEstados = estados.filter(estado => [3, 7].includes(estado.codigo));
            // Modificar la descripción del estado con código 7
            const modifiedEstados = filteredEstados.map(estado => {
                if (estado.codigo === 7) {
                    return {
                        ...estado,
                        estado_descripcion: 'Reasignar para trámite'
                    };
                }
                if (estado.codigo === 3) {
                    return {
                        ...estado,
                        estado_descripcion: 'Solicitar documentación fisica'
                    };
                }
                return estado;
            });
            return modifiedEstados     
        } catch (error) {
            throw new Error("Error al consultar los eventos");
        }
    }

    const saveEventoBitacora = async(evento:EventoBitacora): Promise<EventoBitacora> => {
        try {
            return await $fetch<EventoBitacora>(`${apiUrl}`,
             {
                 method: 'POST',
                 body:   evento,
                 ...await getHeaders(),

             })
         } catch (error:any) {
             return {} as EventoBitacora;
         }
    }

    const deleteEventoBitacora = async(bitCodigo: Number)=>{
        try {
            await $fetch(`${apiUrl}?bitCodigo=${bitCodigo}`,{
                method: 'DELETE',
                ...await getHeaders(),

            })
        } catch (error) {
            throw error;
        }
    }

    return {
        getEventoBitacoraService,
        getAllEventosByBitCodigo,
        getAllEstados,
        getAllEventosVigentesByPerCodigo,
        saveEventoBitacora,
        deleteEventoBitacora
    }
}
