package com.mygdx.custom;


public class ClickListener {
    
    public interface PressListener{
        public void onFocus();
        public void onDeFocus();
    }
    
    
    public ClickListener(PressListener pressListener){
        new MyClickListener(pressListener);
    }
    
    private class MyClickListener{
        private PressListener pressListener;
        
        public MyClickListener(PressListener pressListener){
            this.pressListener = pressListener;
        }
    }

}
