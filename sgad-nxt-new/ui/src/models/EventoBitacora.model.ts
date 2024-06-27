import { Bitacora } from "./Bitacora.model";
import { Estado } from "./Estado.model";
import { Persona } from "./Sumilla.model";

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