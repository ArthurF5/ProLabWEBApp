package com.moliveiralucas.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.moliveiralucas.modelo.Endereco;
import com.moliveiralucas.modelo.Exame;
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
		return negocio.cadastrarUsuario(usr).toString();
	}

	@RequestMapping(value = "/cadAdmin/{usuario}_{senha}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String cadAdmin(@PathVariable String usuario, @PathVariable String senha){
		Usuario usr = new Usuario();
		usr.setUsuario(usuario);
		usr.setSenha(senha);
		usr.setPermissao(0);
		return negocio.cadAdmin(usr).toString();
	}

	@RequestMapping(value = "/cadExame/{exame}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String cadExame(@PathVariable String exame) {
		Exame mExame = new Exame();
		mExame.setExame(exame);
		return negocio.cadExame(mExame).toString();
	}

	@RequestMapping(value = "/cadLabor/{lab}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String cadLabor(@PathVariable String lab) {
		Laboratorio laboratorio = new Laboratorio();
		laboratorio.setLaboratorio(lab);
		return negocio.cadLabor(laboratorio).toString();
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
		return negocio.cadFilial(lab, end).toString();
	}

	@RequestMapping(value = "/cadExameLab/{labID}_{exameID}_{valor}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String cadExameLab(@PathVariable Integer labID, @PathVariable Integer exameID, @PathVariable String valor) {
		Laboratorio lab = new Laboratorio();
		Exame exame = new Exame();
		lab.setLabID(labID);
		exame.setExameID(exameID);
		exame.setValor(Double.parseDouble(valor));
		return negocio.atrExameLaboratorio(lab, exame).toString();
	}

	@RequestMapping(value = "/attAdmin/{usuario}_{senha}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String attAdmin(@PathVariable String usuario, @PathVariable String senha) {
		Usuario usr = new Usuario();
		usr.setUsuario(usuario);
		usr.setSenha(senha);
		return negocio.attAdmin(usr).toString();
	}

	@RequestMapping(value = "/attExame/{exame}_{exameID}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String attExame(@PathVariable String exame, @PathVariable Integer exameID) {
		Exame mExame = new Exame();
		mExame.setExame(exame);
		mExame.setExameID(exameID);
		return negocio.attExame(mExame).toString();
	}

	@RequestMapping(value = "/attLaboratorio/{laboratorio}_{labID}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String attLaboratorio(@PathVariable String laboratorio, @PathVariable Integer labID) {
		Laboratorio lab = new Laboratorio();
		lab.setLaboratorio(laboratorio);
		lab.setLabID(labID);
		return negocio.attLabor(lab).toString();
	}
	
	@RequestMapping(value = "/excluirUsr/{usrID}", method = RequestMethod.GET, produces = "application/json")
	public String excluirUsr(@PathVariable Integer usrID) {
		Usuario usr = new Usuario();
		usr.setId(usrID);
		return negocio.excluiUsr(usr).toString();
	}
	
	@RequestMapping(value = "/excluirExame/{exameID}", method = RequestMethod.GET, produces = "application/json")
	public String excluirExame(@PathVariable Integer exameID) {
		return negocio.excluiExame(exameID).toString();
	}
	
	@RequestMapping(value = "/excluirLaboratorio/{labID}", method = RequestMethod.GET, produces = "application/json")
	public String excluirLaboratorio(@PathVariable Integer labID) {
		return negocio.excluiLaboratorio(labID).toString();
	}
	
	@RequestMapping(value = "/excluirExameLaboratorio/{labID}_{exameID}", method = RequestMethod.GET, produces = "application/json")
	public String excluirExameLaboratorio(@PathVariable Integer labID, @PathVariable Integer exameID) {
		Laboratorio lab = new Laboratorio();
		Exame ex = new Exame();
		lab.setLabID(labID);
		return negocio.excluiExameLaboratorio(lab, ex).toString();
	}
	
	@RequestMapping(value = "/searchUsr/{usuario}", method = RequestMethod.GET, produces = "application/json")
	public String searchUsr(@PathVariable String usuario) {
		Usuario usr = new Usuario();
		usr.setUsuario(usuario);
		usr = negocio.consultaUsuario(usr);
		return gson.toJson(usr);
	}

	@RequestMapping(value = "/searchLabs/", method = RequestMethod.GET, produces = "application/json")
	public String searchLabs() {
		return gson.toJson(negocio.listarLaboratorios());
	}

	@RequestMapping(value = "/searchExame/{exame}", method = RequestMethod.GET, produces = "application/json")
	public String searchExame(@PathVariable String exame) {
		Exame mExame = new Exame();
		mExame.setExame(exame);
		mExame = negocio.consultaExame(mExame);
		return gson.toJson(mExame);
	}

	@RequestMapping(value = "/searchAllExames/",method = RequestMethod.GET, produces = "application/json")
	public String searchAllExames() {
		return gson.toJson(negocio.listarExames());
	}

	@RequestMapping(value = "/searchLabExame/{labID}", method = RequestMethod.GET, produces = "application/json")
	public String searchExamePorLab(@PathVariable Integer labID) {
		return gson.toJson(negocio.buscarExames(labID));
	}

	@RequestMapping(value = "/searchCidade/{cidade}", method = RequestMethod.GET, produces = "application/json")
	public String searchCidade(@PathVariable String cidade) {
		return gson.toJson(negocio.buscarCidade(cidade));
	}

	@RequestMapping(value = "/searchEstado/{estado}", method = RequestMethod.GET, produces = "application/json")
	public String searchEstado(@PathVariable String estado) {
		return gson.toJson(negocio.buscarEstado(estado));
	}

	@RequestMapping(value = "/searchAllUF/",method = RequestMethod.GET, produces = "application/json")
	public String searchAllUF() {
		return gson.toJson(negocio.listarEstados());
	}

	@RequestMapping(value = "/searchCidadePorEstado/{estadoID}", method = RequestMethod.GET, produces = "application/json")
	public String searchCidadeEstado(@PathVariable Integer estadoID) {
		return gson.toJson(negocio.buscarCidadePorEstado(estadoID));
	}
	
	@RequestMapping(value = "/searchFilial/{labID}", method = RequestMethod.GET, produces = "application/json")
	public String searchFilial(@PathVariable Integer labID) {
		return gson.toJson(negocio.buscarExames(labID));
	}

	@RequestMapping(value = "/SearchLabPorCidade/{cidadeID}", method = RequestMethod.GET, produces = "application/json")
	public String searchLabCidade(@PathVariable Integer cidadeID) {
		return gson.toJson(negocio.buscaLaboratorioCidade(cidadeID));
	}
	
	@RequestMapping(value = "/searchLabPorID/{labID}", method = RequestMethod.GET, produces = "application/json")
	public String searchLabPorID(@PathVariable Integer labID) {
		return gson.toJson(negocio.buscaLaboratorioPorID(labID)).toString();
	}
	
	@RequestMapping(value = "/searchUFPorID/{ufID}", method = RequestMethod.GET, produces = "application/json")
	public String searchUFPorID(@PathVariable Integer ufID) {
		return gson.toJson(negocio.buscaUFPorID(ufID)).toString();
	}
}

