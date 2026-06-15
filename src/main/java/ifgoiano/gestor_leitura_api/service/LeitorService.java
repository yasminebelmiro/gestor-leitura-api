package ifgoiano.gestor_leitura_api.service;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import ifgoiano.gestor_leitura_api.dto.request.LeitorRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.LeitorResponseDTO;
import ifgoiano.gestor_leitura_api.dto.response.MetaAnualResponseDTO;
import ifgoiano.gestor_leitura_api.exceptions.MetaAnualNotFoundException;
import ifgoiano.gestor_leitura_api.mapper.LeitorMapper;
import ifgoiano.gestor_leitura_api.mapper.MetaAnualMapper;
import ifgoiano.gestor_leitura_api.model.Leitor;
import ifgoiano.gestor_leitura_api.model.MetaAnual;
import ifgoiano.gestor_leitura_api.repository.LeitorRepository;

@Service
public class LeitorService {
    private static final Logger logger = Logger.getLogger(LeitorService.class.getName());
    private final LeitorRepository repository;
    private final LeitorMapper mapper;
    private final MetaAnualMapper metaAnualMapper;

    public LeitorService(LeitorRepository repository, LeitorMapper mapper, MetaAnualMapper metaAnualMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.metaAnualMapper = metaAnualMapper;
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
    //TODO: add seervice addEstante

    public MetaAnualResponseDTO buscarMetaPorAno(Long id, int ano) {
        logger.info(() -> "Buscando meta por ano de " + ano);
        Leitor leitor = repository.findByIdOrThrow(id);
        MetaAnual meta = leitor.buscarMetaPorAno(ano)
                .orElseThrow(() -> new MetaAnualNotFoundException("Meta referente ao ano " + ano + " não encontrada"));
        return metaAnualMapper.toResponse(meta);
    }

    public boolean verificarMetaBatida(Long id, int ano) {
        logger.info(() -> "Verificando se meta do ano " + ano + " foi batida.");
        Leitor leitor = repository.findByIdOrThrow(id);
        return leitor.verificarMetaBatida(ano);
    }

    public Double calcularProgressoGeral(Long id, int ano) {
        logger.info(() -> "Calculando progresso de leitor " + id);
        Leitor leitor = repository.findByIdOrThrow(id);
        return leitor.calcularProgressoGeral();
    }

    public long contarLivrosLidosNoAno(Long id, int ano) {
        logger.info(() -> "Calculando livros lidos referente ao ano " + ano + " do leitor " + id);
        Leitor leitor = repository.findByIdOrThrow(id);
        return leitor.contarLivrosLidosNoAno(ano);
    }

    public void recalcularMetaDoAno(Long id, int ano) {
        logger.info(() -> "Verificando se meta do ano " + ano + " foi batida.");
        Leitor leitor = repository.findByIdOrThrow(id);
        leitor.recalcularMetaDoAno(ano);
    }
}
