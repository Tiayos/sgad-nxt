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
        <FText id="correoDestinatarioLbl" as="h6" variant="bodyMd" font-weight="semibold">Subir Documentos:</FText>
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
<!--          <template #content>-->
<!--            <div class="p-fileupload-content">-->
<!--              <ul class="p-fileupload-files">-->
<!--                <FVerticalStack gap="4">-->
<!--                <li v-for="(file, index) in files" :key="file.name">-->
<!--                    <FFormLayout>-->
<!--                      <FFormLayoutGroup condensed>-->
<!--                        <FText class="pi pi-file p-fileupload-filename" as="h1" variant="bodyLg" font-weight="regular">{{ file.name }}</FText>-->
<!--                          <FButton-->
<!--                              class="p-fileupload-delete-button"-->
<!--                              @click="removeFile(index)"-->
<!--                              :icon="TrashCanSolid"-->
<!--                          >-->
<!--                          </FButton>-->
<!--                      </FFormLayoutGroup>-->
<!--                    </FFormLayout>-->
<!--                </li>-->

<!--                </FVerticalStack>-->

<!--              </ul>-->
<!--            </div>-->
<!--          </template>-->
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
  files,
  filesError,
  resetFiles,
  ///--------------------Service
  getUsrLogin,
  saveBitacoraExterna,
  editBitacoraExterna,
  getBitacorasById,
  sendEmailUsuarioExterno
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
  resetCorreoRemitente();
  resetCorreoDestinatario();
  resetAsunto();
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
  try {
    bitacoraExterna.value.nombres_remitente = nombreRemitente.value;
    bitacoraExterna.value.apellidos_remitente = apellidoRemitente.value;
    bitacoraExterna.value.asunto = asunto.value;
    bitacoraExterna.value.correo_remitente = correoRemitente.value;
    bitacoraExterna.value.correo_destinatario = correoDestinatario.value;
    bitacoraExterna.value.adicionado = bitacoraExterna.value.correo_remitente;

    await saveBitacoraExterna(bitacoraExterna.value);
    mostrarMsgCorrecto.value = true;
    mensajeToast.value = 'Se envió correctamente el documento';

    //* Send Email
    await sendEmailUsuarioExterno(bitacoraExterna.value)
    limpiar();

  }catch (e) {
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
