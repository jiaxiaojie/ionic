package test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.thinkgem.jeesite.common.test.SpringTransactionalContextTests;
import com.thinkgem.jeesite.modules.message.MessageFacade;

public class YdtTest extends SpringTransactionalContextTests {
	@Autowired
	private MessageFacade messageFacade;
	
	@Test
	@Rollback(false)
	public void test() {
	}
	
	
}
interface Null {}
interface Robot {
	void printName();
}
class SnowRemovalRobot implements Robot {
	SnowRemovalRobot(String name) {
		
	}
	@Override
	public void printName() {
		System.out.println("snowRemoal robot");
	}
	
}
class NullRobotProxyHandler implements InvocationHandler {
	private String nullName;
	private Robot proxied = new NRobot();
	NullRobotProxyHandler(Class<? extends Robot> type) {
		nullName = type.getSimpleName() + " NullRobot";
	}
	private class NRobot implements Null, Robot {
		public String name() {
			return nullName;
		}
		public void printName() {
			System.out.println(name());
		}
	}
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(proxied, args);
	}
}
class NullRobot {
	public static Robot newNullRobot(Class<? extends Robot> type) {
		return (Robot)Proxy.newProxyInstance(NullRobot.class.getClassLoader(),
				new Class[]{Null.class, Robot.class}, new NullRobotProxyHandler(type));
	}
	public static void main(String[] args) {
		Robot[] bots = {
				new SnowRemovalRobot("snowBee"),
				newNullRobot(SnowRemovalRobot.class)
		};
		for(Robot robot : bots) {
			robot.printName();
		}
	}
}