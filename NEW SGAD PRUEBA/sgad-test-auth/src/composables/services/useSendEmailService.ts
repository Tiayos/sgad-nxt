import type {EventoBitacora} from "~/models/EventoBitacora.model";
import type {BitacoraExternos} from "~/models/BitacoraExternos.model";
import type { Sumilla } from "~/models/Sumilla.model";

export const useSendEmailService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_EMAIL}`
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

    const sendEmail = async(eventoBitacoraDTO:EventoBitacora) => {
        try {
            return await $fetch(`${apiUrl}/enviarCorreo`,
             {
                 method: 'POST',
                 body:   eventoBitacoraDTO,
                 ...await getHeaders(),

             })
         } catch (error:any) {
             return {} as EventoBitacora;
         }
    }

    const sendEmailUsuarioExterno = async(bitacoraExterno:BitacoraExternos, numeroSumilla:String) => {
        try {
            return await $fetch(`${apiUrl}/sendCodigoUsuarioExterno?numeroSumilla=${numeroSumilla}`,
                {
                    method: 'POST',
                    body:   bitacoraExterno,
                    ...await getHeaders(),

                })
        } catch (error:any) {
            return {} as BitacoraExternos;
        }
    }

    const sendEmailReasignado = async(bitacoraExterno:BitacoraExternos, sede:number) => {
        try {
            return await $fetch(`${apiUrl}/sendEmailReasignado?sede=${sede}`,
                {
                    method: 'POST',
                    body:   bitacoraExterno
                })
        } catch (error:any) {
            return {} as BitacoraExternos;
        }
    }

    const sendEmailDestinatario = async(bitacoraExterno:BitacoraExternos) => {
        try {
            return await $fetch(`${apiUrl}/sendEmailDestinatario`,
                {
                    method: 'POST',
                    body:   bitacoraExterno
                })
        } catch (error:any) {
            return {} as BitacoraExternos;
        }
    }

    const sendEmailSolDocumentacionFisica = async(bitacoraExterno:BitacoraExternos) => {
        try {
            return await $fetch(`${apiUrl}/sendEmailDocumentacionFisica`,
                {
                    method: 'POST',
                    body:   bitacoraExterno
                })
        } catch (error:any) {
            return {} as BitacoraExternos;
        }
    }

    const sendEmailRespuestaElectronicaRemitente = async(bitacoraExterno:BitacoraExternos) => {
        try {
            return await $fetch(`${apiUrl}/sendEmailRemitenteRespuesta`,
                {
                    method: 'POST',
                    body:   bitacoraExterno
                })
        } catch (error:any) {
            return {} as BitacoraExternos;
        }
    }

    return {
        sendEmail,
        sendEmailUsuarioExterno,
        sendEmailReasignado,
        sendEmailDestinatario,
        sendEmailSolDocumentacionFisica,
        sendEmailRespuestaElectronicaRemitente
    }
}
