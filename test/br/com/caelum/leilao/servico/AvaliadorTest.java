package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;

public class AvaliadorTest {
	
	private Avaliador leiloeiro;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;
	private Usuario ana;
	
	@Before
	public void criaAvaliador() {
		this.joao = new Usuario("Joao");
		this.jose = new Usuario("Jose");
		this.maria = new Usuario("Maria");
		this.ana = new Usuario("Ana");
	}
	
	@Test(expected=RuntimeException.class)
	public void naoDeveAvaliarLeilaoSemNenhumLanceDado() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4 novo").constroi();
		
		leiloeiro.avalia(leilao);
	}
	
	@Before
	public void setUp() {
		this.leiloeiro = new Avaliador();
	}
	
	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		// part 1 : scene
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4 Novo")
				.lance(joao, 300.0)
				.lance(jose, 450.0)
				.lance(maria, 500.0)
				.constroi();
		
		// part 2 : action
		leiloeiro.avalia(leilao);
		
		// part 3 : check
		double maiorEsperado = 500.0;
		double menorEsperado = 300.0;
				
		assertThat(leiloeiro.getMaiorLance(), equalTo(maiorEsperado));
		assertThat(leiloeiro.getMenorLance(), equalTo(menorEsperado));
	}
	
	@Test
	public void deveCalcularMedia() {
		// scene
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4 Novo")
				.lance(joao, 300.0)
				.lance(jose, 400.0)
				.lance(maria, 500.0)
				.constroi();
		
		// action
		leiloeiro.avalia(leilao);
		
		// check
		double mediaEsperado = 400.0;
		assertThat(leiloeiro.getMedia(), equalTo(mediaEsperado));
	}
	
	@Test(expected=RuntimeException.class)
	public void mediaZeroLance() {
		// scene
		Leilao leilao = new CriadorDeLeilao().para("Iphone X").constroi();
		
		// action
		leiloeiro.avalia(leilao);
	}
	
	@Test
	public void deveEntenderLeilaoApenasUmLance() {
		// scene
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4 Novo")
				.lance(joao, 1000.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		assertThat(leiloeiro.getMaiorLance(), equalTo(leiloeiro.getMenorLance()));
	}
	
	@Test
	public void deveEncontrarTresMaioresLances() {
		// scene
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4 Novo")
				.lance(joao, 100.0)
				.lance(maria, 200.0)
				.lance(joao, 300.0)
				.lance(jose, 400.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		List<Lance> maiores = leiloeiro.getTresMaiores();
		assertThat(maiores.size(), equalTo(3));
		assertThat(maiores, hasItems(
				new Lance(jose, 400),
				new Lance(joao, 300),
				new Lance(maria, 200)
		));
	}
	
	@Test
	public void deveEncontrarLeilaoOrdemRandomica() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4 Novo")
				.lance(joao, 200.0)
				.lance(jose, 450.0)
				.lance(joao, 120.0)
				.lance(joao, 700.0)
				.lance(jose, 630.0)
				.lance(joao, 230.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		assertThat(leiloeiro.getMenorLance(), equalTo(120.0));
		assertThat(leiloeiro.getMaiorLance(), equalTo(630.0));
	}
	
	@Test
	public void deveEncontrarLeilaoOrdemDecrescente() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4 Novo")
				.lance(joao, 400.0)
				.lance(jose, 300.0)
				.lance(maria, 200.0)
				.lance(ana, 100.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		assertThat(leiloeiro.getMenorLance(), equalTo(100.0));
		assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
	}
	
	@Test
	public void deveEncontrarDoisLances() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4 Novo")
				.lance(joao, 400.0)
				.lance(jose, 300.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		List<Lance> maiores = leiloeiro.getTresMaiores();
		
		assertThat(maiores.size(), equalTo(2));
		assertThat(maiores.get(0).getValor(), equalTo(400.0));
		assertThat(maiores.get(1).getValor(), equalTo(300.0));
	}
	
	@Test(expected=RuntimeException.class)
	public void deveDevolverListaVaziaSemLance() {
		Leilao leilao = new Leilao("Playstation 3 Novo");
		
		leiloeiro.avalia(leilao);
	}
	
}
