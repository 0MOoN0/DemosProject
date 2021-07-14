# JVM
## 说明
探索JVM堆区的情况，启动时加入`-XX:+PrintGCDetails`打印GC详情信息

## 笔记
### 对象区域分配
JDK1.7及以前Java堆可以细分为新生代和老年代，JDK1.8后永久代被元空间取代，使用直接内存，新生代又可以分为Eden区和两个Survivor区（From区、to区）。

大多数情况下，新生对象会分配在Eden区，当Eden区没有足够的空间进行分配时，会将对象分配到老年代
代码：jvm.test.GCTest
