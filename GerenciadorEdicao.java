// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.SQLException;


public class GerenciadorEdicao extends Gerenciador{
    private String[][] dados;
    private static final String[] colunas = {"Nome evento", "Numero Evento", "Numero Edicao", 
    "Descricao", "Data Inicio", "Data fim", "Local", "Taxa" ,"Saldo", "Total de Artigos"};
	private static final String[] parametros = {"Descicao"};
	private static final int[] position = {3};


	public GerenciadorEdicao(DBConnection dbcon){
		super("Gerenciar Edicao", dbcon);
		
		dados = dbcon.CarregaDados("formataSaidaEdicao");   

		configurar(parametros, position, colunas, dados);
		removerColuna(1);

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
			CadastrarEdicao cadastrarEdicao = new CadastrarEdicao(dbcon);

			try{
				cadastrarEdicao.initUI();
		      	dados = null;
				dados = dbcon.CarregaDados("formataSaidaEdicao"); 

		  		configurarTabela(dados, colunas);
		  		removerColuna(1);

			}
			catch(Exception ex){
				System.out.println("Erro");
			}
		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{
			int linhaSelecionada =  table.getSelectedRow();
		
			if(linhaSelecionada != -1)
			{
				// Seleciona linha selecionada pelo usuario

				// Seleciona ID do Artigo selecionado, chave primaria para remocao
				String codEv = pegarValorCelula(linhaSelecionada, 1);
				String numEd = pegarValorCelula(linhaSelecionada, 2);

				String query = "DELETE FROM EDICAO WHERE codEv = " + codEv + " AND numEd = " + numEd;
				System.out.println(query);

				// Remove da tabela o artigo
				try{
					this.dbcon.executarQuery(query);
			      	dados = null;
					dados = dbcon.CarregaDados("formataSaidaEdicao"); 

		  			configurarTabela(dados, colunas);
		  			removerColuna(1);

				}
				catch(SQLException ex){
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}

			}
			else{
				JOptionPane.showMessageDialog(null, "Erro: Nenhuma linha selecionada", "Erro", JOptionPane.ERROR_MESSAGE);

			}
		}
		else if(e.getActionCommand().equals(editar.getText()))
		{
			int linhaSelecionada = table.getSelectedRow();

			if(linhaSelecionada != -1){

				String[] linha = pegarValorLinha(linhaSelecionada);

				CadastrarEdicao cadastrarEdicao = new CadastrarEdicao(dbcon, linha);
			    
			    try{
    			    cadastrarEdicao.initUI();

		    		dados = null;
					dados = dbcon.CarregaDados("formataSaidaEdicao"); 

		  		  	configurarTabela(dados, colunas);
		  		  	removerColuna(1);
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
}