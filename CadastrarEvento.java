// Event Listners
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Grid layout
import java.awt.GridLayout;

// Componentes da interface
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

// Bordas
import javax.swing.border.EmptyBorder;

// ArryaList
import java.util.ArrayList;

public class CadastrarEvento extends JFrame implements ActionListener
{
	// JPanel para inserir os elementos
	private JPanel panel;

	// Labels
	private JLabel nome;
	private JLabel descricao;
	private JLabel website;

	// TextFields
	private JTextField in_nome;
	private JTextField in_descricao;
	private JTextField in_website;

	// Buttons
	private JButton cadastrar;
	private JButton cancelar;

	public CadastrarEvento()
	{
		// Titulo da janela
		setTitle("Cadastrar Eventos");
		// Setting up close button
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initUI()
	{
		// Criando os Paineis
		panel = new JPanel();
		// Criando o Layout
		panel.setLayout(new GridLayout(8, 0, 5, 5));
		// Adicionando o painel ao JFrame
		getContentPane().add(panel);

		// Criando as bordas
		EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);
		panel.setBorder(emptyBorder);

		// Inicializando os componentes
		nome = new JLabel("Nome:");
		descricao = new JLabel("Descricao:");
		website = new JLabel("Website:");

		in_nome = new JTextField(50);
		in_descricao = new JTextField(50);
		in_website = new JTextField(50);

		// Initializing the arrays 
		cadastrar = new JButton("Cadastrar");
		cancelar = new JButton("Cancelar");

		// Inserindo os labels e textfields no panel
		panel.add(nome);
		panel.add(in_nome);

		panel.add(descricao);
		panel.add(in_descricao);

		panel.add(website);
		panel.add(in_website);

		// Adicionando botoes no painel
		panel.add(cadastrar);
		panel.add(cancelar);

		// Adicionando os eventListeners
		cadastrar.addActionListener(this);
		cancelar.addActionListener(this);

		// Ajustando tamanho das janelas
		pack();
		// Centralizando a janela
		setLocationRelativeTo(null);
		// Seta a janela como visivel
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		// Checando qual botao esta sendo pressionado
		if(e.getActionCommand().equals(cadastrar.getText()))
		{
			onClickCadastrar();
		}
		else if(e.getActionCommand().equals(cancelar.getText()))
		{
			onClickCancelar();
		}
	}

	// Botao cadastrar
	public void onClickCadastrar()
	{
		System.exit(0);
	}

	// Botao cancelar
	public void onClickCancelar()
	{
		System.exit(0);
	}

	public static void main(String args[])
	{
		CadastrarEvento ui = new CadastrarEvento();

		// initializing the UI
		ui.initUI();
	}
}
