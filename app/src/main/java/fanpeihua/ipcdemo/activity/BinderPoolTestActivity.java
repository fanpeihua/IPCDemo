package fanpeihua.ipcdemo.activity;

import android.app.IntentService;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import fanpeihua.ipcdemo.ICompute;
import fanpeihua.ipcdemo.ISecurityCenter;
import fanpeihua.ipcdemo.R;
import fanpeihua.ipcdemo.binderPool.BinderPool;
import fanpeihua.ipcdemo.binderPool.ComputeImpl;
import fanpeihua.ipcdemo.binderPool.SecurityCenterImpl;

public class BinderPoolTestActivity extends AppCompatActivity {

    ISecurityCenter mSecurityCenter;
    ICompute mCompute;
    private static final String TAG = "BinderPoolTestActivity";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool_test);

        doWork();
    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getsInstance(BinderPoolTestActivity.this);
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        mSecurityCenter = (ISecurityCenter) SecurityCenterImpl.asInterface(securityBinder);
        Log.d(TAG, "visit ISecurtiyCenter");
        String msg = "helloworld - 安卓";
        System.out.println("contet:" + msg);

        try {
            String passWord = mSecurityCenter.encrypt(msg);
            System.out.println("encrypt:" + passWord);
            System.out.println("decrypt:" + mSecurityCenter.decrypt(passWord));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "visit ICompute");
        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        mCompute = ComputeImpl.asInterface(computeBinder);

        try {
            System.out.println("3+5=" + mCompute.add(3, 5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
