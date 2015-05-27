package com.duhu.btshelper.entity;

import java.io.Serializable;

public class BtsnameEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int btsid;
	private String btsname;

	public BtsnameEntity(int btsid, String btsname) {
		this.btsid = btsid;
		this.btsname = btsname;
	}

	public BtsnameEntity() {
	}


	public int getBtsid() {
		return btsid;
	}

	public void setBtsid(int btsid) {
		this.btsid = btsid;
	}

	public String getBtsname() {
		return btsname;
	}

	public void setBtsname(String btsname) {
		this.btsname = btsname;
	}
}
