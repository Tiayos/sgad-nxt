import type {Persona} from "~/models/Sumilla.model";
import { useAuthService } from "./useAuthService";

export const usePersonaService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_PERSONA}`
    const authService = useAuthService();

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
            const resp = await $fetch<Persona[]>(`${apiUrl}`, await authService.getHeaders())
            return resp.map(a=>({...a, nombreCompleto: `${a.per_nombres} ${a.per_apellidos}`}))
        } catch (error) {
            throw new Error("Error al consultar las sumillas");
        }
    }

    const getUsersByFilterName = async(name:string): Promise<Persona[]> => {
        try {
            const resp = await $fetch<Persona[]>(`${apiUrl}/getFilterName?name=${name}`, await authService.getHeaders())
            return resp.map(a=>({...a, nombreCompleto: `${a.per_nombres} ${a.per_apellidos}`}))
        } catch (error) {
            throw new Error("Error al consultar las sumillas");
        }
    }

    const getUsersAmbitosGestionDocumental = async(): Promise<Persona[]> => {
        try {
            const resp = await $fetch<Persona[]>(`${apiUrl}/findPersonasGestionDocumental`, await authService.getHeaders())
            return resp.map(a=>({...a, nombreCompleto: `${a.per_nombres} ${a.per_apellidos}`}))
        } catch (error) {
            throw new Error("Error al consultar las sumillas");
        }
    }

    return {
        getUsrLogin,
        getUsers,
        getUsersByFilterName,
        getUsersAmbitosGestionDocumental
    }
}
