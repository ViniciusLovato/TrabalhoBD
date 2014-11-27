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
import javax.swing.JCheckBox;
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

public class CadastrarInscrito extends JDialog implements ActionListener
{
	// JPanel contem todos os elementos
	private JPanel panel;

	// Labels
	private JLabel evento;
	private JLabel edicao;
	private JLabel participante;
	private JLabel data;

	// Campos de texto
	private JTextField in_evento;
	private JTextField in_edicao;
	private JTextField in_participante;
	private JFormattedTextField in_data;

	private JCheckBox in_apresentador;

	// Conexao com o bd
	private DBConnection dbcon;

	// booleans para verificar se eh edicao ou cadastro de inscritos
	private boolean funcaoCadastrar;
	// dados para o prenchimento da linha
	private String[] dados;

	// Botoes
	private JButton selecionarParticipante;
	private JButton selecionarEvento;
	private JButton selecionarEdicao;
	private JButton cadastrar;
	private JButton cancelar;

	// String selecionar dados 
	private String str_codEv = null;
	private String str_numEd = null;
	private String str_participante = null;


	public CadastrarInscrito(DBConnection dbcon)
	{
		// Titulo da janela
		setTitle("Cadastrar Inscritos");

		// Botao de fechar
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		funcaoCadastrar = true;

		this.dbcon = dbcon;
	}
	
	public CadastrarInscrito(DBConnection dbcon, String[] dados)
	{
		// Titulo da janela
		setTitle("Editar Inscritos");

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
		panel.setLayout(new GridLayout(14, 0, 5, 5));
		panel.setPreferredSize(new Dimension(500, 500));
		
		//Adiciona JPanel ao frame
		getContentPane().add(panel);

		// Cria e aplica a borda
		EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);
		panel.setBorder(emptyBorder);

		participante = new JLabel("Participante");
		evento = new JLabel("Evento");
		edicao = new JLabel("Edicao");
		data = new JLabel("Data");

		// Mascara para formatar dados
		MaskFormatter mf1;
		mf1 = new MaskFormatter("##/##/####");
    	mf1.setPlaceholderCharacter('_');

    	// Campos para preencher os dados
    	in_participante = new JTextField(50);
    	in_evento = new JTextField(50);
    	in_edicao = new JTextField(50);
    	in_apresentador = new JCheckBox("Apresentador");
    	in_data = new JFormattedTextField(mf1);

    	in_apresentador.setEnabled(false);

		// Cria botoes
		selecionarEvento = new JButton("Selecionar evento");
		selecionarEdicao = new JButton("Selecionar edicao");
		selecionarParticipante = new JButton("Selecionar apresentador");

		// Cria botoes de cadastra/altera e cancela
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
		panel.add(evento);
		panel.add(in_evento);
		in_evento.setEditable(false);
		panel.add(selecionarEvento);

		panel.add(edicao);
		panel.add(in_edicao);
		in_edicao.setEditable(false);
		panel.add(selecionarEdicao);

		panel.add(participante);
		panel.add(in_participante);
		in_participante.setEditable(false);
		panel.add(selecionarParticipante);
		
		panel.add(data);
		panel.add(in_data);

		panel.add(in_apresentador);

		panel.add(cadastrar);
		panel.add(cancelar);

		selecionarEvento.addActionListener(this);
		selecionarEdicao.addActionListener(this);
		selecionarParticipante.addActionListener(this);

		cadastrar.addActionListener(this);
		cancelar.addActionListener(this);

		// Se estiver em modo editar, preencher os campos
		if(funcaoCadastrar == false){

			in_evento.setText(dados[6]);
			in_edicao.setText(dados[7]);
			in_participante.setText(dados[5]);
			in_data.setText(dados[3].replaceAll("[^0-9]+", ""));
			in_apresentador.setSelected(Integer.parseInt(dados[4]) == 1);

			str_codEv = dados[0];
			str_numEd = dados[1];
			str_participante = dados[2];

			// Chave primaria nao pode ser alterada
			selecionarEvento.setEnabled(false);
			selecionarEdicao.setEnabled(false);
			selecionarParticipante.setEnabled(false);
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
		else if(e.getActionCommand().equals(selecionarParticipante.getText()))
		{
			onClickParticipante();
		}
		else if(e.getActionCommand().equals(selecionarEvento.getText()))
		{
			onClickEventos();
		}
		else if(e.getActionCommand().equals(selecionarEdicao.getText()))
		{
			onClickEdicoes();
		}
	}

	// Metodo do botao de cadastrar, devera salva no banco de dados
	public void onClickOkay()
	{
		if(str_codEv != null && str_codEv != null && str_participante != null)
		{
			String codEv = str_codEv;
			String numEd = str_numEd;
			String idPart = str_participante;
			int tipoApresentador = (in_apresentador.isSelected() ? 1 : 0);

			String dataInsc = "TO_DATE(" + "'" + in_data.getText() + "'," + "'DD/MM/YYYY')";

			String query = null;
			if(funcaoCadastrar)
			{
				query = "call consistenciaPessoa.insereParticipante(" + codEv + "," + numEd + "," + idPart + ", " + dataInsc + ", " + tipoApresentador + ")";
				//query = "INSERT INTO inscrito VALUES(" + codEv + "," + numEd + "," + idPart + ", " + dataInsc + ", " + tipoApresentador + ")";
			}
			else
			{
				query = "UPDATE inscrito SET dataInsc = " + dataInsc + ", tipoApresentador = " + tipoApresentador + " WHERE codEV = " + codEv + " AND numEd = " + numEd + " AND idPart = " + idPart;
			}
			System.out.println(query);

			try{
				dbcon.executarInsert(query);
				if(funcaoCadastrar)
				{
					JOptionPane.showMessageDialog(null, "Inscrito cadastrado com sucesso");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Inscrito alterado com sucesso");
				}
				setVisible(false);
				dispose();
			}catch(SQLException ex){
				GerenciadorErros.errorPanel(ex.getErrorCode());
			}
		}
		// Caso o usuario nao selecione os campos obrigatorios
		else
		{
			JOptionPane.showMessageDialog(null, "Evento, edicao e participante precisam estar selecionados");
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

	public void onClickParticipante(){

		// Valores que serao passados para popular a tabela do JDialog, os resultados obtidos estao nessa ordem e podem ser acessados
		// por meio de miniGerenciador.resultados().get(i);
		String[] colunas = {"idPe", "nomePe", "emailPe", "instituicaoPe", "telefonePe", "nacionalidadePe", "enderecoPe", "tipoOrganizador", "tipoParticipante", "tipoAutor"};		
		String[][] dados = this.dbcon.CarregaDados("PESSOA");  

		// Cria JDialog com a tabela que o usuario ira selecionar
		MiniGerenciador miniGerenciador =  new MiniGerenciador(this, dados, colunas);
		
		// Caso o usuario clicou em ok o resultado esta salvo em miniGerenciador.resutlado
		if(miniGerenciador.resultado() != null){
			System.out.println(miniGerenciador.resultado());
			// Coloca nome do evento no JTextField que o represnta
			in_participante.setText(miniGerenciador.resultado().get(1).toString());
			str_participante  = miniGerenciador.resultado().get(0).toString();
			miniGerenciador.dispose();
		}
	}

}
