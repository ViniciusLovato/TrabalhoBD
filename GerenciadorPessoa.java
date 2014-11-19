// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciadorPessoa extends Gerenciador{

	public GerenciadorPessoa(){
		super("Gerenciar Pessoa");
		String[] parametros = {"Nome", "Email", "Instituicao", "Nacionalidade"};
		String[] colunas = {"Nome", "Email", "Instituicao", "Telefone", "Nacionalidade", "Endereco"};
		

		String[][] dados = null;
		configurar(parametros, colunas, dados);
		//idPe, nomePe, emailPe, instituicaoPe, telefonePe, nacionalidadePe, enderecoPe, tipoOrganizador, tipoParticipante, tipoAutor 

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