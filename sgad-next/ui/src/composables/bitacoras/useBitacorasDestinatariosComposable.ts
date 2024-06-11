import { EventoBitacora } from "models/EventoBitacora.model";
import { Persona } from "models/Sumilla.model";

export const useBitacorasDestinatariosComposable = () =>{
//* store
const useStore = useArchivosStore();
const {eventosBitacorasList} = storeToRefs(useStore);

    //*Services
    const {getUsrLogin} = usePersonaService();
    const {getAllEventosVigentesByPerCodigo, getAllEventosByBitCodigo} = useEventoBitacora();

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
    getAllEventosByBitCodigo
}

}