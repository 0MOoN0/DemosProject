package test;

import java.lang.reflect.Constructor;

/**
 *
 * 反射Demo
 * @author Leon
 */
public class Reflection_Constructor {

    private String name="relection_demo";

    public static void main(String[] args) throws Exception {
        Class clazz = Class.forName("test.Reflection_Constructor");
//        Reflection reflection = (Reflection) clazz.newInstance();  如果不存在无参构造方法，会抛出异常
        Constructor[] constructors = clazz.getConstructors();
        // 获取所有构造方法
        for(Constructor constructor:constructors){
            constructor.newInstance("a");
        }
    }

    public Reflection_Constructor(String name){
        System.out.println("name");
    }

}
