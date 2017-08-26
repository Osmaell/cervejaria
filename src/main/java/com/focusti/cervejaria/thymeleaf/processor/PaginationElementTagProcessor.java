package com.focusti.cervejaria.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class PaginationElementTagProcessor  extends AbstractElementTagProcessor {
	
	private static final int PRECEDENCIA = 1000;
	private static final String NOME_TAG = "pagination";
	
	public PaginationElementTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, NOME_TAG, true, null, false, PRECEDENCIA);
	}
	
	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, IElementTagStructureHandler structureHandler) {
		
		IModelFactory modelFactory = context.getModelFactory();
		IModel model = modelFactory.createModel();
		
		IAttribute page = tag.getAttribute("page");
		String conteudoAtributo = String.format("layout/fragments/Paginacao :: page (%s)", page.getValue());
		
		model.add(modelFactory.createStandaloneElementTag(
				"th:block", // tag
				"th:replace", // atributo
				conteudoAtributo // conteúdo do atributo
		));
		
		structureHandler.replaceWith(model, true);
		
	}
	
}