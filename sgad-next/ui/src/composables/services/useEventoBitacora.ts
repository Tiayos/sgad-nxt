import { Estado } from "models/Estado.model"
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
    
    const getAllEstados= async(): Promise<Estado[]> => {
        try {
            const estados = await $fetch<Estado[]>(`${apiUrl}/getAllEstados`);
            // Filtrar los estados por los códigos especificados
            const filteredEstados = estados.filter(estado => [3, 4, 7].includes(estado.codigo));
            // Modificar la descripción del estado con código 7
            const modifiedEstados = filteredEstados.map(estado => {
                if (estado.codigo === 7) {
                    return {
                        ...estado,
                        estado_descripcion: 'Reasignar'
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
             })
         } catch (error:any) {
             return {} as EventoBitacora;
         }
    }

    const deleteEventoBitacora = async(bitCodigo: Number)=>{
        try {
            await $fetch(`${apiUrl}?bitCodigo=${bitCodigo}`,{
                method: 'DELETE'
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
