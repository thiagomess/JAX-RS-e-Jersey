package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import br.com.alura.loja.modelo.Projeto;

public class ProjetoTest {
	
	
	private HttpServer server;

	@Before
	public void inicializaServidor() {
		server = Servidor.inicializaServidor();
	}
	
	@After
	public void mataServidor() {
		server.stop();
	}
	
	@Test
	public void testaQueBuscarUmProjetoTrazOProjetoEsperado() {
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		String conteudo = target.path("/projetos/1").request().get(String.class);
		
		Projeto projeto = new Gson().fromJson(conteudo, Projeto.class);
		
		Assert.assertEquals("Minha loja", projeto.getNome());
	}
	
	
	@Test
	public void deveAdicionarUmNovoProjeto() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		
        Projeto projeto = new Projeto();
        projeto.setAnoDeInicio(2019);
        projeto.setNome("Loja Teste");
        String json = projeto.toJson();
        
        
        Entity<String> entity = Entity.entity(json, MediaType.APPLICATION_JSON);

        Response response = target.path("/projetos").request().post(entity);
        
        Assert.assertEquals(201, response.getStatus());
        
        String conteudo = client.target(response.getHeaderString("Location")).request().get(String.class);
        Assert.assertTrue(conteudo.contains("Loja Teste"));
	}

}
