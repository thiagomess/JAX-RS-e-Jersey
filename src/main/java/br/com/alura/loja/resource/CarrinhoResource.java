package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

@Path("carrinhos")
public class CarrinhoResource {

	@GET
	@Path("{id}") // Recebe o caminho exemplo :localhost:8080/carrinhos/1
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id) { // captura o caminho e passa para a variavel
		Carrinho carrinho = new CarrinhoDAO().busca(id);

		return carrinho.toXML();
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(String conteudo) {
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		new CarrinhoDAO().adiciona(carrinho);
		
		URI uri = URI.create("/carrinhos/"+carrinho.getId());
		return Response.created(uri).build();
	}
	
	@Path("{id}/produtos/{produtoId}")
	@DELETE
	public Response removeProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId) {
		
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		carrinho.remove(produtoId);
		return Response.ok().build();
		
	}
	
	@Path("{id}/produtos/{produtoId}/quantidade")
	@PUT
	public Response alteraProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId, String conteudo) {
		
		Carrinho carrinho = new CarrinhoDAO().busca(id);

		Produto produto = (Produto) new XStream().fromXML(conteudo);
		carrinho.trocaQuantidade(produto);
		
		
		
		return Response.ok().build();
		
	}
	
	
	
	
	/*
	 * //Pansando Parametro
	 * 
	 * @GET
	 * 
	 * @Produces(MediaType.APPLICATION_XML) // QueryParam captura parametro exem.
	 * http://localhost:8080/carrinhos?id=1 public String
	 * buscaParametro(@QueryParam("id") long id) { Carrinho carrinho = new
	 * CarrinhoDAO().busca(id); return carrinho.toXML(); }
	 */
	
	

}
