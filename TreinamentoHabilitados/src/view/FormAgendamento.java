/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.*;
import model.table.ModeloTableCliente;
import model.table.ModeloTableFuncionario;

/**
 *
 * @author Vitor
 */
public class FormAgendamento extends JInternalFrame {
	private JLabel lbBuscaCeliente, lbBuscaInstrutor, lbBuscaPacote;

	private JTextField tfBuscaCliente, tfBuscaInstrutor;

	private JPanel panelAluno, panelInstrutor;

	private JTable tabelaAluno, tabelaInstrutor;

	private JScrollPane scroll, scrollInstrutor;


	private JInternalFrame internal;
	
	private JTabbedPane abas;
	
	private JPanel pnGeral, pnBusca;
	

	private ArrayList<Cliente> listCliente = new ArrayList<Cliente>();
	
	private ArrayList<Funcionario> listInstrutor = new ArrayList<Funcionario>();

	public FormAgendamento() {
		internal = this;
		inicializaComponentes();

	}

	public void inicializaComponentes() {
		// Paineis
		pnGeral = new JPanel();
		pnGeral.setLayout(null);
		pnBusca = new JPanel();
		pnBusca.setLayout(null);
		
		
		// Painel Geral.
		//Info Clintes.

		lbBuscaCeliente = new JLabel("Buscar Cliente");
		lbBuscaCeliente.setSize(150, 20);
		lbBuscaCeliente.setLocation(5, 40);
		pnGeral.add(lbBuscaCeliente);

		tfBuscaCliente = new JTextField();
		tfBuscaCliente.setSize(280, 25);
		tfBuscaCliente.setLocation(5, 60);
		pnGeral.add(tfBuscaCliente);

		tabelaAluno = new JTable(new ModeloTableCliente(listCliente));
		scroll = new JScrollPane(tabelaAluno);
		scroll.setSize(280, 120);
		scroll.setLocation(5, 90);
		pnGeral.add(scroll);

		//Info Instrutor
		lbBuscaInstrutor = new JLabel("Buscar Instrutor");
		lbBuscaInstrutor.setSize(150, 20);
		lbBuscaInstrutor.setLocation(5, 220);
		pnGeral.add(lbBuscaInstrutor);
		
		tfBuscaInstrutor = new JTextField();
		tfBuscaInstrutor.setSize(280, 25);
		tfBuscaInstrutor.setLocation(5, 240);
		pnGeral.add(tfBuscaInstrutor);
		
		tabelaInstrutor = new JTable(new ModeloTableFuncionario(listInstrutor));
		scrollInstrutor = new JScrollPane(tabelaInstrutor);
		scrollInstrutor.setSize(280, 120);
		scrollInstrutor.setLocation(5, 270);
		pnGeral.add(scrollInstrutor);
		
		//Info Pacotes.
		lbBuscaPacote = new JLabel("Buscar Servico");
		lbBuscaPacote.setSize(150, 20);
		lbBuscaPacote.setLocation(5, 400);
		pnGeral.add(lbBuscaPacote);
		
		
		
			
		abas = new JTabbedPane();
		abas.setBounds(1, 1, 695, 645);
		abas.addTab("Cadastro", pnGeral);
		abas.addTab("Busca", pnBusca);
		add(abas);
		
		getContentPane().setLayout(null);
		setSize(700, 650);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setClosable(true);
		setTitle("Agendamento de aulas");
		setResizable(false);
		setIconifiable(true);

	}

}
