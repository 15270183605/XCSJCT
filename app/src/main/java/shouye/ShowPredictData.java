package shouye;

import java.util.ArrayList;
import java.util.List;

import sqlite.CountPredictSQLite;
import Adapters.CountPredictAdater;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.jiacaitong.R;

import entity.CountPredict;

public class ShowPredictData extends Activity implements OnClickListener {
    private ImageView reback;
    private ListView AllPredictDateListView;
    private CountPredictSQLite predictsqlite;
    private SQLiteDatabase db;
    
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.showallpredictdata);
    	reback=(ImageView)findViewById(R.id.ReBack);
    	predictsqlite=new CountPredictSQLite(this, "Predict.db", null, 1);
    	db=predictsqlite.getReadableDatabase();
    	List<CountPredict> datas=new ArrayList<CountPredict>();
    	datas=predictsqlite.getAllDatas(db);
    	
    	AllPredictDateListView=(ListView)findViewById(R.id.AllPredictDataListView);
    	View view1 = LayoutInflater.from(this).inflate(
				R.layout.allpredictdataitem, null);
    	AllPredictDateListView.addHeaderView(view1);
    	CountPredictAdater adapter=new CountPredictAdater(this, datas);
    	AllPredictDateListView.setAdapter(adapter);
    	reback.setOnClickListener(this);
    }
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.ReBack:
			this.finish();
			break;
		}

	}

}
