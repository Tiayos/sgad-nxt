import type {Bitacora} from "~/models/Bitacora.model";
import type {Estado} from "~/models/Estado.model";
import type {Persona} from "~/models/Sumilla.model";

export interface EventoBitacora {
    codigo:              number;
    fecha:               Date;
    vigencia:            string;
    bitacora:            Bitacora;
    estado:              Estado;
    adicionado:          string;
    per_codigo_responsable: Persona;
    per_codigo_reasignado: Persona;

}