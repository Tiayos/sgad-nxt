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

        <FText id="nombreOrganizacionLbl" as="h6" variant="bodyMd">Nombre de la organización:</FText>
        <FTextField
            id="nombreOrganizacionTxt"
            v-model="nombreOrganizacion"
            :error="nombreOrganizacionError"
        />

        <FText id="asuntoLbl" as="h6" variant="bodyMd">Asunto del trámite:</FText>
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

        <FText id="sedeLbl" as="h6" variant="bodyMd">Sede:</FText>
        <Dropdown
                v-model="sede"
                :options="sedeList"
                optionLabel="label"
                optionValue="value"
                placeholder="Seleccione"
                :style="[sedeError != null ? { 'border-color': '#FF6767' } : {}]"
            />

        <FText id="apellidosDestinatarioLbl" as="h6" variant="bodyMd">Nombres destinatario:</FText>
        <FTextField
            id="nombreDestinatarioTxt"
            v-model="nombreDestinatario"
            rules="required"
            :error="nombreDestinatarioError"
        />
        <FText id="apellidosDestinatarioLbl" as="h6" variant="bodyMd">Apellidos destinario:</FText>
        <FTextField
            id="apellidosRemitenteTxt"
            v-model="apellidoDestinatario"
            :error="apellidoDestinatarioError"
        />

        <FText id="subirDocumentoLbl" as="h6" variant="bodyMd" font-weight="semibold">Subir Documentos:</FText>
        <FVerticalStack gap="4">
        </FVerticalStack>
        <FileUpload
            ref="fileUpload"
            name="file"
            accept=".pdf"
            multiple
            class="custom-file-upload"
            :chooseLabel="'Seleccionar archivos'"
            :onSelect="handleFileSelect"
            :key="uploadKey"
        >
        </FileUpload>
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
    }" :secondaryActions="[{content: 'Limpiar', onAction: () => limpiar(),
    }]"/>

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
  <FToast v-model="mostrarMsgError" :content=mensajeToast error :duration="5000" />
  <FToast v-model="mostrarMsgCorrecto" :content=mensajeToast  :duration="5000" />
</template>
<script lang="ts" setup>
import {
  FileSolid,
  PlusSolid, TrashCanSolid
} from "@ups-dev/freya-icons";
import {useBitacoraExternaComposable} from "~/composables/documentosExternos/bitacoraExternaComposable";
import { RecaptchaV2 } from "vue3-recaptcha-v2";
import type {Persona} from "~/models/Sumilla.model";
import type {BitacoraExternos} from "~/models/BitacoraExternos.model";

const {
  bitacoraExterna,
  bitacora,
  sumilla,
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
  asunto,
  asuntoError,
  resetAsunto,
  nombreOrganizacion,
  nombreOrganizacionError,
  resetNombreOrganizacion,
  nombreDestinatario,
  nombreDestinatarioError,
  resetNombreDestinatario,
  apellidoDestinatario,
  apellidoDestinatarioError,
  resetApellidoDestinatario,
  sede,
  sedeError,
  resetSede,
  sedeList,
  captchaValue,
  captchaValueError,
  files,
  filesError,
  resetFiles,
  ///--------------------Service
  getUsrLogin,
  saveBitacoraExterna,
  editBitacoraExterna,
  getBitacorasById,
  sendEmailUsuarioExterno,
  saveSumilla,
  saveSumillaExterna
} = useBitacoraExternaComposable()
const submitModal = ref<boolean>(false);
const { data: userLogin } = useSessionStorage<Persona>("userLogin");
const mostrarMsgError = ref<boolean>(false);
const mostrarMsgCorrecto = ref<boolean>(false);
const mensajeToast = ref<string>('');
const uploadKey = ref<number>(0);

const removeFile = (index:any) => {
  files.value.splice(index, 1);
}

const limpiar = () =>{
  bitacoraExterna.value = {} as BitacoraExternos;
  resetNombreRemitente();
  resetApellidoRemitente();
  resetNombreOrganizacion();
  resetAsunto();
  resetCorreoRemitente();
  resetNombreDestinatario();
  resetApellidoDestinatario();
  resetSede();
  uploadKey.value +=1;
  resetFiles();
}

const handleFileSelect = (event: any) => {
  files.value = event.files;
};

const handleWidgetId = (widgetId: number) => {
};
const handleErrorCalback = () => {
  console.log("Error callback");
};
const handleExpiredCallback = () => {
  console.log("Expired callback");
  captchaValue.value = false;
};
const handleLoadCallback = (response: any) => {
  // console.log("Load callback", response);
  captchaValue.value = response.length>0 ? true : false;
};

const handleChangeSubmitModal = () =>{
  submitModal.value = !submitModal.value;
}

const onSubmit = handleSubmit( async(values:any) => {
  console.log('object');
  try {
    // sumilla.value.codigo = 0;
    sumilla.value.responsable = {} as Persona;
    sumilla.value.numero_hojas = files.value.length;
    sumilla.value.fecha_sumilla = new Date();
    sumilla.value.hora_sumilla = new Date().getHours() + ":" + new Date().getMinutes();
    sumilla.value.sum_sede = sede.value

    switch (sede.value) {
      case 2:
      const user2 = ref<Persona>({} as Persona);
      user2.value = await getUsrLogin('larias@ups.edu.ec')
      sumilla.value.responsable = user2.value
        break;
      case 3:
      const user3 = ref<Persona>({} as Persona);
      user3.value = await getUsrLogin('amacias@ups.edu.ec');
      sumilla.value.responsable = user3.value
        break;
      case 4:
      const user4 = ref<Persona>({} as Persona);
      user4.value = await getUsrLogin('megas@ups.edu.ec');
      sumilla.value.responsable = user4.value
        break;
    }

    await saveSumillaExterna(sumilla.value);
    mostrarMsgCorrecto.value = true;
    mensajeToast.value = 'Se envió correctamente el documento';

    //* Send Email
    //await sendEmailUsuarioExterno(bitacoraExterna.value)
    limpiar();

  }catch (e) {
    console.log(e);
  }

});
</script>
<style lang="css">
.p-fileupload .p-fileupload-buttonbar {
  display: none;
}

.custom-file-upload .p-button {
  background-color: blue !important;
  border-color: blue !important;
  font-size: 16px !important; /* Ajusta el tamaño según tus necesidades */
}

.custom-file-upload .p-fileupload-choose {
  background-color: blue !important;
  border-color: blue !important;
  font-size: 16px !important; /* Ajusta el tamaño según tus necesidades */
}

.custom-file-upload .p-fileupload-choose .p-button-label {
  color: white !important; /* Cambia el color del texto si es necesario */
}
</style>
