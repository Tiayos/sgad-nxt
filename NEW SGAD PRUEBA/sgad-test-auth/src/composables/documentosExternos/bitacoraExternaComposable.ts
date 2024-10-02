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
import type { DocumentoBitacora } from "~/models/DocumentoBitacora.model";
import { useBitacoraService } from "../services/useBitacoraService";
import type { DocumentosExternos } from "~/models/DocumentosExternos.model";
import { useDocumentosExternosService } from "../services/useDocumentosExternos";

export const useBitacoraExternaComposable = () => {
    //*Service
    const {getUsrLogin} = usePersonaService();
    const {saveBitacoraExterna, editBitacoraExterna, getBitacorasById, getAllBitacorasExternosBySede, getBitacorasElectronicasBySumilla} = useBitacoraExternaService();
    const {saveDocumentoBitacora, saveBitacora} = useBitacoraService();
    const {saveDocumentoExterno, getAllDocumentosExternos, getDocumentoElectronicoByCodigoAleatorio, getDocumentoExternoByBidCodigoRecibidos} = useDocumentosExternosService();
    const {sendEmailUsuarioExterno} = useSendEmailService();
    const {saveSumilla,saveSumillaExterna, getSedeByEmail} = useSumillaService();

    const bitacoraExterna = ref<BitacoraExternos>({} as BitacoraExternos);
    const bitacora = ref<Bitacora>({} as Bitacora);
    const sumilla = ref<Sumilla>({} as Sumilla);
    const documentObj = ref<DocumentosExternos>({} as DocumentosExternos);
    const { data } = useAuth();

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
            correo_remitente: yup.string().required().email("El correo no es válido"),
            nombre_destinatario: yup.string().required(),
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
        documentObj,
        ///----Service
        getUsrLogin,
        saveBitacoraExterna,
        editBitacoraExterna,
        getBitacorasById,
        sendEmailUsuarioExterno,
        saveSumilla,
        saveSumillaExterna,
        saveDocumentoBitacora,
        saveBitacora,
        saveDocumentoExterno,
        getAllDocumentosExternos,
        getAllBitacorasExternosBySede,
        data,
        getSedeByEmail,
        getDocumentoElectronicoByCodigoAleatorio,
        getBitacorasElectronicasBySumilla,
        getDocumentoExternoByBidCodigoRecibidos
    }
}