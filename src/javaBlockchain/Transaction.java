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

    //return true if new transaction could be created
    public boolean processTransaction() {
        if(!verifySignature()) {
            System.out.println("#Transaction signature failed to verify");
            return false;
        }

        //gather transaction inputs (Make sure they are unspent)
        for(TransactionInput t : inputs) {
            t.UTXO = BlockChain.UTXOx.get(t.transactionOutputId);
        }

        //check if transaction is valid
        if (getInputsValue() < BlockChain.minimumTransaction) {
            System.out.println("#Transaction inputs to small : " + getInputsValue());
            return false;
        }

        //generate transaction outputs
        ////get value of inputs then the left over change:
        float leftOver = getInputsValue() - value;
        trasactionId = calculateHash();
        //send value to recipient
        outputs.add(new TransactionOutput(this.reciepient, value , trasactionId));
        // //send the left over 'change' back to sender
        outputs.add(new TransactionOutput( this.sender, leftOver,trasactionId));

        //add outputs to Unspent list
        for(TransactionOutput o : outputs) {
            BlockChain.UTXOx.put(o.id , o);
        }

        //remove transactions inputs from UTXO lists as spent:
        for (TransactionInput i : inputs) {
            //if Transaction can't be found skip it
            if(i.UTXO == null) continue;
            BlockChain.UTXOx.remove(i.UTXO.id);
        }

        return true;
    }

    //return sum of input (UTXOs) values
    public float getInputsValue() {
        float total = 0;
        for(TransactionInput i : inputs) {
            //if Transaction can't be found skip it
            if(i.UTXO == null) continue;
            total += i.UTXO.value;
        }

        return total;
    }

    //return sum of outputs:
    public float getOutputsValue(){
        float total = 0;
        for(TransactionOutput o : outputs) {
            total += o.value;
        }
        return total;
    }

}
