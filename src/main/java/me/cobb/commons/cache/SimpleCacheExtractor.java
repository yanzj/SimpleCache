package me.cobb.commons.cache;

public interface SimpleCacheExtractor<T> {
	
	/**
	 * extract cache value
	 * 
	 * @return cache value
	 * 
	 * @throws Exception
	 */
	T extract() throws Exception;

}