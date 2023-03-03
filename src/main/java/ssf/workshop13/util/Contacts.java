package ssf.workshop13.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import ssf.workshop13.model.Contact;

@Component("contacts")
public class Contacts {

    public void saveContact(Contact ctc, Model model, ApplicationArguments appArgs, String defaultDataDir){
        String dataFileName = ctc.getId();
        //print line by line to the file
        PrintWriter printWriter = null;

        try {
            FileWriter fileWriter = new FileWriter(getDataDir(appArgs, defaultDataDir) + "/" + dataFileName);
            printWriter = new PrintWriter(fileWriter);
            printWriter.println(ctc.getId());
            printWriter.println(ctc.getName());
            printWriter.println(ctc.getEmail());
            printWriter.println(ctc.getPhoneNumber());
            printWriter.println(ctc.getDateOfBirth());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("contact", new Contact(ctc.getId(), ctc.getName(), ctc.getEmail(), ctc.getPhoneNumber(), ctc.getDateOfBirth()));
    }

    public void getContactById(Model model, String contactID, ApplicationArguments appArgs, String defaultDataDir){
        Contact ctc = new Contact();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
        try {
            Path filePath = new File(getDataDir(appArgs, defaultDataDir) + "/" + contactID).toPath();
            Charset charset = Charset.forName("UTF-8");
            List<String> stringValues = Files.readAllLines(filePath, charset);
            ctc.setId(contactID);
            ctc.setName(stringValues.get(0));
            ctc.setEmail(stringValues.get(1));
            ctc.setPhoneNumber(stringValues.get(2));
            LocalDate dob = LocalDate.parse(stringValues.get(3), formatter);
            ctc.setDateOfBirth(dob);
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("contact", ctc);
    }

    public void getAllContacts(Model model, ApplicationArguments appArgs, String defaultDataDir){
        Set <String> dataFiles = listFiles(getDataDir(appArgs, defaultDataDir));
        model.addAttribute("contacts", null);
    }

    //task5
    public Set<String> listFiles(String dir){
        return Stream.of(new File(dir).listFiles())
        .filter(file -> !file.isDirectory())
        .map(File::getName)
        .collect(Collectors.toSet());
    }

    //create a private method
    private String getDataDir(ApplicationArguments appArgs, String defaultDataDir){
        String dataDirResult = "";
        List<String> optValues = null;
        String[] optValuesArr = null;
        Set<String> opsNames = appArgs.getOptionNames();
        String[] opsNamesArr = opsNames.toArray(new String[opsNames.size()]);
        if(opsNamesArr.length > 0){
            optValues = appArgs.getOptionValues(opsNamesArr[0]);
            optValuesArr = optValues.toArray(new String[optValues.size()]);
            dataDirResult = optValuesArr[0];
        } else {
            dataDirResult = defaultDataDir;
        }

        return dataDirResult;
    }
}
