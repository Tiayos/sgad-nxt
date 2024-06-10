import { EventoBitacora } from "models/EventoBitacora.model";
import { Persona } from "models/Sumilla.model";

export const useBitacorasDestinatariosComposable = () =>{

    //*Services
    const {getUsrLogin} = usePersonaService();
    const {getAllEventosBitacoraByPerCodigo} = useEventoBitacora();

    //*Auth
    const { data } = useAuth();

    //*Session storage
    const { data: userLogin } = useSessionStorage<Persona>("userLogin");
    const eventosBitacorasList = ref<EventoBitacora[]>([]);

    onMounted(async() => {
        await findBitacorasDestinatarios();
    })

    const findBitacorasDestinatarios = async() =>{
        userLogin.value = await getUsrLogin(data.value?.user?.email!);
        eventosBitacorasList.value = await getAllEventosBitacoraByPerCodigo(userLogin.value.codigo);
    }

return {
    eventosBitacorasList
}

}