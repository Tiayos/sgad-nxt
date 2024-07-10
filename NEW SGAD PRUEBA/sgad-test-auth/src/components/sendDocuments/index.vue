<template>
  <FPage
         title="Sistema de Gestión documental"
         subtitle="Registra la bitácora que se enviará al usuario de la UPS"
         :pagination="{ hasPrevious: true, hasNext: true}">

    <FCard sectioned title="">
      <FVerticalStack gap="4">
        <FText id="nombresRemitenteLbl" as="h6" variant="bodyMd">Nombres remitente:</FText>
        <FTextField
            id="nombresRemitenteTxt"
            v-model="nombreRemitente"
            rules="required"
            :error="nombreRemitenteError"
        />
        <FText id="apellidosRemitenteLbl" as="h6" variant="bodyMd">Apellidos remitente:</FText>
        <FTextField
            id="apellidosRemitenteTxt"
            v-model="apellidoRemitente"
            :error="apellidoRemitenteError"
        />
        <FText id="asuntoLbl" as="h6" variant="bodyMd">Asunto:</FText>
        <FTextField
            id="asuntoTxt"
            v-model="asunto"
            :error="asuntoError"
        />
        <FText id="correoRemitenteLbl" as="h6" variant="bodyMd">Correo del remitente:</FText>
        <FTextField
            id="correoRemitenteTxt"
            v-model="correoRemitente"
            :error="correoRemitenteError"
        />
        <FText id="correoDestinatarioLbl" as="h6" variant="bodyMd">Correo del destinatario:</FText>
        <FTextField
            id="correoDestinatarioTxt"
            v-model="correoDestinatario"
            :error="correoDestinatarioError"
        />
      </FVerticalStack>
    </FCard>
    <FPageActions :primaryAction="{content: 'Enviar documento', icon: PlusSolid, onAction:() => handleChangeSubmitModal()}"
                  :secondaryActions="[{content: 'Limpiar'}]"/>

    <FModal
        v-model="submitModal"
        title=""
        title-hidden
        large
        :primaryAction="{
        content: 'Enviar documento',
        onAction: onSubmit,
      }"
        :secondaryActions="[
        {
          content: 'Cancelar',
          onAction: handleChangeSubmitModal,
        },
      ]"
    >

      

    </FModal>

  </FPage>
</template>
<script lang="ts" setup>
import {
  PlusSolid
} from "@ups-dev/freya-icons";
import {useBitacoraExternaComposable} from "~/composables/documentosExternos/bitacoraExternaComposable";

const {
  bitacoraExterna,
  handleSubmit,
  nombreRemitente,
  nombreRemitenteError,
  resetNombreRemitente,
  apellidoRemitente,
  apellidoRemitenteError,
  resetApellidoRemitente,
  correoRemitente,
  correoRemitenteError,
  resetCorreoRemitente,
  correoDestinatario,
  correoDestinatarioError,
  resetCorreoDestinatario,
  asunto,
  asuntoError,
  resetAsunto,
} = useBitacoraExternaComposable()
const submitModal = ref<boolean>(false);

const handleChangeSubmitModal = () =>{
  submitModal.value = !submitModal.value;
}

const onSubmit = handleSubmit( (values:any) => {
  console.log('funciona');
});


</script>
<style lang="css">
</style>
