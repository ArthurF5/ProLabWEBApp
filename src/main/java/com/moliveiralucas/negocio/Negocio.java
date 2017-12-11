package com.moliveiralucas.negocio;

import java.util.ArrayList;

import com.moliveiralucas.modelo.Cidade;
import com.moliveiralucas.modelo.Endereco;
import com.moliveiralucas.modelo.Estado;
import com.moliveiralucas.modelo.Exame;
import com.moliveiralucas.modelo.ExameLaboratorio;
import com.moliveiralucas.modelo.Laboratorio;
import com.moliveiralucas.modelo.Usuario;
import com.moliveiralucas.persistencia.CidadePersist;
import com.moliveiralucas.persistencia.EnderecoPersist;
import com.moliveiralucas.persistencia.EstadoPersist;
import com.moliveiralucas.persistencia.ExameLaboratorioPersist;
import com.moliveiralucas.persistencia.ExamePersist;
import com.moliveiralucas.persistencia.LaboratorioPersist;
import com.moliveiralucas.persistencia.UsuarioPersist;

public class Negocio {

	EnderecoPersist endPersist = new EnderecoPersist();
	ExamePersist examePersist = new ExamePersist();
	LaboratorioPersist labPersist = new LaboratorioPersist();
	UsuarioPersist usrPersist = new UsuarioPersist();
	ExameLaboratorioPersist mExameLaboratorioPersist = new ExameLaboratorioPersist();
	CidadePersist mCidadePersist = new CidadePersist();
	EstadoPersist mEstadoPersist = new EstadoPersist();

	/**
	 * CodRetorno:	1 - Sem Permissão
	 * 				2 - Operação realizada com sucesso
	 * 				3 - Objeto Nulo
	 * 				4 - Já possui Cadastro
	 * 				5 - Houve algum erro ao realizar a operação no banco verificar log
	 * 				6 - Usuario ou senha Invalidos
	 */

	public Integer cadAdmin(Usuario usr) {
		Integer incluir, codRetorno = 1;
		if(usr != null) {
			usr.setPermissao(0);
			incluir = usrPersist.incluir(usr);
			if(incluir == 1) {
				codRetorno = 2; // Cadastrado com sucesso
			} else if(incluir == 2) {
				codRetorno = 4; // Ja possui Cadastro
			} else if(incluir == 0) {
				codRetorno = 5; // Houve algum erro ao cadastrar no banco verificar log
			}
		} else {
			codRetorno = 3;
		}
		return codRetorno;
	}
	public Integer cadExame(Exame exame) {
		Integer incluir, codRetorno = 1;
		if(exame != null) {
			incluir = examePersist.incluir(exame);
			if(incluir == 1) {
				codRetorno = 2; // Cadastrado com sucesso
			} else if(incluir == 2) {
				codRetorno = 4; // Ja possui Cadastro
			} else if(incluir == 0) {
				codRetorno = 5; // Houve algum erro ao cadastrar no banco verificar log
			}

		} else {
			codRetorno = 3;
		}
		return codRetorno;
	}
	public Integer cadLabor(Laboratorio lab) {
		Integer incluir, codRetorno = 1;
		if(lab != null) {
			incluir = labPersist.incluir(lab);
			if(incluir == 1) {
				codRetorno = 2; // Cadastrado com sucesso
			} else if(incluir == 2) {
				codRetorno = 4; // Ja possui Cadastro
			} else if(incluir == 0) {
				codRetorno = 5; // Houve algum erro ao cadastrar no banco verificar log
			}
		} else {
			codRetorno = 3;
		}
		return codRetorno;
	}
	public Integer cadFilial(Laboratorio lab, Endereco endereco) {
		Integer incluir, codRetorno = 1;
		if(endereco != null && lab != null) {
			incluir = endPersist.incluir(lab, endereco);;
			if(incluir == 1) {
				codRetorno = 2; // Cadastrado com sucesso
			} else if(incluir == 2) {
				codRetorno = 4; // Ja possui Cadastro
			} else if(incluir == 0) {
				codRetorno = 5; // Houve algum erro ao cadastrar no banco verificar log
			}
		} else {
			codRetorno = 3;
		}
		return codRetorno;
	}
	public Integer atrExameLaboratorio(Laboratorio lab, Exame exame) {
		Integer incluir, codRetorno = 1;
		if(exame != null && lab != null) {
			incluir = mExameLaboratorioPersist.cadastrarExameLaboratorio(exame, lab);
			if(incluir == 1) {
				codRetorno = 2; // Cadastrado com sucesso
			} else if(incluir == 2) {
				codRetorno = 4; // Ja possui Cadastro
			} else if(incluir == 0) {
				codRetorno = 5; // Houve algum erro ao cadastrar no banco verificar log
			}
		} else {
			codRetorno = 3;
		}
		return codRetorno;
	}

	public Integer attAdmin(Usuario usr) {
		Boolean atualizar;
		Integer codRetorno = 1;
		if(usr != null) {
			atualizar = usrPersist.alterar(usr);
			if(atualizar) {
				codRetorno = 2;
			} else {
				codRetorno = 5;
			}
		} else {
			codRetorno = 3;
		}
		return codRetorno;
	}

	public Integer attExame(Exame exame) {
		Boolean atualizar;
		Integer codRetorno = 1;
		if(exame != null){
			atualizar = examePersist.alterar(exame);
			if(atualizar) {
				codRetorno = 2;
			} else {
				codRetorno = 5;
			}
		}
		return codRetorno;
	}
	public Integer attLabor(Laboratorio lab) {
		Boolean atualizar;
		Integer codRetorno = 1;
		if(lab != null) {
			atualizar = labPersist.alterar(lab);
			if(atualizar) {
				codRetorno = 2;
			} else {
				codRetorno = 5;
			}
		}
		return codRetorno;
	}

	public Integer excluiUsr(Usuario usr) {
		Integer excluir, codRetorno = 1;
		if(usr != null) {
			excluir = usrPersist.excluir(usr);
			if(excluir == 1) {
				codRetorno = 2;
			} else {
				codRetorno = 5;
			}
		} else {
			codRetorno = 3;
		}
		return codRetorno;
	}
	public Integer excluiExame(Integer id) {
		Integer excluir, codRetorno = 1;
		excluir = examePersist.excluir(id);
		if(excluir == 1) {
			codRetorno = 2;
		} else {
			codRetorno = 5;
		}
		return codRetorno;
	}
	public Integer excluiLaboratorio(Integer id) {
		Integer excluir, codRetorno = 1;
		excluir = labPersist.excluir(id);
		if(excluir == 1) {
			codRetorno = 2;
		} else {
			codRetorno = 5;
		}
		return codRetorno;
	}
	
	public Integer excluiExameLaboratorio(Laboratorio lab, Exame exame) {
		Integer excluir, codRetorno = 1;
		excluir = mExameLaboratorioPersist.excluirExameLaboratorio(exame, lab);
		if(excluir == 1) {
			codRetorno = 2;
		} else {
			codRetorno = 5;
		}
		return codRetorno;
	}

	public Usuario consultaUsuario(Usuario usr) {
		return usrPersist.consulta(usr);
	}

	public ArrayList<Laboratorio> listarLaboratorios(){
		return labPersist.listar();
	}

	public Exame consultaExame(Exame ex) {
		return examePersist.consulta(ex.getExame());
	}

	public ArrayList<Exame> listarExames(){
		return examePersist.listar();
	}

	public ArrayList<ExameLaboratorio> buscarExames(Integer id){
		return mExameLaboratorioPersist.buscarExameLaboratorio(id);
	}

	/**
	 * Realiza o cadastro de um novo usuario no sistema (Usuario sem permissão de administrador)
	 * @param usr
	 * @return
	 */
	public Integer cadastrarUsuario(Usuario usr) {
		Integer codRetorno = 0;
		usr.setPermissao(1);
		codRetorno = usrPersist.incluir(usr);
		if(codRetorno == 1) {
			codRetorno = 2;
		} else if(codRetorno == 2){
			codRetorno = 4;
		} else {
			codRetorno = 5;
		}
		return codRetorno;
	}
	public Usuario login(Usuario usr) {
		Usuario usuario = consultaUsuario(usr);
		if(usr.getUsuario().equals(usuario.getUsuario()) && usr.getSenha().equals(usuario.getSenha())) {
			return usuario;
		}else {
			usuario = new Usuario();
			usuario.setUsuario("");
			usuario.setId(0);
			usuario.setPermissao(0);
			usuario.setSenha("");
			return usuario;
		}	
	}

	public ArrayList<Cidade> listarCidade(String parametroBusca){
		return mCidadePersist.listarTodos(parametroBusca);
	}
	
	public Cidade buscarCidade(String parametroBusca) {
		return mCidadePersist.consulta(parametroBusca);
	}
	
	public ArrayList<Estado> listarEstados(){
		return mEstadoPersist.listarTodos();
	}
	
	public Estado buscarEstado(String parametroBusca) {
		return mEstadoPersist.consulta(parametroBusca);
	}
	
	public ArrayList<Cidade> buscarCidadePorEstado(Integer estadoID){
		return mCidadePersist.buscarPorEstado(estadoID);
	}
	
	public ArrayList<Endereco> buscaLaboratorioCidade(Integer cidadeID){
		return endPersist.buscarLaboratorioCidade(cidadeID);
	}
	
	public String buscaLaboratorioPorID(Integer id) {
		return labPersist.buscaPorID(id);
	}
	
	public String buscaUFPorID(Integer id) {
		return mEstadoPersist.buscaUFPorID(id);
	}
}
