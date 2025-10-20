import bean.UserService;
import com.small.springframework.beans.factory.config.BeanDefinition;
import com.small.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ApiTest {
        @Test
        public void test_BeanFactory(){
                // 1.初始化 BeanFactory
                DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

                // 2.注册 bean
                BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
                beanFactory.registerBeanDefinition("userService", beanDefinition);

                // 3.第一次获取 bean
                UserService userService = (UserService) beanFactory.getBean("userService");
                userService.queryUserInfo();

                // 4.第二次获取 bean from Singleton
                UserService userService_singleton = (UserService) beanFactory.getBean("userService");
                userService_singleton.queryUserInfo();
        }

        @Test
        public void test_BeanFactory_With_Param() throws Exception {
                DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
                // 2.注册 bean
                BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
                beanFactory.registerBeanDefinition("userService", beanDefinition);
                // 3.第一次获取 bean
                UserService userService = (UserService) beanFactory.getBean("userService", "ly", 10);
                userService.queryUserInfo();
        }

        @Test
        public void test_cglib() {
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(UserService.class);
                enhancer.setCallback(new NoOp() {
                        @Override
                        public int hashCode() {
                                return super.hashCode();
                        }
                });
                Object obj = enhancer.create(new Class[]{String.class}, new Object[]{"ly", 01});
                System.out.println(obj);
        }

        @Test
        public void test_newInstance() throws IllegalAccessException, InstantiationException {
                UserService userService = UserService.class.newInstance();
                System.out.println(userService);
        }

        @Test
        public void test_constructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
                Class<UserService> userServiceClass = UserService.class;
                Constructor<UserService> declaredConstructor = userServiceClass.getDeclaredConstructor(String.class, int.class);
                UserService userService = declaredConstructor.newInstance("ly", 01);
                System.out.println(userService);
        }

        @Test
        public void test_parameterTypes() throws Exception {
                Class<UserService> beanClass = UserService.class;
                Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
                Constructor<?> constructor = null;
                for (Constructor<?> ctor : declaredConstructors) {
                        if (ctor.getParameterTypes().length == 2) {
                                constructor = ctor;
                                break;
                        }
                }
                System.out.println(Arrays.stream(constructor.getParameterTypes()).toList());
                Constructor<UserService> declaredConstructor = beanClass.getDeclaredConstructor(constructor.getParameterTypes());
                UserService userService = declaredConstructor.newInstance("ly", 01);
                System.out.println(userService);
        }
}
