package chapter1__operator.practice

fun main(args: Array<String>) {
    val list = listOf(1, 2, 3, 0, -1, 4, 5, 6, 7, 8, 9)
    // 只要一个成立即可
    println("\n======== any ========================")
    println(list.any { it > 0 })


    println("\n======== all ========================")
    println(list.all { it > 0 })


    println("\n======== count ========================")
    println(list.count { it % 2 == 0 })


    println("\n======== none ========================")
    // 没有一个满足条件，返回 true
    println(list.none { it > 10 })

}


