package br.com.splgenerator.web.relatorio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlDataTable;

import br.com.splgenerator.model.cadastro.Funcionario;

@ManagedBean
@RequestScoped
public class RelatorioMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{jdbcDao}")
	private br.com.splgenerator.dao.ModRhDao dao;
	@ManagedProperty(value="#{logTXT}")
	private br.com.splgenerator.audit.ILog log;
	
	private HtmlDataTable htmlDataTable;
	
	private List<Funcionario> funcionarios = new ArrayList<Funcionario>();
	private Date dataAdmissaoDe;
	private Date dataAdmissaoAte;
	private int mesAniversario;
	private String selectedBancosFuncionarios;

	public RelatorioMB() {
		System.out.println("RelatorioMB.construtor...");
	}
	
	public void setDao(br.com.splgenerator.dao.ModRhDao dao) { this.dao = dao; }
	public void setLog(br.com.splgenerator.audit.ILog log) { this.log = log; }
	
	public HtmlDataTable getHtmlDataTable() { return htmlDataTable; }
	public void setHtmlDataTable(HtmlDataTable htmlDataTable) { this.htmlDataTable = htmlDataTable; }
	
	public Date getDataAdmissaoDe() { return dataAdmissaoDe; }
	public void setDataAdmissaoDe(Date dataAdmissaoDe) { this.dataAdmissaoDe = dataAdmissaoDe; }

	public Date getDataAdmissaoAte() { return dataAdmissaoAte; }
	public void setDataAdmissaoAte(Date dataAdmissaoAte) { this.dataAdmissaoAte = dataAdmissaoAte; }
	
	public java.util.List<br.com.splgenerator.model.cadastro.Funcionario> getFuncionarios() { return this.funcionarios; }
	
	public int getMesAniversario() { return mesAniversario; }
	public void setMesAniversario(int mesAniversario) { this.mesAniversario = mesAniversario; }

	public String getSelectedBancosFuncionarios() { return selectedBancosFuncionarios; }
	public void setSelectedBancosFuncionarios(String selectedBancosFuncionarios) { this.selectedBancosFuncionarios = selectedBancosFuncionarios; }

	public List<String> getBancosFuncionarios() { return dao.getBancos(); }

	public String buscarPeriodoAdmissao() {
		log.log("INFO", "Buscando Periodo Admissao - De: " + dataAdmissaoDe + " Ate: " + dataAdmissaoAte);
		this.funcionarios = dao.getFuncionariosPeriodoAdmissao(dataAdmissaoDe, dataAdmissaoAte);
		return "relatorioAdmissoes";
	}
	
	public String buscarAniversariantes() {
		log.log("INFO", "Buscando Aniversariantes do mes: " + this.mesAniversario);
		this.funcionarios = dao.getFuncionariosAniversariantes(this.mesAniversario);
		return "relatorioAniversariantes";
	}
	
	public String buscarBancosFuncionarios() {
		log.log("INFO", "Buscando Dados Bancarios do banco " + this.selectedBancosFuncionarios);
		this.funcionarios = dao.getFuncionariosBanco(this.selectedBancosFuncionarios);
		return "relatorioDadosBancarios";
	}
	
}
