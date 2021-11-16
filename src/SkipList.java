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
        if (height > maxHeight) {
            maxHeight = height;
            head.changeHeight(height);
        }
        SkipListNode newNode = new SkipListNode(data, height);
        for (int i = 0; i <= newNode.height; i++) { // Start at the new node's height and decrease; for each level,
            SkipListNode currentNode = head;
            while (currentNode.nextNodes[i] != null && currentNode.nextNodes[i].data < data) { // find where the new node should go in order to maintain consecutive order
                currentNode = currentNode.nextNodes[i];
            }
            newNode.nextNodes[i] = currentNode.nextNodes[i]; // set the new node's pointer at that level to point to the next node
            currentNode.nextNodes[i] = newNode; // set the previous node's pointer at that level to point to the new node
        }
    }

    public boolean delete(int value) {
        boolean wasPresent = false;
        for (int i = maxHeight; i >= 0; i--) {
            for (SkipListNode current = head; current.nextNodes[i] != null; current = current.nextNodes[i]) {
                if (current.nextNodes[i].data == value) {
                    wasPresent = true;
                    current.nextNodes[i] = current.nextNodes[i].nextNodes[i];
                    break;
                }
                if (current.nextNodes[i].data > value) {
                    break;
                }
            }
        }
        return wasPresent;
    }

    @Deprecated
    public void oldPrint() {
        System.out.println(" \nSkipList: ");
        SkipListNode currentNode = head;
        System.out.print(currentNode.data + ", h: " + currentNode.height);
        while (currentNode.nextNodes[currentNode.height] != null) {
            currentNode = currentNode.nextNodes[currentNode.height];
            System.out.print(" -> " + currentNode.data + ", h: " + currentNode.height);
        }
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
    }
}