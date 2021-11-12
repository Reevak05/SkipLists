public class SkipList {

    SkipListNode head;
    int maxHeight;

    SkipList() {
        head = new SkipListNode(Integer.MIN_VALUE, 0);
        maxHeight = 0;
    }

    public void insert(int data, int height) {
        if (height > maxHeight) {
            maxHeight = height;
            head.changeHeight(height);
        }
        SkipListNode newNode = new SkipListNode(data, height);
        for (int i = 0; i <= newNode.height; i++) {
            SkipListNode currentNode = head;
            while (currentNode.nextNodes[i] != null) {
                currentNode = currentNode.nextNodes[i];
            }
            currentNode.nextNodes[i] = newNode;
        }
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
        System.out.println(" \n\n Skip List: ");
        for (int i = maxHeight; i >= 0; i--) {
            SkipListNode currentNode = head;
            System.out.print(currentNode.data);
            while (currentNode.nextNodes[i] != null) {
                currentNode = currentNode.nextNodes[i];
                System.out.print(" -> " + ((currentNode != null) ? currentNode.data : "*"));
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        SkipList skipList = new SkipList();
        System.out.println("\n––––––\nExpected:\n-2147483648");
        skipList.print();
        skipList.insert(3, 0);
        System.out.println("\n––––––\nExpected:\n-2147483648 -> 3");
        skipList.print();
        skipList.insert(7, 0);
        System.out.println("\n––––––\nExpected:\n-2147483648 -> 3 -> 57");
        skipList.print();
        skipList.insert(12, 1);
        System.out.println("\n––––––\nExpected:\n-2147483648 -> * -> * -> 12\n-2147483648 -> 3 -> 57 -> 12");
        skipList.print();
        skipList.insert(18, 2);
        System.out.println("\n––––––\nExpected:\n-2147483648 -> * -> * -> * -> 18\n-2147483648 -> * -> * -> 12 -> 18\n-2147483648 -> 3 -> 7 -> 12 -> 18");
        skipList.print();
    }
}
