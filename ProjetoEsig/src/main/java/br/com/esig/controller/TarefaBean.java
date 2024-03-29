package br.com.esig.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import br.com.esig.dao.DaoGeneric;
import br.com.esig.model.Tarefa;

@Named
@SessionScoped
public class TarefaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Tarefa tarefas;

	private DaoGeneric<Tarefa> daoGeneric = new DaoGeneric<Tarefa>();
	private List<Tarefa> tarefaListada = new ArrayList<Tarefa>();

	public String salvaNovaTarefa() {
		daoGeneric.salvar(tarefas);
		tarefas = new Tarefa();
		listarTarefas();
		return "";
	}

	public String limparCampos() {
		tarefas = new Tarefa();
		listarTarefas();
		return "";
	}

	public String excluirTarefa() {
		daoGeneric.excluir(tarefas);
		listarTarefas();
		return "";
	}

	public String excluirTarefaListada(Tarefa listada) {
		daoGeneric.excluir(listada);
		listarTarefas();
		return "";
	}

	public String atualizarTarefa() {
		daoGeneric.merge(tarefas);
		tarefas = new Tarefa();
		listarTarefas();
		return "";
	}

	public void onRowEdit(RowEditEvent<Tarefa> event) {

		tarefas = new Tarefa();

		tarefas.setId(event.getObject().getId());
		tarefas.setStatus(event.getObject().getStatus());
		tarefas.setTitulo(event.getObject().getTitulo());
		tarefas.setDescricao(event.getObject().getDescricao());
		tarefas.setResponsavel(event.getObject().getResponsavel());
		tarefas.setPrioridade(event.getObject().getPrioridade());
		tarefas.setData(event.getObject().getData());

		FacesMessage msg = new FacesMessage("Gerencie sua Tarefa");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent<Tarefa> event) {
		FacesMessage msg = new FacesMessage("Edição Cancelada");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void concluirTarefa(Tarefa listada) {
		listada.setStatus("Concluída");
		daoGeneric.merge(listada);
		FacesMessage msg = new FacesMessage("Tarefa Concluída");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		listarTarefas();
	}

	@PostConstruct
	public void listarTarefas() {
		tarefaListada = daoGeneric.getListEntity(Tarefa.class);
	}

	public Tarefa getTarefas() {
		return tarefas;
	}

	public void setTarefas(Tarefa tarefas) {
		this.tarefas = tarefas;
	}

	public DaoGeneric<Tarefa> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Tarefa> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}

	public List<Tarefa> getTarefaListada() {
		return tarefaListada;
	}

}
