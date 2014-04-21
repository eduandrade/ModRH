// =======================================================================================
// Warning. Do not edit this file directly.  This file was built with the SPL generator.
// Eduardo Andrade - 2014-04-09 13:12:38 -0300
// =======================================================================================
package br.com.splgenerator.model.cadastro;

public class CursoFuncionario {

	private long _id;
	private long _idFuncionario;
	private long _idParcelaCurso;
	private String _curso;
	private String _instituicao;
	private java.util.Date _dataInicio;
	private int _duracaoMeses;
	private String _justificativa;
	private br.com.splgenerator.model.cadastro.ParcelaCurso _parcelaCurso;

	public CursoFuncionario() { 
	
		_id = 0;
	
		_idFuncionario = 0;
	
		_idParcelaCurso = 0;
	
		_curso = null;
	
		_instituicao = null;
	
		_dataInicio = null;
	
		_duracaoMeses = 0;
	
		_justificativa = "";
	
		_parcelaCurso = new br.com.splgenerator.model.cadastro.ParcelaCurso();
	
	}
	
	
		public long getId() { return _id; }
		public void setId( long value ) { _id = value; }
	
		public long getIdfuncionario() { return _idFuncionario; }
		public void setIdfuncionario( long value ) { _idFuncionario = value; }
	
		public long getIdparcelacurso() { return _idParcelaCurso; }
		public void setIdparcelacurso( long value ) { _idParcelaCurso = value; }
	
		public String getCurso() { return _curso; }
		public void setCurso( String value ) { _curso = value; }
	
		public String getInstituicao() { return _instituicao; }
		public void setInstituicao( String value ) { _instituicao = value; }
	
		public java.util.Date getDatainicio() { return _dataInicio; }
		public void setDatainicio( java.util.Date value ) { _dataInicio = value; }
	
		public int getDuracaomeses() { return _duracaoMeses; }
		public void setDuracaomeses( int value ) { _duracaoMeses = value; }
	
		public String getJustificativa() { return _justificativa; }
		public void setJustificativa( String value ) { _justificativa = value; }
	
		public br.com.splgenerator.model.cadastro.ParcelaCurso getParcelacurso() { return _parcelaCurso; }
		public void setParcelacurso( br.com.splgenerator.model.cadastro.ParcelaCurso value ) { _parcelaCurso = value; }
	

}

