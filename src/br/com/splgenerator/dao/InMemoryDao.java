package br.com.splgenerator.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.splgenerator.model.cadastro.CursoFuncionario;
import br.com.splgenerator.model.cadastro.DadosFuncionario;
import br.com.splgenerator.model.cadastro.Funcionario;
import br.com.splgenerator.model.cadastro.Reembolso;
import br.com.splgenerator.model.cadastro.TipoDespesa;

@ManagedBean(name="inMemoryDao")
@SessionScoped
public class InMemoryDao implements ModRhDao {

	private static final long serialVersionUID = 1L;
	
	private static List<Funcionario> listaFuncionario = new ArrayList<Funcionario>();
	private static List<Reembolso> listaReembolso = new ArrayList<Reembolso>();
	private static List<TipoDespesa> listaTipoDespesa = new ArrayList<TipoDespesa>();
	
	static {
		Funcionario funcionario1 = new Funcionario();
		funcionario1.setId(1);
		funcionario1.setNome("Jose da Silva (MEMORIA)");
		funcionario1.setIdade(30);
		funcionario1.setCpf("11122233396");
		funcionario1.setEndereco("Rua Um");
		
		//DadosFuncionario dadosFuncionario = new DadosFuncionario();
		//dadosFuncionario.setMaxauxilioestudo(150F);
		//funcionario1.setDadosfuncionario(dadosFuncionario);
		
		CursoFuncionario cursoFuncionario = new CursoFuncionario();
		cursoFuncionario.setCurso("Ingles");
		//dadosFuncionario.setCursofuncionario(cursoFuncionario);
		
		Funcionario funcionario2 = new Funcionario();
		funcionario2.setId(2);
		funcionario2.setNome("Maria Paula (MEMORIA)");
		funcionario2.setIdade(45);
		funcionario2.setCpf("99988877712");
		funcionario2.setEndereco("Avenida Dois");
		
		//DadosFuncionario dadosFuncionario2 = new DadosFuncionario();
		//dadosFuncionario2.setMaxauxilioestudo(320.55F);
		//funcionario2.setDadosfuncionario(dadosFuncionario2);
		
		CursoFuncionario cursoFuncionario2 = new CursoFuncionario();
		cursoFuncionario2.setCurso("Espanhol");
		//dadosFuncionario2.setCursofuncionario(cursoFuncionario2);
		
		listaFuncionario.add(funcionario1);
		listaFuncionario.add(funcionario2);
		
		//Reembolso
		
		Reembolso reembolso1 = new Reembolso();
		reembolso1.setId(1);
		reembolso1.setIdparcelacurso(1);
		reembolso1.setFinalidade("finalidade 1");
		reembolso1.setValorreembolso(50.0F);
		reembolso1.setDatapedido(new Date());
		
		Reembolso reembolso2 = new Reembolso();
		reembolso2.setId(2);
		reembolso2.setIdparcelacurso(2);
		reembolso2.setFinalidade("finalidade 2");
		reembolso2.setValorreembolso(75.83F);
		reembolso2.setDatapedido(new Date());
		
		listaReembolso.add(reembolso1);
		listaReembolso.add(reembolso2);
		
		//Tipo Despesa
		
		TipoDespesa tipoDespesa1 = new TipoDespesa();
		tipoDespesa1.setId(1);
		tipoDespesa1.setDescricao("Combust’vel");
		TipoDespesa tipoDespesa2 = new TipoDespesa();
		tipoDespesa2.setId(2);
		tipoDespesa2.setDescricao("Hospedagem");
		
		listaTipoDespesa.add(tipoDespesa1);
		listaTipoDespesa.add(tipoDespesa2);
	}

	@Override
	public List<Funcionario> getFuncionarios() {
		return listaFuncionario;
	}

	@Override
	public void salvarFuncionario(Funcionario funcionario) {
		listaFuncionario.add(funcionario);
	}

	@Override
	public void removerFuncionario(Funcionario funcionario) {
		listaFuncionario.remove(funcionario);
	}

	@Override
	public void atualizarFuncionario(Funcionario funcionario) {
		Funcionario tempFuncionario = null;
		for (Funcionario f : listaFuncionario) {
			if (f.equals(funcionario)) {
				tempFuncionario = f;
				break;
			}
		}
		listaFuncionario.remove(tempFuncionario);
		salvarFuncionario(funcionario);
	}
	
	//Reembolso
	
	@Override
	public List<Reembolso> getReembolsos() {
		return listaReembolso;
	}

	@Override
	public void atualizarReembolso(Reembolso reembolso) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void salvarReembolso(Reembolso reembolso) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removerReembolso(Reembolso reembolso) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<TipoDespesa> getTipoDespesas() {
		return listaTipoDespesa;
	}

	@Override
	public void atualizarTipoDespesa(TipoDespesa tipoDespesa) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void salvarTipoDespesa(TipoDespesa tipoDespesa) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removerTipoDespesa(TipoDespesa tipoDespesa) {
		// TODO Auto-generated method stub
		
	}

}
