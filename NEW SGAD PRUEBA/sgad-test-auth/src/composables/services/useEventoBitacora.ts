import type {EventoBitacora} from "~/models/EventoBitacora.model";
import type {Estado} from "~/models/Estado.model";
import { useAuthService } from "./useAuthService";

export const useEventoBitacora = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_EVENTO}`
    const authService = useAuthService();

    const getEventoBitacoraService = async(bitCodigo:number): Promise<EventoBitacora> => {
        try {
            return await $fetch<EventoBitacora>(`${apiUrl}/getEventosCodBitacoraVigente?bitCodigo=${bitCodigo}`, await authService.getHeaders())
        } catch (error) {
            throw new Error("Error al consultar los eventos");
        }
    }

    const getAllEventosVigentesByPerCodigo = async(perCodigo:number): Promise<EventoBitacora[]> => {
        try {
            return await $fetch<EventoBitacora[]>(`${apiUrl}/getEventosByPerCodigo?perCodigo=${perCodigo}`, await authService.getHeaders())
        } catch (error) {
            throw new Error("Error al consultar los eventos");
        }
    }

    const getAllEventosByBitCodigo = async (bitCodigo: number): Promise<EventoBitacora[]> => {
        try {
            const resp = await $fetch<EventoBitacora[]>(`${apiUrl}/getAllEventos?bitCodigo=${bitCodigo}`, await authService.getHeaders());
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
            const estados = await $fetch<Estado[]>(`${apiUrl}/getAllEstados`, await authService.getHeaders());
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
                 ...await authService.getHeaders(),

             })
         } catch (error:any) {
             return {} as EventoBitacora;
         }
    }

    const deleteEventoBitacora = async(bitCodigo: Number)=>{
        try {
            await $fetch(`${apiUrl}?bitCodigo=${bitCodigo}`,{
                method: 'DELETE',
                ...await authService.getHeaders(),

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
