package com.duhu.btshelper.entity;

public class BasicdataEntity {
	int btsid;
	private String jindu;
	private String weidu;
	private String fangweijiao0;
	private String fangweijiao1;
	private String fangweijiao2;
	private String guagao;
	// 数据库中guagao对应tagao
	private String xiaqingjiao0;
	private String xiaqingjiao1;
	private String xiaqingjiao2;
	private String haiba;

	public BasicdataEntity() {
	}

	public BasicdataEntity(int btsid, String jindu,
			String weidu, String fangweijiao0, String fangweijiao1,
			String fangweijiao2, String guagao, String xiaqingjiao0,
			String xiaqingjiao1, String xiaqingjiao2, String haiba) {
		this.btsid = btsid;
		this.jindu = jindu;
		this.weidu = weidu;
		this.fangweijiao0 = fangweijiao0;
		this.fangweijiao1 = fangweijiao1;
		this.fangweijiao2 = fangweijiao2;
		this.guagao = guagao;
		this.xiaqingjiao0 = xiaqingjiao0;
		this.xiaqingjiao1 = xiaqingjiao1;
		this.xiaqingjiao2 = xiaqingjiao2;
		this.haiba = haiba;
	}

	public int getBtsid() {
		return btsid;
	}

	public void setBtsid(int btsid) {
		this.btsid = btsid;
	}


	public String getJindu() {
		return jindu;
	}

	public void setJindu(String jindu) {
		this.jindu = jindu;
	}

	public String getWeidu() {
		return weidu;
	}

	public void setWeidu(String weidu) {
		this.weidu = weidu;
	}

	public String getFangweijiao0() {
		return fangweijiao0;
	}

	public void setFangweijiao0(String fangweijiao0) {
		this.fangweijiao0 = fangweijiao0;
	}

	public String getFangweijiao1() {
		return fangweijiao1;
	}

	public void setFangweijiao1(String fangweijiao1) {
		this.fangweijiao1 = fangweijiao1;
	}

	public String getFangweijiao2() {
		return fangweijiao2;
	}

	public void setFangweijiao2(String fangweijiao2) {
		this.fangweijiao2 = fangweijiao2;
	}

	public String getGuagao() {
		return guagao;
	}

	public void setGuagao(String guagao) {
		this.guagao = guagao;
	}

	public String getXiaqingjiao0() {
		return xiaqingjiao0;
	}

	public void setXiaqingjiao0(String xiaqingjiao0) {
		this.xiaqingjiao0 = xiaqingjiao0;
	}

	public String getXiaqingjiao1() {
		return xiaqingjiao1;
	}

	public void setXiaqingjiao1(String xiaqingjiao1) {
		this.xiaqingjiao1 = xiaqingjiao1;
	}

	public String getXiaqingjiao2() {
		return xiaqingjiao2;
	}

	public void setXiaqingjiao2(String xiaqingjiao2) {
		this.xiaqingjiao2 = xiaqingjiao2;
	}

	public String getHaiba() {
		return haiba;
	}

	public void setHaiba(String haiba) {
		this.haiba = haiba;
	}
}
