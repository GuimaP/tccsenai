package View;

import Controller.ConfigController;
import DAO.DAOcliente;
import Model.Cliente;
import Model.Cnh;
import Model.Endereco;
import Model.EnumFormaca;
import Model.EnumPagamento;
import Model.EnumQuestionario;
import Model.EnumSexo;
import Model.Funcionario;
import Model.ModeloTableCliente;
import Model.ModeloTablePacote;
import Model.Pacote;
import Model.Repository.Repository;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import principal.CadastroCliente;
import principal.VerificadorDeCpf;

public class FormCadastroCliente extends JInternalFrame {

	private JLabel lbNome, lbEmail, lbEscolaridade, lbProfissao, lbNascimento,
			lbTelefone, lbPrimeiraHabilitacao, lbValidadeCnh, lbRegistroCnh,
			lbLogradouro, lbBairro, lbNumero, lbCep, lbRg, lbCpf, lbSexo,
			lbObserva, lbQ1, lbQ2, lbQ3, lbQ4, lbData, lbCelular, lbPacote,
			lbTipoPagamento, lbParcelas, lbPagamentoInicial, lbPagPendente,
			lbDtPagamento, lbDtProximoPagamento, lbBuscaAluno,
			lbNomeAluno, lbCpfAluno, lbInstrutor;
	
	private JTextField tfNome, tfEmail, tfProfissao, tfRegistroCnh,
			tfLogradouro, tfBairro, tfRg, tfQuestao1, tfNumero, tfBuscaPacote;
	
	private JFormattedTextField tfData, tfNascimento, tfCep, tfCpf, tfCelular,
			tfTelefone, tfValidadeCnh, tfPrimeiraHabilitacao,
			tfPagamentoInicial, tfPagamentoPendente, tfBuscaAluno;

	private JButton btSalvar, btBuscarPacote;

	private JTabbedPane abas;

	private JDateChooser dtPagamento, dtProximoPagamento, dtCadastroCliente, cdDataNascimento, cdValidadeCnh, cdPermissao;

	private JTextArea observa;
	
	private JScrollPane scroll, scrollPacote;
	
	private ButtonGroup gQ2, gQ3;
	
	private JRadioButton jrQ2Yes, jrQ2No, jrQ3Yes, jrQ3No;
	
	private JComboBox<EnumFormaca> jcEscolaridade;
	
	private JComboBox<EnumQuestionario> jcPesquisa;
	
	private JComboBox<EnumPagamento> jcPagamento;
	
	private JComboBox<EnumSexo> jcSexo;
	
	private JComboBox<Funcionario> jcFuncionrio;
	
	private JTable table;
	
	private ArrayList<Cliente> listCliente = new ArrayList<Cliente>();
	
	private ArrayList<Pacote> listPacote = new ArrayList<Pacote>();
	
	private ArrayList<Funcionario> listFuncionario = new ArrayList<Funcionario>();
	
	
	private MaskFormatter dataMask, dataMaskNascimento, maskCep,
			maskCpf, maskTelefone, maskCelular, maskValidadeCnh,
			maskPrimeiraHabilitacao;

	protected static JFrame minhaFrame;

	private DAOcliente daoCliente;
	Cliente cliente = new Cliente();
	Endereco endereco = new Endereco();
	Cnh cnh = new Cnh();
	CadastroCliente cadastro = new CadastroCliente();

	private JPanel panelCliente, painelGeral, abaTodos, abaAgendamento,
			painelPagamento, painelAgendamento;

	// componentes usados para o calendario de agendamento de aulas.

	private JButton btTarefa;

	private JTextArea jTextAreaObs;

	private JLabel lbDescricao;

	private JCheckBox checkCpf, checkObs;
	
	private JFormattedTextField tfBuscaAlunoCpf;
	
	private JSpinner jsParcelas;

	public FormCadastroCliente() {
		//
		try {
			Cliente c = new Cliente();
			// try {
			// daoCliente = new DAOcliente();
			// listCliente = daoCliente.buscarTodos();
			// } catch (SQLException e) {
			//
			// JOptionPane.showMessageDialog(null, e.getMessage());
			// }

			inicializaComponentes();

			definirEventos();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());

		}

	}

	public void limparCampos() {
		tfNome.setText("");
		tfData.setValue(dataMask);
		tfLogradouro.setText("");
		tfNumero.setText("");
		tfBairro.setText("");
		jcSexo.setSelectedIndex(-1);
		tfRg.setText("");
		tfCpf.setValue(maskCpf);
		tfCep.setValue(maskCep);
		tfNascimento.setValue(dataMaskNascimento);
		tfTelefone.setValue(maskTelefone);
		tfCelular.setValue(maskCelular);
		tfEmail.setText("");
		jcEscolaridade.setSelectedIndex(-1);
		tfProfissao.setText("");
		tfPrimeiraHabilitacao.setValue(maskPrimeiraHabilitacao);
		tfValidadeCnh.setValue(maskValidadeCnh);
		tfRegistroCnh.setText("");
		tfQuestao1.setText("");
		jcPesquisa.setSelectedIndex(-1);
		observa.setText("");
		cliente = null;
	}

	public void populaList() {
		try {
			for (Cliente c : listCliente) {
				ArrayList<Cliente> l = new ArrayList<Cliente>();
				l = daoCliente.buscarTodos();

				listCliente = l;
				table.setModel(new ModeloTableCliente(listCliente));
			}
		} catch (SQLException e1) {

		}
	}

	public void inicializaComponentes() throws ParseException {
		// Font que ser� usada para o nome do aluno e cpf

		abaTodos = new JPanel();
		abaTodos.setLayout(null);
		
		
		
		// Declaração do painel cliente 
		panelCliente = new JPanel();
		Border border = BorderFactory.createTitledBorder("Cliente");
		panelCliente.setBorder(border);
		panelCliente.setLayout(null);
		panelCliente.setBounds(5,10 , 380, 210);
		
		// Inicio dos componentes do painel Cliente ----------------------------------------------
		
		lbNome = new JLabel("Nome");
		lbNome.setBounds(10, 20, 100, 20);
		panelCliente.add(lbNome);

		tfNome = new JTextField();
		tfNome.setBounds(60, 20, 120, 25);
		panelCliente.add(tfNome);

		lbData = new JLabel("Data");
		lbData.setBounds(190, 20, 100, 20);
		panelCliente.add(lbData);

		dtCadastroCliente = new JDateChooser();
		dtCadastroCliente.setBounds(250	, 20, 120, 25);
		Calendar dtCadastro = Calendar.getInstance();
		dtCadastro.set(Calendar.YEAR, 1900);
		dtCadastro.set(Calendar.MONTH, 1);
		dtCadastro.set(Calendar.DATE, 1);
		dtCadastroCliente.setMinSelectableDate(dtCadastro.getTime());
		Date dataAtualCad = new Date(System.currentTimeMillis());
		dtCadastroCliente.setMaxSelectableDate(dataAtualCad);
		//dtCadastroCliente.setEnabled(false);
		panelCliente.add(dtCadastroCliente);
		
		lbLogradouro = new JLabel("Rua");
		lbLogradouro.setBounds(10, 45, 100, 20);
		panelCliente.add(lbLogradouro);

		tfLogradouro = new JTextField();
		tfLogradouro.setBounds(60, 45, 120, 25);
		panelCliente.add(tfLogradouro);
		
		lbNumero = new JLabel("Numero");
		lbNumero.setBounds(190, 45, 100, 20);
		panelCliente.add(lbNumero);

		tfNumero = new JTextField();
		tfNumero.setBounds(250, 45, 120, 25);
		panelCliente.add(tfNumero);
	
		lbBairro = new JLabel("Bairro");
		lbBairro.setBounds(10, 70, 100, 20);
		panelCliente.add(lbBairro);

		tfBairro = new JTextField();
		tfBairro.setBounds(60, 70, 120, 25);
		panelCliente.add(tfBairro);
		
		lbCep = new JLabel("Cep");
		lbCep.setBounds(190, 70, 100, 20);
		panelCliente.add(lbCep);

		maskCep = new MaskFormatter("#####-###");
		maskCep.setPlaceholderCharacter('_');
		maskCep.setValueContainsLiteralCharacters(false);
		tfCep = new JFormattedTextField(maskCep);
		tfCep.setBounds(250, 70, 120, 25);
		panelCliente.add(tfCep);
		

		lbNascimento = new JLabel("Nasc.");
		lbNascimento.setBounds(10, 95, 100, 20);
		panelCliente.add(lbNascimento);
		
		cdDataNascimento = new JDateChooser();
		cdDataNascimento.setBounds(60, 95, 120, 25);
		Calendar dtNascimento = Calendar.getInstance();
		dtNascimento.set(Calendar.YEAR, 1900);
		dtNascimento.set(Calendar.MONTH, 1);
		dtNascimento.set(Calendar.DATE, 1);
		cdDataNascimento.setMinSelectableDate(dtNascimento.getTime());
		Date dataAtualNas = new Date(System.currentTimeMillis());
		cdDataNascimento.setMaxSelectableDate(dataAtualNas);
		panelCliente.add(cdDataNascimento);
			
		lbSexo = new JLabel("Sexo");
		lbSexo.setBounds(190, 95, 100, 20);
		panelCliente.add(lbSexo);

		jcSexo = new JComboBox<EnumSexo>(EnumSexo.values());
		jcSexo.setBounds(250, 95, 120, 25);
		jcSexo.setSelectedIndex(-1);
		panelCliente.add(jcSexo);
		
		lbCpf = new JLabel("Cpf");
		lbCpf.setBounds(10, 120, 100, 20);
		panelCliente.add(lbCpf);

		maskCpf = new MaskFormatter("###.###.###-##");
		maskCpf.setPlaceholderCharacter('_');
		maskCpf.setValueContainsLiteralCharacters(false);
		tfCpf = new JFormattedTextField(maskCpf);
		tfCpf.setBounds(60, 120, 120, 25);
		panelCliente.add(tfCpf);
		
		lbRg = new JLabel("Rg");
		lbRg.setBounds(190, 120, 100, 20);
		panelCliente.add(lbRg);

		tfRg = new JTextField();
		tfRg.setBounds(250, 120, 120, 25);
		panelCliente.add(tfRg);
		
		lbTelefone = new JLabel("Tel");
		lbTelefone.setBounds(10, 145, 100, 20);
		panelCliente.add(lbTelefone);

		maskTelefone = new MaskFormatter("(##)####-####");
		maskTelefone.setPlaceholderCharacter('_');
		maskTelefone.setValueContainsLiteralCharacters(false);
		tfTelefone = new JFormattedTextField(maskTelefone);
		tfTelefone.setBounds(60, 145, 120, 25);
		panelCliente.add(tfTelefone);
		
		lbCelular = new JLabel("Cel");
		lbCelular.setBounds(190, 145, 100, 20);
		panelCliente.add(lbCelular);

		maskCelular = new MaskFormatter("(##)#-####-####");
		maskCelular.setPlaceholderCharacter('_');
		maskCelular.setValueContainsLiteralCharacters(false);
		tfCelular = new JFormattedTextField(maskCelular);
		tfCelular.setBounds(250, 145, 120, 25);
		panelCliente.add(tfCelular);
		
		lbEmail = new JLabel("E-mail");
		lbEmail.setBounds(10, 170, 100, 20);
		panelCliente.add(lbEmail);

		tfEmail = new JTextField();
		tfEmail.setBounds(60, 170, 120	, 25);
		panelCliente.add(tfEmail);
		
		lbEscolaridade = new JLabel("Formação");
		lbEscolaridade.setBounds(190, 170, 100, 20);
		panelCliente.add(lbEscolaridade);

		jcEscolaridade = new JComboBox<EnumFormaca>(EnumFormaca.values());
		jcEscolaridade.setBounds(250, 170, 120, 25);
		jcEscolaridade.setSelectedIndex(-1);
		panelCliente.add(jcEscolaridade);

		abaTodos.add(panelCliente);
		
		
		// Fim dos componentes do painel cliente -------------------------------------------------------
		
		// Painel Dados Gerais
		painelGeral = new JPanel();
		Border border2 = BorderFactory.createTitledBorder("Dados Gerais");
		painelGeral.setLayout(null);
		painelGeral.setBorder(border2);
		painelGeral.setBounds(5, 220, 380, 255);

		//Inicio dos componentes do painel geral -------------------------------------
		
		lbProfissao = new JLabel("Trabalho");
		lbProfissao.setBounds(10,20, 100	, 20);
		painelGeral.add(lbProfissao);

		tfProfissao = new JTextField();
		tfProfissao.setBounds(60, 20, 120, 25);
		painelGeral.add(tfProfissao);

		lbRegistroCnh = new JLabel("Nº Cnh");
		lbRegistroCnh.setBounds(190, 20, 100, 20);
		painelGeral.add(lbRegistroCnh);

		tfRegistroCnh = new JTextField();
		tfRegistroCnh.setBounds(250, 20, 120, 25);
		painelGeral.add(tfRegistroCnh);
		
		lbValidadeCnh = new JLabel("Validade");
		lbValidadeCnh.setBounds(10, 45, 100, 20);
		painelGeral.add(lbValidadeCnh);

		cdValidadeCnh = new JDateChooser();
		cdValidadeCnh.setBounds(60, 45, 120, 25);
		Calendar dtValiCnh = Calendar.getInstance();
		dtValiCnh.set(Calendar.YEAR, 1900);
		dtValiCnh.set(Calendar.MONTH, 1);
		dtValiCnh.set(Calendar.DATE, 1);
		cdDataNascimento.setMinSelectableDate(dtValiCnh.getTime());
		Date dataValiCnh = new Date(System.currentTimeMillis());
		cdDataNascimento.setMaxSelectableDate(dataValiCnh);
		painelGeral.add(cdValidadeCnh);
		
		lbPrimeiraHabilitacao = new JLabel("Permissão");
		lbPrimeiraHabilitacao.setBounds(190, 45, 100, 20);
		painelGeral.add(lbPrimeiraHabilitacao);

		cdPermissao = new JDateChooser();
		cdPermissao.setBounds(250	, 45, 120, 25);
		Calendar dtPermissao = Calendar.getInstance();
		dtPermissao.set(Calendar.YEAR, 1900);
		dtPermissao.set(Calendar.MONTH, 1);
		dtPermissao.set(Calendar.DATE, 1);
		cdDataNascimento.setMinSelectableDate(dtPermissao.getTime());
		Date dataPermissao = new Date(System.currentTimeMillis());
		cdDataNascimento.setMaxSelectableDate(dataPermissao);
		painelGeral.add(cdPermissao);
		
		lbQ1 = new JLabel("A quanto tempo nao dirige?");
		lbQ1.setBounds(10, 70, 300, 20);
		painelGeral.add(lbQ1);

		tfQuestao1 = new JTextField();
		tfQuestao1.setBounds(190, 70, 180, 25);
		painelGeral.add(tfQuestao1);
		
		lbQ2 = new JLabel("Tem veiculo proprio?");
		lbQ2.setBounds(10, 95, 300, 20);
		painelGeral.add(lbQ2);

		JPanel painelQ2 = new JPanel();
		painelQ2.setBounds(250, 95, 100, 25);

		jrQ2Yes = new JRadioButton("Sim", true);
		jrQ2Yes.setBounds(250, 95, 200, 25);

		jrQ2No = new JRadioButton("Não", false);
		jrQ2No.setBounds(300, 95, 200, 25);
		
		gQ2 = new ButtonGroup();
		gQ2.add(jrQ2Yes);
		gQ2.add(jrQ2No);
		painelQ2.add(jrQ2Yes);
		painelQ2.add(jrQ2No);

		painelGeral.add(painelQ2);
		
		JPanel painelQ3 = new JPanel(); 
		painelQ3.setBounds(240, 120, 120, 25);
		lbQ3 = new JLabel("É possivel treinar nele?");
		lbQ3.setBounds(10, 120, 160, 20);
		painelGeral.add(lbQ3);

		jrQ3Yes = new JRadioButton("Sim", true);
		jrQ3Yes.setBounds(1,120, 60, 20);

		jrQ3No = new JRadioButton("Não", false);
		jrQ3No.setBounds(300,120, 60, 20);

		gQ3 = new ButtonGroup();
		gQ3.add(jrQ3Yes);
		gQ3.add(jrQ3No);
		painelQ3.add(jrQ3Yes);
		painelQ3.add(jrQ3No);

		painelGeral.add(painelQ3);

		lbQ4 = new JLabel("Como você soube da Karol Treinamentos?");
		lbQ4.setBounds(10, 145, 300, 20);
		painelGeral.add(lbQ4);

		jcPesquisa = new JComboBox<EnumQuestionario>(EnumQuestionario.values());
		jcPesquisa.setBounds(250, 145, 120, 25);
		jcPesquisa.setSelectedIndex(-1);
		painelGeral.add(jcPesquisa);

		lbObserva = new JLabel("Observações:");
		lbObserva.setBounds(10, 170, 120, 20);
		painelGeral.add(lbObserva);

		// Obs
		observa = new JTextArea();
		observa.setLineWrap(true);
		scroll = new JScrollPane(observa);
		scroll.setBounds(10, 195, 360, 50);
		painelGeral.add(scroll);

		abaTodos.add(painelGeral);

		// Bot�o
		btSalvar = new JButton("Salvar");
		btSalvar.setBounds(610	, 415, 180, 35);
		abaTodos.add(btSalvar);

		tfBuscaPacote = new JTextField();
		tfBuscaPacote.setBounds(530, 28, 250, 25);
		abaTodos.add(tfBuscaPacote);

		btBuscarPacote = new JButton("Buscar");
		btBuscarPacote.setBounds(785, 28, 80, 25);
		abaTodos.add(btBuscarPacote);

		// Text

		// Table
		lbPacote = new JLabel("Pacote");
		lbPacote.setBounds(530, 10, 100, 20);
		abaTodos.add(lbPacote);

		table = new JTable(new ModeloTablePacote(listPacote));
		scrollPacote = new JScrollPane(table);
		scrollPacote.setBounds(530, 54, 340, 163);
		abaTodos.add(scrollPacote);

		// Dados do pagamento

		painelPagamento = new JPanel();
		Border borderPag = BorderFactory
				.createTitledBorder("Dados do Pagamento");
		painelPagamento.setBorder(borderPag);
		painelPagamento.setLayout(null);
		painelPagamento.setBounds(525, 220, 350, 180);
		abaTodos.add(painelPagamento);

		lbDtPagamento = new JLabel("Data");
		lbDtPagamento.setBounds(10, 20, 120, 20);
		painelPagamento.add(lbDtPagamento);

		dtPagamento = new JDateChooser();
		dtPagamento.setBounds(170, 20, 170, 25);

		Calendar minimo = Calendar.getInstance();
		minimo.set(Calendar.YEAR, 1900);
		minimo.set(Calendar.MONTH, 1);
		minimo.set(Calendar.DATE, 1);
		dtPagamento.setMinSelectableDate(minimo.getTime());
		Date dataAtual = new Date(System.currentTimeMillis());
		dtPagamento.setMaxSelectableDate(dataAtual);
		painelPagamento.add(dtPagamento);

		lbParcelas = new JLabel("Parcelas");
		lbParcelas.setBounds(10, 45, 120, 20);
		painelPagamento.add(lbParcelas);

		SpinnerNumberModel nbparcelasModel;
		nbparcelasModel = new SpinnerNumberModel(1,1,12,1);
		jsParcelas = new JSpinner(nbparcelasModel);
		jsParcelas.setBounds(170, 45, 170, 25);
		painelPagamento.add(jsParcelas);
		
		lbDtProximoPagamento = new JLabel("Próxima Parcela");
		lbDtProximoPagamento.setBounds(10, 70, 120, 20);
		painelPagamento.add(lbDtProximoPagamento);

		dtProximoPagamento = new JDateChooser();
		dtProximoPagamento.setBounds(170, 70, 170, 25);

		Calendar minimo1 = Calendar.getInstance();
		minimo1.set(Calendar.YEAR, 1900);
		minimo1.set(Calendar.MONTH, 1);
		minimo1.set(Calendar.DATE, 1);
		dtProximoPagamento.setMinSelectableDate(minimo.getTime());
		Date dataAtual2 = new Date(System.currentTimeMillis());
		dtProximoPagamento.setMaxSelectableDate(dataAtual2);
		dtProximoPagamento.setEnabled(false);
		painelPagamento.add(dtProximoPagamento);

		lbTipoPagamento = new JLabel("Tipo de pagamento");
		lbTipoPagamento.setBounds(10, 95, 120, 20);
		painelPagamento.add(lbTipoPagamento);

		jcPagamento = new JComboBox<EnumPagamento>(EnumPagamento.values());
		jcPagamento.setBounds(170, 95, 170, 25);
		jcPagamento.setSelectedIndex(-1);
		painelPagamento.add(jcPagamento);

		lbPagamentoInicial = new JLabel("Valor Pago");
		lbPagamentoInicial.setBounds(10, 120, 120, 20);
		painelPagamento.add(lbPagamentoInicial);

		tfPagamentoInicial = new JFormattedTextField();
		tfPagamentoInicial.setBounds(170, 120, 170, 25);
		painelPagamento.add(tfPagamentoInicial);

		lbPagPendente = new JLabel("Pendente");
		lbPagPendente.setBounds(10, 145, 120, 20);
		painelPagamento.add(lbPagPendente);

		tfPagamentoPendente = new JFormattedTextField();
		tfPagamentoPendente.setBounds(170, 145, 170, 25);
		tfPagamentoPendente.setEnabled(false);
		painelPagamento.add(tfPagamentoPendente);

		// Componentes painel de agendamento

		
		
		abaAgendamento = new JPanel();
		abaAgendamento.setLayout(null);
		
		

		painelAgendamento = new JPanel();
		Border border3 = BorderFactory.createTitledBorder("Agendamento");
		painelAgendamento.setLayout(null);
		painelAgendamento.setBorder(border3);
		painelAgendamento.setBounds(5, 10, 420, 465);
		
		lbBuscaAluno = new JLabel("Buscar Aluno");
		lbBuscaAluno.setSize(200, 20);
		lbBuscaAluno.setLocation(10, 20);
		painelAgendamento.add(lbBuscaAluno);

		checkCpf = new JCheckBox();
		checkCpf.setText("CPF");
		checkCpf.setBounds(220, 40, 100, 20);
		checkCpf.setSelected(false);
		painelAgendamento.add(checkCpf);

		checkObs = new JCheckBox();
		checkObs.setText("Descrição");
		checkObs.setBounds(310, 320, 100, 20);
		checkObs.setSelected(false);
		painelAgendamento.add(checkObs);
		
		tfBuscaAluno = new JFormattedTextField();
		tfBuscaAluno.setSize(200, 30);
		tfBuscaAluno.setLocation(10, 40);
		painelAgendamento.add(tfBuscaAluno);
		
		tfBuscaAlunoCpf = new JFormattedTextField(maskCpf);
		tfBuscaAlunoCpf.setSize(200, 30);
		tfBuscaAlunoCpf.setLocation(10, 40);
		painelAgendamento.add(tfBuscaAlunoCpf);

		// String que vai aparecer com o nome do aluno que foi feito a busca.

		lbNomeAluno = new JLabel("'Vitor Barros'"); // Teste
		lbNomeAluno.setSize(1000, 50);
		lbNomeAluno.setLocation(10, 60);
		lbNomeAluno.setFont(ConfigController.definePrincipalFont(22f,
				Font.PLAIN));
		lbNomeAluno.setForeground(new Color(72 ,118, 255));
		painelAgendamento.add(lbNomeAluno);

		lbCpfAluno = new JLabel("322.999.999-99");
		lbCpfAluno.setSize(1000, 50);
		lbCpfAluno.setLocation(10, 90);
		lbCpfAluno
				.setFont(ConfigController.definePrincipalFont(22, Font.PLAIN));
		lbCpfAluno.setForeground(new Color(72 ,118, 255));
		painelAgendamento.add(lbCpfAluno);

		
		lbInstrutor = new JLabel("Istrutor");
		lbInstrutor.setBounds(240, 80, 100, 20);
		painelAgendamento.add(lbInstrutor);
		
		jcFuncionrio = new JComboBox<Funcionario>();
		jcFuncionrio.setBounds(240, 100, 170, 25);
		painelAgendamento.add(jcFuncionrio);
		
		
		// -------------------------------------------------------------------

		// Calendario
		new PainelCalendarioAulas(painelAgendamento);

		// --------------------------------------------

		lbDescricao = new JLabel("Descrição Aula:");
		lbDescricao.setBounds(10, 310, 100, 20);
		lbDescricao.setVisible(false);
		painelAgendamento.add(lbDescricao);
		
		jTextAreaObs = new JTextArea();
		jTextAreaObs.setBounds(10, 335, 280, 100);
		jTextAreaObs.setVisible(false);
		painelAgendamento.add(jTextAreaObs);

		

		
		btTarefa = new JButton("Agendar");
		btTarefa.setBounds(160, 360, 100, 35);
		btTarefa.setToolTipText("Agendar aula");
		painelAgendamento.add(btTarefa);

		
		abaAgendamento.add(painelAgendamento);
		
		
		abas = new JTabbedPane();
		abas.setBounds(1, 1, 890, 535);
		abas.addTab("Cadastro", abaTodos);
		abas.addTab("Agendamento", abaAgendamento);
		add(abas);

		// PAINEL//
		getContentPane().setLayout(null);
		setSize(895, 540);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setClosable(true);
		setTitle("Cliente");
		setResizable(false);
		setIconifiable(true);

	}

	public void definirEventos() {
		
		jcPagamento.addActionListener(new ActionListener() {
			int estado =1;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jcPagamento.getSelectedIndex()== 1){
					jsParcelas.setEnabled(false);
				}else{
					jsParcelas.setEnabled(true);
				}
					
				
			}
		});
		
		jsParcelas.addChangeListener(new ChangeListener() {
			int estado =1;
			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println("passei");
				
				if (!jsParcelas.getValue().equals(estado)){
					dtProximoPagamento.setEnabled(true);
				}else {
					dtProximoPagamento.setEnabled(false);
				}
				
			}
		});
		
		
		
		checkObs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (checkObs.isSelected()){
					jTextAreaObs.setVisible(true);
				lbDescricao.setVisible(true);
					btTarefa.setLocation(310,345);
				}else{
					jTextAreaObs.setVisible(false);
					lbDescricao.setVisible(false);
					btTarefa.setLocation(160,360);
				}
				
			}
		});
		
		checkCpf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(checkCpf.isSelected()){
					tfBuscaAluno.setVisible(false);
					tfBuscaAlunoCpf.setVisible(true);
				}else {
					tfBuscaAluno.setVisible(true);
					tfBuscaAlunoCpf.setVisible(false);
				}
				
			}
		});

		btSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// valida��es
				tfNome.setText(tfNome.getText().trim());
				tfData.setText(tfData.getText().trim());
				tfLogradouro.setText(tfLogradouro.getText().trim());
				tfNumero.setText(tfNumero.getText().trim());
				tfBairro.setText(tfBairro.getText().trim());
				tfCep.setText(tfCep.getText().trim());
				tfNascimento.setText(tfNascimento.getText().trim());
				tfBairro.setText(tfBairro.getText().trim());
				tfRg.setText(tfRg.getText().trim());
				tfEmail.setText(tfEmail.getText().trim());
				tfProfissao.setText(tfProfissao.getText().trim());
				tfQuestao1.setText(tfQuestao1.getText().trim());

				VerificadorDeCpf verificador = new VerificadorDeCpf();

				if (tfNome.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Informar o nome");
					lbNome.setForeground(Color.red);
					tfNome.requestFocus(true);
				} else if (tfData.getValue() == null) {
					JOptionPane.showMessageDialog(null, "Informar a data");
					lbData.setForeground(Color.red);
					tfData.requestFocus(true);
				} else if (tfLogradouro.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Informar a rua");
					lbLogradouro.setForeground(Color.red);
					tfLogradouro.requestFocus(true);
				} else if (tfNumero.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Informar o numero");
					lbNumero.setForeground(Color.red);
					tfNumero.requestFocus(true);
				} else if (tfCep.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Informar o cep");
					lbCep.setForeground(Color.red);
					tfCep.requestFocus(true);
				} else if (tfNascimento.getValue() == null) {
					JOptionPane.showMessageDialog(null,
							"Informar a data de nascimento");
					lbNascimento.setForeground(Color.red);
					tfNascimento.requestFocus(true);
				} else if (tfCep.getValue() == null) {
					JOptionPane.showMessageDialog(null, "Informar o cep");
					lbCep.setForeground(Color.red);
					tfCep.requestFocus(true);
				} else if (jcSexo.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Informar o sexo");
					jcSexo.requestFocus(true);
				} else if (tfCpf.getValue() == null
						|| !verificador.verificarCpf(tfCpf.getValue()
								.toString())) {
					JOptionPane.showMessageDialog(null, "Cpf invalido");
					lbCpf.setForeground(Color.red);
					tfCpf.requestFocus(true);
				} else if (tfRegistroCnh.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Informar o numero de registro da CNH");
					lbRegistroCnh.setForeground(Color.red);
					tfRegistroCnh.requestFocus(true);
				} else if (tfValidadeCnh.getValue() == null) {
					JOptionPane.showMessageDialog(null,
							"Informar a data de validade da CNH");
					lbValidadeCnh.setForeground(Color.red);
					tfValidadeCnh.requestFocus(true);
				} else if (tfPrimeiraHabilitacao.getValue() == null) {
					JOptionPane.showMessageDialog(null,
							"Informar a data da permi��o");
					lbPrimeiraHabilitacao.setForeground(Color.red);
					tfPrimeiraHabilitacao.requestFocus(true);
				} else if (jcPesquisa.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null,
							"Selecionar uma das opções");
					jcPesquisa.requestFocus(true);
				} else {

					// populando os objetos
					cliente.setNome(tfNome.getText());
					cadastro.setData(tfData.getText());
					endereco.setLogradouro(tfLogradouro.getText());

					endereco.setNumero(Long.parseLong(tfNumero.getText()));
					endereco.setBairro(tfBairro.getText());
					endereco.setCep(tfCep.getText());
					Date dt = new Date(tfNascimento.getText());
					cliente.setNascimento(dt);
					cliente.setSexo(jcSexo.getSelectedItem().toString());
					cliente.setCpf(tfCpf.getText());
					cliente.setRg(tfRg.getText());
					cliente.setTelefone(tfTelefone.getText());
					cliente.setCelular(tfCelular.getText());
					cliente.setEmail(tfEmail.getText());
					if (jcEscolaridade.getSelectedIndex() == -1) {
						cliente.setEscolaridade("");
					} else {
						cliente.setEscolaridade(jcEscolaridade
								.getSelectedItem().toString());
					}
					cliente.setProfissao(tfProfissao.getText());
					cnh.setRegistroCnh(tfRegistroCnh.getText());
					cnh.setDtValidade(tfValidadeCnh.getText());
					cnh.setPrimeiraHabilitacao(tfPrimeiraHabilitacao.getText());
					cadastro.setPesquisa1(tfQuestao1.getText());
					if (jrQ2Yes.isSelected()) {
						cadastro.setPesquisa2("sim");
					} else {
						cadastro.setPesquisa2("não");
					}
					if (jrQ3Yes.isSelected()) {
						cadastro.setPesquisa3("sim");
					} else {
						cadastro.setPesquisa3("não");
					}
					cadastro.setPesquisa4(jcPesquisa.getSelectedItem()
							.toString());
					cadastro.setObservacao(observa.getText());

					try {
						Repository<Cliente> repo = new Repository<Cliente>();
						repo.adicionar(cliente);
					} catch (Exception erro) {
						JOptionPane.showMessageDialog(null, erro.getMessage());
					}

					// populaList();
					limparCampos();

					// TODO n�o esquecer de aplicar o ternario para os
					// FORMATEDTEXTFIELD
					// limpar os campos ap�s o cadastro.
				}

			}
		});

		tfNome.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				int caracteres = 50;
				if (tfNome.getText().length() >= caracteres
						&& e.getKeyCode() != 8) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				tfNome.setText(tfNome.getText().toUpperCase());
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		tfLogradouro.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				int caracteres = 50;
				if (tfLogradouro.getText().length() >= caracteres
						&& e.getKeyCode() != 8) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				tfLogradouro.setText(tfLogradouro.getText().toUpperCase());

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		tfNumero.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				int caracteres = 8;
				if (tfNumero.getText().length() >= caracteres
						&& e.getKeyCode() != 8) {
					e.consume();
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		tfBairro.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				int caracteres = 30;
				if (tfBairro.getText().length() >= caracteres
						&& e.getKeyCode() != 8) {
					e.consume();
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				tfBairro.setText(tfBairro.getText().toUpperCase());

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		tfEmail.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				int caracter = 40;
				if (tfEmail.getText().length() >= caracter
						&& e.getKeyCode() != 8) {
					e.consume();
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				tfEmail.setText(tfEmail.getText().toLowerCase());

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		tfProfissao.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				int caracter = 25;
				if (tfProfissao.getText().length() >= caracter
						&& e.getKeyCode() != 8) {
					e.consume();
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				tfProfissao.setText(tfProfissao.getText().toUpperCase());

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		this.addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosing(InternalFrameEvent arg0) {
				Principal.isFrameClienteOpen = false;
			}
		});

	}

}
