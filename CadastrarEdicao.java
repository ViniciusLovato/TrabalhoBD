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

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.JOptionPane;

import java.text.ParseException;
import java.util.StringTokenizer;

// Bordas
import javax.swing.border.EmptyBorder;
import java.sql.SQLException;



// ArryaList
import java.util.ArrayList;

public class CadastrarEdicao extends JDialog implements ActionListener
{
	// JPanel contem todos os elementos
	private JPanel panel;

	// Labels
	private JLabel evento;
	private JLabel descricao;
	private JLabel data_inicio;
	private JLabel data_fim;
	private JLabel local;
	private JLabel taxa_inscricao;

	// Campos de texto
	private JTextField in_eventos;
	private JTextField in_descricao;
	private JFormattedTextField in_data_inicio; //Campo de texto formatado para data
	private JFormattedTextField in_data_fim;	// Campo de texto formatado para data
	private JTextField in_local;
	private JTextField in_taxa_inscricao;

	// Combobox que contem os eventos

	private JButton bt_eventos;

	// Botoes
	private JButton cadastrar;
	private JButton cancelar;

	private DBConnection dbcon;

	private String in_codEvento;

	private String[] dados;

	private boolean funcaoCadastrar;

	public CadastrarEdicao(DBConnection dbcon, String[] dados)
	{
		// Titulo da janela
		setTitle("Cadastrar Edicao");

		// Botao de fechar
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.funcaoCadastrar = false;
		this.dados = dados;
		this.dbcon = dbcon;
	}

	public CadastrarEdicao(DBConnection dbcon)
	{
		// Titulo da janela
		setTitle("Cadastrar Edicao");

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
		panel.setLayout(new GridLayout(15, 0, 5, 5));
		panel.setPreferredSize(new Dimension(500, 500));
		
		//Adiciona JPanel ao frame
		getContentPane().add(panel);

		// Cria e aplica a borda
		EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);
		panel.setBorder(emptyBorder);


		// Criando Labels
		evento = new JLabel("Evento:");
		descricao = new JLabel("Descricao:");
		data_inicio = new JLabel("Data Inicio");
		data_fim = new JLabel("Data Fim");
		local = new JLabel("Local");
		taxa_inscricao = new JLabel("Taxa de Incricao");

		// Mascara para formatar dados
 		MaskFormatter mf1 = new MaskFormatter("##/##/####");
    	mf1.setPlaceholderCharacter('_');

    	// Campos para preencher os dados
    	in_eventos = new JTextField(50);
    	in_eventos.setEditable(false);

	    in_descricao = new JTextField(50);
	    in_data_inicio = new JFormattedTextField(mf1);
	    in_data_fim = new JFormattedTextField(mf1);
	    in_descricao = new JTextField(50);
		in_local = new JTextField(50);
		in_taxa_inscricao = new JTextField(50);

		// Cria botoes
		if(funcaoCadastrar){
			cadastrar = new JButton("Cadastrar");
		}
		else
			cadastrar = new JButton("Alterar");

		cancelar = new JButton("Cancelar");
		bt_eventos = new JButton("Selecione o Evento");

		// Adiciona botoes, campos de textos e labels ao panel
		panel.add(evento);
		panel.add(in_eventos);
		panel.add(bt_eventos);

		panel.add(descricao);
		panel.add(in_descricao);
		
		panel.add(data_inicio);
		panel.add(in_data_inicio);
		
		panel.add(data_fim);
		panel.add(in_data_fim);
		
		panel.add(local);
		panel.add(in_local);
		
		panel.add(taxa_inscricao);
		panel.add(in_taxa_inscricao);

		panel.add(cadastrar);
		panel.add(cancelar);

		cadastrar.addActionListener(this);
		cancelar.addActionListener(this);
		bt_eventos.addActionListener(this);

		// Tamanho da janela sera suficiente para conter todos os componetes
		pack();

		// Centralizando janela
		setLocationRelativeTo(null);

		// nomeEv, codEv, numEd, descricaoEd, dataInicioEd, dataFimEd, localEd, taxaEd, saldoFinanceiroEd, qtdArtigosApresentadosEd
		if(funcaoCadastrar == false){
			in_eventos.setText(dados[0]);
			in_descricao.setText(dados[3]);
			

			in_data_inicio.setText(dados[4]); //Campo de texto formatado para data
			in_data_fim.setText(dados[5]);	// Campo de texto formatado para data



			in_local.setText(dados[6]);
			in_taxa_inscricao.setText(dados[7]);

			System.out.println("DATA FIM!:" + dados[4]);
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
		else if(e.getActionCommand().equals(bt_eventos.getText())){
			System.out.println("Selecione Evento!");
			onClickEventos();
		}
	}

	// Metodo do botao de cadastrar, devera salva no banco de dados
	public void onClickOkay()
	{

		String codEv = in_codEvento;
		String numEd = "SQ_numEd_edicao.NEXTVAL";
		String descricaoEd = "'" + in_descricao.getText() + "'";
		String dataInicioEd = "TO_DATE(" + "'" + in_data_inicio.getText() + "'," + "'DD/MM/YYYY')";
		String dataFimEd = "TO_DATE(" + "'" + in_data_fim.getText() + "'," + "'DD/MM/YYYY')";
		String localEd = "'" + in_local.getText() + "'";
		String taxaEd = in_taxa_inscricao.getText();
		int saldoFinanceiroEd = 0;
		int qtdArtigosApresentadosEd = 0;

		String query = null;
		// codEv, numEd, descricaoEd, dataInicioEd, dataFimEd, localEd, taxaEd, saldoFinanceiroEd, qtdArtigosApresentadosEd
		if(funcaoCadastrar){
		    query = "INSERT INTO edicao VALUES(" + codEv + "," + numEd + "," + descricaoEd + "," + dataInicioEd + ", " 
			+ dataFimEd + "," + localEd + "," + taxaEd + "," + saldoFinanceiroEd + "," + qtdArtigosApresentadosEd + ")";
		}else {
			query = "UPDATE edicao SET descricaoEd =" + descricaoEd + ", dataInicioEd=" + dataInicioEd + ", dataFimEd=" + dataFimEd + 
				", localEd=" + localEd + ", taxaEd=" + taxaEd + "WHERE codEv = " + dados[1] +" AND numEd = " + dados[2]; 
		}

		System.out.println(query);

		try{
			dbcon.executarInsert(query);

			if(funcaoCadastrar){
				JOptionPane.showMessageDialog(null, "Edicao Cadastrada com sucesso");
			}
			else 
				JOptionPane.showMessageDialog(null, "Edicao Alterada com sucesso");

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
			in_eventos.setText(miniGerenciador.resultado().get(1).toString());
			in_codEvento  = miniGerenciador.resultado().get(0).toString();
			miniGerenciador.dispose();
		}
	}

}
