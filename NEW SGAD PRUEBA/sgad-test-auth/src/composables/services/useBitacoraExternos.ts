import type {BitacoraExternos} from "~/models/BitacoraExternos.model";
import { useAuthService } from "./useAuthService";

export const useBitacoraExternaService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_BITACORA_EXTERNO}`
    const authService = useAuthService();

    const getAllBitacorasExternos = async(): Promise<BitacoraExternos[]> => {
        try {
            const resp = await $fetch<BitacoraExternos[]>(`${apiUrl}`, await authService.getHeaders())
            return resp;
        } catch (error) {
            throw new Error("Error al consultar las bitacoras externas");
        }
    }

    const getAllBitacorasExternosBySede = async (sede: number): Promise<BitacoraExternos[]> => {
        try {
          const resp = await $fetch<BitacoraExternos[]>(`${apiUrl}/getDocumentosExternosBySede?sede=${sede}`, await authService.getHeaders());
      
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
            const resp = await $fetch<BitacoraExternos[]>(`${apiUrl}/getDocumentosElectronicosByPerCodigo?perCodigoDestinatario=${perCodigo}`, await authService.getHeaders())
            return resp;
        } catch (error) {
            throw new Error("Error al consultar las bitacoras externas");
        }
    }

    const getBitacorasById = async(codigo:number): Promise<BitacoraExternos> => {
        try {
            const resp = await $fetch<BitacoraExternos>(`${apiUrl}/findById?codigo=${codigo}`, await authService.getHeaders())
            return resp;
        } catch (error) {
            throw new Error("Error al consultar la bitacora segun ID");
        }
    }
    
    const getBitacorasElectronicasBySumilla = async(numSumilla:string, codigo:string): Promise<BitacoraExternos> => {
        try {
            return await $fetch<BitacoraExternos>(`${apiUrl}/findBitacoraElectronicaBySumilla?numSumilla=${numSumilla}&codigo=${codigo}`, await authService.getHeaders())
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
                ...await authService.getHeaders(),
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
                ...await authService.getHeaders(),
            })
        } catch (error) {
            throw new Error("Error al editar la bitacora externa");
        }
    }

    const deleteBitacoraExternos = async(codigo: Number)=>{
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
