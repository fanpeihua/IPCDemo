package fanpeihua.ipcdemo.binderPool;

import android.os.RemoteException;

import fanpeihua.ipcdemo.ICompute;

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
