package bean;

public class UserService {
        private String name;
        private int age;
        public UserService() {
        }

        public UserService(String name, int age) {
                this.name = name;
                this.age = age;
        }

        public void queryUserInfo() {
                System.out.println("查询用户信息：" + name + ", " + age);
        }

        @Override
        public String toString() {
                final StringBuilder sb = new StringBuilder("打印用户信息：");
                sb.append("").append(name);
                sb.append(", ").append(age);
                return sb.toString();
        }

}
