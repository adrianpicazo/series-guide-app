package impl.model;

import java.util.LinkedList;
import java.util.List;

public class RemoteSerie {

	private long id;
	private String seriesName;
	private List<String> aliases;
	private String banner;
	private String seriesId;
	private String status;
	private String firstAired;
	private String network;
	private String networkId;
	private String runtime;
	private List<String> genre;
	private String overview;
	private long lastUpdated;
	private String airsDayOfWeek;
	private String airsTime;
	private String rating;
	private String imdbId;
	private String zap2itld;
	private String added;
	private long addedBy;
	private double siteRating;
	private int siteRatingCount;

	public RemoteSerie() {
		aliases = new LinkedList<>();
		genre = new LinkedList<>();
	}

	// ---------- Getters -----------------------------------------------------
	
	public long getId() {
		return id;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public String getBanner() {
		return banner;
	}

	public String getSeriesId() {
		return seriesId;
	}

	public String getStatus() {
		return status;
	}

	public String getFirstAired() {
		return firstAired;
	}

	public String getNetwork() {
		return network;
	}

	public String getNetworkId() {
		return networkId;
	}

	public String getRuntime() {
		return runtime;
	}

	public List<String> getGenre() {
		return genre;
	}

	public String getOverview() {
		return overview;
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

	public String getAirsDayOfWeek() {
		return airsDayOfWeek;
	}

	public String getAirsTime() {
		return airsTime;
	}

	public String getRating() {
		return rating;
	}

	public String getImdbId() {
		return imdbId;
	}

	public String getZap2itld() {
		return zap2itld;
	}

	public String getAdded() {
		return added;
	}

	public long getAddedBy() {
		return addedBy;
	}

	public double getSiteRating() {
		return siteRating;
	}

	public int getSiteRatingCount() {
		return siteRatingCount;
	}
	
	// ---------- Setters -----------------------------------------------------

	public void setId(long id) {
		this.id = id;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public void setSeriesId(String seriesId) {
		this.seriesId = seriesId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setFirstAired(String firstAired) {
		this.firstAired = firstAired;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public void setGenre(List<String> genre) {
		this.genre = genre;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public void setLastUpdated(long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setAirsDayOfWeek(String airsDayOfWeek) {
		this.airsDayOfWeek = airsDayOfWeek;
	}

	public void setAirsTime(String airsTime) {
		this.airsTime = airsTime;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public void setZap2itld(String zap2itld) {
		this.zap2itld = zap2itld;
	}

	public void setAdded(String added) {
		this.added = added;
	}

	public void setAddedBy(long addedBy) {
		this.addedBy = addedBy;
	}

	public void setSiteRating(double siteRating) {
		this.siteRating = siteRating;
	}

	public void setSiteRatingCount(int siteRatingCount) {
		this.siteRatingCount = siteRatingCount;
	}

	@Override
	public String toString() {
		return "RemoteSerie [id=" + id + ", seriesName=" + seriesName + ", aliases=" + aliases + ", banner=" + banner
				+ ", seriesId=" + seriesId + ", status=" + status + ", firstAired=" + firstAired + ", network="
				+ network + ", networkId=" + networkId + ", runtime=" + runtime + ", genre=" + genre + ", overview="
				+ overview + ", lastUpdated=" + lastUpdated + ", airsDayOfWeek=" + airsDayOfWeek + ", airsTime="
				+ airsTime + ", rating=" + rating + ", imdbId=" + imdbId + ", zap2itld=" + zap2itld + ", added=" + added
				+ ", addedBy=" + addedBy + ", siteRating=" + siteRating + ", siteRatingCount=" + siteRatingCount + "]";
	}

}
