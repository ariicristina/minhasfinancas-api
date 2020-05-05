package com.primeiroteste.minhasfinancas.model.repository;

import com.primeiroteste.minhasfinancas.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTeste {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void  deveVerificarAExistenciaDeUmEmail(){
        //cenario
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        //ação
        boolean result = repository.existsByEmail("usuario@email.com");

        //verificação
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail(){
        //cenario
        //ação
        boolean result = repository.existsByEmail("usuario@email.com");

        //verificacao
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void devePersistirUmUsuaruiNaBaseDeDados(){
        //cenario
        Usuario usuario = criarUsuario();

        //acao
        Usuario usuarioSalvo = repository.save(usuario);

        //verificacao
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }

    @Test
    public void deveBuscarUmUsuarioPorEmail(){
        //cenario
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        //verificacao
        Optional<Usuario> result = repository.findByEmail("usuario@email.com");
        Assertions.assertThat(result.isPresent()).isTrue();
    }
    @Test
    public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBase(){
        //verificacao
        Optional<Usuario> result = repository.findByEmail("usuario@email.com");
        Assertions.assertThat(result.isPresent()).isFalse();

    }


    public static Usuario criarUsuario(){
        return Usuario.builder().nome("usuario").email("usuario@email.com").senha("senha").build();
    }



}
