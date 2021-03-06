package org.rest.annotations.resources;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.wicketstuff.rest.annotations.AuthorizeInvocation;
import org.wicketstuff.rest.annotations.MethodMapping;
import org.wicketstuff.rest.annotations.parameters.RequestBody;
import org.wicketstuff.rest.annotations.parameters.ValidatorKey;
import org.wicketstuff.rest.contenthandling.json.objserialdeserial.JacksonObjectSerialDeserial;
import org.wicketstuff.rest.contenthandling.json.webserialdeserial.JsonWebSerialDeserial;
import org.wicketstuff.rest.resource.AbstractRestResource;
import org.wicketstuff.rest.utils.http.HttpMethod;

import de.alpharogroup.test.objects.Employee;
import de.alpharogroup.test.objects.Gender;
import de.alpharogroup.test.objects.Person;

/**
 * The Class EmployeeResource.
 */
public class EmployeeResource extends AbstractRestResource<JsonWebSerialDeserial>
{

	/** The serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The employees. */
	private final List<Employee> employees;

	{

		employees = new ArrayList<>();
		employees
			.add(
				Employee
					.builder().person(Person.builder().gender(Gender.FEMALE).name("Anna")
						.married(true).about("Ha ha ha...").nickname("beast").build())
					.id("23").build());
		employees
			.add(Employee
				.builder().person(Person.builder().gender(Gender.MALE).name("Andreas")
					.married(false).about("fine person").nickname("cute").build())
				.id("24").build());
		employees
			.add(Employee
				.builder().person(Person.builder().gender(Gender.FEMALE).name("Tatjana")
					.married(false).about("Im hot").nickname("beautiful").build())
				.id("25").build());
	}

	/**
	 * Instantiates a new employee resource.
	 */
	public EmployeeResource(IRoleCheckingStrategy roleCheckingStrategy)
	{
		super(new JsonWebSerialDeserial(new JacksonObjectSerialDeserial()), roleCheckingStrategy);
	}

	// http://localhost:8080/employeesmanager/create
	/**
	 * Create an employee from the given employee object. For instance if the host is localhost the
	 * the url POST call would be: http://localhost:8080/employeesmanager/create and you have to put
	 * in the post data a json string for create the employee.
	 *
	 * @param id
	 *            the id
	 * @return the employee
	 */
	@MethodMapping(value = "/create", httpMethod = HttpMethod.POST)
	@AuthorizeInvocation(value = { "FOO", "BAR" })
	public Employee create(@ValidatorKey("employeeValidator") @RequestBody Employee employee)
	{
		employees.add(employee);
		System.out.println(extractUrlFromRequest());
		System.out.println("new employee added.");
		return employee;
	}

	@MethodMapping(value = "/delete/{id}", httpMethod = HttpMethod.DELETE)
	public void delete(String id)
	{
		System.out.println(extractUrlFromRequest());
		for (Employee employee : employees)
		{
			if (employee.getId().equals(id))
			{
				employees.remove(employee);
			}
		}
	}

	/**
	 * Gets all employees. For instance if the host is localhost the the url GET call would be:
	 * http://localhost:8080/employeesmanager/employees
	 *
	 * @return the all
	 */
	@MethodMapping("/employees")
	public List<Employee> getAll()
	{
		return employees;
	}


	@Override
	protected void onInitialize(JsonWebSerialDeserial objSerialDeserial)
	{
		super.onInitialize(objSerialDeserial);
		registerValidator("employeeValidator", new EmployeeValidator());
	}

	// http://localhost:8080/employeesmanager/read/24
	/**
	 * Read the employee with the given id. For instance if the host is localhost the the url GET
	 * call would be: http://localhost:8080/employeesmanager/read/24
	 *
	 * @param id
	 *            the id
	 * @return the employee
	 */
	@MethodMapping("/read/{id}")
	public Employee read(String id)
	{
		for (Employee employee : employees)
		{
			if (employee.getId().equals(id))
			{
				return employee;
			}
		}
		System.err.println("an error occured...");
		return Employee.builder().build();
	}


	@MethodMapping(value = "/update", httpMethod = HttpMethod.PUT)
	public void update(@ValidatorKey("employeeValidator") @RequestBody Employee employeeToUpdate)
	{
		System.out.println(extractUrlFromRequest());
		for (Employee employee : employees)
		{
			if (employee.getId().equals(employeeToUpdate.getId()))
			{
				employees.remove(employee);
				employees.add(employeeToUpdate);
			}
		}
	}
}
