package student.examples.comm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SecretKeyHolder {

    public static final SecretKey SECRET_KEY_HOLDER;

    static {
        SECRET_KEY_HOLDER = new SecretKeySpec(new byte[]{-33, -57, 100, -43, 38, -116, 112, -43}, "DES");
    }

}
