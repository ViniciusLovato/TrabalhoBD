// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciadorDespesa extends Gerenciador{

	public GerenciadorDespesa(String[][] dados){
		super("Gerenciar Despesas");
		String[] parametros = {"Evento", "Edicao", "Patrocinador", "Data", "Valor"};
		String[] colunas = {"Codigo Despesa", "Codigo Evento", "Numero Edicao", "CNPJ Patrocinador", "Codigo Evento Patrocinador", 
			"Numero Edicao Patrocinador", "Data Despesa", "Valor Despesa", "Descricao"};

		// codDesp, codEv, numEd, cnpjPat, codEvPat, numEdPat, dataDesp, valorDesp, descricaoDesp
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