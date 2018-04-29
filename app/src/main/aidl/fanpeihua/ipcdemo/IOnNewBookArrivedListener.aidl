// IOnNewBookArrivedListener.aidl
package fanpeihua.ipcdemo;

// Declare any non-default types here with import statements
import fanpeihua.ipcdemo.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
