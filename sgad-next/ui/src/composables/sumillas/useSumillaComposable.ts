import { Sumilla } from "models/Sumilla.model";

export const useSumillaComposable = () =>{

    //*services
const {getSumillas, saveSumilla, deleteSumilla, getSumillaByNumeroSumilla} = useSumillaService();

    //*New Sumilla
const sumilla = ref<Sumilla>({} as Sumilla)
const { data } = useAuth();

const sumillaList = ref<Sumilla[]>([]);

onMounted(async() => {
    await findSumillas();
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
    findSumillas,
    deleteSumilla,
    getSumillaByNumeroSumilla,
    data
}
}
