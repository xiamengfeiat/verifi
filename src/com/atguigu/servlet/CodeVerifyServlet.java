package com.atguigu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atguigu.utils.VerifyCodeConfig;

import redis.clients.jedis.Jedis;

public class CodeVerifyServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1获取参数2个，验空
		String phoneNo = request.getParameter("phone_no");
		String verifyCode = request.getParameter("verify_code");
		if (phoneNo == null || verifyCode == null) {
			return;
		}

		// 2 从redis获取校验码
		Jedis jedis = new Jedis(VerifyCodeConfig.HOST, VerifyCodeConfig.PORT);
		String codeKey = VerifyCodeConfig.PHONE_PREFIX + phoneNo + VerifyCodeConfig.PHONE_SUFFIX;
		String code = jedis.get(codeKey);
		jedis.close();
		
		// 3验证校验码
		if (verifyCode.equals(code)) {
			response.getWriter().print(true);
		}
	}

}
