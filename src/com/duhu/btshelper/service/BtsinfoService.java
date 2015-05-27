package com.duhu.btshelper.service;

import java.util.List;

public interface BtsinfoService {
	public List<String> getbtsinfolist(int btsid) throws Exception;
	public void savebtsinfolist(List<String> list,int btsid) throws Exception;
}
