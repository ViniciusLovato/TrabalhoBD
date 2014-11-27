// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.SQLException;



public class GerenciadorRelatorio1 extends Gerenciador{

	private	String[][] dados;
	private static final String[] colunas = {"Nome evento", "Descricao Evento" , "Nome", "Email", "Local Apresentacao"};

	private static final String[] parametros = {"Nome evento", "Descricao Evento" , "Nome", "Email", "Local Apresentacao"};

	private static final int[] position = {0, 1, 2, 3, 4};

	public GerenciadorRelatorio1(DBConnection dbcon){
		super("Lista para enviar email", dbcon);

		String query = "call relatorios.procedureRelatorio1()";

		try{
			dbcon.executarQuery(query);
		}
		catch(SQLException ex){
			GerenciadorErros.errorPanel(ex.getErrorCode());
		}

		dados = dbcon.CarregaDados("avisos"); 
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