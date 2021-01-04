public class BigNumber {

    private IntNode _head;
    private IntNode _tail;

    private boolean empty() {
        return this._head == null;
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

    private void addToStart(IntNode node) {
        if (empty()) {
            this._head = node;
        } else {
            IntNode temp = this._head;
            this._head = node;
            node.setNext(temp);
        }
    }

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

    private BigNumber multiHelper(IntNode thisPtr, IntNode otherPtr, BigNumber otherCopy, BigNumber thisCopy, BigNumber result) {
        ///Outer while loop
        while (otherPtr != null) {
            ///Declaring a new addition to sum value
            BigNumber additionNumber = new BigNumber();
            additionNumber.removeZeroFromStart();

            ///Adding zeros to the addition variable if needed
            if (otherPtr != otherCopy._head) {
                IntNode temp = otherCopy._head;

                while (temp != otherPtr) {
                    additionNumber.addToNextFreeSlot(0);
                    temp = temp.getNext();
                }
            }
            int firstD = 0;
            boolean borrowing = false;

            ///Inner while loop
            while (thisPtr != null) {
                int ptrResult = (otherPtr.getValue() * thisPtr.getValue()) + firstD;
                if (biggerThan9(ptrResult)) {
                    borrowing = true;
                }

                if (borrowing) {
                    if (thisPtr.getNext() == null) {
                        additionNumber.disassembleNumber(ptrResult);
                    } else {
                        additionNumber.addToNextFreeSlot(ptrResult % 10);
                        firstD = ptrResult / 10;
                    }
                } else {
                    additionNumber.disassembleNumber(ptrResult);
                    firstD = 0;
                }
                thisPtr = thisPtr.getNext();
                borrowing = false;
            }

            result = result.addBigNumber(additionNumber);
            thisPtr = thisCopy._head;
            otherPtr = otherPtr.getNext();
        }
        return result;
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

    private void disassembleNumber(int num) {
        if (num > 0) {
            while (num > 0) {
                addToNextFreeSlot(num % 10);
                num /= 10;
            }
        }
    }

    private void addNumber(IntNode thisPtr, IntNode otherPtr, BigNumber res) {
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
    }

    private void subtractNumber(IntNode thisPtr, IntNode otherPtr, BigNumber res) {
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
    }

    private boolean biggerThan9(int num) {
        return num > 9;
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

        addNumber(thisPtr, otherPtr, res);

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

        subtractNumber(thisPtr, otherPtr, res);

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

        if (otherCopy.length() > thisCopy.length()) {
            thisCopy = other;
            otherCopy = this;
        }

        IntNode thisPtr = thisCopy._head, otherPtr = otherCopy._head;

        ///Initialize a new result object to 0
        BigNumber result = new BigNumber();

        return multiHelper(thisPtr, otherPtr, otherCopy, thisCopy, result);
    }

}

