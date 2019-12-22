package br.com.caelum.leilao.dominio;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static br.com.caelum.leilao.builder.LeilaoMatcher.temUmLance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


import br.com.caelum.leilao.builder.CriadorDeLeilao;

public class LeilaoTest {
	
	private Usuario steveJobs;
	private Usuario steveWozniak;
	private Usuario billGates;
	
	@Before
	public void criaUsuarios() {
		this.steveJobs = new Usuario("Steve Jobs");
		this.steveWozniak = new Usuario("Steve Wozniak");
		this.billGates = new Usuario("Bill Gates");
	}
	
	@Test
	public void deveReceberUmLance() {
		Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15").constroi();
		assertEquals(0, leilao.getLances().size());
		
		leilao.propoe(new Lance(steveJobs, 2000));
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
	}
	
	@Test
	public void deveReceberVariosLances() {
		Leilao leilao = new CriadorDeLeilao()
	            .para("Macbook Pro 15")
	            .lance(steveJobs, 2000)
	            .lance(steveWozniak, 3000)
	            .constroi();
		
		assertEquals(2, leilao.getLances().size());
		assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
		assertEquals(3000.0, leilao.getLances().get(1).getValor(), 0.00001);
	}
	
	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
		Leilao leilao = new CriadorDeLeilao()
	            .para("Macbook Pro 15")
	            .lance(steveJobs, 2000.0)
	            .lance(steveJobs, 3000.0)
	            .constroi();
		
		assertEquals(1, leilao.getLances().size());
	}
	
	@Test
	public void naoDeveAceitarMaisDoQueCincoLancesMesmoUsuario() {
		Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15")
                .lance(steveJobs, 2000)
                .lance(billGates, 3000)
                .lance(steveJobs, 4000)
                .lance(billGates, 5000)
                .lance(steveJobs, 6000)
                .lance(billGates, 7000)
                .lance(steveJobs, 8000)
                .lance(billGates, 9000)
                .lance(steveJobs, 10000)
                .lance(billGates, 11000)
                .lance(steveJobs, 12000)
                .constroi();
		
		assertEquals(10, leilao.getLances().size());
		int ultimo = leilao.getLances().size()-1;
		assertEquals(11000, leilao.getLances().get(ultimo).getValor(), 0.00001);
	}
	
	@Test
	public void deveDobrarUltimoLanceUsuario() {
		Leilao leilao = new CriadorDeLeilao()
	            .para("Macbook Pro 15")
	            .lance(steveJobs, 2000)
	            .lance(steveWozniak, 2100)
	            .constroi();
		
		leilao.dobraLance(steveJobs);
	
		assertEquals(4000, leilao.getLances().get(2).getValor(), 0.00001);
	}
	
	@Test
	public void naoDeveDobrarCasoNaoHajaLance() {
		Leilao leilao = new Leilao("Macbook Pro 15");
		Usuario stevejobs = new Usuario("Steve Jobs");
		
		leilao.dobraLance(stevejobs);
		
		assertEquals(0, leilao.getLances().size());
	}
	
	@Test
	public void deveReceberUmLancePersonalizado() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4").constroi();
		assertThat(leilao.getLances().size(), equalTo(0));
		
		Lance lance = new Lance(new Usuario("Steve Jobs"), 400.0);
		leilao.propoe(lance);
		
		assertThat(leilao.getLances().size(), equalTo(1));
		assertThat(leilao, temUmLance(lance));
	}
}
