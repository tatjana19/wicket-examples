package de.alpharogroup.web.css;

import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

public class MainFoobarCssResourceReference extends CssResourceReference
{
	private static final ResourceReference INSTANCE = new MainFoobarCssResourceReference();

	public static ResourceReference get()
	{
		return INSTANCE;
	}

	private MainFoobarCssResourceReference()
	{
		super(MainFoobarCssResourceReference.class, "main-foobar.css");
	}
}
