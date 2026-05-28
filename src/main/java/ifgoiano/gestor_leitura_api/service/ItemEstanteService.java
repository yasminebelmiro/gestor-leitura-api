package ifgoiano.gestor_leitura_api.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import ifgoiano.gestor_leitura_api.dto.request.ItemEstanteRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.ItemEstanteResponseDTO;
import ifgoiano.gestor_leitura_api.exceptions.ItemEstanteNotFoundException;
import ifgoiano.gestor_leitura_api.mapper.ItemEstanteMapper;
import ifgoiano.gestor_leitura_api.model.ItemEstante;
import ifgoiano.gestor_leitura_api.repository.ItemEstanteRepository;

@Service
public class ItemEstanteService {
    private static final Logger logger = Logger.getLogger(ItemEstanteService.class.getName());
    private final ItemEstanteRepository itemEstanteRepository;
    private final ItemEstanteMapper mapper;

    public ItemEstanteService(ItemEstanteRepository itemEstanteRepository, ItemEstanteMapper mapper) {
        this.itemEstanteRepository = itemEstanteRepository;
        this.mapper = mapper;
    }

    public ItemEstanteResponseDTO findById(Long id) {
        logger.info(() -> "Buscando item de id: " + id);
        ItemEstante item = itemEstanteRepository.findByIdOrThrow(id);
        return mapper.toResponse(item);
    }

    public List<ItemEstanteResponseDTO> findAllByEstanteId(Long estanteId) {
        logger.info(() -> "Buscando todos os itens da estante: " + estanteId);
        List<ItemEstante> itens = itemEstanteRepository.findByEstanteId(estanteId);
        return mapper.toResponseList(itens);
    }

    public ItemEstanteResponseDTO create (ItemEstanteRequestDTO dto){
         logger.info(() -> "Add livro: " + dto.livro() + " a estante");
         ItemEstante novo = mapper.toEntity(dto);
         ItemEstante salvo = itemEstanteRepository.save(novo);
         return mapper.toResponse(salvo);

    }

    public ItemEstanteResponseDTO upadate(Long id,ItemEstanteRequestDTO dto) {
        logger.info(() -> "Atualizando item de id: " + dto.id());
        itemEstanteRepository.findByIdOrThrow(id);
        ItemEstante atualizar = mapper.toEntity(dto);
        atualizar.setId(id);
        ItemEstante atualizada = itemEstanteRepository.save(atualizar);
        return mapper.toResponse(atualizada);
    }

    public void delete(Long id) {
        ItemEstante existing = itemEstanteRepository.findByIdOrThrow(id);
        itemEstanteRepository.delete(existing);
    }
}
