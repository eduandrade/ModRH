package br.com.splgenerator.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;

import br.com.splgenerator.model.cadastro.CursoFuncionario;
import br.com.splgenerator.model.cadastro.DadosFuncionario;
import br.com.splgenerator.model.cadastro.Funcionario;
import br.com.splgenerator.model.cadastro.ParcelaCurso;
import br.com.splgenerator.model.cadastro.Reembolso;
import br.com.splgenerator.model.cadastro.TipoDespesa;

@ManagedBean(name = "jdbcDao")
@SessionScoped
public class JdbcDao extends JdbcBaseDao {
	
	private static final long serialVersionUID = 1L;

	public JdbcDao() throws NamingException {
		super();
	}

	@Override
	public List<Funcionario> getFuncionarios() {
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = getConnection();

			String query = "select f.*, df.id as ID_DADOS_FUNC, df.*, cf.id as ID_CURSO_FUNCIONARIO, cf.*, pc.* from funcionario f, dados_funcionario df, curso_funcionario cf, parcela_curso pc where f.id = df.id_funcionario and f.id = cf.id_funcionario and cf.id_parcela = pc.id_parcela";
			stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(rs.getInt("ID"));
				funcionario.setNome(rs.getString("NOME"));
				funcionario.setIdade(rs.getInt("IDADE"));
				funcionario.setCpf(rs.getString("CPF"));
				funcionario.setEndereco(rs.getString("ENDERECO"));
				
				DadosFuncionario dadosFuncionario = new DadosFuncionario();
				dadosFuncionario.setId(rs.getLong("ID_DADOS_FUNC"));
				dadosFuncionario.setIdfuncionario(rs.getInt("ID"));
				dadosFuncionario.setMaxauxilioestudo(rs.getFloat("MAX_AUXILIO_ESTUDO"));
				dadosFuncionario.setPercentualreembolsocursos(rs.getInt("PERCENT_REEMBOLSO_CURSO"));
				dadosFuncionario.setFazreembolsoestudo(rs.getBoolean("FAZ_REEMBOLSO_ESTUDO"));
				
				CursoFuncionario cursoFuncionario = new CursoFuncionario();
				cursoFuncionario.setId(rs.getLong("ID_CURSO_FUNCIONARIO"));
				cursoFuncionario.setIdfuncionario(rs.getInt("ID"));
				cursoFuncionario.setIdparcelacurso(rs.getLong("ID_PARCELA"));
				cursoFuncionario.setCurso(rs.getString("CURSO"));
				cursoFuncionario.setInstituicao(rs.getString("INSTITUICAO"));
				cursoFuncionario.setDatainicio(rs.getDate("DATA_INICIO"));
				cursoFuncionario.setDuracaomeses(rs.getInt("DURACAO_MESES"));
				cursoFuncionario.setJustificativa(rs.getString("JUSTIFICATIVA"));
				
				ParcelaCurso parcela = new ParcelaCurso();
				parcela.setId(rs.getLong("ID_PARCELA"));
				parcela.setDatavencimento(rs.getDate("DATA_VENCIMENTO"));
				parcela.setValor(rs.getFloat("VALOR"));
				parcela.setValorreembolso(rs.getFloat("VALOR_REEMBOLSO"));
				parcela.setFlagabaterauxilioestudo(rs.getBoolean("ABATER_AUX_ESTUDO"));
				parcela.setDataaprovacaochefe(rs.getDate("DATA_APR_CHEFE"));
				
				cursoFuncionario.setParcelacurso(parcela);
				dadosFuncionario.setCursofuncionario(cursoFuncionario);
				funcionario.setDadosfuncionario(dadosFuncionario);
				funcionarios.add(funcionario);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, stmt);
		}
		return funcionarios;
	}

	//TODO SALVAR, ATUALIZAR E REMOVER CURSO FUNCIONARIO
	@Override
	public void salvarFuncionario(Funcionario funcionario) {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);

			String query = "insert into FUNCIONARIO (NOME, CPF, ENDERECO, IDADE) values (?, ?, ?, ?)";
			stmt = connection.prepareStatement(query, new String[] { "ID"} );
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			stmt.setString(3, funcionario.getEndereco());
			stmt.setInt(4, funcionario.getIdade());
			int ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				funcionario.setId(rs.getLong(1));
			}
			
			query = "insert into DADOS_FUNCIONARIO (ID_FUNCIONARIO, MAX_AUXILIO_ESTUDO, PERCENT_REEMBOLSO_CURSO, FAZ_REEMBOLSO_ESTUDO) values (?, ?, ?, ?)";
			stmt = connection.prepareStatement(query);
			stmt.setLong(1, funcionario.getId());
			stmt.setFloat(2, funcionario.getDadosfuncionario().getMaxauxilioestudo());
			stmt.setInt(3, funcionario.getDadosfuncionario().getPercentualreembolsocursos());
			stmt.setInt(4, funcionario.getDadosfuncionario().getFazreembolsoestudo() == true ? 1 : 0);
			ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
			
			query = "insert into PARCELA_CURSO (DATA_VENCIMENTO, VALOR, VALOR_REEMBOLSO, ABATER_AUX_ESTUDO, DATA_APR_CHEFE) values (?, ?, ?, ?, ?)";
			stmt = connection.prepareStatement(query, new String[] { "ID_PARCELA"} );
			stmt.setDate(1, new java.sql.Date(funcionario.getDadosfuncionario().getCursofuncionario().getParcelacurso().getDatavencimento().getTime()));
			stmt.setFloat(2, funcionario.getDadosfuncionario().getCursofuncionario().getParcelacurso().getValor());
			stmt.setFloat(3, funcionario.getDadosfuncionario().getCursofuncionario().getParcelacurso().getValorreembolso());
			stmt.setInt(4, funcionario.getDadosfuncionario().getCursofuncionario().getParcelacurso().getFlagabaterauxilioestudo() == true ? 1 : 0);
			stmt.setDate(5, new java.sql.Date(funcionario.getDadosfuncionario().getCursofuncionario().getParcelacurso().getDataaprovacaochefe().getTime()));
			ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				funcionario.getDadosfuncionario().getCursofuncionario().setIdparcelacurso(rs.getLong(1));
			}
			
			query = "insert into CURSO_FUNCIONARIO (ID_FUNCIONARIO, ID_PARCELA, CURSO, INSTITUICAO, DATA_INICIO, DURACAO_MESES, JUSTIFICATIVA) values (?, ?, ?, ?, ?, ?, ?)";
			stmt = connection.prepareStatement(query);
			stmt.setLong(1, funcionario.getId());
			stmt.setLong(2, funcionario.getDadosfuncionario().getCursofuncionario().getIdparcelacurso());
			stmt.setString(3, funcionario.getDadosfuncionario().getCursofuncionario().getCurso());
			stmt.setString(4, funcionario.getDadosfuncionario().getCursofuncionario().getInstituicao());
			stmt.setDate(5, new java.sql.Date(funcionario.getDadosfuncionario().getCursofuncionario().getDatainicio().getTime()));
			stmt.setInt(6, funcionario.getDadosfuncionario().getCursofuncionario().getDuracaomeses());
			stmt.setString(7, funcionario.getDadosfuncionario().getCursofuncionario().getJustificativa());
			ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RuntimeException(e.getMessage(), e);
			}
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(connection, stmt);
		}
	}

	@Override
	public void atualizarFuncionario(Funcionario funcionario) {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			
			String query = "UPDATE FUNCIONARIO SET NOME = ?, CPF = ?, ENDERECO = ?, IDADE = ? WHERE ID = ?";
			stmt = connection.prepareStatement(query);
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			stmt.setString(3, funcionario.getEndereco());
			stmt.setInt(4, funcionario.getIdade());
			stmt.setLong(5, funcionario.getId());
			int ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
			
			query = "UPDATE DADOS_FUNCIONARIO SET MAX_AUXILIO_ESTUDO = ?, PERCENT_REEMBOLSO_CURSO = ?, FAZ_REEMBOLSO_ESTUDO = ? WHERE ID = ?";
			stmt = connection.prepareStatement(query);
			stmt.setFloat(1, funcionario.getDadosfuncionario().getMaxauxilioestudo());
			stmt.setInt(2, funcionario.getDadosfuncionario().getPercentualreembolsocursos());
			stmt.setInt(3, funcionario.getDadosfuncionario().getFazreembolsoestudo() == true ? 1 : 0);
			stmt.setLong(4, funcionario.getDadosfuncionario().getId());
			ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
			
			query = "UPDATE CURSO_FUNCIONARIO SET CURSO = ?, INSTITUICAO = ?, DATA_INICIO = ?, DURACAO_MESES = ?, JUSTIFICATIVA = ? WHERE ID = ?";
			stmt = connection.prepareStatement(query);
			stmt.setString(1, funcionario.getDadosfuncionario().getCursofuncionario().getCurso());
			stmt.setString(2, funcionario.getDadosfuncionario().getCursofuncionario().getInstituicao());
			stmt.setDate(3, new java.sql.Date(funcionario.getDadosfuncionario().getCursofuncionario().getDatainicio().getTime()));
			stmt.setInt(4, funcionario.getDadosfuncionario().getCursofuncionario().getDuracaomeses());
			stmt.setString(5, funcionario.getDadosfuncionario().getCursofuncionario().getJustificativa());
			stmt.setLong(6, funcionario.getDadosfuncionario().getCursofuncionario().getId());
			ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
			
			query = "UPDATE PARCELA_CURSO SET DATA_VENCIMENTO = ?, VALOR = ?, VALOR_REEMBOLSO = ?, ABATER_AUX_ESTUDO = ?, DATA_APR_CHEFE = ? WHERE ID_PARCELA = ?";
			stmt = connection.prepareStatement(query, new String[] { "ID_PARCELA"} );
			stmt.setDate(1, new java.sql.Date(funcionario.getDadosfuncionario().getCursofuncionario().getParcelacurso().getDatavencimento().getTime()));
			stmt.setFloat(2, funcionario.getDadosfuncionario().getCursofuncionario().getParcelacurso().getValor());
			stmt.setFloat(3, funcionario.getDadosfuncionario().getCursofuncionario().getParcelacurso().getValorreembolso());
			stmt.setInt(4, funcionario.getDadosfuncionario().getCursofuncionario().getParcelacurso().getFlagabaterauxilioestudo() == true ? 1 : 0);
			stmt.setDate(5, new java.sql.Date(funcionario.getDadosfuncionario().getCursofuncionario().getParcelacurso().getDataaprovacaochefe().getTime()));
			stmt.setLong(6, funcionario.getDadosfuncionario().getCursofuncionario().getParcelacurso().getId());
			ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RuntimeException(e.getMessage(), e);
			}
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(connection, stmt);
		}
	}

	@Override
	public void removerFuncionario(Funcionario funcionario) {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			
			String query = "DELETE from CURSO_FUNCIONARIO WHERE ID_FUNCIONARIO = ?";
			stmt = connection.prepareStatement(query);
			stmt.setLong(1, funcionario.getId());
			int ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
			
			query = "DELETE from PARCELA_CURSO WHERE ID_PARCELA = ?";
			stmt = connection.prepareStatement(query);
			stmt.setLong(1, funcionario.getDadosfuncionario().getCursofuncionario().getParcelacurso().getId());
			ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);

			query = "DELETE from DADOS_FUNCIONARIO WHERE ID_FUNCIONARIO = ?";
			stmt = connection.prepareStatement(query);
			stmt.setLong(1, funcionario.getId());
			ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
			
			query = "DELETE from FUNCIONARIO WHERE id = ?";
			stmt = connection.prepareStatement(query);
			stmt.setLong(1, funcionario.getId());
			ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RuntimeException(e.getMessage(), e);
			}
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(connection, stmt);
		}
	}
	
	@Override
	public List<Reembolso> getReembolsos() {
		List<Reembolso> reembolsos = new ArrayList<Reembolso>();
		
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = getConnection();

			String query = "select * from reembolso";
			stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Reembolso reembolso = new Reembolso();
				reembolso.setId(rs.getLong("ID_REEMBOLSO"));
				reembolso.setIdparcelacurso(rs.getLong("ID_PARCELA"));
				reembolso.setDatapedido(rs.getDate("DATA_PEDIDO"));
				reembolso.setDataaprovacaochefe(rs.getDate("DATA_APROVACAO_CHEFE"));
				reembolso.setDataaprovacaodiretor(rs.getDate("DATA_APROVACAO_DIRETOR"));
				reembolso.setDataaprovacaofinanc(rs.getDate("DATA_APROVACAO_FINANC"));
				reembolso.setDatapagamento(rs.getDate("DATA_PAGAMENTO"));
				reembolso.setFinalidade(rs.getString("FINALIDADE"));
				reembolso.setValorreembolso(rs.getFloat("VALOR_REEMBOLSO"));
				reembolso.setDatacontabilizacao(rs.getDate("DATA_CONTABILIZACAO"));
				reembolso.setTiporeembolso(rs.getString("TIPO_REEMBOLSO"));
				
				reembolsos.add(reembolso);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, stmt);
		}
		return reembolsos;
	}

	@Override
	public void atualizarReembolso(Reembolso reembolso) {
		
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			
			String query = "UPDATE REEMBOLSO SET ID_PARCELA = ?, DATA_PEDIDO = ?, DATA_APROVACAO_CHEFE = ?, DATA_APROVACAO_DIRETOR = ?, DATA_APROVACAO_FINANC = ?, DATA_PAGAMENTO = ?, FINALIDADE = ?, VALOR_REEMBOLSO = ?, DATA_CONTABILIZACAO = ?, TIPO_REEMBOLSO = ? WHERE ID_REEMBOLSO = ?";
			stmt = connection.prepareStatement(query);
			stmt.setLong(1, reembolso.getIdparcelacurso());
			stmt.setDate(2, new java.sql.Date(reembolso.getDatapedido().getTime()));
			stmt.setDate(3, new java.sql.Date(reembolso.getDataaprovacaochefe().getTime()));
			stmt.setDate(4, new java.sql.Date(reembolso.getDataaprovacaodiretor().getTime()));
			stmt.setDate(5, new java.sql.Date(reembolso.getDataaprovacaofinanc().getTime()));
			stmt.setDate(6, new java.sql.Date(reembolso.getDatapagamento().getTime()));
			stmt.setString(7, reembolso.getFinalidade());
			stmt.setFloat(8, reembolso.getValorreembolso());
			stmt.setDate(9, new java.sql.Date(reembolso.getDatacontabilizacao().getTime()));
			stmt.setString(10, reembolso.getTiporeembolso());
			stmt.setLong(11, reembolso.getId());
			
			int ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
						
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RuntimeException(e.getMessage(), e);
			}
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(connection, stmt);
		}
		
	}

	@Override
	public void salvarReembolso(Reembolso reembolso) {
		
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			
			String query = "INSERT into REEMBOLSO  (ID_PARCELA, DATA_PEDIDO, DATA_APROVACAO_CHEFE, DATA_APROVACAO_DIRETOR, DATA_APROVACAO_FINANC, DATA_PAGAMENTO, FINALIDADE, VALOR_REEMBOLSO, DATA_CONTABILIZACAO, TIPO_REEMBOLSO) values (?,?,?,?,?,?,?,?,?,?)";
			stmt = connection.prepareStatement(query);
			stmt.setLong(1, reembolso.getIdparcelacurso());
			stmt.setDate(2, new java.sql.Date(reembolso.getDatapedido().getTime()));
			stmt.setDate(3, new java.sql.Date(reembolso.getDataaprovacaochefe().getTime()));
			stmt.setDate(4, new java.sql.Date(reembolso.getDataaprovacaodiretor().getTime()));
			stmt.setDate(5, new java.sql.Date(reembolso.getDataaprovacaofinanc().getTime()));
			stmt.setDate(6, new java.sql.Date(reembolso.getDatapagamento().getTime()));
			stmt.setString(7, reembolso.getFinalidade());
			stmt.setFloat(8, reembolso.getValorreembolso());
			stmt.setDate(9, new java.sql.Date(reembolso.getDatacontabilizacao().getTime()));
			stmt.setString(10, reembolso.getTiporeembolso());
			
			int ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
						
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RuntimeException(e.getMessage(), e);
			}
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(connection, stmt);
		}
		
	}

	@Override
	public void removerReembolso(Reembolso reembolso) {
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			
			String query = "delete from reembolso where ID_REEMBOLSO = ?";
			stmt = connection.prepareStatement(query);
			stmt.setLong(1, reembolso.getId());
			int ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
						
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RuntimeException(e.getMessage(), e);
			}
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(connection, stmt);
		}
		
	}
	
	@Override
	public List<TipoDespesa> getTipoDespesas() {
		List<TipoDespesa> tipoDespesas = new ArrayList<TipoDespesa>();
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = getConnection();

			String query = "select * from TIPO_DESPESA";
			stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				TipoDespesa tipoDespesa = new TipoDespesa();
				tipoDespesa.setId(rs.getLong("ID_DESPESA"));
				tipoDespesa.setDescricao(rs.getString("DESCRICAO"));
				tipoDespesas.add(tipoDespesa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, stmt);
		}
		return tipoDespesas;
	}

	@Override
	public void atualizarTipoDespesa(TipoDespesa tipoDespesa) {
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			
			String query = "UPDATE TIPO_DESPESA SET DESCRICAO = ? WHERE ID_DESPESA = ?";
			stmt = connection.prepareStatement(query);
			stmt.setString(1, tipoDespesa.getDescricao());
			stmt.setLong(2, tipoDespesa.getId());
			int ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
						
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RuntimeException(e.getMessage(), e);
			}
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(connection, stmt);
		}
		
	}

	@Override
	public void salvarTipoDespesa(TipoDespesa tipoDespesa) {
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			
			String query = "insert into TIPO_DESPESA (DESCRICAO) values (?)";
			stmt = connection.prepareStatement(query);
			stmt.setString(1, tipoDespesa.getDescricao());
			int ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
						
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RuntimeException(e.getMessage(), e);
			}
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(connection, stmt);
		}
		
	}

	@Override
	public void removerTipoDespesa(TipoDespesa tipoDespesa) {
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			
			String query = "delete from TIPO_DESPESA where ID_DESPESA = ?";
			stmt = connection.prepareStatement(query);
			stmt.setLong(1, tipoDespesa.getId());
			int ret = stmt.executeUpdate();
			System.out.println("ret = " + ret);
						
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RuntimeException(e.getMessage(), e);
			}
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			close(connection, stmt);
		}
	}

}
