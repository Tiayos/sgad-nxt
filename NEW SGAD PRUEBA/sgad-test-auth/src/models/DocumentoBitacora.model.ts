import type {Bitacora} from "~/models/Bitacora.model";

export interface DocumentoBitacora {
    codigo:                     number;
    doc_archivo:                any;
    doc_nombre_archivo:         string;
    bitacora:                   Bitacora;
    adicionado:                 string;
    estado_tramite:            string;
}