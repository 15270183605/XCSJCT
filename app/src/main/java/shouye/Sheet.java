package shouye;
import java.util.ArrayList;
import java.util.List;

import sqlite.CountSQLite;
import sqlite.IncomeSQLite;
import sqlite.MenuClassSQLite;
import sqlite.PaySQLite;
import sqlite.YingFuSQLite;
import sqlite.YingShouSQLite;
import Adapters.SheetShowDataAdapter;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.CountEntity;
import entity.MenuUseFulClass;
import entity.SheetTemplate;
public class Sheet extends Activity {
	private ImageView GraphNoData;
	private LinearLayout SheetShowDataLayout;
	private LinearLayout headView;
	private LayoutInflater inflater;
	private String day = "";
	private String str = "����";
	private ListView sheetlistView;
	private IncomeSQLite incomesqlite;
	private PaySQLite paysqlite;
	private YingShouSQLite yingshousqlite;
	private YingFuSQLite yingfusqlite;
	private MenuClassSQLite menuclasssqlite;
	private CountSQLite countSqlite;
	private SQLiteDatabase db1, db2, db3, db4, db5,db6;
	private TextView title;
	private List<SheetTemplate> sheetTemplateDatas;
	List<MenuUseFulClass> menuuseclass = new ArrayList<MenuUseFulClass>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sheet);
		init();
	}
//��ʼ��
	public void init() {
		day = GraphContainer.returnTime();
		str = GraphContainer.returnselectType();
		sheetlistView = (ListView) findViewById(R.id.sheetlistView);
		GraphNoData = (ImageView) findViewById(R.id.GraphNoData);
		SheetShowDataLayout = (LinearLayout) findViewById(R.id.sheetShowDataLayout);
		inflater = LayoutInflater.from(this);
		title = (TextView) findViewById(R.id.title);
		sheetTemplateDatas = new ArrayList<SheetTemplate>();
		incomesqlite = new IncomeSQLite(this, "income.db", null, 1);
		db1 = incomesqlite.getReadableDatabase();
		paysqlite = new PaySQLite(this, "pay.db", null, 1);
		db2 = paysqlite.getReadableDatabase();
		yingshousqlite = new YingShouSQLite(this, "yingshou.db", null, 1);
		db3 = yingshousqlite.getReadableDatabase();
		yingfusqlite = new YingFuSQLite(this, "yingfu.db", null, 1);
		db4 = yingfusqlite.getReadableDatabase();
		menuclasssqlite = new MenuClassSQLite(this, "menuclass.db", null, 1);
		db5 = menuclasssqlite.getReadableDatabase();
		countSqlite=new CountSQLite(this, "TotalCount.db", null, 1);
		db6=countSqlite.getReadableDatabase();
		AddHeadView();
		ShowDatas(str);
		
	}
//ΪListview���ͷView;
	public void AddHeadView() {
		headView = (LinearLayout) inflater.inflate(R.layout.sheettitleitem,
				null);
		sheetlistView.addHeaderView(headView);
	}
//��ʾ����
	public void ShowDatas(String str) {
		title.setText(str);
		if (str.equals("����")) {
			sheetTemplateDatas.clear();
			getDatas("���뵥", day);
		} else if (str.equals("֧��")) {
			sheetTemplateDatas.clear();
			getDatas("֧����", day);
		} else if (str.equals("���")) {
			sheetTemplateDatas.clear();
			getDatas("��", day);
		} else if (str.equals("����")) {
			sheetTemplateDatas.clear();
			getDatas("���", day);

		} else if (str.equals("ʵ��")) {
			sheetTemplateDatas.clear();
			getDatas("ʵ�յ�", day);

		} else if (str.equals("ʵ��")) {
			sheetTemplateDatas.clear();
			getDatas("ʵ����", day);

		} else if (str.equals("ȫ��")) {
			sheetTemplateDatas.clear();
			getDatas("���뵥", day);
			getDatas("֧����", day);
			getDatas("��", day);
			getDatas("���", day);
			getDatas("ʵ�յ�", day);
			getDatas("ʵ����", day);
		} else if (str.equals("����")) {
            sheetTemplateDatas.clear();
            GetDataCount("���˵�", day);
		}
		if (sheetTemplateDatas.size() == 0) {
			SheetShowDataLayout.setVisibility(View.GONE);
			GraphNoData.setVisibility(View.VISIBLE);
		} else {
			SheetShowDataLayout.setVisibility(View.VISIBLE);
			GraphNoData.setVisibility(View.GONE);
			SheetShowDataAdapter adapter = new SheetShowDataAdapter(this,
					sheetTemplateDatas);
			sheetlistView.setAdapter(adapter);
		}
	}
//��ȡ����(��������)
	public void getDatas(String str, String time) {
		List<MenuUseFulClass> menuuseclass1 = new ArrayList<MenuUseFulClass>();
		menuuseclass1 = menuclasssqlite.queryMenucalss(db5, str);
		for (int i = 0; i < menuuseclass1.size(); i++) {
			SheetTemplate template1 = new SheetTemplate();
			double TotalCount = 0.0;
			MenuUseFulClass menu = new MenuUseFulClass();
			menu = menuuseclass1.get(i);
			if (str.equals("���뵥")) {
				TotalCount = incomesqlite.TotalCountBySource(db1,
						menu.getMenuUsefulName(), time);
			}
			else if (str.equals("֧����")) {
				TotalCount = paysqlite.TotalCountBySource(db2,
						menu.getMenuUsefulName(), time);
			} else if (str.equals("��")) {
				TotalCount = yingshousqlite.TotalCountBySource(db3,
						menu.getMenuUsefulName(), time, "0");
			} else if (str.equals("���")) {
				TotalCount = yingfusqlite.TotalCountBySource(db4,
						menu.getMenuUsefulName(), time, "0");
			} else if (str.equals("ʵ�յ�")) {
				TotalCount = yingshousqlite.TotalCountBySource(db3,
						menu.getMenuUsefulName(), time, "1");
			} else if (str.equals("ʵ����")) {
				TotalCount = yingfusqlite.TotalCountBySource(db4,
						menu.getMenuUsefulName(), time, "1");
			}
			   if (TotalCount != 0) {
				template1.setClassName(str);
				template1.setClassSource(menu.getMenuUsefulName());
				template1.setCount(TotalCount);
				template1.setTime(time);
				template1.setOperation("����");
				sheetTemplateDatas.add(template1);
			}
			
			}
	}
	//��ȡ��������
	public void GetDataCount(String str,String time){
		SheetTemplate template1 = new SheetTemplate();
		CountEntity count=new CountEntity();
		count=countSqlite.QueryByDate(db6, time);
		template1.setClassName(str);
		if(time.length()>4){
		template1.setClassSource("�����ʲ�����");}
		else{
			template1.setClassSource("�����ʲ�����");
		}
		template1.setCount(count.getShouRuCount()+count.getShiShouCount()-count.getShiFuCount()-count.getZhiChuCount());
		template1.setTime(time);
		template1.setOperation("����");
		sheetTemplateDatas.add(template1);
	}
	
}
