package ifgoiano.gestor_leitura_api.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import ifgoiano.gestor_leitura_api.dto.request.RegistroLeituraRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.RegistroLeituraResponseDTO;
import ifgoiano.gestor_leitura_api.mapper.RegistroLeituraMapper;
import ifgoiano.gestor_leitura_api.model.RegistroLeitura;
import ifgoiano.gestor_leitura_api.repository.ItemEstanteRepository;
import ifgoiano.gestor_leitura_api.repository.RegistroLeituraRepository;

@Service
public class RegistroLeituraService {

    private static final Logger logger = Logger.getLogger(LivroService.class.getName());

    private final RegistroLeituraMapper mapper;
    private final RegistroLeituraRepository repository;
    private final ItemEstanteRepository itemRepository;

    public RegistroLeituraService(RegistroLeituraMapper mapper, RegistroLeituraRepository repository,
            ItemEstanteRepository itemRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.itemRepository = itemRepository;
    }

    public RegistroLeituraResponseDTO findById(Long id) {
        logger.info(() -> "Listando registro " + id);
        RegistroLeitura registro = repository.findByIdOrThrow(id);
        return mapper.toResponse(registro);
    }

    public List<RegistroLeituraResponseDTO> listAllOrdened(Long itemId) {
        logger.info(() -> "Listando registros");

        List<RegistroLeitura> registros = repository.findByItemIdOrderByDataAsc(itemId);
        return mapper.tResponseList(registros);
    }

    public RegistroLeituraResponseDTO create(RegistroLeituraRequestDTO dto) {
        logger.info(() -> "Criando registro na estante " + dto.idItemEstante());
        RegistroLeitura novo = mapper.toEntity(dto);
        RegistroLeitura salvo = repository.save(novo);
        return mapper.toResponse(salvo);
    }

    public RegistroLeituraResponseDTO update(long id, RegistroLeituraRequestDTO dto) {
        logger.info(() -> "Atualizando registro " + id);
        repository.findByIdOrThrow(id);
        RegistroLeitura atualizar = mapper.toEntity(dto);
        atualizar.setId(id);
        RegistroLeitura atualizado = repository.save(atualizar);
        return mapper.toResponse(atualizado);

    }

    public void delete(long id) {
        logger.info(() -> "Deletando registro " + id);
        RegistroLeitura existing = repository.findByIdOrThrow(id);
        repository.delete(existing);

    }

    public void atualizarProgresso(Long itemId, Long registroId, int pagina, String comentario) {
        RegistroLeitura registro = repository.findByIdOrThrow(registroId);
        if (registro.getItem().getId().equals(itemId)) {
            registro.atualizarProgresso(pagina, comentario);
        }
    }

}
