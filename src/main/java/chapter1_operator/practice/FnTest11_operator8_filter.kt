package chapter1__operator.practice

/**
 * filterTo
 */
fun main(args: Array<String>) {
    val list1 = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 10)
    val list2 = list1.map { it * 10 }
    val list3 = list2.map { it * 10 }
    val listTotal = mutableListOf<Int>()
    list1.filterTo(listTotal) { it % 2 == 0 }
    list2.filterTo(listTotal) { it % 2 == 0 }
    list3.filterTo(listTotal) { it % 2 == 0 }

    println("======== filterTo ========================")
    println(listTotal.toString())

    println("======== filterIndexed ========================")
    // 打印出index
    listTotal.filterIndexed { index, it ->
        println("index:$index, value:$it")
        it % 2 == 0
    }


    println("======== filterIsInstance ========================")
    val animals = listOf<Animl>(
            Dog("dog1"),
            Pig("pig1"),
            Dog("dog2"),
            Pig("pig2"),
            Dog("dog3"))
    animals.filterIsInstance<Dog>().forEach { println(it.name) }        // 输出dog

    println("======== filterNot ========================")
    val array = intArrayOf(-1, 2, 9, -20, 5, 3, -9, 18, 9, -7, 4)
    array.filterNot { it > 0 }.forEach { println(it) }

    println("======== filterNotNull ========================")
    val notNull = listOf<Animl?>(
            Dog("dog1"),
            Pig("pig1"),
            null,
            Dog("dog3"),
            null)
    notNull.filterNotNull().forEach { println(it.name) }
}

abstract class Animl(val name: String)
class Dog(name: String) : Animl(name)
class Pig(name: String) : Animl(name)