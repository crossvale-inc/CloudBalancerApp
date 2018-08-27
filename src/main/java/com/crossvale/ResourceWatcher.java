package com.crossvale;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.KieContainer;

public class ResourceWatcher implements Runnable {
	
	private KieContainer kContainer;
	
	private File directory;
	
	private int interval; // millisecond
	
	private int microVersion;
	
	private volatile boolean exit;
	
	private Map<String, Long> fileTimestampMap;
	
	public boolean isExit() {
		return exit;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public ResourceWatcher(KieContainer kContainer, File directory, int interval, int microVersion) {
		this.kContainer = kContainer;
		this.directory = directory;
		this.interval = interval;
		this.microVersion = microVersion;
		
		this.exit = false;
		this.fileTimestampMap = new HashMap<String, Long>();
		
		Utils.scanForUpdate(directory, fileTimestampMap); // init fileTimestampMap
	}

	@Override
	public void run() {
		while (!exit) {
			
			System.out.println(" fileTimestampMap = " + fileTimestampMap);
			
			boolean update = Utils.scanForUpdate(directory, fileTimestampMap);
			if (update) {
			    try {
    				// Increment the microVersion and create a new KieContainer
    				microVersion++;
    				KieContainer newKieContainer = Utils.createKieContainer(directory, microVersion);
    				
    				// Here we don't use the newKieContainer directly. Rather, update the current KieContainer so the running client doesn't have to care
    				System.out.println( " - updateToVersion" );
    				kContainer.updateToVersion(newKieContainer.getReleaseId());
    				System.out.println( " - done" );
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
			}
			
			try {
				Thread.sleep(interval);
			} catch (InterruptedException ie) {
			}
		}
	}

}
