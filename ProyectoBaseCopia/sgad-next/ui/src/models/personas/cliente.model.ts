export interface Cliente {
    numero_identificacion:   string;
    fecha_nacimiento:        string;
    porcentaje_discapacidad: string | '';
    tipo_discapacidad:       string | '';
    nivel_instruccion:       GenericInterface;
    tipo_persona:            GenericInterface;
    area_geografica:         AreaGeografica;
    estado_civil:            string;
    recibe_bono:             string;
    per_codigo:              0;
    codigo:                  number;
    nombres:                 string;
    apellidos:               string;
    edad:                    number;
    direccion:               string;
    discapacidad:            GenericInterface;
    etnia:                   GenericInterface;
    genero:                  GenericInterface;
    ocupacion:               GenericInterface;
    nivel_ingresos:          GenericInterface;
    zona_vivienda:           GenericInterface;
    telefono:                string;
    estado:                  string;
}

export interface AreaGeografica {
    codigo:      number;
    nombre:      string;
    abreviatura: string;
}

export interface GenericInterface {
    codigo:      number;
    descripcion: string;
}
