// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.SQLException;


public class GerenciadorInscrito extends Gerenciador{

	private String[][] dados;
	private static final String[] colunas = {"codEv", "numEd", "idPart", "dataInsc", "tipoApresentador", 
	"Nome do Evento", "Descricao da edicao", "Nome do Inscrito"};
	private static final String[] parametros = {"Nome do Inscrito", "Nome do evento", "Descricao da edicao"};
	private static final int[] position = {7, 5, 6};

	public GerenciadorInscrito(DBConnection dbcon){
		super("Gerenciar Inscritos", dbcon);

		dados = dbcon.CarregaDados("formataSaidaInscrito");   

		// Configurando tabela no gerenciador
		configurar(parametros, position, colunas, dados);

		// Esconde as colunas que nao sao necessarias ao usuario final
		removerColuna(0);
		//this.table.removeColumn(this.table.getColumnModel().getColumn(0));

		// Callback dos botoes da interface
		criar.addActionListener(this);
		deletar.addActionListener(this);
		editar.addActionListener(this);
		voltar.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{	
		// Verificando se o botao escolhido foi criar
		if(e.getActionCommand().equals(criar.getText()))
		{
			// Criando nova janela de cadastro
			CadastrarInscrito cadInsc = new CadastrarInscrito(dbcon);

			try{
				// Configurando a interface do usuario
				cadInsc.initUI();
				// Recarregando a tabela
		      	dados = null;
				dados = dbcon.CarregaDados("formataSaidaInscrito"); 

		  		configurarTabela(dados, colunas);
		  		removerColuna(0);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		// Verificando se o botao escolhido foi deletar
		else if(e.getActionCommand().equals(deletar.getText()))
		{
			int linhaSelecionada =  table.getSelectedRow();

			// Verificando qual coluna da tabela foi selecionada
			// Se alguma foi selecionada
			if(linhaSelecionada != -1)
			{
				// Seleciona ID do Artigo selecionado, chave primaria para remocao
				String str_codEv = pegarValorCelula(linhaSelecionada, 0);
				String str_numEd = pegarValorCelula(linhaSelecionada, 1);
				String str_idPart =pegarValorCelula(linhaSelecionada, 2);

				// Query para a delecao do inscrito
				String query = "DELETE FROM inscrito WHERE idPart = " + str_idPart + " AND codEv = " + str_codEv + " AND numEd = " + str_numEd;
				System.out.println(query);

				// Remove da tabela o inscrito
				try{
					// Executando a query  no banco
					this.dbcon.executarQuery(query);

					// Recarrega a tabela da interface
			      	dados = null;
					dados = dbcon.CarregaDados("formataSaidaInscrito"); 

			  		configurarTabela(dados, colunas);
			  		removerColuna(0);
				}
				catch(SQLException ex){
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
			// Verifica se nenhuma linha foi selecionada para a delecao
			else
			{
				JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
		// Verifica se opcao de edicao foi escolhida
		else if(e.getActionCommand().equals(editar.getText()))
		{
			int linhaSelecionada = table.getSelectedRow();

			// Verificacao de qual linha foi selecionada
			if(linhaSelecionada != -1){

				String[] linha = pegarValorLinha(linhaSelecionada);

				try
				{
					// Abre janela de edicao (janela de cadastro com parametros diferentes)
					CadastrarInscrito cadastrarEvento = new CadastrarInscrito(dbcon, linha);
				    cadastrarEvento.initUI();
				}
				catch(Exception ex)
				{
					System.out.println("Erro");
				}

			    // Apos insercao recarrega tabela da interface
		    	dados = null;
				dados = dbcon.CarregaDados("formataSaidaInscrito"); 

		    	configurarTabela(dados, colunas);
		    	removerColuna(0);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Erro: Nenhuma linha selecionada", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
		// verifica se a opcao voltar foi escolhida
		else if(e.getActionCommand().equals(voltar.getText()))
		{
			setVisible(false);
			dispose();
		}
	}
}