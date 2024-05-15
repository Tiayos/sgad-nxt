import { Cliente } from "models/personas/cliente.model";

export const usePersonasService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SCJ_PERSONA}`
    
    const getClientByIdentificacion = async (cedula: string) => {
        const token = localStorage.getItem('tokenBackend');
        try {
            const resp = await $fetch<Cliente>(`${apiUrl}/identificacion/${cedula}`,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                })
            return resp
        } catch (error) {
            // Aquí puedes mostrar un mensaje personalizado en lugar del error
            const errorMessage = 'El cliente es nuevo: ' + cedula;
            return errorMessage;
        }
    }

    return {
        getClientByIdentificacion,

    }
}
