// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciadorAuxilio extends Gerenciador{
	String[][] dados;


	public GerenciadorAuxilio(DBConnection dbcon){
		super("Gerenciar Despesas", dbcon);
		String[] parametros = {"Evento", "Edicao", "Patrocinador", "Data", "Valor", "Tipo"};
		String[] colunas = {"CNPJ Patrocinador", "Codigo Evento Patrocinador", "Numero Edicao Patrocinador", "Codigo Evento Apr",  
			"Codigo Edicao Apr", "ID Apresentador", "Valor", "Data", "Tipo"};

		dados = dbcon.CarregaDados("AUXILIO");   

		configurar(parametros, colunas, dados);

		// cnpjPat, codEvPat, numEdPat, codEvApr, numEdApr, idApr, valorAux, dataAux, tipoAux

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