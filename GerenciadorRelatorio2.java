// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.SQLException;



public class GerenciadorRelatorio2 extends Gerenciador{

	private	String[][] dados;
	private static final String[] colunas = {"Nome evento", "Descricao Evento", "Nome", "Email", "Tipo de Auxilio", "Apresenta"};

	private static final String[] parametros = {"Nome evento", "Descricao Evento", "Nome", "Email", "Tipo de Auxilio", "Apresenta"};

	private static final int[] position = {0, 1, 2, 4, 5, 6};

	public GerenciadorRelatorio2(DBConnection dbcon){
		super("Relatorio de Auxilio", dbcon);

		String query = "call relatorios.procedureRelatorio2()";
		
		try{
			dbcon.executarQuery(query);
		}
		catch(SQLException ex){
			GerenciadorErros.errorPanel(ex.getErrorCode());
		}

		dados = dbcon.CarregaDados("relatorioAuxilio"); 
		configurar(parametros, position,  colunas, dados);
		// Remove campos nao necessaios 

/*		criar.addActionListener(this);
		deletar.addActionListener(this);
		editar.addActionListener(this);*/
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
}