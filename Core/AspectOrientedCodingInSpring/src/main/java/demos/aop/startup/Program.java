package demos.aop.startup;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {
	public static void main(String[] args) throws Exception {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

		MyBean bean = context.getBean("advisedBean", MyBean.class);
		bean.doTests();

		context.close();
	}
}
