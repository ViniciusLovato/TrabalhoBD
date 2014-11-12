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
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

// Bordas
import javax.swing.border.EmptyBorder;

// ArryaList
import java.util.ArrayList;

public class CadastrarInscritos extends JFrame implements ActionListener
{
	// JPanel para inserir os elementos
	private JPanel panel;

	// Labels
	private JLabel evento;
	private JLabel edicao;
	private JLabel participante;

	// TextFields
	private JComboBox<String> in_evento;
	private JComboBox<String> in_edicao;

	private JCheckBox in_apresentador;

	// Buttons
	private JButton in_participante;
	private JButton cadastrar;
	private JButton cancelar;

	public CadastrarInscritos()
	{
		// Titulo da janela
		setTitle("Cadastrar Inscritos");
		// Setting up close button
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initUI(String[] opcoes_evento, String[] opcoes_edicao)
	{
		// Criando os Paineis
		panel = new JPanel();
		// Criando o Layout
		panel.setLayout(new GridLayout(9, 0, 5, 5));
		// Adicionando o painel ao JFrame
		getContentPane().add(panel);

		// Criando as bordas
		EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);
		panel.setBorder(emptyBorder);

		// Inicializando os componentes
		evento = new JLabel("Evento:");
		edicao = new JLabel("Edicao:");
		participante = new JLabel("Participante:");

		in_evento = new JComboBox<String>(opcoes_evento);
		in_edicao = new JComboBox<String>(opcoes_edicao);

		in_apresentador = new JCheckBox("Apresentador");

		// Initializing the arrays 
		in_participante = new JButton("Escolher Participante");
		cadastrar = new JButton("Cadastrar");
		cancelar = new JButton("Cancelar");

		// Inserindo os labels e textfields e comboxes no panel
		panel.add(evento);
		panel.add(in_evento);

		panel.add(edicao);
		panel.add(in_edicao);

		panel.add(participante);
		panel.add(in_participante);

		panel.add(in_apresentador);
	
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
		String[] evento = {"bagunca", "zuera", "so lamento"};
		String[] edicao = {"bagunca", "zuera", "so lamento"};

		CadastrarInscritos ui = new CadastrarInscritos();

		// initializing the UI
		ui.initUI(evento, edicao);
	}
}
