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

        <RecaptchaV2
            @widget-id="handleWidgetId"
            @error-callback="handleErrorCalback"
            @expired-callback="handleExpiredCallback"
            @load-callback="handleLoadCallback"
        />

      </FVerticalStack>
    </FCard>
    <FPageActions :primaryAction="{content: 'Enviar documento',
    icon: PlusSolid,
    disabled: captchaValue==false,
    onAction:() => onSubmit(),
    }" :secondaryActions="[{content: 'Limpiar'}]"/>

    <FModal
        v-model="submitModal"
        title=""
        title-hidden
        large
        :primaryAction="{
        content: 'Enviar documento',
        onAction: handleChangeSubmitModal,
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
import { RecaptchaV2 } from "vue3-recaptcha-v2";
import type {Persona} from "~/models/Sumilla.model";

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
  captchaValue,
  captchaValueError,
  ///--------------------Service
  getUsrLogin,
} = useBitacoraExternaComposable()
const submitModal = ref<boolean>(false);
const { data: userLogin } = useSessionStorage<Persona>("userLogin");
const { data } = useAuth();

const handleWidgetId = (widgetId: number) => {
  console.log("Widget ID: ", widgetId);
};
const handleErrorCalback = () => {
  console.log("Error callback");
};
const handleExpiredCallback = () => {
  console.log("Expired callback");
  captchaValue.value = false;
};
const handleLoadCallback = (response: string) => {
  // console.log("Load callback", response);
  captchaValue.value = response.length>0 ? true : false;
};

const handleChangeSubmitModal = () =>{
  submitModal.value = !submitModal.value;
}

const onSubmit = handleSubmit( async(values:any) => {
  userLogin.value = await getUsrLogin(data.value?.user?.email!);
  bitacoraExterna.value.nombres_remitente = nombreRemitente.value;
  bitacoraExterna.value.apellidos_remitente = apellidoRemitente.value;
  bitacoraExterna.value.asunto = asunto.value;
  bitacoraExterna.value.correo_remitente = correoRemitente.value;
  bitacoraExterna.value.correo_destinatario = correoDestinatario.value;
  // bitacoraExterna.value.adicionado = userLogin.value.

  console.log('funciona');
});

auth:false;

</script>
<style lang="css">
</style>
