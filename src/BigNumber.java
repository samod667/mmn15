public class BigNumber {

    private IntNode _head;

    private boolean empty(){
        return length() == 0;
    }

    private int length(){
        IntNode ptr = _head;
        int res = 0;
        while(ptr != null){
            res++;
            ptr = ptr.getNext();
        }
        return res;
    }

    private void addToBigNumber(int num){
        IntNode newItem = new IntNode(num);
        if(empty()){
            addToHead(newItem);
        } else {
            IntNode ptr = _head;
            while(ptr.getNext() != null){
                ptr = ptr.getNext();
            }
            ptr.setNext(newItem);
        }
    }

    private void addToHead(IntNode node){
        if(_head == null){
            _head = node;
        } else {
            IntNode ptr = _head;
            _head = node;
            _head.setNext(ptr);
        }
    }

    public BigNumber(){
        this._head = new IntNode(0);
    }

    public BigNumber(long num){
        while(num > 0){
            int lastDigit = (int)(num % 10);
            addToBigNumber(lastDigit);
            num /= 10;
        }
    }

    public String toString(){
        String res = "";
        return toString(_head, res);
    }

    private String toString(IntNode node,String res) {
        if (node.getNext() == null) {
            return res += node.getValue();
        } else {
            toString(node.getNext(), res);
        }

        return res;

    }
}
