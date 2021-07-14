package jvm.test;

/**
 * 测试JVM
 * 分代收集理论，加入-XX:+PrintGCDetails打印GC详情信息
 *
 * @author Leon
 */
public class GCTest {

    public static void main(String[] args) {
        byte[] allocation1, allocation2,allocation3,allocation4,allocation5;
        allocation1 = new byte[30900*1024];  // 对象进入新生代的Eden区，占用72%
        allocation2 = new byte[30900*1024];  // 对象进入老年代 老年代占用23%
        allocation3 = new byte[30900*1024];  // allocation2,3,4,5加起来占用老年代94的内存
        allocation4 = new byte[30900*1024];
        allocation5 = new byte[30900*1024];
    }

}
