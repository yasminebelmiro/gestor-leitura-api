package ifgoiano.gestor_leitura_api.service;

import java.util.List;
import java.util.logging.Logger;

import ifgoiano.gestor_leitura_api.dto.request.EstanteResquestDTO;
import ifgoiano.gestor_leitura_api.dto.response.EstanteResponseDTO;
import ifgoiano.gestor_leitura_api.mapper.EstanteMapper;
import ifgoiano.gestor_leitura_api.model.Estante;
import ifgoiano.gestor_leitura_api.repository.EstanteRepository;
import ifgoiano.gestor_leitura_api.repository.LivroRepository;

public class EstanteService {
    private static final Logger logger = Logger.getLogger(EstanteService.class.getName());
    private final EstanteRepository estanteRepository;
    private final LivroRepository livroRepository;
    private final EstanteMapper mapper;

    public EstanteService(EstanteRepository estanteRepository, LivroRepository livroRepository, EstanteMapper mapper) {
        this.estanteRepository = estanteRepository;
        this.livroRepository = livroRepository;
        this.mapper = mapper;
    }

    public EstanteResponseDTO findById(Long id, Long leitorId) {
        logger.info(() -> "Buscando estante " + id);
        Estante estante = estanteRepository.findByIdAndLeitorId(id, leitorId);
        return mapper.toResponse(estante);
    }

    public List<EstanteResponseDTO> findAllByLeitorId(Long leitorId) {
        logger.info(() -> "Buscando estantes de leitor " + leitorId);
        List<Estante> estantes = estanteRepository.findByLeitorId(leitorId);
        return mapper.toResponseList(estantes);
    }

    public EstanteResponseDTO create(EstanteResquestDTO dto) {
        logger.info(() -> "Criando estante" + dto.name());
        Estante novaEstante = mapper.toEntity(dto);
        Estante salva = estanteRepository.save(novaEstante);
        return mapper.toResponse(salva);
    }

    public EstanteResponseDTO update(EstanteResquestDTO dto) {
        logger.info(() -> "Criando estante " + dto.name());
        Estante existing = estanteRepository.findByIdAndLeitorId(dto.id(), dto.leitorId());
        Estante atualizar = mapper.toEntity(dto);
        atualizar.setId(dto.id());
        Estante atualizada = estanteRepository.save(atualizar);
        return mapper.toResponse(existing);
    }

    public void delete(Long id, Long leitorId) {
        logger.info(() -> "Deletando estante " + id);
        Estante existing = estanteRepository.findByIdAndLeitorId(id, leitorId);
        estanteRepository.delete(existing);
    }
}
