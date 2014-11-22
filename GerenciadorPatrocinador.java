import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciadorPatrocinador extends Gerenciador{
	String[][] dados;

	public GerenciadorPatrocinador(DBConnection dbcon){
		super("Gerenciar Patrocinador", dbcon);
		String[] parametros = {"CNPJ", "Razao Social", "Telefone", "Endereco"};
		String[] colunas = {"CNPJ", "Razao Social", "Telefone", "Endereco"};
		
		dados = dbcon.CarregaDados("PATROCINADOR");   

		configurar(parametros, colunas, dados);
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

		}
		else if(e.getActionCommand().equals(deletar.getText()))
		{

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