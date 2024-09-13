import type { BitacoraExternos } from "./BitacoraExternos.model";

export interface DocumentosExternos {
    codigo:              number;
    doe_archivo:         any;
    doe_nombre_archivo:  string;
    documentos_externos: BitacoraExternos;
    adicionado:          string;
    estado_documento_electronico:   string;
}