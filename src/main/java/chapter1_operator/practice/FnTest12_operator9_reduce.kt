package chapter1__operator.practice

fun main(args: Array<String>) {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    println("\n======== reduce ========================")
    println(list.reduce { init, it -> init + it })

    println("\n======== reduceRight ========================")
    // right to left
    val i = list.reduceRight { index, it ->
        print("index:$index,value:$it ")
        index + it
    }

    println("\n======== 1 .. 100 ========================")
    println((1 .. 100).reduce { acc, next -> acc + next })

    println("\n======== 拼接id ========================")
    val ids = listOf("id1","id2","id3","id4")
    println(ids.reduce { acc, next -> "$acc,$next" })

}
