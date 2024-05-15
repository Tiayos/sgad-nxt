import { Sumilla } from "models/Sumilla.model";

export const useSumillaComposable = () =>{

    //*services
const {getSumillas, saveSumilla, deleteSumilla} = useSumillaService();

    //*New Sumilla
const sumilla = ref<Sumilla>({} as Sumilla)

const sumillaList = ref<Sumilla[]>([]);

onMounted(async() => {
    await findSumillas();
})

const findSumillas = async() =>{
    sumillaList.value = await getSumillas();
    console.log(sumillaList.value , '<<..');

}

return {
    sumillaList,
    sumilla,
    saveSumilla,
    findSumillas,
    deleteSumilla
}
}
