package com.example.search.trie

interface Trie {

    val root: Node

    data class Node(
        var word: String? = null,
        val children: MutableMap<Char, Node> = mutableMapOf()
    )

    fun insert(word: String)

    fun search(constraint: String): String?
}