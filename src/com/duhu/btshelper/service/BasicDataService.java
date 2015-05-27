package com.duhu.btshelper.service;

import com.duhu.btshelper.entity.BasicdataEntity;

public interface BasicDataService {
	public String[] chaxun(int btsid) throws Exception;

	public void updata(int btsid, String jindu, String weidu,
			String fangweijiao0, String fangweijiao1, String fangweijiao2,
			String tagao, String xiaqingjiao0, String xiaqingjiao1,
			String xiaqingjiao2, String haiba) throws Exception;
	public void baocun(BasicdataEntity basicdataEntity)throws Exception;

}
