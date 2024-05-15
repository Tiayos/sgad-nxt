import { Persona } from "models/Sumilla.model"

export const usePersonaService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_PERSONA}`
    
    const getUsrLogin = async(email:String): Promise<Persona> => {
        try {
            const resp = await $fetch<Persona>(`${apiUrl}/findEmail?email=${email}`)
            return resp;
        } catch (error) {
            throw new Error("Error al consultar las sumillas");
        }
    }

    const getUsers = async(): Promise<Persona[]> => {
        try {
            const resp = await $fetch<Persona[]>(`${apiUrl}`)
            return resp.map(a=>({...a, nombreCompleto: `${a.per_nombres} ${a.per_apellidos}`}))
        } catch (error) {
            throw new Error("Error al consultar las sumillas");
        }
    }

    return {
        getUsrLogin,
        getUsers
    }
}
