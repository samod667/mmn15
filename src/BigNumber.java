public class BigNumber {
    private IntNode _head;


    public BigNumber(){
        _head = new IntNode(0);
    }

    public BigNumber(long num){
        while(num != 0){
            num /= 10;
        }
    }
}
