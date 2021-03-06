package main;

import model.Login;
import model.repository.ConnectionFactoryConfig;
import model.repository.ConnectionFactoryRepository;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.management.RuntimeErrorException;
import javax.persistence.EntityManager;
import javax.swing.JFrame;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.bridj.jawt.JAWT.GetComponent_callback;
import org.hibernate.Session;
import org.hibernate.exception.GenericJDBCException;

import view.MyPainelInvisible;
import view.Principal;
import view.TelaLogin;
import controller.ConfigController;
import controller.LogController;

public class Start extends Application{

	protected static boolean isLoading = true;
	public static Session session;

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException {
		try {
			
			setPrimeiraConfig();
			
			
					
		
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			
		//	UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
				
			
			String[] mensagens = { ".              Carregando...",
			". Mais Alguns Instantes..." };
			JFrame janela = new JFrame("Carregando");
			janela.setIconImage(ConfigController.defineIcon());
			janela.setUndecorated(true);
			janela.setSize(400, 400);
			janela.setLocationRelativeTo(null);
			janela.setBackground(new Color(0,0,0,2)); //Fundo da Frame deixa transparente
			janela.setContentPane(new MyPainelInvisible()); //Defino a imagem como opaque e visivel
			janela.setLayout(new BorderLayout());
			JLabel lb = new JLabel("Carregando");
			lb.setFont(ConfigController.definePrincipalFont(30f, Font.BOLD));
			lb.setForeground(Color.black);
			janela.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			janela.getContentPane().add(lb);
			janela.setAlwaysOnTop(true);
			janela.setVisible(true);
			
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					//ConnectionFactoryConfig.openManger().openSession();
					try {
						ConnectionFactoryRepository.getManager();
					} catch (SQLException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Houve um falha ao conectar com o banco! Descri������o do erro "
                    			+ "\n''"+e.getMessage()+"''");
						System.exit(0);
						}
					isLoading = false;
				}
				/*
				 * http://stackoverflow.com/questions/11703794/how-to-set-jframe-background-transparent-but-jpanel-or-jlabel-background-opaque
				 */
			
			

			});
			t.start();

			int cont = 0;
			while (Start.isLoading) {
				try {
					lb.setText(mensagens[(cont % 2)]);
					Thread.sleep(1 * 1000);
					cont++;

					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					Logger.getLogger(Start.class.getName())
							.log(Level.SEVERE, null, ex);
				}

			}
			janela.dispose();
					

					
					
					
			launch(args);
			
			
			
			
			



		//} catch (IllegalAccessException | UnsupportedLookAndFeelException e) {
	//		e.printStackTrace();
		}catch (GenericJDBCException e) {
			JOptionPane.showMessageDialog(null, "Falha ao conectar no banco");
			throw new ExceptionInInitializerError(e);
		}

	}

	private static void setPrimeiraConfig() {
		String local = System.getProperty("user.home"); //Pego o diretorio do usuario
		String nameFile = System.getProperty("file.separator") + "Treinamento";//e o nome da pasta 
		File diretorioProgram = new File(local+nameFile); 
		if(!diretorioProgram.exists()){ //Verifico se existe a pasta
			diretorioProgram.mkdir(); //Se n���o houve, eu crio uma
		}		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/telaLogin.fxml"));
		primaryStage.setTitle("Login");
		primaryStage.setScene(new Scene(root, 465,223));
		primaryStage.show();
		
	}

}
