// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.SQLException;



public class GerenciadorArtigo extends Gerenciador{

	private	String[][] dados;
	private static final String[] colunas = {"Codigo", "Titulo", "Data", "Hora", "Evento", "Edicao", 
	"Codigo Apresentador", "Nome Apresentador", "Nome evneto"};
	private static final String[] parametros = {"Titulo", "Data Apresentacao", "Evento", "Edicao"};
	private static final int[] position = {1, 2, 4, 5};

	public GerenciadorArtigo(DBConnection dbcon){
		super("Gerenciar Artigo", dbcon);

		dados = dbcon.CarregaDados("formataSaidaArtigo");   
		configurar(parametros, position,  colunas, dados);

		// Remove campos nao necessaios 
		removerColuna(0);

		criar.addActionListener(this);
		deletar.addActionListener(this);
		editar.addActionListener(this);
		voltar.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		System.out.println(e.getActionCommand());
	
		if(e.getActionCommand().equals(criar.getText()))
		{
			CadastrarArtigos cadArt = new CadastrarArtigos(dbcon);
			try{
				cadArt.initUI();

		      	dados = null;
				dados = dbcon.CarregaDados("formataSaidaArtigo"); 

		  		configurarTabela(dados, colunas);
		  		removerColuna(0);
			}
			catch(Exception ex){
				System.out.println("Erro");
			}	

		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{
			// Seleciona linha selecionada pelo usuario
			int linhaSelecionada =  table.getSelectedRow();

			if(linhaSelecionada != -1){


				// Seleciona ID do Artigo selecionado, chave primaria para remocao
				String removerId = pegarValorCelula(linhaSelecionada, 0);
				String codEv = pegarValorCelula(linhaSelecionada, 4);
				String numEd = pegarValorCelula(linhaSelecionada, 5);
				String idApr = pegarValorCelula(linhaSelecionada, 6);

				//{"Codigo", "Titulo", "Data", "Hora", "Evento", "Edicao", 
				//	"Codigo Apresentador", "Nome Apresentador", "Nome evneto"};
				String query = "DELETE FROM ARTIGO WHERE idArt = " + removerId;
				System.out.println(query);

				// Remove da tabela o artigo
				try{
					this.dbcon.executarQuery(query);

					query = "call consistenciaPessoa.atualizaApresentador(" + codEv + ", " + numEd + ", " + idApr + ")" ;
					System.out.println(query);
					this.dbcon.executarQuery(query);

			      	dados = null;
					dados = dbcon.CarregaDados("formataSaidaArtigo"); 

			  		configurarTabela(dados, colunas);
			  		removerColuna(0);
				}
				catch(SQLException ex){
					GerenciadorErros.errorPanel(ex.getErrorCode());
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else if(e.getActionCommand().equals(editar.getText()))
		{
			int linhaSelecionada = table.getSelectedRow();

			if(linhaSelecionada != -1){

				String[] linha = pegarValorLinha(linhaSelecionada);
				CadastrarArtigos cadastrarArtigos = new CadastrarArtigos(dbcon, linha);
			    
			    try{
    			    cadastrarArtigos.initUI();

		    		dados = null;
					dados = dbcon.CarregaDados("formataSaidaArtigo"); 

		  		  	configurarTabela(dados, colunas);
		  		  	removerColuna(0);
			    }catch(Exception ex){
			    	System.err.println(ex.getMessage());
			    }

			}
			else
			{
				JOptionPane.showMessageDialog(null, "Erro: Nenhuma linha selecionada", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getActionCommand().equals(voltar.getText()))
		{
			setVisible(false);
			dispose();
		}
	}
	//idArt, tituloArt, dataApresArt, horaApresArt, codEv, numEd, idAp
}