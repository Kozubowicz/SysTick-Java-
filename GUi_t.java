import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.* ;
import java.util.Observer;
import java.util.Observable;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class GUi_t extends JFrame implements ActionListener, DocumentListener, ChangeListener
{
   private SysTick sysTick;
   private Generator generator, geninf;
   JButton oneTick;
   JButton Start, Stop, inf;
   JTextField cvrVal;
   JRadioButton enable;
   JTextField RVR;
   JTextField Gen;
   JTextField Time;
   JTextField Flags;
   JLabel etykieta;
   GraphPanel Graph;
   JSlider Slider;
   static final int Delay_MIN = 0;
    static final int Delay_MAX = 5500;
    static final int Delay_INIT = 1000;
   
   private List<Double> scores= new ArrayList<Double>();

    public GUi_t()
    {
        sysTick = new SysTick();
        generator = new Generator();
        generator.addActionListener(this);
        geninf = new Generator();
        geninf.addActionListener(this);
        makeGUi_t();
        updateGraph(false);
   }

    public void makeGUi_t()
    {
        setSize(500,550);
        setVisible(true);
        setTitle("S__GUI");
        
        JPanel prawy = new JPanel();
        JPanel centralny = new JPanel();
        JPanel lewy = new JPanel();
        JPanel dolny = new JPanel();
        
        JPanel ptop = new JPanel();
        JPanel pend = new JPanel();
        JPanel ctop = new JPanel();
        JPanel cend = new JPanel();
        JPanel ltop = new JPanel();
        JPanel lcen = new JPanel();
        JPanel lend = new JPanel();
        JPanel pcen = new JPanel();
        
        ptop.setLayout(new BorderLayout());
        pend.setLayout(new BorderLayout());
        ctop.setLayout(new BorderLayout());
        cend.setLayout(new BorderLayout());
        ltop.setLayout(new BorderLayout());
        lcen.setLayout(new BorderLayout());
        lend.setLayout(new BorderLayout());
        pcen.setLayout(new BorderLayout());
        dolny.setLayout(new BorderLayout());
        prawy.setLayout(new BorderLayout());
        centralny.setLayout(new BorderLayout());
        lewy.setLayout(new BorderLayout());
        
        setLayout(new FlowLayout());
        
        oneTick = new JButton("Tick");
        oneTick.addActionListener(this);
        
        enable = new JRadioButton();
        enable.addActionListener(this);
                
        cvrVal = new JTextField("" +sysTick.getCVR(),6);
        cvrVal.addActionListener(this) ;
        cvrVal.getDocument().addDocumentListener(this);
        cvrVal.getDocument().putProperty("owner", cvrVal);
                
        RVR = new JTextField(""+sysTick.getRVR(),6);        
        Gen = new JTextField(""+generator.getGen(),6);
        Time = new JTextField(""+generator.getTime(),6);
        Flags = new JTextField(" ",40);
        
        Stop = new JButton("Stop") ;
        Stop.addActionListener(this);
        
        inf = new JButton("Generator infinity");
        inf.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                sysTick.setENABLE(true);
                enable.setSelected(true);
                
                geninf.trigger();
            }
        });
        
        
        Start = new JButton("Uruchom Generator");
        Start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                sysTick.setENABLE(true);
                enable.setSelected(true);
                
                generator.trigger();
            }
        });
          
        lcen.add(oneTick ,BorderLayout.LINE_START);
        ltop.add(etykieta = new JLabel("Uruchom SysTick:  "),BorderLayout.LINE_START);
        ltop.add(enable, BorderLayout.LINE_END);
        
        ctop.add(etykieta = new JLabel(" CVR:  "),BorderLayout.LINE_START);
        ctop.add(cvrVal, BorderLayout.LINE_END);
                        
        cend.add(etykieta = new JLabel(" RVR:  "),BorderLayout.LINE_START);
        cend.add(RVR ,BorderLayout.LINE_END);    
        
        Graph = new GraphPanel();
       
        dolny.add(Graph, BorderLayout.PAGE_END);
        Graph.addActionListener(this) ;
        
        Slider = new JSlider(JSlider.HORIZONTAL,Delay_MIN, Delay_MAX, Delay_INIT);
        pend.add(Slider, BorderLayout.PAGE_END);
        Slider.addChangeListener(this) ;
        Slider.setMajorTickSpacing(2000);
        Slider.setMinorTickSpacing(1000);
        Slider.setPaintTicks(true);
        Slider.setPaintLabels(true);
                        
        ptop.add(Start ,BorderLayout.PAGE_START); 
        ptop.add(inf ,BorderLayout.CENTER);
        ptop.add(Stop ,BorderLayout.PAGE_END);
        pcen.add(etykieta = new JLabel(" Ticki do wykonania:  "),BorderLayout.LINE_START);
        pcen.add(Gen,BorderLayout.LINE_END);
        pend.add(etykieta = new JLabel(" Opó¿nienie(ms):  "),BorderLayout.LINE_START);
        pend.add(Time,BorderLayout.LINE_END);
        
        prawy.add(ptop, BorderLayout.PAGE_START);     
        lewy.add(ltop, BorderLayout.PAGE_START);
        lewy.add(lcen, BorderLayout.CENTER);
        lewy.add(lend, BorderLayout.PAGE_END);
        prawy.add(pcen, BorderLayout.CENTER);
        prawy.add(pend, BorderLayout.PAGE_END);
        centralny.add(ctop, BorderLayout.PAGE_START);
        centralny.add(cend, BorderLayout.PAGE_END);
        add(lewy, BorderLayout.LINE_START);
        add(centralny, BorderLayout.CENTER);
        dolny.add(Flags, BorderLayout.PAGE_START);
        add(prawy, BorderLayout.LINE_END);
        add(dolny, BorderLayout.PAGE_END);
        validate();
    }
    
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource() ;
        
        String VAL = RVR.getText();
        int Vall=Integer.parseInt(VAL);
        sysTick.setRVR(Vall);
        
        String VAL2 = Time.getText();
        int Vall2=Integer.parseInt(VAL2);
        if(Vall2<11){
            generator.setTime(10);
            geninf.setTime(10);
        }
        else {
            generator.setTime(Vall2);
            geninf.setTime(Vall2);
        }
        
        String VAL3 = cvrVal.getText();
        int Vall3=Integer.parseInt(VAL3);
        sysTick.setCVR(Vall3);
        
        if (source==oneTick){
                sysTick.Tick();
                if(sysTick.getENABLE()==true){
                cvrVal.setText("" +sysTick.getCVR());}
        }
        
        if(sysTick.getENABLE()==false){
                geninf.halt();
                generator.halt();
        }
                
                
        if (source==enable){
                sysTick.setENABLE(enable.isSelected());
        }   
        
        if (source==geninf){
            if(sysTick.getENABLE()==false){
                geninf.halt();
            }
            else{
                if(generator.getEGEN()==false){
                geninf.setEGEN(true);
                sysTick.Tick();
                cvrVal.setText("" +sysTick.getCVR());
                Time.setText("" +geninf.getTime());
            }}
        }
        
        if (source==generator){
            if(sysTick.getENABLE()==false){
                generator.halt();
            }
            else{
                
                if(geninf.getEGEN()==false){
                    generator.setEGEN(true);
                    String VAL1 = Gen.getText();
                    int Vall1=Integer.parseInt(VAL1);
                
                    generator.setGen(Vall1);
                    if(Vall1 >= 1){
                        sysTick.Tick();
                        cvrVal.setText("" +sysTick.getCVR());
                    }
                 
                    Time.setText("" +generator.getTime());
                    Gen.setText("" +generator.getGen());
                
                    if(Vall1 <= 1){
                        sysTick.setENABLE(false);
                        enable.setSelected(false);
                        generator.setEGEN(false);
                        generator.halt();
                    }
            }   
        }
        }
        
        if (source==Stop){
                geninf.halt();
                generator.halt();
                geninf.setEGEN(false);
                generator.setEGEN(false);
                sysTick.setENABLE(false);
                enable.setSelected(false);
        }   
        
        if (source == Graph){
            sysTick.setRVR(Graph.getRVR());
            RVR.setText(""+Graph.getRVR());
        }
        
        if (source == Time){
            String nowystring= Time.getText();
            try{
                int nowyStan = Integer.parseInt(nowystring);
                    if(nowyStan<=10000&nowyStan>=0)
                        generator.setTime(nowyStan);
                    else
                    if(nowyStan<0)
                        generator.setTime(0);
                    else
                        generator.setTime(10000);
                int stan = generator.getTime();
                Time.setText(""+stan);
                Slider.setValue(stan);
            }
            catch(NumberFormatException ae){}
        }
        
        
        RVR.setText("" +sysTick.getRVR());
        
        Flags.setText("  ENABLE FLAG = " + sysTick.getENABLE() + 
        ",   COUNT FLAG = " + sysTick.getCOUNTFLAG() +
        ",   INTERRUPT FLAG = " + sysTick.getINTERRUPTflag() +"  ");
        
    }
    
    public void stateChanged(ChangeEvent e)
    {
        Object source = e.getSource();
       if (source == Slider)
        {
            int nowyStan = (Slider.getValue());
            generator.setTime(nowyStan);
            int stan = generator.getTime();
            Time.setText(""+stan);
            Slider.setValue(stan);
        }
    }
    
    public void insertUpdate(DocumentEvent e)
    {
        Object owner = e.getDocument().getProperty("owner");
        if(owner == RVR)
        updateGraph(true);
        else
        updateGraph(false);
    }
    
    public void updateGraph(boolean c0)
    {
        if(!c0)
        {
        while(true)
        {
        if(scores.size()>2*Integer.parseInt(RVR.getText()))
        scores.remove(0);
        else
        break;
        }
        scores.add((double)sysTick.getCVR());
        }
        Graph.setScores(scores,sysTick.getRVR());
    }
    public void changedUpdate(DocumentEvent e){}
  
    public void removeUpdate(DocumentEvent e){}
            
    public static void sample(String[] arg)
    {
       
       EventQueue.invokeLater(() -> {
           new GUi_t();
       });
       
    }
}               