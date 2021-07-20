package MediaPlayer;

import MediaPlayer.Music.Music;

import java.net.*;
import java.io.*;
import java.util.*;
public class Client {
    static final String QUIT = "QUIT";
    static String SERVER_HOST = "172.16.211.3";
    static int PORT = 8888;
    public static void init(String host,int port){// 使用之前先初始化
        SERVER_HOST=host.toString();
        PORT=port;
    }
    public static int login(String username,String password){// 登陆 username password 成功返回用户id，exception或用户不存在返回-1（默认用户不存在），密码错误返回-2
        Socket socket=null;
        int re=-1;
        try {
            socket = new Socket(SERVER_HOST,PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(1);
            dos.writeUTF(username);
            dos.writeUTF(password);
            dos.flush();
            re = dis.readInt();
            socket.close();
        }catch(ConnectException e){// 连接失败
            System.out.println("连接失败");
            re = -1;
        }catch(SocketException e){// 中途服务器失联
            System.out.println("服务器失联");
            re = -1;
        }catch(IOException e){// 包含上述两者
            e.printStackTrace();
            re = -1;
        }finally{
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }

    public static int regist(String username,String password, String nickname){// 注册 username password nickname 返回成功1，失败2（默认失败的原因是用户名已存在）自动生成注册时间yyyy-MM-dd
        Socket socket=null;
        int re=2;
        try {
            socket = new Socket(SERVER_HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(2);
            dos.writeUTF(username);
            dos.writeUTF(password);
            dos.writeUTF(nickname);
            dos.flush();
            re = dis.readInt();
            socket.close();
            return re;
        } catch (ConnectException e) {// 连接失败
            System.out.println("连接失败");
            re=2;
        } catch (SocketException e) {// 中途服务器失联
            System.out.println("服务器失联");
            re=2;
        } catch (IOException e) {// 包含上述两者
            e.printStackTrace();
            re=2;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }

    public static int search(String keyword,Vector<Music> out) {// 搜索 keyword 发送id,name,singer,year,views,downloads，直到id=-1结束，返回成功1，失败2
        Socket socket=null;
        int re=1;
        out.clear();
        try {
            socket = new Socket(SERVER_HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(3);
            dos.writeUTF(keyword);
            dos.flush();
            while ((re=dis.readInt())!=-1) {
                Music m=new Music();
                m.setId(re);
                m.setSongName(dis.readUTF());
                m.setSingerName(dis.readUTF());
                m.setYear(dis.readInt());
                m.setViews(dis.readInt());
                m.setDownloads(dis.readInt());
                m.setAlbum(dis.readUTF());
                m.setTotalTime(dis.readUTF());
                out.add(m);
            }
            re=1;
        } catch (ConnectException e) {// 连接失败
            System.out.println("连接失败");
            re=2;
        } catch (SocketException e) {// 中途服务器失联
            System.out.println("服务器失联");
            re=2;
        } catch (IOException e) {// 包含上述两者
            e.printStackTrace();
            re=2;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }

    public static int search(String keyword, ObjectOutputStream out) {// music类？
        Socket socket = null;
        int re=2;
        try {
            socket=new Socket(SERVER_HOST,PORT);
            ObjectInputStream dis = new ObjectInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(3);
            dos.writeUTF(keyword);
            dos.flush();
            try{
                while (true) {
                    out.writeObject(dis.readObject());
                }
            }catch(EOFException e){
            }
            re=1;
        } catch (ConnectException e) {// 连接失败
            System.out.println("连接失败");
            re=2;
        } catch (SocketException e) {// 中途服务器失联
            System.out.println("服务器失联");
            re=2;
        } catch (Exception e) {// 包含上述两者
            e.printStackTrace();
            re=2;
        }
        finally{
            if (socket!=null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }

    public static int download(int id,FileOutputStream out) {// 下载 id 发送longblong
        Socket socket=null;
        int re=2;
        try {
            socket = new Socket(SERVER_HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(4);
            dos.writeInt(id);
            dos.flush();
            out.write(dis.readAllBytes());
            socket.close();
            re=1;
        } catch (ConnectException e) {// 连接失败
            System.out.println("连接失败");
            re=2;
        } catch (SocketException e) {// 中途服务器失联
            System.out.println("服务器失联");
            re=2;
        } catch (IOException e) {// 包含上述两者
            e.printStackTrace();
            re=2;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }

    public static int score(int uid, int sid,int score) {//打分 uid sid score 返回成功1，失败2
        Socket socket = null;
        int re = 2;
        try {
            socket = new Socket(SERVER_HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(5);
            dos.writeInt(uid);
            dos.writeInt(sid);
            dos.writeInt(score);
            dos.flush();
            System.out.println("asd");
            re = dis.readInt();
        } catch (ConnectException e) {// 连接失败
            System.out.println("连接失败");
            re = 2;
        } catch (SocketException e) {// 中途服务器失联
            System.out.println("服务器失联");
            re = 2;
        } catch (IOException e) {// 包含上述两者
            e.printStackTrace();
            re = 2;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }

    public static int upload(Music m) {// 上传 uid name size longblog 返回成功1，失败2
        Socket socket = null;
        int re = 2;
        try {
            socket = new Socket(SERVER_HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(6);
            dos.writeUTF(m.getSongName());
            dos.writeUTF(m.getSingerName());
            dos.writeInt(m.getUserId());
            dos.writeUTF(m.getAlbum());
            dos.writeUTF(m.getTotalTime());
            InputStream is=new FileInputStream(m.getMusicPath());
            byte[] b=new byte[1024];
            int len,sum=0;
            while ((len=is.read(b))!=-1) {
                sum+=len;
            }
            is.close();
            dos.writeInt(sum);
            is = new FileInputStream(m.getMusicPath());
            while ((len = is.read(b)) != -1) {
                dos.write(b, 0, len);
            }
            dos.flush();
            re = dis.readInt();
        } catch (ConnectException e) {// 连接失败
            System.out.println("连接失败");
            re = 2;
        } catch (SocketException e) {// 中途服务器失联
            System.out.println("服务器失联");
            e.printStackTrace();
            re = 2;
        } catch (IOException e) {// 包含上述两者
            e.printStackTrace();
            re = 2;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }

    public static int getAlbum(int uid,Vector<Integer> id,Vector<String> name){
        Socket socket = null;
        int re = 2;
        id.clear();
        name.clear();
        try {
            socket = new Socket(SERVER_HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(7);
            dos.writeInt(uid);
            dos.flush();
            while ((re = dis.readInt()) != -1) {
                id.add(re);
                name.add(dis.readUTF());
            }
            re = dis.readInt();
        } catch (ConnectException e) {// 连接失败
            System.out.println("连接失败");
            re = 2;
        } catch (SocketException e) {// 中途服务器失联
            System.out.println("服务器失联");
            e.printStackTrace();
            re = 2;
        } catch (IOException e) {// 包含上述两者
            e.printStackTrace();
            re = 2;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }

    public static int AlbumMusic(int id, Vector<Music> out){
        Socket socket = null;
        int re = 2;
        out.clear();
        try {
            socket = new Socket(SERVER_HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(8);
            dos.writeInt(id);
            dos.flush();
            while ((re = dis.readInt()) != -1) {
                Music m = new Music();
                m.setId(re);
                m.setSongName(dis.readUTF());
                m.setSingerName(dis.readUTF());
                m.setYear(dis.readInt());
                m.setViews(dis.readInt());
                m.setDownloads(dis.readInt());
                m.setAlbum(dis.readUTF());
                m.setTotalTime(dis.readUTF());
                out.add(m);
            }
            re = dis.readInt();
        } catch (ConnectException e) {// 连接失败
            System.out.println("连接失败");
            re = 2;
        } catch (SocketException e) {// 中途服务器失联
            System.out.println("服务器失联");
            re = 2;
        } catch (IOException e) {// 包含上述两者
            e.printStackTrace();
            re = 2;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }

    public static int getHead(int id, FileOutputStream out) {// 获取用户头像 id 发送longblob 成功1，失败2
        Socket socket = null;
        int re = 2;
        try {
            socket = new Socket(SERVER_HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(9);
            dos.writeInt(id);
            dos.flush();
            out.write(dis.readAllBytes());
            socket.close();
            re = 1;
        } catch (ConnectException e) {// 连接失败
            System.out.println("连接失败");
            re = 2;
        } catch (SocketException e) {// 中途服务器失联
            System.out.println("服务器失联");
            re = 2;
        } catch (IOException e) {// 包含上述两者
            e.printStackTrace();
            re = 2;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }

    public static int upHead(int id,int size, InputStream is) {
        Socket socket = null;
        int re = 2;
        try {
            socket = new Socket(SERVER_HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(10);
            dos.writeInt(id);
            dos.writeInt(size);
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1) {
                dos.write(b, 0, len);
            }
            dos.flush();
            // dos.close();
            re = dis.readInt();
        } catch (ConnectException e) {// 连接失败
            System.out.println("连接失败");
            re = 2;
        } catch (SocketException e) {// 中途服务器失联
            System.out.println("服务器失联");
            e.printStackTrace();
            re = 2;
        } catch (IOException e) {// 包含上述两者
            e.printStackTrace();
            re = 2;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }

    public static double getMusicScore(int id){// 获取音乐的平均分 id 返回0-10分，无人打分-2，失败-1
        Socket socket = null;
        double re = -1;
        try {
            socket = new Socket(SERVER_HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(11);
            dos.writeInt(id);
            dos.flush();
            re=dis.readDouble();
        } catch (ConnectException e) {// 连接失败
            System.out.println("连接失败");
            re = -1;
        } catch (SocketException e) {// 中途服务器失联
            System.out.println("服务器失联");
            re = -1;
        } catch (IOException e) {// 包含上述两者
            e.printStackTrace();
            re = -1;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }

    public static int getProviderMusic(int id, Vector<Music> out){// 获取某用户上传的音乐发送id,name,singer,year,views,downloads，直到id=-1结束，返回成功1，失败2
        Socket socket = null;
        int re = 2;
        out.clear();
        try {
            socket = new Socket(SERVER_HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(12);
            dos.writeInt(id);
            dos.flush();
            while ((re = dis.readInt()) != -1) {
                Music m = new Music();
                m.setId(re);
                m.setSongName(dis.readUTF());
                m.setSingerName(dis.readUTF());
                m.setYear(dis.readInt());
                m.setViews(dis.readInt());
                m.setDownloads(dis.readInt());
                m.setAlbum(dis.readUTF());
                m.setTotalTime(dis.readUTF());
                out.add(m);
            }
            re = dis.readInt();
        } catch (ConnectException e) {// 连接失败
            System.out.println("连接失败");
            re = 2;
        } catch (SocketException e) {// 中途服务器失联
            System.out.println("服务器失联");
            re = 2;
        } catch (IOException e) {// 包含上述两者
            e.printStackTrace();
            re = 2;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }

    public static int createAlbum(int uid,String name){// 创建歌单 uid name 成功返回歌单id，失败-1
        Socket socket = null;
        int re = -1;
        try {
            socket = new Socket(SERVER_HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(13);
            dos.writeInt(uid);
            dos.writeUTF(name);
            dos.flush();
            re = dis.readInt();
        } catch (ConnectException e) {// 连接失败
            System.out.println("连接失败");
            re=-1;
        } catch (SocketException e) {// 中途服务器失联
            System.out.println("服务器失联");
            re=-1;
        } catch (IOException e) {// 包含上述两者
            e.printStackTrace();
            re=-1;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }

    public static int deleteAlbum(int id){// 删除歌单 id 成功1，失败2
        Socket socket = null;
        int re = 2;
        try {
            socket = new Socket(SERVER_HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(14);
            dos.writeInt(id);
            dos.flush();
            re = dis.readInt();
        } catch (ConnectException e) {// 连接失败
            System.out.println("连接失败");
            re = 2;
        } catch (SocketException e) {// 中途服务器失联
            System.out.println("服务器失联");
            re = 2;
        } catch (IOException e) {// 包含上述两者
            e.printStackTrace();
            re = 2;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }

    public static int addtoAlbum(int id,int mid){// 添加音乐进歌单 id mid 成功1，失败2
        Socket socket = null;
        int re = 2;
        try {
            socket = new Socket(SERVER_HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(15);
            dos.writeInt(id);
            dos.writeInt(mid);
            dos.flush();
            re = dis.readInt();
        } catch (ConnectException e) {// 连接失败
            System.out.println("连接失败");
            re = 2;
        } catch (SocketException e) {// 中途服务器失联
            System.out.println("服务器失联");
            re = 2;
        } catch (IOException e) {// 包含上述两者
            e.printStackTrace();
            re = 2;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }

    public static int deletefromAlbum(int id, int mid) {// 从歌单中删除音乐 id mid 成功1，失败2
        Socket socket = null;
        int re = 2;
        try {
            socket = new Socket(SERVER_HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(16);
            dos.writeInt(id);
            dos.writeInt(mid);
            dos.flush();
            re = dis.readInt();
        } catch (ConnectException e) {// 连接失败
            System.out.println("连接失败");
            re = 2;
        } catch (SocketException e) {// 中途服务器失联
            System.out.println("服务器失联");
            re = 2;
        } catch (IOException e) {// 包含上述两者
            e.printStackTrace();
            re = 2;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }

    public static Vector<String> getUser(int id) {// 获取用户信息 id 返回username,rtime,nickname 失败null
        Socket socket = null;
        Vector<String> re=new Vector<>();
        try {
            socket = new Socket(SERVER_HOST, PORT);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(17);
            dos.writeInt(id);
            dos.flush();
            re.add(dis.readUTF());
            re.add(dis.readUTF());
            re.add(dis.readUTF());
            if (dis.readInt()==2) {
                re=null;
            }
        } catch (ConnectException e) {// 连接失败
            System.out.println("连接失败");
            re = null;
        } catch (SocketException e) {// 中途服务器失联
            System.out.println("服务器失联");
            re = null;
        } catch (IOException e) {// 包含上述两者
            e.printStackTrace();
            re = null;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
        return re;
    }
}
