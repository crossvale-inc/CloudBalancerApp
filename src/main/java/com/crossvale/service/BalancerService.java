package com.crossvale.service;

import java.util.List;
import java.util.ArrayList;
import java.lang.Integer;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Agenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.crossvale.model.ClusterInput;
import com.crossvale.model.ClusterOutput;
import com.crossvale.model.FleetOut;
import com.crossvale.model.ClusterOut;
import com.crossvale.model.Cluster;
import com.crossvale.model.Fleet;
import com.crossvale.model.FleetEval;

@Service
public class BalancerService {

	private final KieContainer kieContainer;

	@Autowired
	public BalancerService(KieContainer kieContainer) {        
        this.kieContainer = kieContainer;
	}

	
	public ClusterOutput createClusterOutput(ClusterInput clusterInput) {
		
		ClusterOutput clusterOutput = new ClusterOutput();
		
		try {
		
		List<ClusterOut> clusterOutList = new ArrayList<>();
		DateTime dt = new DateTime(DateTimeZone.UTC);
	    int hhmm = dt.getHourOfDay()*100 + dt.getMinuteOfHour();
				
		for(Cluster cluster : clusterInput.getCluster()) {
			
			List<FleetOut> fleetOutList = new ArrayList<>();
			
			for(Fleet fleet : cluster.getFleet()) {
				
				FleetOut fleetOut = new FleetOut();		
				FleetEval fleetEval = new FleetEval();
				fleetEval.setCurrentCapacity(fleet.getCurrentCapacity());
				fleetEval.setId(fleet.getId());
				fleetEval.setCurrentTime(hhmm);
				fleetEval.setName(fleet.getName());
				fleetEval.setTargetCapacity(fleet.getCurrentCapacity());
				fleetEval.setCpuLoad(Integer.parseInt(fleet.getCpuLoad().replaceAll("%$", "")));
				fleetEval.setMemoryLoad(Integer.parseInt(fleet.getMemoryLoad().replaceAll("%$", "")));
				fleetEval.setNetworkLoad(Integer.parseInt(fleet.getNetworkLoad().replaceAll("%$", "")));
				fleetEval.setFilter(false);
				//System.out.println("Fleet Memory Load: " + fleetEval.getMemoryLoad() + " Fleet ID " + fleetEval.getId());
				
				KieSession kieSession = kieContainer.newKieSession("rulesSession");
				Agenda agenda = kieSession.getAgenda();
				kieSession.insert(fleetEval);
				
				agenda.getAgendaGroup("lowerlimit").setFocus();
				agenda.getAgendaGroup("upperlimit").setFocus();
				agenda.getAgendaGroup("underload").setFocus();
				agenda.getAgendaGroup("overload").setFocus();
				agenda.getAgendaGroup("adhoc").setFocus();
				agenda.getAgendaGroup("default").setFocus();
				agenda.getAgendaGroup("override").setFocus();
				
				kieSession.fireAllRules();
				kieSession.dispose();
				
				System.out.println("Filter Setting: " + fleetEval.getFilter());
				
				fleetOut.setCurrentCapacity(fleetEval.getCurrentCapacity());
				fleetOut.setId(fleetEval.getId());
				fleetOut.setCurrentTime(hhmm);
				fleetOut.setName(fleetEval.getName());
				fleetOut.setTargetCapacity(fleetEval.getTargetCapacity());
				
				fleetOutList.add(fleetOut);
				
			}

			ClusterOut clusterOut = new ClusterOut();
			clusterOut.setCloud(cluster.getCloud());
			clusterOut.setName(cluster.getName());
			clusterOut.setFleet(fleetOutList);
			
			clusterOutList.add(clusterOut);
		}
		
		clusterOutput.setCluster(clusterOutList);

		} catch (Throwable t) {
            t.printStackTrace();
        }
		
		return clusterOutput;
	}
}