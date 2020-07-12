import javax.swing.*;
import java.awt.event.*; 
import java.awt.*;
public interface Psource
{
final static byte BURST_MODE = 0;
final static byte CONTINOUS_MODE = 1;

    void addActionListener(ActionListener pl);		
    void removeActionListener(ActionListener pl);

    void trigger();
    void halt();
    void setTime(int Time);
    int  getTime();
    void setGen(int Gen);
    
}
