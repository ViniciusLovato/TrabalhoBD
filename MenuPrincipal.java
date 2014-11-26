// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

// Grid layout
import java.awt.GridLayout;

// Componetes basicos para interface
import javax.swing.*;
import java.awt.Dimension;
import java.text.ParseException;
import java.awt.BorderLayout;

// Bordas
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

// ArryaList
import java.util.ArrayList;

public class MenuPrincipal extends JFrame implements ActionListener
{
	// JPanel contem todos os elementos
	private JPanel panel;
	private JPanel outterMenu;
	private JPanel innerMenu;
	private JPanel footer;

	// Barra de menu
	private JMenuBar menuBar;

	// Cada componente menu da barra principal
	private JMenu menuGerenciar;
	private JMenu menuConsultar;
	private JMenu menuRelatorio;

	// Submenus que serao inseridos dentro de cada menu
	private JMenuItem menuEvento;
	private JMenuItem menuEdicao;
	private JMenuItem menuPessoa;
	private JMenuItem menuInscrever;
	private JMenuItem menuOrganiza;
	private JMenuItem menuArtigo;
	private JMenuItem menuEscrever;
	private JMenuItem menuPatrocinador;
	private JMenuItem menuPatrocinio;
	private JMenuItem menuDespesa;
	private JMenuItem menuAuxilio;

	// Sub menus das consultas especiais
	private JMenuItem menuConsulta1;
	private JMenuItem menuConsulta2;

	// sub menus de relatorios
	private JMenuItem menuRelatorio1;
	private JMenuItem menuRelatorio2;


	// Campos para logar
	private JLabel usuario;
	private JLabel senha;
	private JLabel endereco;

	private JLabel desenvolvido;

	private JTextField in_usuario;
	private JPasswordField in_senha;
	private JTextField in_endereco;

	private JButton conectar;
	private JButton desconectar;

    private DBConnection dbcon;


	// Janelas que podem ser abertas

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
		innerMenu = new JPanel();
		outterMenu = new JPanel();
		footer = new JPanel();

		// Grid Layout
		panel.setLayout(new BorderLayout());

		outterMenu.setBorder(new EmptyBorder(50, 50, 50, 50));

		TitledBorder title;
		title = BorderFactory.createTitledBorder("Login");
		title.setTitleJustification(TitledBorder.CENTER);

		innerMenu.setLayout(new GridLayout(4, 2, 5, 5));
		innerMenu.setBorder(title);

		// Adiciona JPanel ao frame
		getContentPane().add(panel);

		// Cria a barra de menu
		menuBar = new JMenuBar();

		// Menus da barra superior da janela
		menuGerenciar = new JMenu("Gerenciar");
		menuConsultar = new JMenu("Consultas Avancada");
		menuRelatorio = new JMenu("Gerar Relatorios");

		// Submenus dentro dos menus da barra superior (Gerenciar)
		menuEvento = new JMenuItem("Evento");
		menuEdicao = new JMenuItem("Edicao");
		menuPessoa = new JMenuItem("Pessoa");
		menuInscrever = new JMenuItem("Inscrever Pessoa");
		menuOrganiza = new JMenuItem("Cadastrar Organizador");
		menuArtigo = new JMenuItem("Artigo");
		menuEscrever = new JMenuItem("Registrar Escritor");
		menuPatrocinador = new JMenuItem("Patrocinador");
		menuPatrocinio = new JMenuItem("Patrocinio");
		menuDespesa = new JMenuItem("Despesa");
		menuAuxilio = new JMenuItem("Auxilio");

		menuConsulta1 = new JMenuItem("Consulta 1");
		menuConsulta2 = new JMenuItem("Consulta 2");

		menuRelatorio1 = new JMenuItem("Relatorio 1");
		menuRelatorio2 = new JMenuItem("Relatorio 2");

		usuario = new JLabel("Usuario: ");
		senha = new JLabel("Senha: ");
		endereco = new JLabel("Endereco: ");

		desenvolvido = new JLabel("Desenvolvido por: Matheus Compri e Vinicius Lovato");

		conectar = new JButton("Conectar");
		desconectar = new JButton("Desconectar");

		in_usuario = new JTextField(20);
		in_senha = new JPasswordField(20);
	 	in_endereco = new JTextField(20); 

		// inserir os menus na menubar
		menuBar.add(menuGerenciar);
		menuBar.add(menuConsultar);
		menuBar.add(menuRelatorio);

		// Insere os submenus no menu Gerenciar
		menuGerenciar.add(menuEvento);
		menuGerenciar.add(menuEdicao);
		menuGerenciar.add(menuPessoa);
		menuGerenciar.add(menuInscrever);
		menuGerenciar.add(menuOrganiza);
		menuGerenciar.add(menuArtigo);
		menuGerenciar.add(menuEscrever);
		menuGerenciar.add(menuPatrocinador);
		menuGerenciar.add(menuPatrocinio);
		menuGerenciar.add(menuDespesa);
		menuGerenciar.add(menuAuxilio);

		menuConsultar.add(menuConsulta1);
		menuConsultar.add(menuConsulta2);

		menuRelatorio.add(menuRelatorio1);
		menuRelatorio.add(menuRelatorio2);

		// Adiciona os listeners ao botoes de submenu
		menuEvento.addActionListener(this);
		menuEdicao.addActionListener(this);
		menuPessoa.addActionListener(this);
		menuInscrever.addActionListener(this);
		menuOrganiza.addActionListener(this);
		menuArtigo.addActionListener(this);
		menuEscrever.addActionListener(this);
		menuPatrocinador.addActionListener(this);
		menuPatrocinio.addActionListener(this);
		menuDespesa.addActionListener(this);
		menuAuxilio.addActionListener(this);

		menuConsulta1.addActionListener(this);
		menuConsulta2.addActionListener(this);
		menuRelatorio1.addActionListener(this);
		menuRelatorio2.addActionListener(this);

		conectar.addActionListener(this);
		desconectar.addActionListener(this);

		innerMenu.add(usuario);
		innerMenu.add(in_usuario);

		innerMenu.add(senha);
		innerMenu.add(in_senha);

		innerMenu.add(endereco);
		innerMenu.add(in_endereco);

		innerMenu.add(conectar);
		innerMenu.add(desconectar);

		footer.add(desenvolvido);

		panel.add(menuBar, BorderLayout.PAGE_START);
		panel.add(outterMenu, BorderLayout.CENTER);
		outterMenu.add(innerMenu);

		panel.add(footer, BorderLayout.SOUTH);

		// Desabilita botoes ate o usuario logar no banco de dados
		menuGerenciar.setEnabled(false);
		menuConsultar.setEnabled(false);
		menuRelatorio.setEnabled(false);

		// Dados de login padrao
		in_usuario.setText("a7151885");
		in_senha.setText("a7151885");
		in_endereco.setText("grad.icmc.usp.br:15214:orcl14");

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
		else if(e.getActionCommand().equals(menuInscrever.getText())){
			GerenciadorInscrito gerenciadorEscreve = new GerenciadorInscrito(dbcon);
			gerenciadorEscreve.setVisible(true);
		}
		else if(e.getActionCommand().equals(menuOrganiza.getText())){
			GerenciadorOrganizador gerenciadorOrganizador = new GerenciadorOrganizador(dbcon);
			gerenciadorOrganizador.setVisible(true);
		}
		else if(e.getActionCommand().equals(menuArtigo.getText())){
			GerenciadorArtigo gerenciadorArtigo = new GerenciadorArtigo(dbcon);
			gerenciadorArtigo.setVisible(true);
		}
		else if(e.getActionCommand().equals(menuEscrever.getText())){
			GerenciadorEscreve gerenciadorEscreve = new GerenciadorEscreve(dbcon);
			gerenciadorEscreve.setVisible(true);
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
		else if(e.getActionCommand().equals(menuEscrever.getText())){
			GerenciadorEscreve gerenciadorEscreve = new GerenciadorEscreve(dbcon);
			gerenciadorEscreve.setVisible(true);
		}
		else if(e.getActionCommand().equals(desconectar.getText())){
			desconectar();
		}
		else if(e.getActionCommand().equals(conectar.getText())){
			if(in_endereco.getText().equals("") || senha.getText().equals("") || in_usuario.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Dados invalidos", "Erro", JOptionPane.ERROR_MESSAGE);

			}else{
				conectar(in_usuario.getText(), new String(in_senha.getPassword()).trim() , in_endereco.getText());
			}
		}
	}

	public void desconectar(){
		try
		{
			dbcon.disconnect();
			menuGerenciar.setEnabled(false);
			menuConsultar.setEnabled(false);
			menuRelatorio.setEnabled(false);

			in_usuario.setEnabled(true);
			in_senha.setEnabled(true);
			in_endereco.setEnabled(true);

			conectar.setEnabled(true);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Nao foi possivel desconectar", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}


	public void conectar(String user, String pwd, String endereco){
        try
        {	
        	// JOptionPane optionPane = new JOptionPane("Conectando ao banco ...");

            dbcon = new DBConnection(user, pwd, endereco);
            if(dbcon.isNull())
            {
				JOptionPane.showMessageDialog(null, "Erro ao conectar no banco de dados", "Erro", JOptionPane.ERROR_MESSAGE);
		       // System.exit(0); 
            }
            else
            {
            	// Habilita os campos da menu bar para poder acessar o banco
    			menuGerenciar.setEnabled(true);
				menuConsultar.setEnabled(true);
				menuRelatorio.setEnabled(true);

				// Desabilita os campos de conexao no Bd para evitar conlfitos
				in_usuario.setEnabled(false);
				in_senha.setEnabled(false);
				in_endereco.setEnabled(false);

				// Desailita botao de conectar
				conectar.setEnabled(false);

            }
        }
        catch(SQLException e)
        {
			if(e.getErrorCode() == 1017){
				JOptionPane.showMessageDialog(null, "Senha ou login invalidos", "Erro", JOptionPane.ERROR_MESSAGE);
			}
        }

	}

	public static void main(String args[])
	{
		MenuPrincipal menu = new MenuPrincipal();
		menu.initUI();
	}
}
