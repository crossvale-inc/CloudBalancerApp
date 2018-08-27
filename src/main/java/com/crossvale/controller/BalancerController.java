package com.crossvale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crossvale.model.ClusterInput;
import com.crossvale.service.BalancerService;
import com.crossvale.model.ClusterOutput;

@RestController
public class BalancerController {

	private final BalancerService balancerService;

	@Autowired
	public BalancerController(BalancerService balancerService) {
		this.balancerService = balancerService;
	}

	@RequestMapping(value = "/balancer", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public ClusterOutput getQuestions(@RequestBody ClusterInput clusterInput) {
		
		ClusterOutput clusterOutput = new ClusterOutput();;
		clusterOutput = balancerService.createClusterOutput(clusterInput);
		return clusterOutput;
	}
}