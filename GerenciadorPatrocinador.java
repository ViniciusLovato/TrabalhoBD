import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.SQLException;



public class GerenciadorPatrocinador extends Gerenciador{
	String[][] dados;

	public GerenciadorPatrocinador(DBConnection dbcon){
		super("Gerenciar Patrocinador", dbcon);
		String[] parametros = {"CNPJ", "Razao Social", "Telefone", "Endereco"};
		String[] colunas = {"CNPJ", "Razao Social", "Telefone", "Endereco"};
		
		dados = dbcon.CarregaDados("PATROCINADOR");   

		configurar(parametros, colunas, dados);
		//cnpjPat, razaoSocialPat, telefonePat, enderecoPat

		
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
				CadastrarPatrocinador cadPat = new CadastrarPatrocinador(dbcon);
				cadPat.initUI();
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
				// Seleciona ID do Artigo selecionado, chave primaria para remocao
				String removerId = dados[linhaSelecionada][0];

				String query = "DELETE FROM PATROCINADOR WHERE CNPJPat = " + removerId;
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