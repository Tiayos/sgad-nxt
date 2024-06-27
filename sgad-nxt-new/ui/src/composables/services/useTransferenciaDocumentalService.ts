
export const useTransferenciaDocumentalService = () => {
    const config = useRuntimeConfig()
    const apiUrl = `${config.public.SGAD_TRANSFERENCIA}`

    const saveTransferencia = async(fechaInicio:string, fechaFin:string, perCodigoDestinatarioGW:number, perCodigoResponsable: number)=>{
        try {
            await $fetch(`${apiUrl}?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}&perCodigoDestinatarioGW=${perCodigoDestinatarioGW}&perCodigoResponsable=${perCodigoResponsable}`,
            {
                method: 'POST',
            })
        } catch (error) {
            throw new Error("Error al guardar la transferencia");
        }
    }

    return {
        saveTransferencia,
    }
}
