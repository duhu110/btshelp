package com.duhu.btshelper.adapter;

import java.util.List;

import com.duhu.btshelper.R;
import com.duhu.btshelper.entity.BtsnameEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BtsnameAdapter extends ArrayAdapter<BtsnameEntity>{
	public BtsnameEntity btsnameEntity2;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	@Override
	public BtsnameEntity getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

	private LayoutInflater myInflater;
	private int myresource;

	public BtsnameAdapter(Context context, int resource,
			List<BtsnameEntity> objects) {
		super(context, resource, objects);
		this.myInflater = LayoutInflater.from(context);
		this.myresource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout view = null;
		if (convertView == null) {
			view = (LinearLayout) myInflater.inflate(myresource, null);
		} else {
			view = (LinearLayout) convertView;
		}
		BtsnameEntity btsnameEntity = getItem(position);
		TextView tvbtsname = (TextView) view.findViewById(R.id.btsnameitemname);
		TextView tvbtsid = (TextView) view.findViewById(R.id.btsnameitemid);
		tvbtsid.setText(String.valueOf(btsnameEntity.getBtsid()));
		tvbtsname.setText(btsnameEntity.getBtsname());
		btsnameEntity2 = btsnameEntity;
		
		return view;
	}

}
