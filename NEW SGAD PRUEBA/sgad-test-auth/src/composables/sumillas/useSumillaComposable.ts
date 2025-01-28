import {useSessionStorage} from "~/utils/useSessionStorage";
import type {Persona, Sumilla} from "~/models/Sumilla.model";
import {useArchivosStore} from "~/composables/store/useArchivosStore";
import {usePersonaService} from "~/composables/services/usePersonaService";
import {useBitacoraService} from "~/composables/services/useBitacoraService";
import {useTransferenciaDocumentalService} from "~/composables/services/useTransferenciaDocumentalService";
import {useEventoBitacora} from "~/composables/services/useEventoBitacora";
import type {Bitacora} from "~/models/Bitacora.model";
import type {SedeProjection} from "~/models/projection/SedeProjection.model";
import type {EventoBitacora} from "~/models/EventoBitacora.model";
import type {DocumentoBitacora} from "~/models/DocumentoBitacora.model";
import {useSumillaService} from "~/composables/services/useSumillaService";
import { useField, useForm, useFieldError, useFormErrors } from 'vee-validate';
import {required, toTypedSchema} from "@vee-validate/rules";
import * as yup from 'yup';
import { useSendEmailService } from "../services/useSendEmailService";
// import useVuelidate from "@vuelidate/core";

export const useSumillaComposable = () =>{
//*Session storage
    const { data: userLogin } = useSessionStorage<Persona>("userLogin");
    const useStore = useArchivosStore();
    const {bitacorasList, filtersSumillaBitacora, eventoBitacora} = storeToRefs(useStore);

    //*services
    const {getSumillasBySede, saveSumilla, deleteSumilla, getSumillaByNumeroSumilla, editSumilla, getSedeByEmail } = useSumillaService();
    const {getUsers, getUsersByFilterName, getUsrLogin, getUsersAmbitosGestionDocumental} = usePersonaService();
    const {getBitacorasBySede, getBitacorasByFechaAndEstado,
        saveBitacora, deleteBitacora, editBitacora, editEstadoEnvioBitacora,
        getBitacoraByNumSumilla, deleteBitacoraByNumSumilla, saveDocumentoBitacora,
        getDocumentosByBitCodigo, deleteDocumentosByBitCodigo, getDocsRespuestaTramiteByBitCodigo} = useBitacoraService();
    const {saveTransferencia} = useTransferenciaDocumentalService();
    const {getEventoBitacoraService, saveEventoBitacora, deleteEventoBitacora} = useEventoBitacora();
    const {sendEmailDocFisicaBitacora} = useSendEmailService();


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
    const filesRespuesta = ref<File[]>([]); // Definir files como un ref que es un arreglo de File
    const documentosBitacoraList = ref<DocumentoBitacora[]>([]);
    const docBitacoraListRespuesta = ref<DocumentoBitacora[]>([]);
    const checked = ref(false);
    const checkedReasignacion = ref(false);

    const { handleSubmit, resetForm } = useForm({
        validationSchema: yup.object({
            numHojas: yup.string().required(),
            nombres_remitente: yup.string().required(),
            apellidos_remitente: yup.string().required(),
            // mensajero: yup.object().required(),
            mensajero: yup.object()
            .nullable() // Permite valores nulos si no es requerido
            .when([], {
                is: () => checked.value == false, // Chequea la condición
                then: (schema) => schema.required('El mensajero es obligatorio'),
                otherwise: (schema) => schema.nullable(), // Si no, no aplica restricciones
            }),
            lugar_destino: yup.string().required(),
            destinatario: yup.object().required(),
            asunto: yup.string().required(),
            mensajero_externo: yup.string()
            .nullable() // Permite valores nulos si no es requerido
            .when([], {
                is: () => checked.value == true, // Chequea la condición
                then: (schema) => schema.required('El mensajero externo es obligatorio'),
                otherwise: (schema) => schema.nullable(), // Si no, no aplica restricciones
            }),
        }),
    });



    const {
        value: numHojas,
        errorMessage: numHojasError,
        resetField: resetNumHojas,
    } = useField<string>("numHojas", {
        required: true,
    })
    const {
        value: nombres_remitente,
        errorMessage: nombres_remitenteError,
        resetField: resetnombres_remitente,
    } = useField<string>("nombres_remitente", {
        required: true,
    })
    const {
        value: apellidos_remitente,
        errorMessage: apellidos_remitenteError,
        resetField: resetapellidos_remitente,
    } = useField<string>("apellidos_remitente", {
        required: true,
    })
    const {
        value: mensajero,
        errorMessage: mensajeroError,
        resetField: resetmensajero,
    } = useField<Persona>("mensajero", {
        esInterno: [checked]
    })
    const {
        value: mensajeroExterno,
        errorMessage: mensajeroExternoError,
        resetField: resetMensajeroExterno,
    } = useField<string>("mensajero_externo", {
        exExterno: [checked]
        // required: true
    })

    const {
        value: lugar_destino,
        errorMessage: lugar_destinoError,
        resetField: resetlugar_destino,
    } = useField<string>("lugar_destino", {
        required: true,
    })

    const {
        value: destinatario,
        errorMessage: destinatarioError,
        resetField: resetdestinatario,
    } = useField<Persona>("destinatario", {
        required: true,
    })
    const {
        value: asunto,
        errorMessage: asuntoError,
        resetField: resetasunto,
    } = useField<string>("asunto", {
        required: true,
    })

    // const v$ = useVuelidate(validateBitacora, bitacora);
    enum persistAction {
        create,
        edit,
        view,
    }
    const action = ref();
    const createModal = ref<boolean>(false);

    const fechaEntrega = ref<string>("");

    const prepareEdit = async (sumillaParam: Sumilla, persistAct:persistAction) => {
        action.value = persistAct;
        sumilla.value = { ...sumillaParam };
        numHojas.value =
            sumilla.value.numero_hojas != null ? sumilla.value.numero_hojas.toString() : "";

        bitacora.value = await getBitacoraByNumSumilla(sumilla.value.numero_sumilla);
        documentosBitacoraList.value = await getDocumentosByBitCodigo(bitacora.value.codigo);
        docBitacoraListRespuesta.value = await getDocsRespuestaTramiteByBitCodigo(bitacora.value.codigo);

        nombres_remitente.value = bitacora.value.nombres_remitente;
        apellidos_remitente.value = bitacora.value.apellidos_remitente;
        mensajero.value = bitacora.value.mensajero != null ? bitacora.value.mensajero : {} as Persona;
        lugar_destino.value = bitacora.value.lugar_destino;
        destinatario.value = bitacora.value.destinatario;
        asunto.value = bitacora.value.asunto;
        mensajeroExterno.value = bitacora.value.mensajero_externo != null ? bitacora.value.mensajero_externo : "";
        checked.value = bitacora.value.mensajero_externo != null ? true : false;

        fechaEntrega.value =
            bitacora.value.fecha_entrega != null ? bitacora.value.fecha_entrega.toString() : "";

        if(bitacora.value.mensajero != null ){
            bitacora.value.mensajero.nombreCompleto = bitacora.value.mensajero.per_apellidos
            .concat(" ")
            .concat(bitacora.value.mensajero.per_nombres);
        mensajero.value.nombreCompleto = bitacora.value.mensajero.nombreCompleto;
        }

        if(bitacora.value.destinatario != null ){
            bitacora.value.destinatario.nombreCompleto = bitacora.value.destinatario.per_apellidos
            .concat(" ")
            .concat(bitacora.value.destinatario.per_nombres);
        destinatario.value.nombreCompleto = bitacora.value.destinatario.nombreCompleto;
        }

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
        getPersonasByFilterName();
        usersGestionDocumentalList.value = await getUsersAmbitosGestionDocumental();
    })

    const getPersonasByFilterName = async(name:string = 'abe') =>{
       return receptorPersonaList.value = await getUsersByFilterName(name);
    }

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
        // v$,
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
        filesRespuesta,
        documentosBitacoraList,
        resetForm,
        handleSubmit,
        useFieldError, useFormErrors,
        nombres_remitente,
        nombres_remitenteError,
        resetnombres_remitente,
        apellidos_remitente,
        apellidos_remitenteError,
        resetapellidos_remitente,
        lugar_destino,
        lugar_destinoError,
        resetlugar_destino,
        destinatario,
        destinatarioError,
        resetdestinatario,
        mensajero,
        mensajeroError,
        resetmensajero,
        asunto,
        asuntoError,
        resetasunto,
        getPersonasByFilterName,
        sendEmailDocFisicaBitacora,
        mensajeroExterno,
        mensajeroExternoError,
        resetMensajeroExterno,
        checked,
        checkedReasignacion,
        docBitacoraListRespuesta, 
        getDocsRespuestaTramiteByBitCodigo
    }
}
