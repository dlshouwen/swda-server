package com.dlshouwen.swda.utils;

import com.dlshouwen.swda.entity.base.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * demo utils
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class DemoUtils {

	/** base */
	private static final String base = "abcdefghijklmnopqrstuvwxyz0123456789";

	/** first name */
	private static final String firstName = "李王张刘陈杨赵黄周吴徐孙胡朱高林何郭马罗梁宋郑谢韩唐冯于董萧程曹袁邓许傅沈曾彭吕苏卢蒋蔡贾丁魏薛叶阎余潘杜戴夏锺汪田任姜范方石姚谭廖邹熊金陆郝孔白崔康毛邱秦江史顾侯邵孟龙万段雷钱汤尹黎易常武乔贺赖龚文";

	/** girl */
	private static final String girl = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 ";

	/** boy */
	private static final String boy = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";

	/** email suffix */
	private static final String[] email_suffix = "@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@live.cn,@outlook.com,@aol.com,@ask.com,@live.com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net,@googlemail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn".split(",");

	/** tel */
	private static final String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153,180,181,182,183,185,186,188,189".split(",");

	/**
	 * get random number
	 * @param min
	 * @param max
	 * @return number
	 */
	public static int number(int min, int max) {
		return (int)(Math.random()*(max-min+1)+min);
	}

	/**
	 * get random email
	 * @param min
	 * @param max
	 * @return email
	 */
	public static String email(int min, int max) {
		int length = number(min, max);
		StringBuilder email = new StringBuilder();
		for (int i=0; i<length; i++) {
			int number = (int)(Math.random()*base.length());
			email.append(base.charAt(number));
		}
		email.append(email_suffix[(int)(Math.random()*email_suffix.length)]);
		return email.toString();
	}
	
	/**
	 * get random ip
	 * @return ip
	 */
	public static String ip() {
		return number(1, 255)+"."+number(0, 255)+"."+number(0, 255)+"."+number(1, 255);
	}

	/**
	 * get random phone
	 * @return phone
	 */
	public static String phone() {
		int index = number(0, telFirst.length - 1);
		String first = telFirst[index];
		String second = String.valueOf(number(1, 888) + 10000).substring(1);
		String third = String.valueOf(number(1, 9100) + 10000).substring(1);
		return first + second + third;
	}

	/**
	 * get random name
	 * @return name
	 */
	public static String name() {
		int index = number(0, firstName.length()-1);
		String first = firstName.substring(index, index+1);
		int sex = number(0, 1);
		String str = boy;
		int length = boy.length();
		if (sex == 0) {
			str = girl;
			length = girl.length();
		}
		index = number(0, length - 1);
		String second = str.substring(index, index + 1);
		int hasThird = number(0, 1);
		String third = "";
		if (hasThird == 1) {
			index = number(0, length - 1);
			third = str.substring(index, index + 1);
		}
		return first + second + third;
	}
	
	/**
	 * get random boy
	 * @return name
	 */
	public static String boy() {
		int index = number(0, firstName.length()-1);
		String first = firstName.substring(index, index+1);
		String str = boy;
		int length = boy.length();
		index = number(0, length - 1);
		String second = str.substring(index, index + 1);
		int hasThird = number(0, 1);
		String third = "";
		if (hasThird == 1) {
			index = number(0, length - 1);
			third = str.substring(index, index + 1);
		}
		return first + second + third;
	}
	
	/**
	 * get random girl
	 * @return name
	 */
	public static String girl() {
		int index = number(0, firstName.length()-1);
		String first = firstName.substring(index, index+1);
		String str = girl;
		int length = girl.length();
		index = number(0, length - 1);
		String second = str.substring(index, index + 1);
		int hasThird = number(0, 1);
		String third = "";
		if (hasThird == 1) {
			index = number(0, length - 1);
			third = str.substring(index, index + 1);
		}
		return first + second + third;
	}
	
	/**
	 * get random date
	 * @param start
	 * @param end
	 * @return date
	 */
	public static Date date(String start, String end) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate= sdf.parse(start);
		Date endDate= sdf.parse(end);
		long time = (long) (Math.random() * (endDate.getTime() - startDate.getTime()) + startDate.getTime());
		return new Date(time);
	}
	
	/**
	 * get random dict key
	 * @param key
	 * @return dict key
	 */
	public static String dictKey(String key) {
		String[] keys = Data.dict.get(key).keySet().toArray(new String[] {});
		return keys[DemoUtils.number(0, keys.length-1)];
	}
	
	/**
	 * get random dict value
	 * @param key
	 * @return dict value
	 */
	public static String dictValue(String key) {
		return Data.dict.get(key).get(dictKey(key));
	}
	
}