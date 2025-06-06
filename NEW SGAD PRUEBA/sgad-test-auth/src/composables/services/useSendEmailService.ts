import type {EventoBitacora} from "~/models/EventoBitacora.model";
import type {BitacoraExternos} from "~/models/BitacoraExternos.model";
import type { Sumilla } from "~/models/Sumilla.model";
import { useAuthService } from "./useAuthService";
import type { Bitacora } from "~/models/Bitacora.model";
import { EventoBitacora } from '../../models/EventoBitacora.model';

export const useSendEmailService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_EMAIL}`
    const authService = useAuthService();

    const sendEmail = async(eventoBitacoraDTO:EventoBitacora) => {
        try {
            return await $fetch(`${apiUrl}/enviarCorreo`,
             {
                 method: 'POST',
                 body:   eventoBitacoraDTO,
                 ...await authService.getHeaders(),

             })
         } catch (error:any) {
             return {} as EventoBitacora;
         }
    }

    const sendEmailUsuarioExterno = async(bitacoraExterno:BitacoraExternos, numeroSumilla:String) => {
        try {
            return await $fetch(`${apiUrl}/sendCodigoUsuarioExterno?numeroSumilla=${numeroSumilla}`,
                {
                    method: 'POST',
                    body:   bitacoraExterno,

                })
        } catch (error:any) {
            return {} as BitacoraExternos;
        }
    }

    const sendEmailReasignado = async(bitacoraExterno:BitacoraExternos, sede:number) => {
        try {
            return await $fetch(`${apiUrl}/sendEmailReasignado?sede=${sede}`,
                {
                    method: 'POST',
                    body:   bitacoraExterno,
                    ...await authService.getHeaders(),
                })
        } catch (error:any) {
            return {} as BitacoraExternos;
        }
    }

    const sendEmailDestinatario = async(bitacoraExterno:BitacoraExternos) => {
        try {
            return await $fetch(`${apiUrl}/sendEmailDestinatario`,
                {
                    method: 'POST',
                    body:   bitacoraExterno,
                    ...await authService.getHeaders(),

                })
        } catch (error:any) {
            return {} as BitacoraExternos;
        }
    }

    const sendEmailSolDocumentacionFisica = async(bitacoraExterno:BitacoraExternos) => {
        try {
            return await $fetch(`${apiUrl}/sendEmailDocumentacionFisica`,
                {
                    method: 'POST',
                    body:   bitacoraExterno,
                    ...await authService.getHeaders(),

                })
        } catch (error:any) {
            return {} as BitacoraExternos;
        }
    }

    const sendEmailRespuestaElectronicaRemitente = async(bitacoraExterno:BitacoraExternos) => {
        try {
            return await $fetch(`${apiUrl}/sendEmailRemitenteRespuesta`,
                {
                    method: 'POST',
                    body:   bitacoraExterno,
                    ...await authService.getHeaders(),

                })
        } catch (error:any) {
            return {} as BitacoraExternos;
        }
    }


    const sendEmailDocFisicaBitacora = async(bitacora:Bitacora) => {
        try {
            return await $fetch(`${apiUrl}/sendEmailDocFisicaDestinatario`,
                {
                    method: 'POST',
                    body:   bitacora,
                    ...await authService.getHeaders(),

                })
        } catch (error:any) {
            return {} as BitacoraExternos;
        }
    }

    
    const enviarMailDocumentacionFisicaReasignada = async(eventoBitacora:EventoBitacora) => {
        try {
            return await $fetch(`${apiUrl}/sendEmailDocReasignado`,
                {
                    method: 'POST',
                    body:   eventoBitacora,
                    ...await authService.getHeaders(),

                })
        } catch (error:any) {
            return {} as BitacoraExternos;
        }
    }


    return {
        sendEmail,
        sendEmailUsuarioExterno,
        sendEmailReasignado,
        sendEmailDestinatario,
        sendEmailSolDocumentacionFisica,
        sendEmailRespuestaElectronicaRemitente,
        sendEmailDocFisicaBitacora,
        enviarMailDocumentacionFisicaReasignada
    }
}
