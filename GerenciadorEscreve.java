import java.sql.SQLException;
// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import java.text.ParseException;
import javax.swing.*;

public class GerenciadorEscreve extends Gerenciador{
	private String[][] dados;
	private static final String[] colunas = {};

	private JLabel artigo;
	private JLabel empty;
	private JTextField in_artigo;
	private JButton selecionarArtigo;

	private String str_idArtigo;

	public GerenciadorEscreve(DBConnection dbcon){
		super("Gerenciar Escreve", dbcon);
		String[] parametros = {};

		// dados = dbcon.CarregaDados();   
		configurar(parametros, colunas, dados);

		artigo = new JLabel("Artigo Selecionado:");
		in_artigo = new JTextField(50);
		empty = new JLabel(" ");
		selecionarArtigo = new JButton("Selecione o Artigo");

		in_artigo.setEditable(false);

		parametrosPanel.add(artigo);
		parametrosPanel.add(in_artigo);
		parametrosPanel.add(empty);
		parametrosPanel.add(selecionarArtigo);

		// cnpjPat, codEvPat, numEdPat, codEvApr, numEdApr, idApr, valorAux, dataAux, tipoAux

		criar.addActionListener(this);
		deletar.addActionListener(this);
		// editar.addActionListener(this);
		voltar.addActionListener(this);
		selecionarArtigo.addActionListener(this);

		editar.setVisible(false);
		criar.setText("Selecionar Pessoa");
		criar.setEnabled(false);

	}

	public void actionPerformed(ActionEvent e)
	{
		System.out.println(e.getActionCommand());
	
		if(e.getActionCommand().equals(criar.getText()))
		{
			onClickApresentador();
		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{
			onClickDeletar();
		}
		else if(e.getActionCommand().equals(editar.getText()))
		{
		}
		else if(e.getActionCommand().equals(selecionarArtigo.getText()))
		{
			onClickSelecionarArtigo();

		}
		else if(e.getActionCommand().equals(voltar.getText()))
		{
			setVisible(false);
			dispose();
		}
	}

	public void preencherTabela(){

		String[] colunas = {"idAut", "idArt", "NomePe", "emailPe"};
		String[][] dados = this.dbcon.CarregaDados("formataSaidaEscreve", " WHERE idArt=" + str_idArtigo);

		configurarTabela(dados, colunas);

		table.removeColumn(table.getColumn("idAut"));
		table.removeColumn(table.getColumn("idArt"));

	}

	public void onClickSelecionarArtigo(){

		String[] colunas = {"idArt", "TituloArt", "dataApresArt", "horaApresArt", "codEv",
		 "numEd", "idApr", "nomePe", "nomeEv"};
		String[][] dados = this.dbcon.CarregaDados("formataSaidaArtigo");

		MiniGerenciador miniGerenciador = new MiniGerenciador(this, dados, colunas);

		if(miniGerenciador.resultado() != null){
			str_idArtigo = miniGerenciador.resultado().get(0).toString();
			System.out.println(str_idArtigo);
			in_artigo.setText(miniGerenciador.resultado().get(1).toString());

			criar.setEnabled(true);
		}

		preencherTabela();

 	}

	public void onClickApresentador(){

		if(in_artigo == null){
			JOptionPane.showMessageDialog(null, "Artigo nao escolhido", "Erro", JOptionPane.ERROR_MESSAGE);
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

				// pega Id do autor
				linha[0] = miniGerenciador.resultado().get(0).toString();


				String query = "INSERT INTO escreve VALUES(" + linha[0] + ", " + str_idArtigo + ")";

				System.out.println(query);

				try{
					dbcon.executarInsert(query);
					JOptionPane.showMessageDialog(null, "Registro inserido com sucesso");

					// setVisible(false);
					// dispose();

				}catch(Exception ex){
					System.err.println(ex.getMessage()); 
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				} 

				miniGerenciador.dispose();

				preencherTabela();
			}		
		}	
	}

	public void	onClickDeletar(){

		int linhaSelectionada = table.getSelectedRow();

		if(linhaSelectionada != -1){

			String idAut = table.getModel().getValueAt(linhaSelectionada, 0).toString();
			String idArt = table.getModel().getValueAt(linhaSelectionada, 1).toString();

			String query = "DELETE FROM escreve WHERE idAut=" + idAut + " AND idArt=" + idArt; 

			System.out.println(query);

			try{
				dbcon.executarInsert(query);
			}catch(Exception ex){
				System.err.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}

			preencherTabela();
			
		}
	}
}