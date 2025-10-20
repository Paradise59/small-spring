一个“Bean工厂”的完整工作被巧妙地拆分成了几个独立的职责
# 1. 职责分离
一个“Bean工厂”的完整工作被巧妙地拆分成了几个独立的职责，每个职责由不同的组件（接口和类）承担
## 职责一：管理Bean的“定义” (BeanDefinition)
谁负责： BeanDefinition 类、BeanDefinitionRegistry 接口、DefaultListableBeanFactory 类。

做什么： BeanDefinition 存储Bean的元数据（比如它的Class类型）。BeanDefinitionRegistry 定义了“注册”和“获取” 

解耦点： 框架把“Bean长什么样” (定义) 和 “Bean的实例” (对象) 分开了

## 职责二：管理Bean的“单例实例” (Singleton Instance)
谁负责： SingletonBeanRegistry 接口、DefaultSingletonBeanRegistry 类

做什么： 定义“注册”和“获取”已经创建好的单例Bean实例的规范。DefaultSingletonBeanRegistry 提供了具体的存储（如图中的 singletonOb

解耦点： 框架把“如何存储和管理单例”这个功能，从Bean的创建流程中抽离出来了

## 职责三：负责“创建Bean实例” (Instantiation)

谁负责： AbstractAutowireCapableBeanFactory 类。

做什么： 它的核心职责是**createBean**方法。它知道如何根据一个 BeanDefinition（职责一）来实例化一个真正的Java对象（比如通过反射）。

解耦点： “创建”这个动作本身被封装起来了。它不关心Bean定义从哪来，也不关心创建好的Bean实例存到哪去。

## 职责四：充当“协调者”，定义获取Bean的“核心流程”
谁负责： AbstractBeanFactory 抽象类。

做什么： 它提供了 getBean 的核心逻辑，即“模板模式”

# 2. 模板方法模式 (Template Method Pattern) 带来的扩展性
AbstractBeanFactory 里的 getBean(String name) 方法就是模板方法，它定义了获取一个Bean的标准算法（骨架）：
1. 尝试从单例缓存中获取实例 (调用 getSingleton，这是职责二的功能)。 
2. 如果缓存中没有，就去获取Bean的定义 (调用抽象方法 getBeanDefinition，这是职责一的功能)。 
3. 如果获取到了定义，就根据定义创建Bean实例 (调用抽象方法 createBean，这是职责三的功能)。 
4. 将创建好的实例放入单例缓存 (调用 addSingleton，职责二的功能)，然后返回

AbstractBeanFactory 作为“模板”，它只管流程，不管具体实现。它把“具体实现”交给了它的子类：
1. getBeanDefinition 是抽象的，它不知道定义存在哪。DefaultListableBeanFactory 继承后，就实现了这个方法，告诉它：“去我的 beanDefinitionMap 里找！” 
2. createBean 是抽象的，它不知道怎么创建实例。AbstractAutowireCapableBeanFactory 继承后，就实现了这个方法，告诉它：“用我这里的反射逻辑来创建！”