import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class BlockChain {

    //blockchain list
    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static void main(String[] args) {
        blockchain.add(new Block("Hi from first block" , "0"));
        blockchain.add(new Block("Hi from second block" , blockchain.get(blockchain.size() - 1).hash));
        blockchain.add( new Block("Hi from third block" , blockchain.get(blockchain.size() - 1).hash));

        //creating java object into json
        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);
    }


    //validating blockchain
    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;

        //looping over blockchain to check hashes
        for(int i = 1 ; i < blockchain.size() ; i++){
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);

            //compare registered hash and calculated hash
            if(!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current hashes does not match , data manipulated!!!!!");
                return false;
            }

            //compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash)){
                System.out.println("Previous hashes not equal");
                return false;
            }
        }

        return true;

    }

}
