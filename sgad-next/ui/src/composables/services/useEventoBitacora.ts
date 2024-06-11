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

    const getAllEventosVigentesByPerCodigo = async(perCodigo:number): Promise<EventoBitacora[]> => {
        try {
            return await $fetch<EventoBitacora[]>(`${apiUrl}/getEventosByPerCodigo?perCodigo=${perCodigo}`)
        } catch (error) {
            throw new Error("Error al consultar los eventos");
        }
    }

    const getAllEventosByBitCodigo= async(bitCodigo:number): Promise<EventoBitacora[]> => {
        try {
            return await $fetch<EventoBitacora[]>(`${apiUrl}/getAllEventos?bitCodigo=${bitCodigo}`)
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
             })
         } catch (error:any) {
             return {} as EventoBitacora;
         }
    }

    return {
        getEventoBitacoraService,
        getAllEventosByBitCodigo,
        getAllEventosVigentesByPerCodigo,
        saveEventoBitacora
    }
}
