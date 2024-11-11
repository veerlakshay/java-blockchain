package javaBlockchain;
//For our ‘Blockcoin’ the public key will act as our address.
// It’s OK to share this public key with others to receive payment.
//Our private key is used to sign our transactions,
// so that nobody can spend our Blockcoin other than the owner of the private key.
//Users will have to keep their private key Secret!

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;
    //only UTXOs owned by this wallet.
    public HashMap<String , TransactionOutput> UTXOx = new HashMap<String,TransactionOutput>();

    public Wallet(){
        generateKeyPair();
    }

    private void generateKeyPair() {
        try{
            // It uses a special type of cryptography (called Elliptic Curve Digital Signature Algorithm, or ECDSA)

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA" , "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            //Initialize the key generator and generate a KeyPair
            //256 bytes provides an acceptable security level
            keyGen.initialize(ecSpec , random);
            KeyPair keyPair = keyGen.generateKeyPair();
            // Set the public and private keys from the keypair
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        }
        catch(Exception e){
            throw  new RuntimeException(e);
        }
    }

    //return balance and stores the UTXO's owned by this wallet in this UTXOs
    public float getBalance() {
        float total = 0;
        for (Map.Entry<String, TransactionOutput> item : BlockChain.UTXOx.entrySet()) {
            TransactionOutput UTXO = item.getValue();
            if (UTXO.isMine(publicKey)) {
                //if output belongs to me (if coins belong to me)
                // add it to our list of unspent transactions
                UTXOx.put(UTXO.id, UTXO);
                total += UTXO.value;
            }
        }

        return total;
    }

    //Generates and return a new transaction from this wallet
    public Transaction sendFunds(PublicKey _recipient , float value ) {
        if(getBalance() < value) {
            //gather balance and check funds
            System.out.println("#Not enough funds send transactions. Transaction Discarded.");
            return null;
        }

        //create array list of input
        ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
        float total = 0;

        for (Map.Entry<String , TransactionOutput> item : UTXOx.entrySet()) {
            TransactionOutput UTXO = item.getValue();
            total += UTXO.value;
            inputs.add(new TransactionInput(UTXO.id));
            if (total > value) break;
        }

        Transaction newTransaction = new Transaction(publicKey , _recipient , value , inputs);
        newTransaction.generateSignature(privateKey);

        for (TransactionInput input : inputs) {
            UTXOx.remove(input.transactionOutputId);
        }

        return newTransaction;
    }
}


