package com.atguigu.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SmsUtil {

	// 产品名称:云通信短信API产品,开发者无需替换
	static final String product = "Dysmsapi";
	// 产品域名,开发者无需替换
	static final String domain = "dysmsapi.aliyuncs.com";

	// TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
	static final String accessKeyId = "LTAI3buexRAagkdy";
	static final String accessKeySecret = "A6hpWJbF3Zz6wj3jxuBe40Mwryt1Zz";

	public static String sendSms(String phonenum, String msg) {

		System.out.println(phonenum + ":" + msg + ":");

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IAcsClient acsClient = new DefaultAcsClient(profile);
		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest req = new SendSmsRequest();
		// 必填:待发送手机号
		req.setPhoneNumbers(phonenum);
		// 必填:短信签名-可在短信控制台中找到
		req.setSignName("张老师短信服务");
		// 必填:短信模板-可在短信控制台中找到
		req.setTemplateCode("SMS_112475358");
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		req.setTemplateParam("{\"name\":\"" + msg + "\"}");

		// hint 此处可能会抛出异常，注意catch
		SendSmsResponse rsp = null;
		try {
			rsp = acsClient.getAcsResponse(req);
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		System.out.println(phonenum + ":" + msg + ":" + rsp.getCode());

		return rsp.getCode();
	}
}
