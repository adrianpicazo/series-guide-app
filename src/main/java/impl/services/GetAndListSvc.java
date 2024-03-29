package impl.services;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import abs.managers.ILocalManager;
import abs.services.IGetAndListService;
import impl.exceptions.NoEpisodesStoredException;
import impl.exceptions.NoSeasonsStoredException;
import impl.exceptions.NoSeriesStoredException;
import impl.model.Episode;
import impl.model.Season;
import impl.model.Serie;

public class GetAndListSvc implements IGetAndListService {

	private ILocalManager localManager;

	@Override
	public void setLocalManager(ILocalManager localManager) {
		this.localManager = localManager;
	}

	@Override
	public Map<String, Long> listSeriesNames() {
		
		// Comprueba existen series
		List<Serie> series = localManager.listSeries();
		if (series.isEmpty())
			throw new NoSeriesStoredException();

		// Crea lista nombres series
		Map<String, Long> seriesNames = new TreeMap<>();
		for (Serie serie : localManager.listSeries())
			seriesNames.put(serie.getSeriesName(), serie.getCodSerie());
		
		return seriesNames;
	}

	@Override
	public String[] listSerieSeasonsEpisodesNames(long codSerie, int airedSeason) {
		
		// Comprueba existe serie
		Serie serie = localManager.getSerie(codSerie);
		if (serie == null)
			throw new NoSeriesStoredException();	
		
		// Comprueba existe temporada
		Season season = serie.getSeasonByAired(airedSeason);
		if (season == null)
			throw new NoSeasonsStoredException();
		
		// Crea vector episodios ordenados
		String[] episodesNames = new String[season.getTotalEpisodes()];
		Episode[] episodes = season.getEpisodes();		
		for (int i = 0; i < episodes.length; i++) {
			Episode episode = episodes[i];
			if (episode != null)
				episodesNames[i] = episode.getEpisodeName();
		}
		
		return episodesNames;
	}

	@Override
	public Serie getSerie(long codSerie) {
		
		// Comprueba existe serie
		Serie serie = localManager.getSerie(codSerie);
		if (serie == null)
			throw new NoSeriesStoredException();

		return serie;
	}

	@Override
	public Season getSeason(long codSerie, int airedSeason) {
		
		// Comprueba existe serie
		Serie serie = localManager.getSerie(codSerie);
		if (serie == null)
			throw new NoSeriesStoredException();
		
		// Comprueba existe temporada
		Season season = serie.getSeasonByAired(airedSeason);
		if (season == null)
			throw new NoSeasonsStoredException();
		
		return season;
	}

	@Override
	public Episode getEpisode(long codSerie, int airedSeason, int airedEpisode) {
		
		// Comprueba existe serie
		Serie serie = localManager.getSerie(codSerie);
		if (serie == null)
			throw new NoSeriesStoredException();	
		
		// Comprueba existe temporada
		Season season = serie.getSeasonByAired(airedSeason);
		if (season == null)
			throw new NoSeasonsStoredException();
		
		// Comprueba existe episodio
		Episode episode = season.getEpisodes()[airedEpisode - 1];
		if (episode == null)
			throw new NoEpisodesStoredException();
		
		return episode;
	}
	
}
