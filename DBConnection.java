import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.util.*;

public class DBConnection
{
	// Objeto de conexao com o banco de dados
	private Connection conexao;

    public DBConnection(String user, String pwd, String endereco) throws SQLException
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }


        String url = "jdbc:oracle:thin:@" + endereco;
        String username = user;
        String passwd = pwd;
        // String url = "jdbc:oracle:thin:@grad.icmc.usp.br:15214:orcl14";
        // String username = "a7151885";
        // String passwd = "a7151885";

        this.conexao = DriverManager.getConnection(url, username, passwd);

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
    public ResultSet executarQuery(String sqlQuery) throws SQLException
    {
	   	Statement stmt = this.conexao.createStatement();
       	return(stmt.executeQuery(sqlQuery));
    }

    public void executarInsert(String sqlQuery) throws SQLException{

        // create our java jdbc statement
        Statement stmt = this.conexao.createStatement();
        stmt.executeUpdate(sqlQuery);
    }

    public String[] interpretaTabela(String tabela, ResultSet rs) throws Exception
    {
    	String[] linha = null;

		if (tabela.equals("EVENTO")) {
			linha = new String[5];
			linha[0] = rs.getString("codEv");
			linha[1] = rs.getString("nomeEv");
			linha[2] = rs.getString("descricaoEv");
			linha[3] = rs.getString("websiteEv");
			linha[4] = rs.getString("totalArtigosApresentadosEv");
		}
		else if(tabela.equals("EDICAO"))
		{
			linha = new String[9];
			linha[0] = rs.getString("codEv");
			linha[1] = rs.getString("numEd");
			linha[2] = rs.getString("descricaoEd");
			linha[3] = rs.getString("dataInicioEd");
			linha[4] = rs.getString("dataFimEd");	
			linha[5] = rs.getString("localEd");	
			linha[6] = rs.getString("taxaEd");	
			linha[7] = rs.getString("SaldoFinanceiroEd");	
			linha[8] = rs.getString("qtdArtigosApresentadosEd");	
		}

		else if(tabela.equals("PESSOA"))
		{
			linha = new String[10];
			linha[0] = rs.getString("idPe");
			linha[1] = rs.getString("nomePe");
			linha[2] = rs.getString("emailPe");
			linha[3] = rs.getString("instituicaoPe");
			linha[4] = rs.getString("telefonePe");	
			linha[5] = rs.getString("nacionalidadePe");	
			linha[6] = rs.getString("enderecoPe");	
			linha[7] = rs.getString("tipoOrganizador");	
			linha[8] = rs.getString("tipoParticipante");	
			linha[9] = rs.getString("tipoAutor");
		}
		else if(tabela.equals("ARTIGO"))
		{
			linha = new String[7];
			linha[0] = rs.getString("idArt");
			linha[1] = rs.getString("TituloArt");
			linha[2] = rs.getString("dataApresArt");
			linha[3] = rs.getString("horaApresArt");
			linha[4] = rs.getString("codEv");	
			linha[5] = rs.getString("numEd");	
			linha[6] = rs.getString("idApr");
		}
		else if(tabela.equals("PATROCINADOR"))
		{
			linha = new String[4];
			linha[0] = rs.getString("cnpjPat");
			linha[1] = rs.getString("razaoSocialPat");
			linha[2] = rs.getString("telefonePat");
			linha[3] = rs.getString("enderecoPat");
		}
		else if(tabela.equals("PATROCINIO"))
		{
			linha = new String[6];
			linha[0] = rs.getString("cnpjPat");
			linha[1] = rs.getString("codEv");
			linha[2] = rs.getString("numEd");
			linha[3] = rs.getString("valorPat");
			linha[4] = rs.getString("saldoPat");	
			linha[5] = rs.getString("dataPat");	
		}
		else if(tabela.equals("formataSaidaPatrocinio"))
		{
			linha = new String[9];
			linha[0] = rs.getString("cnpjPat");
			linha[1] = rs.getString("codEv");
			linha[2] = rs.getString("numEd");
			linha[3] = rs.getString("valorPat");
			linha[4] = rs.getString("saldoPat");	
			linha[5] = rs.getString("dataPat");	
			linha[6] = rs.getString("razaoSocialPat");
			linha[7] = rs.getString("nomeEv");
			linha[8] = rs.getString("descricaoEd");
		}
		else if(tabela.equals("DESPESA"))
		{
			linha = new String[9];
			linha[0] = rs.getString("codDesp");
			linha[1] = rs.getString("codEv");
			linha[2] = rs.getString("numEd");
			linha[3] = rs.getString("cnpjPat");
			linha[4] = rs.getString("codEvPat");	
			linha[5] = rs.getString("numEdPat");	
			linha[6] = rs.getString("dataDesp");
			linha[7] = rs.getString("valorDesp");
			linha[8] = rs.getString("descricaoDesp");
		}
		else if(tabela.equals("formataSaidaDespesa"))
		{
			linha = new String[12];
			linha[0] = rs.getString("codDesp");

			linha[1] = rs.getString("codEv");
			linha[2] = rs.getString("nomeEv");
			
			linha[3] = rs.getString("numEd");
			linha[4] = rs.getString("descricaoEd");

			linha[5] = rs.getString("cnpjPat");
			linha[6] = rs.getString("razaoSocialPat");

			linha[7] = rs.getString("codEvPat");	
			linha[8] = rs.getString("numEdPat");

			linha[9] = rs.getString("dataDesp");
			linha[10] = rs.getString("valorDesp");
			linha[11] = rs.getString("descricaoDesp");
		}
        else if(tabela.equals("formataSaidaAuxilio"))
        {
            linha = new String[13];

            linha[0] = rs.getString("cnpjPat");
            linha[1] = rs.getString("codEvPat");
            linha[2] = rs.getString("numEdPat");

            linha[3] = rs.getString("codEvApr");
            linha[4] = rs.getString("numEdApr");
            linha[5] = rs.getString("idApr");

            linha[6] = rs.getString("valorAux");
            linha[7] = rs.getString("dataAux");
            linha[8] = rs.getString("tipoAux");

            linha[9] = rs.getString("razaoSocialPat");
            linha[10] = rs.getString("nomeEv");
            linha[11] = rs.getString("descricaoEd");
            linha[12] = rs.getString("nomePe");


        }
		else if(tabela.equals("AUXILIO"))
		{
			linha = new String[9];
			linha[0] = rs.getString("cnpjPat");
			linha[1] = rs.getString("codEvPat");
			linha[2] = rs.getString("numEdPat");
			linha[3] = rs.getString("codEvApr");
			linha[4] = rs.getString("numEdApr");	
			linha[5] = rs.getString("idApr");	
			linha[6] = rs.getString("valorAux");
			linha[7] = rs.getString("dataAux");
			linha[8] = rs.getString("tipoAux");
		}
		else if(tabela.equals("formataSaidaInscrito"))
		{
			linha = new String[8];
			linha[0] = rs.getString("codEv");
			linha[1] = rs.getString("numEd");
			linha[2] = rs.getString("idPart");
			linha[3] = rs.getString("dataInsc");
			linha[4] = rs.getString("tipoApresentador");
			linha[5] = rs.getString("nomePe");
			linha[6] = rs.getString("nomeEv");
			linha[7] = rs.getString("descricaoEd");
		}
        else if(tabela.equals("formataSaidaEdicao"))
        {
            linha = new String[10];
            linha[0] = rs.getString("nomeEv");
            linha[1] = rs.getString("codEv");
            linha[2] = rs.getString("numEd");
            linha[3] = rs.getString("descricaoEd");
            linha[4] = rs.getString("DataInicio");
            linha[5] = rs.getString("dataFim");   
            linha[6] = rs.getString("localEd"); 
            linha[7] = rs.getString("taxaEd");  
            linha[8] = rs.getString("SaldoFinanceiroEd");   
            linha[9] = rs.getString("qtdArtigosApresentadosEd");    
        }
        else if(tabela.equals("formataSaidaArtigo"))
        {
            linha = new String[9];
            linha[0] = rs.getString("idArt");
            linha[1] = rs.getString("TituloArt");
            linha[2] = rs.getString("dataApresArt");
            linha[3] = rs.getString("horaApresArt");
            linha[4] = rs.getString("codEv");   
            linha[5] = rs.getString("numEd");   
            linha[6] = rs.getString("idApr");  
            linha[7] = rs.getString("nomePe"); 
            linha[8] = rs.getString("nomeEv");
        }
        else if(tabela.equals("formataSaidaEscreve"))
        {
            linha = new String[9];
            linha[0] = rs.getString("idAut");
            linha[1] = rs.getString("idArt");
            linha[2] = rs.getString("nomePe");
            linha[3] = rs.getString("emailPe");
        }
        else if(tabela.equals("formataSaidaOrganiza"))
        {
            linha = new String[7];
            linha[0] = rs.getString("idOrg");
            linha[1] = rs.getString("codEv");
            linha[2] = rs.getString("numEd");
            linha[3] = rs.getString("cargoOrg");
            linha[4] = rs.getString("nomePe");
            linha[5] = rs.getString("nomeEv");
            linha[6] = rs.getString("descricaoEd");


        }
		return linha;
    }

    // Sobrecarca de metodos para uma consulta simples
    public String[][] CarregaDados(String nomeTabela)
    {
    	return CarregaDados(nomeTabela, "");
    }

    // Funcao que carrega dados da tabela e aceita clausulas extras como where, group by, sort
    public String[][] CarregaDados(String nomeTabela, String clausulaExtra)
    {
    	// result set
    	ResultSet rs;
    	// dados a serem retornados
        String[][] dados = null;

        ArrayList<String[]> tabela;

        try
        {
        	// Se estiver conectado
            if(!isNull())
            {
                rs = executarQuery("SELECT * FROM " + nomeTabela + " " + clausulaExtra);

                tabela = new ArrayList<String[]>();


                // Para cada registro da tabela
                while(rs.next())
                {
                	// Inicializa os valores
                	String[] linha = interpretaTabela(nomeTabela, rs);
                	// Adiciona a lista
                	tabela.add(linha);
                }
                dados = new String [tabela.size()][5];

                // convertendo para vetor de Strings
            	int i = 0;
            	for(String[] tmp : tabela)
            	{
            		dados[i] =  tmp;
            		i++;
            	}
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            dados = null;
        }
        return dados;

    }

    public static void main(String[] args)
    {
    	
    }
}
