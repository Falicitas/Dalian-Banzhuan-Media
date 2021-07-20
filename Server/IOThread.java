import java.net.*;
import java.io.*;
public class IOThread extends Thread{
    private Socket socket = null;
    public IOThread(){

    }
    public void serve(){
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            int command = 0;
            command = dis.readInt();
            System.out.printf("protocal %d\n",command);
            switch (command) {
                case 1: // 登陆 username password 返回成功1，失败2
                    dos.writeInt(jdbc.login(dis.readUTF(), dis.readUTF()));
                    dos.flush();
                    break;
                case 2: // 注册 username password 返回成功1，失败2（默认失败的原因是用户名已存在）
                    dos.writeInt(jdbc.regist(dis.readUTF(), dis.readUTF(),dis.readUTF()));
                    dos.flush();
                    break;
                case 3: // 搜索 keyword 发送id,name,singer,year,views,downloads,album，直到id=-1结束，返回成功1，失败2
                    jdbc.search(dis.readUTF(), dos);
                    dos.flush();
                    break;
                case 4:// 下载 id 发送longblong 修改Music表的downloads（没写）
                    jdbc.download(dis.readInt(), dos);
                    dos.flush();
                    break;
                case 5:// 打分 uid sid score 返回成功1，失败2
                    dos.writeInt(jdbc.score(dis.readInt(),dis.readInt(),dis.readInt()));
                    dos.flush();
                    break;
                case 6:// 上传 uid name size longblog 返回成功1，失败2
		    dos.writeInt(jdbc.upload(dis));
                    dos.flush();
                    break;
		case 7:// 获取专辑 uid 发送id,name 直到-1结束 返回成功1，失败2
                    dos.writeInt(jdbc.getAlbum(dis.readInt(), dos));
                    dos.flush();
                    break;
                case 8://获取专辑中的音乐 发送id,name,singer,year,views,downloads，直到id=-1结束，返回成功1，失败2
                    dos.writeInt(jdbc.AlbumMusic(dis.readInt(), dos));
                    dos.flush();
                    break;
		case 9://获取用户头像 id 发送longblob 成功1，失败2
                    jdbc.getHead(dis.readInt(), dos);
                    dos.flush();
                    break;
		case 10://上传用户头像
                    dos.writeInt(jdbc.upHead(dis.readInt(), dis.readInt(), dis));
                    dos.flush();
                    break;
		case 11:// 获取音乐的平均分 id 返回0-10分，无人打分-2，失败-1
                    dos.writeDouble(jdbc.getMusicScore(dis.readInt()));
                    dos.flush();
                    break;
		case 12:// 获取某用户上传的音乐 发送id,name,singer,year,views,downloads，直到id=-1结束，返回成功1，失败2
                    dos.writeInt(jdbc.getProviderMusic(dis.readInt(), dos));
                    dos.flush();
                    break;
		case 13:// 创建歌单 uid name 成功返回歌单id，失败-1
                    dos.writeInt(jdbc.createAlbum(dis.readInt(), dis.readUTF()));
                    dos.flush();
                    break;
                case 14:// 删除歌单 id 成功1，失败2
                    dos.writeInt(jdbc.deleteAlbum(dis.readInt()));
                    dos.flush();
                    break;
		case 15:// 添加音乐进歌单 id mid 成功1，失败2
                    dos.writeInt(jdbc.addtoAlbum(dis.readInt(), dis.readInt()));
                    dos.flush();
                    break;
                case 16:// 从歌单中删除音乐 id mid 成功1，失败2
                    dos.writeInt(jdbc.deletefromAlbum(dis.readInt(), dis.readInt()));
                    dos.flush();
                    break;
		case 17:// 获取用户信息 id 返回username,rtime,nickname 成功1，失败2
                    dos.writeInt(jdbc.getUser(dis.readInt(), dos));
                    dos.flush();
                    break;
                default:
                    dos.writeUTF("fuck client");
                    dos.flush();
            }
        } catch (Exception e) {
            System.out.println("fuck client");
        }finally{
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    System.out.println("fuck socket");
                }
            }
        }
    }
    public synchronized void awake(Socket s){
        socket=s;
        notify();
    }
    public synchronized void run() {
        while (true) {
            try {
                wait();
                serve();
                socket = null;
                ThreadPool.AddQueue(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
