package ifgoiano.gestor_leitura_api.service;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import ifgoiano.gestor_leitura_api.dto.request.LeitorRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.LeitorResponseDTO;
import ifgoiano.gestor_leitura_api.mapper.LeitorMapper;
import ifgoiano.gestor_leitura_api.model.Leitor;
import ifgoiano.gestor_leitura_api.repository.LeitorRepository;

@Service
public class LeitorService {
    private static final Logger logger = Logger.getLogger(LeitorService.class.getName());
    private final LeitorRepository repository;
    private final LeitorMapper mapper;

    public LeitorService(LeitorRepository repository, LeitorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public LeitorResponseDTO findById(Long id) {
        logger.info("Buscando leitor de id :" + id);
        Leitor leitor = repository.findByIdOrThrow(id);
        return mapper.toResponse(leitor);
    }

    public LeitorResponseDTO findByEmail(String email) {
        logger.info("Buscando leitor de email :" + email);
        Leitor leitor = repository.findByEmailIgnoreCase(email);
        return mapper.toResponse(leitor);
    }

    public LeitorResponseDTO create(LeitorRequestDTO dto) {
        logger.info(() -> "Criando leitor: " + dto.nickName());
        Leitor novo = mapper.toEntity(dto);
        Leitor salvo = repository.save(novo);
        return mapper.toResponse(salvo);
    }

    public LeitorResponseDTO update(Long id, LeitorRequestDTO dto) {
        logger.info(() -> "Atualizando leitor: " + dto.nickName());
        repository.findByIdOrThrow(id);
        Leitor atualizar = mapper.toEntity(dto);
        atualizar.setId(id);
        Leitor atualizado = repository.save(atualizar);
        return mapper.toResponse(atualizado);
    }

    public void detele(Long id) {
        logger.info(() -> "Deletando leitor: " + id);
        Leitor leitor = repository.findByIdOrThrow(id);
        repository.delete(leitor);
    }

}
