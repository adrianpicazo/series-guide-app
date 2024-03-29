package tests.acceptance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import impl.exceptions.NoEpisodesStoredException;
import impl.exceptions.NoSeasonsStoredException;
import impl.exceptions.NoSeriesStoredException;
import impl.model.Episode;
import resources.FactoryExpectedResults;
import resources.FactoryLocalManagers;

public class R07_HU1 extends AcceptanceTest {

	// REQUISITO 07
	// Debe permitir que el usuario indique que ha visto un episodio concreto de una
	// temporada determinada para una serie dada. En ese caso el programa debe
	// permitir que el usuario introduzca un comentario al respecto si es su
	// voluntad -máx. 150 caracteres.

	// HISTORIA DE USUARIO 07.1
	// Como usuario quiero indicar como visto un episodio de una temporada de una de
	// mis series para saber que lo he visto.
	
	// -----------------------------------------------------------------------------

	// ESCENARIO 07.1.1
	// El episodio está almacenado en la BDL + está indicado como no visto.

	// PRUEBA DE ACEPTACIÓN 07.1.1.1
	
	@Test
	public void indicarVistoEpisodio_EpisodioUnicoComoNoVisto_EpisodioVistoTemporadaVista() {
		
		// Given
		seriesGuideApp.setLocalManager(FactoryLocalManagers.R07_1_1_1.getLocalManager());

		// When
		Episode resultReturned = seriesGuideApp.checkEpisodeAsViewed(321060, 1, 1);
		
		// Then
		assertNotNull(resultReturned);
		assertEquals(FactoryExpectedResults.R07_1_1_1.getExpectedResult(), resultReturned);
		assertTrue(seriesGuideApp.getEpisode(321060, 1, 1).isSeen());
		assertTrue(seriesGuideApp.getSeason(321060, 1).isSeen());
		
	}
	
	// PRUEBA DE ACEPTACIÓN 07.1.1.2
	
	@Test
	public void indicarVistoEpisodio_EpisodioVariosComoNoVistos_EpisodioVisto() {
		
		// Given
		seriesGuideApp.setLocalManager(FactoryLocalManagers.R07_1_1_2.getLocalManager());

		// When
		Episode resultReturned = seriesGuideApp.checkEpisodeAsViewed(321060, 1, 1);
		
		// Then
		assertNotNull(resultReturned);
		assertEquals(FactoryExpectedResults.R07_1_1_2.getExpectedResult(), resultReturned);
		assertTrue(seriesGuideApp.getEpisode(321060, 1, 1).isSeen());
		
	}

	// -----------------------------------------------------------------------------

	// ESCENARIO 07.1.2
	// El episodio está almacenado en la BDL + está ya indicado como visto.

	// PRUEBA DE ACEPTACIÓN 07.1.2.1
	
	@Test
	public void indicarVistoEpisodio_EpisodioVistoNoComentado_EpisodioVisto() {
		
		// Given
		seriesGuideApp.setLocalManager(FactoryLocalManagers.R07_1_2_1.getLocalManager());

		// When
		Episode resultReturned = seriesGuideApp.checkEpisodeAsViewed(321060, 1, 2);
		
		// Then
		assertNotNull(resultReturned);
		assertEquals(FactoryExpectedResults.R07_1_2_1.getExpectedResult(), resultReturned);
		assertTrue(seriesGuideApp.getEpisode(321060, 1, 2).isSeen());
		
	}
	
	// PRUEBA DE ACEPTACIÓN 07.1.2.2
	
	@Test
	public void indicarVistoEpisodio_EpisodioVistoComentado_EpisodioVisto() {
		
		// Given
		seriesGuideApp.setLocalManager(FactoryLocalManagers.R07_1_2_2.getLocalManager());

		// When
		Episode resultReturned = seriesGuideApp.checkEpisodeAsViewed(321060, 1, 2);
		
		// Then
		assertNotNull(resultReturned);
		assertEquals(FactoryExpectedResults.R07_1_2_2.getExpectedResult(), resultReturned);
		assertTrue(seriesGuideApp.getEpisode(321060, 1, 2).isSeen());
		
	}

	// -----------------------------------------------------------------------------

	// ESCENARIO 07.1.3
	// El episodio no está almacenado en la BDL.

	// PRUEBA DE ACEPTACIÓN 07.1.3.1
	
	@Test
	public void indicarVistoEpisodio_ExisteSerieExisteTemporadaNoExisteEpisodio_Excepcion() {
		
		thrown.expect(NoEpisodesStoredException.class);
		
		// Given
		seriesGuideApp.setLocalManager(FactoryLocalManagers.R07_1_3_1.getLocalManager());
		
		// When
		seriesGuideApp.checkEpisodeAsViewed(321060, 1, 2);
		
		// Then
		
	}
	
	// PRUEBA DE ACEPTACIÓN 07.1.3.2
	
	@Test
	public void indicarVistoEpisodio_ExisteSerieNoExisteTemporada_Excepcion() {
		
		thrown.expect(NoSeasonsStoredException.class);
		
		// Given
		seriesGuideApp.setLocalManager(FactoryLocalManagers.R07_1_3_2.getLocalManager());
		
		// When
		seriesGuideApp.checkEpisodeAsViewed(321060, 1, 2);
		
		// Then
		
	}
	
	// PRUEBA DE ACEPTACIÓN 07.1.3.3
	
	@Test
	public void indicarVistoEpisodio_NoExisteSerie_Excepcion() {
		
		thrown.expect(NoSeriesStoredException.class);
		
		// Given
		seriesGuideApp.setLocalManager(FactoryLocalManagers.R07_1_3_3.getLocalManager());
		
		// When
		seriesGuideApp.checkEpisodeAsViewed(321060, 1, 2);
		
		// Then
		
	}

}
