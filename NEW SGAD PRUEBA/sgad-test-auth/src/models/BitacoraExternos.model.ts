import type { Sumilla } from "./Sumilla.model";
import type { Persona } from './Sumilla.model';

export interface BitacoraExternos {
    codigo:              number;
    nombres_remitente:   string;
    apellidos_remitente: string;
    asunto:              string;
    correo_remitente:    string;
    correo_destinatario: string;
    adicionado:          string;
    sumilla:             Sumilla;
    nombre_completo_destinatario:   string;
    estado:              number;
    destinatario:        Persona;
    responsable:         Persona;
    observacion:         string;
    nombre_organizacion:    string;
    codigo_consulta:     string;
}