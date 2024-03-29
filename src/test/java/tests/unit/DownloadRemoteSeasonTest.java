package tests.unit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import abs.managers.ILocalManager;
import abs.managers.IRemoteManager;
import abs.services.IDownloadAndStoreService;
import impl.exceptions.ErrorOnRemoteServerException;
import impl.exceptions.NoSeriesStoredException;
import impl.exceptions.NotFoundOnRemoteServerException;
import impl.exceptions.NotFoundSeasonOnRemoteServerException;
import impl.exceptions.TimeoutOnRemoteServerException;
import impl.model.Episode;
import impl.model.Season;
import impl.services.DownloadAndStoreSvc;
import resources.FactoryExpectedResults;
import resources.FactoryLocalManagers;
import resources.FactoryMocks;

public class DownloadRemoteSeasonTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Rule
	public Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
	
	// ------------------------------------------------------------------------
	
	private static IDownloadAndStoreService downloadAndStoreService;

	@BeforeClass
	public static void inicia() {
		downloadAndStoreService = new DownloadAndStoreSvc();
	}

	@AfterClass
	public static void termina() {
		downloadAndStoreService = null;
	}
	
	// ------------------------------------------------------------------------
	
	@Mock
	private IRemoteManager remoteManager;
	
	@Before
	public void prepara() {
		MockitoAnnotations.initMocks(this);
		downloadAndStoreService.setRemoteManager(remoteManager);
	}
	
	// ------------------------------------------------------------------------------------------------------
	//  Season downloadRemoteSeason(long codSerie, int airedSeason)
	// ------------------------------------------------------------------------------------------------------

	@Test
	public void descargarTemporadaRemota_NoAlmacenadaExisteSerieRemotaExisteTemporadaRemota_TemporadaRemota() {
		
		// Arrange
		ILocalManager localManager = FactoryLocalManagers.R14_1_1_1.getFakeLocalManager();
		Season mockSeason = (Season) FactoryMocks.R14_1_1_1.getMock();
		Season resultExpected = (Season) FactoryExpectedResults.R14_1_1_1.getExpectedResult();
		downloadAndStoreService.setLocalManager(localManager);
		
		// Act
		when(remoteManager.getRemoteSeason(321060, 1)).thenReturn(mockSeason);
		Season resultReturned = downloadAndStoreService.downloadRemoteSeason(321060, 1);

		// Assert
		assertNotNull(resultReturned);
		assertTrue(compruebaMismaTemporadaMismosEpisodios(resultExpected, resultReturned));
		
	}
	
	@Test
	public void descargarTemporadaRemota_NoAlmacenadaExisteSerieRemotaNoExisteTemporadaRemota_Excepcion() {
			
		// Arrange
		thrown.expect(NotFoundSeasonOnRemoteServerException.class);
		downloadAndStoreService.setLocalManager(FactoryLocalManagers.R14_1_1_2.getFakeLocalManager());
		
		// Act
		when(remoteManager.getRemoteSeason(321060, 4)).thenThrow(NotFoundOnRemoteServerException.class);
		downloadAndStoreService.downloadRemoteSeason(321060, 4);

		// Assert
		
	}
	
	@Test
	public void descargarTemporadaRemota_NoAlmacenadaNoExisteSerieRemota_Excepcion() {
		
		// Arrange
		thrown.expect(NotFoundSeasonOnRemoteServerException.class);
		downloadAndStoreService.setLocalManager(FactoryLocalManagers.R14_1_1_3.getFakeLocalManager());
		
		// Act
		when(remoteManager.getRemoteSeason(999999, 1)).thenThrow(NotFoundOnRemoteServerException.class);
		downloadAndStoreService.downloadRemoteSeason(999999, 1);

		// Assert
		
	}

	@Test
	public void descargarTemporadaRemota_AlmacenadaConTodosEpisodios_TemporadaLocal() {

		
		// Arrange
		ILocalManager localManager = FactoryLocalManagers.R14_1_2_1.getFakeLocalManager();
		Season resultExpected = (Season) FactoryExpectedResults.R14_1_2_1.getExpectedResult();
		downloadAndStoreService.setLocalManager(localManager);
		
		// Act
		Season resultReturned = downloadAndStoreService.downloadRemoteSeason(321060, 1);

		// Assert
		assertNotNull(resultReturned);
		assertTrue(compruebaMismaTemporadaMismosEpisodios(resultExpected, resultReturned));
		
	}
		
	@Test
	public void descargarTemporadaRemota_AlmacenadaConUnEpisodio_TemporadaLocal() {

		// Arrange
		ILocalManager localManager = FactoryLocalManagers.R14_1_2_2.getFakeLocalManager();
		Season resultExpected = (Season) FactoryExpectedResults.R14_1_2_2.getExpectedResult();
		downloadAndStoreService.setLocalManager(localManager);
		
		// Act
		Season resultReturned = downloadAndStoreService.downloadRemoteSeason(321060, 1);

		// Assert
		assertNotNull(resultReturned);
		assertTrue(compruebaMismaTemporadaMismosEpisodios(resultExpected, resultReturned));
		
	}
		
	@Test
	public void descargarTemporadaRemota_AlmacenadaSinEpisodios_TemporadaLocal() {
	
		// Arrange
		ILocalManager localManager = FactoryLocalManagers.R14_1_2_3.getFakeLocalManager();
		Season resultExpected = (Season) FactoryExpectedResults.R14_1_2_3.getExpectedResult();
		downloadAndStoreService.setLocalManager(localManager);
		
		// Act
		Season resultReturned = downloadAndStoreService.downloadRemoteSeason(321060, 1);

		// Assert
		assertNotNull(resultReturned);
		assertTrue(compruebaMismaTemporadaMismosEpisodios(resultExpected, resultReturned));
		
	}

	@Test
	public void descargarTemporadaRemota_SerieNoAlmacenada_Excepcion() {
	
		// Arrange
		thrown.expect(NoSeriesStoredException.class);
		downloadAndStoreService.setLocalManager(FactoryLocalManagers.R14_1_3_1.getFakeLocalManager());
		
		// Act
		when(remoteManager.getRemoteSeason(321060, 1)).thenThrow(NoSeriesStoredException.class);
		downloadAndStoreService.downloadRemoteSeason(321060, 1);

		// Assert
		
	}

	@Test
	public void descargarTemporadaRemota_ErrorDeServidor_Excepcion() {
		
		// Arrange
		thrown.expect(ErrorOnRemoteServerException.class);
		downloadAndStoreService.setLocalManager(FactoryLocalManagers.R14_1_4_1.getFakeLocalManager());
		
		// Act
		when(remoteManager.getRemoteSeason(321060, 1)).thenThrow(ErrorOnRemoteServerException.class);
		downloadAndStoreService.downloadRemoteSeason(321060, 1);

		// Assert
		
	}

	@Test
	public void descargarTemporadaRemota_ErrorDeTimeout_Excepcion() {
		
		// Arrange
		thrown.expect(TimeoutOnRemoteServerException.class);
		downloadAndStoreService.setLocalManager(FactoryLocalManagers.R14_1_4_2.getFakeLocalManager());
		
		// Act
		when(remoteManager.getRemoteSeason(321060, 1)).thenThrow(TimeoutOnRemoteServerException.class);
		downloadAndStoreService.downloadRemoteSeason(321060, 1);

		// Assert
		
	}

	// -----------------------------------------------------------------------------
	
	private boolean compruebaMismaTemporadaMismosEpisodios(Season seasonExpected, Season seasonReturned) {
		if (!seasonExpected.equals(seasonReturned))
			return false;
		Episode[] seasonExpectedEpisodes = seasonExpected.getEpisodes();
		Episode[] seasonReturnedEpisodes = seasonReturned.getEpisodes();
		return Arrays.equals(seasonExpectedEpisodes, seasonReturnedEpisodes);
	}

}