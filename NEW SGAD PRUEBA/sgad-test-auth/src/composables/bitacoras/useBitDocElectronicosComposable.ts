import { ErrorMessage } from 'vee-validate';
import type { Persona } from '~/models/Sumilla.model';
import * as yup from 'yup';
import { useBitacoraExternaService } from '../services/useBitacoraExternos';
import { usePersonaService } from '../services/usePersonaService';
import { useSendEmailService } from '../services/useSendEmailService';
export const useBitDocElectronicosComposable = () =>{
   
const {editBitacoraExterna} = useBitacoraExternaService();
const {getUsrLogin} = usePersonaService();
const {sendEmailReasignado, sendEmailDestinatario} = useSendEmailService();

    const { handleSubmit, resetForm } = useForm({
        validationSchema: yup.object({
          accion: yup.number().required(),
          destinatario: yup.object().when('accion', (accionValue: any, schema: any) => {
            return accionValue == 1 ? schema.required() : schema.notRequired();
          }),
          sedeSelected: yup.number().when('accion', (accionValue: any, schema: any) => {
            return accionValue == 2 ? schema.required() : schema.notRequired();
          }),
          observacion: yup.string().when('accion', (accionValue: any, schema: any) => {
            return accionValue == 3 ? schema.required() : schema.notRequired();
          }),
        }),
      });

const {
    value: sedeSelected,
    errorMessage: sedeSelectedError,
    resetField: resetSedeSelected,
} = useField<number>("sedeSelected", {
    required: true,
})

const {
    value: observacion,
    errorMessage: observacionError,
    resetField: resetObservacion,
} = useField<string>("observacion", {
    required: true,
})

const {
    value: accion,
    errorMessage: accionError,
    resetField: resetAccion,
} = useField<number>("accion", {
    required: true,
})

const {
    value: destinatario,
    errorMessage: destinatarioError,
    resetField: resetdestinatario,
} = useField<Persona>("destinatario", {
    required: true,
})

    return {
        accion,
        accionError,
        destinatario,
        destinatarioError,
        resetdestinatario,
        sedeSelected,
        sedeSelectedError,
        resetSedeSelected,
        observacion,
        observacionError,
        resetObservacion,
        ErrorMessage,
        handleSubmit,
        getUsrLogin,
        editBitacoraExterna,
        sendEmailReasignado,
        sendEmailDestinatario
    }
}
