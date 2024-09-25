
import { useAuthService } from "./useAuthService";

export const useTransferenciaDocumentalService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_TRANSFERENCIA}`
    const authService = useAuthService();

    const saveTransferencia = async(fechaInicio:string, fechaFin:string, perCodigoDestinatarioGW:number, perCodigoResponsable: number)=>{
        try {
            await $fetch(`${apiUrl}?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}&perCodigoDestinatarioGW=${perCodigoDestinatarioGW}&perCodigoResponsable=${perCodigoResponsable}`,
            {
                method: 'POST',
                ...await authService.getHeaders(),
            })
        } catch (error) {
            throw new Error("Error al guardar la transferencia");
        }
    }

    return {
        saveTransferencia,
    }
}
