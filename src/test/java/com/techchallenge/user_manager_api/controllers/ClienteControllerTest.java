//package com.techchallenge.user_manager_api.controllers;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class ClienteControllerTest {
//
//    private ClienteController clienteController;
//    private ClienteService clienteService;
//
//    private CadastroResponseDTO cadastroResponseDTO;
//    private ClienteResponseDTO clienteResponseDTO;
//
//    @BeforeEach
//    void setUp() {
//        clienteService = Mockito.mock(ClienteService.class);
//        clienteController = new ClienteController(clienteService);
//
//        cadastroResponseDTO = new CadastroResponseDTO(
//                new UsuarioResponseDTO(UUID.randomUUID(), "João da Silva", "joao@email.com", "joaodasilva"),
//                "token-jwt"
//        );
//
//        clienteResponseDTO = new ClienteResponseDTO(
//                UUID.randomUUID(),
//                "João da Silva",
//                "123.456.789-00",
//                null,
//                "joao@email.com",
//                "joaodasilva",
//                "+5581999999999",
//                List.of()
//        );
//    }
//
//    @Test
//    void deveriaCadastrarClienteComSucesso() {
//        when(clienteService.cadastrarCliente(any())).thenReturn(cadastroResponseDTO);
//
//        ResponseEntity<CadastroResponseDTO> response = clienteController.cadastrarCliente(null);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(cadastroResponseDTO, response.getBody());
//    }
//
//    @Test
//    void deveriaBuscarClientePorIdComSucesso() {
//        UUID idCliente = UUID.randomUUID();
//        when(clienteService.buscarCliente(idCliente)).thenReturn(clienteResponseDTO);
//
//        ResponseEntity<ClienteResponseDTO> response = clienteController.buscarClientePorId(idCliente);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(clienteResponseDTO, response.getBody());
//    }
//
//    @Test
//    void deveriaRetornarErroAoBuscarClientePorIdInexistente() {
//        UUID idCliente = UUID.randomUUID();
//        when(clienteService.buscarCliente(idCliente)).thenThrow(new ResourceNotFoundException("Cliente não encontrado"));
//
//        assertThrows(ResourceNotFoundException.class, () -> clienteController.buscarClientePorId(idCliente));
//    }
//
//    @Test
//    void deveriaBuscarTodosOsClientesComSucesso() {
//        List<ClienteResponseDTO> clientes = List.of(clienteResponseDTO);
//        when(clienteService.buscarClientes()).thenReturn(clientes);
//
//        ResponseEntity<List<ClienteResponseDTO>> response = clienteController.buscarClientes();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(clientes, response.getBody());
//    }
//
//    @Test
//    void deveriaDeletarClienteComSucesso() {
//        UUID idCliente = UUID.randomUUID();
//
//        ResponseEntity<Void> response = clienteController.deletarCliente(idCliente);
//
//        verify(clienteService, times(1)).deletarCliente(idCliente);
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//    }
//
//    @Test
//    void deveriaRetornarErroAoDeletarClienteInexistente() {
//        UUID idCliente = UUID.randomUUID();
//        doThrow(new ResourceNotFoundException("Cliente não encontrado")).when(clienteService).deletarCliente(idCliente);
//
//        assertThrows(ResourceNotFoundException.class, () -> clienteController.deletarCliente(idCliente));
//    }
//}