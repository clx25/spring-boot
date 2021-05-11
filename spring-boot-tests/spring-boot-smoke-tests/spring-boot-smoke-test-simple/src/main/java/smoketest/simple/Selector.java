package smoketest.simple;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.function.Predicate;

public class Selector implements DeferredImportSelector {
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		System.out.println("Selector->selectImports");
		return new String[0];
	}

	@Override
	public Predicate<String> getExclusionFilter() {
		System.out.println("Selector->getExclusionFilter");
		return t->true;
	}

	@Override
	public Class<? extends Group> getImportGroup() {
		System.out.println("Selector->getImportGroup");
		return null;
	}
}
