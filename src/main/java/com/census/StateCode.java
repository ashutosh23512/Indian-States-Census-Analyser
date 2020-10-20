package com.census;

public class StateCode {

	public String srNo;
	public String stateName;
	public int tin;
	public String stateCode;

	public StateCode() {
	}

	public StateCode(String srNo, String stateName, int tin, String stateCode) {
		this.srNo = srNo;
		this.stateName = stateName;
		this.tin = tin;
		this.stateCode = stateCode;
	}

	@Override
	public String toString() {
		return "IndiaStateCodeCSV{" + "srNo='" + srNo + '\'' + ", stateName=" + stateName + ", tin=" + tin
				+ ", stateCode=" + stateCode + '}';
	}
}
