public class Driver {
    public static void main(String[] args) {
        System.out.println("************************ MMN 15 TEST START **************************");

        System.out.println("***** b1 = new  BigNumber() = ");
        BigNumber b1 = new BigNumber();
        System.out.println(b1);

        System.out.println("\n***** b2 = new BigNumber(1234567895432L) = ");
        BigNumber b2 = new BigNumber(1234567895432L);
        System.out.println(b2);

        System.out.println("\n****** b3 = new BigNumber(b2) = ");
        BigNumber b3 = new BigNumber(b2);
        System.out.println(b3);
//
        System.out.println("\n****** b4 = b3.addLong(123456789L) = ");
        BigNumber b4 = b3.addLong(123456789L);
        System.out.println(b4);
//
        System.out.println("\n****** b5 = b3.addBigNumber(b4) = ");
        BigNumber b5 = b3.addBigNumber(b4);
        System.out.println(b5);

        System.out.println("\n****** b6 = b4.subtractBigNumber(b2) = ");
        BigNumber b6 = b4.subtractBigNumber(b2);
        System.out.println(b6);

        System.out.println("\n****** comp = b2.compareTo(b3) = ");
        int comp = b2.compareTo(b3);
        System.out.println(comp);

        System.out.println("\n****** b7 = b2.multBigNumber(b4) = ");
        BigNumber b7 = b2.multBigNumber(b4);
        System.out.println(b7);

        System.out.println("\n************************ MMN 15 TEST END **************************");

        BigNumber big1 = new BigNumber(987654);
        BigNumber big2 = new BigNumber(2);
        big1 = big1.addLong(99999999);

        big1 = big1.addBigNumber(big1);
        big1 = big1.addBigNumber(big1);
        big1 = big1.multBigNumber(big2);

        BigNumber big4 = new BigNumber(99);


        BigNumber big3 = new BigNumber(99);
        System.out.println(big1 +"addLong");
        System.out.println(big2.addBigNumber(big3) + "AddBig");

        ////////////////
    }
}
