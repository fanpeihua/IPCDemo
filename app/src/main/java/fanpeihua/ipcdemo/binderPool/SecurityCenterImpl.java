package fanpeihua.ipcdemo.binderPool;

import android.os.RemoteException;

import fanpeihua.ipcdemo.ISecurityCenter;

public class SecurityCenterImpl extends ISecurityCenter.Stub {

    private static final char SECRET_CODE = '^';

    @Override
    public String encrypt(String content) {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) {
        return encrypt(password);
    }
}
