// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciadorEdicao extends Gerenciador{
    String[][] dados;

	public GerenciadorEdicao(DBConnection dbcon){
		super("Gerenciar Edicao", dbcon);
		String[] parametros = {"Numero"};
		String[] colunas = {"Numero Evento", "Numero Edicao", "Descricao", "Data Inicio", "Data fim", "Local", "Taxa" ,"Saldo", "Total de Artigos"};
		
		dados = dbcon.CarregaDados("EDICAO");   

		configurar(parametros, colunas, dados);

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
			String[] eventos = {"Churras na casa do Brunao"};
			CadastrarEdicao cadastrarEdicao = new CadastrarEdicao();

			try{
				cadastrarEdicao.initUI(eventos);
			}
			catch(Exception ex){
				System.out.println("Erro");
			}
		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{
			// Seleciona linha selecionada pelo usuario
			int linhaSelecionada =  table.getSelectedRow();

			// Seleciona ID do Artigo selecionado, chave primaria para remocao
			String codEv = dados[linhaSelecionada][0];
			String numEd = dados[linhaSelecionada][1];

			String query = "DELETE FROM EDICAO WHERE codEv = " + codEv + " AND numEd = " + numEd;
			System.out.println(query);

			// Remove da tabela o artigo
			this.dbcon.executarQuery(query);

			removerLinha(linhaSelecionada);
		}
		else if(e.getActionCommand().equals(editar.getText()))
		{

		}
		else if(e.getActionCommand().equals(voltar.getText()))
		{
			setVisible(false);
			dispose();
		}
	}
}