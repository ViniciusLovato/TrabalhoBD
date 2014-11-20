// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciadorPatrocinio extends Gerenciador{

	public GerenciadorPatrocinio(String[][] dados){
		super("Gerenciar Patrocinio");
		String[] parametros = {"CNPJ", "Evento", "Edicao", "Valor", "Saldo", "Data"};
		String[] colunas = {"CNPJ", "Evento", "Edicao", "Valor", "Saldo", "Data"};
		
		configurar(parametros, colunas, dados);
		// cnpjPat, codEv, numEd, valorPat, saldoPat, dataPat
	
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