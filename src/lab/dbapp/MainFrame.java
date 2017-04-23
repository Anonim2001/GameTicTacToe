package lab.dbapp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
//import javax.swing.LayoutStyle.ComponentPlacement;
//import java.awt.CardLayout;
//import javax.swing.BoxLayout;
//import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class MainFrame {

	private JFrame frame;
	private static final String PROPERTIES_PATH = "db/hsqldb.properties";
	private DbParams dbParams = null;
	private boolean isDbReady = false;
	private JTextField textField;
	private JTextField textField_1;
	private ExecutorService kreator = Executors.newSingleThreadExecutor();
	private JButton btnButton_1, btnNewButton_2, btnNewButton_3, btnNewButton_4, btnNewButton_5,btnNewButton_6, btnNewButton_7,btnNewButton_8,btnNewButton_9;
	private boolean akcja = false;
	private boolean koniec_gry = false;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	
	/**
	 * Launch the application.
	 */
	private JTable pobierzDaneDoTabeli() {
		JTable tabela = null;
		if (isDbReady) {
			ResultSet rs = null;
			Statement stmt = null;
			Connection connect = null;
			try {
				connect = DriverManager.getConnection(dbParams.getDbUrl(), dbParams.getDbUser(),
						dbParams.getDbPassword());
				stmt = connect.createStatement();

				String query = "SELECT ZWYCIEZCA, PRZEGRANY, DATA FROM WYNIKI";
				rs = stmt.executeQuery(query);
				if (rs != null) {
					tabela = new JTable();
					DefaultTableModel modelTabeli = (DefaultTableModel) tabela.getModel();
					String[] nazwyKolumn = { "Zwyciezca","Przegrany", "Data" };
					modelTabeli.setColumnIdentifiers(nazwyKolumn);

					while (rs.next()) {
						Object[] objects = new Object[3];
						objects[0] = rs.getString("ZWYCIEZCA");
						objects[1] = rs.getString("PRZEGRANY");
						objects[2] = rs.getDate("DATA");
						modelTabeli.addRow(objects);
					}
				}

			} catch (SQLException e) {
				System.out.println(e.getMessage() + ", State: " + e.getSQLState());
				e.printStackTrace();
			} finally {
				if (stmt != null)
					try {
						stmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if (connect != null)
					try {
						connect.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}

		return tabela;
	}

			

	private void initDbParams(){
		DbParams dbParams = new DbParams();
		try {
			if (dbParams.loadParams(PROPERTIES_PATH))
				if (Util.registerDBDriver(dbParams.getDriverUrl(),dbParams.getDriverClass())){
					isDbReady = true;
					this.dbParams = dbParams;
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 554, 424);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel Rozgrywka = new JPanel();
		tabbedPane.addTab("Rozgrywka", null, Rozgrywka, null);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JButton btnRozgrywka = new JButton("Nowa Rozgrywka");

		
		JPanel panel_gry = new JPanel();
		
		lblNewLabel = new JLabel("Gracz z X");
		
		lblNewLabel_1 = new JLabel("Gracz z O");
		GroupLayout gl_Rozgrywka = new GroupLayout(Rozgrywka);
		gl_Rozgrywka.setHorizontalGroup(
			gl_Rozgrywka.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Rozgrywka.createSequentialGroup()
					.addGroup(gl_Rozgrywka.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_Rozgrywka.createSequentialGroup()
							.addGap(19)
							.addGroup(gl_Rozgrywka.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_Rozgrywka.createSequentialGroup()
									.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(2))
								.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(28)
							.addGroup(gl_Rozgrywka.createParallelGroup(Alignment.TRAILING)
								.addComponent(textField_1, Alignment.LEADING)
								.addComponent(textField, Alignment.LEADING)))
						.addGroup(gl_Rozgrywka.createSequentialGroup()
							.addGap(61)
							.addComponent(btnRozgrywka, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(3)))
					.addGap(32)
					.addComponent(panel_gry, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_Rozgrywka.setVerticalGroup(
			gl_Rozgrywka.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Rozgrywka.createSequentialGroup()
					.addGap(36)
					.addGroup(gl_Rozgrywka.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField)
						.addComponent(lblNewLabel))
					.addGap(18)
					.addGroup(gl_Rozgrywka.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_gry, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
						.addGroup(gl_Rozgrywka.createSequentialGroup()
							.addGroup(gl_Rozgrywka.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_1)
								.addComponent(lblNewLabel_1))
							.addGap(68)
							.addComponent(btnRozgrywka)
							.addGap(69)))
					.addGap(103))
		);
		panel_gry.setLayout(new GridLayout(3, 3, 0, 0));
		btnRozgrywka.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				kreator.execute(new Runnable() {

					public void run() {
						btnButton_1.setText("");
						btnNewButton_2.setText("");
						btnNewButton_3.setText("");
						btnNewButton_4.setText("");
						btnNewButton_5.setText("");
						btnNewButton_6.setText("");
						btnNewButton_7.setText("");
						btnNewButton_8.setText("");
						btnNewButton_9.setText("");
						btnButton_1.setEnabled(true);
						btnNewButton_2.setEnabled(true);
						btnNewButton_3.setEnabled(true);
						btnNewButton_4.setEnabled(true);
						btnNewButton_5.setEnabled(true);
						btnNewButton_6.setEnabled(true);
						btnNewButton_7.setEnabled(true);
						btnNewButton_8.setEnabled(true);
						btnNewButton_9.setEnabled(true);
						akcja = false;
						koniec_gry = false;
						
					}
					});
				
			}
		});
		btnButton_1 = new JButton("");
		btnButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kreator.execute(new Runnable() {
					public void run() {
						if (akcja == false){
							btnButton_1.setText("x");
							akcja=true;
						}else{
							btnButton_1.setText("o");
							akcja=false;
						}
				btnButton_1.setEnabled(false);
				sprawdzanie();
					}
				});
			}
		});
		panel_gry.add(btnButton_1);
		
		btnNewButton_2 = new JButton("");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kreator.execute(new Runnable() {
					public void run() {
						if (akcja == false){
							btnNewButton_2.setText("x");
							akcja=true;
						}else{
							btnNewButton_2.setText("o");
							akcja=false;
						}
						btnNewButton_2.setEnabled(false);
						sprawdzanie();
			}
		});
			}
		});
		panel_gry.add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kreator.execute(new Runnable() {
					public void run() {
						if (akcja == false){
							btnNewButton_3.setText("x");
							akcja=true;
						}else{
							btnNewButton_3.setText("o");
							akcja=false;
						}
						btnNewButton_3.setEnabled(false);
						sprawdzanie();
					}
				});
				}
		});
		panel_gry.add(btnNewButton_3);
		
		btnNewButton_4 = new JButton("");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kreator.execute(new Runnable() {
					public void run() {
						if (akcja == false){
							btnNewButton_4.setText("x");
							akcja=true;
						}else{
							btnNewButton_4.setText("o");
							akcja=false;
						}
						btnNewButton_4.setEnabled(false);
						sprawdzanie();
					}
				});
			}
		});
		panel_gry.add(btnNewButton_4);
		
		btnNewButton_5 = new JButton("");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kreator.execute(new Runnable() {
					public void run() {
						if (akcja == false){
							btnNewButton_5.setText("x");
							akcja=true;
						}else{
							btnNewButton_5.setText("o");
							akcja=false;
						}
						btnNewButton_5.setEnabled(false);
						sprawdzanie();
			}
		});
			}
		});
		panel_gry.add(btnNewButton_5);
		
		btnNewButton_6 = new JButton("");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kreator.execute(new Runnable() {
					public void run() {
						if (akcja == false){
							btnNewButton_6.setText("x");
							akcja=true;
						}else{
							btnNewButton_6.setText("o");
							akcja=false;
						}
						btnNewButton_6.setEnabled(false);
						sprawdzanie();
					}
				});
				}
		});
		panel_gry.add(btnNewButton_6);
		
		btnNewButton_7 = new JButton("");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kreator.execute(new Runnable() {
					public void run() {
						if (akcja == false){
							btnNewButton_7.setText("x");
							akcja=true;
						}else{
							btnNewButton_7.setText("o");
							akcja=false;
						}
						btnNewButton_7.setEnabled(false);
						sprawdzanie();
			}
		});
			}
		});
		panel_gry.add(btnNewButton_7);
		
		btnNewButton_8 = new JButton("");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kreator.execute(new Runnable() {
					public void run() {
						if (akcja == false){
							btnNewButton_8.setText("x");
							akcja=true;
						}else{
							btnNewButton_8.setText("o");
							akcja=false;
						}
						btnNewButton_8.setEnabled(false);
						sprawdzanie();
					}
				});
				}
		});
		panel_gry.add(btnNewButton_8);
		
		btnNewButton_9 = new JButton("");
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				kreator.execute(new Runnable() {
					public void run() {
						if (akcja == false){
							btnNewButton_9.setText("x");
							akcja=true;
						}else{
							btnNewButton_9.setText("o");
							akcja=false;
						}
						btnNewButton_9.setEnabled(false);
						sprawdzanie();
						
					}
				});
				}
		});
		panel_gry.add(btnNewButton_9);
		Rozgrywka.setLayout(gl_Rozgrywka);
		
		JPanel wyniki = new JPanel();
		tabbedPane.addTab("Wyniki", null, wyniki, null);
		
		scrollPane = new JScrollPane();
		
		JButton Wyniki = new JButton("Wyniki");
		Wyniki.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kreator.execute(new Runnable() {
					public void run() {
						JTable tabela = pobierzDaneDoTabeli();
						if (tabela != null)
							tabela.setRowHeight(2 * tabela.getRowHeight());
						scrollPane.setViewportView(tabela);
					}
				});
				}
		});
		GroupLayout gl_wyniki = new GroupLayout(wyniki);
		gl_wyniki.setHorizontalGroup(
			gl_wyniki.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_wyniki.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_wyniki.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_wyniki.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_wyniki.createSequentialGroup()
							.addGap(242)
							.addComponent(Wyniki, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(218))))
		);
		gl_wyniki.setVerticalGroup(
			gl_wyniki.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_wyniki.createSequentialGroup()
					.addGap(33)
					.addComponent(Wyniki)
					.addGap(32)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
					.addContainerGap())
		);
		wyniki.setLayout(gl_wyniki);
		initDbParams();
	}
	public void sprawdzanie(){
		if(btnButton_1.getText()=="x" & btnNewButton_2.getText()=="x" & btnNewButton_3.getText()=="x"){
			JOptionPane.showMessageDialog(null, "wygra³ x");
			dodaj_wygranego_x();
			koniec_gry = true;
		}
		if(btnNewButton_4.getText()=="x" & btnNewButton_5.getText()=="x" & btnNewButton_6.getText()=="x"){
			JOptionPane.showMessageDialog(null, "wygra³ x");
			dodaj_wygranego_x();
			koniec_gry = true;
		}
		if(btnNewButton_7.getText()=="x" & btnNewButton_8.getText()=="x" & btnNewButton_9.getText()=="x"){
			JOptionPane.showMessageDialog(null, "wygra³ x");
			dodaj_wygranego_x();
			koniec_gry = true;
		}
		if(btnButton_1.getText()=="x" & btnNewButton_4.getText()=="x" & btnNewButton_7.getText()=="x"){
			JOptionPane.showMessageDialog(null, "wygra³ x");
			dodaj_wygranego_x();
			koniec_gry = true;
		}
		if(btnNewButton_2.getText()=="x" & btnNewButton_5.getText()=="x" & btnNewButton_8.getText()=="x"){
			JOptionPane.showMessageDialog(null, "wygra³ x");
			dodaj_wygranego_x();
			koniec_gry = true;
		}
		if(btnNewButton_3.getText()=="x" & btnNewButton_6.getText()=="x" & btnNewButton_9.getText()=="x"){
			JOptionPane.showMessageDialog(null, "wygra³ x");
			dodaj_wygranego_x();
			koniec_gry = true;
		}
		if(btnButton_1.getText()=="x" & btnNewButton_5.getText()=="x" & btnNewButton_9.getText()=="x"){
			JOptionPane.showMessageDialog(null, "wygra³ x");
			dodaj_wygranego_x();
			koniec_gry = true;
		}
		if(btnNewButton_3.getText()=="x" & btnNewButton_5.getText()=="x" & btnNewButton_7.getText()=="x"){
			JOptionPane.showMessageDialog(null, "wygra³ x");
			dodaj_wygranego_x();
			koniec_gry = true;
		}
		if(btnButton_1.getText()=="o" & btnNewButton_2.getText()=="o" & btnNewButton_3.getText()=="o"){
			JOptionPane.showMessageDialog(null, "wygra³ o");
			dodaj_wygranego_o();
			koniec_gry = true;
		}
		if(btnNewButton_4.getText()=="o" & btnNewButton_5.getText()=="o" & btnNewButton_6.getText()=="o"){
			JOptionPane.showMessageDialog(null, "wygra³ o");
			dodaj_wygranego_o();
			koniec_gry = true;
		}
		if(btnNewButton_7.getText()=="o" & btnNewButton_8.getText()=="o" & btnNewButton_9.getText()=="o"){
			JOptionPane.showMessageDialog(null, "wygra³ o");
			dodaj_wygranego_o();
			koniec_gry = true;
		}
		if(btnButton_1.getText()=="o" & btnNewButton_4.getText()=="o" & btnNewButton_7.getText()=="o"){
			JOptionPane.showMessageDialog(null, "wygra³ o");
			dodaj_wygranego_o();
			koniec_gry = true;
		}
		if(btnNewButton_2.getText()=="o" & btnNewButton_5.getText()=="o" & btnNewButton_8.getText()=="o"){
			JOptionPane.showMessageDialog(null, "wygra³ o");
			dodaj_wygranego_o();
			koniec_gry = true;
		}
		if(btnNewButton_3.getText()=="o" & btnNewButton_6.getText()=="o" & btnNewButton_9.getText()=="o"){
			JOptionPane.showMessageDialog(null, "wygra³ o");
			dodaj_wygranego_o();
			koniec_gry = true;
		}
		if(btnButton_1.getText()=="o" & btnNewButton_5.getText()=="o" & btnNewButton_9.getText()=="o"){
			JOptionPane.showMessageDialog(null, "wygra³ o");
			dodaj_wygranego_o();
			koniec_gry = true;
		}
		if(btnNewButton_3.getText()=="o" & btnNewButton_5.getText()=="o" & btnNewButton_7.getText()=="o"){
			JOptionPane.showMessageDialog(null, "wygra³ o");
			dodaj_wygranego_o();
			koniec_gry = true;
		}
		if ((koniec_gry == false) &(btnButton_1.getText()=="o" | btnButton_1.getText()=="x") & (btnNewButton_2.getText()=="o" || btnNewButton_2.getText()=="x") 
			&	(btnNewButton_3.getText()=="o" | btnNewButton_3.getText()=="x") & (btnNewButton_4.getText()=="o" | btnNewButton_4.getText()=="x") 
			&	(btnNewButton_5.getText()=="o" | btnNewButton_5.getText()=="x") & (btnNewButton_6.getText()=="o" | btnNewButton_6.getText()=="x") 
			&	(btnNewButton_7.getText()=="o" | btnNewButton_7.getText()=="x") & (btnNewButton_8.getText()=="o" | btnNewButton_8.getText()=="x") 
			&	(btnNewButton_9.getText()=="o" | btnNewButton_9.getText()=="x"))
		{
			JOptionPane.showMessageDialog(null, "No to mamy remis");
		}
	}

	public void dodaj_wygranego_o()
	{
		if (isDbReady) {
			// .getClass(). ResultSet rs = null;
			Statement stmt = null;
			Connection connect = null;

			try {
				connect = DriverManager.getConnection(dbParams.getDbUrl(), dbParams.getDbUser(),
						dbParams.getDbPassword());
				stmt = connect.createStatement();
				String wygrany = textField_1.getText();
				String przegrany = textField.getText();
				String query = "INSERT INTO WYNIKI (zwyciezca,przegrany, data) " + "Values('"+wygrany+"','"+przegrany+"', now())";

				stmt.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void dodaj_wygranego_x()
	{
		if (isDbReady) {
			// .getClass(). ResultSet rs = null;
			Statement stmt = null;
			Connection connect = null;

			try {
				connect = DriverManager.getConnection(dbParams.getDbUrl(), dbParams.getDbUser(),
						dbParams.getDbPassword());
				stmt = connect.createStatement();
				String wygrany = textField.getText();
				String przegrany = textField_1.getText();
				String query = "INSERT INTO WYNIKI (zwyciezca,przegrany, data) " + "Values('"+wygrany+"','"+przegrany+"', now())";

				stmt.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
