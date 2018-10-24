package br.com.alura.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;

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
	@Produces(MediaType.APPLICATION_XML)
	public String adiciona(String conteudo) {
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		new CarrinhoDAO().adiciona(carrinho);
		
		return "<status>sucesso</status>";
	}
	
	
	
	//Pansando Parametro
	@GET
	@Produces(MediaType.APPLICATION_XML)
	// QueryParam captura parametro exem. http://localhost:8080/carrinhos?id=1
	public String buscaParametro(@QueryParam("id") long id) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		return carrinho.toXML();
	}

}
