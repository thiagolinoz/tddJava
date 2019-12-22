package br.com.caelum.leilao.dominio;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.servico.Avaliador;

public class LanceTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void deveReceberExceptionComZero() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4")
				.lance(new Usuario("Joao"), 0)
				.constroi();
		
		new Avaliador().avalia(leilao);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void deveReceberExceptionComNegativo() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 4")
				.lance(new Usuario("Jose"), -100)
				.constroi();
		
		new Avaliador().avalia(leilao);
	}

}
