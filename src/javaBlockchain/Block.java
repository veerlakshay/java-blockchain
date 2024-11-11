package javaBlockchain;
import java.util.Date;
import java.util.ArrayList;

public class Block {
    public String hash;
    public String previousHash;
    public String merkleRoot;
    //our data will be a simple message.
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private long timeStamp; // as number of milliseconds since 1/1/1970
    private int nonce;

    //Block Contructor.
    public Block( String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        //doing this after setting other values
        this.hash = calculateHash();
    }

    //applySha256 helper
    public String calculateHash() {
        return StringUtil.applySha256(
                previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + merkleRoot
        );
    }

    //We will require miners to do proof-of-work by trying different variable values in the block until its hash starts with a certain number of 0’s.
    //block mining method
    //A tampered blockchain will not be able to catch up with a longer & valid chain. *
    //unless they have vastly more computation speed than all other nodes in your network combined. A future quantum computer or something
    public void mineBlock(int difficulty) {

        merkleRoot = StringUtil.getMerkleRoot(transactions);
        //create a String with difficulty * "0"
        String target = StringUtil.getMerkleRoot(transactions);
        while(!hash.substring(0 , difficulty).equals(target)){
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }

    //Add transactions to this block
    //Our addTransaction boolean method will add the transactions and
    // will only return true if the transaction has been successfully added.
    public boolean addTransaction(Transaction transaction) {
        //process transaction and check if valid , unless block is genesis block then ignore
        if(transaction == null) return false;
        if((!("0".equals(previousHash)))) {
            if ((transaction.processTransaction() != true)) {
                System.out.println("Transaction failed to process. Discarded");
                return false;
            }
        }

        transactions.add(transaction);
        System.out.println("Transaction Successfully added to block");
        return true;
    }
}
