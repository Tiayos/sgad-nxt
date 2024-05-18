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

    const getSumillaByNumeroSumilla = async(numeroSumilla:string): Promise<Sumilla|null> => {
        try {
            return await $fetch<Sumilla>(`${apiUrl}/findSumillaByNumSumilla?numeroSumilla=${numeroSumilla}`)
        } catch (error:any) {
            toast.add({
                severity: "error",
                detail: `No se encontró la sumilla`,
                life: 2000,
              });
            return null;
        }
    }

    const saveSumilla = async(sumilla:Sumilla, email:string): Promise<Sumilla>=>{
        try {
           return await $fetch<Sumilla>(`${apiUrl}?email=${email}`,
            {
                method: 'POST',
                body:   sumilla,
            })
        } catch (error:any) {
            return {} as Sumilla;
        }
    }

    const editSumilla = async(sumilla:Sumilla, codigo:number)=>{
        try {
            await $fetch(`${apiUrl}?codigo=${codigo}`,
            {
                method: 'PUT',
                body:  sumilla,
            })
        } catch (error) {
            throw new Error("Error al editar la bitacora");
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
        getSumillaByNumeroSumilla,
        saveSumilla,
        editSumilla,
        deleteSumilla
    }
}
