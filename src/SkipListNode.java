public class SkipListNode {
    int data;
    int height;
    SkipListNode[] nextNodes;

    SkipListNode(int data, int height) {
        this.data = data;
        this.height = height;
        nextNodes = new SkipListNode[100]; //TODO: remove limit of 100
    }

    public void changeHeight(int newHeight) {
        height = newHeight;
    }

    public void incrementHeight(int heightChange) {
        changeHeight(height+heightChange);
    }
}
