package com.duhu.btshelper.service;

import com.duhu.btshelper.entity.BtsCheckEntity;

public interface BtsCheckService {
	public void upload(BtsCheckEntity btsCheck)throws Exception;
	public BtsCheckEntity download(int btsid)throws Exception;
}
