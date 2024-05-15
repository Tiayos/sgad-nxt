import { useVuelidate, ValidationRuleWithParams  } from '@vuelidate/core';
import { ErrorMessage } from 'vee-validate';
import { required, is_not } from "@vee-validate/rules";
import { Bitacora } from "models/Bitacora.model";
import { Persona, Sumilla } from "models/Sumilla.model";
import { requiredIf } from "@vuelidate/validators";

export const useBitacoraComposable = () =>{

    //*services
const {getBitacoras, saveBitacora, deleteBitacora, editBitacora} = useBitacoraService();
const {getSumillaById} = useSumillaService();
const {getUsers} = usePersonaService();

    //*New Bitacora
const bitacora = ref<Bitacora>({} as Bitacora);
const sumilla = ref<Sumilla|null>({} as Sumilla);
const receptorPersonaList = ref<Persona[]>([]);

const reqNumeroTramite = () =>{
    return true;
}


    //*Validate
const validateBitacora = {
    nombres_remitente:   { required },
    apellidos_remitente: { required },
    receptor_documento:  { required },
    lugar_destino:       { required },
    registro_sgad:       { required },
    numero_tramite:      { 
        requiredIf: (value:any, vm:any) => {
            if(bitacora.value.registro_sgad == 'S' && (bitacora.value.numero_tramite == '' || bitacora.value.numero_tramite == null) ){
                return false
            }else{
             return true;
            }
            
        }
    },
    fecha_recepcion:     { required },
    hora_recepcion:      { required },
    destinatario:        { required },
    asunto:              { required },
    mensajero:           { required },
    sumilla:             { required },
}

const v$ = useVuelidate(validateBitacora, bitacora);
const bitacorasList = ref<Bitacora[]>([]);

onMounted(async() => {
    await findBitacoras();
})

const findBitacoras = async() =>{
    bitacorasList.value = await getBitacoras();
    console.log(bitacorasList.value);
}

return {
    bitacora,
    sumilla,
    receptorPersonaList,
    bitacorasList,
    findBitacoras,
    getSumillaById,
    getUsers,
    saveBitacora,
    editBitacora,
    deleteBitacora,
    v$,
    ErrorMessage
}
}
