public class BigNumber {

    private IntNode _head;

    private boolean empty() {
        return length() == 0;
    }

    private void printList() {
        IntNode ptr = _head;

        while (ptr != null) {
            System.out.print(ptr.getValue());
            ptr = ptr.getNext();
        }
    }

    private void removeZero() {
        this._head = null;
    }

    private int sumOfDigits() {
        IntNode ptr = _head;

        int res = 0;

        while (ptr != null) {
            res += ptr.getValue();
            ptr = ptr.getNext();
        }
        return res;
    }

    private IntNode nextNode(IntNode node) {
        return node.getNext();
    }

    private int length() {
        IntNode ptr = _head;
        int res = 0;
        while (ptr != null) {
            res++;
            ptr = ptr.getNext();
        }
        return res;
    }

    private void addToBigNumber(int num) {
        IntNode newItem = new IntNode(num);
        if (empty()) {
            this._head = new IntNode(num);
        } else {
            IntNode ptr = _head;
            while (ptr.getNext() != null) {
                ptr = ptr.getNext();
            }
            ptr.setNext(newItem);
        }
    }

    private void addToHead(IntNode node) {
        if (_head == null) {
            _head = node;
        } else {
            IntNode ptr = _head;
            _head = node;
            _head.setNext(ptr);
        }
    }

    public BigNumber() {
        this._head = new IntNode(0);
    }

    public BigNumber(long num) {
        while (num > 0) {
            int lastDigit = (int) (num % 10);
            addToBigNumber(lastDigit);
            num /= 10;
        }
    }

    public BigNumber(BigNumber other){
        this._head = new IntNode(other._head.getValue());

        IntNode ptr = other._head;
        IntNode ptr2 = this._head;

        while(ptr != null){
            ptr2 = new IntNode(ptr.getValue());
            ptr = ptr.getNext();
            ptr2 = ptr2.getNext();
        }
    }

    public String toString() {
        return toString(_head);
    }

    private String toString(IntNode node) {
        return node == null ? "" : toString(node.getNext()) + node.getValue();
    }

    public int compareTo(BigNumber other) {
        if (this.length() > other.length()) {
            return 1;
        } else if (this.length() < other.length()) {
            return -1;
        } else {
            if (this.sumOfDigits() > other.sumOfDigits()) {
                return 1;
            } else if (this.sumOfDigits() < other.sumOfDigits()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /// O(n)
    public BigNumber addBigNumber(BigNumber other) {
        IntNode ptr = this._head, ptr2 = other._head;
        BigNumber res = new BigNumber();
        res.removeZero();
        if (other.length() > this.length()) {
            ptr = other._head;
            ptr2 = this._head;
        }
        while (ptr != null) {
            while (ptr2 != null) {
                if (ptr.getValue() + ptr2.getValue() >= 10 && ptr.getNext() != null) {
                    res.addToBigNumber((ptr.getValue() + ptr2.getValue()) % 10);
                    ptr.getNext().setValue(ptr.getNext().getValue() + 1);
                } else {
                    res.addToBigNumber(ptr.getValue() + ptr2.getValue());
                }
                ptr = ptr.getNext();
                ptr2 = ptr2.getNext();
            }
            if (ptr != null) {
                if (ptr.getValue() >= 10 && ptr.getNext() != null) {
                    res.addToBigNumber(ptr.getValue() % 10);
                    ptr.getNext().setValue(ptr.getNext().getValue() + 1);
                } else {
                    res.addToBigNumber(ptr.getValue());
                }
                ptr = ptr.getNext();
            }
        }
        return res;
    }
}

