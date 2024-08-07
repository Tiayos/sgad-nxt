import type {BitacoraExternos} from "~/models/BitacoraExternos.model";
import {useField, useForm} from 'vee-validate';
import * as yup from "yup";
import { required } from '@vee-validate/rules';
import {usePersonaService} from "~/composables/services/usePersonaService";
import {useBitacoraExternaService} from "~/composables/services/useBitacoraExternos";
import {useSendEmailService} from "~/composables/services/useSendEmailService"; // Importa la regla required de VeeValidate
import type { Bitacora } from "~/models/Bitacora.model";
import type { Sumilla } from "~/models/Sumilla.model";
import { useSumillaService } from "../services/useSumillaService";

export const useBitacoraExternaComposable = () => {
    //*Service
    const {getUsrLogin} = usePersonaService();
    const {saveBitacoraExterna, editBitacoraExterna, getBitacorasById} = useBitacoraExternaService();
    const {sendEmailUsuarioExterno} = useSendEmailService();
    const {saveSumilla,saveSumillaExterna} = useSumillaService();

    const bitacoraExterna = ref<BitacoraExternos>({} as BitacoraExternos);
    const bitacora = ref<Bitacora>({} as Bitacora);
    const sumilla = ref<Sumilla>({} as Sumilla);
    const sedeList = ref(
    [   { label: 'CUENCA', value: 2 },
        { label: 'GUAYAQUIL', value: 4 },
        { label: 'QUITO', value: 3 }
    ]);



    const { handleSubmit, resetForm } = useForm({
        validationSchema: yup.object({
            nombres_remitente: yup.string().required(),
            apellidos_remitente: yup.string().required(),
            nombre_organizacion: yup.string().required(),
            asunto: yup.string().required(),
            correo_remitente: yup.string().required(),
            nombre_destinatario: yup.string().required(),
            apellido_destinatario: yup.string().required(),
            captchaValue: yup.boolean().required(),
            files: yup.array().required(),
            sede: yup.number().required()
        }),
    });

    const {
        value: sede,
        errorMessage: sedeError,
        resetField: resetSede,
    } = useField<number>("sede", {
        required: true,
    })

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
        value: nombreOrganizacion,
        errorMessage: nombreOrganizacionError,
        resetField: resetNombreOrganizacion,
    } = useField<string>("nombre_organizacion", {
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
        value: nombreDestinatario,
        errorMessage: nombreDestinatarioError,
        resetField: resetNombreDestinatario,
    } = useField<string>("nombre_destinatario", {
        required: true,
    })

    const {
        value: apellidoDestinatario,
        errorMessage: apellidoDestinatarioError,
        resetField: resetApellidoDestinatario,
    } = useField<string>("apellido_destinatario", {
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
        value: captchaValue,
        errorMessage: captchaValueError,
    } = useField<boolean>("captchaValue", {
        required: true,
    })
    const {
        value: files,
        errorMessage: filesError,
        resetField: resetFiles
    } = useField<File[]>("files", {
        required: true,
    })

    onMounted(() => {
        captchaValue.value = false
    })

    return {
        bitacoraExterna,
        sumilla,
        bitacora,
        nombreRemitente,
        nombreRemitenteError,
        resetNombreRemitente,
        apellidoRemitente,
        apellidoRemitenteError,
        resetApellidoRemitente,
        correoRemitente,
        correoRemitenteError,
        resetCorreoRemitente,
        asunto,
        asuntoError,
        resetAsunto,
        nombreOrganizacion,
        nombreOrganizacionError,
        resetNombreOrganizacion,
        nombreDestinatario,
        nombreDestinatarioError,
        resetNombreDestinatario,
        apellidoDestinatario,
        apellidoDestinatarioError,
        resetApellidoDestinatario,
        sede,
        sedeError,
        resetSede,
        sedeList,
        captchaValue,
        captchaValueError,
        files,
        filesError,
        resetFiles,
        handleSubmit,
        ///----Service
        getUsrLogin,
        saveBitacoraExterna,
        editBitacoraExterna,
        getBitacorasById,
        sendEmailUsuarioExterno,
        saveSumilla,
        saveSumillaExterna
    }
}