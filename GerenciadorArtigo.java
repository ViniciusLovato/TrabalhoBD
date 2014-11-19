// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciadorArtigo extends Gerenciador{

	public GerenciadorArtigo(){
		super("Gerenciar Artigo");
		String[] parametros = {"Titulo", "Data Apresentacao", "Evento", "Edicao"};
		String[] colunas = {"Codigo", "Titulo", "Data", "Hora", "Evento", "Edicao", "Codigo Apresentador"};

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
		//idArt, tituloArt, dataApresArt, horaApresArt, codEv, numEd, idAp
}