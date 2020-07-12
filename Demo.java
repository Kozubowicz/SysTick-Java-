public class Demo
{
    private SysTick Pokaz;
    public Demo()
    {
        Pokaz = new SysTick();
        PokazLicznika();
    }
    
    public void PokazLicznika(){          
        System.out.println("Pokaz dzialania SysTicka");
        System.out.println("Stan poczatkowy: " + Pokaz.toString());
        Pokaz.setRVR(6);
        Pokaz.setENABLE(true);
        for(int i=1;i<26;i++){
            Pokaz.Tick();
            System.out.println("Stan po  " + i + ", " + Pokaz.toString()); 
            if(Pokaz.getINTERRUPTflag() == true){
                System.out.println("Licznik osiagnol ZERO ");
            }
        }
    }
    
    public static void sample(String[] arg)
    {
       new Demo();
    }
}
