package chapter1_operator.sample.filescoding

import java.io.File


/**
 * 获取目录：
 * https://www.cnblogs.com/keyi/p/6282838.html
 * 批量改变文件编码
 */

private const val filePath = "../../../chapter1/txt"

private data class DataClass(val name: String)

fun main(args: Array<String>) {
    before()
}

/**
 * 未编码之前
 */
private fun before() {
    val filepath = DataClass::class.java.getResource(filePath).path
    val file = File(filepath)

    // get all dirs
    file.listFiles { dir -> dir.isDirectory }.forEach {
        //        println(it.name)
    }

    // get all files
    file.listFiles { dir -> dir.isDirectory }.flatMap {
        it.listFiles().toList()
    }.forEach { println(it.readText())}

}