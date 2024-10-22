import type {DocumentosExternos} from "~/models/DocumentosExternos.model";
import { useAuthService } from "./useAuthService";

export const useDocumentosExternosService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_DOCUMENTOS_EXTERNOS}`
    const authService = useAuthService();

    const getAllDocumentosExternos = async(): Promise<DocumentosExternos[]> => {
        try {
            return await $fetch<DocumentosExternos[]>(`${apiUrl}`, await authService.getHeaders())
        } catch (error) {
            throw new Error("Error al consultar los documentos externos");
        }
    }

    const getDocumentoExternoById = async(codigo:number): Promise<DocumentosExternos> => {
        try {
            return await $fetch<DocumentosExternos>(`${apiUrl}/getDocumentById?codigo=${codigo}`, await authService.getHeaders())
        } catch (error) {
            throw new Error("Error al consultar la bitacora externa segun ID");
        }
    }

    const getDocumentoExternoByBidCodigo = async(codigo:number): Promise<DocumentosExternos[]> => {
        try {
            return await $fetch<DocumentosExternos[]>(`${apiUrl}/getDocumentsByBidCodigo?bidCodigo=${codigo}`, await authService.getHeaders())
        } catch (error) {
            throw new Error("Error al consultar la bitacora externa segun ID");
        }
    }

    const getDocumentoExternoByBidCodigoRecibidos = async(codigo:number): Promise<DocumentosExternos[]> => {
        try {
            return await $fetch<DocumentosExternos[]>(`${apiUrl}/getDocumentsByBidCodigoRecibidos?bidCodigo=${codigo}`, await authService.getHeaders())
        } catch (error) {
            throw new Error("Error al consultar la bitacora externa segun ID");
        }
    }

    const getDocumentoExternoByBidCodigoRespuesta = async(codigo:number): Promise<DocumentosExternos[]> => {
        try {
            return await $fetch<DocumentosExternos[]>(`${apiUrl}/getDocumentsByBidCodigoRespuesta?bidCodigo=${codigo}`, await authService.getHeaders())
        } catch (error) {
            throw new Error("Error al consultar la bitacora externa segun ID");
        }
    }

    const getDocumentoElectronicoByCodigoAleatorio = async(numSumilla:string, codigoDocumento:string): Promise<DocumentosExternos[]> => {
        try {
            return await $fetch<DocumentosExternos[]>(`${apiUrl}/obtenerDocumentosElectronicosPorCodigoConsulta?numSumilla=${numSumilla}&codDocumento=${codigoDocumento}`, await authService.getHeaders())
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
            })
        } catch (error) {
            throw new Error("Error al guardar los documentos externos");
        }
    }

    const deleteDocumentoExterno = async(codigo: Number)=>{
        try {
            await $fetch(`${apiUrl}?codigo=${codigo}`,{
                method: 'DELETE',
                ...await authService.getHeaders(),

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
