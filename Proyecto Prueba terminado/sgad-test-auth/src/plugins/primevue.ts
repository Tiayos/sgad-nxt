import {defineNuxtPlugin} from '#app'

import Freya from '@ups-dev/freya-primevue';
import PrimeVue from 'primevue/config'
import Ripple from 'primevue/ripple'
import Tooltip from 'primevue/tooltip'
import StyleClass from 'primevue/styleclass'
import BadgeDirective from 'primevue/badgedirective'
import ToastService from 'primevue/toastservice'
import ConfirmationService from 'primevue/confirmationservice'
import Row from 'primevue/row'
import Badge from 'primevue/badge'
import Toast from 'primevue/toast'
import Panel from 'primevue/panel'
import Button from 'primevue/button'
import Dialog from 'primevue/dialog'
import Column from 'primevue/column'
import Toolbar from 'primevue/toolbar'
import Dropdown from 'primevue/dropdown'
import Textarea from 'primevue/textarea'
import Paginator from 'primevue/paginator'
import InputText from 'primevue/inputtext'
import InputNumber from 'primevue/inputnumber'
import DataTable from 'primevue/datatable'
import ColumnGroup from 'primevue/columngroup'
import MultiSelect from 'primevue/multiselect'
import ConfirmDialog from 'primevue/confirmdialog'
import CheckBox from 'primevue/checkbox'
import Image from 'primevue/image';
import Divider from 'primevue/divider';
import Splitter from 'primevue/splitter';
import SplitterPanel from 'primevue/splitterpanel';
import RadioButton from 'primevue/radiobutton'
import DataView from 'primevue/dataview';
import DataViewLayoutOptions from 'primevue/dataviewlayoutoptions'
import FileUpload from 'primevue/fileupload';
import Card from 'primevue/card';

export default defineNuxtPlugin(({vueApp: app}) => {
    app.use(PrimeVue, {
        ripple: false,
        unstyled: true,
        pt: Freya
    })

    // Directives
    app.directive('ripple', Ripple)
    app.directive('tooltip', Tooltip)
    app.directive('badge', BadgeDirective)
    app.directive('styleclass', StyleClass)

    // Services
    app.use(ToastService)
    app.use(ConfirmationService)

    // Components
    app.component('Row', Row)
    app.component('Badge', Badge)
    app.component('Toast', Toast)
    app.component('Panel', Panel)
    app.component('Button', Button)
    app.component('Dialog', Dialog)
    app.component('Column', Column)
    app.component('Toolbar', Toolbar)
    app.component('Dropdown', Dropdown)
    app.component('Textarea', Textarea)
    app.component('Paginator', Paginator)
    app.component('InputText', InputText)
    app.component('InputNumber', InputNumber)
    app.component('DataTable', DataTable)
    app.component('ColumnGroup', ColumnGroup)
    app.component('MultiSelect', MultiSelect)
    app.component('ConfirmDialog', ConfirmDialog)
    app.component('Card', Card)
    app.component('CheckBox', CheckBox)
    app.component('Image', Image)
    app.component('Divider', Divider)
    app.component('Splitter', Splitter)
    app.component('SplitterPanel', SplitterPanel)
    app.component('RadioButton', RadioButton)
    app.component('DataView', DataView)
    app.component('DatDataViewLayoutOptionsaView', DataViewLayoutOptions)
    app.component('FileUpload', FileUpload)


})