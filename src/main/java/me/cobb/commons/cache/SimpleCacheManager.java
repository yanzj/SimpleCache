package me.cobb.commons.cache;

import java.util.HashMap;
import java.util.Map;

public class SimpleCacheManager {
	
    private final Map<String, SimpleCacheEntry<?>> mCache = new HashMap<String, SimpleCacheEntry<?>>();

    /**
     * Add a cache item to change manager.
     * 
     * @param key cache key
     * @param an object implements SimpleCacheExtractor
     * @param timeToKeep time to keep 
     * 
     * @throws Exception
     */
    public <T> void addCache(String key, SimpleCacheExtractor<T> sce, long timeToKeep) throws Exception {
    	SimpleCacheEntry<T> entry = new SimpleCacheEntry<T>(sce, timeToKeep);
    	entry.cachedVal = sce.extract();
    	mCache.put(key, entry);
    }
    
    /**
     * Clear cache manager.
     */
    public void clear() {
        synchronized (mCache) {
            mCache.clear();
        }
    }

    /**
     * Delete cache item.
     * 
     * @param key cache key
     */
    public void delete(String key) {
        if (key != null) {
            synchronized (mCache) {
                mCache.remove(key);
            }
        }
    }

    /**
     * Retrieve value from cahce.
     * 
     * @param key cache key
     * @param type value type
     * 
     * @return cache value
     * 
     * @throws Exception
     */
    public <T> T retrieve(String key, Class<T> type) throws Exception {
        if (key != null) {
            synchronized (mCache) {
                if (mCache.containsKey(key)) {
                    SimpleCacheEntry<T> entry = (SimpleCacheEntry<T>) mCache.get(key);
                    if (System.currentTimeMillis() < entry.keepUntilTimestamp) {
                        return entry.cachedVal;
                    } else {
                    	// on cache timeout refresh cache value
                    	entry.refreshKeepTime();
                    	entry.cachedVal = entry.sce.extract();
                    	return entry.cachedVal;
                    }
                    
                }
            }
        }

        return null;
    }

}