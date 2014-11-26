import java.sql.SQLException;
// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import java.text.ParseException;


public class GerenciadorAuxilio extends Gerenciador{
	private String[][] dados;
	private static final String[] colunas = {"CNPJ Patrocinador", "Codigo Evento Patrocinador", "Numero Edicao Patrocinador", 
	"Codigo Evento Apr",  "Codigo Edicao Apr", "ID Apresentador", "Valor", "Data", "Tipo", "Razao Social", "Evento", "Descricao", "Nome Apresentador"};

	private static final String[] parametros = {"Evento", "Edicao", "Patrocinador", "Data", "Valor", "Tipo"};
	private static final int[] position = {10, 11, 9, 7, 6, 8};

	public GerenciadorAuxilio(DBConnection dbcon){
		super("Gerenciar Auxilio", dbcon);

		dados = dbcon.CarregaDados("formataSaidaAuxilio");   

		configurar(parametros, position, colunas, dados);
		esconderColunas();

		// cnpjPat, codEvPat, numEdPat, codEvApr, numEdApr, idApr, valorAux, dataAux, tipoAux

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
			try
			{
				CadastrarAuxilio cadAux = new CadastrarAuxilio(dbcon);
				cadAux.initUI();

		      	dados = null;
				dados = dbcon.CarregaDados("formataSaidaAuxilio"); 

		  		configurarTabela(dados, colunas);
		  		esconderColunas();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{
			int linhaSelecionada =  table.getSelectedRow();

			if(linhaSelecionada != -1)
			{
				// codEvApr, numEdApr, idApr, tipoAux

				String codEvApr = pegarValorCelula(linhaSelecionada, 1);
				String numEdApr =  pegarValorCelula(linhaSelecionada, 2);
				String idApr = pegarValorCelula(linhaSelecionada, 5);
				String tipoAux = "'" +  pegarValorCelula(linhaSelecionada, 8) + "'";

				String query = "DELETE FROM AUXILIO WHERE codEvApr = " + codEvApr + " AND numEdApr = " + numEdApr + " AND idApr = " + 
				idApr + " AND tipoAux = " + tipoAux;
				System.out.println(query);

				// Remove da tabela o artigo
				try{
					this.dbcon.executarQuery(query);


			      	dados = null;
					dados = dbcon.CarregaDados("formataSaidaAuxilio"); 

		  			configurarTabela(dados, colunas);
		  			esconderColunas();
				}
				catch(SQLException ex){
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
					CadastrarAuxilio cadastrarAuxilio = new CadastrarAuxilio(dbcon, linha);
				    cadastrarAuxilio.initUI();

			    	dados = null;
					dados = dbcon.CarregaDados("formataSaidaAuxilio"); 

			    	configurarTabela(dados, colunas);
					esconderColunas();

				}catch(Exception ex){
					System.out.println(ex.getMessage());
				}
				// this.table.removeColumn(this.table.getColumnModel().getColumn(0));

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

	public void esconderColunas(){
		removerColuna(1);
		removerColuna(2);
		removerColuna(3);
		removerColuna(4);
		removerColuna(5);
	}
}