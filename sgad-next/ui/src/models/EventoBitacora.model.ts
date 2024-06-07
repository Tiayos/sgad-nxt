import { Estado } from './Estado.model';
export interface Bitacora {
    codigo:              number;
    fecha:               Date;
    vigencia:            string;
    bitacora:            Bitacora;
    estado:              Estado;
}