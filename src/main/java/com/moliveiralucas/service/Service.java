package com.moliveiralucas.service;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.moliveiralucas.modelo.Endereco;
import com.moliveiralucas.modelo.Exame;
import com.moliveiralucas.modelo.ExameLaboratorio;
import com.moliveiralucas.modelo.Laboratorio;
import com.moliveiralucas.modelo.Usuario;
import com.moliveiralucas.negocio.Negocio;

@RestController
@RequestMapping("/service")
public class Service {

	Negocio negocio = new Negocio();
	Gson gson = new Gson();

	@RequestMapping(value = "/login/{usuario}_{senha}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String login(@PathVariable String usuario, @PathVariable String senha) {
		Usuario usr = new Usuario();
		usr.setUsuario(usuario);
		usr.setSenha(senha);
		return gson.toJson(negocio.login(usr));
	}

	@RequestMapping(value = "/newUser/{usuario}_{senha}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String cadastrarUsuario(@PathVariable String usuario, @PathVariable String senha) {
		Usuario usr = new Usuario();
		usr.setUsuario(usuario);
		usr.setSenha(senha);
		usr.setPermissao(1);
		Integer retorno = negocio.cadastrarUsuario(usr);
		String resposta = null;
		switch(retorno) {
		case 1:
			resposta = "Sem Permissão";
			break;
		case 2:
			resposta = "Operação realizada com sucesso";
			break;
		case 3:
			resposta = "Objeto Nulo";
			break;
		case 4:
			resposta = "Já possui Cadastro";
			break;
		case 5:
			resposta = "Houve algum erro ao realizar a operação no banco verificar log";
			break;
		case 6:
			resposta = "Usuario ou senha Invalidos";
			break;
		}
		return resposta;
	}

	@RequestMapping(value = "/cadAdmin/{usuario}_{senha}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String cadAdmin(@PathVariable String usuario, @PathVariable String senha){
		Usuario usr = new Usuario();
		usr.setUsuario(usuario);
		usr.setSenha(senha);
		usr.setPermissao(0);
		Integer retorno = negocio.cadAdmin(usr);
		String resposta = null;
		switch(retorno) {
		case 1:
			resposta = "Sem Permissão";
			break;
		case 2:
			resposta = "Operação realizada com sucesso";
			break;
		case 3:
			resposta = "Objeto Nulo";
			break;
		case 4:
			resposta = "Já possui Cadastro";
			break;
		case 5:
			resposta = "Houve algum erro ao realizar a operação no banco verificar log";
			break;
		case 6:
			resposta = "Usuario ou senha Invalidos";
			break;
		}
		return resposta;
	}

	@RequestMapping(value = "/cadExame/{exame}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String cadExame(@PathVariable String exame) {
		Exame mExame = new Exame();
		mExame.setExame(exame);
		Integer retorno = negocio.cadExame(mExame);
		String resposta = null;
		switch(retorno) {
		case 1:
			resposta = "Sem Permissão";
			break;
		case 2:
			resposta = "Operação realizada com sucesso";
			break;
		case 3:
			resposta = "Objeto Nulo";
			break;
		case 4:
			resposta = "Já possui Cadastro";
			break;
		case 5:
			resposta = "Houve algum erro ao realizar a operação no banco verificar log";
			break;
		case 6:
			resposta = "Usuario ou senha Invalidos";
			break;
		}
		return resposta;
	}

	@RequestMapping(value = "/cadLabor/{lab}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String cadLabor(@PathVariable String lab) {
		Laboratorio laboratorio = new Laboratorio();
		laboratorio.setLaboratorio(lab);
		Integer retorno = negocio.cadLabor(laboratorio);
		String resposta = null;
		switch(retorno) {
		case 1:
			resposta = "Sem Permissão";
			break;
		case 2:
			resposta = "Operação realizada com sucesso";
			break;
		case 3:
			resposta = "Objeto Nulo";
			break;
		case 4:
			resposta = "Já possui Cadastro";
			break;
		case 5:
			resposta = "Houve algum erro ao realizar a operação no banco verificar log";
			break;
		case 6:
			resposta = "Usuario ou senha Invalidos";
			break;
		}
		return resposta;
	}

	@RequestMapping(value = "/cadFilial/{labID}_{logradouro}_{numero}_{cidadeID}_{ufID}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String cadFilial(
			@PathVariable Integer labID,
			@PathVariable String logradouro,
			@PathVariable String numero,
			@PathVariable Integer cidadeID,
			@PathVariable Integer ufID) {
		Endereco end = new Endereco();
		Laboratorio lab = new Laboratorio();
		lab.setLabID(labID);
		end.setLogradouro(logradouro);
		end.setNumero(numero);
		end.setCidade(cidadeID);
		end.setEstado(ufID);
		Integer retorno = negocio.cadFilial(lab, end);
		String resposta = null;
		switch(retorno) {
		case 1:
			resposta = "Sem Permissão";
			break;
		case 2:
			resposta = "Operação realizada com sucesso";
			break;
		case 3:
			resposta = "Objeto Nulo";
			break;
		case 4:
			resposta = "Já possui Cadastro";
			break;
		case 5:
			resposta = "Houve algum erro ao realizar a operação no banco verificar log";
			break;
		case 6:
			resposta = "Usuario ou senha Invalidos";
			break;
		}
		return resposta;
	}
	
	@RequestMapping(value = "/atrExameLaboratorio/{labID}_{exameID}_{valor)", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String atrExameLaboratorio(@PathVariable Integer labID, @PathVariable Integer exameID, @PathVariable Double valor) {
		Laboratorio lab = new Laboratorio();
		Exame exame = new Exame();
		lab.setLabID(labID);
		exame.setExameID(exameID);
		exame.setValor(valor);
		Integer retorno = negocio.atrExameLaboratorio(lab, exame);
		String resposta = null;
		switch(retorno) {
		case 1:
			resposta = "Sem Permissão";
			break;
		case 2:
			resposta = "Operação realizada com sucesso";
			break;
		case 3:
			resposta = "Objeto Nulo";
			break;
		case 4:
			resposta = "Já possui Cadastro";
			break;
		case 5:
			resposta = "Houve algum erro ao realizar a operação no banco verificar log";
			break;
		case 6:
			resposta = "Usuario ou senha Invalidos";
			break;
		}
		return resposta;
	}
	
	@RequestMapping(value = "/attAdmin/{usuario}_{senha}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String attAdmin(@PathVariable String usuario, @PathVariable String senha) {
		Usuario usr = new Usuario();
		usr.setUsuario(usuario);
		usr.setSenha(senha);
		Integer retorno = negocio.attAdmin(usr);
		String resposta = null;
		switch(retorno) {
		case 1:
			resposta = "Sem Permissão";
			break;
		case 2:
			resposta = "Operação realizada com sucesso";
			break;
		case 3:
			resposta = "Objeto Nulo";
			break;
		case 4:
			resposta = "Já possui Cadastro";
			break;
		case 5:
			resposta = "Houve algum erro ao realizar a operação no banco verificar log";
			break;
		case 6:
			resposta = "Usuario ou senha Invalidos";
			break;
		}
		return resposta;
	}
	
	@RequestMapping(value = "/attExame/{exame}_{exameID)",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String attExame(@PathVariable String exame, @PathVariable Integer exameID) {
		Exame ex = new Exame();
		ex.setExame(exame);
		ex.setExameID(exameID);
		Integer retorno = negocio.attExame(ex);
		String resposta = null;
		switch(retorno) {
		case 1:
			resposta = "Sem Permissão";
			break;
		case 2:
			resposta = "Operação realizada com sucesso";
			break;
		case 3:
			resposta = "Objeto Nulo";
			break;
		case 4:
			resposta = "Já possui Cadastro";
			break;
		case 5:
			resposta = "Houve algum erro ao realizar a operação no banco verificar log";
			break;
		case 6:
			resposta = "Usuario ou senha Invalidos";
			break;
		}
		return resposta;
	}
	
	@RequestMapping(value = "/attLaboratorio/{laboratorio}_{labID}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String attLaboratorio(@PathVariable String laboratorio, @PathVariable Integer labID) {
		Laboratorio lab = new Laboratorio();
		lab.setLaboratorio(laboratorio);
		lab.setLabID(labID);
		Integer retorno = negocio.attLabor(lab);
		String resposta = null;
		switch(retorno) {
		case 1:
			resposta = "Sem Permissão";
			break;
		case 2:
			resposta = "Operação realizada com sucesso";
			break;
		case 3:
			resposta = "Objeto Nulo";
			break;
		case 4:
			resposta = "Já possui Cadastro";
			break;
		case 5:
			resposta = "Houve algum erro ao realizar a operação no banco verificar log";
			break;
		case 6:
			resposta = "Usuario ou senha Invalidos";
			break;
		}
		return resposta;
	}
	@RequestMapping(value = "/excluirUsr/{usuario}_{usrID}", method = RequestMethod.GET, produces = "application/json")
	public String excluirUsr(@PathVariable String usuario, @PathVariable Integer usrID) {
		Usuario usr = new Usuario();
		usr.setId(usrID);
		usr.setUsuario(usuario);
		Integer retorno = negocio.excluiUsr(usr);
		String resposta = null;
		switch(retorno) {
		case 1:
			resposta = "Sem Permissão";
			break;
		case 2:
			resposta = "Operação realizada com sucesso";
			break;
		case 3:
			resposta = "Objeto Nulo";
			break;
		case 4:
			resposta = "Já possui Cadastro";
			break;
		case 5:
			resposta = "Houve algum erro ao realizar a operação no banco verificar log";
			break;
		case 6:
			resposta = "Usuario ou senha Invalidos";
			break;
		}
		return resposta;
	}
	@RequestMapping(value = "/excluirExame/{exame}_{exameID}", method = RequestMethod.GET, produces = "application/json")
	public String excluirExame(@PathVariable String exame,@PathVariable Integer exameID) {
		Exame ex = new Exame();
		ex.setExame(exame);
		ex.setExameID(exameID);
		Integer retorno = negocio.excluiExame(ex);
		String resposta = null;
		switch(retorno) {
		case 1:
			resposta = "Sem Permissão";
			break;
		case 2:
			resposta = "Operação realizada com sucesso";
			break;
		case 3:
			resposta = "Objeto Nulo";
			break;
		case 4:
			resposta = "Já possui Cadastro";
			break;
		case 5:
			resposta = "Houve algum erro ao realizar a operação no banco verificar log";
			break;
		case 6:
			resposta = "Usuario ou senha Invalidos";
			break;
		}
		return resposta;
	}
	@RequestMapping(value = "/excluirLaboratorio/{laboratorio}_{labID}", method = RequestMethod.GET, produces = "application/json")
	public String excluirLaboratorio(@PathVariable String laboratorio, @PathVariable Integer labID) {
		Laboratorio lab = new Laboratorio();
		lab.setLabID(labID);
		lab.setLaboratorio(laboratorio);
		Integer retorno = negocio.excluiLaboratorio(lab);
		String resposta = null;
		switch(retorno) {
		case 1:
			resposta = "Sem Permissão";
			break;
		case 2:
			resposta = "Operação realizada com sucesso";
			break;
		case 3:
			resposta = "Objeto Nulo";
			break;
		case 4:
			resposta = "Já possui Cadastro";
			break;
		case 5:
			resposta = "Houve algum erro ao realizar a operação no banco verificar log";
			break;
		case 6:
			resposta = "Usuario ou senha Invalidos";
			break;
		}
		return resposta;
	}
	@RequestMapping(value = "/excluirFilial/{labID}_{endID}", method = RequestMethod.GET, produces = "application/json")
	public String excluirFilial(@PathVariable Integer labID, @PathVariable Integer endID) {
		Laboratorio lab = new Laboratorio();
		Endereco end = new Endereco();
		lab.setLabID(labID);
		end.setEndID(endID);
		Integer retorno = negocio.excluiFilial(lab, end);
		String resposta = null;
		switch(retorno) {
		case 1:
			resposta = "Sem Permissão";
			break;
		case 2:
			resposta = "Operação realizada com sucesso";
			break;
		case 3:
			resposta = "Objeto Nulo";
			break;
		case 4:
			resposta = "Já possui Cadastro";
			break;
		case 5:
			resposta = "Houve algum erro ao realizar a operação no banco verificar log";
			break;
		case 6:
			resposta = "Usuario ou senha Invalidos";
			break;
		}
		return resposta;
	}
	@RequestMapping(value = "/excluirExameLaboratorio/{labID}_{exameID}", method = RequestMethod.GET, produces = "application/json")
	public String excluirExameLaboratorio(@PathVariable Integer labID, @PathVariable Integer exameID) {
		Laboratorio lab = new Laboratorio();
		Exame ex = new Exame();
		lab.setLabID(labID);
		ex.setExameID(exameID);
		Integer retorno = negocio.excluiExameLaboratorio(lab, ex);
		String resposta = null;
		switch(retorno) {
		case 1:
			resposta = "Sem Permissão";
			break;
		case 2:
			resposta = "Operação realizada com sucesso";
			break;
		case 3:
			resposta = "Objeto Nulo";
			break;
		case 4:
			resposta = "Já possui Cadastro";
			break;
		case 5:
			resposta = "Houve algum erro ao realizar a operação no banco verificar log";
			break;
		case 6:
			resposta = "Usuario ou senha Invalidos";
			break;
		}
		return resposta;
	}
	@RequestMapping(value = "/searchUsr/{usuario}", method = RequestMethod.GET, produces = "application/json")
	public String searchUsr(@PathVariable String usuario) {
		Usuario usr = new Usuario();
		usr.setUsuario(usuario);
		usr = negocio.consultaUsuario(usr);
		return gson.toJson(usr);
	}
	@RequestMapping(value = "/searchLab/{laboratorio}", method = RequestMethod.GET, produces = "application/json")
	public String searchLab(@PathVariable String laboratorio) {
		Laboratorio lab = new Laboratorio();
		lab.setLaboratorio(laboratorio);
		lab = negocio.consultaLaboratorio(lab);
		return gson.toJson(lab);
	}
	
	@RequestMapping(value = "/searchLabs/{laboratorio}", method = RequestMethod.GET, produces = "application/json")
	public String searchLabs(@PathVariable String laboratorio) {
		return gson.toJson(negocio.listarLaboratorios(laboratorio));
	}
	
	@RequestMapping(value = "/searchExame/{exame}", method = RequestMethod.GET, produces = "application/json")
	public String searchExame(@PathVariable String exame) {
		Exame mExame = new Exame();
		mExame.setExame(exame);
		mExame = negocio.consultaExame(mExame);
		return gson.toJson(mExame);
	}
	
	@RequestMapping(value = "/searchLabExame/{exameID}", method = RequestMethod.GET, produces = "application/json")
	public String searchExamePorLab(@PathVariable Integer exameID) {
		Exame mExame = new Exame();
		mExame.setExameID(exameID);
		ArrayList<ExameLaboratorio> mExameLab = negocio.buscarExames(mExame);
		return gson.toJson(mExameLab);
	}
	
	@RequestMapping(value = "/searchCidade/{cidade}", method = RequestMethod.GET, produces = "application/json")
	public String searchCidade(@PathVariable String cidade) {
		return gson.toJson(negocio.buscarCidade(cidade));
	}
	
	@RequestMapping(value = "/searchEstado/{estado}", method = RequestMethod.GET, produces = "application/json")
	public String searchEstado(@PathVariable String estado) {
		return gson.toJson(negocio.buscarEstado(estado));
	}
	
	@RequestMapping(value = "/searchCidadePorEstado/{estadoID}")
	public String searchCidadeEstado(@PathVariable Integer estadoID) {
		return gson.toJson(negocio.buscarCidadePorEstado(estadoID));
	}
}