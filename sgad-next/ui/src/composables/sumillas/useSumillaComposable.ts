import { Persona, Sumilla } from "models/Sumilla.model";
import { required, is_not } from "@vee-validate/rules";
import { Bitacora } from "models/Bitacora.model";
import { useVuelidate, ValidationRuleWithParams  } from '@vuelidate/core';
import { SedeProjection } from "models/projection/SedeProjection.model";
import { EventoBitacora } from "models/EventoBitacora.model";
import { DocumentoBitacora } from "models/DocumentoBitacora.model";

export const useSumillaComposable = () =>{
//*Session storage
const { data: userLogin } = useSessionStorage<Persona>("userLogin");
const useStore = useArchivosStore();
const {bitacorasList, filtersSumillaBitacora, eventoBitacora} = storeToRefs(useStore);

    //*services
const {getSumillas, getSumillasBySede, saveSumilla, deleteSumilla, getSumillaByNumeroSumilla, editSumilla, getSedeByEmail } = useSumillaService();
const {getUsers, getUsrLogin, getUsersAmbitosGestionDocumental} = usePersonaService();
const {getBitacoras, getBitacorasBySede, getBitacorasByFechaAndEstado,
    saveBitacora, deleteBitacora, editBitacora, editEstadoEnvioBitacora,
    getBitacoraByNumSumilla, deleteBitacoraByNumSumilla, saveDocumentoBitacora,
    getDocumentosByBitCodigo, deleteDocumentosByBitCodigo} = useBitacoraService();
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
const documentObj = ref<DocumentoBitacora>({} as DocumentoBitacora);
const files = ref<File[]>([]); // Definir files como un ref que es un arreglo de File
const documentosBitacoraList = ref<DocumentoBitacora[]>([]);

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

const prepareEdit = async (sumillaParam: Sumilla, persistAct:persistAction) => {
    action.value = persistAct;
    sumilla.value = { ...sumillaParam };
    numHojas.value =
      sumilla.value.numero_hojas != null ? sumilla.value.numero_hojas.toString() : "";
  
    bitacora.value = await getBitacoraByNumSumilla(sumilla.value.numero_sumilla);
    documentosBitacoraList.value = await getDocumentosByBitCodigo(bitacora.value.codigo);

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
  
  const uint8ArrayToFile = (byteArray: number[], fileName: string): File => {
    const uint8Array = new Uint8Array(byteArray);
    const blob = new Blob([uint8Array], { type: "application/pdf" });
    return new File([blob], fileName, { type: "application/pdf" });
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

const findBitacorasByFechaTransferencia = async(fechaInicio:string, fechaFin:string, resPerCodigo:number) =>{
    bitacorasListTransferenciaDocumental.value = await getBitacorasByFechaAndEstado(fechaInicio, fechaFin, resPerCodigo);
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
    deleteDocumentosByBitCodigo,
    getDocumentosByBitCodigo,
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
    deleteEventoBitacora,
    documentObj,
    saveDocumentoBitacora,
    files,
    documentosBitacoraList
}
}
