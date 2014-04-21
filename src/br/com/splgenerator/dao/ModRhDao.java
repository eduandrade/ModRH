package br.com.splgenerator.dao;

import java.io.Serializable;
import java.util.List;

import br.com.splgenerator.model.cadastro.Funcionario;
import br.com.splgenerator.model.cadastro.Reembolso;
import br.com.splgenerator.model.cadastro.TipoDespesa;

public interface ModRhDao extends Serializable {
	
	//Funcionario
	
	List<Funcionario> getFuncionarios();
	
	void salvarFuncionario(Funcionario funcionario);
	
	void atualizarFuncionario(Funcionario funcionario);
	
	void removerFuncionario(Funcionario funcionario);
	
	//Reembolso
	
	List<Reembolso> getReembolsos();
	
	void atualizarReembolso(Reembolso reembolso);
	
	void salvarReembolso(Reembolso reembolso);
	
	void removerReembolso(Reembolso reembolso);
	
	//Tipo Despesa
	
	List<TipoDespesa> getTipoDespesas();
	
	void atualizarTipoDespesa(TipoDespesa tipoDespesa);
	
	void salvarTipoDespesa(TipoDespesa tipoDespesa);
	
	void removerTipoDespesa(TipoDespesa tipoDespesa);
	
}
