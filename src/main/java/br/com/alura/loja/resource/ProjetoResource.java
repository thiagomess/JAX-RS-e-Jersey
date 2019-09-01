package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;

@Path("projetos")
public class ProjetoResource {
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public String busca(@PathParam("id") long id) {
		Projeto projeto = new ProjetoDAO().busca(id);
		
		return projeto.toJson();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response adiciona(String conteudo) {
		Projeto projeto = new Gson().fromJson(conteudo, Projeto.class);
		new ProjetoDAO().adiciona(projeto);
		
		URI uri = URI.create("/projetos/"+projeto.getId());
		return Response.created(uri).build();
	}
	

}
