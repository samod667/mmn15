/**
 * A one way linked list that will represent big numbers which cannot be contained within a long variable.
 * Each node in the BigNumber list will represent a single digit from the number.
 * The linked list will be represented backwards - singles will be stored first, tens after etc...
 * You will be able to do simple calculations such as add, subtract and multiply from one BigNumber to the other.
 * BigNumber list is using the IntNode class!
 *
 * @author Dor Samoha
 * ID: 312503287
 * @version Jan 4th
 */
public class BigNumber {

    //Declaring variables which will hold an object that will point to the start and the end of the list
    private IntNode _head;

    /**
     * Initiates a BigNumber object containing one node with default value 0
     */
    public BigNumber() {
        this._head = new IntNode(0);
    }

 /**
     * Instantiates a BigNumber objects with the values of the parameter num
     * @param num = the number which will be represented as a new BigNumber list
     */
    public BigNumber(long num) {
        // A loop which will take the last digit of the number, use a helper function to add it to the list and remove last digit from param num
        while (num > 0) {
            addToNextFreeSlot((int) (num % 10));
            num /= 10;
        }
    }

    /**
     * Instantiates a new BigNumber with the identical values and nodes from Other BigNumber
     * @param other = the other BigNumber which values will be copied from for the new BigNumber
     */
    public BigNumber(BigNumber other) {
        // ptr variable will point to the Other BigNumber first value
        IntNode ptr = other._head;
        //While loop will loop through the Other BigNumber, copy its node value,
        // and will create new node for the new BigNumber with the same values as the Other BigNumber (using a helper function)
        while (ptr != null) {
            addToNextFreeSlot(ptr.getValue());
            ptr = ptr.getNext();
        }
    }

    /**
     * To string method is a recursive to string which will print the number saved in BigNumber
     * @return a string representing the number saved in the list
     */
    public String toString() {
        return toString(_head);
    }

    //Private recursive helper method which will run through the list and print the values backwards
    private String toString(IntNode node) {
        return node == null ? "" : toString(node.getNext()) + node.getValue();
    }

    /**
     * Compare two BigNumber objects and check which is the bigger one. This function is recursive, and will use the private helper function isBigger
     *
     * Runtime Complexity:
     * O(n)
     *
     * Space Complexity:
     * O(1)
     *
     * @param other the Other BigNumber to check its size against
     * @return 1 if the this BigNumber is bigger than Other, -1 if Other BigNumber is bigger, or 0 if they are equal
     */
    public int compareTo(BigNumber other) {
        if (this.isBigger(other) == 1) {
            return 1;
        }
        if (this.isBigger(other) == -1) {
            return -1;
        }
        return 0;
    }

    /**
     * A function that will do an addition of two BigNumber objects and will return a new BigNumber object with the result.
     * This function is using a private helper function addNumber!
     *
     * Runtime Complexity:
     * O(1) + O(n) + O(n) = O(n)
     *
     * Space Complexity:
     * O(1)
     *
     * @param other the Other BigNumber to add to this BigNumber
     * @return a new BigNumber object which will contain the result of the addition of the two BigNumber objects
     */
    public BigNumber addBigNumber(BigNumber other) {
        //Creating a copy objects of this and other
        BigNumber thisCopy = new BigNumber(this);
        BigNumber otherCopy = new BigNumber(other);

        IntNode thisPtr = thisCopy._head, otherPtr = otherCopy._head;

        //Declaring the new BigNumber which will hold the result and will eventually be returned
        BigNumber res = new BigNumber();
        //Removing the initial 0 value which was created with the constructor --> O(1)
        res.removeZeroFromStart();

        //Checking which BigNumber has more digits (to avoid nullExceptions errors), a private method -- > O(n)
        if (other.length() > this.length()) {
            thisPtr = otherCopy._head;
            otherPtr = thisCopy._head;
        }

        //Using the private method addNumber which will handle the calculations --> O(n)
        addNumber(thisPtr, otherPtr, res);

        //Return the res object which will hold the result
        return res;
    }

    /**
     * Get the addition of a BigNumber with a other long num
     *
     * Runtime Complexity:
     * O(n)
     *
     * Space Complexity:
     * O(1)
     *
     * @param num the other long number to add for the BigNumber object
     * @return A new BigNumber with the result of the addition of the long num and the BigNumber object
     */
    public BigNumber addLong(long num) {
        //Using constructor to create another BigNumber object from the long num
        BigNumber temp = new BigNumber(num);

        //Using the addBigNumber method which calculates the addition of two BigNumber objects and returns a new BigNumber with the result --> O(n)
        return this.addBigNumber(temp);
    }

    /**
     * A subtraction calculation over two BigNumber objects
     *
     * Runtime Complexity:
     * O(n) + O(1) + O(n) + O(n) + O(n) = O(n)
     *
     * Space Complexity:
     * O(1)
     *
     * @param other The other BigNumber to subtract
     * @return a new BigNumber object with the value of the subtraction of this BigNumber and other BigNumber
     */
    public BigNumber subtractBigNumber(BigNumber other) {
        //Creating a copy of this and other objects
        BigNumber thisCopy = new BigNumber(this);
        BigNumber otherCopy = new BigNumber(other);

        IntNode thisPtr = thisCopy._head, otherPtr = otherCopy._head;

        //Initiating a new BigNumber object which will hold the result of subtraction
        BigNumber res = new BigNumber();

        //Using the compareTo method to check which object is bigger (to avoid result that are smaller than 0) --> O(n)
        //If other number is bigger --> switch variables
        if (other.compareTo(this) == 1) {
            thisPtr = otherCopy._head;
            otherPtr = thisCopy._head;
        } else if (other.compareTo(this) == 0) {
            //If numbers are equal - return the new result object which its value is 0 (because of the empty constructor)
            return res;
        }
        //Remove 0 from the res variable --> O(1)
        res.removeZeroFromStart();

        //This is a private helper function that will change the values stored in the nodes by in case borrowing is needed --> O(n)
        subtractionBorrowing(thisPtr, otherPtr);

        //This is a private helper method that will iterate through the bigger number one time and will only subtract the values of the two BigNumber's --> O(n)
        subtractNumber(thisPtr, otherPtr, res);

        ///In case there are 0 that need to be trimmed this private recursive helper method will take care of it --> O(n)
        res.trimZerosFromEnd();
        //Return the new result object
        return res;
    }

    /**
     * A multiplication method on two BigNumber objects
     *
     * Runtime Complexity:
     * O(n) + O(n^2) = O(n^2)
     *
     * Space Complexity:
     * O(n)
     *
     * @param other the other BigNumber to multiply with
     * @return a new BigNumber object which will hold the result for the multiplication calculation of the two BigNumbers
     */
    public BigNumber multBigNumber(BigNumber other) {
        //Creating copy of this and other BigNumber
        BigNumber thisCopy = new BigNumber(this);
        BigNumber otherCopy = new BigNumber(other);

        //Checking which BigNumber is bigger to avoid nullPointerExceptions --> O(n)
        if (otherCopy.length() > thisCopy.length()) {
            thisCopy = other;
            otherCopy = this;
        }

        IntNode thisPtr = thisCopy._head, otherPtr = otherCopy._head;

        ///Initialize a new result object to 0
        BigNumber result = new BigNumber();

        //Return the result object after calculations -- Runtime: O(n^2) --> Space: O(n)
        return multiHelper(thisPtr, otherPtr, otherCopy, thisCopy, result);
    }

    ///// PRIVATE HELPER METHODS //////////////////////////

    //Check if BigNumber is empty --> O(1)
    private boolean empty() {
        return this._head == null;
    }

    //Will remove the initial 0 value from the empty constructor --> O(1)
    private void removeZeroFromStart() {
        this._head = null;
    }

    //A function that will trim any 0's from the end of a BigNumber --> O(n)
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

    //Remove Node from head --> O(1)
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

    //Add node to start --> O(1)
    private void addToStart(IntNode node) {
        if (empty()) {
            this._head = node;
        } else {
            IntNode temp = this._head;
            this._head = node;
            node.setNext(temp);
        }
    }

    //Iterate recursively through the subtraction of two numbers and see if there is a need of borrowing --> O(n)
    private void subtractionBorrowing(IntNode node, IntNode node2) {
        if (node2 == null) {
            return;
        }
        if (node.getValue() - node2.getValue() < 0) {
            node.setValue(node.getValue() + 10);
            //If borrowing is needed change the values with this helper function
            subtractionBorrowing(node.getNext());
        }
        subtractionBorrowing(node.getNext(), node2.getNext());
    }

    //A overloading subtraction method that will handle and change the values of the BigNumber if borrowing is needed
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

    //A function that will Iterate once through the bigger number from the subtraction method and will subtract the values --> O(n)
    private void subtractNumber(IntNode thisPtr, IntNode otherPtr, BigNumber res) {
        while (thisPtr != null) {
            while (otherPtr != null) {
                res.addToNextFreeSlot((thisPtr.getValue() - otherPtr.getValue()));
                thisPtr = thisPtr.getNext();
                otherPtr = otherPtr.getNext();
            }
            if (thisPtr != null) {
                res.addToNextFreeSlot(thisPtr.getValue());
                thisPtr = thisPtr.getNext();
            }
        }
    }

    //A function that will do the multiplication calculations --> Time: O(n^2) -->Space: O(n)
    private BigNumber multiHelper(IntNode thisPtr, IntNode otherPtr, BigNumber otherCopy, BigNumber thisCopy, BigNumber result) {
        ///Outer while loop
        while (otherPtr != null) {
            ///Declaring a new addition to sum object
            BigNumber additionNumber = new BigNumber();
            //O(1)
            additionNumber.removeZeroFromStart();

            //Add zeros if needed
            if (otherPtr != otherCopy._head) {
                IntNode temp = otherCopy._head;

                while (temp != otherPtr) {
                    //O(1)
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

            result = result.addBigNumber(additionNumber); //O(n)
            thisPtr = thisCopy._head;
            otherPtr = otherPtr.getNext();
        }
        return result;
    }

    //Helper method to compareTo public method
    private int isBigger(BigNumber other) {
        IntNode thisPtr = this._head, otherPtr = other._head;
        return isBigger(thisPtr, otherPtr);
    }
    //isBigger helper method - checking recursively which values are bigger from this & other BigNumber --> O(n)
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

    //Returns the length of the list --> O(n) (One iteration over the list)
    private int length() {
        IntNode ptr = _head;
        int res = 0;
        while (ptr != null) {
            res++;
            ptr = ptr.getNext();
        }
        return res;
    }

    //Creates a new node with the value num and adds value num to the next free slot available (the end) in the list
    //If list is empty than add to head --> O(1)
    private void addToNextFreeSlot(int num) {
        IntNode newItem = new IntNode(num);
        if (empty()) {
            this._head = newItem;

        } else {
            IntNode tmp = this._head;
            while(tmp.getNext() != null){
                tmp = tmp.getNext();
            }
            tmp.setNext(new IntNode(num));
        }
    }

    //take a numb and add its digits separately to a BigNumber
    private void disassembleNumber(int num) {
        if (num > 0) {
            while (num > 0) {
                addToNextFreeSlot(num % 10);
                num /= 10;
            }
        }
    }

    //A private method to handle BigNumber addition calculations --> O(n) (One iteration over the BigNumber)
    private void addNumber(IntNode thisPtr, IntNode otherPtr, BigNumber res) {
        //While loop on the first BigNumber
        while (thisPtr != null) {
            //While loop on the second BigNumber
            while (otherPtr != null) {
                //Add the two digits and addition to the next item in list if needed
                if ((thisPtr.getValue() + otherPtr.getValue()) >= 10 && thisPtr.getNext() != null) {
                    res.addToNextFreeSlot((thisPtr.getValue() + otherPtr.getValue()) % 10);
                    thisPtr.getNext().setValue(thisPtr.getNext().getValue() + 1);
                } else {
                    res.addToNextFreeSlot((thisPtr.getValue() + otherPtr.getValue()));
                }
                //Continue with the next values in the list
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

    //If number is bigger than 9
    private boolean biggerThan9(int num) {
        return num > 9;
    }

}

