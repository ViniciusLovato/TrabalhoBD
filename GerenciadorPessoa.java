// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciadorPessoa extends Gerenciador{

	public GerenciadorPessoa(String[][] dados){
		super("Gerenciar Pessoa");
		String[] parametros = {"Nome", "Email", "Instituicao", "Nacionalidade"};
		String[] colunas = {"Id", "Nome", "Email", "Instituicao", "Telefone", "Nacionalidade", "Endereco", "Organizador", "Participante", "Autor"};
		

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

		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{

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