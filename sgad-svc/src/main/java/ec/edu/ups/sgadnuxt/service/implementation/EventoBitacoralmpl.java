package ec.edu.ups.sgadnuxt.service.implementation;
import ec.edu.ups.sgadnuxt.entity.dto.SumillaDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.EstadoDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.EventoBitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadEventoBitacora;
import ec.edu.ups.sgadnuxt.exception.NotFoundException;
import ec.edu.ups.sgadnuxt.repository.IEstadoDao;
import ec.edu.ups.sgadnuxt.repository.IEventoBitacoraDao;
import ec.edu.ups.sgadnuxt.repository.ISumillaDao;
import ec.edu.ups.sgadnuxt.service.IEventoBitacoraService;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventoBitacoralmpl implements IEventoBitacoraService {
    @Autowired
    private ISumillaDao iSumillaDao;
    @Autowired
    private IEventoBitacoraDao iEventoBitacoraDao;
    @Autowired
    private IEstadoDao iEstadoDao;
    @Override
    public List<EventoBitacoraDTO> findAllEventosBitacoras() {
        return iEventoBitacoraDao.findAll()
                .stream()
                .map(EventoBitacoraDTO::toDTO)
                .toList();
    }

    @Override
    public EventoBitacoraDTO findEventosByBitCodigo(Long bitCodigo) {
        return EventoBitacoraDTO.toDTO(iEventoBitacoraDao.getEventosBitacora(bitCodigo));
    }

    @Override
    public List<EventoBitacoraDTO> findAllEventosByBitCodigo(Long bitCodigo) {
        return iEventoBitacoraDao.getAllEventosByBitCodigo(bitCodigo)
                .stream()
                .map(EventoBitacoraDTO::toDTO)
                .toList();
    }

    @Override
    public List<EstadoDTO> findAllEstados() {
        return iEstadoDao.findAll()
                .stream()
                .map(EstadoDTO::toDTO)
                .toList()
                ;
    }

    @Override
    public List<EventoBitacoraDTO> findEventosByPerCodigo(Long perCodigo) {
        List<EventoBitacoraDTO> eventoBitacoraDTOList = iEventoBitacoraDao.getAllEventosByPerCodigo(perCodigo)
                .stream()
                .map(EventoBitacoraDTO::toDTO)
                .toList();
        if(eventoBitacoraDTOList.size()!=0){
            return eventoBitacoraDTOList;
        }else{
            return eventoBitacoraDTOList;
        }
    }

    @Override
    public void saveEventoBitacora(EventoBitacoraDTO eventoBitacoraDTO) {
        //Consultamos el ultimo vigente y cambiamos el estado
        try {
            String numSumilla1;
            SgadEventoBitacora sgadEventoBitacora = iEventoBitacoraDao.getEventoVigente(eventoBitacoraDTO.bitacora().codigo());
            sgadEventoBitacora.setEbiVigencia('N');
            iEventoBitacoraDao.save(sgadEventoBitacora);

            SgadEventoBitacora sgadEventoBitacora1 = new SgadEventoBitacora(eventoBitacoraDTO);
            iEventoBitacoraDao.save(sgadEventoBitacora1);


            Long ultimoIdSumilla = eventoBitacoraDTO.bitacora().sumilla().sumCodSede(); // se cambia por el sumCodSede
            Long numeroReferenciaSumilla = iSumillaDao.completarNumSumilla(eventoBitacoraDTO.bitacora().sumilla().codigo());

            if(eventoBitacoraDTO.bitacora().sumilla().sumSede() == 2){ //cuenca
                numSumilla1 = "00"+ ultimoIdSumilla + "-00"+ numeroReferenciaSumilla + "-UPSCUE";
            } else if (eventoBitacoraDTO.bitacora().sumilla().sumSede() == 3) { //QUITO
                numSumilla1 = "00"+ ultimoIdSumilla + "-00"+ numeroReferenciaSumilla + "-UPSUIO";
            }else if (eventoBitacoraDTO.bitacora().sumilla().sumSede() == 4) { //GUAYAQUIL
                numSumilla1 = "00"+ ultimoIdSumilla + "-00"+ numeroReferenciaSumilla + "-UPSGYE";
            } else {
                numSumilla1 = null;
            }

            iSumillaDao.findById(eventoBitacoraDTO.bitacora().sumilla().codigo())
                    .map(
                            sumillaMap -> {
                                sumillaMap.setNumeroSumilla(numSumilla1);
                                return SumillaDTO.toDTO(iSumillaDao.save(sumillaMap));
                            }) .orElseThrow(
                            () -> new NotFoundException("No se encontró la sumilla con número: ")
                    );

        }catch (PersistenceException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateEventoBitacora(EventoBitacoraDTO eventoBitacoraDTO) {

    }

    @Override
    public void deleteEventoByBitCodigo(Long bitCodigo) {
        iEventoBitacoraDao.deleteEventoByBitCodigo(bitCodigo);
    }
}
