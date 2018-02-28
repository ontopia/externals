
package net.ontopia.maven.documentation;

import com.vladsch.flexmark.ast.Block;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.util.TextCollectingVisitor;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;

public class Generator {
	
	private final Set<File> markdownFiles = new HashSet<>();
	private final Set<File> resourceFiles = new HashSet<>();
	
	// used to find relative path
	private String markdownDetector = File.separator + "src" + File.separator + "site";
	private String resourceDetector = markdownDetector + File.separator + "resources";
	
	private String extension = "html";
	private Map<String, Object> conversionData = new HashMap<>();
	
	private File outputDir;
	
	private HtmlRenderer renderer;
	private Parser parser;
	
	private final Log log;
	private final MavenProject project;
	
	private File templateFile;
	private Template template;
	
	public Generator(MavenProject project, Log log) {
		this.project = project;
		this.log = log;
	}

	public void generate() {
		initialize();
		convertMarkdownFiles();
		copyResourceFiles();
	}

	private void initialize() {
		MutableDataHolder options = PegdownOptionsAdapter.flexmarkOptions(
			Extensions.ALL & ~Extensions.HARDWRAPS 
				| Extensions.EXTANCHORLINKS | Extensions.SMARTS | Extensions.DEFINITIONS
				| Extensions.FOOTNOTES | Extensions.TOC
		).toMutable();

		options.set(TablesExtension.CLASS_NAME, "table");
		
		parser = Parser.builder(options).build();
		renderer = HtmlRenderer.builder(options).build();
		
		Properties p = new Properties();
		p.put("resource.loader", "file,cp");
		p.put("cp.resource.loader.class", ClasspathResourceLoader.class.getName());
		p.put("file.resource.loader.class", FileResourceLoader.class.getName());
		p.put("file.resource.loader.path", templateFile.getParent());
		
		VelocityEngine velocity = new VelocityEngine(p);
		template = velocity.getTemplate(templateFile.getName(), "utf-8");		
	}

	private void convertMarkdownFiles() {
		for (File mdFile : markdownFiles) {
			convert(mdFile, getMarkdownTarget(mdFile));
		}
	}

	private void convert(File source, File target) {
		try {
			Node document = parser.parseReader(new FileReader(source));
			
			try (PrintWriter writer = new PrintWriter(new FileOutputStream(target))) {
				VelocityContext c = new VelocityContext(conversionData);

				// todo: stop/capture velocity logging

				c.put("body", renderer.render(document));
				c.put("title", findTitle(document));
//				c.put("subtitle", converter.getSubtitle());
				c.put("root", getRelativeToOutput(target));
				c.put("inputFile", source);

//				if (converter.getToc().isEmpty() || markdownContent.contains("<notoc/>")) {
//					c.put("toc", false);
//				} else {
//					c.put("toc", converter.getToc());
//				}
				
				template.merge(c, writer);
				
				log.info("Converted " + source.getName());
			}
		} catch (IOException ioe) {
			throw new RuntimeException("Could not convert " + source, ioe);
		}
	}
	
	private String findTitle(Node root) {
		if (root instanceof Heading) {
			Heading h = (Heading) root;
			if (h.getLevel() == 1 && h.hasChildren()) {
				TextCollectingVisitor collectingVisitor = new TextCollectingVisitor();
				return collectingVisitor.collectAndGetText(h);
			}
		}

		if (root instanceof Block && root.hasChildren()) {
			Node child = root.getFirstChild();
			while (child != null) {
				String title = findTitle(child);
				if (title != null) {
					return title;
				}
				child = child.getNext();
			}
		}

		return null;
	}

	private void copyResourceFiles() {
		for (File resource : resourceFiles) {
			try {
				FileUtils.copyFile(resource, getResourceTarget(resource));
			} catch (IOException ioe) {
				throw new RuntimeException("Could not copy resource " + resource, ioe);
			}
		}
		log.info("Copied " + resourceFiles.size() + " resources");
	}

	private File getMarkdownTarget(File file) {
		String path = FilenameUtils.removeExtension(file.getPath());
		if (!path.contains(markdownDetector)) {
			throw new RuntimeException("Could not find relative path for markdown file " + file);
		}
		String relative = path.substring(path.lastIndexOf(markdownDetector) + markdownDetector.length() + 1);
		File target = new File(outputDir, relative + "." + extension);
		target.getParentFile().mkdirs();
		return target;
	}

	private File getResourceTarget(File file) {
		String path = file.getPath();
		if (!path.contains(resourceDetector)) {
			throw new RuntimeException("Could not find relative path for resource file " + file);
		}
		String relative = path.substring(path.lastIndexOf(resourceDetector) + resourceDetector.length() + 1);
		File target = new File(outputDir, relative);
		target.getParentFile().mkdirs();
		return target;
	}
	
	private String getRelativeToOutput(File target) {
		int depth = StringUtils.countMatches(target.getPath().substring(outputDir.getPath().length() + 1), File.separator);
		String toRoot = "";
		for (int i = 0; i < depth; i++) {
			toRoot += "../";
		}
		return toRoot;
	}

	// --- setters --- //
	
	public void setOutputDir(File outputDir) {
		this.outputDir = outputDir;
	}
	
	public void addMarkdownFile(File mdFile) {
		markdownFiles.add(mdFile);
	}
	
	public void addMarkdownFiles(Collection<File> mdFiles) {
		markdownFiles.addAll(mdFiles);
	}
	
	public void addResourceFile(File resourceFile) {
		this.resourceFiles.add(resourceFile);
	}

	public void addResourceFiles(Collection<File> resourceFiles) {
		this.resourceFiles.addAll(resourceFiles);
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setMarkdownDetector(String markdownDetector) {
		this.markdownDetector = markdownDetector;
	}

	public void setResourceDetector(String resourceDetector) {
		this.resourceDetector = resourceDetector;
	}

	public void setConversionData(Map<String, Object> conversionData) {
		this.conversionData = conversionData;
	}
	
	public void addConversionData(String key, Object value) {
		conversionData.put(key, value);
	}

	public void setTemplate(File templateFile) {
		this.templateFile = templateFile;
	}
	
}
