
package net.ontopia.maven.documentation;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.DirectoryScanner;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.SITE)
public class GenerateMojo extends AbstractMojo {

	@Parameter( property = "generate.root", defaultValue = "${project.basedir}" )	
	private File rootDirectory;
	
	@Parameter( property = "generate.out", defaultValue = "${project.basedir}/target/site" )	
	private File outDirectory;
	
	@Parameter( property = "generate.template", defaultValue = "${project.basedir}/src/site/templates/distribution.vm" )	
	private File template;
	
	@Parameter( property = "generate.skip", defaultValue = "false" )
	private boolean skip = false;
	
	@Parameter( property = "generate.includes", defaultValue = "**/*.md" )
	private String[] includes;
	
	@Parameter( property = "generate.excludes")
	private String[] excludes;

	@Parameter( property = "generate.resources", defaultValue = "${project.basedir}/src/site/resources" )
	private File[] resources;
	
	@Parameter( property = "generate.resourceIncludes", defaultValue = "**/*" )
	private String[] resourceIncludes;
	
	@Parameter( property = "generate.resourceExcludes", defaultValue = "**/.*")
	private String[] resourceExcludes;
		
	@Parameter( defaultValue = "${project}" )
	private MavenProject project;
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		Log log = getLog();
		
		if (skip) {
			log.info("Skipping execution");
			return;
		}
		
		Generator generator = new Generator(project, log);
		generator.setOutputDir(outDirectory);
		generator.setTemplate(template);
		generator.addMarkdownFiles(collectFiles(rootDirectory, includes, excludes));
		generator.addResourceFiles(Arrays.asList(resources).parallelStream().map((resource) -> {
			return collectFiles(resource, resourceIncludes, resourceExcludes);
		}).flatMap(Collection::stream).collect(Collectors.toSet()));
		generator.generate();
	}
	
	private Collection<File> collectFiles(File root, String[] includes, String[] excludes) {
		if (!root.exists()) { return Collections.emptySet(); }
		DirectoryScanner scanner = new DirectoryScanner();
		scanner.setBasedir(root);
		scanner.setIncludes(includes);
		scanner.setExcludes(excludes);
		scanner.scan();
		return Arrays.asList(scanner.getIncludedFiles()).parallelStream().map((file) -> {
			return new File(root, file);
		}).collect(Collectors.toSet());
	}
}
