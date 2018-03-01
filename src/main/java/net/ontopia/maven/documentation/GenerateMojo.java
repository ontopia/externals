
package net.ontopia.maven.documentation;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

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
	
	@Parameter( property = "generate.debug", defaultValue = "false" )
	private boolean debug = false;
		
	@Parameter( defaultValue = "${project}" )
	private MavenProject project;
	
	@Parameter
	private Map<String, String> properties;
	
	private Log log;
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		log = getLog();
		
		if (skip) {
			log.info("Skipping execution");
			return;
		}
		
		if (!rootDirectory.exists()) {
			log.info("Input directory " + rootDirectory + " does not exist, skipping execution");
			return;
		}
		
		if (!outDirectory.exists()) {
			if (!outDirectory.mkdirs()) {
				throw new MojoFailureException("Could not create output directory " + outDirectory);
			}
		}
		
		Generator generator = new Generator(project, log);
		generator.setOutputDir(outDirectory);
		generator.setTemplate(template);
		generator.addResourceFiles(collectResources());
		
		generator.addMarkdownFiles(
			FileUtils.listFiles(rootDirectory, new SuffixFileFilter(".md"), DirectoryFileFilter.INSTANCE).parallelStream().filter((file) -> {
				return file.getPath().contains(markdownDetector);
			}).collect(Collectors.toSet())
		);
		
		generator.generate();
	}

	private String markdownDetector = File.separator + "src" + File.separator + "site";
	private static final String RESOURCES = File.separator + "src" + File.separator  + "site" + File.separator + "resources";
	private Collection<File> collectResources() {
		return FileUtils.listFiles(rootDirectory, 
			new IOFileFilter() {
				@Override
				public boolean accept(File file) {
					return file.getPath().contains(RESOURCES);
				}

				@Override
				public boolean accept(File dir, String name) {
					return dir.getPath().contains(RESOURCES);
				}
			}, DirectoryFileFilter.INSTANCE);		
	}	
}
