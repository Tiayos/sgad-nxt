import { EventoBitacora } from "models/EventoBitacora.model"

export const useSendEmailService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_EMAIL}`
    
    const sendEmail = async(eventoBitacoraDTO:EventoBitacora) => {
        try {
            return await $fetch(`${apiUrl}/enviarCorreo`,
             {
                 method: 'POST',
                 body:   eventoBitacoraDTO,
             })
         } catch (error:any) {
             return {} as EventoBitacora;
         }
    }

    return {
        sendEmail
    }
}
