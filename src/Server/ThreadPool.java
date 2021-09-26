import java.util.*;
import java.net.*;
public class ThreadPool {
    public static Queue<IOThread> idle=new LinkedList<IOThread>();
    public static final int ThreadNum=100;
    public static IOThread threads[]=new IOThread[ThreadNum]; 
    public ThreadPool(){
        for (int i = 0; i < threads.length; i++) {
            threads[i]=new IOThread();
            threads[i].start();
            idle.offer(threads[i]);
        }
        try {
            Thread.sleep(300);
        } catch (Exception e) {
        }
    }
    public static synchronized void AddQueue(IOThread th){
        idle.offer(th);
    }
    public void service(Socket socket) {
        while (idle.isEmpty()) {}
        idle.poll().awake(socket);
    }
}
