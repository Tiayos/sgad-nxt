import { Persona } from "models/Sumilla.model";

export const useBitacorasDestinatariosComposable = () =>{
    //* store pinia
    const useStore = useArchivosStore();
    const {bitacorasList} = storeToRefs(useStore);

    //*Services
    const {getBitacorasByPerCodigoDestinatario} = useBitacoraService();
    const {getUsrLogin} = usePersonaService();

    //*Auth
    const { data } = useAuth();

    //*Session storage
    const { data: userLogin } = useSessionStorage<Persona>("userLogin");


    onMounted(async() => {
        await findBitacorasDestinatarios();
    })

    const findBitacorasDestinatarios = async() =>{
        userLogin.value = await getUsrLogin(data.value?.user?.email!);
        bitacorasList.value = await getBitacorasByPerCodigoDestinatario(userLogin.value.codigo);
    }

return {
    bitacorasList
}

}