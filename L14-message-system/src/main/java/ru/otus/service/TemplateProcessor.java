package ru.otus.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

public class TemplateProcessor {
	private static final String TEMPLATE_DIR = "/template";

	private Configuration configuration;

	public TemplateProcessor() {
		configuration = new Configuration(Configuration.VERSION_2_3_28);
		configuration.setClassForTemplateLoading(this.getClass(), TEMPLATE_DIR);
		configuration.setDefaultEncoding("UTF-8");
	}

	public String getPage(String filename, HashMap<String, Object> variables) throws IOException {
		try (Writer writer = new StringWriter()) {
			final Template template = configuration.getTemplate(filename);
			template.process(variables, writer);
			return writer.toString();
		} catch (TemplateException | IOException e) {
			throw new IOException(e);
		}
	}
}
