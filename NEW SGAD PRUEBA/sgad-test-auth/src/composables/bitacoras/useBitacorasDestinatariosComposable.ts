import {useArchivosStore} from "~/composables/store/useArchivosStore";
import type {DocumentoBitacora} from "~/models/DocumentoBitacora.model";
import {usePersonaService} from "~/composables/services/usePersonaService";
import {useBitacoraService} from "~/composables/services/useBitacoraService";
import {useSendEmailService} from "~/composables/services/useSendEmailService";
import {useEventoBitacora} from "~/composables/services/useEventoBitacora";
import type {Persona} from "~/models/Sumilla.model";


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

    const {sendEmail} = useSendEmailService();

    //*Auth
    const { data } = useAuth();

    //*Session storage
    const { data: userLogin } = useSessionStorage<Persona>("userLogin");

    onMounted(async() => {
        await findBitacorasDestinatarios();
    })

    const findBitacorasDestinatarios = async() =>{
        userLogin.value = await getUsrLogin(data.value?.user?.email!);
        eventosBitacorasList.value = await getAllEventosVigentesByPerCodigo(userLogin.value.codigo);
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
        getDocumentosByBitCodigo
    }

}