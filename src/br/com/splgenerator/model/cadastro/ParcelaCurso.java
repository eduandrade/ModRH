// =======================================================================================
// Warning. Do not edit this file directly.  This file was built with the SPL generator.
// Eduardo Andrade - 2014-04-09 13:12:38 -0300
// =======================================================================================
package br.com.splgenerator.model.cadastro;

public class ParcelaCurso {

	private long _id;
	private java.util.Date _dataVencimento;
	private Float _valor;
	private Float _valorReembolso;
	private boolean _flagAbaterAuxilioEstudo;
	private java.util.Date _dataAprovacaoChefe;

	public ParcelaCurso() { 
	
		_id = 0;
	
		_dataVencimento = null;
	
		_valor = null;
	
		_valorReembolso = null;
	
		_flagAbaterAuxilioEstudo = false;
	
		_dataAprovacaoChefe = null;
	
	}
	
	
		public long getId() { return _id; }
		public void setId( long value ) { _id = value; }
	
		public java.util.Date getDatavencimento() { return _dataVencimento; }
		public void setDatavencimento( java.util.Date value ) { _dataVencimento = value; }
	
		public Float getValor() { return _valor; }
		public void setValor( Float value ) { _valor = value; }
	
		public Float getValorreembolso() { return _valorReembolso; }
		public void setValorreembolso( Float value ) { _valorReembolso = value; }
	
		public boolean getFlagabaterauxilioestudo() { return _flagAbaterAuxilioEstudo; }
		public void setFlagabaterauxilioestudo( boolean value ) { _flagAbaterAuxilioEstudo = value; }
	
		public java.util.Date getDataaprovacaochefe() { return _dataAprovacaoChefe; }
		public void setDataaprovacaochefe( java.util.Date value ) { _dataAprovacaoChefe = value; }
	

}

