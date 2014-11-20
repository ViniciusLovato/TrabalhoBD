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

        String[][] dados;

		// Checking if it is button okay
		if(e.getActionCommand().equals(menuEvento.getText()))
		{

			dados = dbcon.CarregaDados("EVENTO");   
			GerenciadorEventos gerenciadorEventos = new GerenciadorEventos(dados);
			//gerenciadorEventos.configurar(parametros, colunas, dados);
			gerenciadorEventos.setVisible(true);
		}
		else if(e.getActionCommand().equals(menuEdicao.getText())){
			dados = dbcon.CarregaDados("EDICAO");   
			GerenciadorEdicao gerenciadorEdicao = new GerenciadorEdicao(dados);
			gerenciadorEdicao.setVisible(true);
		}
		else if(e.getActionCommand().equals(menuPessoa.getText())){
			dados = dbcon.CarregaDados("PESSOA");   
			GerenciadorPessoa gerenciadorPessoa = new GerenciadorPessoa(dados);
			gerenciadorPessoa.setVisible(true);
		}
		else if(e.getActionCommand().equals(menuArtigo.getText())){
			dados = dbcon.CarregaDados("ARTIGO");   
			GerenciadorArtigo gerenciadorArtigo = new GerenciadorArtigo(dados);
			gerenciadorArtigo.setVisible(true);
		}
		else if(e.getActionCommand().equals(menuPatrocinador.getText())){
			dados = dbcon.CarregaDados("PATROCINADOR");   
			GerenciadorPatrocinador gerenciadorPatrocinador = new GerenciadorPatrocinador(dados);
			gerenciadorPatrocinador.setVisible(true);
		}
		else if(e.getActionCommand().equals(menuPatrocinio.getText())){
			dados = dbcon.CarregaDados("PATROCINIO");   
			GerenciadorPatrocinio gerenciadorPatrocinio = new GerenciadorPatrocinio(dados);
			gerenciadorPatrocinio.setVisible(true);
		}
		else if(e.getActionCommand().equals(menuDespesa.getText())){
			dados = dbcon.CarregaDados("DESPESA");   
			GerenciadorDespesa gerenciadorDespesa = new GerenciadorDespesa(dados);
			gerenciadorDespesa.setVisible(true);
		}
		else if(e.getActionCommand().equals(menuAuxilio.getText())){
			dados = dbcon.CarregaDados("AUXILIO");   
			GerenciadorAuxilio gerenciadorAuxilio = new GerenciadorAuxilio(dados);
			gerenciadorAuxilio.setVisible(true);
		}
	}

	public static void main(String args[])
	{
		MenuPrincipal menu = new MenuPrincipal();
		menu.initUI();
	}
}
