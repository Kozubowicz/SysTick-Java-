import java.util.Observable;
import java.util.Observer;
import java.awt.event.*; 
import java.awt.*;
import javax.swing.* ;

public class Generator extends Thread implements Psource
{
    
    boolean on=false;
    
    private boolean EGEN = false; 
    private int Gen = 0;
    private int Time = 1000;
    ActionListener actionListener;
   
    public void addActionListener(ActionListener listener){
        actionListener = AWTEventMulticaster.add(actionListener, listener);
    }
    
    public void removeActionListener(ActionListener listener){
        actionListener = AWTEventMulticaster.remove(actionListener, listener);
    }
    
    public Generator(){
        start();
    }

    public void trigger(){
        on = true;
    }
    
    public void halt(){
        on = false;
    }
    
    public void setGen(int Gen){
        Gen--;
        this.Gen = Gen;  
        if (Gen<1){
            this.Gen = 0;
        }

    }
    
    public int getGen(){
        return Gen;
    }
    
     public void setTime(int Time){  
        this.Time = Time;
    }
    
    public int getTime(){
        return Time;
    }
    
    public void setEGEN(boolean EGEN){
        this.EGEN = EGEN;
    }
    
    public boolean getEGEN(){
        return EGEN;
    }
  
    public void run(){
       
       while (true){
          try{
               if (on){
                if(actionListener!=null)
                actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "impulss"));
                sleep(Time);   
              }
         
         }
         catch(InterruptedException ee){}
        }
   } 
}
