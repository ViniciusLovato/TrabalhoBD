// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.SQLException;



public class GerenciadorDespesa extends Gerenciador{
	String[][] dados;


	public GerenciadorDespesa(DBConnection dbcon){
		super("Gerenciar Despesas", dbcon);
		String[] parametros = {"Evento", "Edicao", "Patrocinador", "Data", "Valor"};
		String[] colunas = {"Codigo Despesa", "Codigo Evento", "Numero Edicao", "CNPJ Patrocinador", "Codigo Evento Patrocinador", 
			"Numero Edicao Patrocinador", "Data Despesa", "Valor Despesa", "Descricao"};

		dados = dbcon.CarregaDados("DESPESA");   

		// codDesp, codEv, numEd, cnpjPat, codEvPat, numEdPat, dataDesp, valorDesp, descricaoDesp
		configurar(parametros, colunas, dados);

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

		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{
			int linhaSelecionada =  table.getSelectedRow();

			if(linhaSelecionada != -1)
			{
				// Seleciona ID do Artigo selecionado, chave primaria para remocao
				String codDesp = dados[linhaSelecionada][0];
				String codEv = dados[linhaSelecionada][1];
				String numEd = dados[linhaSelecionada][1];

				String query = "DELETE FROM DESPESA WHERE codDesp = " + codDesp + " AND codEv = " + codEv + " AND numEd = " + numEd;
				System.out.println(query);

				// Remove da tabela o artigo
				try{
					this.dbcon.executarQuery(query);
					removerLinha(linhaSelecionada);	
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