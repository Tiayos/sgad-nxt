import type {BitacoraExternos} from "~/models/BitacoraExternos.model";

export const useBitacoraExternaService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_BITACORA_EXTERNO}`
    
    const getAllBitacorasExternos = async(): Promise<BitacoraExternos[]> => {
        try {
            const resp = await $fetch<BitacoraExternos[]>(`${apiUrl}`)
            return resp;
        } catch (error) {
            throw new Error("Error al consultar las bitacoras externas");
        }
    }

    const getBitacorasById = async(codigo:number): Promise<BitacoraExternos> => {
        try {
            const resp = await $fetch<BitacoraExternos>(`${apiUrl}/findById?codigo=${codigo}`)
            return resp;
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

    const editBitacoraExterna = async(bitacora:BitacoraExternos, codigo:number)=>{
        // try {
        //     await $fetch(`${apiUrl}?codigo=${codigo}`,
        //     {
        //         method: 'PUT',
        //         body:  bitacora,
        //     })
        // } catch (error) {
        //     throw new Error("Error al editar la bitacora");
        // }
    }

    const deleteBitacoraExternos = async(codigo: Number)=>{
        try {
            await $fetch(`${apiUrl}?codigo=${codigo}`,{
                method: 'DELETE'
            })
        } catch (error) {
            throw error;
        }
    }

    return {
        getAllBitacorasExternos,
        getBitacorasById,
        saveBitacoraExterna,
        editBitacoraExterna,
        deleteBitacoraExternos,
    }
}
