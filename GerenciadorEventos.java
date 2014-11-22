// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciadorEventos extends Gerenciador{
	String[][] dados;

	public GerenciadorEventos(DBConnection dbcon){
		super("Gerenciar Eventos", dbcon);

		String[] parametros = {"Codigo", "Nome", "Website"};
		String[] colunas = {"Codigo", "Nome", "Descricao", "Website", "Total de Artigos"};

		dados = dbcon.CarregaDados("EVENTO");   

		configurar(parametros, colunas, dados);

		// Remove campos nao necessarios
		this.table.removeColumn(this.table.getColumnModel().getColumn(0));
		
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
			CadastrarEvento cadastrarEvento = new CadastrarEvento();
		    cadastrarEvento.initUI();
		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{
			// Seleciona linha selecionada pelo usuario
			int linhaSelecionada =  table.getSelectedRow();

			// Seleciona ID do Artigo selecionado, chave primaria para remocao
			String removerId = dados[linhaSelecionada][0];

			String query = "DELETE FROM EVENTO WHERE codEv = " + removerId;
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