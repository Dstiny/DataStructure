package concurrent.c_2_3;

//模拟两个屏幕和两个售票处的电影院。一个售票处卖出一张票，只能用户其中一个电影院，不能同时用于两个。两个电影院
//票是独立的属性

public class Cinema {

    private int ticketCinema1;
    private int ticketCinema2;

    private final Object controlCinema1,controlCinema2;

    public Cinema() {

        controlCinema1=new Object();
        controlCinema2=new Object();

        ticketCinema1=20;
        ticketCinema2=20;
    }
//    售票1
    public boolean sellTicket1(int number){

        synchronized(controlCinema1){

            if(ticketCinema1>number){
                ticketCinema1-=number;
                return true;
            }else return false;
        }
    }
//售票2
    public boolean sellTicket2(int number){

        synchronized(controlCinema2){

            if(ticketCinema2>number){
                ticketCinema2-=number;
                return true;
            }else return false;
        }
    }

//    退票1
    public boolean returnTicket1(int number){

        synchronized(controlCinema1){
            ticketCinema1+=number;
            return true;
        }
    }
//    退票2
    public boolean returnTicket2(int number){

        synchronized(controlCinema2){
            ticketCinema2+=number;
            return true;
        }
    }

    public int getTicketCinema1() {
        return ticketCinema1;
    }

    public int getTicketCinema2() {
        return ticketCinema2;
    }
}

//售票处1
class TicketOffice1 implements Runnable{

    private Cinema cinema;

    public TicketOffice1(Cinema cinema) {
        this.cinema = cinema;
    }

    public void run() {
        cinema.sellTicket1(3);
        cinema.sellTicket1(2);
        cinema.returnTicket1(3);
        cinema.sellTicket1(4);
    }
}
//售票处2
class TicketOffice2 implements Runnable{

    private Cinema cinema;

    public TicketOffice2(Cinema cinema) {
        this.cinema = cinema;
    }

    public void run() {
        cinema.sellTicket2(3);
        cinema.sellTicket2(2);
        cinema.returnTicket2(3);
        cinema.sellTicket2(5);
    }
}

class Mian{

    public static void main(String[] args) {

        Cinema cinema=new Cinema();

        TicketOffice1 ticketOffice1=new TicketOffice1(cinema);
        Thread thread1=new Thread(ticketOffice1,"office1");

        TicketOffice2 ticketOffice2=new TicketOffice2(cinema);
        Thread thread2=new Thread(ticketOffice2,"office2");

        thread1.start();
        thread2.start();

        try{
            thread1.join();
            thread2.join();
        }catch(InterruptedException e){
        }

        System.out.println("cinema1:"+cinema.getTicketCinema1()+"   "+"cinema2:"+cinema.getTicketCinema2());

    }
}
