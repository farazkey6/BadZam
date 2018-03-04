/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundrecg2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

/**
 *
 * @author Farshad-PC
 */
public class AvaDB {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {

        String values = "23:32:32";
        Insertintodb(11, values);

//        findMusic(values);
    }

    public static void Insertintodb(int songID, String songHash) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/avaDB", "avadb", "avadb");
        Statement st = con.createStatement();

        String query = "INSERT INTO keyvalue (songid,songhash) values("
                + String.valueOf(songID)
                + ","
                + "'" + songHash + "'"
                + ")";
        st.executeUpdate(query);

    }

    public static int findMusic(String filter) throws SQLException {
//        // TODO code application logic here
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/avaDB", "avadb", "avadb");
        Statement st = con.createStatement();
//
//        Hashtable<Integer, Integer> ht = new Hashtable<Integer, Integer>();
//(11,"23:45:56:65")
        String query;
//
//        for (int i = 0; i < values.length; i++) {
        query = "SELECT * FROM keyvalue WHERE songHash = '" + filter + "'";
        ResultSet rs = st.executeQuery(query);

        try {
            rs.first();
            return rs.getInt("songID");
        } catch (Exception e) {
            return -1;
        }
        //  }
//
//        Enumeration<Integer> enumKey = ht.keys();
//
//        Integer maxKey = enumKey.nextElement();
//        Integer maxVal = ht.get(maxKey);
//
//        while (enumKey.hasMoreElements()) {
//            Integer key = enumKey.nextElement();
//            Integer val = ht.get(key);
//            if (maxVal < val) {
//                maxKey = key;
//                maxVal = val;
//            }
//        }
//
//        query = "SELECT * FROM musics WHERE songid = " + String.valueOf(maxKey);
//        ResultSet rs = st.executeQuery(query);
//        rs.first();
//        System.out.println("This is music with"
//                + " ID: " + String.valueOf(rs.getString("songid"))
//                + ", Name: " + rs.getString("songname")
//                + ", Singer: " + rs.getString("singer")
//                + " and Nationality: " + rs.getString("nationality")
//        );
    }

}
