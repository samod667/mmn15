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

    private void trimZerosFromEnd() {
        IntNode headPtr = this._head;

        BigNumber newBig = new BigNumber();
        newBig.removeZeroFromStart();

        while (headPtr != null) {
            newBig.addToStart(new IntNode(headPtr.getValue()));
            headPtr = headPtr.getNext();
            this.removeNodeFromHead();
        }

        IntNode newBigHeadPtr = newBig._head;

        while (newBigHeadPtr.getValue() == 0) {
            newBig.removeNodeFromHead();
            newBigHeadPtr = newBig._head;
        }

        newBigHeadPtr = newBig._head;
        while (newBigHeadPtr != null) {
            this.addToStart(new IntNode(newBigHeadPtr.getValue()));
            newBigHeadPtr = newBigHeadPtr.getNext();
        }
    }

    private void removeNodeFromTail() {
        IntNode temp = this._head;

        if (!empty()) {
            if (this.length() > 1) {
                while (temp.getNext().getNext() != null && temp.getNext() != null) {
                    temp = temp.getNext();
                }
                this._tail = temp;
                temp.setNext(null);
            }
        }
    }

    private void removeNodeFromHead() {
        if (length() == 1) {
            _head = null;
        }
        if (this.length() >= 2) {
            IntNode temp = this._head.getNext();
            this._head = null;
            _head = temp;
        }
    }

    private void addToEnd(IntNode node) {
        IntNode temp = this._tail;

        temp.setNext(node);
        this._tail = node;
    }

    private void addToStart(IntNode node) {
        if (empty()) {
            this._head = node;
        } else {
            IntNode temp = this._head;
            this._head = node;
            node.setNext(temp);
        }
    }

    public IntNode getHead() {
        return this._head;
    }

    public IntNode getTail() {
        return this._tail;
    }

//    private void subtractionBorrowing(IntNode node, IntNode node2){
//            while(node2 != null){
//                if(node.getValue() - node2.getValue() < 0){
//                    node.setValue(node.getValue() + 10);
//                    subtractionBorrowing(node.getNext());
//                }
//                node = node.getNext();
//                node2 = node2.getNext();
//            }
//    }

    private void subtractionBorrowing(IntNode node, IntNode node2) {
        if (node2 == null) {
            return;
        }
        if (node.getValue() - node2.getValue() < 0) {
            node.setValue(node.getValue() + 10);
            subtractionBorrowing(node.getNext());
        }
        subtractionBorrowing(node.getNext(), node2.getNext());
    }

    private void subtractionBorrowing(IntNode node) {
        if (node == null) {
            return;
        }
        if (node.getValue() > 0) {
            node.setValue(node.getValue() - 1);
            return;
        }
        node.setValue(node.getValue() + 10);
        subtractionBorrowing(node.getNext());
        node.setValue(node.getValue() - 1);
    }

    private int isBigger(BigNumber other) {
        IntNode thisPtr = this._head, otherPtr = other._head;
        return isBigger(thisPtr, otherPtr);
    }

    private int isBigger(IntNode ptr1, IntNode ptr2) {
        if (ptr1 == null && ptr2 == null) {
            return 0;
        }
        if (ptr1 == null) {
            return -1;
        }

        if (ptr2 == null) {
            return 1;
        }

        int restBigger = isBigger(ptr1.getNext(), ptr2.getNext());

        if (restBigger == 1) {
            return 1;
        }

        if (restBigger == -1) {
            return -1;
        }

        if (ptr1.getValue() > ptr2.getValue()) {
            return 1;
        }
        if (ptr2.getValue() > ptr1.getValue()) {
            return -1;
        }
        return 0;
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

    private void addToNextFreeSlot(int num) {
        IntNode newItem = new IntNode(num);
        if (empty()) {
            this._head = newItem;
            this._tail = this._head;
        } else {
            _tail.setNext(newItem);
            _tail = _tail.getNext();
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

    ///O(n)
    public int compareTo(BigNumber other) {
        if (this.isBigger(other) == 1) {
            return 1;
        }
        if (this.isBigger(other) == -1) {
            return -1;
        }
        return 0;
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
                int additionResult = thisPtr.getValue() + otherPtr.getValue();
                if (additionResult >= 10 && thisPtr.getNext() != null) {
                    res.addToNextFreeSlot(additionResult % 10);
                    thisPtr.getNext().setValue(thisPtr.getNext().getValue() + 1);
                } else {
                    res.addToNextFreeSlot(additionResult);
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

    ///O(n)
    public BigNumber subtractBigNumber(BigNumber other) {
        BigNumber thisCopy = new BigNumber(this);
        BigNumber otherCopy = new BigNumber(other);

        IntNode thisPtr = thisCopy._head, otherPtr = otherCopy._head;

        BigNumber res = new BigNumber();

        ///O(n)
        if (other.compareTo(this) == 1) {
            thisPtr = otherCopy._head;
            otherPtr = thisCopy._head;
        } else if (other.compareTo(this) == 0) {
            return res;
        }
        res.removeZeroFromStart();

        subtractionBorrowing(thisPtr, otherPtr);

        while (thisPtr != null) {
            while (otherPtr != null) {
                int subtractionRes = thisPtr.getValue() - otherPtr.getValue();
                res.addToNextFreeSlot(subtractionRes);

                thisPtr = thisPtr.getNext();
                otherPtr = otherPtr.getNext();
            }
            if (thisPtr != null) {
                res.addToNextFreeSlot(thisPtr.getValue());
                thisPtr = thisPtr.getNext();
            }
        }

        ///TRIM ZEROS IF NEEDED
        if (res._tail.getValue() == 0) {
            res.trimZerosFromEnd();
        }
        return res;
    }

    ///O(n2)
    public BigNumber multBigNumber(BigNumber other) {
        BigNumber thisCopy = new BigNumber(this);
        BigNumber otherCopy = new BigNumber(other);

        IntNode thisPtr = thisCopy._head, otherPtr = otherCopy._head;

        if (otherCopy.length() > thisCopy.length()) {
            thisPtr = otherCopy._head;
            otherPtr = thisCopy._head;
        }

        BigNumber result = new BigNumber();

        ///Outer while loop
        while (otherPtr != null) {
            ///Declaring a new addition to sum value
            BigNumber additionNumber = new BigNumber();
            additionNumber.removeZeroFromStart();

            ///Adding zeros to the addition variable if needed
            IntNode temp = otherCopy._head;
            if (otherPtr != temp) {
                while (temp != otherPtr) {
                    additionNumber.addToNextFreeSlot(0);
                    temp = temp.getNext();
                }
            }
            int firstD = 0;
            boolean borrowing = false;
            ///Inner while loop
            while (thisPtr != null) {
                int ptrResult = otherPtr.getValue() * thisPtr.getValue();
                if(ptrResult > 9 && thisPtr.getNext() != null){
                    borrowing = true;
                }
                if (borrowing) {
                    additionNumber.addToNextFreeSlot(ptrResult % 10 + firstD);
                    firstD = ptrResult / 10;
                } else {
                    additionNumber.addToNextFreeSlot(ptrResult + firstD);
                    firstD = 0;
                }
                thisPtr = thisPtr.getNext();
                borrowing = false;
            }

            result = result.addBigNumber(additionNumber);
            System.out.println("number that was added to sum --> " + additionNumber);
            System.out.println("sum is ---> " + result);
            thisPtr = thisCopy._head;
            otherPtr = otherPtr.getNext();
        }
        return result;
    }

}

