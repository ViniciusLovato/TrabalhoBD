// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.SQLException;

import java.text.ParseException;

public class GerenciadorPatrocinio extends Gerenciador{
	private String[][] dados;
	private static final String[] colunas = {"CNPJ", "Evento", "Edicao", "Valor", "Saldo", "Data", "Razao Social", "Nome Evento", "Descricao Evento"};

	public GerenciadorPatrocinio(DBConnection dbcon){
		super("Gerenciar Patrocinio", dbcon);
		String[] parametros = {"CNPJ", "Evento", "Edicao", "Valor", "Saldo", "Data", "Razao Social", "Nome Evento", "Descricao Evento"};
		
		
		dados = dbcon.CarregaDados("formataSaidaPatrocinio");   
		configurar(parametros, colunas, dados);
		// cnpjPat, codEv, numEd, valorPat, saldoPat, dataPat
	
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
				CadastrarPatrocinio cadPat = new CadastrarPatrocinio(dbcon);
				cadPat.initUI();
			
		      	dados = null;
				dados = dbcon.CarregaDados("formataSaidaPatrocinio"); 

		  		configurarTabela(dados, colunas);
			}

			catch(Exception exception)
			{
				System.out.println();
			}
		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{	
			int linhaSelecionada =  table.getSelectedRow();

			if(linhaSelecionada != -1)
			{
				// Seleciona ID do Artigo selecionado, chave primaria para remocao
				String removerId = dados[linhaSelecionada][0];
				String codEv = dados[linhaSelecionada][1];
				String numEd = dados[linhaSelecionada][2];

				String query = "DELETE FROM PATROCINIO WHERE cnpjPat = " + removerId + " AND codEv = " + codEv + " AND numEd = " + numEd;
				System.out.println(query);

				// Remove da tabela o artigo
				try{
					this.dbcon.executarQuery(query);


			      	dados = null;
					dados = dbcon.CarregaDados("formataSaidaPatrocinio"); 

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
			int linhaSelecionada = table.getSelectedRow();

			if(linhaSelecionada != -1){

				String[] linha = dados[linhaSelecionada];

				try
				{
					CadastrarPatrocinio cadastrarEvento = new CadastrarPatrocinio(dbcon, linha);
				    cadastrarEvento.initUI();
				}
				catch(Exception exception)
				{
					System.out.println(exception);
				}
		    	dados = null;
				dados = dbcon.CarregaDados("formataSaidaPatrocinio"); 

		    	configurarTabela(dados, colunas);
				this.table.removeColumn(this.table.getColumnModel().getColumn(0));

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