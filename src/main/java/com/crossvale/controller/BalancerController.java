package com.crossvale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//import com.crossvale.model.Product;
import com.crossvale.model.ClusterInput;
import com.crossvale.service.BalancerService;
import com.crossvale.model.ClusterOutput;
//import com.crossvale.model.Type;

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

		//Product product = new Product();
		ClusterOutput clusterOutput = new ClusterOutput();
		//product.setType(type.getType());
		//balancerService.getProductDiscount(product);
		clusterOutput = balancerService.createClusterOutput(clusterInput);
		return clusterOutput;
	}
}