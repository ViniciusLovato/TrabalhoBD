// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.SQLException;



public class GerenciadorConsulta1 extends Gerenciador{

	private	String[][] dados;
	private static final String[] colunas = {"Codigo Evento", "Nome Evento", "Numero Edicao", "Descricao Edicao", "Tipo Auxilio", 
			"Nome Completo", "Valor"};

	private static final String[] parametros = {"Nome Evento", "Descricao Edicao", "Nome pessoa",  "Tipo Auxilio"};

	private static final int[] position = {1, 3, 5, 4};

	public GerenciadorConsulta1(DBConnection dbcon){
		super("Relacao Auxilio/Pessoa", dbcon);

		dados = dbcon.CarregaDados("consultaAvancada1"); 

		configurar(parametros, position,  colunas, dados);
		// Remove campos nao necessaios 
		removerColuna(0);
		removerColuna(2);

		criar.addActionListener(this);
		deletar.addActionListener(this);
		editar.addActionListener(this);
		voltar.addActionListener(this);

		criar.setVisible(false);
		deletar.setVisible(false);
		editar.setVisible(false);

	}

	public void actionPerformed(ActionEvent e)
	{
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equals(voltar.getText())){
			this.setVisible(false);
			this.dispose();
		}
	}
	//idArt, tituloArt, dataApresArt, horaApresArt, codEv, numEd, idAp
}