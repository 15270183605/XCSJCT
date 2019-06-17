package loginOrRegister;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.jiacaitong.R;

import dingWei.StreamPool;
import entity.TianQi;
  public class LoactionAndTianQi {
     private Context context;
	  private Map<String, Integer> TianQiMap;
		private static List<TianQi> TianQiDatas;
		private int Month,MaxDay,NowDay;
		private static String LoactionText="λ��δ֪", City,LoactionTextTotal;
		private final static String PATH = "http://wthrcdn.etouch.cn/weather_mini?city=";
		protected static final int SUCCESS = 0;
		protected static final int INVALID_CITY = 1;
		protected static final int ERROR = 2;
		private String ul;
		/** �����־��ʶ */
		private String TAG = "MainActivity";
		/** ��λ�ɹ���ʶ */
		private final int LOCATION_SUCCESS = 3;
		/** ��λʧ�ܱ�ʶ */
		private final int LOCATION_ERROR = 4;
		/** �ٶȶ�λ�� */
		private LocationClient mLocClient;
		/** �ӳٷ��ͣ����ó�ȫ�֣����ڽ�����ٰ���ť���µĿ����˶���߳� */
		private Runnable mRunable;
		// ��λ����Handler
		private Handler loactionhandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case LOCATION_SUCCESS:
					Map<String, String> map = (HashMap<String, String>) msg.obj;
					LoactionText = map.get("city") + "--" + map.get("address")
							+ "-" + map.get("addstr");
					LoactionTextTotal= map.get("addstr")+map.get("str")+map.get("street");
					City = map.get("city");
					StartTianQi(City);
					//NowLoaction.setText(LoactionText);
					break;
				case LOCATION_ERROR:
					LoactionText = "��λʧ�ܣ�������";
					break;
				}
			}

		};
		// �Զ��嶨λ��������
		private BDLocationListener mLocationListener = new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				if (location != null) {
					String city = "";
					String address = "";
					String street = "";
					city = location.getCity();
					address = location.getAddrStr();
					String addstr = location.getAddrStr();
					String dis = location.getDistrict();
					String coor = location.getCoorType();
					String poi = location.getPoi();
					street=location.getStreetNumber();
					String str=location.getStreet();
					Message msg = loactionhandler.obtainMessage();
					if (city == null) {
						msg.what = LOCATION_ERROR;
						msg.sendToTarget();
					} else {
						int end = city.indexOf("��");
						if (end != -1) {
							city = city.substring(0, end);
						}
						end = address.indexOf("��");
						if (end != -1) {
							address = address.substring(end + 1, address.length());
						}
						// ��λ�ɹ�
						Map<String, String> map = new HashMap<String, String>();
						map.put("city", city);
						map.put("address", address);
						map.put("addstr", addstr);
						map.put("dis", dis);
						map.put("coor", coor);
						map.put("poi", poi);
						map.put("street", street);
						map.put("str", str);
						msg.what = LOCATION_SUCCESS;
						msg.obj = map;
						msg.sendToTarget();
					}

				} else {
					Message msg = loactionhandler.obtainMessage();
					msg.what = LOCATION_ERROR;
					msg.sendToTarget();
				}
				mLocClient.stop();
			}

			@Override
			public void onReceivePoi(BDLocation arg0) {
			}
		};
		/** ��λ�������� */

		// �����������߳�
		private Handler mhandler = new Handler() {

			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case SUCCESS:
					JSONArray data = (JSONArray) msg.obj;
					try {
						for (int i = 0; i < 4; i++) {
							String day = data.getString(i);
							if(i>=(MaxDay-NowDay)+1 && Month!=12){
								dealString(Month+1,day, i);
							}else if(i>=(MaxDay-NowDay)+1 && Month==12){
								dealString(1,day, i);
							}else{
							dealString(Month,day, i);}
						}

					} catch (Exception e) {

						// TODO Auto-generated catch block

						e.printStackTrace();

					}

					break;
				case INVALID_CITY:
					Toast.makeText(context, "������Ч", 0).show();
					break;

				case ERROR:

					Toast.makeText(context, "������Ч", 0).show();

					break;
				default:
					break;
				}
			};
		};
		public LoactionAndTianQi(Context context) {
			super();
			this.context = context;
			TianQiDatas = new ArrayList<TianQi>();
			initData();
			Start();
			initTianQiData();
		}
		// �߳�����
		public void onDestroy() {
			if (mLocClient != null) {
				mLocClient.stop();// ֹͣ��λ
			}
			//super.onDestroy();
		}

		// ��ʼ��λ
		private void Start() {
			loactionhandler.removeCallbacks(mRunable);
			mRunable = new Runnable() {

				@Override
				public void run() {
					mLocClient.start();// ��ʼ��λ

				}
			};
			loactionhandler.postDelayed(mRunable, 0);
		}

		// ��ʼ����λ����
		private void initData() {
			Calendar cal=Calendar.getInstance();
			Date date=new Date(System.currentTimeMillis());
			cal.setTime(date);
			Month=cal.get(Calendar.MONTH)+1;
			MaxDay=cal.getActualMaximum(Calendar.DATE);
			NowDay=cal.get(Calendar.DAY_OF_MONTH);
			/* ��ʼ����λ��Ϣ */
			mLocClient = new LocationClient(context);// ������λ��
			mLocClient.registerLocationListener(mLocationListener);

			LocationClientOption mLocOption = new LocationClientOption();// λ����������
			mLocOption.setOpenGps(true);// �����ֻ�GPS����
			mLocOption.setAddrType("all");// ���صĶ�λ���������ַ��Ϣ
			mLocOption.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02.������������ܺ͵�ͼ����ƥ�䡣
			mLocOption.setScanSpan(5000);// ���÷���λ����ļ��ʱ��Ϊ5000ms
			mLocOption.disableCache(true);// ��ֹ���û��涨λ
			mLocOption.setPoiNumber(5); // ��෵��POI����
			mLocOption.setPoiDistance(1000); // poi��ѯ����
			mLocOption.setPoiExtraInfo(true); // �Ƿ���ҪPOI�ĵ绰�͵�ַ����ϸ��Ϣ
			mLocClient.setLocOption(mLocOption);
		}

		// ��ʼ���������ݣ�ͼƬ��
		public void initTianQiData() {
			TianQiMap = new HashMap<String, Integer>();
			TianQiMap.put("��", R.drawable.qing);
			TianQiMap.put("С��", R.drawable.xiaoyu);
			TianQiMap.put("����", R.drawable.zhongyu);
			TianQiMap.put("����", R.drawable.baoyu);
			TianQiMap.put("��", R.drawable.yin);
			TianQiMap.put("��", R.drawable.wu);
			TianQiMap.put("����", R.drawable.duoyun);
			TianQiMap.put("��ѩ", R.drawable.daxue);
			TianQiMap.put("����", R.drawable.bingbao);
			TianQiMap.put("����", R.drawable.dongyu);
			TianQiMap.put("����", R.drawable.zhenyu);
			TianQiMap.put("��ѩ", R.drawable.zhenxue);
			TianQiMap.put("������", R.drawable.leizhenyu);
			TianQiMap.put("����", R.drawable.dayu);
		}
		// ��ȡ�������ݵķ���
		public void StartTianQi(final String str) {
			// ����������Ǹ���վ

			new Thread() {

				public void run() {

					try {

						ul = PATH + URLEncoder.encode(str, "UTF-8");

						URL url = new URL(ul);
						// ���ñ�Ҫ�Ĳ�����Ϣ
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();

						conn.setConnectTimeout(0);

						conn.setRequestMethod("GET");
						// �ж���Ӧ��

						int code = conn.getResponseCode();

						if (code == 200) {

							// ��������ɹ�

							InputStream in = conn.getInputStream();

							String data = StreamPool.decodeStream(in);
							// ����json��ʽ������

							JSONObject jsonObj = new JSONObject(data);

							// ���desc��ֵ

							String result = jsonObj.getString("desc");

							if ("OK".equals(result)) {

								// ������Ч����������Ҫ������

								JSONObject dataObj = jsonObj.getJSONObject("data");
								JSONArray jsonArray = dataObj
										.getJSONArray("forecast");

								// ֪ͨ����ui

								Message msg = Message.obtain();

								msg.obj = jsonArray;

								msg.what = SUCCESS;

								mhandler.sendMessage(msg);
							} else {

								// ������Ч

								Message msg = Message.obtain();

								msg.what = INVALID_CITY;

								mhandler.sendMessage(msg);

							}

						}

					} catch (Exception e) {

						// TODO Auto-generated catch block

						e.printStackTrace();

						Message msg = Message.obtain();

						msg.what = ERROR;

						mhandler.sendMessage(msg);

					}

				};

			}.start();
		}

		// ����������ʽ���ַ������д���
		public void dealString(int month,String str, int i) {
			TianQi tianqi = new TianQi();

			String tianqidata = str.replaceAll(
					"[\\p{Punct}\\p{Space}\\p{Symbol}]+", "");
			// String TQ=tianqi.replaceAll("[a-zA-Z]","");
			String TQ = tianqidata.replaceAll("[A-Z]", "").replaceAll("date", "")
					.replaceAll("��", "��").replaceAll("high", "B")
					.replaceAll("����", "").replaceAll("fengli", "C")
					.replaceAll("����", "").replaceAll("low", "")
					.replaceAll("fengxiang", "D").replaceAll("type", "E");
			if (i == 0) {
				tianqi.setDate("����");
			} else {
				if (month < 10) {
					tianqi.setDate("0" + String.valueOf(month) + "/"
							+ TQ.substring(0, TQ.indexOf("B")));
				} else {
					tianqi.setDate(String.valueOf(month) + "/"
							+ TQ.substring(0, TQ.indexOf("B")));
				}
			}
			if (str.indexOf("-") != -1) {
				// str.indexOf("��")+1
				tianqi.setCondition(TQ.substring(TQ.indexOf("��") + 1,
						TQ.indexOf("D"))
						+ "��"
						+ "~"
						+ TQ.substring(TQ.indexOf("B") + 1, TQ.indexOf("C"))
						+ "��"
						+ TQ.substring(TQ.indexOf("D") + 1, TQ.indexOf("E"))
						+ str.substring(str.indexOf("-") - 1, str.indexOf("��") - 1)
						+ str.substring(str.indexOf("��") - 1, str.indexOf("��") + 1));
			} else {
				tianqi.setCondition(TQ.substring(TQ.indexOf("��") + 1,
						TQ.indexOf("D"))
						+ "��"
						+ "~"
						+ TQ.substring(TQ.indexOf("B") + 1, TQ.indexOf("C"))
						+ "��"
						+ TQ.substring(TQ.indexOf("D") + 1, TQ.indexOf("E"))
						+ TQ.substring(TQ.indexOf("C") + 1, TQ.indexOf("��") + 1));
			}

			tianqi.setTianqi(TQ.substring(TQ.indexOf("E") + 1, TQ.length()));
			tianqi.setImage(TianQiMap.get(TQ.substring(TQ.indexOf("E") + 1,
					TQ.length())));
			if (tianqi != null) {
				TianQiDatas.add(tianqi);
			}
		}
		// ���ض�λ���е�����
			public static String returnCity() {
				return City;
			}

			// ���ص��ص���������
			public static List<TianQi> returnTainQiDatas() {
				return TianQiDatas;
			}

			public static String returnLoaction() {
				return LoactionText;
			}
			public static String returnLoactionTotal() {
				return LoactionTextTotal;
			}
}