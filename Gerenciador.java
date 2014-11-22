// Event Listener
import java.awt.event.*;



// Grid layout
import java.awt.GridLayout;

// Componetes basicos para interface
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

import java.awt.Dimension;


import java.text.ParseException;

// Bordas
import javax.swing.border.EmptyBorder;

// ArryaList
import java.util.ArrayList;

public class Gerenciador extends JFrame implements ActionListener, KeyListener
{
	// JPanel contem todos os elementos
	private JPanel parametrosPanel;
	private JPanel panel;
	private JPanel buttonPanel;

	private ArrayList<JLabel> parametros;
	private ArrayList<JTextField> buscas;

	private DefaultTableModel model;
	protected JTable table;

	private TableRowSorter<TableModel> sorter;
	private RowFilter<TableModel, Object> firstFilter;
	private RowFilter<TableModel, Object> secondFilter;
	private ArrayList<RowFilter<TableModel, Object>> filters;
	private RowFilter<TableModel, Object> compoundRowFilter;

	protected JButton criar;
	protected JButton deletar;
	protected JButton editar;
	protected JButton	voltar;

	protected DBConnection dbcon;

	public Gerenciador(String nome, DBConnection dbcon)
	{

		this.dbcon = dbcon;

		// Titulo da janela
		setTitle(nome);

		// Botao de fechar
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void configurar(String[] nomeParametro, String[] colunas, Object[][] dados)
	{
		// Criando panel
		parametrosPanel = new JPanel();
		panel = new JPanel();
		buttonPanel = new JPanel();

		parametrosPanel.setLayout(new GridLayout(nomeParametro.length, 2, 5, 5));
		parametrosPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

		// Grid Layout
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));

		buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		buttonPanel.setLayout(new GridLayout(2, 2, 5, 5));

		//Adiciona JPanel ao frame
		getContentPane().add(panel);

		configurarTabela(dados, colunas);

		parametros = new ArrayList<JLabel>();
		buscas = new ArrayList<JTextField>();

		criar = new JButton("Criar");
		deletar = new JButton("Obliterar");
		editar = new JButton("Editar");
		voltar = new JButton("voltar");

		panel.add(parametrosPanel);

		for (String s : nomeParametro){
			parametros.add(new JLabel(s));
			buscas.add(new JTextField(20));
		}

		for(int i = 0; i < parametros.size(); i++){
			parametrosPanel.add(parametros.get(i));
			parametrosPanel.add(buscas.get(i));

			buscas.get(i).addKeyListener(this);
		}		

		panel.add(new JScrollPane(table));

		panel.add(buttonPanel);

		buttonPanel.add(criar);
		buttonPanel.add(deletar);
		buttonPanel.add(editar);
		buttonPanel.add(voltar);

		// Tamanho da janela sera suficiente para conter todos os componetes
		pack();

		// Centralizando janela
		setLocationRelativeTo(null);

		// Janela agora visivel
		setVisible(true);
	}

	public void configurarTabela(Object[][] dados, String[] colunas){
		// Tabela
		model = new DefaultTableModel(dados, colunas);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		sorter = new TableRowSorter<TableModel>(model);

		firstFilter = null;
		secondFilter = null;

		filters = new ArrayList<RowFilter<TableModel, Object>>();

		compoundRowFilter = null;

		table.setRowSorter(sorter);
	}

	public void removerLinha(int linhaSelecionada){
		model.removeRow(linhaSelecionada);
		table.repaint();
	}

	public void keyPressed(KeyEvent e)
	{
		return;
	}
	
	public void keyTyped(KeyEvent e)
	{
		return;
	}

	public void keyReleased(KeyEvent e)
	{
		try
    	{
    		filters.clear();

    		for(int i = 0; i < parametros.size(); i++)
    		{
    			firstFilter = RowFilter.regexFilter(buscas.get(i).getText(), i);
    			filters.add(firstFilter);	
    		}
    		
    		compoundRowFilter = RowFilter.andFilter(filters);

    		sorter.setRowFilter(compoundRowFilter);
    	}
    	catch(java.util.regex.PatternSyntaxException exception)
    	{
    		System.out.println("Bad Regex");
    	}
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

	/*
	public static void main(String args[])
	{
		String[] parametros = {"campo da bagunca", "campo da zueira"};
		String[] colunas = {"bagunca", "zueira"};
		String[][] dados = {
			{"roubo", "da boa"},
		    {"picadinho", "merenda time"},
			{"roubo", "never ends"},
			{"picadinho2", "horario"},
			{"largato", "pomba"},
			{"largato", "da boa"}

		};

		Gerenciador ui = new Gerenciador("bagunca");
		ui.configurar(parametros, colunas, dados);
	} */
}
