package com.primeiroteste.minhasfinancas.model.entity;

import com.primeiroteste.minhasfinancas.model.enums.StatusLancamento;
import com.primeiroteste.minhasfinancas.model.enums.TipoLancamento;
import lombok.Data;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lancamento", schema = "financas")
@Data
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="descricao")
    private String descricao;

    @Column(name="mes")
    private Integer mes;

    @Column(name="ano")
    private Integer ano;

    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name="data_cadastro")
    private LocalDate dataCadastro;

    @Column(name="tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipoLancamento;

    @Column(name="status")
    @Enumerated(value = EnumType.STRING)
    private StatusLancamento statusLancamento;
}
