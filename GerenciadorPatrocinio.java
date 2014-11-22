// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.SQLException;



public class GerenciadorPatrocinio extends Gerenciador{
	String[][] dados;

	public GerenciadorPatrocinio(DBConnection dbcon){
		super("Gerenciar Patrocinio", dbcon);
		String[] parametros = {"CNPJ", "Evento", "Edicao", "Valor", "Saldo", "Data"};
		String[] colunas = {"CNPJ", "Evento", "Edicao", "Valor", "Saldo", "Data"};
		
		
		dados = dbcon.CarregaDados("PATROCINIO");   
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