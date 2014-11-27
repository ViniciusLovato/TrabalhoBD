import java.sql.SQLException;
// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import java.text.ParseException;
import javax.swing.*;

public class GerenciadorOrganizador extends Gerenciador{

	// String de dados que sera usado para preencher a tabela
	private String[][] dados;
	private static final String[] colunas = {};

	// Label apenas para manter consistencia do panel superior
	private JLabel empty1;
	private JLabel empty2;

	// Label de edicao e evento
	private JLabel evento;
	private JLabel edicao;
	private JLabel cargoOrg;

	// Campos de texto
	private JTextField in_evento;
	private JTextField in_edicao;
	private JTextField in_cargoOrg;

	// Botoes
	private JButton selecionarEvento;
	private JButton selecionarEdicao;

	private String str_numEd;
	private String str_codEv;
	private String str_cargoOrg;

	// private String str_idArtigo;

	public GerenciadorOrganizador(DBConnection dbcon){
		super("Gerenciar Escreve", dbcon);
		String[] parametros = {};
		int[] position = {};

		// Inicializando dados com null pois a tabela deve comear vazia
		dados = null;
		configurar(parametros, position, colunas, dados);

		// label para manter coesao da interface
		empty1 = new JLabel("");
		empty2 = new JLabel("");


		// LAbels de evento
		evento = new JLabel("Evento: ");
		edicao = new JLabel("Edicao: ");
		cargoOrg = new JLabel("Cargo");

		// Campos de evento e edicao
		in_evento = new JTextField(50);
		in_edicao = new JTextField(50);
		in_cargoOrg = new JTextField(50);

		// Botoes para selecionar evento e edicao
		selecionarEvento = new JButton("Selecione o Evento");
		selecionarEdicao = new JButton("Selecionar a Edicao");

		// configura os textfields para evitar edicao
		in_evento.setEnabled(false);
		in_edicao.setEnabled(false);

		// Adiciona elementos no painel superior
		parametrosPanel.add(evento);
		parametrosPanel.add(in_evento);
		parametrosPanel.add(empty1);
		parametrosPanel.add(selecionarEvento);

		// manter consistencia no layout

		// Adiciona elementos no painel superior
		parametrosPanel.add(edicao);
		parametrosPanel.add(in_edicao);
		parametrosPanel.add(empty2);
		parametrosPanel.add(selecionarEdicao);

		parametrosPanel.add(cargoOrg);
		parametrosPanel.add(in_cargoOrg);

		criar.addActionListener(this);
		deletar.addActionListener(this);
		// editar.addActionListener(this);
		voltar.addActionListener(this);
		selecionarEvento.addActionListener(this);
		selecionarEdicao.addActionListener(this);

		editar.setVisible(false);
		criar.setText("Selecionar Pessoa");
		criar.setEnabled(false);

		selecionarEdicao.setEnabled(false);
	}

	public void actionPerformed(ActionEvent e)
	{
		System.out.println(e.getActionCommand());
	
		if(e.getActionCommand().equals(criar.getText()))
		{
			onClickPessoa();
		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{
			onClickDeletar();
		}
		else if(e.getActionCommand().equals(editar.getText()))
		{
		}
		else if(e.getActionCommand().equals(selecionarEvento.getText()))
		{
			onClickEventos();
		}
		else if(e.getActionCommand().equals(selecionarEdicao.getText()))
		{
			onClickEdicoes();
		}
		else if(e.getActionCommand().equals(voltar.getText()))
		{
			setVisible(false);
			dispose();
		}
	}

	public void preencherTabela(){

		String[] colunas = {"idOrg", "codEv", "numEd", "cargoOrg", "nomePe", "nomeEv", "descricaoEd"};
		// idOrg, codEv, numEd, cargoOrg
		String[][] dados = this.dbcon.CarregaDados("formataSaidaOrganiza", " WHERE codEv=" + str_codEv +
		 " AND numEd=" + str_numEd);

		configurarTabela(dados, colunas);
		removerColuna(0);
		removerColuna(1);
		removerColuna(2);


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

			selecionarEdicao.setEnabled(true);
		}
	}

	public void onClickEdicoes(){

		if(str_codEv == null)
		{
			JOptionPane.showMessageDialog(null, "Evento nao escolhido", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			String str_numEd_antigo = str_numEd;
			// Valores que serao passados para popular a tabela do JDialog, os resultados obtidos estao nessa ordem e podem ser acessados
			// por meio de miniGerenciador.resultados().get(i);			
			String[] colunas = {"Nome evento", "Numero Evento", "Numero Edicao", 
    "Descricao", "Data Inicio", "Data fim", "Local", "Taxa" ,"Saldo", "Total de Artigos"};	
			String[][] dados = this.dbcon.CarregaDados("formataSaidaEdicao", " WHERE codEv = " + str_codEv);   

			// Cria JDialog com a tabela que o usuario ira selecionar
			MiniGerenciador miniGerenciador =  new MiniGerenciador(this, dados, colunas);
			
			// Caso o usuario clicou em ok o resultado esta salvo em miniGerenciador.resutlado
			if(miniGerenciador.resultado() != null){
				System.out.println(miniGerenciador.resultado());
				// Coloca nome do evento no JTextField que o represnta
				in_edicao.setText(miniGerenciador.resultado().get(2).toString());
				str_numEd  = miniGerenciador.resultado().get(2).toString();
				miniGerenciador.dispose();

				// Pode selecionar pessoa agora
				criar.setEnabled(true);

				// Popula a tabela com os organizadores daquela edicao
				preencherTabela();
			}

		}
	}

	public void onClickPessoa(){

		if(in_cargoOrg.getText().equals("")){
			JOptionPane.showMessageDialog(null, "Cargo necessario", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		else{
			// Valores que serao passados para popular a tabela do JDialog, os resultados obtidos estao nessa ordem e podem ser acessados
			// por meio de miniGerenciador.resultados().get(i);
			String[] colunas = {"idPe", "nomePe", "emailPe"};		
			String[][] dados = this.dbcon.CarregaDados("PESSOA"); // "WHERE idPe = " + idArt); 
			// Cria JDialog com a tabela que o usuario ira selecionar
			MiniGerenciador miniGerenciador =  new MiniGerenciador(this, dados, colunas);
			
			// Caso o usuario clicou em ok o resultado esta salvo em miniGerenciador.resutlado
			if(miniGerenciador.resultado() != null){
				System.out.println(miniGerenciador.resultado());
				// Coloca nome do evento no JTextField que o represnta

				String[] linha = new String[4];

				// pega Id do organizador
				linha[0] = miniGerenciador.resultado().get(0).toString();

				str_cargoOrg = "'" + in_cargoOrg.getText() + "'";

				String query = "call consistenciaPessoa.insereOrganizador(" + linha[0] + ", " + str_codEv + ", " + str_numEd + 
					", " + str_cargoOrg + ")";

				System.out.println(query);

				try{

					dbcon.executarInsert(query);
					JOptionPane.showMessageDialog(null, "Registro inserido com sucesso");
					// setVisible(false);
					// dispose();
				}catch(SQLException ex){
					GerenciadorErros.errorPanel(ex.getErrorCode());
				} 

				miniGerenciador.dispose();

				preencherTabela();
			}		
		}	
	}


	public void	onClickDeletar(){

		int linhaSelectionada = table.getSelectedRow();

		if(linhaSelectionada != -1){

			String idOrg = table.getModel().getValueAt(linhaSelectionada, 0).toString();
			String codEv = table.getModel().getValueAt(linhaSelectionada, 1).toString();
			String numEd = table.getModel().getValueAt(linhaSelectionada, 1).toString();

			String query = "call consistenciaPessoa.removeOrganizador(" + idOrg + ", " + codEv + ", " + numEd + ")";
			// String query = "DELETE FROM organiza WHERE idOrg=" + idOrg + " AND codEv=" + codEv + " AND numEd=" + numEd; 
			System.out.println(query);

			try{
				dbcon.executarInsert(query);
			}catch(SQLException ex){
				GerenciadorErros.errorPanel(ex.getErrorCode());
			}
			preencherTabela();
		}
	}
}