package Adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jiacaitong.R;
import android.support.v4.view.*;
import entity.Contact;

public class CallViewPagerAdapter extends PagerAdapter {
    private Map<String,List<Contact>> PhoneDatas=new HashMap<String, List<Contact>>();
    private Context context;
    private String str[]={"常用联系人","不常用联系人","通信服务电话","生活紧急电话","机构监督举报电话","银行客户服务电话号码","快递公司客服电话","外卖订餐电话","保险客服电话"};
	private int count;
	private int images[]={R.drawable.my,R.drawable.people,R.drawable.kefu,R.drawable.police,R.drawable.jiandu,R.drawable.bankcall,R.drawable.kuaidi,R.drawable.waimai,R.drawable.baoxian};
	private List<Contact> datas=new ArrayList<Contact>();
    public CallViewPagerAdapter(Map<String, List<Contact>> phoneDatas,
			Context context) {
		PhoneDatas = phoneDatas;
		this.context = context;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return PhoneDatas.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
public Object instantiateItem(ViewGroup container, int position) {
	View view=LayoutInflater.from(context).inflate(R.layout.callviewpageritem, null);
	TextView CallTitle=(TextView)view.findViewById(R.id.CallTitle);
	ListView ContactListView=(ListView)view.findViewById(R.id.ContactListView);
	ImageView CallSearch=(ImageView)view.findViewById(R.id.CallSearch);
	ImageView CallJinShen=(ImageView)view.findViewById(R.id.CallJinShen);
	EditText CallEditText=(EditText)view.findViewById(R.id.CallEditText);
	CallEditText.clearFocus();
	CallTitle.setText(str[position]);
	datas=PhoneDatas.get(str[position]);
	CallAdapter adapter=new CallAdapter(context,datas,images[position]);
	ContactListView.setAdapter(adapter);
	ItemClick(ContactListView,position);
	EditTextWatch(CallEditText,ContactListView,position);
	ImageClick(CallSearch,CallEditText,ContactListView,position);
	CallJinShen.setVisibility(View.GONE);
	if(position==3 || position==4){
		CallJinShen.setVisibility(View.VISIBLE);
	}
	container.addView(view);
	return view;
}
public void destroyItem(ViewGroup container, int position, Object object) {
	container.removeView((View)object);
}
  public void ItemClick(ListView listview,final int position){
	  if(position==0 || position==1){
	  listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int posi,
					long arg3) {
				   Intent intent=new Intent();
			       intent.setAction(Intent.ACTION_CALL);
				   intent.setData(Uri.parse("tel:"+PhoneDatas.get(str[position]).get(posi).getPhone()));
				   context.startActivity(intent);
				
			}
		});}
  }
  private void ImageClick(ImageView image,final EditText text,final ListView list,final int position){
	   count=-1;
	  image.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
		   for(int i=0;i<PhoneDatas.get(str[position]).size();i++){
			   if((text.getText().toString().trim().length()!=0 && text.getText().toString().equals(PhoneDatas.get(str[position]).get(i).getPhone())) || (text.getText().toString().trim().length()!=0 && text.getText().toString().equals(PhoneDatas.get(str[position]).get(i).getUsername()))){
				   count=i;
				   break;
			   }
		   }
			if(count!=-1){
				list.setSelection(count);
			}
		}
	});
  }
  private void EditTextWatch(final EditText edit,final ListView list,final int position){
	   count=-1;
	   edit.addTextChangedListener(new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			for(int i=0;i<PhoneDatas.get(str[position]).size();i++){
				   if((edit.getText().toString().trim().length()!=0 && edit.getText().toString().equals(PhoneDatas.get(str[position]).get(i).getPhone())) || (edit.getText().toString().trim().length()!=0 && edit.getText().toString().equals(PhoneDatas.get(str[position]).get(i).getUsername()))){
					   count=i;
					   break;
				   }
			   }
				if(count!=-1){
					list.setSelection(count);
				}
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			
		}
	});
 }

}
