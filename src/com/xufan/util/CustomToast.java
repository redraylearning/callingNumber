/**********************************************************************
 * FILE                ：CustomToast.java
 * PACKAGE			   ：com.xufan.wirelessOrder.util
 * AUTHOR              ：xufan
 * DATE				   ：2013-3-20 下午9:16:11
 * FUNCTION            ：
 *
 * 杭州思伟版权所有
 *======================================================================
 * CHANGE HISTORY LOG
 *----------------------------------------------------------------------
 * MOD. NO.|  DATE    | NAME           | REASON            | CHANGE REQ.
 *----------------------------------------------------------------------
 *         |          | Iam        | Created           |
 *
 * DESCRIPTION:
 *
 ***********************************************************************/
package com.xufan.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 项目名称：WirelessOrder
 * 类名称：CustomToast
 * 类描述：
 * 创建人：xufan
 * 创建时间：2013-3-20 下午9:16:11
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-3-20 下午9:16:11
 * 修改备注：
 * @version：
 */
public class CustomToast {
        private static String oldMsg;  
        protected static Toast toast   = null;  
        private static long oneTime=0;  
        private static long twoTime=0;  
          
        public static void showToast(Context context, String s){      
            if(toast==null){   
                toast =Toast.makeText(context, s, Toast.LENGTH_SHORT);  
                toast.show();  
                oneTime=System.currentTimeMillis();  
            }else{  
                twoTime=System.currentTimeMillis();  
                if(s.equals(oldMsg)){  
                    if(twoTime-oneTime>Toast.LENGTH_SHORT){  
                        toast.show();  
                    }  
                }else{  
                    oldMsg = s;  
                    toast.setText(s);  
                    toast.show();  
                }         
            }  
            oneTime=twoTime;  
        }  
          
        public static void showToast(Context context, int resId){     
            showToast(context, context.getString(resId));  
        }  
    
}
