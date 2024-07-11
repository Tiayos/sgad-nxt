import type {BitacoraExternos} from "~/models/BitacoraExternos.model";
import {useField, useForm} from 'vee-validate';
import * as yup from "yup";
import { required } from '@vee-validate/rules';
import {usePersonaService} from "~/composables/services/usePersonaService"; // Importa la regla required de VeeValidate

export const useBitacoraExternaComposable = () => {
    //*Service
    const {getUsrLogin} = usePersonaService();

    const bitacoraExterna = ref<BitacoraExternos>({} as BitacoraExternos)

    const { handleSubmit, resetForm } = useForm({
        validationSchema: yup.object({
            nombres_remitente: yup.string().required(),
            apellidos_remitente: yup.string().required(),
            asunto: yup.string().required(),
            correo_remitente: yup.string().required(),
            correo_destinatario: yup.string().required(),
            captchaValue: yup.boolean().required()
        }),
    });

    const {
        value: nombreRemitente,
        errorMessage: nombreRemitenteError,
        resetField: resetNombreRemitente,
    } = useField<string>("nombres_remitente", {
        required: true,
    })
    const {
        value: apellidoRemitente,
        errorMessage: apellidoRemitenteError,
        resetField: resetApellidoRemitente,
    } = useField<string>("apellidos_remitente", {
        required: true,
    })
    const {
        value: asunto,
        errorMessage: asuntoError,
        resetField: resetAsunto,
    } = useField<string>("asunto", {
        required: true,
    })
    const {
        value: correoRemitente,
        errorMessage: correoRemitenteError,
        resetField: resetCorreoRemitente,
    } = useField<string>("correo_remitente", {
        required: true,
    })
    const {
        value: correoDestinatario,
        errorMessage: correoDestinatarioError,
        resetField: resetCorreoDestinatario,
    } = useField<string>("correo_destinatario", {
        required: true,
    })
    const {
        value: captchaValue,
        errorMessage: captchaValueError,
    } = useField<boolean>("captchaValue", {
        required: true,
    })

    onMounted(() => {
        captchaValue.value = false
    })

    return {
        bitacoraExterna,
        nombreRemitente,
        nombreRemitenteError,
        resetNombreRemitente,
        apellidoRemitente,
        apellidoRemitenteError,
        resetApellidoRemitente,
        correoRemitente,
        correoRemitenteError,
        resetCorreoRemitente,
        correoDestinatario,
        correoDestinatarioError,
        resetCorreoDestinatario,
        asunto,
        asuntoError,
        resetAsunto,
        captchaValue,
        captchaValueError,
        handleSubmit,
        ///----Service
        getUsrLogin,
    }
}