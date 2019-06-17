package mine;

import tool.AutoTextureView;
import android.app.Activity;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.Window;

import com.example.jiacaitong.R;

public class CamerActivity extends Activity {
    private static final SparseIntArray ORIENTIONS=new SparseIntArray();
    static{
    	ORIENTIONS.append(Surface.ROTATION_0, 90);
    	ORIENTIONS.append(Surface.ROTATION_90, 0);
    	ORIENTIONS.append(Surface.ROTATION_180, 270);
    	ORIENTIONS.append(Surface.ROTATION_270, 180);
    	
    }
    private AutoTextureView textureView;
    //����ͷID,ͨ��0������ã�1����ǰ�ã�
    private String mCamerId="0";
    //��������ͷ�ĳ�Ա����;
    //private CameraDevice camerDevice;
    protected void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.licaiknowledge);
    }
}
