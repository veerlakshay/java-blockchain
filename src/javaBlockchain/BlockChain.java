package javaBlockchain;//This blockchain:
//> Is made up of blocks that store data.
//> Has a digital signature that chains your blocks together.
//> Requires proof of work mining to validate new blocks.
//> Can be check to see if data in it is valid and unchanged.

import java.security.Security;
import java.util.ArrayList;
import java.util.Set;

import com.google.gson.GsonBuilder;

public class BlockChain {

    //blockchain list
    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static int difficulty = 3;
    public static Wallet walletA;
    public static Wallet walletB;
    public static void main(String[] args) {
//        blockchain.add(new Block("Hi from first block" , "0"));
//        System.out.println("Trying to mine block 1 ...");
//        blockchain.get(0).mineBlock(difficulty);
//
//        blockchain.add(new Block("Hi from second block" , blockchain.get(blockchain.size() - 1).hash));
//        System.out.println("Trying to mine block 2 ...");
//        blockchain.get(1).mineBlock(difficulty);
//
//        blockchain.add( new Block("Hi from third block" , blockchain.get(blockchain.size() - 1).hash));
//        System.out.println("Trying to mine block 3 ...");
//        blockchain.get(2).mineBlock(difficulty);
//
//        System.out.println("\nBlockchain is Valid: " + isChainValid());
//
//        //creating java object into json
//        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
//        System.out.println("\nThe block chain: ");
//        System.out.println(blockchainJson);

        //Setup Boumcey castle as a Security provider
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        //create new wallets
        walletA = new Wallet();
        walletB = new Wallet();

        //testing public and private key
        System.out.println("Private key and public keys : ");
        System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
        System.out.println(StringUtil.getStringFromKey(walletA.publicKey));

        //creating transaction from wallet A to B

        Transaction transaction = new Transaction(walletA.publicKey , walletB.publicKey , 5, null);
        transaction.generateSignature(walletA.privateKey);

        //verifying that signature worked and verify it from public key
        System.out.println("IS signature verified : " + transaction.verifySignature());
    }

    //validating blockchain
    //Any change to the blockchain’s blocks will cause this method to return false.
    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;

        //looping over blockchain to check hashes
        for(int i = 1 ; i < blockchain.size() ; i++){
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            String hashTarget = new String(new char[difficulty]).replace('\0' , '0');

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
            //check if hash is solved
            if(!currentBlock.hash.substring(0 , difficulty).equals(hashTarget)){
                System.out.println("This block hasn't been mined");
                return false;
            }
        }

        return true;
    }

}
