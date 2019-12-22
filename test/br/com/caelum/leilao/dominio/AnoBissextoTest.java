package br.com.caelum.leilao.dominio;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AnoBissextoTest {
	
	@Test
	public void anoDeveSerBissexto() {
		AnoBissexto bissexto = new AnoBissexto();
		assertEquals(true, bissexto.ehBissexto(2012));
		assertEquals(true, bissexto.ehBissexto(2016));
	}
	
	@Test
	public void AnoNaoDeveSerBissexto() {
		AnoBissexto bissexto = new AnoBissexto();
		assertEquals(false, bissexto.ehBissexto(2015));
		assertEquals(false, bissexto.ehBissexto(2011));
	}

}
