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

package org.springframework.boot.env;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;

import org.springframework.boot.BootstrapContext;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.boot.util.Instantiator;

/**
 * {@link EnvironmentPostProcessorsFactory} implementation that uses reflection to create
 * instances.
 *
 * @author Phillip Webb
 */
class ReflectionEnvironmentPostProcessorsFactory implements EnvironmentPostProcessorsFactory {

	private final List<String> classNames;

	ReflectionEnvironmentPostProcessorsFactory(Class<?>... classes) {
		this(Arrays.stream(classes).map(Class::getName).toArray(String[]::new));
	}

	ReflectionEnvironmentPostProcessorsFactory(String... classNames) {
		this(Arrays.asList(classNames));
	}

	ReflectionEnvironmentPostProcessorsFactory(List<String> classNames) {
		/**
		 * 这个值包含spring.factories中包含的Environment相关PostProcessors
		 * 这个值的初始化查看
		 * @see EnvironmentPostProcessorApplicationListener#EnvironmentPostProcessorApplicationListener()
		 */
		this.classNames = classNames;
	}

	@Override
	public List<EnvironmentPostProcessor> getEnvironmentPostProcessors(DeferredLogFactory logFactory,
			ConfigurableBootstrapContext bootstrapContext) {
		//这个方法的主要功能就是用这个Instantiator初始化this.classNames中保持的postProcessor
		//这里的lambda表达式表示postProcessor的构造器如果需要哪个类型，那么就把对应的实例传入。
		Instantiator<EnvironmentPostProcessor> instantiator = new Instantiator<>(EnvironmentPostProcessor.class,
				(parameters) -> {
					parameters.add(DeferredLogFactory.class, logFactory);
					parameters.add(Log.class, logFactory::getLog);
					parameters.add(ConfigurableBootstrapContext.class, bootstrapContext);
					parameters.add(BootstrapContext.class, bootstrapContext);
					parameters.add(BootstrapRegistry.class, bootstrapContext);
				});
		//实例化Environment相关PostProcessors
		return instantiator.instantiate(this.classNames);
	}

}
