package javaBlockchain;

import java.security.*;
import java.util.ArrayList;

public class Transaction {
    //this is also the hash of transaction
    public String trasactionId;
    //sender address / public key
    public PublicKey sender;
    //Recipients address / public key
    public PublicKey reciepient;
    public float value;
    //this is to prevent anyone else from spending funds in one's wallet
    public byte[] signature;

    public  ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public  ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    //a count of how many transactions have been generated
    private  static int sequence = 0;

    //constructor
    public Transaction(PublicKey from , PublicKey to , float value , ArrayList<TransactionInput> inputs){
        this.sender = from;
        this.reciepient = to;
        this.value = value;
        this.inputs = inputs;
    }

    //This calculates the transaction hash(which will be used as its Id)
    private String calculateHash(){
        sequence++;
        return StringUtil.applySha256(
                StringUtil.getStringFromKey(sender) +
                        StringUtil.getStringFromKey(reciepient) +
                        Float.toString(value) + sequence
        );
    }

    //Signs all the data we don't wish to be tempered with
    public void generateSignature(PrivateKey privateKey){
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value);
        signature = StringUtil.applyECDSASig(privateKey , data);
    }

    //verifies the data we signed has not been tempered with
    //In reality, you may want to sign more information, like the outputs/inputs used and/or time-stamp
    public boolean verifySignature(){
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value);
        return StringUtil.verifyECDSASig(sender , data , signature);
    }

}
