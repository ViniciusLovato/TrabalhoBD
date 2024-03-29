// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.SQLException;

import java.text.ParseException;

public class GerenciadorDespesa extends Gerenciador{
	private String[][] dados;
	private static final String[] colunas = {"Codigo Despesa", "Codigo Evento", "Nome Evento", "Numero Edicao", "Descricao Ed", 
			"CNPJ Patrocinador", "Razao Social", "Codigo Evento Patrocinador", "Numero Edicao Patrocinador", 
			"Data Despesa", "Valor Despesa", "Descricao Descricao"};

	private static final String[] parametros = {"Evento", "Edicao", "Patrocinador", "Data", "Valor"};
	private static final int[] position = {2, 4, 6, 9, 10};

	public GerenciadorDespesa(DBConnection dbcon){
		super("Gerenciar Despesas", dbcon);

		dados = dbcon.CarregaDados("formataSaidaDespesa");   

		// codDesp, codEv, numEd, cnpjPat, codEvPat, numEdPat, dataDesp, valorDesp, descricaoDesp
		configurar(parametros, position, colunas, dados);

		esconderColunas();

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
			CadastrarDespesa cadDesp = new CadastrarDespesa(dbcon);

			try{
				cadDesp.initUI();

		      	dados = null;
				dados = dbcon.CarregaDados("formataSaidaDespesa"); 

		  		configurarTabela(dados, colunas);
		  		esconderColunas();

			}
			catch(Exception ex){
				//GereciandorErros.errorPanel(ex.getErrorCode());
			}	

		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{
			int linhaSelecionada =  table.getSelectedRow();

			if(linhaSelecionada != -1)
			{
				// Seleciona ID do Artigo selecionado, chave primaria para remocao
				String codDesp = pegarValorCelula(linhaSelecionada, 0);
				String numEd = pegarValorCelula(linhaSelecionada, 1);
				String codEv = pegarValorCelula(linhaSelecionada, 3);

				String query = "DELETE FROM DESPESA WHERE codDesp = " + codDesp + " AND codEv = " + codEv + " AND numEd = " + numEd;
				System.out.println(query);

				// Remove da tabela o artigo
				try{
					this.dbcon.executarQuery(query);

			      	dados = null;
					dados = dbcon.CarregaDados("formataSaidaDespesa"); 

		  			configurarTabela(dados, colunas);
		  			esconderColunas();

				}
				catch(SQLException ex){
					GerenciadorErros.errorPanel(ex.getErrorCode());					
				}

			}
			else
			{
				JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getActionCommand().equals(editar.getText()))
		{
			int linhaSelecionada = table.getSelectedRow();

			if(linhaSelecionada != -1){

				String[] linha = pegarValorLinha(linhaSelecionada);

				try{
					CadastrarDespesa cadDesp = new CadastrarDespesa(dbcon, linha);

					cadDesp.initUI();

			      	dados = null;
					dados = dbcon.CarregaDados("formataSaidaDespesa"); 

			  		configurarTabela(dados, colunas);
			  		esconderColunas();

				}
				catch(Exception ex){
					//GereciandorErros.errorPanel(ex.getErrorCode());					
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getActionCommand().equals(voltar.getText()))
		{
			setVisible(false);
			dispose();
		}
	}

	public void esconderColunas(){

		//esconde codigo da despesa
		//	this.table.removeColumn(this.table.getColumn("Codigo Despesa"));
		removerColuna(0);

		// esconde codigo do evento
		removerColuna(1);
		//this.table.removeColumn(this.table.getColumn("Codigo Evento"));

		// Esconde numero da edicao
		removerColuna(3);
		//this.table.removeColumn(this.table.getColumn("Numero Edicao"));
		
		// Esconde codigo evento patrocinador
		//this.table.removeColumn(this.table.getColumn("Codigo Evento Patrocinador"));
		removerColuna(7);

		// Esconde numero edicao patrocinador
		//this.table.removeColumn(this.table.getColumn("Numero Edicao Patrocinador"));
		removerColuna(8);

			/*			linha = new String[12];
			linha[0] = rs.getString("codDesp"); 0

			linha[1] = rs.getString("codEv"); 1
			linha[2] = rs.getString("nomeEv"); 2
			
			linha[3] = rs.getString("numEd"); 3
			linha[4] = rs.getString("descricaoEd"); 4

			linha[5] = rs.getString("cnpjPat"); 5
			linha[6] = rs.getString("razaoSocialPat"); 6

			linha[7] = rs.getString("codEvPat");	7
			linha[8] = rs.getString("numEdPat"); 8

			linha[9] = rs.getString("dataDesp"); 9 
			linha[10] = rs.getString("valorDesp");10
			linha[11] = rs.getString("descricaoDesp") 11*/
	}

}