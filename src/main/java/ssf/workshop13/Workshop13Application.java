package ssf.workshop13;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//static import import everything in the class
import static ssf.workshop13.util.IOUtil.*;

import java.util.List;

@SpringBootApplication
public class Workshop13Application {
	private static final Logger logger = LoggerFactory.getLogger(Workshop13Application.class);

	public static void main(String[] args) {
		//SpringApplication.run(Workshop13Application.class, args);
		SpringApplication app = new SpringApplication(Workshop13Application.class);
		DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);
		List<String> opsVal = appArgs.getOptionValues("dataDir");
		System.out.println("before createDir");
		if(null != opsVal){
			logger.info("" + (String)opsVal.get(0));
			System.out.println("inside create Dir");
			createDir((String)opsVal.get(0));
		} else{
			System.out.println("exit");
			System.exit(1);
		}
		app.run(args);
	}

	//mvn spring-boot:run -Dspring-boot.run.arguments=--dataDir=/Users/trix/Desktop/SSF/workshop13/data
	// creates a directory

}
