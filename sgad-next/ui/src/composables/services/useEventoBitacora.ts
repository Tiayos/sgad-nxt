import { EventoBitacora } from "models/EventoBitacora.model"

export const useEventoBitacora = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_EVENTO}`
    
    const getEventoBitacoraService = async(bitCodigo:number): Promise<EventoBitacora> => {
        try {
            return await $fetch<EventoBitacora>(`${apiUrl}/getEventosCodBitacoraVigente?bitCodigo=${bitCodigo}`)
        } catch (error) {
            throw new Error("Error al consultar los eventos");
        }
    }

    const getAllEventosBitacoraByPerCodigo = async(perCodigo:number): Promise<EventoBitacora[]> => {
        try {
            return await $fetch<EventoBitacora[]>(`${apiUrl}/getEventosByPerCodigo?perCodigo=${perCodigo}`)
        } catch (error) {
            throw new Error("Error al consultar los eventos");
        }
    }

    return {
        getEventoBitacoraService,
        getAllEventosBitacoraByPerCodigo
    }
}
