import {useArchivosStore} from "~/composables/store/useArchivosStore";
import type {DocumentoBitacora} from "~/models/DocumentoBitacora.model";
import {usePersonaService} from "~/composables/services/usePersonaService";
import {useBitacoraService} from "~/composables/services/useBitacoraService";
import {useSendEmailService} from "~/composables/services/useSendEmailService";
import {useEventoBitacora} from "~/composables/services/useEventoBitacora";
import type {Persona} from "~/models/Sumilla.model";
import type { EventoBitacora } from "~/models/EventoBitacora.model";
import type { BitacoraExternos } from "~/models/BitacoraExternos.model";
import { useBitacoraExternaService } from "../services/useBitacoraExternos";
import { useSumillaService } from "../services/useSumillaService";
import type { SedeProjection } from "~/models/projection/SedeProjection.model";
import { useDocumentosExternosService } from "../services/useDocumentosExternos";

export const useBitacorasDestinatariosComposable = () =>{
//* store
    const useStore = useArchivosStore();
    const {eventosBitacorasList} = storeToRefs(useStore);
    const documentosBitacoraList = ref<DocumentoBitacora[]>([]);

    //*Services
    const {getUsrLogin,getUsers} = usePersonaService();
    const {getAllEventosVigentesByPerCodigo, getAllEventosByBitCodigo,
        getAllEstados, saveEventoBitacora, getEventoBitacoraService} = useEventoBitacora();
    const {getDocumentosByBitCodigo} = useBitacoraService();
    const {getAllBitacorasExternosBySede, getAllBitacorasExternosByPerCodigo, editBitacoraExterna, editBitacoraElectronica} = useBitacoraExternaService();
    const { getSedeByEmail} = useSumillaService();
    const { getDocumentoExternoByBidCodigo, saveDocumentoExterno, getDocumentoExternoByBidCodigoRecibidos, getDocumentoExternoByBidCodigoRespuesta} = useDocumentosExternosService();
    const {sendEmail, sendEmailSolDocumentacionFisica, sendEmailRespuestaElectronicaRemitente, enviarMailDocumentacionFisicaReasignada} = useSendEmailService();

    //*Auth
    const { data } = useAuth();

    //*Session storage
    const { data: userLogin } = useSessionStorage<Persona>("userLogin");
    const bitacorasExternasList = ref<BitacoraExternos[]>([]);
    const sede = ref<SedeProjection>({} as SedeProjection);
    onMounted(async() => {
        await findBitacorasDestinatarios();
        await findBitacorasElectronicas();
    })

    const findBitacorasDestinatarios = async() =>{
        userLogin.value = await getUsrLogin(data.value?.user?.email!);
        eventosBitacorasList.value = await getAllEventosVigentesByPerCodigo(userLogin.value.codigo);
    }

    const findBitacorasElectronicas = async() =>{
        sede.value = await getSedeByEmail(data.value?.user?.email!);
        bitacorasExternasList.value = await getAllBitacorasExternosByPerCodigo(userLogin.value.codigo);
    }

    return {
        eventosBitacorasList,
        getAllEventosByBitCodigo,
        getAllEstados,
        getUsers,
        saveEventoBitacora,
        userLogin,
        sendEmail,
        getEventoBitacoraService,
        documentosBitacoraList,
        getDocumentosByBitCodigo,
        bitacorasExternasList,
        findBitacorasElectronicas,
        getDocumentoExternoByBidCodigo,
        saveDocumentoExterno,
        getDocumentoExternoByBidCodigoRecibidos,
        getDocumentoExternoByBidCodigoRespuesta,
        editBitacoraExterna,
        editBitacoraElectronica,
        sendEmailSolDocumentacionFisica,
        sendEmailRespuestaElectronicaRemitente,
        enviarMailDocumentacionFisicaReasignada
    }

}