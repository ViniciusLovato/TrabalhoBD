// Event Listener
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.SQLException;



public class GerenciadorArtigo extends Gerenciador{

	String[][] dados;

	public GerenciadorArtigo(DBConnection dbcon){
		super("Gerenciar Artigo", dbcon);
		String[] parametros = {"Titulo", "Data Apresentacao", "Evento", "Edicao"};
		String[] colunas = {"Codigo", "Titulo", "Data", "Hora", "Evento", "Edicao", "Codigo Apresentador"};

		dados = dbcon.CarregaDados("ARTIGO");   
		configurar(parametros, colunas, dados);

		// Remove campos nao necessaios 
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
			CadastrarArtigos cadArt = new CadastrarArtigos(dbcon);
			cadArt.initUI();
		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{
			// Seleciona linha selecionada pelo usuario
			int linhaSelecionada =  table.getSelectedRow();

			if(linhaSelecionada != -1){


				// Seleciona ID do Artigo selecionado, chave primaria para remocao
				String removerId = dados[linhaSelecionada][0];

				String query = "DELETE FROM ARTIGO WHERE idArt = " + removerId;
				System.out.println(query);

				// Remove da tabela o artigo
				try{
					this.dbcon.executarQuery(query);
					removerLinha(linhaSelecionada);
				}
				catch(SQLException ex){
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

				}
			}
			else{
				JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			
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
	//idArt, tituloArt, dataApresArt, horaApresArt, codEv, numEd, idAp
}