//package com.techchallenge.user_manager_api.services.impl;
//
//import com.techchallenge.user_manager_api.naousar.dto.requests.AtualizarSenhaRequestDTO;
//import com.techchallenge.user_manager_api.naousar.entities.Usuario;
//import com.techchallenge.user_manager_api.naousar.repositories.UsuarioRepository;
//import com.techchallenge.user_manager_api.naousar.services.PasswordService;
//
//import com.techchallenge.user_manager_api.naousar.services.impl.UsuarioServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.Authentication;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UsuarioServiceImplTest {
//
//    @Mock
//    private UsuarioRepository usuarioRepository;
//
//    @Mock
//    private PasswordService passwordService;
//
//    @InjectMocks
//    private UsuarioServiceImpl usuarioService;
//
//    @Mock
//    private Authentication authentication;
//
//    @Test
//    void atualizarSenha_SenhaAtualCorreta() {
//        Usuario usuario = new Usuario();
//        AtualizarSenhaRequestDTO atualizarSenhaDTO = new AtualizarSenhaRequestDTO("senhaAtual", "novaSenha");
//
//        when(authentication.getPrincipal()).thenReturn(usuario);
//        when(passwordService.matches(atualizarSenhaDTO.senhaAtual(), usuario.getSenha())).thenReturn(true);
//        when(passwordService.encryptPassword(atualizarSenhaDTO.novaSenha())).thenReturn("novaSenhaCriptografada");
//
//        usuarioService.atualizarSenha(atualizarSenhaDTO, authentication);
//
//        verify(usuarioRepository, times(1)).save(usuario);
//        assertEquals("novaSenhaCriptografada", usuario.getSenha());
//    }
//
//    @Test
//    void atualizarSenha_SenhaAtualIncorreta() {
//        Usuario usuario = new Usuario();
//        AtualizarSenhaRequestDTO atualizarSenhaDTO = new AtualizarSenhaRequestDTO("senhaErrada", "novaSenha");
//
//        when(authentication.getPrincipal()).thenReturn(usuario);
//        when(passwordService.matches(atualizarSenhaDTO.senhaAtual(), usuario.getSenha())).thenReturn(false);
//
//        assertThrows(UnauthorizedException.class, () -> usuarioService.atualizarSenha(atualizarSenhaDTO, authentication));
//        verify(usuarioRepository, never()).save(any(Usuario.class));
//    }
//
//    @Test
//    void existsByLogin_LoginExiste() {
//        when(usuarioRepository.existsByLogin("loginExistente")).thenReturn(true);
//        assertTrue(usuarioService.existsByLogin("loginExistente"));
//    }
//
//    @Test
//    void existsByLogin_LoginNaoExiste() {
//        when(usuarioRepository.existsByLogin("loginInexistente")).thenReturn(false);
//        assertFalse(usuarioService.existsByLogin("loginInexistente"));
//    }
//}