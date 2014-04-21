// =======================================================================================
// Warning. Do not edit this file directly.  This file was built with the SPL generator.
// Eduardo Andrade - 2014-04-09 13:12:38 -0300
// =======================================================================================
package br.com.splgenerator.model.cadastro;

public class Reembolso {

	private long _id;
	private long _idParcelaCurso;
	private java.util.Date _dataPedido;
	private java.util.Date _dataAprovacaoChefe;
	private java.util.Date _dataAprovacaoDiretor;
	private java.util.Date _dataAprovacaoFinanc;
	private java.util.Date _dataPagamento;
	private String _finalidade;
	private Float _valorReembolso;
	private java.util.Date _dataContabilizacao;
	private String _tipoReembolso;
	private br.com.splgenerator.model.cadastro.ParcelaCurso _parcelaCurso;
	private br.com.splgenerator.model.cadastro.ComprovanteDespesa _comprovanteDespesa;

	public Reembolso() { 
	
		_id = 0;
	
		_idParcelaCurso = 0;
	
		_dataPedido = null;
	
		_dataAprovacaoChefe = null;
	
		_dataAprovacaoDiretor = null;
	
		_dataAprovacaoFinanc = null;
	
		_dataPagamento = null;
	
		_finalidade = null;
	
		_valorReembolso = null;
	
		_dataContabilizacao = null;
	
		_tipoReembolso = null;
	
		_parcelaCurso = new br.com.splgenerator.model.cadastro.ParcelaCurso();
	
		_comprovanteDespesa = new br.com.splgenerator.model.cadastro.ComprovanteDespesa();
	
	}
	
	
		public long getId() { return _id; }
		public void setId( long value ) { _id = value; }
	
		public long getIdparcelacurso() { return _idParcelaCurso; }
		public void setIdparcelacurso( long value ) { _idParcelaCurso = value; }
	
		public java.util.Date getDatapedido() { return _dataPedido; }
		public void setDatapedido( java.util.Date value ) { _dataPedido = value; }
	
		public java.util.Date getDataaprovacaochefe() { return _dataAprovacaoChefe; }
		public void setDataaprovacaochefe( java.util.Date value ) { _dataAprovacaoChefe = value; }
	
		public java.util.Date getDataaprovacaodiretor() { return _dataAprovacaoDiretor; }
		public void setDataaprovacaodiretor( java.util.Date value ) { _dataAprovacaoDiretor = value; }
	
		public java.util.Date getDataaprovacaofinanc() { return _dataAprovacaoFinanc; }
		public void setDataaprovacaofinanc( java.util.Date value ) { _dataAprovacaoFinanc = value; }
	
		public java.util.Date getDatapagamento() { return _dataPagamento; }
		public void setDatapagamento( java.util.Date value ) { _dataPagamento = value; }
	
		public String getFinalidade() { return _finalidade; }
		public void setFinalidade( String value ) { _finalidade = value; }
	
		public Float getValorreembolso() { return _valorReembolso; }
		public void setValorreembolso( Float value ) { _valorReembolso = value; }
	
		public java.util.Date getDatacontabilizacao() { return _dataContabilizacao; }
		public void setDatacontabilizacao( java.util.Date value ) { _dataContabilizacao = value; }
	
		public String getTiporeembolso() { return _tipoReembolso; }
		public void setTiporeembolso( String value ) { _tipoReembolso = value; }
	
		public br.com.splgenerator.model.cadastro.ParcelaCurso getParcelacurso() { return _parcelaCurso; }
		public void setParcelacurso( br.com.splgenerator.model.cadastro.ParcelaCurso value ) { _parcelaCurso = value; }
	
		public br.com.splgenerator.model.cadastro.ComprovanteDespesa getComprovantedespesa() { return _comprovanteDespesa; }
		public void setComprovantedespesa( br.com.splgenerator.model.cadastro.ComprovanteDespesa value ) { _comprovanteDespesa = value; }
	

}
