package me.cobb.commons.cache;

import java.text.SimpleDateFormat;

public class Demo {
	
	public static void main(String[] args) throws Exception {
		
		final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final String DEMO_CAHCE_NAME = "Your Cache Name";
		
		final SimpleCacheManager scm = new SimpleCacheManager();
		scm.addCache(DEMO_CAHCE_NAME, new SimpleCacheExtractor<String>() {
			int extractCount = 0;
			
			@Override
			public String extract() {
				extractCount++;
				System.out.println("Extract [" + extractCount + "] at : " + SDF.format(System.currentTimeMillis()));
				return String.valueOf(extractCount);
			}

		}, 3 /* 3 seconds refresh again */);

		
		
		for (int a = 0; a < 100; a++) {

			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 1000; i++) {
						try {
							// Get cached value
							String cachedValue = scm.retrieve(DEMO_CAHCE_NAME, String.class);
							System.out.println("Get Cache Times : " + i + "; Value: " + cachedValue);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
			}).start();
		}
	}

}
