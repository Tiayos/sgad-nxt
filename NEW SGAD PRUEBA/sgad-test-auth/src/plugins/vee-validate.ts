import { defineRule, configure, Form, Field, ErrorMessage, useField, useForm } from 'vee-validate';

import * as data from '@vee-validate/rules'
import { localize } from '@vee-validate/i18n';

export default defineNuxtPlugin(({ vueApp: app }) => {
    
    // const rules = data as { [key: string]: any }
    // Object.keys(data)
    //     .filter(k => k !== 'default')
    //     .forEach(rule => {
    //         defineRule(rule, rules[rule])
    //         // logger.info(`Defining rule: ${rule}`)
    //     })

    defineRule('esInterno', (value: string, [checked]:any[]) => {
        if(checked._value==false && value==undefined){
            return 'El campo es obligatorio'
        }else{
            return true
        } 
     });

     defineRule('esExterno', (value: string, [checked]:any[]) => {
        if(checked._value==true && (value==undefined || value=='')){
            return 'El campo es obligatorio'
        }else{
            return true
        } 
     });

    //  otra materia 
     defineRule('otraMateria', (value: string, [materia]:any[]) => {
        if(materia._value==13&& (value==undefined || value=='')){
            return 'El campo es obligatorio'
        }else{
            return true
        } 
     });


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

