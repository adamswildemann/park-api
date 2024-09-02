package com.awildemann.demo_park_api;

import com.awildemann.demo_park_api.web.dto.ClienteCreateDTO;
import com.awildemann.demo_park_api.web.dto.ClienteResponseDTO;
import com.awildemann.demo_park_api.web.dto.PageableDTO;
import com.awildemann.demo_park_api.web.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/clientes/clientes-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clientes/clientes-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClienteIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void criarClienteComDadosValidosStatus201() {
        ClienteResponseDTO responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .bodyValue(new ClienteCreateDTO("Luis Ben", "52572604094"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ClienteResponseDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isNotNull();
        Assertions.assertThat(responseBody.getNome()).isEqualTo("Luis Ben");
        Assertions.assertThat(responseBody.getCpf()).isEqualTo("52572604094");
    }

    @Test
    public void ErroAoCriarClienteComCpfJaCadastradoStatus409() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .bodyValue(new ClienteCreateDTO("Luis Ben", "01278358064"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void ErroAoCriarClienteComDadosInvalidosStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .bodyValue(new ClienteCreateDTO("", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .bodyValue(new ClienteCreateDTO("Lui", "0000000000"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .bodyValue(new ClienteCreateDTO("Lui", "012.783.580-64"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void ErroAoCriarClienteComUsuarioNaoPermitidoStatus403() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new ClienteCreateDTO("Luis Ben", "52572604094"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void buscarClienteComIdExistenteStatus200() {
        ClienteResponseDTO responseBody = testClient
                .get()
                .uri("/api/v1/clientes/11")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClienteResponseDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isEqualTo(11);
    }

    @Test
    public void buscarClientePeloAdminComIdInexistenteStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/clientes/500")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void buscarClientePeloClienteComIdExistenteStatus403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/clientes/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "luis@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void buscarClientesComPaginacaoPeloAdminStatus200() {
        PageableDTO responseBody = testClient
                .get()
                .uri("/api/v1/clientes")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getContent().size()).isEqualTo(2);
        Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
        Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(1);

        responseBody = testClient
                .get()
                .uri("/api/v1/clientes?size=1&page=1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDTO.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
        Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
    }

    @Test
    public void buscarClientesComPaginacaoPeloClienteStatus403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/clientes")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "joao@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }
}
