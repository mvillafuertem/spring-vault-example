/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.primespringvaultexample;

import java.io.File;

/**
 * @author Mark Paluch
 */
public class WorkDirHelper {

	/**
	 * Find the {@code work} directory, starting at the {@code user.dir} directory. Search
	 * is performed by walking the parent directories.
	 * @return the {@link File} pointing to the {@code work} directory
	 * @throws IllegalStateException If the {@code work} directory cannot be found.
	 */
	public static File findWorkDir() {
		return findWorkDir(new File(System.getProperty("user.dir")));
	}

	/**
	 * Find the {@code work} directory, starting at the given {@code directory}. Search is
	 * performed by walking the parent directories.
	 * @return the {@link File} pointing to the {@code work} directory
	 * @throws IllegalStateException If the {@code work} directory cannot be found.
	 */
	public static File findWorkDir(File directory) {

		File searchLevel = directory;
		while (searchLevel.getParentFile() != null
				&& searchLevel.getParentFile() != searchLevel) {

			File work = new File(searchLevel, "work");
			if (work.isDirectory() && work.exists()) {
				return work;
			}

			searchLevel = searchLevel.getParentFile();
		}

		throw new IllegalStateException(String.format(
				"Cannot find work directory in %s or any parent directories",
				directory.getAbsoluteFile()));
	}
}
