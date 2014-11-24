// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Grid layout
import java.awt.GridLayout;
import javax.swing.*;
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
import javax.swing.JOptionPane;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import java.text.ParseException;
import java.sql.SQLException;


// Bordas
import javax.swing.border.EmptyBorder;

// ArryaList
import java.util.ArrayList;

public class CadastrarDespesa extends JDialog implements ActionListener
{
	// JPanel contem todos os elementos
	private JPanel panel;

	// Labels
	private JLabel evento;
	private JLabel edicao;
	private JLabel patrocinador;
	private JLabel data;
	private JLabel valor;
	private JLabel descricao;

	// Campos de texto
	private JTextField in_evento;	
	private JTextField in_edicao;
	private JTextField in_patrocinador;

	private JFormattedTextField in_data; //Campo de texto formatado para data
	private JTextField in_valor;
	private JTextField in_descricao;

	private DBConnection dbcon;

	// Botoes
	private JButton buttonEvento;
	private JButton buttonEdicao;
	private JButton buttonPatrocinio;
	private JButton cadastrar;
	private JButton cancelar;

	private String str_codEv;
	private String str_numEd;
	private String str_cnpjPat;

	private String[] dados;

	private boolean funcaoCadastrar;


	public CadastrarDespesa(DBConnection dbcon, String[] dados)
	{
		// Titulo da janela
		setTitle("Editar Despesa");

		// Botao de fechar
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.funcaoCadastrar = false;
		this.dados = dados;
		this.dbcon = dbcon;
	}


	public CadastrarDespesa(DBConnection dbcon)
	{
		// Titulo da janela
		setTitle("Cadastrar Despesa");

		// Botao de fechar
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.funcaoCadastrar = true;
		this.dbcon = dbcon;
	}

	public void initUI() throws ParseException
	{
		this.setModal(true);
		
		// Criando panel
		panel = new JPanel();

		// Grid Layout
		panel.setLayout(new GridLayout(17, 0, 5, 5));
		panel.setPreferredSize(new Dimension(500, 500));
		
		//Adiciona JPanel ao frame
		getContentPane().add(panel);

		// Cria e aplica a borda
		EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);
		panel.setBorder(emptyBorder);

		evento = new JLabel("Evento");
		edicao = new JLabel("Edicao");
		patrocinador = new JLabel("Patrocinio");
		data = new JLabel("Data");
		valor = new JLabel("Valor");
		descricao = new JLabel("Descricao");


    	// Campos para preencher os dados
    	in_evento = new JTextField(50);
    	in_edicao = new JTextField(50);
    	in_patrocinador = new JTextField(50);

		// Mascara para formatar dados
 		MaskFormatter mf1 = new MaskFormatter("##/##/####");
    	mf1.setPlaceholderCharacter('_');

    	in_valor = new JTextField(70);
    	in_data = new JFormattedTextField(mf1);
    	in_descricao = new JTextField(70);

		// Cria botoes
		buttonPatrocinio = new JButton("Selecionar Patrocinio");
		buttonEvento = new JButton("Selecionar Evento");
		buttonEdicao = new JButton("Selecionar Edicao");


		if(funcaoCadastrar){
			cadastrar = new JButton("Cadastrar");

		}else{
			cadastrar = new JButton("Editar");
		}

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

		panel.add(data);
		panel.add(in_data);
		
		panel.add(valor);
		panel.add(in_valor);

		panel.add(descricao);
		panel.add(in_descricao);

		panel.add(cadastrar);
		panel.add(cancelar);

		buttonPatrocinio.addActionListener(this);
		buttonEvento.addActionListener(this);
		buttonEdicao.addActionListener(this);

		cadastrar.addActionListener(this);
		cancelar.addActionListener(this);

		// Tamanho da janela sera suficiente para conter todos os componetes
		pack();

		// Centralizando janela
		setLocationRelativeTo(null);

		if(!funcaoCadastrar){

		 	in_evento.setText(dados[2]);	
		 	in_edicao.setText(dados[4]);
		 	in_patrocinador.setText(dados[6]);
		 	in_data.setText(dados[9]);

		 	in_valor.setText(dados[10].replaceAll("[$\\s]", ""));		 	
		 	in_descricao.setText(dados[11]);
		
			str_codEv = dados[1];
			str_numEd = dados[3];
			str_cnpjPat = dados[5];
		}

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
	}

	// Metodo do botao de cadastrar, devera salva no banco de dados
	public void onClickOkay()
	{
		if(!(str_cnpjPat == null)){
			String codDesp = "SQ_codDesp_despesa.NEXTVAL";
			String cnpjPat = str_cnpjPat;
			String codEv = str_codEv;
			String numEd = str_numEd;
			String dataDesp = "TO_DATE(" + "'" + in_data.getText() + "'," + "'DD/MM/YYYY')";
			String valorDesp = in_valor.getText();
			String descricaoDesp = "'" + in_descricao.getText() + "'";

			String query = null;
			if(funcaoCadastrar){
				 query = "INSERT INTO despesa VALUES(" + codDesp + "," + codEv + "," + numEd + "," + cnpjPat + ", " 
				+ codEv + "," + numEd + "," + dataDesp + "," + valorDesp + ", " + descricaoDesp + ")";
			}
			else {
				query = "UPDATE despesa SET cnpjPat = " + cnpjPat + ", codEv = " + codEv + 
				", numEd= " + numEd + ", dataDesp=" + dataDesp + ", valorDesp=" + valorDesp + ", descricaoDesp=" + descricaoDesp +
				 " WHERE codDesp =" + dados[0] + " AND codEv =" + codEv + " AND numEd =" + numEd;
			}

			System.out.println(query);

			try{
				dbcon.executarInsert(query);
				if(funcaoCadastrar){
					JOptionPane.showMessageDialog(null, "Despesa Cadastrada com sucesso");
				}
				else {
					JOptionPane.showMessageDialog(null, "Despesa Alterada com sucesso");
				}
				setVisible(false);
				dispose();
			}catch(SQLException ex){
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}else{
				JOptionPane.showMessageDialog(null, "CNPJ deve ser preenchido!", "Erro", JOptionPane.ERROR_MESSAGE);
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

			str_numEd = null;
			str_cnpjPat = null;

			in_edicao.setText(null);
			in_patrocinador.setText(null);
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

				str_cnpjPat = null;
				in_patrocinador.setText(null);

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
}
