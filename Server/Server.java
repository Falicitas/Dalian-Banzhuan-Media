import java.io.*;
import java.net.*;
public class Server{
    public static void main(String[] args) {
        final String QUIT = "QUIT";
        final int DEFAULT_PORT = 8888;
        ServerSocket serverSocket = null;
        ThreadPool pool=new ThreadPool();
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("启动服务器，监听端口" + DEFAULT_PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                pool.service(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                    System.out.println("关闭serverSocket");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
