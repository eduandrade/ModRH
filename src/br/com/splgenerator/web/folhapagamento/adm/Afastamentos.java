// =======================================================================================
// Warning. Do not edit this file directly.  This file was built with the SPL generator.
// Eduardo Andrade - 2014-07-16 00:08:57 -0300
// =======================================================================================
package br.com.splgenerator.web.folhapagamento.adm;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;

import br.com.splgenerator.audit.ILog;
import br.com.splgenerator.dao.ModRhDao;
import br.com.splgenerator.model.cadastro.Funcionario;
import br.com.splgenerator.monitoring.ModRhStats;

@ManagedBean
@SessionScoped
public class Afastamentos implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{jpaDao}")
	private ModRhDao dao;
	@ManagedProperty(value = "#{logTXT}")
	private ILog log;

	private transient HtmlDataTable htmlDataTable;

	private br.com.splgenerator.model.cadastro.Funcionario _editarFuncionario;

	public Afastamentos() {
		_editarFuncionario = null;
	}

	public void setDao(ModRhDao dao) { this.dao = dao; }
	public void setLog(ILog log) { this.log = log; }

	public HtmlDataTable getHtmlDataTable() { return htmlDataTable; }
	public void setHtmlDataTable(HtmlDataTable htmlDataTable) { this.htmlDataTable = htmlDataTable; }

	public java.util.List<br.com.splgenerator.model.cadastro.Funcionario> getFuncionarios() {
		log.log("TRACE", "Executando getFuncionarios()...");
		ModRhStats.incrementCounterSelectFuncionarios();
		return dao.getFuncionarios();
	}

	public String atualizarFuncionario() {
		log.log("TRACE", "Executando atualizarFuncionario()...");
		ModRhStats.incrementCounterUpdateFuncionarios();
		dao.atualizarFuncionario(_editarFuncionario);
		addFacesMessage("Funcionario atualizado com sucesso!");
		return "afastamentos";
	}

	public String selecionarFuncionario() {
		log.log("TRACE", "Executando selecionarFuncionario()...");
		this._editarFuncionario = (Funcionario) htmlDataTable.getRowData();
		return "afastamentosAtualizar";

	}

	public Funcionario getEditarfuncionario() { return _editarFuncionario; }
	public void setEditarfuncionario(Funcionario value) { _editarFuncionario = value; }

	private void addFacesMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
	}

}
