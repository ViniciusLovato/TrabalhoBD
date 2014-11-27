// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.SQLException;


public class GerenciadorPessoa extends Gerenciador{

	private String[][] dados;
	private static final String[] colunas = {"Id", "Nome", "Email", "Instituicao", "Telefone", "Nacionalidade", "Endereco", "Organizador", "Participante", "Autor"};
	private static final String[] parametros = {"Nome", "Email", "Instituicao", "Nacionalidade"};
	private static final int[] position = {1, 2, 3, 5};

	public GerenciadorPessoa(DBConnection dbcon){
		super("Gerenciar Pessoa", dbcon);

		dados = dbcon.CarregaDados("PESSOA");   

		//String[][] dados = null;
		configurar(parametros, position, colunas, dados);

		// Esconde as colunas que nao sao necessarias ao usuario final
		removerColuna(0);

		criar.addActionListener(this);
		deletar.addActionListener(this);
		editar.addActionListener(this);
		voltar.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		System.out.println(e.getActionCommand());
	
		if(e.getActionCommand().equals(criar.getText()))
		{
			CadastrarPessoas cadPe = new CadastrarPessoas(dbcon);


			try{
				cadPe.initUI();
				
		      	dados = null;
				dados = dbcon.CarregaDados("PESSOA"); 

		  		configurarTabela(dados, colunas);
		  		removerColuna(0);

			}
			catch(Exception ex){
				// GerenciadorErros.errorPanel(ex.getErrorCode());									
			}
		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{
			int linhaSelecionada =  table.getSelectedRow();

			if(linhaSelecionada != -1)
			{
				// Seleciona ID do Artigo selecionado, chave primaria para remocao
				String removerId = pegarValorCelula(linhaSelecionada, 0);

				String query = "DELETE FROM PESSOA WHERE idPe = " + removerId;
				System.out.println(query);

				// Remove da tabela o artigo
				try{
					this.dbcon.executarQuery(query);

			      	dados = null;
					dados = dbcon.CarregaDados("PESSOA"); 

			  		configurarTabela(dados, colunas);
			  		removerColuna(0);
				}
				catch(SQLException ex){
					GerenciadorErros.errorPanel(ex.getErrorCode());									
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getActionCommand().equals(editar.getText()))
		{
			int linhaSelecionada = table.getSelectedRow();

			if(linhaSelecionada != -1){

				String[] linha = pegarValorLinha(linhaSelecionada);

				try{
					CadastrarPessoas cadastrarEvento = new CadastrarPessoas(dbcon, linha);
				    cadastrarEvento.initUI();

			    	dados = null;
					dados = dbcon.CarregaDados("PESSOA"); 

			    	configurarTabela(dados, colunas);
			    	removerColuna(0);
				}
				catch(Exception ex){
					//	GerenciadorErros.errorPanel(ex.getErrorCode());									
				}

			}
			else
			{
				JOptionPane.showMessageDialog(null, "Erro: Nenhuma linha selecionada", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getActionCommand().equals(voltar.getText()))
		{
			setVisible(false);
			dispose();
		}
	}
}