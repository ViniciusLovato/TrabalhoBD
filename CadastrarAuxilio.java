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

import javax.swing.JOptionPane;
import java.sql.SQLException;

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
	private JTextField in_evento;	
	private JTextField in_edicao;
	private JTextField in_patrocinador;
	private JTextField in_apresentador;


	private JFormattedTextField in_data; //Campo de texto formatado para data
	private JTextField in_valor;
	private JComboBox<String> in_tipo;

	private DBConnection dbcon;

	// Botoes
	private JButton buttonPatrocinio;
	private JButton buttonApresentador;
	private JButton buttonEvento;
	private JButton buttonEdicao;
	private JButton cadastrar;
	private JButton cancelar;

	private String str_cnpjPat;
	private String str_codEv;
	private String str_numEd;
	private String str_idApr;


	public CadastrarAuxilio(DBConnection dbcon)
	{
		// Titulo da janela
		setTitle("Cadastrar Auxilio");

		// Botao de fechar
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.dbcon = dbcon;
	}

	public void initUI() throws ParseException
	{
		// Criando panel
		panel = new JPanel();

		// Grid Layout
		panel.setLayout(new GridLayout(20, 0, 5, 5));
		panel.setPreferredSize(new Dimension(500, 500));
		
		//Adiciona JPanel ao frame
		getContentPane().add(panel);

		// Cria e aplica a borda
		EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);
		panel.setBorder(emptyBorder);

		evento = new JLabel("Evento");
		edicao = new JLabel("Edicao");
		patrocinador = new JLabel("Patrocinio");
		apresentador = new JLabel("apresentador");
		data = new JLabel("Data");
		valor = new JLabel("Valor");
		tipo = new JLabel("Tipo");


    	// Campos para preencher os dados
    	in_evento = new JTextField(50);
    	in_edicao = new JTextField(50);
    	in_patrocinador = new JTextField(50);
    	in_apresentador = new JTextField(50);

		// Mascara para formatar dados
 		MaskFormatter mf1 = new MaskFormatter("##/##/####");
    	mf1.setPlaceholderCharacter('_');

    	in_valor = new JTextField(70);
    	in_data = new JFormattedTextField(mf1);
    	String[] opcoes_tipo = {"Hospedagem", "Moradia", "Alimentacao"};
    	in_tipo = new JComboBox<String>(opcoes_tipo);

		// Cria botoes
		buttonApresentador = new JButton("Selecionar Apresentador");
		buttonPatrocinio = new JButton("Selecionar Patrocinio");
		buttonEvento = new JButton("Selecionar Evento");
		buttonEdicao = new JButton("Selecionar Edicao");

		cadastrar = new JButton("Cadastrar");
		cancelar = new JButton("Cancelar");

		// Adiciona botoes, campos de textos e labels ao panel


		panel.add(evento);
		panel.add(in_evento);
		in_evento.setEditable(false);
		panel.add(buttonEvento);

		panel.add(edicao);
		panel.add(in_edicao);
		in_edicao.setEditable(false);
		panel.add(buttonEdicao);

		panel.add(patrocinador);
		panel.add(in_patrocinador);
		in_patrocinador.setEditable(false);
		panel.add(buttonPatrocinio);

		panel.add(apresentador);
		panel.add(in_apresentador);
		in_apresentador.setEditable(false);
		panel.add(buttonApresentador);


		panel.add(data);
		panel.add(in_data);
		
		panel.add(valor);
		panel.add(in_valor);

		panel.add(tipo);
		panel.add(in_tipo);


		panel.add(cadastrar);
		panel.add(cancelar);

		buttonEvento.addActionListener(this);
		buttonEdicao.addActionListener(this);
		buttonApresentador.addActionListener(this);
		buttonPatrocinio.addActionListener(this);

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
		else if(e.getActionCommand().equals(buttonPatrocinio.getText()))
		{
			onClickPatrocinio();
		}
		else if(e.getActionCommand().equals(buttonEvento.getText()))
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

	// Metodo do botao de cadastrar, devera salva no banco de dados
	public void onClickOkay()
	{
		String cnpjPat = str_cnpjPat;
		String codEv = str_codEv;
		String numEd = str_numEd;
		String idApr = str_idApr;
		String valorAux = in_valor.getText();
		String dataAux = "TO_DATE(" + "'" + in_data.getText() + "'," + "'DD/MM/YYYY')";
		String tipoAux = "'" + in_tipo.getSelectedItem().toString() + "'";

		// codEv, numEd, descricaoEd, dataInicioEd, dataFimEd, localEd, taxaEd, saldoFinanceiroEd, qtdArtigosApresentadosEd
		String query = "INSERT INTO auxilio VALUES(" + cnpjPat + ", " + codEv + ", " + numEd + ", " + codEv + ", " + numEd + ", "  
			+ idApr + ", " + valorAux + ", " + dataAux + ", " + tipoAux + ")";

		System.out.println(query);

		try{
			dbcon.executarInsert(query);
			JOptionPane.showMessageDialog(null, "Auxilio Cadastrado com sucesso");
			setVisible(false);
			dispose();
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}	
	}

	// Metodo do botao que cancela a acao
	public void onClickCancel()
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
			JOptionPane.showMessageDialog(null, "Evento nao escolhido", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			// Valores que serao passados para popular a tabela do JDialog, os resultados obtidos estao nessa ordem e podem ser acessados
			// por meio de miniGerenciador.resultados().get(i);
			String[] colunas = {"Codigo", "Numero", "Descricao", "Data Inicio", "Data Fim", "Local", "Saldo Financeiro"};		
			String[][] dados = this.dbcon.CarregaDados("EDICAO");   

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

	public void onClickPatrocinio(){

		if(str_codEv == null || str_numEd == null)
		{
			JOptionPane.showMessageDialog(null, "Evento ou Edicao nao escolhidos", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			// Valores que serao passados para popular a tabela do JDialog, os resultados obtidos estao nessa ordem e podem ser acessados
			// por meio de miniGerenciador.resultados().get(i);
			String[] colunas = {"CNPJ", "codEv", "numEd", "Valor Patrocinio", "Saldo Patrocinio", "dataPat"};	
			String[][] dados = this.dbcon.CarregaDados("PATROCINIO", "WHERE codEv = " + str_codEv + " AND numEd = " + str_numEd);   

			// Cria JDialog com a tabela que o usuario ira selecionar
			MiniGerenciador miniGerenciador =  new MiniGerenciador(this, dados, colunas);
			
			// Caso o usuario clicou em ok o resultado esta salvo em miniGerenciador.resutlado
			if(miniGerenciador.resultado() != null){
				System.out.println(miniGerenciador.resultado());
				// Coloca nome do evento no JTextField que o represnta
				in_patrocinador.setText(miniGerenciador.resultado().get(0).toString());
				str_cnpjPat  = miniGerenciador.resultado().get(0).toString();
				miniGerenciador.dispose();
			}
		}
	}

	public void onClickApresentador(){

		if(str_codEv == null || str_numEd == null)
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
