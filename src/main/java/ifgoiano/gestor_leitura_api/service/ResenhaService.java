package ifgoiano.gestor_leitura_api.service;

import java.util.List;
import java.util.logging.Logger;

import ifgoiano.gestor_leitura_api.dto.request.ResenhaRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.ResenhaResponseDTO;
import ifgoiano.gestor_leitura_api.mapper.ResenhaMapper;
import ifgoiano.gestor_leitura_api.model.Resenha;
import ifgoiano.gestor_leitura_api.repository.ResenhaRepository;

public class ResenhaService {
    private static final Logger logger = Logger.getLogger(ResenhaService.class.getName());
    private final ResenhaRepository repository;
    private final ResenhaMapper mapper;

    public ResenhaService(ResenhaMapper mapper, ResenhaRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public ResenhaResponseDTO fingById(Long id) {
        logger.info(() -> "Buscando resenha de id" + id);
        Resenha existing = repository.findByIdOrThrow(id);
        return mapper.toResponse(existing);
    }

    public List<ResenhaResponseDTO> listByLivro(Long livroId) {
        logger.info(() -> "Listando resenha por livro" + livroId);
        List<Resenha> resenhas = repository.findByLivroId(livroId);
        return mapper.toListResponse(resenhas);
    }

    public ResenhaResponseDTO create(ResenhaRequestDTO dto) {
        logger.info(() -> "Criando resenha do livro" + dto.idLivro());
        Resenha novo = mapper.toEntity(dto);
        Resenha salvo = repository.save(novo);
        return mapper.toResponse(salvo);
    }

    public ResenhaResponseDTO update(Long id, ResenhaRequestDTO dto) {
        logger.info(() -> "Atualizando resenha do livro" + dto.idLivro());
        repository.findByIdOrThrow(id);
        Resenha atualizar = mapper.toEntity(dto);
        atualizar.setId(id);
        Resenha atualizada = repository.save(atualizar);
        return mapper.toResponse(atualizada);
    }

    public void delete(Long id) {
        logger.info(() -> "Delete resenha " + id);
        Resenha existing = repository.findByIdOrThrow(id);
        repository.delete(existing);
    }
}
