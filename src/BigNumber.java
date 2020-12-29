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

    private void removeZeroFromStart() {
        this._head = null;
    }

    private void removeZeroFromEnd() {
        IntNode ptr = this._tail;

        do{
            IntNode temp = this._head;
            while(temp.getNext() != ptr){
                temp = temp.getNext();
            }
            ptr = temp;
            ptr.setNext(null);
        }while(ptr.getValue() != 0);

    }

    private void subtractionBorrowing(IntNode node) {
        subtractionBorrowing(node, 0);
    }

    private void subtractionBorrowing(IntNode node, int count) {
        if (node.getValue() != 0) {
            node.setValue(node.getValue() - 1);
            return;
        }
        node.setValue(node.getValue() + 10);
        subtractionBorrowing(node.getNext(), ++count);
        node.setValue(node.getValue() - count);
    }

    private int whoIsBigger(BigNumber big1, BigNumber big2) {
        IntNode ptr1 = big1._tail, ptr2 = big2._tail;

        do {
            if (ptr1.getValue() > ptr2.getValue()) {
                return 1;
            } else if (ptr2.getValue() > ptr1.getValue()) {
                return 2;
            } else {
                IntNode temp1 = big1._head;
                IntNode temp2 = big2._head;

                while (temp1.getNext() != ptr1 && temp2.getNext() != ptr2) {
                    temp1 = temp1.getNext();
                    temp2 = temp2.getNext();
                }
                ptr1 = temp1;
                ptr2 = temp2;
            }
        } while (ptr1 != big1._head && ptr2 != big2._head);

        if (ptr1.getValue() > ptr2.getValue()) {
            return 1;
        } else if (ptr2.getValue() > ptr1.getValue()) {
            return 2;
        }
        return 0;
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
            if (whoIsBigger(this, other) == 1) {
                return 1;
            } else if (whoIsBigger(this, other) == 2) {
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

        IntNode thisPtr = thisCopy._head, otherPtr = otherCopy._head;

        BigNumber res = new BigNumber();
        res.removeZeroFromStart();

        if (other.length() > this.length()) {
            thisPtr = otherCopy._head;
            otherPtr = thisCopy._head;
        }
        while (thisPtr != null) {
            while (otherPtr != null) {
                if (thisPtr.getValue() + otherPtr.getValue() >= 10 && thisPtr.getNext() != null) {
                    res.addToNextFreeSlot((thisPtr.getValue() + otherPtr.getValue()) % 10);
                    thisPtr.getNext().setValue(thisPtr.getNext().getValue() + 1);
                } else {
                    res.addToNextFreeSlot(thisPtr.getValue() + otherPtr.getValue());
                }
                thisPtr = thisPtr.getNext();
                otherPtr = otherPtr.getNext();
            }
            if (thisPtr != null) {
                if (thisPtr.getValue() >= 10 && thisPtr.getNext() != null) {
                    res.addToNextFreeSlot(thisPtr.getValue() % 10);
                    thisPtr.getNext().setValue(thisPtr.getNext().getValue() + 1);
                } else {
                    res.addToNextFreeSlot(thisPtr.getValue());
                }
                thisPtr = thisPtr.getNext();
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
        BigNumber thisCopy = new BigNumber(this);
        BigNumber otherCopy = new BigNumber(other);

        IntNode thisPtr = thisCopy._head, otherPtr = otherCopy._head;

        BigNumber res = new BigNumber();


        if (other.compareTo(this) == 1) {
            thisPtr = otherCopy._head;
            otherPtr = thisCopy._head;
        } else if (other.compareTo(this) == 0) {
            return res;
        }

        res.removeZeroFromStart();

        while (thisPtr != null) {
            while (otherPtr != null) {
                if (thisPtr.getValue() - otherPtr.getValue() < 0) {
                    thisPtr.setValue(thisPtr.getValue() + 10);
                    ///Borrowing :)
                    IntNode temp = thisPtr.getNext();
                    subtractionBorrowing(temp);
                }
                res.addToNextFreeSlot(thisPtr.getValue() - otherPtr.getValue());
                thisPtr = thisPtr.getNext();
                otherPtr = otherPtr.getNext();
            }
            if (thisPtr != null) {
                res.addToNextFreeSlot(thisPtr.getValue());
                thisPtr = thisPtr.getNext();
            }
        }

        if(res._tail.getValue() == 0){
            ///REMOVE ZEROS FROM TAIL
            res.removeZeroFromEnd();
        }
        return res;
    }
}

