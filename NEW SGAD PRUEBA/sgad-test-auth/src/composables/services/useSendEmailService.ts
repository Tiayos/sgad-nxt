import type {EventoBitacora} from "~/models/EventoBitacora.model";
import type {BitacoraExternos} from "~/models/BitacoraExternos.model";

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

    const sendEmailUsuarioExterno = async(bitacoraExterno:BitacoraExternos) => {
        try {
            return await $fetch(`${apiUrl}/sendCodigoUsuarioExterno`,
                {
                    method: 'POST',
                    body:   bitacoraExterno,
                })
        } catch (error:any) {
            return {} as BitacoraExternos;
        }
    }

    return {
        sendEmail,
        sendEmailUsuarioExterno
    }
}
