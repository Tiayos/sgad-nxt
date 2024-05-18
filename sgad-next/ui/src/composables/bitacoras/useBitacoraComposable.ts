import { useVuelidate, ValidationRuleWithParams  } from '@vuelidate/core';
import { ErrorMessage } from 'vee-validate';
import { required, is_not } from "@vee-validate/rules";
import { Bitacora } from "models/Bitacora.model";
import { Persona, Sumilla } from "models/Sumilla.model";
import { requiredIf } from "@vuelidate/validators";

export const useBitacoraComposable = () =>{

    //*services
const {getBitacoras, saveBitacora, deleteBitacora, editBitacora} = useBitacoraService();
const {getSumillaByNumeroSumilla} = useSumillaService();
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
    lugar_destino:       { required },
    destinatario:        { required },
    asunto:              { required },
    mensajero:           { required },
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
    getSumillaByNumeroSumilla,
    getUsers,
    saveBitacora,
    editBitacora,
    deleteBitacora,
    v$,
    ErrorMessage
}
}
