import kotlin.test.*
import np.com.sudanchapagain.*

class RegexTest {
    private fun testMatch(regexNode: RegexNode, text: String, startIndex: Int, expected: Boolean) {
        val result = match(regexNode, text, startIndex)
        assert(result == expected) { "Failed on match: Expected $expected but got $result" }
    }

    private fun testMatchLength(regexNode: RegexNode, text: String, startIndex: Int, expectedLength: Int) {
        val length = matchLength(regexNode, text, startIndex)
        assert(length == expectedLength) { "Failed on matchLength: Expected $expectedLength but got $length" }
    }

    @Test
    fun testLiteralMatch() {
        val literalNode = Literal('a')
        testMatch(literalNode, "a", 0, true)
        testMatch(literalNode, "b", 0, false)
        testMatch(literalNode, "abc", 1, false)
    }

    @Test
    fun testCharacterClassMatch() {
        val classNode = CharacterClass(setOf('a', 'b', 'c'))
        testMatch(classNode, "a", 0, true)
        testMatch(classNode, "b", 0, true)
        testMatch(classNode, "c", 0, true)
        testMatch(classNode, "d", 0, false)
    }

    @Test
    fun testStarMatch() {
        val starNode = Star(Literal('a'))
        testMatch(starNode, "a", 0, true)
        testMatch(starNode, "aa", 0, true)
        testMatch(starNode, "aaa", 0, true)
        testMatch(starNode, "b", 0, false)
    }

    @Test
    fun testPlusMatch() {
        val plusNode = Plus(Literal('a'))
        testMatch(plusNode, "a", 0, true)
        testMatch(plusNode, "aa", 0, true)
        testMatch(plusNode, "", 0, false) // Plus should not match zero occurrences
        testMatch(plusNode, "b", 0, false)
    }

    @Test
    fun testQuestionMarkMatch() {
        val questionMarkNode = QuestionMark(Literal('a'))
        testMatch(questionMarkNode, "a", 0, true)
        testMatch(questionMarkNode, "", 0, true) // QuestionMark should match zero occurrences
        testMatch(questionMarkNode, "b", 0, false)
    }

    @Test
    fun testConcatMatch() {
        val concatNode = Concat(Literal('a'), Literal('b'))
        testMatch(concatNode, "ab", 0, true)
        testMatch(concatNode, "abc", 0, true)
        testMatch(concatNode, "ba", 0, false)
    }

    @Test
    fun testOrMatch() {
        val orNode = Or(Literal('a'), Literal('b'))
        testMatch(orNode, "a", 0, true)
        testMatch(orNode, "b", 0, true)
        testMatch(orNode, "c", 0, false)
    }

    @Test
    fun testGroupMatch() {
        val groupNode = Group(Concat(Literal('a'), Literal('b')))
        testMatch(groupNode, "ab", 0, true)
        testMatch(groupNode, "ba", 0, false)
    }

    @Test
    fun testGroupWithQuantifiers() {
        val groupNode = Group(Star(Literal('a')))
        testMatch(groupNode, "aa", 0, true)
        testMatch(groupNode, "aaa", 0, true)
        testMatch(groupNode, "b", 0, false)
    }

    @Test
    fun testOrWithConcat() {
        val orNode = Or(Concat(Literal('a'), Literal('b')), Concat(Literal('c'), Literal('d')))
        testMatch(orNode, "ab", 0, true)
        testMatch(orNode, "cd", 0, true)
        testMatch(orNode, "ac", 0, false)
    }

    @Test
    fun testMultipleQuantifiers() {
        val starPlusNode = Concat(Star(Literal('a')), Plus(Literal('b')))
        testMatch(starPlusNode, "ab", 0, true)
        testMatch(starPlusNode, "aaab", 0, true)
        testMatch(starPlusNode, "bb", 0, false)
    }

    @Test
    fun testMatchLength() {
        val literalNode = Literal('a')
        testMatchLength(literalNode, "a", 0, 1)
        testMatchLength(literalNode, "b", 0, 0)

        val starNode = Star(Literal('a'))
        testMatchLength(starNode, "aaa", 0, 3)
        testMatchLength(starNode, "", 0, 0)
    }

    @Test
    fun testComplexRegex() {
        val complexPattern = Concat(Literal('a'), Or(Literal('b'), Literal('c')))
        testMatch(complexPattern, "ab", 0, true)
        testMatch(complexPattern, "ac", 0, true)
        testMatch(complexPattern, "ad", 0, false)
    }
}
