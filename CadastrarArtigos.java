// Event Listners
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Grid layout
import java.awt.GridLayout;

// Componentes da interface
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

// Bordas
import javax.swing.border.EmptyBorder;
import java.sql.SQLException;

// ArryaList
import java.util.ArrayList;

public class CadastrarArtigos extends JDialog implements ActionListener
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
	private JTextField in_evento;
	private JTextField in_edicao;
	private JTextField in_apresentador;


	// Strings q contem os dados do evento, edicao, apresentador
	private String str_codEv;
	private String str_numEd;
	private String str_idApr;

	DBConnection dbcon;

	// Buttons
	private JButton buttonEventos;
	private JButton buttonEdicao;
	private JButton buttonApresentador;
	private JButton cadastrar;
	private JButton cancelar;

	public CadastrarArtigos(DBConnection dbcon)
	{
		// Titulo da janela
		setTitle("Cadastrar Artigos");
		// Setting up close button
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.dbcon = dbcon;
	}

	public void initUI()
	{
		this.setModal(true);
		// Criando os Paineis
		panel = new JPanel();
		// Criando o Layout
		panel.setLayout(new GridLayout(17, 0, 5, 5));
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
		in_evento = new JTextField(50);
		in_edicao = new JTextField(50);
		in_apresentador = new JTextField(50);


		// Initializing the arrays 
		buttonEventos = new JButton("Escolher Evento");
		buttonEdicao = new JButton("Escolher Edicao");
		buttonApresentador = new JButton("Escolher apresentador");

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
    	in_evento.setEditable(false);
		panel.add(buttonEventos);

		panel.add(edicao);
		panel.add(in_edicao);
    	in_edicao.setEditable(false);
		panel.add(buttonEdicao);

		panel.add(apresentador);
		panel.add(in_apresentador);
    	in_apresentador.setEditable(false);
		panel.add(buttonApresentador);

		// Adicionando botoes no painel
		panel.add(cadastrar);
		panel.add(cancelar);

		// Adicionando os eventListeners
		cadastrar.addActionListener(this);
		cancelar.addActionListener(this);
		buttonEventos.addActionListener(this);
		buttonEdicao.addActionListener(this);
		buttonApresentador.addActionListener(this);

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
		else if(e.getActionCommand().equals(buttonEventos.getText()))
		{
			onClickEventos();
		}
		else if(e.getActionCommand().equals(buttonEdicao.getText()))
		{
			onClickEdicoes();
		}
		else if(e.getActionCommand().equals(buttonApresentador.getText()))
		{
			onClickApresentador();
		}
	}

	// Botao cadastrar
	public void onClickCadastrar()
	{
		String idArt = "SQ_idArt_artigo.NEXTVAL";
		String tituloArt = "'" + in_titulo.getText() + "'";
		String dataApreArt = "TO_DATE(" + "'" + in_dataApresentacao.getText() + "'," + "'DD/MM/YYYY')";
		String horaApresArt = "TO_DATE(" + "'" + in_horarioApresentacao.getText() + "'," + "'HH24:MI')";
		String codEv = str_codEv;
		String numEd = str_numEd;
		String idApr = str_idApr;

		// codEv, numEd, descricaoEd, dataInicioEd, dataFimEd, localEd, taxaEd, saldoFinanceiroEd, qtdArtigosApresentadosEd
		String query = "INSERT INTO artigo VALUES(" + idArt + "," + tituloArt + "," + dataApreArt + "," + horaApresArt + ", " 
			+ codEv + "," + numEd + "," + idApr + ")";

		System.out.println(query);

		try{
			dbcon.executarInsert(query);
			JOptionPane.showMessageDialog(null, "Artigo Cadastrado com sucesso");
			setVisible(false);
			dispose();
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}	
	}

	// Botao cancelar
	public void onClickCancelar()
	{
		setVisible(false);
		dispose();	
	}

	public void onClickEventos(){

		// Valores que serao passados para popular a tabela do JDialog, os resultados obtidos estao nessa ordem e podem ser acessados
		// por meio de miniGerenciador.resultados().get(i);
		String[] colunas = {"Codigo", "Nome", "Descricao", "Website", "Total de Artigos"};		
		String[][] dados = this.dbcon.CarregaDados("EVENTO");   

		// Cria JDialog com a tabela que o usuario ira selecionar
		MiniGerenciador miniGerenciador =  new MiniGerenciador(this, dados, colunas);
		
		// Caso o usuario clicou em ok o resultado esta salvo em miniGerenciador.resutlado
		if(miniGerenciador.resultado() != null){
			System.out.println(miniGerenciador.resultado());
			// Coloca nome do evento no JTextField que o represnta
			in_evento.setText(miniGerenciador.resultado().get(1).toString());
			str_codEv  = miniGerenciador.resultado().get(0).toString();
			miniGerenciador.dispose();
		}
	}

	public void onClickEdicoes(){

		if(str_codEv == null)
		{
			JOptionPane.showMessageDialog(null, "Evento nao escolhidos", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			// Valores que serao passados para popular a tabela do JDialog, os resultados obtidos estao nessa ordem e podem ser acessados
			// por meio de miniGerenciador.resultados().get(i);
			String[] colunas = {"Codigo", "Numero", "Descricao", "Data Inicio", "Data Fim", "Local", "Saldo Financeiro"};		
			String[][] dados = this.dbcon.CarregaDados("EDICAO", " WHERE codEv = " + str_codEv);   

			// Cria JDialog com a tabela que o usuario ira selecionar
			MiniGerenciador miniGerenciador =  new MiniGerenciador(this, dados, colunas);
			
			// Caso o usuario clicou em ok o resultado esta salvo em miniGerenciador.resutlado
			if(miniGerenciador.resultado() != null){
				System.out.println(miniGerenciador.resultado());
				// Coloca nome do evento no JTextField que o represnta
				in_edicao.setText(miniGerenciador.resultado().get(2).toString());
				str_numEd  = miniGerenciador.resultado().get(1).toString();
				miniGerenciador.dispose();
			}
		}
	}

	public void onClickApresentador(){

		if(str_codEv == null && str_numEd == null)
		{
			JOptionPane.showMessageDialog(null, "Evento ou Edicao nao escolhidos", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			// Valores que serao passados para popular a tabela do JDialog, os resultados obtidos estao nessa ordem e podem ser acessados
			// por meio de miniGerenciador.resultados().get(i);
			String[] colunas = {"codEv", "numEd", "idPart", "Nome", "tiApresentador"};		
			String[][] dados = this.dbcon.CarregaDados("formataSaidaInscrito", "WHERE codEv = " + str_codEv + " AND numEd = " + str_numEd + " AND tipoApresentador = 1");   

			// Cria JDialog com a tabela que o usuario ira selecionar
			MiniGerenciador miniGerenciador =  new MiniGerenciador(this, dados, colunas);
			
			// Caso o usuario clicou em ok o resultado esta salvo em miniGerenciador.resutlado
			if(miniGerenciador.resultado() != null){
				System.out.println(miniGerenciador.resultado());
				// Coloca nome do evento no JTextField que o represnta
				in_apresentador.setText(miniGerenciador.resultado().get(3).toString());
				str_idApr  = miniGerenciador.resultado().get(2).toString();
				miniGerenciador.dispose();
			}
		}
	}
}
