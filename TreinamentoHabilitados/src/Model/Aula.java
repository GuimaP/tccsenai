package model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_aula")

public class Aula {
	
	@Id
	@GeneratedValue
	private long idAula;
	private String descAulas;
	private java.util.Date data;
	

	
	@ManyToOne
	@JoinColumn(name = "cpf")
	private Funcionario instrutor;
	private long idPacote;
	

	
	@ManyToOne
	@JoinColumn(name = "id")
	private Pacote pacote;
	
	
	public long getIdPacote() {
		return idPacote;
	}
	public void setIdPacote(long idPacote) {
		this.idPacote = idPacote;
	}
	public Funcionario getInstrutor() {
		return instrutor;
	}
	public void setInstrutor(Funcionario instrutor) {
		this.instrutor = instrutor;
	}

<<<<<<< HEAD

	public long getIdAula() {
		return idAula;
	}
	public void setIdAula(long idAula) {
		this.idAula = idAula;
	}
	public Date getData() {
=======
	public java.util.Date getData() {
>>>>>>> cefb0ed265212f4bbbf966bc6d5a651312ca71e0
		return data;
	}
	public void setData(java.util.Date dataSelecionada) {
		this.data = dataSelecionada;
	}
	public Pacote getPacote() {
		return pacote;
	}
	public void setPacote(Pacote pacote) {
		this.pacote = pacote;
	}
	public long getId() {
		return idAula;
	}
	public void setId(long id) {
		this.idAula = id;
	}
	public String getDescAulas() {
		return descAulas;
	}
	public void setDescAulas(String descAulas) {
		this.descAulas = descAulas;
	}

}
