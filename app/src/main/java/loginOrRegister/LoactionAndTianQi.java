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
		private static String LoactionText="位置未知", City,LoactionTextTotal;
		private final static String PATH = "http://wthrcdn.etouch.cn/weather_mini?city=";
		protected static final int SUCCESS = 0;
		protected static final int INVALID_CITY = 1;
		protected static final int ERROR = 2;
		private String ul;
		/** 输出日志标识 */
		private String TAG = "MainActivity";
		/** 定位成功标识 */
		private final int LOCATION_SUCCESS = 3;
		/** 定位失败标识 */
		private final int LOCATION_ERROR = 4;
		/** 百度定位器 */
		private LocationClient mLocClient;
		/** 延迟发送，设置成全局，用于解决快速按按钮导致的开启了多个线程 */
		private Runnable mRunable;
		// 定位处理Handler
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
					LoactionText = "定位失败，无内容";
					break;
				}
			}

		};
		// 自定义定位监听器；
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
						int end = city.indexOf("市");
						if (end != -1) {
							city = city.substring(0, end);
						}
						end = address.indexOf("市");
						if (end != -1) {
							address = address.substring(end + 1, address.length());
						}
						// 定位成功
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
		/** 定位操作处理 */

		// 处理天气的线程
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
					Toast.makeText(context, "城市无效", 0).show();
					break;

				case ERROR:

					Toast.makeText(context, "网络无效", 0).show();

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
		// 线程销毁
		public void onDestroy() {
			if (mLocClient != null) {
				mLocClient.stop();// 停止定位
			}
			//super.onDestroy();
		}

		// 开始定位
		private void Start() {
			loactionhandler.removeCallbacks(mRunable);
			mRunable = new Runnable() {

				@Override
				public void run() {
					mLocClient.start();// 开始定位

				}
			};
			loactionhandler.postDelayed(mRunable, 0);
		}

		// 初始化定位数据
		private void initData() {
			Calendar cal=Calendar.getInstance();
			Date date=new Date(System.currentTimeMillis());
			cal.setTime(date);
			Month=cal.get(Calendar.MONTH)+1;
			MaxDay=cal.getActualMaximum(Calendar.DATE);
			NowDay=cal.get(Calendar.DAY_OF_MONTH);
			/* 初始化定位信息 */
			mLocClient = new LocationClient(context);// 创建定位器
			mLocClient.registerLocationListener(mLocationListener);

			LocationClientOption mLocOption = new LocationClientOption();// 位置区域设置
			mLocOption.setOpenGps(true);// 开启手机GPS导航
			mLocOption.setAddrType("all");// 返回的定位结果包含地址信息
			mLocOption.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02.必须用这个才能和地图完美匹配。
			mLocOption.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
			mLocOption.disableCache(true);// 禁止启用缓存定位
			mLocOption.setPoiNumber(5); // 最多返回POI个数
			mLocOption.setPoiDistance(1000); // poi查询距离
			mLocOption.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
			mLocClient.setLocOption(mLocOption);
		}

		// 初始化天气数据（图片）
		public void initTianQiData() {
			TianQiMap = new HashMap<String, Integer>();
			TianQiMap.put("晴", R.drawable.qing);
			TianQiMap.put("小雨", R.drawable.xiaoyu);
			TianQiMap.put("中雨", R.drawable.zhongyu);
			TianQiMap.put("暴雨", R.drawable.baoyu);
			TianQiMap.put("阴", R.drawable.yin);
			TianQiMap.put("雾", R.drawable.wu);
			TianQiMap.put("多云", R.drawable.duoyun);
			TianQiMap.put("大雪", R.drawable.daxue);
			TianQiMap.put("冰雹", R.drawable.bingbao);
			TianQiMap.put("冻雨", R.drawable.dongyu);
			TianQiMap.put("阵雨", R.drawable.zhenyu);
			TianQiMap.put("阵雪", R.drawable.zhenxue);
			TianQiMap.put("雷阵雨", R.drawable.leizhenyu);
			TianQiMap.put("大雨", R.drawable.dayu);
		}
		// 获取天气数据的方法
		public void StartTianQi(final String str) {
			// 发起请求给那个网站

			new Thread() {

				public void run() {

					try {

						ul = PATH + URLEncoder.encode(str, "UTF-8");

						URL url = new URL(ul);
						// 设置必要的参数信息
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();

						conn.setConnectTimeout(0);

						conn.setRequestMethod("GET");
						// 判断响应码

						int code = conn.getResponseCode();

						if (code == 200) {

							// 连接网络成功

							InputStream in = conn.getInputStream();

							String data = StreamPool.decodeStream(in);
							// 解析json格式的数据

							JSONObject jsonObj = new JSONObject(data);

							// 获得desc的值

							String result = jsonObj.getString("desc");

							if ("OK".equals(result)) {

								// 城市有效，返回了需要的数据

								JSONObject dataObj = jsonObj.getJSONObject("data");
								JSONArray jsonArray = dataObj
										.getJSONArray("forecast");

								// 通知更新ui

								Message msg = Message.obtain();

								msg.obj = jsonArray;

								msg.what = SUCCESS;

								mhandler.sendMessage(msg);
							} else {

								// 城市无效

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

		// 利用正则表达式对字符串进行处理
		public void dealString(int month,String str, int i) {
			TianQi tianqi = new TianQi();

			String tianqidata = str.replaceAll(
					"[\\p{Punct}\\p{Space}\\p{Symbol}]+", "");
			// String TQ=tianqi.replaceAll("[a-zA-Z]","");
			String TQ = tianqidata.replaceAll("[A-Z]", "").replaceAll("date", "")
					.replaceAll("日", "号").replaceAll("high", "B")
					.replaceAll("高温", "").replaceAll("fengli", "C")
					.replaceAll("低温", "").replaceAll("low", "")
					.replaceAll("fengxiang", "D").replaceAll("type", "E");
			if (i == 0) {
				tianqi.setDate("今天");
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
				// str.indexOf("级")+1
				tianqi.setCondition(TQ.substring(TQ.indexOf("级") + 1,
						TQ.indexOf("D"))
						+ "℃"
						+ "~"
						+ TQ.substring(TQ.indexOf("B") + 1, TQ.indexOf("C"))
						+ "℃"
						+ TQ.substring(TQ.indexOf("D") + 1, TQ.indexOf("E"))
						+ str.substring(str.indexOf("-") - 1, str.indexOf("级") - 1)
						+ str.substring(str.indexOf("级") - 1, str.indexOf("级") + 1));
			} else {
				tianqi.setCondition(TQ.substring(TQ.indexOf("级") + 1,
						TQ.indexOf("D"))
						+ "℃"
						+ "~"
						+ TQ.substring(TQ.indexOf("B") + 1, TQ.indexOf("C"))
						+ "℃"
						+ TQ.substring(TQ.indexOf("D") + 1, TQ.indexOf("E"))
						+ TQ.substring(TQ.indexOf("C") + 1, TQ.indexOf("级") + 1));
			}

			tianqi.setTianqi(TQ.substring(TQ.indexOf("E") + 1, TQ.length()));
			tianqi.setImage(TianQiMap.get(TQ.substring(TQ.indexOf("E") + 1,
					TQ.length())));
			if (tianqi != null) {
				TianQiDatas.add(tianqi);
			}
		}
		// 返回定位城市的名称
			public static String returnCity() {
				return City;
			}

			// 返回当地的天气数据
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