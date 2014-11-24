// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Grid layout
import java.awt.GridLayout;

// Componetes basicos para interface
import javax.swing.*;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import java.awt.Dimension;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import java.text.ParseException;

// Bordas
import javax.swing.border.EmptyBorder;

// ArryaList
import java.util.ArrayList;

import java.sql.SQLException;

public class CadastrarPatrocinio extends JDialog implements ActionListener
{
	// JPanel contem todos os elementos
	private JPanel panel;

	// Labels
	private JLabel patrocinador;
	private JLabel evento;
	private JLabel edicao;
	private JLabel valor;
	private JLabel data;

	// Campos de texto
	private JTextField in_patrocinador;
	private JTextField in_evento;
	private JTextField in_edicao;

	private JTextField in_valor;
	private JFormattedTextField in_data; //Campo de texto formatado para data

	private DBConnection dbcon;


	private boolean funcaoCadastrar;
	private String[] dados;

	// Botoes
	private JButton buttonCNPJ;
	private JButton buttonEvento;
	private JButton buttonEdicao;
	private JButton cadastrar;
	private JButton cancelar;

	private String str_cnpjPat = null;
	private String str_codEv = null;
	private String str_numEd = null;

	public CadastrarPatrocinio(DBConnection dbcon)
	{
		// Titulo da janela
		setTitle("Cadastrar Edicao");

		// Botao de fechar
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		funcaoCadastrar = true;

		this.dbcon = dbcon;
	}
	
	public CadastrarPatrocinio(DBConnection dbcon, String[] dados)
	{
		// Titulo da janela
		setTitle("Cadastrar Edicao");

		// Botao de fechar
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.dados = dados;
		funcaoCadastrar = false;

		this.dbcon = dbcon;
	}

	public void initUI() throws ParseException
	{
		this.setModal(true);
		
		// Criando panel
		panel = new JPanel();

		// Grid Layout
		panel.setLayout(new GridLayout(15, 0, 5, 5));
		panel.setPreferredSize(new Dimension(500, 500));
		
		//Adiciona JPanel ao frame
		getContentPane().add(panel);

		// Cria e aplica a borda
		EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);
		panel.setBorder(emptyBorder);

		patrocinador = new JLabel("Patrocinador");
		evento = new JLabel("Evento");
		edicao = new JLabel("Edicao");
		valor = new JLabel("Valor");
		data = new JLabel("Data");

		// Mascara para formatar dados
 		MaskFormatter mf1 = new MaskFormatter("##/##/####");
    	mf1.setPlaceholderCharacter('_');

    	// Campos para preencher os dados
    	in_patrocinador = new JTextField(50);
    	in_evento = new JTextField(50);
    	in_edicao = new JTextField(50);

    	in_valor = new JTextField(70);

    	in_data = new JFormattedTextField(mf1);

		// Cria botoes
		buttonCNPJ = new JButton("Selecionar Patrocinador");
		buttonEvento = new JButton("Selecionar Evento");
		buttonEdicao = new JButton("Selecionar Edicao");

		if(funcaoCadastrar)
		{
			cadastrar = new JButton("Cadastrar");
		}
		else
		{
			cadastrar = new JButton("Alterar");
		}
		cancelar = new JButton("Cancelar");

		// Adiciona botoes, campos de textos e labels ao panel
		panel.add(patrocinador);
		panel.add(in_patrocinador);
		in_patrocinador.setEditable(false);
		panel.add(buttonCNPJ);

		panel.add(evento);
		panel.add(in_evento);
		in_evento.setEditable(false);
		panel.add(buttonEvento);

		panel.add(edicao);
		panel.add(in_edicao);
		in_edicao.setEditable(false);
		panel.add(buttonEdicao);
		
		panel.add(valor);
		panel.add(in_valor);
		
		panel.add(data);
		panel.add(in_data);

		panel.add(cadastrar);
		panel.add(cancelar);

		buttonCNPJ.addActionListener(this);
		buttonEdicao.addActionListener(this);
		buttonEvento.addActionListener(this);

		cadastrar.addActionListener(this);
		cancelar.addActionListener(this);

		if(funcaoCadastrar == false){

			in_patrocinador.setText(dados[6]);
			in_evento.setText(dados[7]);
			in_edicao.setText(dados[8]);
			in_valor.setText(dados[3]);
			in_data.setText(dados[5].replaceAll("[^0-9]+", ""));

			str_cnpjPat = dados[0].replaceAll("[^0-9]+", "");
			str_codEv = dados[1];
			str_numEd = dados[2];

			// Chave primaria nao pode ser alterada
			buttonCNPJ.setEnabled(false);
			buttonEvento.setEnabled(false);
			buttonEdicao.setEnabled(false);

		}

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
		else if(e.getActionCommand().equals(buttonCNPJ.getText()))
		{
			onClickCNPJ();
		}
		else if(e.getActionCommand().equals(buttonEvento.getText()))
		{
			onClickEventos();
		}
		else if(e.getActionCommand().equals(buttonEdicao.getText()))
		{
			onClickEdicoes();
		}
	}

	// Metodo do botao de cadastrar, devera salva no banco de dados
	public void onClickOkay()
	{
		String cnpjPat = str_cnpjPat;
		String codEv = str_codEv;
		String numEd = str_numEd;
		String valorPat = in_valor.getText();
		String saldoPat = valorPat;
		String dataPat = "TO_DATE(" + "'" + in_data.getText() + "'," + "'DD/MM/YYYY')";

		String query = null;
		if(funcaoCadastrar)
		{
			query = "INSERT INTO patrocinio VALUES(" + cnpjPat + "," + codEv + "," + numEd + "," + valorPat + ", " 
			+ saldoPat + "," + dataPat + ")";
		}
		else
		{
			query = "UPDATE patrocinio SET valorPat = " + valorPat + ", dataPat = " + dataPat + " WHERE cnpjPat = " + cnpjPat + " AND codEv = " + codEv + " AND numEd = " + numEd;
		}
		System.out.println(query);

		try{
			dbcon.executarInsert(query);
			if(funcaoCadastrar)
			{
				JOptionPane.showMessageDialog(null, "Patrocinador cadastrado com sucesso");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Patrocinador alterado com sucesso");
			}
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

	public void onClickCNPJ(){

		// Valores que serao passados para popular a tabela do JDialog, os resultados obtidos estao nessa ordem e podem ser acessados
		// por meio de miniGerenciador.resultados().get(i);
		String[] colunas = {"CNPJ", "Razao Social", "telefonePat", "enderecoPat"};		
		String[][] dados = this.dbcon.CarregaDados("PATROCINADOR");   

		// Cria JDialog com a tabela que o usuario ira selecionar
		MiniGerenciador miniGerenciador =  new MiniGerenciador(this, dados, colunas);
		
		// Caso o usuario clicou em ok o resultado esta salvo em miniGerenciador.resutlado
		if(miniGerenciador.resultado() != null){
			System.out.println(miniGerenciador.resultado());
			// Coloca nome do evento no JTextField que o represnta
			in_patrocinador.setText(miniGerenciador.resultado().get(1).toString());
			str_cnpjPat  = miniGerenciador.resultado().get(0).toString();
			miniGerenciador.dispose();
		}
	}

}
