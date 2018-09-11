package chapter1__operator.practice

/**
 * 过滤操作符
 */
fun main(args: Array<String>) {
    val array = intArrayOf(-1, 2, 9, -20, 5, 3, -9, 18, 9, -7, 4)

    // 1.filter
    println(array.filter { it > 0 })

    // 2.filterNot, 所有不满足条件的
    println(array.filterNot { it > 0 })

    // 3.filterTo 过滤后 添加到指定的集合
    val list2 = mutableListOf(100, 200)
    println(array.filterTo(list2) { it > 0 })

    // 4.filterIndex，输出索引
    val filterIndex = array.filterIndexed { index, i -> print("($index, $i)");i > 0 }
    println()
    println(filterIndex)


    // 5.take 获取前2个
    println("take: " + array.take(2))
    // 6. takeLast 最后2个
    println("takeLast: " + array.takeLast(2))
    // 7.takeWhile
    val array2 = intArrayOf(-3, -2, -1, 0, 1, 2, 3, 4)
    val takeWhile = array2.takeWhile { it < 0 }
    println("takeWhile: " + takeWhile)
    val takeLastWhile = array2.takeLastWhile { it > 0 }
    println("takeLastWhile: " + takeLastWhile)

    // 8.drop，丢弃前面的几个
    val drop = array.drop(2)
    println("drop:$drop")   // 等价于
    println("takeLast: ${array.takeLast(array.size - 2)}")

    // 9.dropWhile
    val dropWhile = array2.dropWhile { it < 0 }
    println("dropWhile: $dropWhile")

    val list3 = listOf(1, 2, 3, -5, 0, -3, -2, 1)
    val dropWhile2 = list3.dropWhile { it > 0 }
    println("dropWhile2:$dropWhile2") // 输出 [-5, 0, -3, -2, 1]，看源码理解
    // 10.dropLastWhile 与 上类似

    // 11.slice
    println(array2.slice(listOf(0, 2, 4)))
    array2.sliceArray((0..2)).forEach { print(it) }

    println()

    // 12.distinct 去掉重复的
    val list = listOf(-1, 2, 9, -20, 5, 3, -1, 2, 9, -9, 18, 9, -7, 4)
    println("distinct: ${list.distinct()}")
    println("distinct: ${list.distinctBy { it }}")

}
