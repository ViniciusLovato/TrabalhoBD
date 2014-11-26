// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.sql.SQLException;


public class GerenciadorEventos extends Gerenciador{
	
	private String[][] dados;
	private static final String[] colunas = {"Codigo", "Nome", "Descricao", "Website", "Total de Artigos"};

	public GerenciadorEventos(DBConnection dbcon){
		super("Gerenciar Eventos", dbcon);

		String[] parametros = {"Codigo", "Nome", "Website"};

		dados = dbcon.CarregaDados("EVENTO");   

		configurar(parametros, colunas, dados);

		// Remove campos nao necessarios
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
			CadastrarEvento cadastrarEvento = new CadastrarEvento(dbcon);
		    cadastrarEvento.initUI();

		    dados = null;
			dados = dbcon.CarregaDados("EVENTO"); 

		    configurarTabela(dados, colunas);
			removerColuna(0);
		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{
			// Seleciona linha selecionada pelo usuario
			int linhaSelecionada =  table.getSelectedRow();

			if(linhaSelecionada != -1)
			{
				// Verificando se existe alguma linha selecionada
				System.out.println("Linha select: " + linhaSelecionada);

				// Seleciona ID do Artigo selecionado, chave primaria para remocao
				String removerId = pegarValorCelula(linhaSelecionada, 0);

				String query = "DELETE FROM EVENTO WHERE codEv = " + removerId;
				System.out.println(query);

				// Remove da tabela o artigo
				try{
					this.dbcon.executarQuery(query);
				    dados = null;
					dados = dbcon.CarregaDados("EVENTO"); 

		    		configurarTabela(dados, colunas);
					removerColuna(0);
				}
				catch(SQLException ex){
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Erro: Nenhuma linha selecionada", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getActionCommand().equals(editar.getText()))
		{

			int linhaSelecionada = table.getSelectedRow();

			if(linhaSelecionada != -1){

				String[] linha = pegarValorLinha(linhaSelecionada);


				CadastrarEvento cadastrarEvento = new CadastrarEvento(dbcon, linha);
			    cadastrarEvento.initUI();

		    	dados = null;
				dados = dbcon.CarregaDados("EVENTO"); 

		    	configurarTabela(dados, colunas);
				removerColuna(0);

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