import { Bitacora } from "./Bitacora.model";
import { Estado } from "./Estado.model";

export interface EventoBitacora {
    codigo:              number;
    fecha:               Date;
    vigencia:            string;
    bitacora:            Bitacora;
    estado:              Estado;
    adicionado:          string;
}