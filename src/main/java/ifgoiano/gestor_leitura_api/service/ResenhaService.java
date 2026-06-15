package ifgoiano.gestor_leitura_api.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import ifgoiano.gestor_leitura_api.dto.request.ResenhaRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.ResenhaResponseDTO;
import ifgoiano.gestor_leitura_api.exceptions.LivroNotFoundException;
import ifgoiano.gestor_leitura_api.mapper.ResenhaMapper;
import ifgoiano.gestor_leitura_api.model.Leitor;
import ifgoiano.gestor_leitura_api.model.Livro;
import ifgoiano.gestor_leitura_api.model.Resenha;
import ifgoiano.gestor_leitura_api.repository.LeitorRepository;
import ifgoiano.gestor_leitura_api.repository.LivroRepository;
import ifgoiano.gestor_leitura_api.repository.ResenhaRepository;

@Service
public class ResenhaService {
    private static final Logger logger = Logger.getLogger(ResenhaService.class.getName());
    private final ResenhaRepository repository;
    private final LivroRepository livroRepository;
    private final LeitorRepository leitorRepository;
    private final ResenhaMapper mapper;

    public ResenhaService(ResenhaRepository repository, LivroRepository livroRepository,
            LeitorRepository leitorRepository, ResenhaMapper mapper) {
        this.repository = repository;
        this.livroRepository = livroRepository;
        this.leitorRepository = leitorRepository;
        this.mapper = mapper;
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

    @Caching(evict= {
        @CacheEvict(value="mediaAvaliacoesLivro", key="#dto.googleVolumeId()"),
        @CacheEvict(value="fichaTecnicaLivro", key="dto.googleVolumeId()")
    })
    public ResenhaResponseDTO create(ResenhaRequestDTO dto) {
        logger.info(() -> "Criando resenha do livro" + dto.googleVolumeId());

        Livro livro = livroRepository.findByGoogleVolumeId(dto.googleVolumeId()).
        orElseThrow(() -> new LivroNotFoundException(dto.googleVolumeId()));

        Leitor leitor = leitorRepository.findByIdOrThrow(dto.idLeitor());
        
        Resenha novo = mapper.toEntity(dto);

        novo.setLeitor(leitor);
        novo.setLivro(livro);
        livro.adicionarResenha(novo);

        Resenha salvo = repository.save(novo);

        return mapper.toResponse(salvo);
    }

    @CacheEvict(value = {"mediaAvaliacoesLivro", "fichaTecnicaLivro"}, allEntries = true)
    public ResenhaResponseDTO update(Long id, ResenhaRequestDTO dto) {
        logger.info(() -> "Atualizando resenha do livro" + dto.googleVolumeId());
        repository.findByIdOrThrow(id);
        Resenha atualizar = mapper.toEntity(dto);
        atualizar.setId(id);
        Resenha atualizada = repository.save(atualizar);
        return mapper.toResponse(atualizada);
    }

    @CacheEvict(value = {"mediaAvaliacoesLivro", "fichaTecnicaLivro"}, allEntries = true)
    public void delete(Long id) {
        logger.info(() -> "Delete resenha " + id);
        Resenha existing = repository.findByIdOrThrow(id);
        repository.delete(existing);
    }
}
