// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.SQLException;



public class GerenciadorConsulta2 extends Gerenciador{

	private	String[][] dados;
	private static final String[] colunas = {"Codigo Evento", "Nome Evento", "Numero Edicao", "Descricao Edicao", "Razao Social", 
			"Tipo Auxilio", "Soma dos Auxilios"};

	private static final String[] parametros = {"Nome Evento", "Descricao Edicao", "Razao Social", "Tipo Auxilio"};

	private static final int[] position = {1, 3, 4, 5};

	public GerenciadorConsulta2(DBConnection dbcon){
		super("Relacao Patrocinador/Auxilio", dbcon);

		dados = dbcon.CarregaDados("consultaAvancada2"); 

		configurar(parametros, position,  colunas, dados);
		// Remove campos nao necessaios 

/*		criar.addActionListener(this);
		deletar.addActionListener(this);
		editar.addActionListener(this);*/
		voltar.addActionListener(this);

		removerColuna(0);
		removerColuna(2);

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