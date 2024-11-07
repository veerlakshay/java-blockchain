
public class BlockChain {

    public static void main(String[] args) {
        Block genesisBlock = new Block("Hi from first block" , "0");
        System.out.println("Hash for block 1 : " + genesisBlock.hash);

        Block secondBlock = new Block("Hi from second block" , genesisBlock.hash);
        System.out.println("Hash for block 2 : " + secondBlock.hash);

        Block thirdBlock = new Block("Hi from third block" , secondBlock.hash);
        System.out.println("Hash for block 3 : " + thirdBlock.hash);
    }

}
