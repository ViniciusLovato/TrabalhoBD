// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciadorDespesa extends Gerenciador{

	public GerenciadorDespesa(){
		super("Gerenciar Despesas");
		String[] parametros = {"Evento", "Edicao", "Patrocinador", "Data", "Valor"};
		String[] colunas = {"Evento", "Edicao", "Patrocinador","Data",  "Valor", "Descricao"};

		// codDesp, codEv, numEd, cnpjPat, codEvPat, numEdPat, dataDesp, valorDesp, descricaoDesp

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

}