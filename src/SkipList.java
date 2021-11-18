public class SkipList {

    // Instance variables
    SkipListNode head; // the first node
    int maxHeight; // the height of the tallest node

    SkipList() {
        // Create head with the minimum value possible, so it always stays at the beginning of the list
        head = new SkipListNode(Integer.MIN_VALUE, 0);
        maxHeight = 0; // All height values are zero-indexed, so they correspond with the nodes' array indexes
    }

    public void insert(int data, int height) {
        if (height > maxHeight) { // If the new node is taller than the head node, increase the height of head (and change maxHeight accordingly)
            maxHeight = height;
            head.changeHeight(height);
        }

        int currentLevel = maxHeight;
        SkipListNode currentNode = head;
        SkipListNode newNode = new SkipListNode(data, height); // the new node to be inserted
        SkipListNode[] previousNodesForEachLevel = new SkipListNode[maxHeight + 1]; // the last visited nodes for each level; their pointers will be set to point to the new node

        while (true) {
            previousNodesForEachLevel[currentLevel] = currentNode; // Save the current node in its level's index in the list of last visited nodes

            if (currentNode.nextNodes[currentLevel] == null || currentNode.nextNodes[currentLevel].data > data) { // If there is no node after the current node or if the node after the current node has a value greater than the new node, insert the node if on the lowest level or, if not, move down
                if (currentLevel == 0) { // If on the lowest level, insert the new node
                    for (int i = 0; i <= maxHeight; i++) { // Point the appropriate pointer of each the last visited nodes for each level to the new node
                        SkipListNode nextNode = previousNodesForEachLevel[i].nextNodes[i];
                        previousNodesForEachLevel[i].nextNodes[i] = newNode;
                        newNode.nextNodes[i] = nextNode;
                    }
                    break; // Stop because the new node has been inserted
                } else { // If not on the lowest level, go down a level
                    currentLevel--;
                }
            } else { // If the value of the node to right is less than the new node's value, move to the right
                currentNode = currentNode.nextNodes[currentLevel];
            }
        }
    }

    public boolean search(int data) {
        int currentLevel = maxHeight;
        SkipListNode currentNode = head;

        while (true) {
            if (currentNode.nextNodes[currentLevel] == null || currentNode.nextNodes[currentLevel].data > data) { // If there is no node after the current node or if the node after the current node has a value greater than the new node, insert the node if on the lowest level or, if not, move down
                if (currentNode.data == data) {
                    return true;
                } else {
                    if (currentLevel == 0) {
                        return false;
                    } else { // If not on the lowest level, go down a level
                        currentLevel--;
                    }
                }
            } else { // If the value of the node to right is less than the new node's value, move to the right
                currentNode = currentNode.nextNodes[currentLevel];
            }
        }
    }

    public boolean delete(int value) {
        int pointer_size = 0;

        int currentLevel = maxHeight;//start from the top of the SkipList
        SkipListNode currentNode = head;
        SkipListNode[] formerNodeset = new SkipListNode[maxHeight + 1];
        int[] formerNodeset_level = new int[maxHeight + 1];
        if (!search(value)) {
            return false;
        } else {
            while (currentLevel > -1) {
                if (currentNode.nextNodes[currentLevel].data == value) {
                    formerNodeset[pointer_size] = currentNode;
                    formerNodeset_level[pointer_size] = currentLevel;
                    currentLevel--;
                    pointer_size++;
                } else if (currentNode.data < value && currentNode.nextNodes[currentLevel].data > value) {
                    currentLevel--;
                } else if (currentNode.nextNodes[currentLevel].data != value) {
                    currentNode = currentNode.nextNodes[currentLevel];
                }
            }
        }
        for (int i = 0; i < pointer_size; i++) {
            formerNodeset[i].nextNodes[formerNodeset_level[i]] = formerNodeset[i].nextNodes[formerNodeset_level[i]].nextNodes[formerNodeset_level[i]];
        }
        return true;
    }

    public void print() {
        System.out.println("\n\nSkip List: ");
        for (int i = maxHeight; i >= 0; i--) { // Start at the highest level and decrease; for each level,
            SkipListNode currentNode = head;
            System.out.print("head"); // print head
            while (currentNode.nextNodes[i] != null) { // iterate through the following nodes and print the value of each one
                currentNode = currentNode.nextNodes[i];
                System.out.print(" -> " + ((currentNode != null) ? currentNode.data : "*"));
            }
            System.out.println(); // print a new line for the next level
        }
    }

    public static void main(String[] args) {
        SkipList skipList = new SkipList();
        System.out.println("\n––––––\nExpected:\nhead");
        skipList.print();
        skipList.insert(4, 0);
        System.out.println("\n––––––\nExpected:\nhead -> 4");
        skipList.print();
        skipList.insert(2, 0);
        System.out.println("\n––––––\nExpected:\nhead -> 2 -> 4");
        skipList.print();
        skipList.insert(12, 1);
        System.out.println("\n––––––\nExpected:\nhead -> * -> * -> 12\nhead -> 2 -> 4 -> 12");
        skipList.print();
        skipList.insert(9, 2);
        System.out.println("\n––––––\nExpected:\nhead -> * -> * -> 9 -> *\nhead -> * -> * -> 9 -> 12\nhead -> 2 -> 4 -> 9 -> 12");
        skipList.print();
        skipList.delete(9);
        System.out.println("\n––––––\nExpected:\nhead -> * -> * -> *\nhead -> * -> * -> 12\nhead -> 2 -> 4 -> 12");
        skipList.print();
        skipList.delete(3);
        System.out.println("\n––––––\nExpected:\nhead -> * -> * -> *\nhead -> * -> * -> 12\nhead -> 2 -> 4 -> 12");
        skipList.print();
        System.out.println("\n––––––\nExpected:\ntrue\nfalse\ntrue\nfalse\ntrue\nfalse\n\n");
        System.out.println(skipList.search(12));
        System.out.println(skipList.search(0));
        System.out.println(skipList.search(4));
        System.out.println(skipList.search(21));
        System.out.println(skipList.search(2));
        System.out.println(skipList.search(-300));
    }
}