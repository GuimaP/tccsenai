package Model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import Controller.ClientePacotePagamento;

public class ModeloTableClientePacotePaga extends AbstractTableModel {

	ArrayList<ClientePacotePagamento> clienteCadastroPag;

	String[] colunas = { "Aluno", "Cpf", "Pacote", "Pre�o", "Parcelas", "Pago" };

	public ModeloTableClientePacotePaga(
			ArrayList<ClientePacotePagamento> clienteCadastroPag) {
		this.clienteCadastroPag = clienteCadastroPag;
	}

	@Override
	public int getColumnCount() {

		return colunas.length;
	}

	@Override
	public int getRowCount() {
		return clienteCadastroPag.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		switch (columnIndex) {
		case 0:
			return clienteCadastroPag.get(rowIndex).getC().getNome();
		case 1:
			return clienteCadastroPag.get(rowIndex).getC().getCpf();
		case 2:
			return clienteCadastroPag.get(rowIndex).getP().getDescricao();
		case 3:
			return clienteCadastroPag.get(rowIndex).getP().getPrecoPacote();
		case 4:
			return clienteCadastroPag.get(rowIndex).getPagamento()
					.getParcelas();
		case 5:
			return clienteCadastroPag.get(rowIndex).getPagamento()
					.getValorPago();

		default:
			break;
		}

		return null;
	}

	public String getColumnName(int column) {

		return colunas[column];
	}

}