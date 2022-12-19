package controller;

import br.ce.wcaquino.taskbackend.controller.TaskController;
import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class TaskControllerTest {

	@Mock
	public TaskRepo taskRepo;

	@InjectMocks
	public TaskController controller = new TaskController();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws ValidationException {
		Task todo = new Task();
		todo.setDueDate(LocalDate.now());
		//todo.setTask("Des");
		try {
			controller.save(todo);
			Assert.fail("Não deve chegar aqui");
		} catch (Exception e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}
	}

	@Test
	public void naoDeveSalvarTarefaSemData() {
		Task todo = new Task();
		//todo.setDueDate(LocalDate.now());
		todo.setTask("Des");
		try {
			controller.save(todo);
		} catch (Exception e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}
	}

	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		Task todo = new Task();
		todo.setTask("Descricao");
		todo.setDueDate(LocalDate.of(2010, 1, 01));
		try {
			controller.save(todo);
		} catch (Exception e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}
	}

	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		Task todo = new Task();
		todo.setTask("Descricao");
		todo.setDueDate(LocalDate.now());
		controller.save(todo);
		Mockito.verify(taskRepo).save(todo);
	}
}
