import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.SQLException;



public class GerenciadorPatrocinador extends Gerenciador{
	private String[][] dados;
	private static final String[] colunas = {"CNPJ", "Razao Social", "Telefone", "Endereco"};
	private static final String[] parametros = {"CNPJ", "Razao Social", "Telefone", "Endereco"};
	private static final int[] position = {0, 1, 2, 3};


	public GerenciadorPatrocinador(DBConnection dbcon){
		super("Gerenciar Patrocinador", dbcon);
		
		dados = dbcon.CarregaDados("PATROCINADOR");   

		configurar(parametros, position, colunas, dados);
		//cnpjPat, razaoSocialPat, telefonePat, enderecoPat

		
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

			try{
				CadastrarPatrocinador cadPat = new CadastrarPatrocinador(dbcon);

				cadPat.initUI();
		      	dados = null;
				dados = dbcon.CarregaDados("PATROCINADOR"); 

		  		configurarTabela(dados, colunas);

			}
			catch(Exception ex){
				System.out.println("Erro");
			}

		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{
			int linhaSelecionada =  table.getSelectedRow();

			if(linhaSelecionada != -1)
			{
				// Seleciona ID do Artigo selecionado, chave primaria para remocao
				String removerId = pegarValorCelula(linhaSelecionada, 0);

				String query = "DELETE FROM PATROCINADOR WHERE CNPJPat = " + removerId;
				System.out.println(query);

				// Remove da tabela o artigo
				try{
					this.dbcon.executarQuery(query);

			      	dados = null;
					dados = dbcon.CarregaDados("PATROCINADOR"); 

		  			configurarTabela(dados, colunas);
				}
				catch(SQLException ex){
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

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

				try
				{
					CadastrarPatrocinador cadastrarEvento = new CadastrarPatrocinador(dbcon, linha);
				    cadastrarEvento.initUI();
				}
				catch(Exception exception)
				{
					System.out.println(exception);
				}
				
		    	dados = null;
				dados = dbcon.CarregaDados("PATROCINADOR"); 

		    	configurarTabela(dados, colunas);

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