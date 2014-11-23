import javax.swing.*;
// Componetes basicos para interface
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

// Event Listener
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;


public class MiniGerenciador extends JDialog implements ActionListener{

	private DefaultTableModel model;
	protected JTable table;

	private JButton confirmar;
	private JButton cancelar;

	private JPanel panel;
	private JPanel bt_panel;

	private ArrayList<Object> result;

	private String[][] dados;
	
	public MiniGerenciador(JDialog frame, String[][] dados, String[] colunas){
		//setVisible(true);
		super(frame,"Selecione o Evento",true);

		result = null;

		this.dados = dados;

		// pega panel do Jdialgo para adicionar containers
		// Grid Layout
		panel = new JPanel();
		// Grid Layout
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));	

		getContentPane().add(panel);

		// Panel que contem os botoes
		bt_panel = new JPanel();
		bt_panel.setBorder(new EmptyBorder(10, 0, 0, 0));
		bt_panel.setLayout(new GridLayout(2, 2, 5, 5));

		// Botoes
		confirmar = new JButton("Confirmar");
		cancelar = new JButton("Cancelar");

		// Tabela
		model = new DefaultTableModel(dados, colunas);
		table = new JTable(model);
		table.setFillsViewportHeight(true);

		// adiciona tabela ao panel principal
		panel.add(new JScrollPane(table));
		
		// adiciona botoes ao pane de botao
		bt_panel.add(confirmar);
		bt_panel.add(cancelar);

		// adiciona panel que contem botoes
		panel.add(bt_panel);
		
		// listener
		confirmar.addActionListener(this);
		cancelar.addActionListener(this);

		this.pack();	
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	private void pegarResultado(){
		result = new ArrayList<Object>();

		// Pega a linha que foi selecionada pelo usuario
		int linhaSelecionada =  table.getSelectedRow();

		if(linhaSelecionada != -1)
		{
			// Seleciona linha selecionada pelo usuario e coloca valores no arraylist
			for(int i = 0; i < dados[0].length; i++){
				result.add(dados[linhaSelecionada][i]);
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "Erro: Nenhuma linha selecionada", "Erro", JOptionPane.ERROR_MESSAGE);

		}
		this.setVisible(false);
	}

	public ArrayList<Object> resultado(){
		return result;
	}

	public void actionPerformed(ActionEvent e)
	{
		System.out.println(e.getActionCommand());

		// Caso apertou o obotao ok, salva no banco de dados
		if(e.getActionCommand().equals(confirmar.getText()))
		{
			pegarResultado();	
		}
		// Caso nao, cancela toda a acao e nao salvas
		else if(e.getActionCommand().equals(cancelar.getText()))
		{
			this.setVisible(false);
			this.dispose();
		}
	}

}
 