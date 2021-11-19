public class SkipListNode {
    int data;
    int height;
    SkipListNode[] nextNodes;

    SkipListNode(int data, int height) {
        this.data = data;
        this.height = height;
        nextNodes = new SkipListNode[101];
    }

    public void changeHeight(int newHeight) {
        height = newHeight;
    }
}
