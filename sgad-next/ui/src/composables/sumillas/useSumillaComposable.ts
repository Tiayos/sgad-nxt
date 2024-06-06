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
const {getSumillas, getSumillasBySede, saveSumilla, deleteSumilla, getSumillaByNumeroSumilla, editSumilla, getSedeByEmail } = useSumillaService();
const {getUsers, getUsrLogin, getUsersAmbitosGestionDocumental} = usePersonaService();
const {getBitacoras, getBitacorasBySede, getBitacorasByFechaAndEstado, saveBitacora, deleteBitacora, editBitacora, editEstadoEnvioBitacora, getBitacoraByNumSumilla, deleteBitacoraByNumSumilla} = useBitacoraService();
const {saveTransferencia} = useTransferenciaDocumentalService();


    //*New Sumilla
const sumilla = ref<Sumilla>({} as Sumilla)
const { data } = useAuth();

const sumillaList = ref<Sumilla[]>([]);
const receptorPersonaList = ref<Persona[]>([]);
const usersGestionDocumentalList = ref<Persona[]>([]);

const bitacora = ref<Bitacora>({} as Bitacora);
const sede = ref<SedeProjection>({} as SedeProjection);
const bitacorasListTransferenciaDocumental = ref<Bitacora[]>([]);
const mensajeTransferencia = ref<string>("");


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
    usersGestionDocumentalList.value = await getUsersAmbitosGestionDocumental();
})

const findBitacoras = async() =>{
    sede.value = await getSedeByEmail(data.value?.user?.email!);
    bitacorasList.value = await getBitacorasBySede(sede.value.dee_codigo);
}

const findBitacorasByFechaTransferencia = async(fechaInicio:string, fechaFin:string) =>{
    bitacorasListTransferenciaDocumental.value = await getBitacorasByFechaAndEstado(fechaInicio, fechaFin);
    if(bitacorasListTransferenciaDocumental.value.length==0){
        mensajeTransferencia.value = 'No se encuentra ningún documento por enviar en las fechas seleccionadas'
    }
}

const findSumillas = async() =>{
    sede.value = await getSedeByEmail(data.value?.user?.email!);
    sumillaList.value = await getSumillasBySede(sede.value.dee_codigo);
}

//* BUSCAR SUMILLA BITACORA
const sumillaEncontrada = ref<Sumilla|null>({} as Sumilla)

return {
    sumillaList,
    usersGestionDocumentalList,
    bitacorasList,
    mensajeTransferencia,
    bitacorasListTransferenciaDocumental,
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
    findBitacorasByFechaTransferencia,
    editEstadoEnvioBitacora,
    filtersSumillaBitacora,
    v$,
    data,
    sede
}
}
