package globalStructures;

import java.security.MessageDigest;
import java.util.Arrays;

public class Hasher {

    public static String hash(String in){
        byte[] byteArray = null;
        String out = "";
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            sha.update(in.getBytes());
            byteArray = sha.digest();
            out = new String(byteArray).replaceAll(",", "");
        }
        catch (Exception e) {
            System.err.println("Fault with hashing");
        }
        return out;
    }
}
