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
     * Initiates a BigNumber object containing one node with the the value 0.
     * Runtime Complexity: O(1)
     * Space Complexity: O(1)
     */
    public BigNumber() {
        this._head = new IntNode(0);
    }

    /**
     * Initiates a BigNumber object from a long type number. List head will be the singles.
     *
     * Runtime Complexity: O(log n) --> Dividing the number by 10 for each iteration
     * Space Complexity: O(n) --> Declaring a new node for every iteration within the while loop
     * @param num = the long number to be converted into a BigNumber list
     */
    public BigNumber(long num) {
        this._head = new IntNode((int) (num % 10));
        num /= 10;

        IntNode ptr = this._head;

        while (num > 0) {
            IntNode node = new IntNode((int) (num % 10));
            ptr.setNext(node);
            ptr = ptr.getNext();
            num /= 10;
        }
    }

    /**
     * Initiates a BigNumber object from an other  BigNumber. The constructor will take the values from the other BigNumber and will create
     * new Nodes with the same value and with the same order as the other BigNumber.
     *
     * Runtime Complexity: O(n) --> Iterating once over the other BigNumber
     * Space Complexity: O(n) --> Creating a new node for each iteration
     * @param other = the other BigNumber to copy the list values from
     */
    public BigNumber(BigNumber other) {
        IntNode otherPtr = other._head;

        this._head = new IntNode(otherPtr.getValue());

        IntNode thisPtr = _head;
        otherPtr = otherPtr.getNext();

        while (otherPtr != null) {
            thisPtr.setNext(new IntNode(otherPtr.getValue()));
            thisPtr = thisPtr.getNext();
            otherPtr = otherPtr.getNext();
        }
    }

    /**
     * To string method is a recursive to string which will print the number saved in BigNumber
     *
     * Runtime Complexity: O(n)
     * Space Complexity: O(1)
     * @return a string representing the number saved in the list (in the correct order)
     */
    public String toString() {
        return toString(_head);
    }

    //Private recursive helper method which will run through the list and print the values backwards
    private String toString(IntNode node) {
        return node == null ? "" : toString(node.getNext()) + node.getValue();
    }

    /**
     * Compare two BigNumber objects and check which is the bigger one. This function is recursive, and will use the private helper function isBigger.
     *
     * Runtime Complexity: O(n)
     * Space Complexity: O(1)
     * @param other the Other BigNumber to check its size against
     * @return 1 if the this BigNumber is bigger than Other, -1 if Other BigNumber is bigger, or 0 if numbers are equal
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
     * Runtime Complexity: O(n) --> Iterating one time over the bigger number / if numbers are equal iterating once over either of the two
     * Space Complexity: O(n) --> Inserting a new node with the addition result for each iteration
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

        //Using the private method addNumber which will handle the digit calculations
        addNumber(thisPtr, otherPtr, res);  //--> O(n)

        //Return the res object which holds the result calling the addNumber function
        return res;
    }

    /**
     * Get the addition of a BigNumber with a other long num.
     *
     * Runtime Complexity: O(n) --> iterating one time over the bigger number / if numbers are equal iterating once over either of the two
     * Space Complexity: O(n) --> Inserting a new node with the addition result for each iteration
     * @param num the other long number to add for the BigNumber object
     * @return A new BigNumber with the result of the addition of the long num and the BigNumber object
     */
    public BigNumber addLong(long num) {
        //Creating a this object copy
        BigNumber thisCopy = new BigNumber(this);
        //Creating a pointer for this object
        IntNode thisPtr = thisCopy._head;

        //Creating a new result object which will hold the addition result
        BigNumber result = new BigNumber();
        IntNode resPtr = result._head;

        //This variable will hold the remainder
        int remainder = 0;
        //Loop on both numbers
        while (num > 0 && thisPtr != null) {
            int res = (thisPtr.getValue() + (int) (num % 10)) + remainder;
            if (res > 9 && thisPtr.getNext() != null) {
                result.addToNextSlot(resPtr, res % 10);
                remainder = 1;
            } else if(res > 9 && num > 10){
                result.addToNextSlot(resPtr, res % 10);
                remainder = 1;
            } else {
                result.addToNextSlot(resPtr, res);
                remainder = 0;
            }
            thisPtr = thisPtr.getNext();
            resPtr = resPtr.getNext();
            num /= 10;
        }
        //If this BigNumber is bigger
        while (thisPtr != null) {
            int res = (thisPtr.getValue() + remainder);
            if (res > 9 && thisPtr.getNext() != null) {
                result.addToNextSlot(resPtr, res % 10);
                remainder = 1;
            } else if (res > 9) {
                result.addToNextSlot(resPtr, res);
                remainder = 1;
            } else {
                result.addToNextSlot(resPtr, res);
                remainder = 0;
            }
            thisPtr = thisPtr.getNext();
            resPtr = resPtr.getNext();
        }

        //If long number is bigger
        while(num > 0){
            int res = (int)(num % 10) + remainder;
             if(res > 9 && num >= 10){
                 result.addToNextSlot(resPtr, res % 10);
             } else {
                 result.addToNextSlot(resPtr, res);
                 remainder = 0;
             }
             resPtr = resPtr.getNext();
             num /= 10;
        }
        //Remove initial 0
        result.removeNodeFromHead();    // --> O(1)

        //Return result object
        return result;
    }

    /**
     * A subtraction calculation over two BigNumber objects. Result cannot be less than 0. If result is less than 0 numbers will swap places in the subtraction order.
     *
     * Runtime Complexity: O(n) + O(1) + O(n) + O(n) + O(n) = O(n)
     * Space Complexity: O(n) --> Inserting a new node with the subtraction result for each iteration
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

        //Using the compareTo method to check which object is bigger (to avoid result that are smaller than 0)
        //If other number is bigger --> switch variables
        if (other.compareTo(this) == 1) {   // --> O(n)
            thisPtr = otherCopy._head;
            otherPtr = thisCopy._head;
        } else if (other.compareTo(this) == 0) {
            //If numbers are equal - return the new result object which its value is 0
            return res;
        }

        //This is a private helper function that will change the values stored in the nodes in case borrowing is needed
        subtractionBorrowing(thisPtr, otherPtr);    //--> O(n)

        //This is a private helper method that will iterate through the bigger number one time and will subtract the values of the two BigNumber's
        subtractNumber(thisPtr, otherPtr, res); // --> O(n)

        ///In case there are 0 that need to be trimmed this private recursive helper method will take care of it
        res.trimZerosFromEnd();     //--> O(n)
        res.removeNodeFromHead();   //-->O(1)

        //Return the new result object
        return res;
    }

    /**
     * A multiplication method on two BigNumber objects.
     *
     * Runtime Complexity:
     * O(n) + O(n * m) = O(n^2) (n == other Bignumber length | m == this BigNumber length)
     * Space Complexity: O(n) --> Inserting a new node with the multiplication result for each iteration
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

    ////////////////////////// HELPER METHODS //////////////////////////        //////////////////////////      //////////////////////////      //////////////////////////

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
            newBig.addToHead(new IntNode(headPtr.getValue()));
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
            this.addToHead(new IntNode(newBigHeadPtr.getValue()));
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
    private void addToHead(IntNode node) {
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

    //A overloading subtraction method that will handle and change the values of the BigNumber if borrowing is needed --> O(n)
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
        IntNode resPtr = res._head;

        while (thisPtr != null) {
            while (otherPtr != null) {
                res.addToNextSlot(resPtr, (thisPtr.getValue() - otherPtr.getValue()));
                resPtr = resPtr.getNext();
                thisPtr = thisPtr.getNext();
                otherPtr = otherPtr.getNext();
            }
            if (thisPtr != null) {
                res.addToNextSlot(resPtr, thisPtr.getValue());
                resPtr = resPtr.getNext();
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
//            additionNumber.removeZeroFromStart();
            IntNode additionPtr = additionNumber._head;
            //Add zeros if needed
            if (otherPtr != otherCopy._head) {
                IntNode temp = otherCopy._head;
                while (temp != otherPtr) {
                    additionNumber.addToHead(new IntNode(0));
                    temp = temp.getNext();
                }
            }

            int firstD = 0;
            boolean borrowing = false;

            ///Inner while loop
            while (thisPtr != null) {
                int ptrResult = (otherPtr.getValue() * thisPtr.getValue()) + firstD;
                if (ptrResult > 9) {
                    borrowing = true;
                }
                if (borrowing) {
                    if (thisPtr.getNext() == null) {
                        additionNumber.disassembleNumber(additionPtr, ptrResult);
                    } else {
                        additionNumber.addToNextSlot(additionPtr, ptrResult % 10);
                        firstD = (ptrResult / 10);
                    }
                } else {
                    additionNumber.addToNextSlot(additionPtr, ptrResult);
                    firstD = 0;
                }
                additionPtr = additionPtr.getNext();
                thisPtr = thisPtr.getNext();
                borrowing = false;
            }
            additionNumber.removeNodeFromHead();
            result = result.addBigNumber(additionNumber);   //--> O(n)
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
    private void addToNextSlot(IntNode node, int num) {
        IntNode newItem = new IntNode(num);
        if (empty()) {
            _head = newItem;
        } else {
            node.setNext(newItem);
        }
    }

    //take a numb and add its digits separately to a BigNumber --> O(log n)
    private void disassembleNumber(IntNode node, int num) {
        if (num > 0) {
            while (num > 0) {
                addToNextSlot(node, (num % 10));
                num /= 10;
                node = node.getNext();
            }
        }
    }

    //A private method to handle BigNumber addition calculations
    //Runtime complexity: O(n) + O(n) + O(1) + O(1) + O(1) = O(n)
    //Space Complexity: O(n) --> Adding a new node for the result object for each iteration
    private void addNumber(IntNode thisPtr, IntNode otherPtr, BigNumber res) {
        IntNode resPtr = res._head;

        int remainder = 0;
        //While loop on until thisPtr or otherPtr is null
        while(thisPtr != null && otherPtr != null){
            int digitAdditionRes = (thisPtr.getValue() + otherPtr.getValue()) + remainder;
            if (digitAdditionRes > 9 && thisPtr.getNext() != null) {
                res.addToNextSlot(resPtr, digitAdditionRes % 10);   //addToNextFreeSlot = O(n)
                remainder = 1;
            } else if(digitAdditionRes > 9 && otherPtr.getNext() != null){
                res.addToNextSlot(resPtr, digitAdditionRes % 10);
                remainder = 1;
            } else {
                res.addToNextSlot(resPtr, digitAdditionRes);
                remainder = 0;
            }
            thisPtr = thisPtr.getNext();
            otherPtr = otherPtr.getNext();
            resPtr = resPtr.getNext();
        }

        ///If thisNumber was bigger than other BigNumber
        while(thisPtr != null){
            int digitAdditionRes = (thisPtr.getValue() + remainder);
            if (digitAdditionRes > 9 && thisPtr.getNext() != null) {
                res.addToNextSlot(resPtr, digitAdditionRes % 10);
                remainder = 1;
            } else if (digitAdditionRes > 9) {
                res.addToNextSlot(resPtr, digitAdditionRes);
                remainder = 1;
            } else {
                res.addToNextSlot(resPtr, digitAdditionRes);
                remainder = 0;
            }
            thisPtr = thisPtr.getNext();
            resPtr = resPtr.getNext();
        }

        //If other BigNumber was bigger than this BigNumber
        while(otherPtr != null){
            int digitAdditionRes = otherPtr.getValue() + remainder;
            if(digitAdditionRes > 9 && otherPtr.getNext() != null){
                res.addToNextSlot(resPtr, digitAdditionRes % 10);
            } else {
                res.addToNextSlot(resPtr, digitAdditionRes);
                remainder = 0;
            }
            resPtr = resPtr.getNext();
            otherPtr = otherPtr.getNext();
        }
        //Remove initial 0 from head
        res.removeNodeFromHead(); //---> O(1)
    }
}

