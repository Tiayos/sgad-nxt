import type { Bitacora } from "./Bitacora.model";

export interface DocumentoBitacora {
    codigo:                     number;
    doc_archivo:                any;
    doc_nombre_archivo:         string;
    bitacora:                   Bitacora;
    adicionado:                 string;
}