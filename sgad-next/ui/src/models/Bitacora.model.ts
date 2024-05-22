import { Sumilla, Persona } from './Sumilla.model';
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
}