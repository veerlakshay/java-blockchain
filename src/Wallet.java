//For our ‘Blockcoin’ the public key will act as our address.
// It’s OK to share this public key with others to receive payment.
//Our private key is used to sign our transactions,
// so that nobody can spend our Blockcoin other than the owner of the private key.
//Users will have to keep their private key Secret!

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;

    public Wallet(){
        generateKeyPair();
    }

    private void generateKeyPair() {
        try{
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
}


