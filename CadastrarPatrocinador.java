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
import javax.swing.*;


import java.text.ParseException;

// Bordas
import javax.swing.border.EmptyBorder;

// ArryaList
import java.util.ArrayList;

public class CadastrarPatrocinador extends JFrame implements ActionListener
{
	// JPanel contem todos os elementos
	private JPanel panel;

	// Labels
	private JLabel cnpj;
	private JLabel razao_social;
	private JLabel telefone;
	private JLabel endereco;

	// Campos de texto
	private JFormattedTextField in_cnpj;
	private JTextField in_razao_social;
	private JFormattedTextField in_telefone; //Campo de texto formatado para data
	private JTextField in_endereco;

	// Conexao
	DBConnection dbcon;

	// Botoes
	private JButton cadastrar;
	private JButton cancelar;


	public CadastrarPatrocinador(DBConnection dbcon)
	{
		// Titulo da janela
		setTitle("Cadastrar Edicao");

		// Botao de fechar
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.dbcon = dbcon;
	}

	public void initUI()  throws ParseException
	{
		// Criando panel
		panel = new JPanel();

		// Grid Layout
		panel.setLayout(new GridLayout(10, 0, 5, 5));
		
		//Adiciona JPanel ao frame
		getContentPane().add(panel);

		// Cria e aplica a borda
		EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);
		panel.setBorder(emptyBorder);


		// Criando Labels
		cnpj = new JLabel("CNPJ:");
		razao_social = new JLabel("Razao Social:");
		telefone = new JLabel("Telefone");
		endereco = new JLabel("endereco");
		
		// Mascara para formatar dados
 		MaskFormatter mf1 = new MaskFormatter("(##)####-####");
    	mf1.setPlaceholderCharacter('_');
    	mf1.setValueContainsLiteralCharacters( false );

 		MaskFormatter mf2 = new MaskFormatter("##.###.###/####-##");
    	mf2.setPlaceholderCharacter('_');
    	mf2.setValueContainsLiteralCharacters( false );

    	// Campos para preencher os dados
    	in_cnpj = new JFormattedTextField(mf2);
	    in_razao_social = new JTextField(50);
	    in_telefone = new JFormattedTextField(mf1);
		in_endereco = new JTextField(50);

		// Cria botoes
		cadastrar = new JButton("Cadastrar");
		cancelar = new JButton("Cancelar");

		// Adiciona botoes, campos de textos e labels ao panel
		panel.add(cnpj);
		panel.add(in_cnpj);

		panel.add(razao_social);
		panel.add(in_razao_social);
		
		panel.add(telefone);
		panel.add(in_telefone);
		
		panel.add(endereco);
		panel.add(in_endereco);

		panel.add(cadastrar);
		panel.add(cancelar);

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
	}

	// Metodo do botao de cadastrar, devera salva no banco de dados
	public void onClickOkay()
	{
		//  cnpjPat, razaoSocialPat, telefonePat, enderecoPat
		String cnpjPat = in_cnpj.getText().replaceAll("[^0-9]+", "");
		String razaoSocialPat = "'" + in_razao_social.getText() + "'";
		//String telefonePat = "'" + in_telefone.getText() + "'";
		String telefonePat = in_telefone.getText().replaceAll("[^0-9]+", "");
		String enderecoPat = "'" + in_endereco.getText() + "'";
		
		String query = "INSERT INTO patrocinador VALUES(" + cnpjPat + "," + razaoSocialPat + "," + telefonePat + ", " + enderecoPat + ")";
		
		System.out.println(query);

		try{
			dbcon.executarInsert(query);
			JOptionPane.showMessageDialog(null, "Registro inserido com sucesso");
			setVisible(false);
			dispose();

		}catch(Exception ex){
			System.err.println(ex.getMessage()); 
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		} 
	}

	// Metodo do botao que cancela a acao
	public void onClickCancel()
	{
		setVisible(false);
		dispose();	
	}

}
