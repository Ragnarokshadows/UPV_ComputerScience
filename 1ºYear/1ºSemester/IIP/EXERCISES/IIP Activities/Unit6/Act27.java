package Unit6;
import java.util.*;

public class Act27{
    public static class Clock{
        int dayname;
        int hour;
        int minutes;
        int seconds;
        
        public Clock(int ndayname, int nhour, int nminutes, int nseconds){
            if (ndayname < 7){dayname = ndayname;}
            else{dayname = 0;}
            if (nhour < 24){hour = nhour;}
            else{hour = 0;}
            if (nminutes < 60){minutes = nminutes;}
            else{minutes = 0;}
            if (nseconds < 60){seconds = nseconds;}
            else{seconds = 0;}
        }
        
        public void oneSecondMore(){
            seconds = seconds + 1;
            if (seconds == 60){
                seconds = 0;
                minutes = minutes + 1;
                if (minutes == 60){
                    minutes = 0;
                    hour = hour + 1;
                    if(hour == 24){
                        hour = 0;
                        dayname = dayname + 1;
                        if (dayname == 7){
                            dayname = 0;
                        }
                    }
                }
            }
        }
        
        public String toString(){
            String r = "";
            switch(dayname){
                case 0: r = "Monday";break;
                case 1: r = "Tuesday";break;
                case 2: r = "Wednesday";break;
                case 3: r = "Thursday";break;
                case 4: r = "Friday";break;
                case 5: r = "Saturday";break;
                case 6: r = "Sunday";break;
            }
            return r + "    "+hour+"    "+minutes+"    "+seconds;
        }
    }
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        int dayname,hour,minutes,seconds,i;
        Clock r;
        
        System.out.print("Write the dayname: ");
        dayname = kbd.nextInt();
        System.out.print("Write the hour: ");
        hour = kbd.nextInt();
        System.out.print("Write the minutes: ");
        minutes = kbd.nextInt();
        System.out.print("Write the seconds: ");
        seconds = kbd.nextInt();
        
        r = new Clock(dayname,hour,minutes,seconds);
        
        for(i = 0;i < 100;i++){
            System.out.println(r);
            r.oneSecondMore();
        }
    }
}

