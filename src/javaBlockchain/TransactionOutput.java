package javaBlockchain;

import java.security.PublicKey;

public class TransactionOutput {
    public String id;
    //new owner of the coins
    public PublicKey reciepient;
    //amount of coin they own
    public float value;
    //the id of the transaction this output was created in
    public String parentTransactionId;

    public TransactionOutput(PublicKey reciepient , float value , String parentTransactionId){
        this.reciepient = reciepient;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = StringUtil.applySha256(StringUtil.getStringFromKey(reciepient) + Float.toString(value) + parentTransactionId);

    }

    //check if coin belongs to you
    public boolean isMine(PublicKey publicKey){
        return (publicKey.equals(reciepient));
    }
}
