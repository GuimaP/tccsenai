package main;

import model.Login;
import model.repository.ConnectionFactoryConfig;
import model.repository.ConnectionFactoryRepository;
import View.MyPainelInvisible;
import View.Principal;
import View.TelaLogin;

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

import org.hibernate.Session;
import org.hibernate.exception.GenericJDBCException;

import com.ibm.media.ShowDocumentEvent;

import controller.ConfigController;
import controller.LogController;

public class Start {

	protected static boolean isLoading = true;
	public static Session session;

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException {
		try {
			
			setPrimeiraConfig();
			
			
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				System.out.println(info.getName());
				if ("Nimbus".equals(info.getName())) {
					System.out.println("is nimbus");
					UIManager.setLookAndFeel(info.getClassName());
					
					
					Thread t = new Thread(new Runnable() {

						@Override
						public void run() {
							String[] mensagens = { ".              Carregando...",
									". Mais Alguns Instantes..." };
							JFrame janela = new JFrame("Carregando");
							janela.setIconImage(ConfigController.defineIcon());
							janela.setUndecorated(true);
							janela.setSize(400, 400);
						//	janela.setOpacity(0.4f);
							janela.setDefaultCloseOperation(EXIT_ON_CLOSE);

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
						}
						/*
						 * http://stackoverflow.com/questions/11703794/how-to-set-jframe-background-transparent-but-jpanel-or-jlabel-background-opaque
						 */
					
					

					});
					t.start();
					System.out.println("Inicando Log");
					// LogController.insertLog(new Exception("Iniciando"));

					
					ConnectionFactoryConfig.generateSession();//.openSession();
					

					isLoading = false;
					TelaLogin tela = new TelaLogin();
//
//					Login l = new Login();
//					l.setUsuario("guima");
//					l.setSenha("123");
//					new Principal(l);
//					break;
				}

			}
		} catch (IllegalAccessException | UnsupportedLookAndFeelException e) {
			//LogController.insertLog(e);
			e.printStackTrace();
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
			diretorioProgram.mkdir(); //Se n�o houve, eu crio uma
		}		
	}

}
