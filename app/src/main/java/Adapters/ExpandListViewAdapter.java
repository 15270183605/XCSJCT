package Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class ExpandListViewAdapter extends BaseExpandableListAdapter {
	 private String[] classes;
	   private String[][] type;
	   private Context content;
	   
		public ExpandListViewAdapter(String[] classes, String[][] type, Context content) {
		super();
		this.classes = classes;
		this.type = type;
		this.content = content;
	}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return type[groupPosition][childPosition];
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public int getChildrenCount(int groupPostion) {
			// TODO Auto-generated method stub
			return type[groupPostion].length;
		}

		@Override
		public Object getGroup(int groupPostion) {
			// TODO Auto-generated method stub
			return classes[groupPostion];
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return classes.length;
		}

		@Override
		public long getGroupId(int groupPostion) {
			// TODO Auto-generated method stub
			return groupPostion;
		}

		@Override
		public View getGroupView(int groupPostion, boolean isExpanded, View covertView, ViewGroup parent) {
			GroupViewHolder groupHolder;
			if(covertView==null){
				covertView=LayoutInflater.from(content).inflate(R.layout.expandlistviewitem, null);
				groupHolder=new GroupViewHolder();
				groupHolder.text1=(TextView)covertView.findViewById(R.id.text);
				covertView.setTag(groupHolder);
			}else{
				groupHolder=(GroupViewHolder)covertView.getTag();
			}
			groupHolder.text1.setText(classes[groupPostion]);
			return covertView;
		}
	public View getChildView(int groupPosition,int childPosition,boolean isLastChild,View covertView,ViewGroup parent){
		ChildViewHolder childHolder;
		if(covertView==null){
			covertView=LayoutInflater.from(content).inflate(R.layout.expandlistviewitem, null);
			childHolder=new ChildViewHolder();
			childHolder.text2=(TextView)covertView.findViewById(R.id.text);
			covertView.setTag(childHolder);
		}else{
			childHolder=(ChildViewHolder)covertView.getTag();
		}
		childHolder.text2.setText(type[groupPosition][childPosition]);
		return covertView;
	}
		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return true;
		}
	class GroupViewHolder{
		TextView text1;
	}
	class ChildViewHolder{
		TextView text2;
	}

}
