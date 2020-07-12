import java.util.Observable;
import java.util.Observer;

public class SysTick extends Observable  implements Interface
{
    private int Syst_CVR;
    private int Syst_CSR;
    private int Syst_RVR;
    private boolean COUNTFLAG;
    private boolean ENABLE; 
    private boolean INTERRUPTflag;
    
    public SysTick(){
        ENABLE = false;
        COUNTFLAG = false;
        INTERRUPTflag = false;
    }

    public void setCSR(int Syst_CSR){
        this.Syst_CSR = Syst_CSR;
    }
    
    public int getCSR(){
        return Syst_CSR;
    }
    
    public void setRVR(int Syst_RVR){
        this.Syst_RVR = Syst_RVR;
    }
    
    public int getRVR(){
        return Syst_RVR;
    }
    
    public void setCVR(int Syst_CVR){
        this.Syst_CVR = Syst_CVR;
        
    }
    
    public int getCVR(){
        
        return Syst_CVR;
    }
    
    public void setENABLE(boolean ENABLE){
        this.ENABLE = ENABLE;
    }
    
    public boolean getENABLE(){
        return ENABLE;
    }
     
    public void setCOUNTFLAG(boolean COUNTFLAG){
        this.COUNTFLAG = COUNTFLAG;
    }
    
    public boolean getCOUNTFLAG(){
        return COUNTFLAG;
    }
    
    public void setINTERRUPTflag(boolean INTERRUPTflag){
        this.INTERRUPTflag = INTERRUPTflag;
    }
    
    public boolean getINTERRUPTflag(){
        return INTERRUPTflag;
    }
    
    public void Tick(){
        if(ENABLE == true && Syst_CVR ==0){
             Syst_CVR=Syst_RVR;
             INTERRUPTflag = false;
             Syst_CSR=0;
        }
        else if(ENABLE == true){
            Syst_CVR--;
            INTERRUPTflag = false;
            Syst_CSR=0;
            if(Syst_CVR == 0){
                Syst_CSR=1;
                COUNTFLAG = true;
                INTERRUPTflag = true;
                setChanged();
                notifyObservers();
            }
        }     
    }
           
    public String toString(){
        return "CVR = " + Syst_CVR + ", RVR = " + Syst_RVR+", CSR = " + Syst_CSR+
        ", ENABLEflag = " + getENABLE() + ", COUNTFLAG = " +getCOUNTFLAG()+
        ", INTERRUPTflag = " + getINTERRUPTflag(); 
    }    
}
