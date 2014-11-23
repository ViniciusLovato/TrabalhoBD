// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.SQLException;


public class GerenciadorEdicao extends Gerenciador{
    String[][] dados;

	public GerenciadorEdicao(DBConnection dbcon){
		super("Gerenciar Edicao", dbcon);
		String[] parametros = {"Numero"};
		String[] colunas = {"Numero Evento", "Numero Edicao", "Descricao", "Data Inicio", "Data fim", "Local", "Taxa" ,"Saldo", "Total de Artigos"};
		
		dados = dbcon.CarregaDados("EDICAO");   

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

			CadastrarEdicao cadastrarEdicao = new CadastrarEdicao(dbcon);

			try{
				cadastrarEdicao.initUI();
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
				String codEv = dados[linhaSelecionada][0];
				String numEd = dados[linhaSelecionada][1];

				String query = "DELETE FROM EDICAO WHERE codEv = " + codEv + " AND numEd = " + numEd;
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
			else{
				JOptionPane.showMessageDialog(null, "Erro: Nenhuma linha selecionada", "Erro", JOptionPane.ERROR_MESSAGE);

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