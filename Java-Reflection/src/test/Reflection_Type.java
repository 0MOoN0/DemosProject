package test;

import sun.reflect.generics.reflectiveObjects.GenericArrayTypeImpl;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 测试获取泛型
 *
 * @author Leon
 */
public class Reflection_Type {
    private List<Integer> list1;
    private List<Set<Integer>> listSet;
    private List<Integer[]> list_gentype_array;

    public Set<String> fun1(Map<Integer, String> map) {
        return null;
    }

    // 成员变量泛型参数获取，非数组
    public static void getFieldGenericType() {
        try {
            Class clazz = Reflection_Type.class;
            Field field = clazz.getDeclaredField("list1");
            Type type = field.getGenericType();  //取得field的type
            ParameterizedTypeImpl parameterizedType = (ParameterizedTypeImpl) type; //强转成具体的实现类，如果是数组类型，会报错
            Type[] genericTypes = parameterizedType.getActualTypeArguments();  //取得包含的泛型类型
            System.out.println(genericTypes[0]);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private List<Integer>[] list_array;  // 无法将type转为ParameterizedTypeImpl
    // 数组成员变量泛型参数获取
    public static void getFieldGenericArrayType() throws Exception {
        Class<Reflection_Type> clazz = Reflection_Type.class;
        Field list_array = clazz.getDeclaredField("list_array");
        Type type = list_array.getGenericType();
        GenericArrayTypeImpl genericArrayType = (GenericArrayTypeImpl) type;
        Type genericComponentType = genericArrayType.getGenericComponentType();
        System.out.println(genericComponentType);  //对象类型  java.util.List<java.lang.Integer>
        ParameterizedTypeImpl gentype = (ParameterizedTypeImpl) genericComponentType;
        Type[] actualTypeArguments = gentype.getActualTypeArguments();
        System.out.println(actualTypeArguments[0]);  // 获取实际的泛型对象  class java.lang.Integer
    }




    public static void main(String[] args) throws Exception {
        getFieldGenericArrayType();
//        getFieldGenericType();
    }


}
