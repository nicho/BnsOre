package com.bns.bnsore.data;

import com.bns.bnsore.entity.Task;
import com.bns.bnsore.entity.User;
import org.springside.modules.test.data.RandomData;

/**
 * Task相关实体测试数据生成.
 * 
 * @author calvin
 */
public class TaskData {

	public static Task randomTask() {
		Task task = new Task();
		task.setTitle(randomTitle());
		User user = new User(1L);
		task.setUser(user);
		return task;
	}

	public static String randomTitle() {
		return RandomData.randomName("Task");
	}
}