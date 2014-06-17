package webcam;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamPicker;
import com.github.sarxos.webcam.WebcamResolution;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.log.SysoCounter;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;



/**
 * Proof of concept of how to handle webcam video stream from Java
 * 
 * @author Bartosz Firyn (SarXos)
 */
public class WebCamPhotoAutoEscola extends JDialog implements Runnable, WebcamListener, WindowListener, UncaughtExceptionHandler, ItemListener {

	private static final long serialVersionUID = 1L;
        private JDialog minhaDialog;
	private Webcam webcam = null;
	private WebcamPanel panel = null;
	private WebcamPicker picker = null;
        public String pathImgTirada="";
        private String diretorioParaSalvar,nameFile;
        
        public WebCamPhotoAutoEscola(JFrame minhaFrame, String strPath, String strNameFile) {
            super(minhaFrame, "Auto Escola", ModalityType.APPLICATION_MODAL);
            diretorioParaSalvar = strPath;
            this.nameFile = nameFile;
            minhaDialog =this;
            run();
            this.addWindowListener(new WindowListener() {

                @Override
                public void windowOpened(WindowEvent e) {}

                @Override
                public void windowClosing(WindowEvent e) {
                     System.out.println("Saindo");
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    System.out.println("Saiu");
                    panel.stop();
                    remove(panel);
                    webcam.close();
                    System.out.println(webcam.isOpen());
                    
                }

                @Override
                public void windowIconified(WindowEvent e) {}

                @Override
                public void windowDeiconified(WindowEvent e) {}

                @Override
                public void windowActivated(WindowEvent e) { }

                @Override
                public void windowDeactivated(WindowEvent e) {}
            });
        }

    
        
	@Override
	public void run() {

		setTitle("Auto Escola");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		//addWindowListener(this);

		picker = new WebcamPicker();
		picker.addItemListener(this);

		webcam = picker.getSelectedWebcam();

		if (webcam == null) {
			System.out.println("No webcams found...");
			System.exit(1);
		}

		webcam.setViewSize(WebcamResolution.VGA.getSize());
		webcam.addWebcamListener(WebCamPhotoAutoEscola.this);

		panel = new WebcamPanel(webcam, false);
		panel.setFPSDisplayed(true);
                
                JButton btTirarFoto = new JButton("Tirar Foto");
                btTirarFoto.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                            Webcam webcam = picker.getSelectedWebcam();
                            System.out.println(webcam.getName());
                            webcam.open();
                            BufferedImage image = webcam.getImage();
                            
                             try {
                                JOptionPane.showMessageDialog(null, "Cliq");
                                
                                
//                                pathImgTirada =new File("").getCanonicalPath()+ "\\src\\Resources\\FotosInstrutor\\test.jpg";
                                ImageIO.write(image, "JPG", new File(diretorioParaSalvar+"\\"+nameFile+".jpg"));
                                 pathImgTirada = diretorioParaSalvar+"\\"+nameFile+".jpg";
                                 JOptionPane.showMessageDialog(minhaDialog, "Foto tirada","Informação", JOptionPane.INFORMATION_MESSAGE);
                                 JOptionPane.showMessageDialog(minhaDialog, diretorioParaSalvar,"Informação", JOptionPane.INFORMATION_MESSAGE);
                                 minhaDialog.dispose();
                            } catch (IOException ex) {
                                 Logger.getLogger(tirarFoto.class.getName()).log(Level.SEVERE, null, ex);
                            }
                          
                    }
                });
                
		add(picker, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);
                add(btTirarFoto,BorderLayout.SOUTH);
		pack();
		setVisible(true);

		Thread t = new Thread() {

			@Override
			public void run() {
				panel.start();
			}
		};
		t.setName("example-starter");
		t.setDaemon(true);
		t.setUncaughtExceptionHandler(this);
		t.start();
	}

	

	@Override
	public void webcamOpen(WebcamEvent we) {
		System.out.println("webcam open");
	}

	@Override
	public void webcamClosed(WebcamEvent we) {
		System.out.println("webcam closed");
                
	}

	@Override
	public void webcamDisposed(WebcamEvent we) {
		System.out.println("webcam disposed");
                
	}

	@Override
	public void webcamImageObtained(WebcamEvent we) {
		// do nothing
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
            System.out.println("Fechou janela");
            
	}

	@Override
	public void windowClosing(WindowEvent e) {
            System.out.println("Fechou janela aqui");
            
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		System.out.println("webcam viewer resumed");
		panel.resume();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		System.out.println("webcam viewer paused");
		panel.pause();
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.err.println(String.format("Exception in thread %s", t.getName()));
		e.printStackTrace();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getItem() != webcam) {
			if (webcam != null) {

				panel.stop();

				remove(panel);

				webcam.removeWebcamListener(this);
				webcam.close();

				webcam = (Webcam) e.getItem();
				webcam.setViewSize(WebcamResolution.VGA.getSize());
				webcam.addWebcamListener(this);

				System.out.println("selected " + webcam.getName());

				panel = new WebcamPanel(webcam, false);

				add(panel, BorderLayout.CENTER);
				pack();

				Thread t = new Thread() {

					@Override
					public void run() {
						panel.start();
					}
				};
				t.setName("example-stoper");
				t.setDaemon(true);
				t.setUncaughtExceptionHandler(this);
				t.start();
			}
		}
	}
        
        public static void main(String[] args){ 
           // new WebCamPhotoAutoEscola(new JFrame());
        }
}
