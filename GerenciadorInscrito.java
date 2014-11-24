import java.sql.SQLException;
// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import java.text.ParseException;


public class GerenciadorInscrito extends Gerenciador{
	private String[][] dados;
	private static final String[] colunas = {};


	public GerenciadorInscrito(DBConnection dbcon){
		super("Gerenciar Inscrito", dbcon);
		String[] parametros = {};

		// dados = dbcon.CarregaDados();   

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