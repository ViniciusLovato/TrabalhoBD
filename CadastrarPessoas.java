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
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

// Bordas
import javax.swing.border.EmptyBorder;

// ArryaList
import java.util.ArrayList;

// SQL
import java.sql.*;

public class CadastrarPessoas extends JDialog implements ActionListener
{
	// JPanel para inserir os elementos
	private JPanel panel;

	// Labels
	private JLabel nome;
	private JLabel email;
	private JLabel instituicao;
	private JLabel telefone;
	private JLabel nacionalidade;
	private JLabel endereco;
	private JLabel tipo;

	// TextFields
	private JTextField in_nome;
	private JTextField in_email;
	private JTextField in_instituicao;
	private JTextField in_telefone;
	private JTextField in_nacionalidade;
	private JTextField in_endereco;

	private JCheckBox in_organizador;
	private JCheckBox in_participante;
	private JCheckBox in_autor;

	private DBConnection dbcon;

	private boolean funcaoCadastrar;
	private String[] dados;
	
	// Buttons
	private JButton cadastrar;
	private JButton cancelar;

	public CadastrarPessoas(DBConnection dbcon)
	{
		// Titulo da janela
		setTitle("Cadastrar Pessoas");
		// Setting up close button
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.funcaoCadastrar = true;

		this.dbcon = dbcon;
	}

	public CadastrarPessoas(DBConnection dbcon, String dados[])
	{
		// Titulo da janela
		setTitle("Editar Pessoas");
		// Setting up close button
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.funcaoCadastrar = false;
		this.dados = dados;

		this.dbcon = dbcon;
	}

	public void initUI()
	{
		this.setModal(true);
		// Criando os Paineis
		panel = new JPanel();
		// Criando o Layout
		panel.setLayout(new GridLayout(18, 0, 5, 5));
		// Adicionando o painel ao JFrame
		getContentPane().add(panel);

		// Criando as bordas
		EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);
		panel.setBorder(emptyBorder);

		// Inicializando os componentes
		nome = new JLabel("Nome:");
		email = new JLabel("E-mail:");
		instituicao = new JLabel("Instituicao:");
		telefone = new JLabel("Telefone:");
		nacionalidade = new JLabel("Nacionalidade:");
		endereco = new JLabel("Endereco:");
		tipo = new JLabel("Tipo:");

		in_nome = new JTextField(50);
		in_email = new JTextField(50);
		in_instituicao = new JTextField(50);
		in_telefone = new JTextField(50);
		in_nacionalidade = new JTextField(50);
		in_endereco = new JTextField(50);

		in_organizador = new JCheckBox("Organizador");
		in_participante = new JCheckBox("Participante");
		in_autor = new JCheckBox("Autor");

		// Initializing the arrays 
		if(funcaoCadastrar)
			cadastrar = new JButton("Cadastrar");
		else
			cadastrar = new JButton("Alterar");

		cancelar = new JButton("Cancelar");

		// Inserindo os labels e textfields no panel
		panel.add(nome);
		panel.add(in_nome);

		panel.add(email);
		panel.add(in_email);

		panel.add(instituicao);
		panel.add(in_instituicao);

		panel.add(telefone);
		panel.add(in_telefone);

		panel.add(nacionalidade);
		panel.add(in_nacionalidade);

		panel.add(endereco);
		panel.add(in_endereco);

		panel.add(tipo);
		panel.add(in_organizador);
		panel.add(in_participante);
		panel.add(in_autor);

		// Adicionando botoes no painel
		panel.add(cadastrar);
		panel.add(cancelar);

		// Adicionando os eventListeners
		cadastrar.addActionListener(this);
		cancelar.addActionListener(this);


		// Usuario esta tentando alterar 
		if(funcaoCadastrar == false){
			in_nome.setText(dados[1]);
			in_email.setText(dados[2]);
			in_instituicao.setText(dados[3]);
			in_telefone.setText(dados[4]);
			in_nacionalidade.setText(dados[5]);
			in_endereco.setText(dados[6]);

			in_organizador.setSelected(dados[7].equals("1") ? true : false);
			in_participante.setSelected(dados[8].equals("1") ? true : false);
			in_autor.setSelected(dados[9].equals("1") ? true : false);
		}

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
			onClickCadastrarOuAlterar();
		}
		else if(e.getActionCommand().equals(cancelar.getText()))
		{
			onClickCancelar();
		}
	}

	// Botao cadastrar
	public void onClickCadastrarOuAlterar()
	{
		//  idPe, nomePe, emailPe, instituicaoPe, telefonePe, nacionalidadePe, enderecoPe, tipoOrganizador, tipoParticipante, tipoAutor
		String nomePe = "'" + in_nome.getText() + "'";
		String emailPe = "'" + in_email.getText() + "'";
		String instituicaoPe = "'" + in_instituicao.getText() + "'";
		String telefonePe = "'" + in_telefone.getText() + "'";
		String nacionalidadePe = "'" + in_nacionalidade.getText() + "'";
		String enderecoPe = "'" + in_endereco.getText() + "'";
		int tipoOrganizador = (in_organizador.isSelected() ? 1 : 0);
		int tipoParticipante = (in_participante.isSelected() ? 1 : 0);
		int tipoAutor =  (in_autor.isSelected() ? 1 : 0);

		String query;
		if(funcaoCadastrar)
		{
			query = "INSERT INTO pessoa VALUES(sq_idPe_pessoa.NEXTVAL, " + nomePe + "," + emailPe + "," + instituicaoPe + "," +
			telefonePe + "," + nacionalidadePe + "," + enderecoPe + "," + tipoOrganizador + "," + tipoParticipante + "," + tipoAutor + ")";
		}
		else
		{
			query = "UPDATE pessoa SET nomePe =" + nomePe + ", emailPe=" + emailPe + ", telefonePe=" + telefonePe + ", nacionalidadePe = " + nacionalidadePe + ", enderecoPe = " + enderecoPe +
			 ", tipoOrganizador = " + tipoOrganizador + ", tipoParticipante = " + tipoParticipante + ", tipoAutor = " + tipoAutor +
				"WHERE idPe = " + dados[0]; 
		}

		System.out.println(query);

		try{
			dbcon.executarInsert(query);
			if(funcaoCadastrar)
			{
				JOptionPane.showMessageDialog(null, "Registro inserido com sucesso");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Registro Alterado com sucesso");
			}
			setVisible(false);
			dispose();

		}catch(Exception ex){
			System.err.println(ex.getMessage()); 
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		} 
	}

	// Botao cancelar
	public void onClickCancelar()
	{
		setVisible(false);
		dispose();	
	}
}
