package ifgoiano.gestor_leitura_api.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import ifgoiano.gestor_leitura_api.dto.request.MetaAnualRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.MetaAnualResponseDTO;
import ifgoiano.gestor_leitura_api.exceptions.MetaAnualNotFoundException;
import ifgoiano.gestor_leitura_api.mapper.MetaAnualMapper;
import ifgoiano.gestor_leitura_api.model.Leitor;
import ifgoiano.gestor_leitura_api.model.MetaAnual;
import ifgoiano.gestor_leitura_api.repository.LeitorRepository;
import ifgoiano.gestor_leitura_api.repository.MetaAnualRepository;

@Service
public class MetaAtualService {

    private static final Logger logger = Logger.getLogger(MetaAtualService.class.getName());

    private final MetaAnualMapper mapper;
    private final MetaAnualRepository repository;
    private final LeitorRepository leitorRepository;

    public MetaAtualService(MetaAnualMapper mapper, MetaAnualRepository repository, LeitorRepository leitorRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.leitorRepository = leitorRepository;
    }

    public MetaAnualResponseDTO findById(Long id) {
        logger.info("Buscando meta por id " + id);
        MetaAnual existing = repository.findByIdOrThrow(id);
        return mapper.toResponse(existing);
    }


    public List<MetaAnualResponseDTO> listByLeitor(Long id) {
        logger.info("Listando meta por id do leitor " + id);
        List<MetaAnual> metas = repository.findByLeitorId(id);
        return mapper.toListResponse(metas);
    }

    public MetaAnualResponseDTO create(MetaAnualRequestDTO dto) {
        logger.info("Criando MetaAnual do ano de " + dto.ano());
        Leitor leitor = leitorRepository.findByIdOrThrow(dto.leitorId());
        MetaAnual novo = mapper.toEntity(dto);
        novo.setLeitor(leitor);
        leitor.adicionarMeta(novo);
        MetaAnual salvo = repository.save(novo);
        
        return mapper.toResponse(salvo);
    }

    public MetaAnualResponseDTO update(Long id, MetaAnualRequestDTO dto) {
        logger.info("Criando MetaAnual do ano de " + dto.ano());
        repository.findByIdOrThrow(id);
        MetaAnual atualizar = mapper.toEntity(dto);
        atualizar.setId(id);
        MetaAnual atualizado = repository.save(atualizar);
        return mapper.toResponse(atualizado);
    }

    public void delete(Long id) {
        logger.info("Deetando meta " + id);
        MetaAnual existing = repository.findByIdOrThrow(id);
        repository.delete(existing);
    }

}
