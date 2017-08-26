package com.focusti.cervejaria.thymeleaf;
	
import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import com.focusti.cervejaria.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import com.focusti.cervejaria.thymeleaf.processor.MessageElementTagProcessor;
import com.focusti.cervejaria.thymeleaf.processor.OrderElementTagProcessor;
	
public class BrewerDialect extends AbstractProcessorDialect {
	
	public BrewerDialect() {
		super("Algaworks Brewer", "brewer", StandardDialect.PROCESSOR_PRECEDENCE);
	}
	
	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		processadores.add(new MessageElementTagProcessor(dialectPrefix));
		processadores.add(new OrderElementTagProcessor(dialectPrefix));
		return processadores;
	}
	
}