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

	// Barra de menu
	private JMenuBar menuBar;

	// Cada componente menu da barra principal
	private JMenu menuGerenciar;
	private JMenu menuConsultar;

	// Submenus que serao inseridos dentro de cada menu
	private JMenuItem menuEvento;
	private JMenuItem menuEdicao;
	private JMenuItem menuPessoa;
	private JMenuItem menuArtigo;
	private JMenuItem menuPatrocinador;
	private JMenuItem menuPatrocinio;
	private JMenuItem menuDespesa;
	private JMenuItem menuAuxilio;

    private DBConnection dbcon;


	// Janelas que podem ser abertas

	public MenuPrincipal()
	{
		// Titulo da janela
		setTitle("Menu Principal");

		// Botao de fechar
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        try
        {
            dbcon = new DBConnection();
            if(dbcon.isNull())
            {
		       // JOptionPane.showMessageInfo(null, "Erro ao conectar no banco de dados");
		       System.exit(0); 
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
	}

	public void initUI()
	{
		// Criando panel
		panel = new JPanel();

		// Grid Layout
		panel.setLayout(new GridLayout(16, 0, 5, 5));
		panel.setPreferredSize(new Dimension(500, 500));
		
		// Adiciona JPanel ao frame
		getContentPane().add(panel);

		// Cria a barra de menu
		menuBar = new JMenuBar();

		// Menus da barra superior da janela
		menuGerenciar = new JMenu("Gerenciar");
		menuConsultar = new JMenu("Consultas Avancada");

		// Submenus dentro dos menus da barra superior (Gerenciar)
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

		// Insere os submenus no menu Gerenciar
		menuGerenciar.add(menuEvento);
		menuGerenciar.add(menuEdicao);
		menuGerenciar.add(menuPessoa);
		menuGerenciar.add(menuArtigo);
		menuGerenciar.add(menuPatrocinador);
		menuGerenciar.add(menuPatrocinio);
		menuGerenciar.add(menuDespesa);
		menuGerenciar.add(menuAuxilio);

		// Adiciona os listeners ao botoes de submenu
		menuEvento.addActionListener(this);
		menuEdicao.addActionListener(this);
		menuPessoa.addActionListener(this);
		menuArtigo.addActionListener(this);
		menuPatrocinador.addActionListener(this);
		menuPatrocinio.addActionListener(this);
		menuDespesa.addActionListener(this);
		menuAuxilio.addActionListener(this);

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
		System.out.println(e.getActionCommand());

		// Checking if it is button okay
		if(e.getActionCommand().equals(menuEvento.getText()))
		{

			GerenciadorEventos gerenciadorEventos = new GerenciadorEventos(dbcon);
			gerenciadorEventos.setVisible(true);
		}
		else if(e.getActionCommand().equals(menuEdicao.getText())){
			GerenciadorEdicao gerenciadorEdicao = new GerenciadorEdicao(dbcon);
			gerenciadorEdicao.setVisible(true);
		}
		else if(e.getActionCommand().equals(menuPessoa.getText())){
			GerenciadorPessoa gerenciadorPessoa = new GerenciadorPessoa(dbcon);
			gerenciadorPessoa.setVisible(true);
		}
		else if(e.getActionCommand().equals(menuArtigo.getText())){
			GerenciadorArtigo gerenciadorArtigo = new GerenciadorArtigo(dbcon);
			gerenciadorArtigo.setVisible(true);
		}
		else if(e.getActionCommand().equals(menuPatrocinador.getText())){
			GerenciadorPatrocinador gerenciadorPatrocinador = new GerenciadorPatrocinador(dbcon);
			gerenciadorPatrocinador.setVisible(true);
		}
		else if(e.getActionCommand().equals(menuPatrocinio.getText())){
			GerenciadorPatrocinio gerenciadorPatrocinio = new GerenciadorPatrocinio(dbcon);
			gerenciadorPatrocinio.setVisible(true);
		}
		else if(e.getActionCommand().equals(menuDespesa.getText())){
			GerenciadorDespesa gerenciadorDespesa = new GerenciadorDespesa(dbcon);
			gerenciadorDespesa.setVisible(true);
		}
		else if(e.getActionCommand().equals(menuAuxilio.getText())){
			GerenciadorAuxilio gerenciadorAuxilio = new GerenciadorAuxilio(dbcon);
			gerenciadorAuxilio.setVisible(true);
		}
	}

	public static void main(String args[])
	{
		MenuPrincipal menu = new MenuPrincipal();
		menu.initUI();
	}
}
