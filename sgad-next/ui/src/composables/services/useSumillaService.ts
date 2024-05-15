import { Sumilla } from "models/Sumilla.model"
import { useToast } from "primevue/usetoast";

export const useSumillaService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_SUMILLA}`
    const toast = useToast();

    const getSumillas = async(): Promise<Sumilla[]> => {
        try {
            const resp = await $fetch<Sumilla[]>(`${apiUrl}`)
            return resp;
        } catch (error) {
            throw new Error("Error al consultar las sumillas");
        }
    }

    const getSumillaById = async(codigo:number): Promise<Sumilla|null> => {
        try {
            const resp = await $fetch<Sumilla>(`${apiUrl}/findSumilla?codigo=${codigo}`)
            return resp;
        } catch (error:any) {
            toast.add({
                severity: "error",
                detail: `No se encontró la sumilla`,
                life: 2000,
              });
            return null;
        }
    }

    const saveSumilla = async(sumilla:Sumilla)=>{
        try {
            await $fetch(`${apiUrl}`,
            {
                method: 'POST',
                body:  sumilla,
            })
        } catch (error) {
            
        }
    }

    const deleteSumilla = async(codigo: Number)=>{
        try {
            await $fetch(`${apiUrl}?codigo=${codigo}`,{
                method: 'DELETE'
            })
        } catch (error) {
            throw error;
        }
    }

    return {
        getSumillas,
        getSumillaById,
        saveSumilla,
        deleteSumilla
    }
}
