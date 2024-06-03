import { ErrorMessage } from 'vee-validate';
import { Bitacora } from "models/Bitacora.model";
import { Persona, Sumilla } from "models/Sumilla.model";

export const useBitacoraComposable = () =>{
    //*services
const {getSumillaByNumeroSumilla} = useSumillaService();
const {getUsers} = usePersonaService();

    //*New Bitacora
const bitacora = ref<Bitacora>({} as Bitacora);
const sumilla = ref<Sumilla|null>({} as Sumilla);
const receptorPersonaList = ref<Persona[]>([]);

onMounted(async() => {
})


return {
    bitacora,
    sumilla,
    receptorPersonaList,
    getSumillaByNumeroSumilla,
    getUsers,
    ErrorMessage
}
}
