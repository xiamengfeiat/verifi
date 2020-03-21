package com.atguigu.servlet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atguigu.utils.VerifyCodeConfig;

import redis.clients.jedis.Jedis;

public class CodeSenderServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1获取参数验空
		String phoneNo = request.getParameter("phone_no");
		if (phoneNo == null) {
			return;
		}
		//-----------------------------------------------------------
		// 1.5验证发送次数3次，24小时
		// 获取计数器
		Jedis jedis = new Jedis(VerifyCodeConfig.HOST, VerifyCodeConfig.PORT);
		String countKey = VerifyCodeConfig.PHONE_PREFIX + phoneNo + VerifyCodeConfig.COUNT_SUFFIX;
		String countStr = jedis.get(countKey);
		// 验空
		if (countStr == null) {
			jedis.setex(countKey, VerifyCodeConfig.SECONDS_PER_DAY, "1");
		} else {
			// 判断是否3次
			int count = Integer.parseInt(countStr);
			if (count >= VerifyCodeConfig.COUNT_TIMES_1DAY) {
				jedis.close();
				response.getWriter().print("limit");
				return;
			} else {
				jedis.incr(countKey);
			}
		}
		//-----------------------------------------------------------
		// 2生成校验码,6位
		String code = getCode(VerifyCodeConfig.CODE_LEN);

		// 3保存校验码，120秒
		String codeKey = VerifyCodeConfig.PHONE_PREFIX + phoneNo + VerifyCodeConfig.PHONE_SUFFIX;
		jedis.setex(codeKey, VerifyCodeConfig.CODE_TIMEOUT, code);
		jedis.close();
		// 4发送
		System.out.println(code);
		// 5返回
		response.getWriter().print(true);

	}

	private String getCode(int len) {
		String code = "";
		for (int i = 0; i < len; i++) {
			int rand = new Random().nextInt(10);
			code += rand;
		}
		return code;
	}

}
