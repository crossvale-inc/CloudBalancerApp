package com.crossvale.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.File;
import java.util.ArrayList;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crossvale.model.ClusterInput;
import com.crossvale.model.ClusterOutput;
import com.crossvale.model.FleetOut;
import org.kie.api.KieBase;
import com.crossvale.ResourceWatcher;
import com.crossvale.Utils;
import com.crossvale.model.ClusterOut;
import com.crossvale.model.Cluster;
import com.crossvale.model.Fleet;

@Service
public class BalancerService {

	//private final KieContainer kieContainer;
	private static File resrouceDirectory = new File("src/main/resources/rules");

	/*@Autowired
	public BalancerService(KieContainer kieContainer) {        
        this.kieContainer = kieContainer;
	}*/
	public BalancerService() {        
        
	}
	
	public ClusterOutput createClusterOutput(ClusterInput clusterInput) {
		
		List<ClusterOut> clusterOutList = new ArrayList<>();
		List<FleetOut> fleetOutList = new ArrayList<>();
    	int microVersion = 0;
		
    	KieContainer kcontainer = Utils.createKieContainer(resrouceDirectory, microVersion);
    	
    	KieBase kbase = kcontainer.getKieBase();
    	
    	
        ResourceWatcher watcher = new ResourceWatcher(kcontainer, resrouceDirectory, 2000, microVersion);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(watcher);
		
		for(Cluster cluster : clusterInput.getCluster()) {
			
			for(Fleet fleet : cluster.getFleet()) {
				
				FleetOut fleetOut = new FleetOut();				
				fleetOut.setCurrentCapacity(fleet.getCurrentCapacity());
				fleetOut.setId(fleet.getId());
				fleetOut.setName(fleet.getName());
				fleetOut.setTargetCapacity(fleet.getCurrentCapacity());
				
				//KieSession kieSession = kcontainer.newKieSession("rulesSession");
				KieSession ksession = kbase.newKieSession();
				 ksession.insert(fleetOut);
				 ksession.fireAllRules();
				 ksession.dispose();
				
				fleetOutList.add(fleetOut);
				
			}

			ClusterOut clusterOut = new ClusterOut();
			clusterOut.setCloud(cluster.getCloud());
			clusterOut.setName(cluster.getName());
			clusterOut.setFleet(fleetOutList);
			
			clusterOutList.add(clusterOut);
		}
		
		ClusterOutput clusterOutput = new ClusterOutput(clusterOutList);
		

		return clusterOutput;
	}
}