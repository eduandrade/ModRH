// =======================================================================================
// Warning. Do not edit this file directly.  This file was built with the SPL generator.
// Eduardo Andrade - 2014-04-09 13:12:38 -0300
// =======================================================================================
package br.com.splgenerator.web.cadastro;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class GerenciarReembolso implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{jdbcDao}")
	private br.com.splgenerator.dao.ModRhDao dao;
	
	@ManagedProperty(value="#{logTXT}")
	private br.com.splgenerator.audit.ILog log;
	
	private HtmlDataTable htmlDataTable;
	
	
	private br.com.splgenerator.model.cadastro.Reembolso _novoReembolso;
	
	private br.com.splgenerator.model.cadastro.Reembolso _editarReembolso;
	
	
	public GerenciarReembolso() { 
	
		_novoReembolso = new  br.com.splgenerator.model.cadastro.Reembolso();
		_editarReembolso = null;
	}
	
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
	
	
	public java.util.List<br.com.splgenerator.model.cadastro.Reembolso> getReembolsos() {
		log.log("TRACE", "Executando getReembolsos()...");
	
									return dao.getReembolsos();
								
	}
	
	public String atualizarReembolso() {
		log.log("TRACE", "Executando atualizarReembolso()...");
	
									dao.atualizarReembolso(_editarReembolso);
									addFacesMessage("Reembolso editado com sucesso!");
									return "gerenciarReembolso";
								
	}
	
	public String salvarReembolso() {
		log.log("TRACE", "Executando salvarReembolso()...");
	
									dao.salvarReembolso(_novoReembolso);
									addFacesMessage("Pedido de reembolso adicionado com sucesso!");
									this._novoReembolso = new br.com.splgenerator.model.cadastro.Reembolso();
									return "gerenciarReembolso";
								
	}
	
	public String removerReembolso() {
		log.log("TRACE", "Executando removerReembolso()...");
	
									dao.removerReembolso((br.com.splgenerator.model.cadastro.Reembolso) htmlDataTable.getRowData());
									addFacesMessage("Pedido de reembolso removido com sucesso!");
									return null;
								
	}
	
	public String selecionarReembolso() {
		log.log("TRACE", "Executando selecionarReembolso()...");
	
									this._editarReembolso = (br.com.splgenerator.model.cadastro.Reembolso) htmlDataTable.getRowData();
									return "editarReembolso";
								
	}
	
	
	
	public br.com.splgenerator.model.cadastro.Reembolso getNovoreembolso() { return _novoReembolso; }
	public void setNovoreembolso( br.com.splgenerator.model.cadastro.Reembolso value ) { _novoReembolso = value; }
	
	public br.com.splgenerator.model.cadastro.Reembolso getEditarreembolso() { return _editarReembolso; }
	public void setEditarreembolso( br.com.splgenerator.model.cadastro.Reembolso value ) { _editarReembolso = value; }
	
	
	private void addFacesMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
	}

}
