package com.eenet.androidbase.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

public class AppsBaseAdapter<T> extends ArrayAdapter<T>{

	protected Context context;
	protected List<T> dataSource;
	protected View parentView;
	 
	public AppsBaseAdapter(Context context, int textViewResourceId,
						   List<T> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.dataSource = objects;
	}
	
	public AppsBaseAdapter(Context context, int resource, int textViewResourceId,
						   List<T> objects, View view) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.parentView = view;
		this.dataSource = objects;
	}
	
	public AppsBaseAdapter(Context context, int resource, int textViewResourceId,
						   List<T> objects) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.dataSource = objects;
	}

	public void setParentView(View view){
		this.parentView = view;
	}
	
	public int getHeight(){
		int totalHeight = 0;
		int count = this.getCount();
		for(int i = 0; i < count; i++) {
			View listItem = null;
//			if(this.parentView instanceof ListView){
				listItem = this.getView(i, null, (ListView)this.parentView);
//			}else if(this.parentView instanceof GridView){
//				listItem = this.getView(i, null, (GridView)this.parentView);
//			}
//			if(listItem != null){
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
//			}
		}
		
		if(this.parentView instanceof ListView){
			ViewGroup.LayoutParams params = ((ListView)this.parentView).getLayoutParams();
			params.height = totalHeight + (1 * (count - 1));
			((ListView)this.parentView).setLayoutParams(params);
		}else if(this.parentView instanceof GridView){
			ViewGroup.LayoutParams params = ((GridView)this.parentView).getLayoutParams();
			params.height = totalHeight + (1 * (count - 1));
			((GridView)this.parentView).setLayoutParams(params);
		}
		return totalHeight;
	}
	
	public int getHeight(double height){
		int count = this.getCount();
		for(int i = 0; i < count; i++) {
			View listItem = null;
			listItem = this.getView(i, null, (ListView)this.parentView);
			listItem.measure(0, 0);
		}
		ViewGroup.LayoutParams params = ((ListView)this.parentView).getLayoutParams();
		params.height = (int) (count * height);
		
		((ListView)this.parentView).setLayoutParams(params);
		return params.height;
	}

	protected List<View> dataSourceViews = new ArrayList<View>();
	public void setDataSourceViews(List<View> views){
		this.dataSourceViews.clear();
		this.dataSourceViews.addAll(views);
	}

}
