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
	
	public RelatorioMB() {
		System.out.println("construtor...");
	}
	
	private Date dataAdmissaoDe;
	
	private Date dataAdmissaoAte;
	
	public void setDao(br.com.splgenerator.dao.ModRhDao dao) {
		this.dao = dao;
	}
	
	public void setLog(br.com.splgenerator.audit.ILog log) {
		this.log = log;
	}
	
	public HtmlDataTable getHtmlDataTable() {
		return htmlDataTable;
	}

	public void setHtmlDataTable(HtmlDataTable htmlDataTable) {
		this.htmlDataTable = htmlDataTable;
	}
	
	public Date getDataAdmissaoDe() {
		return dataAdmissaoDe;
	}
	public void setDataAdmissaoDe(Date dataAdmissaoDe) {
		this.dataAdmissaoDe = dataAdmissaoDe;
	}

	public Date getDataAdmissaoAte() {
		return dataAdmissaoAte;
	}
	public void setDataAdmissaoAte(Date dataAdmissaoAte) {
		this.dataAdmissaoAte = dataAdmissaoAte;
	}
	
	public java.util.List<br.com.splgenerator.model.cadastro.Funcionario> getFuncionarios() {
		return this.funcionarios;

	}
	
	public String buscarPeriodoAdmissao() {
		//System.out.println(this.log);
		log.log("INFO", "Buscando Periodo Admissao - De: " + dataAdmissaoDe + " Ate: " + dataAdmissaoAte);
		this.funcionarios = dao.getFuncionariosPeriodoAdmissao(dataAdmissaoDe, dataAdmissaoAte);
		//System.out.println(funcionarios);
		
		return "relatorioAdmissoes";
	}
	
}
