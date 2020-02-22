import java.util.*;

class Huffman {
    static String convert(String text) {
        String[] words = text.split(" ");

        List<Node> priorityWords = new ArrayList<>();
        filtering(priorityWords, words);

        Queue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getCount));
        queue.addAll(priorityWords);
        createTree(queue);

        Map<String, String> codeTable = new HashMap<>();
        if (queue.size() == 1) {
            createCodeTable(queue.poll(), new StringBuilder(), codeTable);
        }

        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            builder.append(codeTable.get(word)).append(' ');
        }
        return builder.toString();
    }

    private static void createCodeTable(Node node, StringBuilder builder, Map<String, String> codeTable) {
        if (node.word != null) {
            codeTable.put(node.word, builder.toString());
            return;
        }
        createCodeTable(node.leftChild, builder.append('0'), codeTable);
        builder.deleteCharAt(builder.length() - 1);
        createCodeTable(node.rightChild, builder.append('1'), codeTable);
        builder.deleteCharAt(builder.length() - 1);
    }

    private static void createTree(Queue<Node> queue) {
        while (queue.size() > 1) {
            Node node1 = queue.poll();
            Node node2 = queue.poll();
            if (node1 != null && node2 != null) {
                Node node = new Node(node1, node2);
                queue.add(node);
            } else {
                throw new NullPointerException("Node1 or Node2 are null!");
            }
        }
    }

    private static void filtering(List<Node> priorityWords, String[] words) {
        for (String s : new TreeSet<>(Arrays.asList(words))) {
            priorityWords.add(new Node(s));
        }

        for (Node priorityWord : priorityWords) {
            for (String word : words) {
                if (priorityWord.getWord().equals(word)) {
                    priorityWord.addToCount();
                }
            }
        }
    }


    private static class Node {
        private String word;
        private int count;
        private Node leftChild;
        private Node rightChild;

        private Node(String word, int count) {
            this.word = word;
            this.count = count;
            this.leftChild = null;
            this.rightChild = null;
        }

        private Node(String word) {
            this(word, 0);
        }

        private Node(Node o1, Node o2) {
            this(null, o1.count + o2.count);
            leftChild = o1;
            rightChild = o2;
        }

        private void addToCount() {
            count++;
        }

        private String getWord() {
            return word;
        }

        private int getCount() {
            return count;
        }
    }
}
