import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection
{
	// Objeto de conexao com o banco de dados
	private Connection conexao;

    public DBConnection()
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        try
        {
            String url = "jdbc:oracle:thin:@grad.icmc.usp.br:15214:orcl14";
            String username = "a7151885";
            String passwd = "a7151885";

            this.conexao = DriverManager.getConnection(url, username, passwd);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    // Checa se a conexao foi criada com sucesso
    public boolean isNull()
    {
    	return(conexao == null);
    }

    // Disconectar
    public void disconnect() throws SQLException
    {
        this.conexao.close();
    }

    // Commit das alteracoes
    public void commit() throws SQLException
    {
        this.conexao.commit();
    }

    // rollback das alteracoes
    public void rollback() throws SQLException
    {
        this.conexao.rollback();
    }

    // executar as queries no bd
    public ResultSet executarQuery(String sqlQuery)
    {
    	try
    	{
    	   	Statement stmt = this.conexao.createStatement();
	       	return(stmt.executeQuery(sqlQuery));
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
    		return null;
    	}
    }

    public static void main(String[] args)
    {
        DBConnection dbcon;
        ResultSet rs;

        try
        {
            dbcon = new DBConnection();

            if(!dbcon.isNull())
            {
                rs = dbcon.executarQuery("SELECT * FROM PESSOA");

                while(rs.next())
                {
                	System.out.println(rs.getString("nomePe"));
                }
            }
            dbcon.disconnect();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
