package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.caelum.leilao.dominio.MatematicaMaluca;

public class MatamaticaMalucaTest {
	
	@Test
	public void verificaMaiorTrinta() {
		MatematicaMaluca conta = new MatematicaMaluca(); 
		assertEquals(160, conta.contaMaluca(40));
	}
	
	@Test
	public void verificaMenorTrintaMaiorDez() {
		MatematicaMaluca conta = new MatematicaMaluca();
		assertEquals(45, conta.contaMaluca(15));
	}
	
	@Test
	public void verificaMenorDez() {
		MatematicaMaluca conta = new MatematicaMaluca();
		assertEquals(10, conta.contaMaluca(5));
	}

}
