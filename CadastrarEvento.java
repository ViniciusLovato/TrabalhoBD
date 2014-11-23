// Event Listners
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Grid layout
import java.awt.GridLayout;

// Componentes da interface
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.*;


// Bordas
import javax.swing.border.EmptyBorder;

// ArryaList
import java.util.ArrayList;

public class CadastrarEvento extends JDialog implements ActionListener
{
	// JPanel para inserir os elementos
	private JPanel panel;

	// Labels
	private JLabel nome;
	private JLabel descricao;
	private JLabel website;
	// TextFields
	private JTextField in_nome;
	private JTextField in_descricao;
	private JTextField in_website;

	// Conexao
	DBConnection dbcon;

	// Buttons
	private JButton cadastrar;
	private JButton cancelar;

	private boolean funcaoCadastrar;
	private String[] dados;

	public CadastrarEvento(DBConnection dbcon)
	{
		// Titulo da janela
		setTitle("Cadastrar Eventos");
		// Setting up close button
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.funcaoCadastrar = true;
		// Conexao
		this.dbcon = dbcon;
	}

	public CadastrarEvento(DBConnection dbcon, String[] dados)
	{
		// Titulo da janela
		setTitle("Cadastrar Eventos");
		// Setting up close button
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.funcaoCadastrar = false;
		this.dados = dados;
		// Conexao
		this.dbcon = dbcon;
	}

	public void initUI()
	{
		this.setModal(true);
		// Criando os Paineis
		panel = new JPanel();
		// Criando o Layout
		panel.setLayout(new GridLayout(8, 0, 5, 5));
		// Adicionando o painel ao JFrame
		getContentPane().add(panel);

		// Criando as bordas
		EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);
		panel.setBorder(emptyBorder);

		// Inicializando os componentes
		nome = new JLabel("Nome:");
		descricao = new JLabel("Descricao:");
		website = new JLabel("Website:");

		in_nome = new JTextField(50);
		in_descricao = new JTextField(50);
		in_website = new JTextField(50);

		// Initializing the arrays 
		if(funcaoCadastrar == true){
			cadastrar = new JButton("Cadastrar");
		}
		else cadastrar = new JButton("Alterar");

		cancelar = new JButton("Cancelar");

		// Inserindo os labels e textfields no panel
		panel.add(nome);
		panel.add(in_nome);

		panel.add(descricao);
		panel.add(in_descricao);

		panel.add(website);
		panel.add(in_website);

		// Adicionando botoes no painel
		panel.add(cadastrar);
		panel.add(cancelar);

		// Adicionando os eventListeners
		cadastrar.addActionListener(this);
		cancelar.addActionListener(this);

		// Ajustando tamanho das janelas
		pack();
		// Centralizando a janela
		setLocationRelativeTo(null);
		// Seta a janela como visivel

		// Usuario esta tentando alterar 
		if(funcaoCadastrar == false){
			in_nome.setText(dados[1]);
			in_descricao.setText(dados[2]);
			in_website.setText(dados[3]);
		}

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		// Checando qual botao esta sendo pressionado
		if(e.getActionCommand().equals(cadastrar.getText()))
		{
			onClickCadastrarOuRegistrar();

		}
		else if(e.getActionCommand().equals(cancelar.getText()))
		{
			onClickCancelar();
		}
	}

	// Botao cadastrar
	public void onClickCadastrarOuRegistrar()
	{
		//  codEv, nomeEv, descricaoEv, websiteEv, totalArtigosApresentadosEv
		String nomeEv = "'" + in_nome.getText() + "'";
		String descricaoEv = "'" + in_descricao.getText() + "'";
		String websiteEv = "'" + in_website.getText() + "'";
		
		String query = null;
		if(funcaoCadastrar){
			int totalArtigosApresentadosEv = 0;
			query = "INSERT INTO evento VALUES(sq_codEv_evento.NEXTVAL, " + nomeEv + "," + descricaoEv + "," + websiteEv + ", " + totalArtigosApresentadosEv + ")";
		}else{
			query = "UPDATE evento SET nomeEv =" + nomeEv + ", descricaoEv=" + descricaoEv + ", websiteEv=" + websiteEv + 
				"WHERE codEv = " + dados[0]; 

			//UPDATE table_name SET column1=value1,column2=value2, WHERE some_column=some_value;
		}
		
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
	// Botao cancelar
	public void onClickCancelar()
	{
		setVisible(false);
		dispose();
	}
}
