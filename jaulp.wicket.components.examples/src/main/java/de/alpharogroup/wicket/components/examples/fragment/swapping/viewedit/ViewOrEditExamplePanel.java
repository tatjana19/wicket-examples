package de.alpharogroup.wicket.components.examples.fragment.swapping.viewedit;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.alpharogroup.test.objects.Gender;
import de.alpharogroup.test.objects.Person;
import de.alpharogroup.wicket.components.editable.checkbox.EditableCheckbox;
import de.alpharogroup.wicket.components.editable.textarea.EditableTextArea;
import de.alpharogroup.wicket.components.editable.textfield.EditableTextField;

public class ViewOrEditExamplePanel extends GenericPanel<Person>
{

	private boolean enableFields = true;
	private final EditableTextArea<Person> about;
	private final EditableTextField<Person> nameTextField;
	private final EditableCheckbox<Person> married;
	private final Form<Person> form;

	public ViewOrEditExamplePanel(final String id, final IModel<Person> model)
	{
		super(id, model);

		final Person person = new Person();
		person.setGender(Gender.UNDEFINED);
		person.setName("");
		person.setAbout("bla");
		person.setMarried(false);
		setModel(Model.of(person));


		final IModel<Person> cpm = getModel();

		form = new Form<>("form", cpm);
		form.setOutputMarkupId(true);
		add(form);
		nameTextField = new EditableTextField<>("name",
			getModel(), Model.of("Name"));
		form.add(nameTextField);

		about = new EditableTextArea<>("about",
			getModel(), Model.of("About"));
		form.add(about);

		married = new EditableCheckbox<>("married",
			getModel(), Model.of("Married"));

		form.add(married);

		// Create submit button for the form
		final AjaxButton submitButton = new AjaxButton("submitButton", form)
		{
			/**
			 * The serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit(final AjaxRequestTarget target, final Form<?> form)
			{
				ViewOrEditExamplePanel.this.onSubmit(target, form);
			}
		};

		final AjaxLink<Void> link = new AjaxLink<Void>("submitLink")
		{
			/**
			 * The serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(final AjaxRequestTarget target)
			{
				ViewOrEditExamplePanel.this.onSubmit(target, form);
			}
		};
		form.add(link);

		form.add(submitButton);

		add(new FeedbackPanel("feedbackpanel"));
	}

	public void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
		info("Person:" + getDefaultModelObjectAsString());
		ViewOrEditExamplePanel.this.enableFields = !ViewOrEditExamplePanel.this.enableFields;
		ViewOrEditExamplePanel.this.setModelObject(ViewOrEditExamplePanel.this.getModelObject());
		if (ViewOrEditExamplePanel.this.enableFields)
		{
			about.getSwapPanel().onSwapToEdit(target, form);
			nameTextField.getSwapPanel().onSwapToEdit(target, form);
			married.getSwapPanel().onSwapToEdit(target, form);
		}
		else
		{
			about.getSwapPanel().onSwapToView(target, form);
			nameTextField.getSwapPanel().onSwapToView(target, form);
			married.getSwapPanel().onSwapToView(target, form);
		}
	}
}
