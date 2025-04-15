package com.dlshouwen.swda.core.sms.strategy;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.CharsetUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.dlshouwen.swda.bms.platform.entity.SmsPlatform;
import com.dlshouwen.swda.core.base.exception.SwdaException;
import com.dlshouwen.swda.core.base.utils.JsonUtils;

import javax.net.ssl.*;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * huawei sms strategy
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class HuaweiSmsStrategy implements SmsStrategy {
	
	/** wsse header format */
	private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
	
	/** auth header value */
	private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";
	
	/** platform */
	private final SmsPlatform platform;

	/**
	 * constructor
	 * @param platform
	 */
	public HuaweiSmsStrategy(SmsPlatform platform) {
//		set platform
		this.platform = platform;
	}

	/**
	 * send
	 * @param mobile
	 * @param params
	 */
	@SuppressWarnings("resource")
	@Override
	public void send(String mobile, Map<String, String> params) {
//		set template params
		String templateParams = null;
		if (MapUtil.isNotEmpty(params)) {
			templateParams = JsonUtils.toJsonString(params.values().toArray(new String[0]));
		}
//		get body
		String body = buildRequestBody(platform.getSenderId(), "+86"+mobile, platform.getTemplateId(), templateParams, null, platform.getSignName());
//		if body null
		if (StringUtils.isBlank(body)) {
			throw new SwdaException("body is null.");
		}
//		build wsse header
		String wsseHeader = buildWsseHeader(platform.getAccessKey(), platform.getSecretKey());
//		wsse header is null
		if (StringUtils.isBlank(wsseHeader)) {
			throw new SwdaException("wsse header is null.");
		}
		try {
//			use https
			trustAllHttpsCertificates();
//			get url
			URL url = new URL(platform.getUrl() + "/sms/batchSendSms/v1");
//			defined connection
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//			set host name verifier
			HostnameVerifier verifier = (hostname, session) -> true;
			connection.setHostnameVerifier(verifier);
//			set connect info
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Authorization", AUTH_HEADER_VALUE);
			connection.setRequestProperty("X-WSSE", wsseHeader);
//			connect
			connection.connect();
//			write body to stream
			IoUtil.writeUtf8(connection.getOutputStream(), true, body);
//			get response code
			int status = connection.getResponseCode();
//			success
			if (status == HttpStatus.OK.value()) {
//				get response value
				String response = IoUtil.read(connection.getInputStream(), CharsetUtil.CHARSET_UTF_8);
//				parse to huawei sms result
				HuaweiSmsResult result = JsonUtils.parseObject(response, HuaweiSmsResult.class);
//				assert result is not null
				assert result != null;
//				if not success then throw exception
				if (!"000000".equals(result.code)) {
					throw new SwdaException(result.description);
				}
			} else {
//				connect error
				throw new SwdaException(IoUtil.read(connection.getErrorStream(), CharsetUtil.CHARSET_UTF_8));
			}
		} catch (Exception e) {
			throw new SwdaException(e.getMessage());
		}
	}

	/**
	 * build request body
	 * @param sender
	 * @param receiver
	 * @param templateId
	 * @param templateParas
	 * @param statusCallBack
	 * @param signature
	 * @return request body
	 */
	private static String buildRequestBody(String sender, String receiver, String templateId, String templateParas, String statusCallBack, String signature) {
//		if has error
		if (null == sender || null == receiver || null == templateId || sender.isEmpty() || receiver.isEmpty() || templateId.isEmpty()) {
			throw new SwdaException("buildRequestBody(): sender, receiver or templateId is null.");
		}
//		defined info
		Map<String, String> info = new HashMap<>();
//		set from, to , template id
		info.put("from", sender);
		info.put("to", receiver);
		info.put("templateId", templateId);
//		put template params
		if (null != templateParas && !templateParas.isEmpty()) {
			info.put("templateParas", templateParas);
		}
//		put status call back
		if (null != statusCallBack && !statusCallBack.isEmpty()) {
			info.put("statusCallback", statusCallBack);
		}
//		put signature
		if (null != signature && !signature.isEmpty()) {
			info.put("signature", signature);
		}
//		defined body
		StringBuilder body = new StringBuilder();
//		defined temp
		String temp = "";
//		for each info
		for (String key : info.keySet()) {
			try {
//				set value to temp
				temp = URLEncoder.encode(info.get(key), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
//			append to body
			body.append(key).append("=").append(temp).append("&");
		}
//		delete last chart and return
		return body.deleteCharAt(body.length()-1).toString();
	}

	/**
	 * build wsse header
	 * @param appKey
	 * @param appSecret
	 * @return wsse header
	 */
	private static String buildWsseHeader(String appKey, String appSecret) {
		if (null == appKey || null == appSecret || appKey.isEmpty() || appSecret.isEmpty()) {
			throw new SwdaException("buildWsseHeader(): appKey or appSecret is null.");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		String time = sdf.format(new Date());
		String nonce = UUID.randomUUID().toString().replace("-", "");
		MessageDigest md;
		byte[] passwordDigest = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update((nonce + time + appSecret).getBytes());
			passwordDigest = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String passwordDigestBase64Str = Base64.getEncoder().encodeToString(passwordDigest);
		return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
	}

	/**
	 * trust all https certificates
	 * @throws Exception
	 */
	private static void trustAllHttpsCertificates() throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) {
			}
			public void checkServerTrusted(X509Certificate[] chain, String authType) {
			}
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		}};
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	/**
	 * huawei sms result
	 */
	@Data
	static class HuaweiSmsResult {
		
		private String code;
		
		private String description;
		
		private List<Object> result;
	}

}
