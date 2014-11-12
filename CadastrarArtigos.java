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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

// Bordas
import javax.swing.border.EmptyBorder;

// ArryaList
import java.util.ArrayList;

public class CadastrarArtigos extends JFrame implements ActionListener
{
	// JPanel para inserir os elementos
	private JPanel panel;

	// Labels
	private JLabel titulo;
	private JLabel dataApresentacao;
	private JLabel horarioApresentacao;
	private JLabel evento;
	private JLabel edicao;
	private JLabel apresentador;

	// TextFields
	private JTextField in_titulo;
	private JTextField in_dataApresentacao;
	private JTextField in_horarioApresentacao;
	private JComboBox<String> in_evento;
	private JComboBox<String> in_edicao;

	// Buttons
	private JButton in_apresentador;
	private JButton cadastrar;
	private JButton cancelar;

	public CadastrarArtigos()
	{
		// Titulo da janela
		setTitle("Cadastrar Artigos");
		// Setting up close button
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initUI(String[] opcoes_evento, String[] opcoes_edicao)
	{
		// Criando os Paineis
		panel = new JPanel();
		// Criando o Layout
		panel.setLayout(new GridLayout(14, 0, 5, 5));
		// Adicionando o painel ao JFrame
		getContentPane().add(panel);

		// Criando as bordas
		EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);
		panel.setBorder(emptyBorder);

		// Inicializando os componentes
		titulo = new JLabel("Titulo:");
		dataApresentacao = new JLabel("Data da apresentacao:");
		horarioApresentacao = new JLabel("Horario da Apresentacao:");
		evento = new JLabel("Evento:");
		edicao = new JLabel("Edicao:");
		apresentador = new JLabel("Apresentador:");


		in_titulo = new JTextField(50);
		in_dataApresentacao = new JTextField(50);
		in_horarioApresentacao = new JTextField(50);
		in_evento = new JComboBox<String>(opcoes_evento);
		in_edicao = new JComboBox<String>(opcoes_edicao);

		// Initializing the arrays 
		in_apresentador = new JButton("Escolher apresentador");
		cadastrar = new JButton("Cadastrar");
		cancelar = new JButton("Cancelar");

		// Inserindo os labels e textfields e comboxes no panel
		panel.add(titulo);
		panel.add(in_titulo);

		panel.add(dataApresentacao);
		panel.add(in_dataApresentacao);

		panel.add(horarioApresentacao);
		panel.add(in_horarioApresentacao);

		panel.add(evento);
		panel.add(in_evento);

		panel.add(edicao);
		panel.add(in_edicao);

		panel.add(apresentador);
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

		CadastrarArtigos ui = new CadastrarArtigos();

		// initializing the UI
		ui.initUI(evento, edicao);
	}
}
