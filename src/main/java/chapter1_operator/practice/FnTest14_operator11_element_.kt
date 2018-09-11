package chapter1__operator.practice

fun main(args: Array<String>) {
    val list = listOf(-3, -2, -1, 0, -1, 1, 2, 3, 4)

    // 1.elementAtOrElse 如果越界会根据方法返回默认值
    println("\n======== elementAtOrElse ========================")
    val atOrElse = list.elementAtOrElse(20) { "index $it not found" }
    println(atOrElse)

    // 2.elementAtOrNull 查找元素，找不到，返回null
    println("\n======== elementAtOrNull ========================")
    println(list.elementAtOrNull(20))

    // 3.first 返回符合条件的第一个元素，没有 抛NoSuchElementException
    println("\n======== first ========================")
    println(list.first { it == -1 })
    println(list.first())

    println("\n======== firstOrNull ========================")
    println(list.firstOrNull { it == 10 })


    println("\n======== find ========================")
    println(list.find { it < 0 })

    println("\n======== findLast ========================")
    println(list.findLast { it < 0 })

    println("\n======== indexOf ========================")
    println(list.indexOf(20))

    println("\n======== indexOfFirst ========================")
    println(list.indexOfFirst { it == -1 })

    println("\n======== indexOfLast ========================")
    println(list.indexOfLast { it == -1 })

    println("\n======== lastIndexOf ========================")
    println(list.lastIndexOf(-1))

    println("\n======== single 是否只有一个element ========================")
    println(listOf(1).singleOrNull())
    println(listOf(1).single())

}

