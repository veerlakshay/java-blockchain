import java.util.Date;
public class Block {
    public String hash;
    public String previousHash;
    private String data;
    private long timeStamp;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        //doing this after setting other values
        this.hash = calculateHash();
    }

    //applySha256 helper
    private String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                previousHash + Long.toString(timeStamp) + data
        );

        return calculatedhash;
    }
}
