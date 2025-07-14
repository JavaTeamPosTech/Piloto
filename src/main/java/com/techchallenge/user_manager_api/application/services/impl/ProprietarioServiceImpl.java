//package com.techchallenge.user_manager_api.application.services.impl;
//
//import com.techchallenge.user_manager_api.application.services.PasswordService;
//import com.techchallenge.user_manager_api.application.services.ProprietarioService;
//import com.techchallenge.user_manager_api.application.services.UsuarioService;
//import com.techchallenge.user_manager_api.naousar.dto.requests.AtualizarProprietarioRequestDTO;
//import com.techchallenge.user_manager_api.naousar.dto.requests.ProprietarioRequestDTO;
//import com.techchallenge.user_manager_api.naousar.dto.response.CadastroResponseDTO;
//import com.techchallenge.user_manager_api.naousar.dto.response.ProprietarioResponseDTO;
//import com.techchallenge.user_manager_api.naousar.dto.response.UsuarioResponseDTO;
//import com.techchallenge.user_manager_api.naousar.entities.Endereco;
//import com.techchallenge.user_manager_api.naousar.entities.Proprietario;
//import com.techchallenge.user_manager_api.naousar.repositories.ProprietarioRepository;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@Service
//public class ProprietarioServiceImpl implements ProprietarioService {
//
//    private final ProprietarioRepository proprietarioRepository;
//    private final PasswordService passwordService;
//    private final TokenService tokenService;
//    private final UsuarioService usuarioService;
//
//    public ProprietarioServiceImpl(ProprietarioRepository proprietarioRepository, PasswordService passwordService, TokenService tokenService, UsuarioService usuarioService) {
//        this.proprietarioRepository = proprietarioRepository;
//        this.passwordService = passwordService;
//        this.tokenService = tokenService;
//        this.usuarioService = usuarioService;
//    }
//
//    @Override
//    public CadastroResponseDTO cadastrarProprietario(ProprietarioRequestDTO proprietarioDTO) {
//
//        if (usuarioService.existsByLogin(proprietarioDTO.login())) {
//            throw new DataIntegrityViolationException("uk_proprietario_login:  O login '" + proprietarioDTO.login() + "' já está em uso.");
//        }
//
//        String senhaCriptografada = passwordService.encryptPassword(proprietarioDTO.senha());
//        Proprietario proprietario = UsuarioMapper.toProprietario(proprietarioDTO, senhaCriptografada);
//        Proprietario proprietarioSalvo = proprietarioRepository.save(proprietario);
//        String token = tokenService.generateToken(proprietarioSalvo.getLogin());
//
//        return new CadastroResponseDTO(UsuarioResponseDTO.deProprietario(proprietarioSalvo), token);
//    }
//
//    @Override
//    public ProprietarioResponseDTO buscarProprietarioPorId(UUID id) {
//        Proprietario proprietario = proprietarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Proprietário com id '%s' não encontrado", id)));
//        return UsuarioMapper.toProprietarioResponseDTO(proprietario);
//    }
//
//    @Override
//    public void deletarProprietario(UUID id) {
//        proprietarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Proprietário com id '%s' não encontrado", id)));
//        proprietarioRepository.deleteById(id);
//    }
//
//    @Override
//    public ProprietarioResponseDTO editarProprietario(UUID id, AtualizarProprietarioRequestDTO proprietarioRequestDTO) {
//        Proprietario proprietario = proprietarioRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException(String.format("Proprietario com id '%s' não encontrado", id)));
//
//        if (usuarioService.existsByLogin(proprietarioRequestDTO.login()) && !proprietario.getLogin().equals(proprietarioRequestDTO.login())) {
//            throw new DataIntegrityViolationException("uk_usuario_login: O login '" + proprietarioRequestDTO.login() + "' já está em uso.");
//        }
//        atualizarDadosProprietario(proprietario, proprietarioRequestDTO);
//        Proprietario proprietarioAtualizado = proprietarioRepository.save(proprietario);
//        return UsuarioMapper.toProprietarioResponseDTO(proprietarioAtualizado);
//    }
//
//    private void atualizarDadosProprietario(Proprietario proprietario, AtualizarProprietarioRequestDTO proprietarioRequestDTO) {
//        proprietario.setNome(proprietarioRequestDTO.nome());
//        proprietario.setEmail(proprietarioRequestDTO.email());
//        proprietario.setLogin(proprietarioRequestDTO.login());
//        proprietario.setCnpj(proprietarioRequestDTO.cnpj());
//        proprietario.setRazaoSocial(proprietarioRequestDTO.razaoSocial());
//        proprietario.setNomeFantasia(proprietarioRequestDTO.nomeFantasia());
//        proprietario.setInscricaoEstadual(proprietarioRequestDTO.inscricaoEstadual());
//        proprietario.setTelefoneComercial(proprietarioRequestDTO.telefoneComercial());
//        proprietario.setWhatsapp(proprietarioRequestDTO.whatsapp());
//        proprietario.setStatusConta(proprietarioRequestDTO.statusConta());
//
//        if (proprietarioRequestDTO.enderecos() != null && !proprietarioRequestDTO.enderecos().isEmpty()) {
//            proprietario.getEnderecos().clear();
//            proprietarioRequestDTO.enderecos().forEach(enderecoDTO -> {
//                Endereco endereco = new Endereco(enderecoDTO, proprietario);
//                proprietario.getEnderecos().add(endereco);
//            });
//        }
//    }
//}
