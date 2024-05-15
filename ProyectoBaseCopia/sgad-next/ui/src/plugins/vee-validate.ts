import { defineRule, configure, Form, Field, ErrorMessage, useField, useForm } from 'vee-validate';

import * as data from '@vee-validate/rules'
import { localize } from '@vee-validate/i18n';

export default defineNuxtPlugin(({ vueApp: app }) => {
    
    const rules = data as { [key: string]: any }
    Object.keys(data)
        .filter(k => k !== 'default')
        .forEach(rule => {
            defineRule(rule, rules[rule])
            // logger.info(`Defining rule: ${rule}`)
        })

    defineRule('correoPersonal', (value: string) => {
        if (!/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(value)) {
            return 'El correo no es válido';
        }
        return true;
    });

    defineRule('telefonosCelular', (value: string) => {
        if (value != null) {
            if (value.length > 10) {
                return 'Ingrese máximo 10 números';
            }
        }

        return true;
    });

    defineRule('telefonoDomicilio', (value: string) => {
        if (value != null) {
            if (value.length > 7) {
                return 'Ingrese máximo 7 números';
            }
        }

        return true;
    });

    defineRule('numeroMiembros', (value: number) => {
        if (value <= 0 && value > 30) {
            return 'ingrese un numero entre 0 y 30'
        }
        return true;
    });

    defineRule('reqSegunRol', (value: string, [activeModalFilter]:any[]) => {
        if(activeModalFilter._value==true && (value==undefined || value == null)){
            return 'El campo es obligatorio'
        }else{
            return true;
        } 
     });

    defineRule('tieneDiscapacidad', (value: string, [activeModalFilter]:any[]) => {
        if(activeModalFilter._value==1 && (value==undefined || value == null)){
            return 'El campo es obligatorio'
        }else{
            return true;
        } 
     });

    defineRule('modalFilter', (value: string, [activeModalFilter]:any[]) => {
        if(activeModalFilter._value==true && value==undefined){
            return 'El campo es obligatorio'
        }else{
            return true;
        } 
     });

     defineRule('modalEditClient', (value: string, [activeEditCliente]:any[]) => {
        if(activeEditCliente._value==true && value==undefined){
            return 'El campo es obligatorio'
        }else{
            return true;
        } 
     });

     defineRule('clienteTieneDiscapaciad', (value: string, [activeEditCliente]:any[]) => {
        if(activeEditCliente._value=='SI' && value==undefined){
            return 'El campo es obligatorio'
        }else{
            return true;
        } 
     });

     defineRule('activeEditDerivacion', (value: string, [activeEditDerivacion]:any[]) => {
        if(activeEditDerivacion._value==true && value==undefined){
            return 'El campo es obligatorio'
        }else{
            return true;
        } 
     });

     defineRule('modalEditUsers', (value: string, [activeEditUsers]:any[]) => {
        if(activeEditUsers._value==true && (value==undefined || value=="")){
            return 'El campo es obligatorio'
        }else{
            return true;
        } 
     });
    
     defineRule('modalNewUsers', (value: string, [activeNewUsers]:any[]) => {
        if(activeNewUsers._value==true && (value==undefined || value=="")){
            return 'El campo es obligatorio'
        }else{
            return true;
        } 
     });

     defineRule('validarFecha', (fechaFin: string, [fechaInicio]:any[]) => {
        const fechaInicioObj = new Date(fechaInicio._value);
        const fechaFinObj = new Date(fechaFin);
        if(fechaInicioObj>fechaFinObj ){
            return 'La fecha final no puede ser menor a la fecha inicial'
        }else{
            return true;
        } 
     });

     defineRule('activeModalCaso', (value: string, [active]:any[]) => {
        if(active._==true &&value==undefined){
            return 'El campo es obligatorio'
        }else{
            return true
        } 
     });

    defineRule('esPatrocinio', (value: string, [patrocinio]:any[]) => {
        if(patrocinio._value=='SI'&&value==undefined){
            return 'El campo es obligatorio'
        }else{
            return true
        } 
     });

     defineRule('esDerivacion', (value: string, [derivacion]:any[]) => {
        if(derivacion._value=='SI'&&value==undefined){
            return 'El campo es obligatorio'
        }else{
            return true
        } 
     });


    defineRule('validarCedula', (value: string,  [otraVariable]: any[] ) => {
if(value&&otraVariable._value=='CEDULA'){
    let cad = value;
    let total = 0;
    let longitud = cad.length;
    let longcheck = longitud - 1;
    if (value.length == 10) {
        for (let i = 0; i < longcheck; i++) {
            if (i % 2 === 0) {
                let aux = +cad.charAt(i) * 2;
                if (aux > 9) aux -= 9;
                total += aux;
            } else {
                total += parseInt(cad.charAt(i)); // parseInt o concatenará en lugar de sumar
            }
        }

        total = total % 10 ? 10 - total % 10 : 0;

        if (+cad.charAt(longitud - 1) == total) {
            return true
        } else {
            return false
        }
    } else {
        return false
    }
}
        return true;
    }
    );


    configure({
        generateMessage: localize("es", {
            messages: {
                required: "Este campo es requerido",
                email: "El correo no es válido",
                min: "Ingrese al menos 0:{min} caracteres",
                max: "Ingrese máximo 0:{max} caracteres",
                validarCedula: "la cédula no es válida"
            }
        })
    })

    app.component('Form', Form)
    app.component('Field', Field)
    app.component('ErrorMessage', ErrorMessage)

    localize("es")

})

