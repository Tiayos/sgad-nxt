import { Persona, Sumilla } from "models/Sumilla.model";
import { required, is_not } from "@vee-validate/rules";
import { Bitacora } from "models/Bitacora.model";
import { useVuelidate, ValidationRuleWithParams  } from '@vuelidate/core';
import { SedeProjection } from "models/projection/SedeProjection.model";
import { EventoBitacora } from "models/EventoBitacora.model";

export const useSumillaComposable = () =>{
//*Session storage
const { data: userLogin } = useSessionStorage<Persona>("userLogin");
const useStore = useArchivosStore();
const {bitacorasList, filtersSumillaBitacora, eventoBitacora} = storeToRefs(useStore);

    //*services
const {getSumillas, getSumillasBySede, saveSumilla, deleteSumilla, getSumillaByNumeroSumilla, editSumilla, getSedeByEmail } = useSumillaService();
const {getUsers, getUsrLogin, getUsersAmbitosGestionDocumental} = usePersonaService();
const {getBitacoras, getBitacorasBySede, getBitacorasByFechaAndEstado, saveBitacora, deleteBitacora, editBitacora, editEstadoEnvioBitacora, getBitacoraByNumSumilla, deleteBitacoraByNumSumilla} = useBitacoraService();
const {saveTransferencia} = useTransferenciaDocumentalService();
const {getEventoBitacoraService, saveEventoBitacora, deleteEventoBitacora} = useEventoBitacora();


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
const eventoVigente = ref<EventoBitacora>({} as EventoBitacora);

const validateBitacora = {
    nombres_remitente:   { required },
    apellidos_remitente: { required },
    lugar_destino:       { required },
    destinatario:        { required },
    asunto:              { required },
    mensajero:           { required },
}
const v$ = useVuelidate(validateBitacora, bitacora);
enum persistAction {
  create,
  edit,
  view,
}
const action = ref();
const createModal = ref<boolean>(false);

const {
    value: numHojas,
    errorMessage: numHojasError,
    resetField: resetNumHojas,
  } = useField<string>("numHojas", {
    required: true,
  })
  const fechaEntrega = ref<string>("");

const prepareEdit = async (sumillaParam: Sumilla) => {
    action.value = persistAction.edit;
    sumilla.value = { ...sumillaParam };
    console.log(sumilla.value.numero_hojas, "sumilla.value.numero_hojas");
    numHojas.value =
      sumilla.value.numero_hojas != null ? sumilla.value.numero_hojas.toString() : "";
  
    bitacora.value = await getBitacoraByNumSumilla(sumilla.value.numero_sumilla);
    fechaEntrega.value =
      bitacora.value.fecha_entrega != null ? bitacora.value.fecha_entrega.toString() : "";
  
    bitacora.value.mensajero.nombreCompleto = bitacora.value.mensajero.per_apellidos
      .concat(" ")
      .concat(bitacora.value.mensajero.per_nombres);
    bitacora.value.destinatario.nombreCompleto = bitacora.value.destinatario.per_apellidos
      .concat(" ")
      .concat(bitacora.value.destinatario.per_nombres);
  
    if (bitacora.value.usr_emisor != null) {
      bitacora.value.usr_emisor.nombreCompleto = bitacora.value.usr_emisor.per_apellidos
        .concat(" ")
        .concat(bitacora.value.usr_emisor.per_nombres);
    }
  
    if (bitacora.value.usr_receptor != null) {
      bitacora.value.usr_receptor.nombreCompleto = bitacora.value.usr_receptor.per_apellidos
        .concat(" ")
        .concat(bitacora.value.usr_receptor.per_nombres);
    }
  
    createModal.value = !createModal.value;
  };
  

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
    eventoBitacora,
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
    //*EVENTO BITACORA
    getEventoBitacoraService,
    saveEventoBitacora,
    eventoVigente,
    filtersSumillaBitacora,
    v$,
    data,
    sede,
    prepareEdit,
    persistAction,
    action,
    numHojas,
    fechaEntrega,
    resetNumHojas,
    numHojasError,
    createModal,
    deleteEventoBitacora
}
}
