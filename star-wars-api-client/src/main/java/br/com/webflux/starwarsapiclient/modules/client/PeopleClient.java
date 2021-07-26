package br.com.webflux.starwarsapiclient.modules.client;

import br.com.webflux.starwarsapiclient.config.ConfigProperties;
import br.com.webflux.starwarsapiclient.config.exception.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PeopleClient {

    private static final Integer MIN_PAGE = 1;

    @Autowired
    private WebClient webClient;

    @Autowired
    private ConfigProperties properties;

    @Autowired
    private ObjectMapper objectMapper;

    public Flux<Object> findAllPeople(Integer page) {
        var uri = buildUri(page);
        logRequest(uri);
        return webClient
            .get()
            .uri(uri)
            .retrieve()
            .onStatus(HttpStatus::isError, this::handleError)
            .bodyToFlux(Object.class)
            .doOnNext(this::logResponse);
    }

    private String buildUri(Integer page) {
        if (!ObjectUtils.isEmpty(page) && page > MIN_PAGE) {
            return properties.getPeople().concat("/?page=").concat(page.toString());
        }
        return properties.getPeople();
    }

    private Mono<? extends Throwable> handleError(final ClientResponse clientResponse) {
        return clientResponse
            .bodyToMono(String.class)
            .flatMap(errorBody -> Mono.error(new ValidationException("Ocorreu um erro ao tentar realizar a requisição.")));
    }

    private void logRequest(String uri) {
        log.info("Chamando o endpoint da API de personagens: {}.", uri);
    }

    private void logResponse(Object data) {
        try {
            log.info("Retornando dados: {}", objectMapper.writeValueAsString(data));
        } catch (Exception ex) {
            log.error("Erro ao tentar processar dados da requisição: ", ex);
        }
    }
}
