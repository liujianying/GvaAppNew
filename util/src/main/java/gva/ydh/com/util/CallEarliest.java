package gva.ydh.com.util;

/** 
 *  
 * 回调接口,回调方法运行于主线程 
 * @ClassName: CallEarliest    
 * @version 1.0 2012-1-16 下午11:23:41    
 * @param <T> 
 */  
public interface CallEarliest<T> {  
      
    public void onCallEarliest() throws Exception;  
}  