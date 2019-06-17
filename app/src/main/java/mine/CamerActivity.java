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
    //摄像头ID,通常0代表后置，1代表前置；
    private String mCamerId="0";
    //定义摄像头的成员变量;
    //private CameraDevice camerDevice;
    protected void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.licaiknowledge);
    }
}
