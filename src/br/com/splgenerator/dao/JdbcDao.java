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

			//String query = "select f.*, df.id as ID_DADOS_FUNC, df.*, cf.id as ID_CURSO_FUNCIONARIO, cf.*, pc.* from funcionario f, dados_funcionario df, curso_funcionario cf, parcela_curso pc where f.id = df.id_funcionario and f.id = cf.id_funcionario and cf.id_parcela = pc.id_parcela";
			String query = "select * from FUNCIONARIO";
			stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(rs.getInt("ID"));
				funcionario.setNome(rs.getString("NOME"));
				funcionario.setIdade(rs.getInt("IDADE"));
				funcionario.setCpf(rs.getString("CPF"));
				funcionario.setEndereco(rs.getString("ENDERECO"));
				
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

			String query = "DELETE from FUNCIONARIO WHERE id = ?";
			stmt = connection.prepareStatement(query);
			stmt.setLong(1, funcionario.getId());
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
