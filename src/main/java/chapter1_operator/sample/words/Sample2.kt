package chapter1_operator.sample.words

import java.io.File
import java.util.regex.Pattern

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
    getWords().forEach {
        println(it)
    }
}

private fun getWords(): List<Word?> {
    val filePath = Word::class.java.getResource(filePath).path
    val words = mutableListOf<Word?>()
    val file = File(filePath)
    if (!file.exists()) {
        return words
    }

    // 过滤掉 空行 与 #开头的
    file.readLines(charset = Charsets.UTF_8).filterNot {
        it.trim().isEmpty() || it.startsWith("#")
    }.forEach {
                words += lineToWord(it)
            }

    return words
}

private inline fun lineToWord(line: String): Word? {
    // 利用rg来切割字符串  prison	['prɪz(ə)n]	监狱
    val regex = """(?<g1>([\s\w$])+)\s+((?<g2>\[.+?\])\s)?+(?<g3>.+)"""
    val p = Pattern.compile(regex)
    val matcher = p.matcher(line)
    while (matcher.find()) {
        return Word(matcher.group("g1").trim(), matcher.group("g2"), matcher.group("g3"))
    }
    return null
}

/**
 * === 1. 将所有生词按照字母排序 ==============
 */
private fun solve_One() {

}