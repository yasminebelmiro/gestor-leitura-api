package ifgoiano.gestor_leitura_api.service;

import java.util.logging.Logger;

import ifgoiano.gestor_leitura_api.dto.response.LivroResponseDTO;
import ifgoiano.gestor_leitura_api.mapper.LivroMapper;
import ifgoiano.gestor_leitura_api.model.Livro;
import ifgoiano.gestor_leitura_api.repository.LivroRepository;
import jakarta.transaction.Transactional;

public class LivroService {

    private static final Logger logger = Logger.getLogger(LivroService.class.getName());
    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;

    public LivroService(LivroRepository livroRepository,
            LivroMapper livroMapper) {
        this.livroRepository = livroRepository;
        this.livroMapper = livroMapper;
    }

    @Transactional
    public Livro create(LivroResponseDTO dto) {
        logger.info(() -> "Criando livro" + dto.titulo());
        return livroRepository.findByGoogleVolumeId(dto.googleId())
                .orElseGet(() -> {
                    Livro novaEntidade = livroMapper.toEntity(dto);
                    return livroRepository.save(novaEntidade);
                });
    }

    public void delete(String googleId){
        logger.info(() ->"Deletando livro: " + googleId);
        livroRepository.deleteByGoogleVolumeId(googleId);
    }

}
