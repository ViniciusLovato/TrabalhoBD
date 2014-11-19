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

public class CadastrarEdicao extends JFrame implements ActionListener
{
	// JPanel contem todos os elementos
	private JPanel panel;

	// Labels
	private JLabel evento;
	private JLabel descricao;
	private JLabel data_inicio;
	private JLabel data_fim;
	private JLabel local;
	private JLabel taxa_inscricao;

	// Campos de texto
	private JTextField in_evento;
	private JTextField in_descricao;
	private JFormattedTextField in_data_inicio; //Campo de texto formatado para data
	private JFormattedTextField in_data_fim;	// Campo de texto formatado para data
	private JTextField in_local;
	private JTextField in_taxa_inscricao;

	// Combobox que contem os eventos
	private JComboBox<String> in_eventos;

	// Botoes
	private JButton cadastrar;
	private JButton cancelar;


	public CadastrarEdicao()
	{
		// Titulo da janela
		setTitle("Cadastrar Edicao");

		// Botao de fechar
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initUI(String[] opcoes_ev) throws ParseException
	{
		// Criando panel
		panel = new JPanel();

		// Grid Layout
		panel.setLayout(new GridLayout(14, 0, 5, 5));
		panel.setPreferredSize(new Dimension(500, 500));
		
		//Adiciona JPanel ao frame
		getContentPane().add(panel);

		// Cria e aplica a borda
		EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);
		panel.setBorder(emptyBorder);


		// Criando Labels
		evento = new JLabel("Evento:");
		descricao = new JLabel("Descricao:");
		data_inicio = new JLabel("Data Inicio");
		data_fim = new JLabel("Data Fim");
		local = new JLabel("Local");
		taxa_inscricao = new JLabel("Taxa de Incricao");

		// Mascara para formatar dados
 		MaskFormatter mf1 = new MaskFormatter("##/##/####");
    	mf1.setPlaceholderCharacter('_');

    	// Campos para preencher os dados
    	in_eventos = new JComboBox<String>(opcoes_ev);
	    in_descricao = new JTextField(70);
	    in_data_inicio = new JFormattedTextField(mf1);
	    in_data_fim = new JFormattedTextField(mf1);
	    in_descricao = new JTextField(70);
		in_local = new JTextField(70);
		in_taxa_inscricao = new JTextField(70);

		// Cria botoes
		cadastrar = new JButton("Cadastrar");
		cancelar = new JButton("Cancelar");

		// Adiciona botoes, campos de textos e labels ao panel
		panel.add(evento);
		panel.add(in_eventos);

		panel.add(descricao);
		panel.add(in_descricao);
		
		panel.add(data_inicio);
		panel.add(in_data_inicio);
		
		panel.add(data_fim);
		panel.add(in_data_fim);
		
		panel.add(local);
		panel.add(in_local);
		
		panel.add(taxa_inscricao);
		panel.add(in_taxa_inscricao);

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
		setVisible(false);
		dispose();	
	}
}
