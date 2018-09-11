package chapter1_operator.sample.words

import java.io.File

/**
 * 最近在读英语短文小说，发现了不少生词，顺便记录了一下，然后就有了一下需求；
 *
 * 统计单词数据
 * 需求：
 * 1. 将所有生词按照字母排序
 * 2. 按首字母分组统计生词个数
 * 3. 找出所有没有音标的生词
 * 4. 找出所有词组
 */

private const val filePath = "../../../chapter1/words.txt"

private data class Word(val spell: String?, val soundmark: String?, val summary: String?)

fun main(args: Array<String>) {
    getWords()
}

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

            }

    return words
}

/**
 * === 1. 将所有生词按照字母排序 ==============
 */
private fun solve_One() {

}