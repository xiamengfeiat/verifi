package com.atguigu.utils;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SmsUtil {

    public static String URL ="http://gw.api.taobao.com/router/rest";
	
	public static String APPKEY="23605043";
	
	public static String SECRET="974500f11f71df2e4a515d8ccb05d852";
	
	public static String TEMPLATE_CODE="SMS_63910126";

	
	public static String sendSms(  String phonenum, String  msg  )  {
 
 
	    System.out.println (phonenum+":"+msg+":");
	    
 
		TaobaoClient client = new DefaultTaobaoClient(URL, APPKEY, SECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend( "" );
		req.setSmsType( "normal" );
		req.setSmsFreeSignName( "张晨老师短信服务" );
		req.setSmsParamString( "{code:'"+msg+"'}" );
		req.setRecNum( phonenum );
		req.setSmsTemplateCode( TEMPLATE_CODE );
		AlibabaAliqinFcSmsNumSendResponse rsp=null;
		try {
			rsp = client.execute(req);
			System.out.println( phonenum+":"+msg+":"+rsp.getBody());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return rsp.getBody() ;
	}
}
