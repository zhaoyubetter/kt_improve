# 1.Kotlin 专题之集合操作符与应用

在日常的项目开发中，集合（数组）应该是我们打交道最多的了，比如：排序，数据存储，等等；在Kotlin中，

官方提供了很多非常方便好用的操作符方便我们进行开发，他们大多是通过扩展函数结合高阶函数实现的，如果了解过RxJava，那么Kt的集合操作符很容易就上手了；



> 如果您有更好的例子或是对本文档示例有自己的建议，欢迎告诉我，同时更新此文档，非常感谢；



**本文档，所有源代码请参考**

[source code](https://github.com/zhaoyubetter/kt_improve/tree/master/src/main/java/chapter1_operator/sample)



## 1.1 参考资源

- http://kotlinlang.org/docs/reference/basic-syntax.html
- http://www.cnblogs.com/Jetictors/p/9237108.html
- https://zhuanlan.zhihu.com/c_183300276
- https://www.jianshu.com/p/8f32de00c5dc

## 1.2 应用示例说明

示例中的数据为个人添加的模拟数据，仅限测试，不代表实际生产数据，仅限学习与测试；


# 2. 操作符的分类

可将集合操作符分为6大类：

1. 筛选操作符；
2. 总数（并集）操作符；
3. 映射类操作符；
4. 生成类操作符；
5. 排序类操作符
6. 元素操作符；

为避免篇幅过长，每一类操作符，一些操作符与相应的例子，请参考上面的参考资料，这就不班门弄斧了；

## 2.1 操作符总览图

![集合操作符](https://github.com/zhaoyubetter/MarkdownPhotos/raw/master/img/kotlin/operator_total.jpg)

## 2.2 筛选类操作符

![筛选操作符](https://github.com/zhaoyubetter/MarkdownPhotos/raw/master/img/kotlin/operator_filter.jpg)

### 2.2.1 filter

filter为筛选所有满足条件的项目，将所有满足条件的项目，形成一个新的集合并返回；

其kt源码如下：

```kotlin
public inline fun <T> Iterable<T>.filter(predicate: (T) -> Boolean): List<T> {
    return filterTo(ArrayList<T>(), predicate)
}

public inline fun <T, C : MutableCollection<in T>> Iterable<T>.filterTo(destination: C, 		predicate: (T) -> Boolean): C {
    // 符合 predicate 高阶函数，则添加到新的集合
    for (element in this) if (predicate(element)) destination.add(element)
    return destination
}
```

可以看到 `filter`最终会调用`filterTo` 内联扩展函数，通过遍历当前接收者`Iterable`，并判断是否符合高阶函数`predicate`条件，是则添加到新集合，并返回新集合；

### 2.2.2 drop

drop 为丢弃前面n个元素后，并返回一个新的列表；

其Kt源码如下：

```kotlin
public fun <T> Iterable<T>.drop(n: Int): List<T> {
    // 1.前置检查
    require(n >= 0) { "Requested element count $n is less than zero." }
    // 2.如果n为0，直接调用toList方法，返回整个集合
    if (n == 0) return toList()
    
    // 3.声明list局部变量
    val list: ArrayList<T>
    if (this is Collection<*>) {
        val resultSize = size - n	     // list 局部变量集合大小
        if (resultSize <= 0)
            return emptyList()			 // 集合大小 < n,则返回空集合
        if (resultSize == 1)			 // = 1时，则返回最后一个元素
            return listOf(last())
        list = ArrayList<T>(resultSize)	 // 创建list局部变量
        if (this is List<T>) {
            if (this is RandomAccess) {	 // 从 n 往后的 item 全部添加到 list变量集合
                for (index in n until size)
                    list.add(this[index])
            } else {
                for (item in listIterator(n))
                    list.add(item)
            }
            return list
        }
    } else {
        list = ArrayList<T>()
    }
    
    var count = 0
    for (item in this) {			// 从 n 往后的 item 全部添加到 list变量集合
        if (count++ >= n) list.add(item)
    }
    return list.optimizeReadOnlyList()
}
```

大概的源码分析，请见上面的注释内容；

示例代码：

```kotlin
val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
list.drop(3).forEach { print("$it ") }  // 4 5 6 7 8 9 
```

### 2.2.3 take

take 为带走前面n项，将这n项形成新的集合并返回；

其Kt源码如下：

```kotlin
public fun <T> Iterable<T>.take(n: Int): List<T> {
    // 1.前置检查
    require(n >= 0) { "Requested element count $n is less than zero." }
    // 2.如果n为0，返回空集合
    if (n == 0) return emptyList()
    if (this is Collection<T>) {
        if (n >= size) return toList()		// 返回整个集合
        if (n == 1) return listOf(first())	// 返回第一项
    }
    // 3.创建新的集合
    var count = 0
    val list = ArrayList<T>(n)
    for (item in this) {		 // 返回前n项
        if (count++ == n)
            break
        list.add(item)
    }
    return list.optimizeReadOnlyList()
}
```

源码类似于`drop`操作符，以n为分割点，一个往前，一个向后分割；

### 2.2.4 slice

slice为切片，意为取集合的某个部分（`此部分的元素是连续的`），或是集合中的某几个元素(`这几个元素不一定是连续的`)，并形成新的集合返回；

其Kt源码如下：

```kotlin
// 取某个部分
public fun <T> List<T>.slice(indices: IntRange): List<T> {
    if (indices.isEmpty()) return listOf()
    return this.subList(indices.start, indices.endInclusive + 1).toList()
}
// 取某几个元素
public fun <T> List<T>.slice(indices: Iterable<Int>): List<T> {
    val size = indices.collectionSizeOrDefault(10)
    if (size == 0) return emptyList()
    val list = ArrayList<T>(size)
    for (index in indices) {
        list.add(get(index))
    }
    return list
}
```

示例代码：

```kotlin
val list = listOf(0, 1, 2, 3, 4, 5, 6, 7, 111, 10)
println(list.slice(IntRange(1, 4)))         // [1, 2, 3, 4]
println(list.slice(listOf(0, 2, 4, 8)))     // [0, 2, 4, 111]
```

### 2.2.5 distinct

distinct为消除重复;

其Kt相关源码如下：

```kotlin
// 直接通过set转
public fun <T> Iterable<T>.distinct(): List<T> {
    return this.toMutableSet().toList()
}
// distinctBy
public inline fun <T, K> Iterable<T>.distinctBy(selector: (T) -> K): List<T> {
    val set = HashSet<K>()
    val list = ArrayList<T>()
    for (e in this) {
        val key = selector(e)
        if (set.add(key))		// 如果没重复，添加到集合
            list.add(e)
    }
    return list
}
```

示例代码：

```kotlin
val list = listOf(1, 1, 2, 2, -1, -2, 3, 9, -9, 5)
println(list.distinct())                   // [1, 2, -1, -2, 3, 9, -9, 5]
println(list.distinctBy { Math.abs(it) })  // [1, 2, 3, 9, 5]
```

## 2.3 总数(并集)操作符

![筛选操作符](https://github.com/zhaoyubetter/MarkdownPhotos/raw/master/img/kotlin/operator_union.jpg)

### 2.3.1 any,count,all,none

这几个操作符为判断集合中元素是否满足的特定条件的操作符；

- any 有一个元素满足条件，返回true;
- all 必须所有原生满足条件，返回true;
- none 如果没有任何元素满足条件，返回true;
- count 返回满足条件的item个数；

其Kt源码如下：

```kotlin
// any
public inline fun <T> Iterable<T>.any(predicate: (T) -> Boolean): Boolean {
    if (this is Collection && isEmpty()) return false
    // 不会遍历所有元素
    for (element in this) if (predicate(element)) return true
    return false
}
// all
public inline fun <T> Iterable<T>.all(predicate: (T) -> Boolean): Boolean {
    if (this is Collection && isEmpty()) return true
    for (element in this) if (!predicate(element)) return false
    return true
}

// 其他略
```

示例代码：

```kotlin
val list = listOf(1, 2, 3, 0, -1, 4, 5, 6, 7, 8, 9)
// 只要一个成立即可
println("\n======== any ========================")
println(list.any { it > 0 })   // true

println("\n======== all ========================")
println(list.all { it > 0 })	// false

println("\n======== count ========================")
println(list.count { it % 2 == 0 })  // 5

println("\n======== none ========================")
// 没有一个满足条件，返回 true
println(list.none { it > 10 })  //true
```

### 2.3.2 reduce

reduce为从集合的第一项到最后一项进行累计，类似于滚雪球；

其Kt源码如下：

```kotlin
public inline fun <S, T: S> Iterable<T>.reduce(operation: (acc: S, T) -> S): S {
    // 1.拿到iterator
    val iterator = this.iterator()
    if (!iterator.hasNext()) throw 
    			UnsupportedOperationException("Empty collection can't be reduced.")
    // 2.获取集合的第一项
    var accumulator: S = iterator.next()
   	// 3.循环调用 lambada operation 
    while (iterator.hasNext()) {
        // 4.重新赋值 accumulator
        accumulator = operation(accumulator, iterator.next())
    }
    return accumulator
}
```

示例代码：

```kotlin
// 示例1：计算1累计到100的值
(1 .. 100).reduce { acc, next -> acc + next }   // 5050

// 示例2：拼接逗号，再也不要处理最后一个逗号了
val ids = listOf("id1","id2","id3","id4")
println(ids.reduce { acc, next -> "$acc,$next" })  // id1,id2,id3,id4
```

### 2.3.3 fold

fold 类似于reduce操作符，其新增了一个初始值；

略；

### 2.3.4 forEach

forEach为遍历集合中所有元素，并对每个元素，执行操作；

源码比较简单，略；

### 2.3.5 max, min, sum 

这几个操作符都比较简单，略；

## 2.4 映射类操作符

![映射类操作符](https://github.com/zhaoyubetter/MarkdownPhotos/raw/master/img/kotlin/opertor_map.jpg)

### 2.4.1 map

map为转换，将集合中元素通过转换方法转换，并将转换后的结果返回；

其Kt源码如下：

```kotlin
public inline fun <T, R> Iterable<T>.map(transform: (T) -> R): List<R> {
    return mapTo(ArrayList<R>(collectionSizeOrDefault(10)), transform)
}
public inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.mapTo(destination: C, 			transform: (T) -> R): C {
    // 遍历所有元素，执行变换
    for (item in this)	
        destination.add(transform(item))
    return destination
}
```

示例代码：

```kotlin
val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
// 每个元素 * 2，并返回
list.map { it * 2 }.forEach { print("$it" ) }
```



### 2.4.2  flatmap

相当于map操作符，flatmap就显得很复杂；

flatmap用于合并2个集合（`铺平`），形成新的集合；

其Kt源码如下：

```kotlin
public inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.flatMapTo(destination: 			C, transform: (T) -> Iterable<R>): C {
    // 1. 遍历自身
    for (element in this) {
        // 2.将自身的每个元素执行变换，变换的返回结果是 Iterable 类型
        val list = transform(element)
        // 3.添加到新集合，并返回
        destination.addAll(list)
    }
    return destination
}
```

示例代码：

```kotlin
// 示例1，铺平集合, 结合源码看，比较好理解
val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
val list2 = listOf("a", "b", "c", "d")
listOf(list,list2).flatMap { it -> it }.forEach {
    print("$it ")   // 1 2 3 4 5 6 7 8 9 a b c d 
}

// 示例2，参考rxJava中的例子，每个学生有不同的课程
data class Course(val id: Int, val name: String)
data class Student(val name: String, val courseList: List<Course>)

// === 课程
val kotin = Course(1, "Kotlin")
val java = Course(2, "Java")
val py = Course(3, "Python")
val js = Course(4, "Javascript")
val go = Course(5, "Golang")
val c = Course(6, "C/C++")

// === 学生
val stud1 = Student("better", listOf(kotin, java))
val stud2 = Student("cz", listOf(kotin, java, py))
val stud3 = Student("cc", listOf(go, c, js))

// 打印所有学生的课程, ===》 use map 
listOf(stud1, stud2, stud3).map { it.courseList }.forEach {
    it.forEach { print("${it.name} ")}
}

// 打印所有学生的课程, ===》 use flatMap
listOf(stud1, stud2, stud3).flatMap { it.courseList }.forEach { print("${it.name} ") }
// 看到区别了么？使用flatMap少了一个forEach调用，流程更清晰
```

后面的应用例子的，也会重点说明 flatMap操作符；

### 2.4.3 groupBy

- groupBy 将集合中的元素按条件分组，并返回Map；
- groupingBy 用于按键（key）分组，同时折叠（fold）每个组；**源码有些复杂，暂不分析，O(∩_∩)O~**

其Kt源码如下：

```kotlin
// destination 为 LinkedHashMap
public inline fun <T, K, M : MutableMap<in K, MutableList<T>>> 
				Iterable<T>.groupByTo(destination: M, keySelector: (T) -> K): M {
    for (element in this) {
        // 1.通过lambda获取key
        val key = keySelector(element)	  		
        // 2.更新key对应的内容
        val list = destination.getOrPut(key) { ArrayList<T>() }
        list.add(element)
    }
    return destination
}
```

示例代码：

```kotlin
// groupBy
val array = intArrayOf(-3, -2, -1, 0, 1, 2, 3, 4)
array.groupBy { if(it > 0) "正数" else "负数" }	//{负数=[-3, -2, -1, 0], 正数=[1, 2, 3, 4]}

// groupingBy 来自官方的例子
val words = "one two three four five six seven eight nine ten".split(' ')
words.groupingBy { it.first() }.eachCount().forEach { it ->
    print("(${it.key}, ${it.value}) ") // (o, 1) (t, 3) (f, 2) (s, 2) (e, 1) (n, 1) 
}
```

## 2.5 元素操作符
![元素操作符](https://github.com/zhaoyubetter/MarkdownPhotos/raw/master/img/kotlin/operator_element.jpg)

元素操作符，相对比较简单，略；

## 2.6 排序类操作符
![排序类操作符](https://github.com/zhaoyubetter/MarkdownPhotos/raw/master/img/kotlin/operator_sort.jpg)

相对比较简单，略；

## 2.7 生成类操作符
![生成类操作符](https://github.com/zhaoyubetter/MarkdownPhotos/raw/master/img/kotlin/operator_produce.jpg)

### 2.7.1 zip

- zip 将2个集合按照下标组合成的Pair塞到新集合中并返回；
- zipWithNext 相邻的2个元素合并；

其Kt源码如下：

```kotlin
public inline fun <T, R, V> Iterable<T>.zip(other: Iterable<R>, transform: (a: T, b: R) -> 
                                            V): List<V> {
    val first = iterator()
    val second = other.iterator()
    val list = ArrayList<V>(minOf(collectionSizeOrDefault(10), 	
                                  other.collectionSizeOrDefault(10)))
    // 注意这里，如果2个集合大小不一致，大集合后续元素，将抹去
    while (first.hasNext() && second.hasNext()) {
        list.add(transform(first.next(), second.next()))
    }
    return list
}
```

示例代码：

```kotlin
val list1 = listOf(1, 2, 3, 4)
val list2 = listOf(5, 6, 7, 8, 9)
// 1.zip 按照下标组合成Pair
println(list1.zip(list2))      // [(1, 5), (2, 6), (3, 7), (4, 8)]

// 2.zipWithNext
println(list1.zipWithNext { a: Int, b: Int -> a * b })  // [2, 6, 12]
```

### 2.7.2 partition

partition 根据条件，将原集合拆分为2个集合；

其Kt源码如下：

```kotlin
public inline fun <T> Iterable<T>.partition(predicate: (T) -> Boolean): Pair<List<T>, 
											List<T>> {
    val first = ArrayList<T>()		// 2个集合
    val second = ArrayList<T>()
    for (element in this) {
        if (predicate(element)) {
            first.add(element)
        } else {
            second.add(element)
        }
    }
    return Pair(first, second)
}
```

示例代码：

```kotlin
val list3 = listOf(-3, -2, -1, 0, 1, 2, 3, 4)
println(list3.partition { it > 0 })  // ([1, 2, 3, 4], [-3, -2, -1, 0])
```



# 3. 综合应用示例

## 3.1 手机销售排行榜

案例：参考电商的手机销售排行榜，这里模拟一些数据，用来练习与巩固；

本案例需要用到的kt知识：

- kt 与java反射相关知识，如果您只关心操作符应用，可忽略数据获取这部分；

### 3.1.1 数据准备

1. 准备了一张Excel表格（Excel是为了编辑数据方便，最终是将Excel转成txt格式），如下：

| 品牌(brand) | 型号(mode) | 价格(price) | 内存(memory) | 容量(storage) | 分辨率(pixel) | 颜色(color) | 屏幕大小(screenSize) | 上市日期(sellDay) | 销量(台) |
| ----------- | ---------- | ----------- | ------------ | ------------- | ------------- | ----------- | -------------------- | ----------------- | -------- |
| 小米        | MI6        | 2588        | 4G           | 64G           | 1024 * 768    | 白          | 5.5                  | 2018/7/2          | 12000    |
| 小米        | MI8        | 2699        | 6G           | 64G           | 1080 * 2048   | 黑          | 6.21                 | 2018/8/2          | 500      |
| 小米        | 6X         | 1189        | 4G           | 32G           | 1024 * 768    | 白          | 5.5                  | 2017/7/2          | 3900     |
| 小米        | 红米6      | 899         | 4G           | 64G           | 1080 * 2048   | 黑          | 5                    | 2017/5/2          | 5000     |
| 小米        | Note5      | 1199        | 4G           | 128G          | 1080 * 2048   | 白          | 6                    | 2017/7/4          | 100      |
| 小米        | MAX 3      | 1999        | 6G           | 32G           | 1081 * 2048   | 白          | 6.5                  | 2017/7/5          | 303      |
| 一加        | 6          | 3299        | 6G           | 128G          | 1082 * 2048   | 黑          | 6.2                  | 2017/7/6          | 2000     |
| 一加        | 5T         | 3000        | 4G           | 32G           | 1083 * 2048   | 白          | 6                    | 2017/7/7          | 305      |
| 华为        | p20        | 3388        | 8G           | 64G           | 1084 * 2048   | 白          | 6                    | 2017/7/8          | 1000     |


2. 有了这个数据，我们就可以把他导出成，txt格式，具体如下：

> ```
> brand,mode,price,memory,storage,pixel,color,screenSize,sellTime,sellCount
> 小米,MI6,2588,4G,64G,1024 * 768,白,5.5,2018/7/2,12000
> 小米,MI8,2699,6G,64G,1080 * 2048,黑,6.21,2018/8/2,500
> ```

3. 建立 `data class` 对象，用来将文件中数据转换成具体的Bean类，用以形成集合元素数据；

程序代码如下：

```kotlin
// 数据类
data class PhoneGoods(
        var brand: String? = null,
        var mode: String? = null,
        var price: Double? = null,
        var memory: String? = null,
        var storage: String? = null,
        var pixel: String? = null,
        var color: String? = null,
        var screenSize: Double? = null,
        var sellTime: String? = null,
        var sellCount: Int? = null
)

// ======== 将文件中数据转存bean，并形成集合数据 ========================
// 获取最终集合数据
internal fun getPhoneGoods(): List<PhoneGoods> {
    var result: List<PhoneGoods> = ArrayList()
    val filePath = PhoneGoods::class.java.getResource("../operator_test.txt").path
    // 序号与属性
    var propsMap: Map<Int, KProperty1<PhoneGoods, *>>? = null

    File(filePath)?.run {
        if (exists()) {
            readLines().forEachIndexed { index, s ->
                if (index == 0) {        // 0行获取KPropertyMap
                    propsMap = getKPropertyMap(s)
                } else {
                    val propValues = s.split(",")
                    result += getOnePhoneGoods(propsMap, propValues)
                }
            }
        }
    }
    return result
}

// 分割文件第一行，获取属性 KProperty1 Map
private inline fun getKPropertyMap(s: String): Map<Int, KProperty1<PhoneGoods, *>> {
    var propsMap = mutableMapOf<Int, KProperty1<PhoneGoods, *>>()
    s.split(",").forEachIndexed { i, propName ->
        propsMap[i] = PhoneGoods::class.memberProperties.first { it.name == propName }
    }
    return propsMap.toMap()
}

// 通过反射读取一行数据，并转化成一个bean对象
private inline fun getOnePhoneGoods(propMap: Map<Int, KProperty1<PhoneGoods, *>>?, 
                                    propValues: List<String>): PhoneGoods {
    val phone = PhoneGoods()
    propMap?.forEach { i, it ->
        if (it is KMutableProperty1) {
            when (it.returnType.javaType) {
                java.lang.String::class.java -> {
                    it as KMutableProperty1<PhoneGoods, String?>
                    it.set(phone, propValues[i])
                }
                java.lang.Integer::class.java -> {       // 默认是Integer
                    it as KMutableProperty1<PhoneGoods, Int?>
                    it.set(phone, propValues[i].toIntOrNull())
                }
                java.lang.Double::class.java -> {        // 默认是double
                    it as KMutableProperty1<PhoneGoods, Double?>
                    it.set(phone, propValues[i].toDoubleOrNull())
                }
            }
        }
    }
    return phone
}
```

### 3.1.2 需求来了

1. 获取销量前三的手机品牌与型号
2. 获取手机价格大于等于 3000 小于等于 5000的手机品牌与型号
3. 获取手机价格大于等于 3000 小于等于 5000的销量前3的手机品牌与型号，与销售数量
4. 根据手机品牌分组，算出每个品牌销售量第一的手机型号，与最后一名的型号
5. 根据手机品牌分组，返回所有价格 >= 3000 的手机
6. 根据手机品牌分组，返回各个品牌的总营业额，并按倒序排序

> 大家可以自己试试，或添加更多的需求，您有更好的需求，或者对原数据有更好的修改建议，欢迎反馈给我，谢谢；

### 3.1.3 参考答案

```kotlin
val phones = getPhoneGoods()

println("\n======== 获取销量前三的手机品牌与型号 ========================")
phones.sortedByDescending { it.sellCount }.take(3).forEachIndexed { index, it ->                                                    
   println("Number ${index + 1}, 品牌：${it.brand}, 型号：${it.mode}, 销量：${it.sellCount}")
}

println("\n======== 获取手机价格大于 3000 小于 5000的手机品牌与型号 ========================")
// 因为price允许null，所以这里的写法有些奇怪，大家可以试试 null 相关操作符
phones.filter { it.price?.compareTo(3000) ?: 0 >= 0 && it.price?.compareTo(5000) ?: 0 <= 0 }.forEach {
    println("品牌：${it.brand}, 型号：${it.mode}, 价格：${it.price}")
}

println("\n======== 获取手机价格大于 3000 小于 5000的销量前3的手机品牌与型号==========")
phones.filter { it.price?.compareTo(3000) ?: 0 >= 0 && it.price?.compareTo(5000) ?: 0 <= 0 }.sortedByDescending { it.sellCount }.take(3)
.forEach {
    println("品牌：${it.brand}, 型号：${it.mode}, 价格：${it.price}, 销量: ${it.sellCount}")
}
```

 

groupBy 与 flatMap 综合运用

```kotlin
println("\n======== 根据手机品牌分组，算出每个品牌销售量第一的手机型号 =========")
// 品牌与销量铺平
phones.sortedByDescending { it.sellCount }.groupBy { it.brand }.flatMap { it.value.take(1) }.forEach {
    println("top one 品牌:${it.brand}, 型号：${it.mode}, 销量：${it.sellCount}")
}

println("\n======== 根据手机品牌分组，返回所有价格大于3000 的手机 ========================")
// 品牌随只有3个，但flatMap中返回的list是多个，所以最终结果也是多个
phones.sortedByDescending { it.sellCount }.groupBy { it.brand }.flatMap { it.value.filter { it.price?.compareTo(3000) ?: 0 >= 0 } }.forEach {
    println("品牌：${it.brand}, 型号：${it.mode}, 价格：${it.price}")
}

println("\n======== 根据手机品牌分组，返回各个品牌的总营业额 ========================")
// 这个嘛，大家先自己理解一下吧。O(∩_∩)O
phones.groupBy { it.brand }.flatMap { it ->
         listOf(Pair(it.key, it.value.sumByDouble { it.price ?: 0.0 }))
}.sortedByDescending { it.second }.forEach {
    println("品牌：${it.first}, 营业额：${it.second}")
}
```

## 3.2 单词统计

案例：单词统计需求，来源于最近记录的一些生词列表，用来分组暂时，个数统计，是否有音标等；

需要的用到知识：

- 正则表达式；


### 3.2.1数据准备

1. 生词是用一个txt文件保存的，如下格式：

```
#爱情与金钱
cambridge  [ˈkembrɪdʒ]	剑桥（英国城市）
cigarette  [sɪgə'ret]	香烟；纸烟
run across	跑着穿过
tablet	['tæblɪt]	 碑；药片；写字板；小块；平板电脑

Albert ['ælbət] 艾伯特（男子名）
at once	马上，立刻
Roger	 ['rɒdʒə]  罗杰（男子名）
across	[ə'krɒs]	穿过；横穿，横过；在对面
Annie	[ænɪ]	安妮（女子名）
get out	离开，出去；泄露；出版
quietly	[ˈkwaɪətli]	安静地
go across	穿过，横过；走过
Clarkson	克拉克森（男子名）
just then	就在那时
bottle	['bɑtl]	瓶子

#苏格兰玛丽女王
Mary  [ˈmɛərɪ]	 玛丽（女子名）
```



2. 分析原数据，我们可以得到，文件的`每一行都是一个单词`（空行与#开头除外）,并且他们的规律是：

- 每个单词有3个部分：`拼写`,`音标`,`中文解释`
- `拼写` 与 `中文解释`一定会有，`音标`可能没有；
- 单词的拼写，可能是一个词组；

3. 通过上面的分析，我们可用正则表示式，来分割没一行，获取数据，如下代码：

```kotlin
// 数据类
private data class Word(val spell: String, val soundmark: String?, val summary: String)

// 将每一行，转成单词
private inline fun lineToWord(line: String): Word {
    val regex = """(?<g1>([\s\w$])+)\s+(?<g2>\[.+?\])?(?<g3>.+)"""
    val p = Pattern.compile(regex)
    val matcher = p.matcher(line)
    if (matcher.find()) {
        return Word(matcher.group("g1").trim(), matcher.group("g2"), matcher.group("g3"))
    } else {
        throw RuntimeException("line ${line} is error!")
    }
}

// 获取数据集合
private fun getWords(): List<Word> {
    val filePath = Word::class.java.getResource(filePath).path
    val words = mutableListOf<Word>()
    val file = File(filePath)
    if (!file.exists()) {
        return words
    }
    // 过滤掉 空行 与 #开头的
    file.readLines(charset = Charsets.UTF_8).filterNot {
        it.trim().isEmpty() || it.startsWith("#")
    }.forEach {
        words += lineToWord(it)
    }
    return words
}
```

### 3.2.2 需求来了

1. 将所有生词按照字母排序；
2. 按首字母分组统计生词个数 （获取生词数最高的前3组）；
3. 找出所有没有音标的生词；
4. 找出所有词组；

> 大家可以自己试试，或添加更多的需求，您有更好的需求，或者对原数据有更好的修改建议，欢迎反馈给我，谢谢；



### 3.2.3 参考答案

```kotlin
/**
 * === 1. 将所有生词按照字母排序  sortedWith ==============
 */
private fun resolve_one(words: List<Word?>) {
    println("===1. 将所有生词按照字母排序===")
    words.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it?.spell ?: "" })
    .forEach {
        println(it?.spell)
    }
}

/**
 * === 2. 按首字母分组统计生词个数 groupBy ==============
 */
private fun resolve_two(words: List<Word>) {
    println("===2. 按首字母分组统计生词个数===")
    words.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.spell })
            .groupBy { it.spell[0].toLowerCase() }
            .forEach { key, value ->
                println("$key: [${value.joinToString { it.spell }}]")
            }

    println("======2.1. 生词数最高的前三组======")
    words.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.spell })
            .groupBy { it.spell[0].toLowerCase() }      // Map<String, List<Word>>
            .map { it }                                 // Entry
            .sortedByDescending { it.value.size }       // order by Entry.values.size
            .take(3)
            .forEach {
                println("${it.key}: [${it.value.size}]")
            }
}
```



其他几个，请自己实现实现吧，O(∩_∩)O~；