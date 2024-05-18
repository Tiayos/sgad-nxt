import { Persona, Sumilla } from "models/Sumilla.model";

export const useSumillaComposable = () =>{

    //*services
const {getSumillas, saveSumilla, deleteSumilla, getSumillaByNumeroSumilla, editSumilla} = useSumillaService();
const {getUsers} = usePersonaService();

    //*New Sumilla
const sumilla = ref<Sumilla>({} as Sumilla)
const { data } = useAuth();

const sumillaList = ref<Sumilla[]>([]);
const receptorPersonaList = ref<Persona[]>([]);

onMounted(async() => {
    await findSumillas();
    receptorPersonaList.value = await getUsers();

})

const findSumillas = async() =>{
    sumillaList.value = await getSumillas();
}


//* BUSCAR SUMILLA BITACORA
const sumillaEncontrada = ref<Sumilla|null>({} as Sumilla)

return {
    sumillaList,
    sumilla,
    sumillaEncontrada,
    saveSumilla,
    editSumilla,
    findSumillas,
    receptorPersonaList,
    deleteSumilla,
    getSumillaByNumeroSumilla,
    data
}
}
