import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection
{
    public DBConnection() throws Exception{

    }

    public void disconnect(Connection con) throws SQLException
    {
        con.close();
    }

    public void commit(Connection con) throws SQLException
    {
        con.commit();
    }


    public void rollback(Connection con) throws SQLException
    {
        con.rollback();
    }

    public static Connection getConexao()
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch(Exception e)
        {
            System.out.println("Classe " + e);
        }

        try
        {
            String conexao = "jdbc:oracle:thin:@192.168.2:1521:orcl";
            String username = "a7151885";
            String passwd = "a7151885";

            Connection con = DriverManager.getConnection(conexao, username, passwd);
            return con;
        }
        catch(Exception e)
        {
            System.out.println("conectar " + e);
            return null;
        }
    }
    public static void main(String[] args)
    {
        DBConnection dbcon;
        try
        {
            dbcon = new DBConnection();

            Connection con = dbcon.getConexao();
            if(con != null)
            {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM evento");
                rs.next();
                System.out.println(rs.getString("nomeEv"));
            }
            dbcon.disconnect(con);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
