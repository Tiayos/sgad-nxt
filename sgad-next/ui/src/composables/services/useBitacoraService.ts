import { Bitacora } from "models/Bitacora.model"

export const useBitacoraService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_BITACORA}`
    
    const getBitacoras = async(): Promise<Bitacora[]> => {
        try {
            const resp = await $fetch<Bitacora[]>(`${apiUrl}`)
            return resp;
        } catch (error) {
            throw new Error("Error al consultar las bitacoras");
        }
    }

    const saveBitacora = async(bitacora:Bitacora)=>{
        try {
            await $fetch(`${apiUrl}`,
            {
                method: 'POST',
                body:  bitacora,
            })
        } catch (error) {
            throw new Error("Error al guardar las bitacoras");
        }
    }

    const editBitacora = async(bitacora:Bitacora, codigo:number)=>{
        try {
            await $fetch(`${apiUrl}?codigo=${codigo}`,
            {
                method: 'PUT',
                body:  bitacora,
            })
        } catch (error) {
            throw new Error("Error al editar la bitacora");
        }
    }

    const deleteBitacora = async(codigo: Number)=>{
        try {
            await $fetch(`${apiUrl}?codigo=${codigo}`,{
                method: 'DELETE'
            })
        } catch (error) {
            throw error;
        }
    }

    return {
        getBitacoras,
        saveBitacora,
        editBitacora,
        deleteBitacora
    }
}
