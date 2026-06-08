 package ifgoiano.gestor_leitura_api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import ifgoiano.gestor_leitura_api.dto.request.ItemEstanteRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.ItemEstanteResponseDTO;
import ifgoiano.gestor_leitura_api.dto.response.RegistroLeituraResponseDTO;
import ifgoiano.gestor_leitura_api.exceptions.EstanteNotFoundException;
import ifgoiano.gestor_leitura_api.mapper.ItemEstanteMapper;
import ifgoiano.gestor_leitura_api.mapper.RegistroLeituraMapper;
import ifgoiano.gestor_leitura_api.model.Estante;
import ifgoiano.gestor_leitura_api.model.ItemEstante;
import ifgoiano.gestor_leitura_api.model.RegistroLeitura;
import ifgoiano.gestor_leitura_api.repository.EstanteRepository;
import ifgoiano.gestor_leitura_api.repository.ItemEstanteRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemEstanteService {
    private static final Logger logger = Logger.getLogger(ItemEstanteService.class.getName());
    private final ItemEstanteRepository itemEstanteRepository;
    private final EstanteRepository estanteRepository;
    private final ItemEstanteMapper mapper;
    private final RegistroLeituraMapper leituraMapper;

    public ItemEstanteService(EstanteRepository estanteRepository, ItemEstanteRepository itemEstanteRepository,
            RegistroLeituraMapper leituraMapper, ItemEstanteMapper mapper) {
        this.estanteRepository = estanteRepository;
        this.itemEstanteRepository = itemEstanteRepository;
        this.leituraMapper = leituraMapper;
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

    public ItemEstanteResponseDTO create(ItemEstanteRequestDTO dto) {
        logger.info(() -> "Add livro: " + dto.livro() + " a estante");
        ItemEstante novo = mapper.toEntity(dto);
        ItemEstante salvo = itemEstanteRepository.save(novo);
        return mapper.toResponse(salvo);

    }

    public ItemEstanteResponseDTO upadate(Long id, ItemEstanteRequestDTO dto) {
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

    @Transactional
    public ItemEstanteResponseDTO moverParaOutraEstante(Long idItemEstante, Long idLeitor, Long idNovaEstante) {
        logger.info(() -> "Movendo item " + idItemEstante + " para a estante " + idNovaEstante);

        ItemEstante item = itemEstanteRepository.findByIdOrThrow(idItemEstante);

        if (!item.getEstante().getLeitor().getId().equals(idLeitor)) {
            throw new SecurityException("Este item não pertence ao seu usuário.");
        }

        Estante novaEstante = estanteRepository.findByIdAndLeitorId(idNovaEstante, idLeitor);
        if (novaEstante == null) {
            throw new EstanteNotFoundException("Nova estante não encontrada.");
        }

        item.moverParaOutraEstante(novaEstante);
        ItemEstante salvo = itemEstanteRepository.save(item);
        return mapper.toResponse(salvo);
    }

    @Transactional
    public void marcarComoAbandonado(Long id) {
        logger.info(() -> "Atualizando o item " + id + "como abandondo");
        ItemEstante item = itemEstanteRepository.findByIdOrThrow(id);
        item.marcarComoAbandonado();
        itemEstanteRepository.save(item);

    }

    @Transactional
    public void marcarComoLido(Long id) {
        logger.info(() -> "Atualizando o item " + id + "como lido");
        ItemEstante item = itemEstanteRepository.findByIdOrThrow(id);
        item.marcarComoLido();
        itemEstanteRepository.save(item);

    }

    @Transactional
    public void marcarComoQueroLer(Long id) {
        logger.info(() -> "Atualizando o item " + id + "como quero ler");
        ItemEstante item = itemEstanteRepository.findByIdOrThrow(id);
        item.marcarComoQueroLer();
        itemEstanteRepository.save(item);
    }

    @Transactional
    public void marcarComoLendo(Long id) {
        logger.info(() -> "Atualizando o item " + id + "como lendo");
        ItemEstante item = itemEstanteRepository.findByIdOrThrow(id);
        item.marcarComoLendo();
        itemEstanteRepository.save(item);
    }

    @Transactional
    public RegistroLeituraResponseDTO adicionarRegistroLeitura(Long idItemEstante, LocalDate data, int paginaAtual,
            String comentario) {
        logger.info(() -> "Criando registro de leituta");
        ItemEstante item = itemEstanteRepository.findByIdOrThrow(idItemEstante);
        RegistroLeitura registro = item.adicionarRegistroLeitura(data, paginaAtual, comentario);
        itemEstanteRepository.save(item);
        return leituraMapper.toResponse(registro);
    }

    public int calcularDiasDeLeitura(Long id) {
        logger.info(() -> "Calculando dias de leitura");
        ItemEstante item = itemEstanteRepository.findByIdOrThrow(id);
        return item.calcularDiasDeLeitura();
    }

    public int buscarPaginaAtual(Long id) {
        logger.info(() -> "Buscando página atual da leitura");
        ItemEstante item = itemEstanteRepository.findByIdOrThrow(id);
        return item.getPaginaAtualConsiderada();
    }
}
