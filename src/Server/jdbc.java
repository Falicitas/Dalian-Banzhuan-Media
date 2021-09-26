import java.sql.*;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.SimpleDateFormat;
public class jdbc {

    public static final String URL = "jdbc:mysql://127.0.0.1:3306/project";//test
    public static final String USER = "debian-sys-maint";
    public static final String PASSWORD = "xTZEF0G87SMCClsh";//qweasdzxc0
    public static int UserNum = 0,MusicNum = 0;
    public static void getUserNum(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("select id from User");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserNum++;
            }
        } catch (Exception e) {
        }
    }
    
    public static void getMusicNum() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("select id from Music");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MusicNum++;
            }
        } catch (Exception e) {
        }
    }
    public static int login(String username,String password){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("select id,password from User where username=?;");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString(2).equals(password)) {
                    return rs.getInt(1);
                }
                return -2;
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    public static int regist(String username, String password,String nickname){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("select id from User where username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
		//System.out.println("User exist");
                return 2;
            }
            if (UserNum==0) {
                getUserNum();
            }
            ps = conn.prepareStatement("insert into User values (?,?,?,null,?,?);");
            ps.setInt(1, UserNum++);
            ps.setString(2, username);
            ps.setString(3, password);
	    ps.setString(4, (String) (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
            ps.setString(5, nickname);
            if (ps.executeUpdate()>0) {
                return 1;
            }
            return 2;
        } catch (Exception e) {
	    //e.printStackTrace();
            return 2;
        }
    }
    
    public static int search(String keyword, DataOutputStream dos) {//
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("select id,name,singer,year,views,downloads,album,lenth from Music where name like \"%\"?\"%\" or singer like \"%\"?\"%\";");
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dos.writeInt(rs.getInt(1));//id
		if(rs.getString(2)==null)
			dos.writeUTF("null");
		else
                	dos.writeUTF(rs.getString(2));//name
		if(rs.getString(3)==null)
			dos.writeUTF("null");
		else
                	dos.writeUTF(rs.getString(3));//singer
		//if(rs.getInt(4)==null)
                //        dos.writeInt(-1);
                //else
                	dos.writeInt(rs.getInt(4));//year
                dos.writeInt(rs.getInt(5));//views
                dos.writeInt(rs.getInt(6));//downloads
		if (rs.getString(7) == null)
                    dos.writeUTF("null");
                else
                    dos.writeUTF(rs.getString(7));// album
		if (rs.getString(8) == null)
                    dos.writeUTF("null");
                else
                    dos.writeUTF(rs.getString(8));// lenth
            }
            dos.writeInt(-1);
            return 1;
        } catch (Exception e) {
            return 2;
        }
    }
    
    public static int download(int id, DataOutputStream dos) {//
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("select source from Music where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                InputStream is = rs.getBinaryStream(1);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    dos.write(buffer,0,len);
                }
		ps = conn.prepareStatement("update Music set downloads = downloads + 1 where id=?");
		ps.setInt(1, id);
		ps.execute();
                return 1;
            }
            return 2;
        } catch (Exception e) {
            return 2;
        }
    }
    public static int getscore(int uid,int mid){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("select score from Score where uid=? and mid=?;");
            ps.setInt(1, uid);
            ps.setInt(2, mid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -2;
        }
    }
    public static int score(int uid,int mid,int score){
        try {
	    System.out.println("score");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            int s=getscore(uid, mid);
            if (s==-2) {
                return 2;
            }
            else if(s==-1){
                PreparedStatement ps = conn.prepareStatement("insert into Score values (?,?,?);");
                ps.setInt(1, uid);
                ps.setInt(2, mid);
                ps.setInt(3, score);
                if(ps.executeUpdate()>0){
                    return 1;
                }
                return 2;
            }
            else{
                PreparedStatement ps = conn.prepareStatement("update Score set score=? where uid=? and mid=?;");
                ps.setInt(1, score);
                ps.setInt(2, uid);
                ps.setInt(3, mid);
                if (ps.executeUpdate()>0) {
                    return 1;
                }
                return 2;
            }
        } catch (Exception e) {
	    e.printStackTrace();
            return 2;
        }
    }

    public static int upload(DataInputStream dis){
        try {
            if (MusicNum==0) {
                getMusicNum();
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("insert into Music(id, name, singer, year, views, downloads, provider, source, album, lenth) values(?,?,?,?,?,?,?,?,?,?);");
            ps.setInt(1, MusicNum++);// id
            ps.setString(2, dis.readUTF());// name
            ps.setString(3, dis.readUTF());// singer
            ps.setNull(4,Types.INTEGER);// year
            ps.setInt(5, 0);// views
            ps.setInt(6, 0);// downloads
            ps.setInt(7, dis.readInt());// provider
            ps.setString(9, dis.readUTF());//album
            ps.setString(10, dis.readUTF());//lenth
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            byte[] b=new byte[1024];
            int len,sum=0,size=dis.readInt();
            while ((len=dis.read(b))!=-1) {
                baos.write(b,0,len);
                // System.out.println(len);
                sum+=len;
                if (sum>=size) {
                    break;
                }
            }
            ps.setBytes(8, baos.toByteArray());// source
            if (ps.executeUpdate()>0) {
                return 1;
            }
            return 2;
        } catch (Exception e) {
            System.out.println(2);
            return 2;
        }
    }

    public static int getAlbum(int uid, DataOutputStream dos)
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("select distinct id,name from Album where uid=?");
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dos.writeInt(rs.getInt(1));
                dos.writeUTF(rs.getString(2));
            }
            dos.writeInt(-1);
            return 1;
        } catch (Exception e) {
            return 2;
        }
    }

    public static int AlbumMusic(int id, DataOutputStream dos){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("select M.id,M.name,singer,year,views,downloads,album,lenth from Music M join Album A on M.id = A.mid where A.id = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dos.writeInt(rs.getInt(1));// id
                if (rs.getString(2) == null)
                    dos.writeUTF("null");
                else
                    dos.writeUTF(rs.getString(2));// name
                if (rs.getString(3) == null)
                    dos.writeUTF("null");
                else
                    dos.writeUTF(rs.getString(3));// singer
                dos.writeInt(rs.getInt(4));// year
                dos.writeInt(rs.getInt(5));// views
                dos.writeInt(rs.getInt(6));// downloads
		if (rs.getString(7) == null)
                    dos.writeUTF("null");
                else
                    dos.writeUTF(rs.getString(7));// album
		if (rs.getString(8) == null)
                    dos.writeUTF("null");
                else
                    dos.writeUTF(rs.getString(8));// lenth
            }
            dos.writeInt(-1);
            return 1;
        } catch (Exception e) {
            return 2;
        }
    }

    public static int getHead(int id,DataOutputStream dos){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("select head from User where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                InputStream is = rs.getBinaryStream(1);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    dos.write(buffer, 0, len);
                }
                return 1;
            }
            return 2;
        } catch (Exception e) {
            return 2;
        }
    }

    public static int upHead(int id,int size, DataInputStream dis) {
        try {
            if (MusicNum == 0) {
                getMusicNum();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len, sum = 0;
            while ((len = dis.read(b)) != -1) {
                baos.write(b, 0, len);
                // System.out.println(len);
                sum += len;
                if (sum >= size) {
                    break;
                }
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("update User set head=? where id=?;");
            ps.setBytes(1, baos.toByteArray());// source
            ps.setInt(2, id);// id
            if (ps.executeUpdate() > 0) {
                return 1;
            }
            return 2;
        } catch (Exception e) {
            return 2;
        }
    }

    public static double getMusicScore(int id){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("select ifnull(avg(score),-2) from Score where mid=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
            return -1.0;
        } catch (Exception e) {
            return -1.0;
        }
    }

    public static int getProviderMusic(int id,DataOutputStream dos){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("select id,name,singer,year,views,downloads,album,lenth from Music where provider = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dos.writeInt(rs.getInt(1));// id
                if (rs.getString(2) == null)
                    dos.writeUTF("null");
                else
                    dos.writeUTF(rs.getString(2));// name
                if (rs.getString(3) == null)
                    dos.writeUTF("null");
                else
                    dos.writeUTF(rs.getString(3));// singer
                dos.writeInt(rs.getInt(4));// year
                dos.writeInt(rs.getInt(5));// views
                dos.writeInt(rs.getInt(6));// downloads
                if (rs.getString(7) == null)
                    dos.writeUTF("null");
                else
                    dos.writeUTF(rs.getString(7));// album
                if (rs.getString(8) == null)
                    dos.writeUTF("null");
                else
                    dos.writeUTF(rs.getString(8));// lenth
            }
            dos.writeInt(-1);
            return 1;
        } catch (Exception e) {
            return 2;
        }
    }

    public static int createAlbum(int uid,String name){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("select distinct id from Album order by id;");
            ResultSet rs = ps.executeQuery();
            int id=0;
            while (rs.next()) {
                if (rs.getInt(1)==id) {
                    id++;
                }
            }
            ps = conn.prepareStatement("insert into Album values(?,?,?,-1);");
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, uid);
            if(ps.executeUpdate()>0)
                return id;
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    public static int deleteAlbum(int id){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("delete from Album where id=?;");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0)
                return 1;
            return 2;
        } catch (Exception e) {
            return 2;
        }
    }

    public static int addtoAlbum(int id,int mid){
        if (mid<0) {
            return 2;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("select distinct name,uid from Album where id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            String name;
            int uid;
            if (rs.next()) {
                name=rs.getString(1);
                uid=rs.getInt(2);
            }
            else{
		System.out.println("Album unexist");
                return 2;
	    }
            ps = conn.prepareStatement("select count(*) from Album where id=? and mid=?;");
            ps.setInt(1, id);
            ps.setInt(2, mid);
            rs = ps.executeQuery();
            if (rs.next()&&rs.getInt(1)!=0) {
		System.out.println("Music exists");
                return 2;
            }
            ps = conn.prepareStatement("insert into Album values(?,?,?,?);");
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, uid);
            ps.setInt(4, mid);
            if (ps.executeUpdate() > 0)
                return 1;
            return 2;
        } catch (Exception e) {
	    e.printStackTrace();
            return 2;
        }
    }

    public static int deletefromAlbum(int id,int mid){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("delete from Album where id=? and mid=?;");
            ps.setInt(1, id);
            ps.setInt(2, mid);
            if (ps.executeUpdate() > 0)
                return 1;
            return 2;
        } catch (Exception e) {
            return 2;
        }
    }

    public static int getUser(int id,DataOutputStream dos){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement("select username,rtime,nickname from User where id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                dos.writeUTF(rs.getString(1));
                dos.writeUTF(rs.getString(2));
                dos.writeUTF(rs.getString(3));
                return 1;
            }
            return 2;
        } catch (Exception e) {
            return 2;
        }
    }

}
