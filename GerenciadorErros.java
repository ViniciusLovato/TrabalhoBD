import javax.swing.JOptionPane;

public class GerenciadorErros{

	// String de dados que sera usado para preencher a tabe
	public static void errorPanel(int errorCode){
		if(errorCode == 936){
			JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		else if(errorCode == 1){
			JOptionPane.showMessageDialog(null, "Cadastro ja existente", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		else if(errorCode == 1400){
			JOptionPane.showMessageDialog(null, "Campos obrigatorios estao em branco", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
}