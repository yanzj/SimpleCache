package me.cobb.commons.cache;

public class SimpleCacheEntry<T> {
	
	public long timeToKeep = 0;
	public long keepUntilTimestamp;
	public SimpleCacheExtractor<T> sce;
	public T cachedVal;

	public SimpleCacheEntry(SimpleCacheExtractor<T> sce, long timeToKeep) {
		this.timeToKeep = timeToKeep;
		this.sce = sce;
		this.keepUntilTimestamp = System.currentTimeMillis() + this.timeToKeep * 1000;
	}

	public void refreshKeepTime() {
		this.keepUntilTimestamp = System.currentTimeMillis() + this.timeToKeep * 1000;
	}
}
