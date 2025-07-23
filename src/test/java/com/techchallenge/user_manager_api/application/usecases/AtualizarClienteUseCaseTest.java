package com.techchallenge.user_manager_api.application.usecases;

import com.techchallenge.user_manager_api.api.controllers.gateways.ClienteGatewayRepository;
import com.techchallenge.user_manager_api.application.mappers.UsuarioMapper;
import com.techchallenge.user_manager_api.application.usecases.cliente.AtualizarClienteUseCase;
import com.techchallenge.user_manager_api.application.usecases.presenters.ClientePresenter;
import com.techchallenge.user_manager_api.domain.dto.requests.AtualizarClienteRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.requests.EnderecoRequestDTO;
import com.techchallenge.user_manager_api.domain.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.domain.dto.response.EnderecoResponseDTO;
import com.techchallenge.user_manager_api.domain.entities.ClienteDomain;
import com.techchallenge.user_manager_api.infra.model.enums.AlergiaAlimentarEnum;
import com.techchallenge.user_manager_api.infra.model.enums.GeneroEnum;
import com.techchallenge.user_manager_api.infra.model.enums.MetodoPagamentoEnum;
import com.techchallenge.user_manager_api.infra.model.enums.TiposComidaEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarClienteUseCaseTest {

    @Mock
    private ClienteGatewayRepository clienteRepository;

    @Mock
    private ClientePresenter clientePresenter;

    @InjectMocks
    private AtualizarClienteUseCase useCase;

    @DisplayName("Deve lançar exceção quando cliente não for encontrado")
    @Test
    void deveLancarExcecaoQuandoClienteNaoForEncontrado() {
        UUID clienteId = UUID.randomUUID();

        AtualizarClienteRequestDTO dto = new AtualizarClienteRequestDTO(
                "João da Silva", // Nome
                "123.456.789-00", // CPF
                "joaodasilva@email.com", // Email
                "joaodasilva", // Login
                LocalDate.of(1990, 1, 1), // Data de nascimento
                GeneroEnum.MASCULINO, // Gênero
                "+5581999992345", // Telefone
                Set.of(TiposComidaEnum.VEGETARIANA, TiposComidaEnum.SAUDAVEL), // Preferências alimentares
                Set.of(AlergiaAlimentarEnum.AMENDOIM, AlergiaAlimentarEnum.LACTOSE), // Alergias
                MetodoPagamentoEnum.CREDITO, // Método de pagamento preferido
                true, // Notificações ativas
                List.of(new EnderecoRequestDTO(
                        "SP",                // Estado
                        "Sao Paulo",         // Cidade
                        "Jardim Paulista",   // Bairro
                        "Avenida Paulista",  // Rua
                        123,                 // Número
                        "Apt 101",           // Complemento
                        "01311-000" )));       // CEP


        when(clienteRepository.buscarClientePorId(clienteId)).thenThrow(new IllegalArgumentException("Cliente não encontrado"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            useCase.executar(clienteId, dto);
        });

        assertEquals("Cliente não encontrado", ex.getMessage());
        verify(clienteRepository, never()).alterarInformacoesDoCliente(any(), any());
    }

    @DisplayName("Deve lançar exceção quando login já estiver em uso")
    @Test
    void deveLancarExcecaoQuandoLoginForNuloOuVazio() {
        UUID clienteId = UUID.randomUUID();
        AtualizarClienteRequestDTO dto = new AtualizarClienteRequestDTO(
                "João da Silva", // Nome
                "123.456.789-00", // CPF
                "joaodasilva@email.com", // Email
                "joaosilva", // Login
                LocalDate.of(1990, 1, 1), // Data de nascimento
                GeneroEnum.MASCULINO, // Gênero
                "+5581999992345", // Telefone
                Set.of(TiposComidaEnum.VEGETARIANA, TiposComidaEnum.SAUDAVEL), // Preferências alimentares
                Set.of(AlergiaAlimentarEnum.AMENDOIM, AlergiaAlimentarEnum.LACTOSE), // Alergias
                MetodoPagamentoEnum.CREDITO, // Método de pagamento preferido
                true, // Notificações ativas
                List.of(new EnderecoRequestDTO(
                        "SP",                // Estado
                        "Sao Paulo",         // Cidade
                        "Jardim Paulista",   // Bairro
                        "Avenida Paulista",  // Rua
                        123,                 // Número
                        "Apt 101",           // Complemento
                        "01311-000" )));       // CEP


        ClienteDomain clienteDomain = new ClienteDomain(
                UUID.randomUUID(), // id
                "123.456.789-00", // cpf
                LocalDate.of(1990, 5, 15), // dataNascimento
                GeneroEnum.MASCULINO, // genero
                "(11) 98765-4321", // telefone
                Set.of(TiposComidaEnum.INDIANA, TiposComidaEnum.JAPONESA), // preferenciasAlimentares
                Set.of(AlergiaAlimentarEnum.GLUTEN, AlergiaAlimentarEnum.LACTOSE), // alergias
                MetodoPagamentoEnum.CREDITO, // metodoPagamentoPreferido
                true, // notificacoesAtivas
                "João Silva", // nome
                "joao.silva@example.com", // email
                "joaosilvaOld", // login
                "senha123" // senhaCriptografada
        );



        when(clienteRepository.buscarClientePorId(clienteId)).thenReturn(clienteDomain);
        when(clienteRepository.existsByLogin(dto.login())).thenReturn(true); // simula login já usado

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            useCase.executar(clienteId, dto);
        });

        assertEquals("Login já está em uso", ex.getMessage());
        verify(clienteRepository, never()).alterarInformacoesDoCliente(any(), any());
    }


    @DisplayName("Deve atualizar cliente com sucesso quando todos os dados são válidos")
    @Test
    void deveAtualizarClienteComSucessoQuandoDadosSaoValidos() {
        UUID clienteId = UUID.randomUUID();
        String senhaAtual = "senha123";

        ClienteDomain clienteAtual = new ClienteDomain(
                UUID.randomUUID(), // id
                "123.456.789-00", // cpf
                LocalDate.of(1990, 5, 15), // dataNascimento
                GeneroEnum.MASCULINO, // genero
                "(11) 98765-4321", // telefone
                Set.of(TiposComidaEnum.INDIANA, TiposComidaEnum.JAPONESA), // preferenciasAlimentares
                Set.of(AlergiaAlimentarEnum.GLUTEN, AlergiaAlimentarEnum.LACTOSE), // alergias
                MetodoPagamentoEnum.CREDITO, // metodoPagamentoPreferido
                true, // notificacoesAtivas
                "João Silva", // nome
                "joao.silva@example.com", // email
                "joaosilvaOld", // login
                "senha123" // senhaCriptografada
        );

        AtualizarClienteRequestDTO dto = new AtualizarClienteRequestDTO(
                "João da Silva", // Nome
                "123.456.789-00", // CPF
                "joaodasilva@email.com", // Email
                "joaosilva", // Login
                LocalDate.of(1990, 1, 1), // Data de nascimento
                GeneroEnum.MASCULINO, // Gênero
                "+5581999992345", // Telefone
                Set.of(TiposComidaEnum.VEGETARIANA, TiposComidaEnum.SAUDAVEL), // Preferências alimentares
                Set.of(AlergiaAlimentarEnum.AMENDOIM, AlergiaAlimentarEnum.LACTOSE), // Alergias
                MetodoPagamentoEnum.CREDITO, // Método de pagamento preferido
                true, // Notificações ativas
                List.of(new EnderecoRequestDTO(
                        "SP",                // Estado
                        "Sao Paulo",         // Cidade
                        "Jardim Paulista",   // Bairro
                        "Avenida Paulista",  // Rua
                        123,                 // Número
                        "Apt 101",           // Complemento
                        "01311-000" )));       // CEP

        ClienteDomain clienteAtualizado = UsuarioMapper.toClienteDomain(dto, senhaAtual);

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(
                UUID.randomUUID(),
                "João da Silva",
                "123.456.789-00",
                LocalDate.of(1990, 1, 1),
                "joaodasilva@email.com",
                "joaosilva",
                "(11) 98765-4321",
                List.of(new EnderecoResponseDTO(
                        UUID.randomUUID(),
                        "SP",
                        "Sao Paulo",
                        "Jardim Paulista",
                        "Avenida Paulista",
                        123,
                        "Apt 101",
                        "01311-000"
                ))
        );

        when(clienteRepository.buscarClientePorId(clienteId)).thenReturn(clienteAtual);
        when(clienteRepository.existsByLogin(dto.login())).thenReturn(false);
        when(clientePresenter.retornarCliente(any())).thenReturn(clienteResponseDTO);

        ClienteResponseDTO resultado = useCase.executar(clienteId, dto);

        assertEquals("João da Silva", resultado.nome());
        assertEquals("joaodasilva@email.com", resultado.email());
        assertEquals("joaosilva", resultado.login());

        ArgumentCaptor<ClienteDomain> captor = ArgumentCaptor.forClass(ClienteDomain.class);
        verify(clienteRepository).alterarInformacoesDoCliente(captor.capture(), eq(senhaAtual));

        ClienteDomain capturado = captor.getValue();
        assertEquals("João da Silva", capturado.getNome());
        assertEquals("joaodasilva@email.com", capturado.getEmail());
        assertEquals("joaosilva", capturado.getLogin());
        assertEquals(LocalDate.of(1990, 1, 1), capturado.getDataNascimento());
        assertEquals(Set.of(TiposComidaEnum.VEGETARIANA, TiposComidaEnum.SAUDAVEL), capturado.getPreferenciasAlimentares());
        assertEquals(Set.of(AlergiaAlimentarEnum.AMENDOIM, AlergiaAlimentarEnum.LACTOSE), capturado.getAlergias());
        assertEquals("+5581999992345", capturado.getTelefone());

        verify(clientePresenter).retornarCliente(capturado);
    }

    @DisplayName("Deve permitir atualização quando login está em uso mas é do próprio cliente")
    @Test
    void devePermitirAtualizacaoQuandoLoginEstaEmUsoMasEhDoProprioCliente() {
        UUID clienteId = UUID.randomUUID();
        String login = "joaosilva";
        String senhaAtual = "senha123";

        AtualizarClienteRequestDTO dto = new AtualizarClienteRequestDTO(
                "João Atualizado",
                "123.456.789-00",
                "joao.atualizado@email.com",
                login,
                LocalDate.of(1990, 1, 1),
                GeneroEnum.MASCULINO,
                "+5581999992345",
                Set.of(TiposComidaEnum.VEGETARIANA),
                Set.of(AlergiaAlimentarEnum.GLUTEN),
                MetodoPagamentoEnum.CREDITO,
                true,
                List.of(new EnderecoRequestDTO(
                        "SP", "São Paulo", "Centro", "Rua A", 100, "", "01000-000"
                ))
        );

        ClienteDomain clienteAtual = new ClienteDomain(
                clienteId,
                "123.456.789-00",
                LocalDate.of(1990, 5, 15),
                GeneroEnum.MASCULINO,
                "+5581999992345",
                Set.of(TiposComidaEnum.VEGETARIANA),
                Set.of(AlergiaAlimentarEnum.GLUTEN),
                MetodoPagamentoEnum.CREDITO,
                true,
                "João Atual",
                "joao@atual.com",
                login,
                senhaAtual
        );

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(
                clienteId,
                "João Atualizado",
                "123.456.789-00",
                LocalDate.of(1990, 1, 1),
                "joao.atualizado@email.com",
                login,
                "+5581999992345",
                List.of(new EnderecoResponseDTO(
                        UUID.randomUUID(), "SP", "São Paulo", "Centro", "Rua A", 100, "", "01000-000"
                ))
        );

        when(clienteRepository.buscarClientePorId(clienteId)).thenReturn(clienteAtual);
        when(clienteRepository.existsByLogin(login)).thenReturn(true); // login está em uso, mas é do próprio cliente
        when(clientePresenter.retornarCliente(any())).thenReturn(clienteResponseDTO);

        ClienteResponseDTO resultado = useCase.executar(clienteId, dto);

        assertEquals("João Atualizado", resultado.nome());
        verify(clienteRepository).alterarInformacoesDoCliente(any(), eq(senhaAtual));
        verify(clientePresenter).retornarCliente(any());
    }



}
