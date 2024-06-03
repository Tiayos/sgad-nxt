import { Persona, Sumilla } from "models/Sumilla.model";
import { required, is_not } from "@vee-validate/rules";
import { Bitacora } from "models/Bitacora.model";
import { useVuelidate, ValidationRuleWithParams  } from '@vuelidate/core';
import { SedeProjection } from "models/projection/SedeProjection.model";

export const useSumillaComposable = () =>{
//*Session storage
const { data: userLogin } = useSessionStorage<Persona>("userLogin");
const useStore = useArchivosStore();
const {bitacorasList, filtersSumillaBitacora} = storeToRefs(useStore);

    //*services
const {getSumillas, saveSumilla, deleteSumilla, getSumillaByNumeroSumilla, editSumilla, getSedeByEmail } = useSumillaService();
const {getUsers, getUsrLogin} = usePersonaService();
const {getBitacoras, saveBitacora, deleteBitacora, editBitacora, getBitacoraByNumSumilla, deleteBitacoraByNumSumilla} = useBitacoraService();
const {saveTransferencia} = useTransferenciaDocumentalService();


    //*New Sumilla
const sumilla = ref<Sumilla>({} as Sumilla)
const { data } = useAuth();

const sumillaList = ref<Sumilla[]>([]);
const receptorPersonaList = ref<Persona[]>([]);

const bitacora = ref<Bitacora>({} as Bitacora);

const sede = ref<SedeProjection>({} as SedeProjection);

const validateBitacora = {
    nombres_remitente:   { required },
    apellidos_remitente: { required },
    lugar_destino:       { required },
    destinatario:        { required },
    asunto:              { required },
    mensajero:           { required },
}
const v$ = useVuelidate(validateBitacora, bitacora);

onMounted(async() => {
    await findSumillas();
    await findBitacoras();
    receptorPersonaList.value = await getUsers();
})

const findBitacoras = async() =>{
    bitacorasList.value = await getBitacoras();
}

const findSumillas = async() =>{
    sumillaList.value = await getSumillas();
}

//* BUSCAR SUMILLA BITACORA
const sumillaEncontrada = ref<Sumilla|null>({} as Sumilla)

return {
    sumillaList,
    bitacorasList,
    sumilla,
    bitacora,
    sumillaEncontrada,
    saveSumilla,
    editSumilla,
    findSumillas,
    getUsrLogin,
    receptorPersonaList,
    deleteSumilla,
    getSumillaByNumeroSumilla,
    saveBitacora,
    findBitacoras,
    editBitacora,
    deleteBitacora,
    getUsers,
    getBitacoraByNumSumilla,
    deleteBitacoraByNumSumilla,
    getSedeByEmail,
    saveTransferencia,
    filtersSumillaBitacora,
    v$,
    data,
    sede
}
}
