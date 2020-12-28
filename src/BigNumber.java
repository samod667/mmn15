public class BigNumber {

    private IntNode _head;
    private IntNode _tail;

    private boolean empty() {
        return this._head == null;
    }

    private void printList() {
        IntNode ptr = _head;

        while (ptr != null) {
            System.out.print(ptr.getValue());
            ptr = ptr.getNext();
        }
        System.out.println("");
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

    private int length() {
        IntNode ptr = _head;
        int res = 0;
        while (ptr != null) {
            res++;
            ptr = ptr.getNext();
        }
        return res;
    }

    private int numOfDigits(long num) {
        if (num == 0) {
            return 0;
        }
        return 1 + numOfDigits(num / 10);
    }

    private void addToNextFreeSlot(int num) {
        IntNode newItem = new IntNode(num);
        if (empty()) {
            this._head = newItem;
            this._tail = this._head;
        } else {
//            IntNode ptr = this._head;
//            while (ptr.getNext() != null) {
//                ptr = ptr.getNext();
//            }
//            ptr.setNext(newItem);

            _tail.setNext(newItem);
            _tail = _tail.getNext();
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
        this._tail = _head;
    }

    public BigNumber(long num) {
        while (num > 0) {
            addToNextFreeSlot((int) (num % 10));
            num /= 10;
        }
    }

    public BigNumber(BigNumber other) {
        IntNode ptr = other._head;
        while (ptr != null) {
            addToNextFreeSlot(ptr.getValue());
            ptr = ptr.getNext();
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
        BigNumber thisCopy = new BigNumber(this);
        BigNumber otherCopy = new BigNumber(other);

        IntNode ptr = thisCopy._head, ptr2 = otherCopy._head;

        BigNumber res = new BigNumber();
        res.removeZero();

        if (other.compareTo(this) == 1) {
            ptr = otherCopy._head;
            ptr2 = thisCopy._head;
        }
        while (ptr != null) {
            while (ptr2 != null) {
                if (ptr.getValue() + ptr2.getValue() >= 10 && ptr.getNext() != null) {
                    res.addToNextFreeSlot((ptr.getValue() + ptr2.getValue()) % 10);
                    ptr.getNext().setValue(ptr.getNext().getValue() + 1);
                } else {
                    res.addToNextFreeSlot(ptr.getValue() + ptr2.getValue());
                }
                ptr = ptr.getNext();
                ptr2 = ptr2.getNext();
            }
            if (ptr != null) {
                if (ptr.getValue() >= 10 && ptr.getNext() != null) {
                    res.addToNextFreeSlot(ptr.getValue() % 10);
                    ptr.getNext().setValue(ptr.getNext().getValue() + 1);
                } else {
                    res.addToNextFreeSlot(ptr.getValue());
                }
                ptr = ptr.getNext();
            }
        }
        return res;
    }

    /// O(n)
    public BigNumber addLong(long num) {
        BigNumber temp = new BigNumber(num);
        return this.addBigNumber(temp);
    }

    public BigNumber subtractBigNumber(BigNumber other) {
        IntNode ptr = this._head, ptr2 = other._head;
        BigNumber res = new BigNumber();
        res.removeZero();
        if (other.compareTo(this) == 1) {
            ptr = other._head;
            ptr2 = this._head;
        }
        return null;
    }
}

