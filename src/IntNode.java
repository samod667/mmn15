public class IntNode {
    private int _value;
    private IntNode _next;

    public IntNode(int val){
        this._value = val;
        this._next = null;
    }

    public int getValue(){
        return this._value;
    }

    public void setValue(int value){
        this._value = value;
    }

    public IntNode getNext(){
        return this._next;
    }

    public void setNext(IntNode node){
        this._next = node;
    }
}
