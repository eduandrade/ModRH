// =======================================================================================
// Warning. Do not edit this file directly.  This file was built with the SPL generator.
// Eduardo Andrade - 2014-04-09 13:12:38 -0300
// =======================================================================================
package br.com.splgenerator.model.cadastro;

public class Funcionario {

	private long _id;
	private String _nome;
	private int _idade;
	private String _cpf;
	private String _endereco;
	//private br.com.splgenerator.model.cadastro.DadosFuncionario _dadosFuncionario;

	public Funcionario() { 
		_id = 0;
		_nome = null;
		_idade = 0;
		_cpf = null;
		_endereco = null;	
	}
	
	
		public long getId() { return _id; }
		public void setId( long value ) { _id = value; }
	
		public String getNome() { return _nome; }
		public void setNome( String value ) { _nome = value; }
	
		public int getIdade() { return _idade; }
		public void setIdade( int value ) { _idade = value; }
	
		public String getCpf() { return _cpf; }
		public void setCpf( String value ) { _cpf = value; }
	
		public String getEndereco() { return _endereco; }
		public void setEndereco( String value ) { _endereco = value; }

}

