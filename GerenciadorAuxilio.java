import java.sql.SQLException;
// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import java.text.ParseException;


public class GerenciadorAuxilio extends Gerenciador{
	private String[][] dados;
	private static final String[] colunas = {"CNPJ Patrocinador", "Codigo Evento Patrocinador", "Numero Edicao Patrocinador", "Codigo Evento Apr",  
			"Codigo Edicao Apr", "ID Apresentador", "Valor", "Data", "Tipo"};


	public GerenciadorAuxilio(DBConnection dbcon){
		super("Gerenciar Auxilio", dbcon);
		String[] parametros = {"Evento", "Edicao", "Patrocinador", "Data", "Valor", "Tipo"};

		dados = dbcon.CarregaDados("AUXILIO");   

		configurar(parametros, colunas, dados);

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
				dados = dbcon.CarregaDados("AUXILIO"); 

		  		configurarTabela(dados, colunas);
			}
			catch(Exception exception)
			{
				System.out.println(exception);
			}
		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{
			int linhaSelecionada =  table.getSelectedRow();

			if(linhaSelecionada != -1)
			{
				// codEvApr, numEdApr, idApr, tipoAux
				String codEvApr = dados[linhaSelecionada][3];
				String numEdApr = dados[linhaSelecionada][4];
				String idApr = dados[linhaSelecionada][5];
				String tipoAux = "'" +  dados[linhaSelecionada][8] + "'";

				String query = "DELETE FROM AUXILIO WHERE codEvApr = " + codEvApr + " AND numEdApr = " + numEdApr + " AND idApr = " + 
				idApr + " AND tipoAux = " + tipoAux;
				System.out.println(query);

				// Remove da tabela o artigo
				try{
					this.dbcon.executarQuery(query);


			      	dados = null;
					dados = dbcon.CarregaDados("AUXILIO"); 

		  			configurarTabela(dados, colunas);
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

		}
		else if(e.getActionCommand().equals(voltar.getText()))
		{
			setVisible(false);
			dispose();
		}
	}
}