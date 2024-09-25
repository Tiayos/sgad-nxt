import type {Bitacora} from "~/models/Bitacora.model";
import type {DocumentoBitacora} from "~/models/DocumentoBitacora.model";
import { useAuthService } from "./useAuthService";

export const useBitacoraService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_BITACORA}`
    const authService = useAuthService();


    const getBitacoras = async(): Promise<Bitacora[]> => {
        try {
            const resp = await $fetch<Bitacora[]>(`${apiUrl}`, await authService.getHeaders())
            return resp;
        } catch (error) {
            throw new Error("Error al consultar las bitacoras");
        }
    }

    const getBitacorasBySede = async(sede:number): Promise<Bitacora[]> => {
        try {
            const resp = await $fetch<Bitacora[]>(`${apiUrl}/getBitacorasBySede?sede=${sede}`, await authService.getHeaders())
            return resp;
        } catch (error) {
            throw new Error("Error al consultar las bitacoras");
        }
    }

    const getBitacorasByFechaAndEstado = async(fechaInicio:string, fechaFin:string, resPerCodigo:number): Promise<Bitacora[]> => {
        try {
            const resp = await $fetch<Bitacora[]>(`${apiUrl}/getBitacorasByFechasAndEstado?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}&resPerCodigo=${resPerCodigo}`, await authService.getHeaders())
            return resp;
        } catch (error) {
            throw new Error("Error al consultar las bitacoras");
        }
    }

    const getBitacoraByNumSumilla = async(numSumilla:string): Promise<Bitacora> => {
        try {
            return await $fetch<Bitacora>(`${apiUrl}/getBitacoraByNumSumilla?numSumilla=${numSumilla}`, await authService.getHeaders())
        } catch (error) {
            throw new Error("Error al consultar las bitacoras");
        }
    }

    const getDocumentosByBitCodigo = async(bitCodigo:number): Promise<DocumentoBitacora[]> => {
        try {
            return await $fetch<DocumentoBitacora[]>(`${apiUrl}/getDocumentosByBitCodigo?bitCodigo=${bitCodigo}`, await authService.getHeaders())
        } catch (error) {
            throw new Error("Error al consultar los documentos");
        }
    }

    const getBitacorasByPerCodigoDestinatario = async(perCodigoDestinatario:number): Promise<Bitacora[]> => {
        try {
            return await $fetch<Bitacora[]>(`${apiUrl}/getBitacorasByDestinatario?perCodigoDestinatario=${perCodigoDestinatario}`,await authService.getHeaders())
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
                ...await authService.getHeaders(),
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
                ...await authService.getHeaders(),
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
                ...await authService.getHeaders(),
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
                ...await authService.getHeaders(),
            })
        } catch (error) {
            throw new Error("Error al editar la bitacora");
        }
    }

    const deleteBitacora = async(codigo: Number)=>{
        try {
            await $fetch(`${apiUrl}?codigo=${codigo}`,{
                method: 'DELETE',
                ...await authService.getHeaders(),

            })
        } catch (error) {
            throw error;
        }
    }

    const deleteBitacoraByNumSumilla = async(sumCodigo: number)=>{
        try {
            await $fetch(`${apiUrl}/deleteBitacoraBySumCodigo?sumCodigo=${sumCodigo}`,{
                method: 'DELETE',
                ...await authService.getHeaders(),

            })
        } catch (error) {
            throw error;
        }
    }

    const deleteDocumentosByBitCodigo = async(bitCodigo: number)=>{
        try {
            await $fetch(`${apiUrl}/deleteDocumentos?bitCodigo=${bitCodigo}`,{
                method: 'DELETE',
                ...await authService.getHeaders(),

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
