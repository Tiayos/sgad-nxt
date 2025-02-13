import type {Persona, Sumilla} from "~/models/Sumilla.model";

export interface Bitacora {
    codigo:              number;
    nombres_remitente:   string;
    apellidos_remitente: string;
    receptor_documento:  Persona;
    lugar_destino:       string;
    numero_guia:         string;
    usr_emisor:          Persona;
    usr_receptor:        Persona;
    fecha_entrega:       Date;
    hora_entrega:        string;
    fecha_recepcion:     Date;
    hora_recepcion:      String;
    destinatario:        Persona;
    asunto:              string;
    mensajero:           Persona;
    observaciones:       string;
    sumilla:             Sumilla;
    doc_archivo:         any;
    nombre_archivo:      string;
    estado_transferencia: string;
    adicionado:          string;
    mensajero_externo:   string;
    documento_reasignado: boolean;
    codigo_recepcion_reasignado: number;
    per_codigo_entrega_documentacion: Persona;
    per_codigo_recibe_documentacion: Persona;
    fecha_entrega_documentacion: Date;
    hora_entrega_documentacion: string;
    numero_tramite : number;
    secuencial_documento: number;
}