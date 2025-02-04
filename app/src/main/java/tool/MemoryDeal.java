package tool;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

 //该类用于图片缓存，防止内存溢出
 
public class MemoryDeal {
	public static MemoryDeal cache;
	/** 用于Chche内容的存储 */
	Hashtable bitmapRefs;
	/** 垃圾Reference的队列（所引用的对象已经被回收，则将该引用存入队列中） */
	ReferenceQueue q;

	/**
	 * 继承SoftReference，使得每一个实例都具有可识别的标识。
	 */
	class BtimapRef extends SoftReference {
		Integer _key = 0;

		public BtimapRef(Bitmap bmp, ReferenceQueue q, int key) {
			super(bmp, q);
			_key = key;
		}
	}

	public MemoryDeal() {
		bitmapRefs = new Hashtable();
		q = new ReferenceQueue();

	}

	/**
	 * 取得缓存器实例
	 */
	public static MemoryDeal getInstance() {
		if (cache == null) {
			cache = new MemoryDeal();
		}
		return cache;

	}

	/**
	 * 以软引用的方式对一个Bitmap对象的实例进行引用并保存该引用
	 */
	public void addCacheBitmap(Bitmap bmp, Integer key) {
		cleanCache();// 清除垃圾引用
		BtimapRef ref = new BtimapRef(bmp, q, key);
		bitmapRefs.put(key, ref);
	}

	/**
	 * 依据所指定的drawable下的图片资源ID号（可以根据自己的需要从网络或本地path下获取），重新获取相应Bitmap对象的实例
	 */
	public Bitmap getBitmap(int resId, Context context) {
		Bitmap bmp = null;
		// 缓存中是否有该Bitmap实例的软引用，如果有，从软引用中取得。
		if (bitmapRefs.containsKey(resId)) {
			BtimapRef ref = (BtimapRef) bitmapRefs.get(resId);
			bmp = (Bitmap) ref.get();
		}
		// 如果没有软引用，或者从软引用中得到的实例是null，重新构建一个实例，
		// 并保存对这个新建实例的软引用
		if (bmp == null) {
			bmp = BitmapFactory.decodeResource(context.getResources(), resId);
			this.addCacheBitmap(bmp, resId);
		}
		return bmp;
	}

	public void cleanCache() {
		BtimapRef ref = null;
		while ((ref = (BtimapRef) q.poll()) != null) {
			bitmapRefs.remove(ref._key);
		}
	}

	// 清除Cache内的全部内容
	public void clearCache() {
		cleanCache();
		bitmapRefs.clear();
		System.gc();
		System.runFinalization();
	}

}
