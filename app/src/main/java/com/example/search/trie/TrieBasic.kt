package com.example.search.trie

class TrieBasic : Trie {
    override val root: Trie.Node = Trie.Node()

    override fun insert(word: String) {
        var currentNode = root
        for (char in word) {
            if (currentNode.children[char] == null) {
                currentNode.children[char] = Trie.Node()
            }
            currentNode = currentNode.children[char]!!
        }
        currentNode.word = word
    }

    override fun search(constraint: String): String? {
        var currentNode = root
        for (char in constraint) {
            if (currentNode.children[char] == null) return null
            currentNode = currentNode.children[char]!!
        }
        return currentNode.word
    }
}