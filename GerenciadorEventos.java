// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciadorEventos extends Gerenciador{

	public GerenciadorEventos(){
		super("Gerenciar Eventos");
		String[] parametros = {"Codigo", "Nome", "Website"};
		String[] colunas = {"Codigo", "Nome", "Descricao", "Website", "Total de Artigos"};
		
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
			CadastrarEvento cadastrarEvento = new CadastrarEvento();
		    cadastrarEvento.initUI();
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