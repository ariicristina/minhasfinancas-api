package com.primeiroteste.minhasfinancas.service.impl;

import com.primeiroteste.minhasfinancas.exception.RegraNegocioException;
import com.primeiroteste.minhasfinancas.model.entity.Lancamento;
import com.primeiroteste.minhasfinancas.model.enums.StatusLancamento;
import com.primeiroteste.minhasfinancas.model.enums.TipoLancamento;
import com.primeiroteste.minhasfinancas.model.repository.LancamentoRepository;
import com.primeiroteste.minhasfinancas.service.LancamentoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private LancamentoRepository respository;

    public LancamentoServiceImpl(LancamentoRepository respository){
        this.respository = respository;
    }

    @Override
    @Transactional
    public Lancamento salvar(Lancamento lancamento) {
        validar(lancamento);
        lancamento.setStatusLancamento(StatusLancamento.PENDENTE);
        return respository.save(lancamento);
    }

    @Override
    @Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId()); //garanteQueVaiPedirOIdAntesDeAtualizarESalvar
        validar(lancamento);
        return respository.save(lancamento);
    }

    @Override
    @Transactional
    public void deletar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId()); //MesmaCoisaPrecisaPassarUmIdAntesDeDeletar
        respository.delete(lancamento);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
        Example example = Example.of(lancamentoFiltro, ExampleMatcher.matching()
                .withIgnoreCase() //ignoraOTamanhoDoStringNaBusca
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)); //seDigitarUmPedacoDaDescricaoEleJaBusca
        return respository.findAll(example);
    }

    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
        lancamento.setStatusLancamento(status); //SetaOStatus
        atualizar(lancamento);

    }

    @Override
    public void validar(Lancamento lancamento) {
        if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")){
            throw new RegraNegocioException("Informe descrição válida!");
        }
        if(lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() >12){
            throw new RegraNegocioException("Informe um mês válido!");
        }
        if (lancamento.getAno() == null || lancamento.getAno().toString().length() != 4){
            throw new RegraNegocioException("Informe um ano válido!");
        }
        if (lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null){
            throw new RegraNegocioException("Informe um usuário!");
        }
        if(lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1){
            throw new RegraNegocioException("Informe um valor válido!");
        }
        if(lancamento.getTipoLancamento() == null){
            throw new RegraNegocioException("Informe um tipo de lançamento!");
        }
    }

    @Override
    public Optional<Lancamento> obterPorId(Long id) {
        return respository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal obterSaldoPorUsuario(Long id){
        BigDecimal receitas = respository.obterSaldoPorTipoLancamentoEUsuario(id, TipoLancamento.RECEITA);
        BigDecimal despesas = respository.obterSaldoPorTipoLancamentoEUsuario(id, TipoLancamento.DESPESA);

        if(receitas == null){
            receitas = BigDecimal.ZERO;
        }
        if(despesas == null){
            despesas = BigDecimal.ZERO;
        }
        return receitas.subtract(despesas);
    }


}
