// IBookManager.aidl
package fanpeihua.ipcdemo;
import fanpeihua.ipcdemo.Book;
import fanpeihua.ipcdemo.IOnNewBookArrivedListener;

// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
