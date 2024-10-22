package ec.edu.ups.sgadnuxt.service;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.EstadoDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.EventoBitacoraDTO;
import java.util.List;

public interface IEventoBitacoraService {
    List<EventoBitacoraDTO> findAllEventosBitacoras();
    EventoBitacoraDTO findEventosByBitCodigo(Long bitCodigo);
    List<EventoBitacoraDTO> findAllEventosByBitCodigo(Long bitCodigo);
    List<EstadoDTO> findAllEstados();
    List<EventoBitacoraDTO> findEventosByPerCodigo(Long perCodigo);
    void saveEventoBitacora(EventoBitacoraDTO eventoBitacoraDTO);
    void updateEventoBitacora(EventoBitacoraDTO eventoBitacoraDTO);
    void deleteEventoByBitCodigo(Long bitCodigo);
}

