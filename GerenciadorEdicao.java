// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciadorEdicao extends Gerenciador{

	public GerenciadorEdicao(){
		super("Gerenciar Edicao");
		String[] parametros = {"Numero"};
		String[] colunas = {"Numero", "Descricao", "Data Inicio", "Data fim", "Local", "Taxa" ,"Saldo", "Total de Artigos"};
		
		String[][] dados = null;
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
			String[] eventos = {"Churras na casa do Brunao"};
			CadastrarEdicao cadastrarEdicao = new CadastrarEdicao();

			try{
				cadastrarEdicao.initUI(eventos);
			}
			catch(Exception ex){
				System.out.println("Erro");
			}
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