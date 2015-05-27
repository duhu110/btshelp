package com.duhu.btshelper.service;

import java.util.List;

public interface ChuanshuService {
	public List<String> getchuanshulist(int btsid) throws Exception;
	public void savechuanshulist(List<String> list,int btsid) throws Exception;
}
