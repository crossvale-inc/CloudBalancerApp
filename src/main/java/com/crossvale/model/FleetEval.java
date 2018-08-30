package com.crossvale.model;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class FleetEval implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@org.kie.api.definition.type.Label("name")
	private java.lang.String name;
	@org.kie.api.definition.type.Label("currentTime")
	private int currentTime;
	@org.kie.api.definition.type.Label("targetCapacity")
	private java.lang.Integer targetCapacity;

	@org.kie.api.definition.type.Label(value = "currentCapacity")
	private java.lang.Integer currentCapacity;

	@org.kie.api.definition.type.Label(value = "id")
	private java.lang.String id;
	
	@org.kie.api.definition.type.Label(value = "cpuLoad")
	private int cpuLoad;
	@org.kie.api.definition.type.Label(value = "memoryLoad")
	private int memoryLoad;
	@org.kie.api.definition.type.Label(value = "networkLoad")
	private int networkLoad;
	@org.kie.api.definition.type.Label(value = "filter")
	private boolean filter;

	public FleetEval() {
	}

	public java.lang.String getName() {
		return this.name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public int getCurrentTime() {
		return this.currentTime;
	}

	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}
	public java.lang.Integer getTargetCapacity() {
		return this.targetCapacity;
	}

	public void setTargetCapacity(java.lang.Integer targetCapacity) {
		this.targetCapacity = targetCapacity;
	}

	public java.lang.Integer getCurrentCapacity() {
		return this.currentCapacity;
	}

	public void setCurrentCapacity(java.lang.Integer currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	public java.lang.String getId() {
		return this.id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	public int getMemoryLoad() {
		return this.memoryLoad;
	}
	
	public void setMemoryLoad(int memoryLoad) {
		this.memoryLoad = memoryLoad;
	}

	public int getNetworkLoad() {
		return this.networkLoad;
	}

	public void setNetworkLoad(int networkLoad) {
		this.networkLoad = networkLoad;
	}

	public int getCpuLoad() {
		return this.cpuLoad;
	}

	public void setCpuLoad(int cpuLoad) {
		this.cpuLoad = cpuLoad;
	}
	
	public boolean getFilter() {
		return this.filter;
	}

	public void setFilter(boolean filter) {
		this.filter = filter;
	}

	public FleetEval(java.lang.String name, java.lang.Integer targetCapacity, java.lang.Integer currentCapacity,
			java.lang.String id, int currentTime, int memoryLoad, int networkLoad, int cpuLoad, boolean filter) {
		this.name = name;
		this.targetCapacity = targetCapacity;
		this.currentCapacity = currentCapacity;
		this.id = id;
		this.currentTime = currentTime;
		this.memoryLoad = memoryLoad;
		this.cpuLoad = cpuLoad;
		this.networkLoad = networkLoad;
		this.filter = filter;
	}

}
