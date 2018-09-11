package chapter1__operator.practice

/**
 * 顺序操作符
 */
fun main(args: Array<String>) {
    val array = listOf(-3, -2, -1, 0, 1, 2, 3, 4)

    //1.reverse 反转
    val copy = ArrayList(array)
    println("\n======== reversed ========================")
    println(copy.reversed())

    println("\n======== sortedBy ========================")
    //2.sortedBy 根据绝对值来
    println(copy.sortedBy { Math.abs(it) })

    //3.sortedDescending
    println("\n======== sortedDescending ========================")
    println(copy.sortedDescending())

    // 4.根据绝对值来
    println("\n======== sortedDescending ========================")
    println(copy.sortedByDescending { Math.abs(it) })

    println("\n======== sortedWith use Comparator ========================")
    val list2 = listOf("a", "b", "c", "d", "e")
    println(list2.sortedWith(Comparator { o1, o2 -> (o1[0].toInt() - o2[0].toInt()) }))
//
//    // 5.自然排序
//    val list = listOf(2,1,0,-9,5,2,-8,-2)
//    println(list.sorted())
}