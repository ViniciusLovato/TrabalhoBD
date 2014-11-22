// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class GerenciadorPessoa extends Gerenciador{

	String[][] dados;

	public GerenciadorPessoa(DBConnection dbcon){
		super("Gerenciar Pessoa", dbcon);
		String[] parametros = {"Nome", "Email", "Instituicao", "Nacionalidade"};
		String[] colunas = {"Id", "Nome", "Email", "Instituicao", "Telefone", "Nacionalidade", "Endereco", "Organizador", "Participante", "Autor"};

		dados = dbcon.CarregaDados("PESSOA");   

		//String[][] dados = null;
		configurar(parametros, colunas, dados);

		// Esconde as colunas que nao sao necessarias ao usuario final
		this.table.removeColumn(this.table.getColumnModel().getColumn(0));

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
			CadastrarPessoas cadPe = new CadastrarPessoas(dbcon);
			cadPe.initUI();
		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{
			int linhaSelecionada =  table.getSelectedRow();

			if(linhaSelecionada != -1)
			{
				// Seleciona ID do Artigo selecionado, chave primaria para remocao
				String removerId = dados[linhaSelecionada][0];

				String query = "DELETE FROM PESSOA WHERE idPe = " + removerId;
				System.out.println(query);

				// Remove da tabela o artigo
				this.dbcon.executarQuery(query);

				removerLinha(linhaSelecionada);
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