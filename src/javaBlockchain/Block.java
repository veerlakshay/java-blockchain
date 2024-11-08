package javaBlockchain;

import javaBlockchain.StringUtil;

import java.util.Date;
public class Block {
    public String hash;
    public String previousHash;
    private String data;
    private long timeStamp; // as number of milliseconds since 1/1/1970
    private int nonce;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        //doing this after setting other values
        this.hash = calculateHash();
    }

    //applySha256 helper
    public String calculateHash() {
        return StringUtil.applySha256(
                previousHash + Long.toString(timeStamp) + data
        );
    }

    //We will require miners to do proof-of-work by trying different variable values in the block until its hash starts with a certain number of 0’s.
    //block mining method
    //A tampered blockchain will not be able to catch up with a longer & valid chain. *
    //unless they have vastly more computation speed than all other nodes in your network combined. A future quantum computer or something
    public void mineBlock(int difficulty) {
        //create a String with difficulty * "0"
        String target = new String(new char[difficulty]).replace('\0' , '0');
        while(!hash.substring(0 , difficulty).equals(target)){
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }
}
