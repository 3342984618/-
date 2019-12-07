package com.duen.test;

import java.util.ArrayList;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.duen.bean.User;
import com.duen.utils.StringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext-redis.xml")
public class WeekTest {
       @Resource
       private RedisTemplate<String, Object> redisTemplate;
       
       @Test
       public void testDate() {
    	    ArrayList<User> userlist=new ArrayList<>();
    	    for (int i = 0; i <100000; i++) {
				User user = new User();
				//id
				user.setId(i+1);
				//随机姓名
				String randomChinese = StringUtils.getRandomChinese(3);
				user.setName(randomChinese);
				//随机性别
				Random random = new Random();
				String sex=random.nextBoolean()?"男":"女";
				user.setSex(sex);
				//随机手机号
				String phone = "13"+StringUtils.getRandomNumber(9);
				user.setPhone(phone);
				//随机邮箱
				int random2=(int) (Math.random()*20);
				int len=random2<3?random2+3:random2;
				String randomStr = StringUtils.getRandomStr(len);
				String randomEmailSuffex = StringUtils.getRandomEmailSuffex();
			    user.setEmail(randomStr+randomEmailSuffex);
			    
			    //随机生日
			    String randomBirthday = StringUtils.randomBirthday();
			    user.setBirthday(randomBirthday);
			    
			    userlist.add(user);
			    
    	    }
    	    
		/*
		 * //JDK序列化方式 System.out.println("JDK序列化方式"); long start =
		 * System.currentTimeMillis(); BoundListOperations<String, Object> boundListOps
		 * = redisTemplate.boundListOps("jdk"); boundListOps.leftPush(userlist); long
		 * end = System.currentTimeMillis(); System.out.println("总耗时"+(end-start)+"毫秒");
		 */
            
            //JSON序列化方式
		/*
		 * System.out.println("JSON序列化方式"); long start = System.currentTimeMillis();
		 * BoundListOperations<String, Object> boundListOps =
		 * redisTemplate.boundListOps("json"); boundListOps.leftPush(userlist); long end
		 * = System.currentTimeMillis(); System.out.println("总耗时"+(end-start)+"毫秒");
		 */
            //Hash序列化
            System.out.println("Hash序列化方式");
            System.out.println("保存条数:100000");
    	    long start = System.currentTimeMillis();
             BoundHashOperations<String, Object, Object> boundHashOps = redisTemplate.boundHashOps("hash");
          
			  boundHashOps.put("hash",userlist);
		  
            long end = System.currentTimeMillis();
            System.out.println("总耗时"+(end-start)+"毫秒");
       }
}
