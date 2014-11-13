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

public class MenuPrincipal extends JFrame implements ActionListener
{
	// JPanel contem todos os elementos
	private JPanel panel;

	// Labels
	private JMenuBar menuBar;

	private JMenu menuGerenciar;
	private JMenu menuConsultar;

	private JMenuItem menuEvento;
	private JMenuItem menuEdicao;
	private JMenuItem menuPessoa;
	private JMenuItem menuArtigo;
	private JMenuItem menuPatrocinador;
	private JMenuItem menuPatrocinio;
	private JMenuItem menuDespesa;
	private JMenuItem menuAuxilio;

	public MenuPrincipal()
	{
		// Titulo da janela
		setTitle("Menu Principal");

		// Botao de fechar
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initUI()
	{
		// Criando panel
		panel = new JPanel();

		// Grid Layout
		panel.setLayout(new GridLayout(16, 0, 5, 5));
		panel.setPreferredSize(new Dimension(500, 500));
		
		//Adiciona JPanel ao frame
		getContentPane().add(panel);

		//Create the menu bar.
		menuBar = new JMenuBar();

		menuGerenciar = new JMenu("Gerenciar");
		menuConsultar = new JMenu("Consultas Avancada");

		menuEvento = new JMenuItem("Evento");
		menuEdicao = new JMenuItem("Edicao");
		menuPessoa = new JMenuItem("Pessoa");
		menuArtigo = new JMenuItem("Artigo");
		menuPatrocinador = new JMenuItem("Patrocinador");
		menuPatrocinio = new JMenuItem("Patrocinio");
		menuDespesa = new JMenuItem("Despesa");
		menuAuxilio = new JMenuItem("Auxilio");

		// inserir os menus na menubar
		menuBar.add(menuGerenciar);
		menuBar.add(menuConsultar);

		menuGerenciar.add(menuEvento);
		menuGerenciar.add(menuEdicao);
		menuGerenciar.add(menuPessoa);
		menuGerenciar.add(menuArtigo);
		menuGerenciar.add(menuPatrocinador);
		menuGerenciar.add(menuPatrocinio);
		menuGerenciar.add(menuDespesa);
		menuGerenciar.add(menuAuxilio);

		panel.add(menuBar);


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

		MenuPrincipal ui = new MenuPrincipal();
		ui.initUI();
	}
}
