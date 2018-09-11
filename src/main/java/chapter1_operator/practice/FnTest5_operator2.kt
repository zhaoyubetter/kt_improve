package chapter1__operator.practice

/**
 * 总数操作符
 */
fun main(args: Array<String>) {
    val array = intArrayOf(-1, 2, 9, -20, 5, 3, -9, 18, 9, -7, 4)

    // 1. any
    println(array.any { it > 0 })

    // 2.all
    println(array.all { it > 0 })

    // 3.none，是否都不满足条件
    println(array.none { it > 30 })

    // 4.类似 reduce，不过有个初始值
    val fold = array.fold(initial = 10) { a, b ->
        a + b
    }
    println("fold: $fold")

    // 5. foldRight 类似 reduceRight

    // 6.max
    val max = array.max()
    println("max:$max")

    // 7.maxBy --- 获取绝对值最大的数
    val maxBy = array.maxBy { Math.abs(it) }
    println("maxBy:$maxBy")

    // 8.min与minBy 与 max类似

    // 9. sum与sumBy
    println("sum: " + array.sum())
    println("sumBy: " + array.sumBy { Math.abs(it) })


    // 10.forEach、forEachIndex
    array.forEachIndexed { index, i ->  }
}