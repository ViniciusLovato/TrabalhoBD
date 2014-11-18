// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Grid layout
import java.awt.GridLayout;

// Componetes basicos para interface
import javax.swing.*;

import java.awt.Dimension;


import java.text.ParseException;

// Bordas
import javax.swing.border.EmptyBorder;

// ArryaList
import java.util.ArrayList;

public class Gerenciador extends JFrame implements ActionListener
{
	// JPanel contem todos os elementos
	private JPanel panel;

	// Tabela
	private JLabel label1;
	private JLabel label2;


	private JTable table;

	public Gerenciador()
	{
		// Titulo da janela
		setTitle("Menu Principal");

		// Botao de fechar
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void configurar(String[] colunas, Object[][] dados)
	{
		// Criando panel
		panel = new JPanel();

		// Grid Layout
		panel.setLayout(new GridLayout(3, 0, 5, 5));
		//setSize(new Dimension(500, 500));
		
		//Adiciona JPanel ao frame
		getContentPane().add(panel);

		table = new JTable(dados, colunas);
		table.setFillsViewportHeight(true);

		label1 = new JLabel("bagunca");
		label2 = new JLabel("roubo");

		panel.add(label1);
		panel.add(label2);

		panel.add(table);

		// Tamanho da janela sera suficiente para conter todos os componetes
		pack();

		// Centralizando janela
		setLocationRelativeTo(null);

		// Janela agora visivel
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		System.out.println(e.getSource());
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

	public static void main(String args[])
	{
		String[] colunas = {"bagunca", "zueira", "bandejao", "5:15"};
		String[][] dados = {
			{"roubo", "da boa", "picadinho", "merenda time"},
			{"roubo", "never ends", "picadinho2", "horario"}
		};

		Gerenciador ui = new Gerenciador();
		ui.configurar(colunas, dados);
	}
}
