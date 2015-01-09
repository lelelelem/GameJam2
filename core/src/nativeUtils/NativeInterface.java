package nativeUtils;

public interface NativeInterface {
    
    public interface OnResponseListener{
        public void doAction();
    }
    
    public void showDialog(OnResponseListener responseListener);
}
