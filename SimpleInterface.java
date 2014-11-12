// Event Listners
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Grid layout
import java.awt.GridLayout;

// Basic components for the interface
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

// Borders
import javax.swing.border.EmptyBorder;

// ArryaList
import java.util.ArrayList;

public class SimpleInterface extends JFrame implements ActionListener
{
	// JPanel that is going to have everything connected
	private JPanel panel;

	// ArrayList of Labels
	private ArrayList<JLabel> labels;

	// ArrayList of textFields
	private ArrayList<JComponent> fields;

	// Buttons
	private JButton okay;
	private JButton cancel;

	// Number of forms
	private int numberOfFields;

	public SimpleInterface(String windowTitle)
	{
		// Title of the window
		setTitle(windowTitle);
		// Setting up close button
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// Initializing the arrays 
		labels = new ArrayList<JLabel>();
		fields = new ArrayList<JComponent>();

		// Initializing the arrays 
		okay = new JButton("Okay");
		cancel = new JButton("Cancel");

		// Initializing the number of forms
		numberOfFields = 0;
	}

	// Method to add fields to the interface
	public void addTextField(String description)
	{
		// Adding the fields into the arrays
		labels.add(new JLabel(description));
		fields.add(new JTextField(70));
		numberOfFields++;
	}

	// Method to add combo boxes to the interface
	public void addComboBox(String description, String[] options)
	{
		// Adding the fields into the arrays
		labels.add(new JLabel(description));
		fields.add(new JComboBox<String>(options));
		numberOfFields++;
	}

	public void initUI()
	{
		// Creating the panel
		panel = new JPanel();
		// Setting up the grid layout
		panel.setLayout(new GridLayout(2*numberOfFields + 2, 0, 5, 5));
		// Adding the panel to the frame
		getContentPane().add(panel);

		// Creating and applying the border
		EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);
		panel.setBorder(emptyBorder);

		// Adding the fields to the panel
		for(int i = 0; i < labels.size(); i++)
		{
			panel.add(labels.get(i));
			panel.add(fields.get(i));
		}

		// Adding the buttons to the panel
		panel.add(okay);
		panel.add(cancel);

		okay.addActionListener(this);
		cancel.addActionListener(this);

		// Customizing the Frame options
		// Size of the window
		pack();
		// Centralizing the window
		setLocationRelativeTo(null);
		// Making it visible
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		// Checking if it is button okay
		if(e.getActionCommand().equals(okay.getText()))
		{
			onClickOkay();
		}
		else if(e.getActionCommand().equals(cancel.getText()))
		{
			onClickCancel();
		}
	}

	// Okay event
	// Method to be overwriten in each new class
	public void onClickOkay()
	{
		System.exit(0);
	}

	// Cancel event
	// Method to be overwriten in each new class
	public void onClickCancel()
	{
		System.exit(0);
	}

	public static void main(String args[])
	{
		String[] b = {"Sim", "Nao"};
		// Creating the new interface
		SimpleInterface ui = new SimpleInterface("Cadastro de Usuario");

		// Adding fields
		ui.addTextField("Nome: ");
		ui.addTextField("E-mail: ");
		ui.addTextField("Instituicao: ");
		ui.addTextField("Telefone: ");
		ui.addTextField("Nacionalidade: ");
		ui.addTextField("Endereco: ");

		ui.addComboBox("Tipo participante", b);
		ui.addComboBox("Tipo autor", b);
		ui.addComboBox("Tipo organizador", b);

		// initializing the UI
		ui.initUI();
	}
}
