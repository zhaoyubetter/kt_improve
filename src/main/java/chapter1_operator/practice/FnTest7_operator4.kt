package chapter1__operator.practice

/**
 * 映射操作
 */
fun main(args: Array<String>) {
    val array = intArrayOf(-3, -2, -1, 0, 1, 2, 3, 4)

    // 1.map
    array.map { it * 2 }.forEach { print("$it ") }
    println()
    // 2.mapIndex
    array.mapIndexed { index: Int, i: Int ->
        print("($index,$i)")
        i * 2
    }.forEach { print("$it ") }

    println()
    // 3.mapIndexedTo 指定list
    val mut = mutableListOf<Int>()
    val operRef = { index: Int, i: Int ->
        i * 2
    }
    array.mapIndexedTo(mut, operRef)
    println(mut)

    // 4. flatMap 合并集合
    val array2 = arrayListOf("a", "b", "c", "d")
    val flat = array.flatMap { listOf(it) + array2 }
    println(flat)

    /*
    组合: [-3, -2, -1, 0, 1, 2, 3, 4] 与 ["a", "b", "c", "d"] 组合成
    -3a,-2b,-1c,0d, ...
    */
    println(array.flatMap { a -> array2.map { "$a$it" } })

    // 5.groupBy 分组，返回map
    val groupBy = array.groupBy { if(it > 0) "正数" else "负数" }
    println(groupBy)
}