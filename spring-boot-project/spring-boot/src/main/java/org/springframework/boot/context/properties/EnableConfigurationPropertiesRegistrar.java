/*
 * Copyright 2012-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.context.properties;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.validation.beanvalidation.MethodValidationExcludeFilter;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.Conventions;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotationMetadata;

/**
 * {@link ImportBeanDefinitionRegistrar} for
 * {@link EnableConfigurationProperties @EnableConfigurationProperties}.
 *
 * @author Phillip Webb
 * @author Andy Wilkinson
 */
class EnableConfigurationPropertiesRegistrar implements ImportBeanDefinitionRegistrar {

	private static final String METHOD_VALIDATION_EXCLUDE_FILTER_BEAN_NAME = Conventions
			.getQualifiedAttributeName(EnableConfigurationPropertiesRegistrar.class, "methodValidationExcludeFilter");

	/**
	 * @param metadata 如果需要的话，自动配置类会使用EnableConfigurationProperties注解
	 *                 来加载properties类，每个注解都会使用此方法来获取properties类，所以这个metadata
	 *                 是自动配置类的元数据
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		/**
		 * 注册一些组件到bd
		 * 1. {@link ConfigurationPropertiesBindingPostProcessor}，就是一个PostProcessor，用来处理properties类
		 * 2. {@link ConfigurationPropertiesBinder.Factory} 用于创建Binder
		 * 3. 通过工厂创建的{@link ConfigurationPropertiesBinder} properties绑定器，用于属性绑定
		 * 4. {@link BoundConfigurationProperties} 母鸡
		 */
		registerInfrastructureBeans(registry);
		//一个过滤器，用于把某个类排除在方法验证之外
		registerMethodValidationExcludeFilter(registry);
		ConfigurationPropertiesBeanRegistrar beanRegistrar = new ConfigurationPropertiesBeanRegistrar(registry);
		/**
		 * 获取{@link EnableConfigurationProperties}注解中的值，
		 * 这个值是一个properties类的Class
		 * 把这个类注册到bd中，并且如果这个类没有{@link ConfigurationProperties}注解会抛出异常
		 */
		getTypes(metadata).forEach(beanRegistrar::register);
	}

	private Set<Class<?>> getTypes(AnnotationMetadata metadata) {
		//获取元数据中EnableConfigurationProperties注解中的类
		return metadata.getAnnotations().stream(EnableConfigurationProperties.class)
				.flatMap((annotation) -> Arrays.stream(annotation.getClassArray(MergedAnnotation.VALUE)))
				.filter((type) -> void.class != type).collect(Collectors.toSet());
	}

	static void registerInfrastructureBeans(BeanDefinitionRegistry registry) {
		ConfigurationPropertiesBindingPostProcessor.register(registry);
		BoundConfigurationProperties.register(registry);
	}

	static void registerMethodValidationExcludeFilter(BeanDefinitionRegistry registry) {
		if (!registry.containsBeanDefinition(METHOD_VALIDATION_EXCLUDE_FILTER_BEAN_NAME)) {
			BeanDefinition definition = BeanDefinitionBuilder
					.genericBeanDefinition(MethodValidationExcludeFilter.class,
							() -> MethodValidationExcludeFilter.byAnnotation(ConfigurationProperties.class))
					.setRole(BeanDefinition.ROLE_INFRASTRUCTURE).getBeanDefinition();
			registry.registerBeanDefinition(METHOD_VALIDATION_EXCLUDE_FILTER_BEAN_NAME, definition);
		}
	}

}
