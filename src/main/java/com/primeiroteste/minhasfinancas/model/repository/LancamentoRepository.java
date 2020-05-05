package com.primeiroteste.minhasfinancas.model.repository;

import com.primeiroteste.minhasfinancas.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
