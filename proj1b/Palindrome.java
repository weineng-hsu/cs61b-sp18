public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> d = new LinkedListDeque();
        /** Bellow works just fine.
         Deque<Character> d = new ArrayDeque<Character>(); */
        for (int i = 0; i < word.length(); i++) {
            d.addLast(word.charAt(i));
        }
        return d;
    }

    public boolean isPalindrome(String word) {
        String w = word;
        Deque<Character> wordD = wordToDeque(word);
        if (wordD.size() < 2) {
            return true;
        }
        if (first(wordD) != last(wordD)) {
            return false;
        }
        return isPalindrome(getRidOf(w));
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        String w = word;
        Deque<Character> wordD = wordToDeque(word);
        if (wordD.size() < 2) {
            return true;
        }
        if (!cc.equalChars(first(wordD), last(wordD))) {
            return false;
        }
        return isPalindrome(getRidOf(w), cc);
    }

    private char first(Deque<Character> D) {
        char first = D.removeFirst();
        return first;
    }

    private char last(Deque<Character> D) {
        char last = D.removeLast();
        return last;
    }

    private String getRidOf(String word) {
        return word.substring(1, word.length() - 1);
    }
}
