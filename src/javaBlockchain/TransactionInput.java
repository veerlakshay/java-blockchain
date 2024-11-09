package javaBlockchain;

public class TransactionInput {

    //reference to transactionOutputs -> transactionId
    public String transactionOutputId;
    //contains the unspent transaction output
    public TransactionOutput UTXO;

    public TransactionInput(String transactionOutputId){
        this.transactionOutputId = transactionOutputId;
    }
}
