export interface Sumilla {
    codigo?: number;
    fecha_sumilla: Date;
    hora_sumilla:  string;
    numero_hojas:  number|0;
    responsable:   Persona;
    numero_sumilla: string;
    numero_tramite: number;
    sum_sede:       number;
    sum_cod_sede:   number;
}

export interface Persona {
    numero_identificacion: string;
    per_apellidos:         string;
    per_nombres:           string;
    per_genero:            string;
    fecha_nacimiento:      Date;
    calle_principal:       string;
    numero_casa:           string;
    codigo:                number;
    nombreCompleto:        any;
}
