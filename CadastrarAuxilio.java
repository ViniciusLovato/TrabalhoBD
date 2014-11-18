// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Grid layout
import java.awt.GridLayout;

// Componetes basicos para interface
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import java.awt.Dimension;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import java.text.ParseException;

// Bordas
import javax.swing.border.EmptyBorder;

// ArryaList
import java.util.ArrayList;

public class CadastrarAuxilio extends JFrame implements ActionListener
{
	// JPanel contem todos os elementos
	private JPanel panel;

	// Labels
	private JLabel evento;
	private JLabel edicao;
	private JLabel patrocinador;
	private JLabel apresentador;
	private JLabel data;
	private JLabel valor;
	private JLabel tipo;

	// Campos de texto
	private JComboBox<String> in_evento;	
	private JComboBox<String> in_edicao;
	private JComboBox<String> in_patrocinador;
	private JComboBox<String> in_apresentador;


	private JFormattedTextField in_data; //Campo de texto formatado para data
	private JTextField in_valor;
	private JComboBox<String> in_tipo;


	// Botoes
	private JButton cadastrar;
	private JButton cancelar;


	public CadastrarAuxilio()
	{
		// Titulo da janela
		setTitle("Cadastrar Despesa");

		// Botao de fechar
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initUI(String[] opcoes_pat, String[] opcoes_ev, String[] opcoes_ed, String[] opcoes_apr, String[] opcoes_tipo) throws ParseException
	{
		// Criando panel
		panel = new JPanel();

		// Grid Layout
		panel.setLayout(new GridLayout(16, 0, 5, 5));
		panel.setPreferredSize(new Dimension(500, 500));
		
		//Adiciona JPanel ao frame
		getContentPane().add(panel);

		// Cria e aplica a borda
		EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);
		panel.setBorder(emptyBorder);

		evento = new JLabel("Evento");
		edicao = new JLabel("Edicao");
		patrocinador = new JLabel("Patrocinador");
		apresentador = new JLabel("apresentador");
		data = new JLabel("Data");
		valor = new JLabel("Valor");
		tipo = new JLabel("Tipo");


    	// Campos para preencher os dados
    	in_evento = new JComboBox<String>(opcoes_ev);
    	in_edicao = new JComboBox<String>(opcoes_ed);
    	in_patrocinador = new JComboBox<String>(opcoes_pat);
    	in_apresentador = new JComboBox<String>(opcoes_apr);

		// Mascara para formatar dados
 		MaskFormatter mf1 = new MaskFormatter("##/##/####");
    	mf1.setPlaceholderCharacter('_');

    	in_valor = new JTextField(70);
    	in_data = new JFormattedTextField(mf1);
    	in_tipo = new JComboBox<String>(opcoes_tipo);

		// Cria botoes
		cadastrar = new JButton("Cadastrar");
		cancelar = new JButton("Cancelar");

		// Adiciona botoes, campos de textos e labels ao panel


		panel.add(evento);
		panel.add(in_evento);
		
		panel.add(edicao);
		panel.add(in_edicao);

		panel.add(patrocinador);
		panel.add(in_patrocinador);

		panel.add(apresentador);
		panel.add(in_apresentador);

		panel.add(data);
		panel.add(in_data);
		
		panel.add(valor);
		panel.add(in_valor);

		panel.add(tipo);
		panel.add(in_tipo);


		panel.add(cadastrar);
		panel.add(cancelar);

		cadastrar.addActionListener(this);
		cancelar.addActionListener(this);

		// Tamanho da janela sera suficiente para conter todos os componetes
		pack();

		// Centralizando janela
		setLocationRelativeTo(null);

		// Janela agora visivel
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		// Caso apertou o obotao ok, salva no banco de dados
		if(e.getActionCommand().equals(cadastrar.getText()))
		{
			onClickOkay();
		}
		// Caso nao, cancela toda a acao e nao salvas
		else if(e.getActionCommand().equals(cancelar.getText()))
		{
			onClickCancel();
		}
	}

	// Metodo do botao de cadastrar, devera salva no banco de dados
	public void onClickOkay()
	{
		System.exit(0);
	}

	// Metodo do botao que cancela a acao
	public void onClickCancel()
	{
		System.exit(0);
	}

	public static void main(String args[]) throws ParseException
	{
		String[] pat = {"Elma Chip", "Cutrale", "USP"};
		String[] eventos = {"Comic con", "Churras da casa do brunao"};
		String[] edicao = {"1 edicao", "2 edicao"};
		String[] pessoa = {"betao", "predinho da zueira", "jao droptable"};
		String[] tipo = {"Da bagunca", "da zueira", "perdigao" };

		CadastrarAuxilio ui = new CadastrarAuxilio();
		ui.initUI(pat, eventos, edicao, pessoa, tipo);
	}
}
