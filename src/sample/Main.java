package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TreeSet;


public class Main extends Application {

    private final Button btnAdaugaSBV =new Button("Adauga director SBV");
    private final Button btnAdaugaCSV =new Button("Adauga fisier CSV");
    private final Button btnAdaugaCSVIesire =new Button("Adauga fisierul CSV de iesire");
    private final Button btnAdaugaConfig=new Button("Adauga fisier de configurare");
    private final Button btnAdaugaGrupaCautate =new Button("Grupa cautata");
    private final Button btnAdaugaGrupeAcceptate =new Button("Grupe acceptate");
    private final Button btnAdaugaString =new Button("Adauga String");
    private final Button btnExecuta =new Button("Executa");
    private final CheckBox cBoxDupaNumePrenume =new CheckBox("Adaugare dupa Nume Prenume");
    private final CheckBox cBoxDupaNumePrenumeSemigrupa =new CheckBox("Adaugare dupa Nume Prenume si semigrupa");
    private final CheckBox cBoxDupaUnString =new CheckBox("Dupa un string oferit");
    private final CheckBox cBoxDacaAScrisCeva =new CheckBox("Daca studentul a scris ceva pe chat");
    private final TextField tFGrupaCautata =new TextField();
    private final TextField tFGrupeAcceptate =new TextField();
    private final TextField tFStringCautat =new TextField();
    private final Label textAutor=new Label("Made By:Horomnea Tudor\nProfesor indrumator: Stefan-Gheorghe Pentiuc\nRealizat ca proiect pentru materia SDA and 3");

    TextArea zonaTextArea;
    private Stage mainStage;
    private String fisierCSVCitit;
    private String fisierSBV;
    private String fisierCSVIesire;
    private String fisierConfig;
    private String stringulCautat;
    private String grupeCautate;
    private String grupeAcceptate="gfjkhgdjfkghdjkf";
    private boolean cautareDacaAScrisCeva;
    private boolean cautareDupaNumePrenume;
    private boolean cautareDupaNumePrenumeSemigrupa;
    private boolean cautareDupaUnString;

    private VBox panouButoane()
    {
        VBox panou=new VBox(10, btnAdaugaCSV, btnAdaugaSBV, btnAdaugaCSVIesire,btnAdaugaConfig,textAutor);
        btnAdaugaCSV.setOnAction(a-> deschideFisierCSV());
        btnAdaugaSBV.setOnAction(a-> deschideFisierSBV());
        btnAdaugaCSVIesire.setOnAction(a-> CreateNewOutFile());
        btnAdaugaConfig.setOnAction(a->deschideFisierConfig());
        return panou;
    }
    private VBox panouFiltre()
    {
        HBox panouDupaUnString=new HBox(10, btnAdaugaString, tFStringCautat);
        HBox panouAdaugaGrupaCautata=new HBox(10, tFGrupaCautata, btnAdaugaGrupaCautate);
        HBox panouAdaugaGrupaAcceptate=new HBox(10, tFGrupeAcceptate, btnAdaugaGrupeAcceptate);
        VBox panouSelectFiltre=new VBox(10, cBoxDacaAScrisCeva, cBoxDupaNumePrenume, cBoxDupaNumePrenumeSemigrupa, cBoxDupaUnString,panouDupaUnString,panouAdaugaGrupaCautata,panouAdaugaGrupaAcceptate, btnExecuta);
        cBoxDacaAScrisCeva.setOnAction(a->{

            grupeCautate="";
            grupeAcceptate="gfjkhgdjfkghdjkf";

            cBoxDupaNumePrenume.setSelected(false);
            cautareDacaAScrisCeva=true;
            cautareDupaNumePrenume=false;
            cBoxDupaNumePrenumeSemigrupa.setSelected(false);
            cautareDupaNumePrenumeSemigrupa=false;
            cBoxDupaUnString.setSelected(false);
            cautareDupaUnString=false;
            tFGrupaCautata.setDisable(true);
            tFGrupeAcceptate.setDisable(true);
            btnAdaugaGrupaCautate.setDisable(true);
            btnAdaugaGrupeAcceptate.setDisable(true);
            btnAdaugaString.setDisable(true);
        });
        cBoxDupaNumePrenume.setOnAction(a->{

            if(cBoxDupaNumePrenume.isSelected())
            {
                grupeCautate = "";
                cautareDupaNumePrenumeSemigrupa=false;
                tFGrupaCautata.setDisable(true);
                tFGrupeAcceptate.setDisable(true);
                btnAdaugaGrupaCautate.setDisable(true);
                btnAdaugaGrupeAcceptate.setDisable(true);
                grupeAcceptate = "gfjkhgdjfkghdjkf";
                cBoxDupaNumePrenumeSemigrupa.setSelected(false);
                cautareDupaNumePrenume=true;
                cBoxDacaAScrisCeva.setSelected(false);
                cautareDacaAScrisCeva=false;
            }
            else{

                cautareDupaNumePrenume=false;
            }

        });
        cBoxDupaNumePrenumeSemigrupa.setOnAction(a->{

            if(cBoxDupaNumePrenumeSemigrupa.isSelected())
            {
                cautareDupaNumePrenumeSemigrupa=true;
                cBoxDacaAScrisCeva.setSelected(false);
                cBoxDupaNumePrenume.setSelected(false);
                cautareDacaAScrisCeva=false;
                tFGrupaCautata.setDisable(false);
                tFGrupeAcceptate.setDisable(false);
                btnAdaugaGrupaCautate.setDisable(false);
                btnAdaugaGrupeAcceptate.setDisable(false);
            }
            else{
                cautareDupaNumePrenumeSemigrupa=false;
                tFGrupaCautata.setDisable(true);
                tFGrupeAcceptate.setDisable(true);
                btnAdaugaGrupaCautate.setDisable(true);
                btnAdaugaGrupeAcceptate.setDisable(true);
            }

        });
        cBoxDupaUnString.setOnAction(a->{

            if(cBoxDupaUnString.isSelected())
            {
                tFStringCautat.setDisable(false);
                cautareDupaUnString=true;
                cBoxDacaAScrisCeva.setSelected(false);
                cautareDacaAScrisCeva=false;
                btnAdaugaString.setDisable(false);
            }
            else
            {
                cautareDupaUnString=false;
                tFStringCautat.setDisable(true);
                btnAdaugaString.setDisable(true);
            }

        });
        btnAdaugaString.setOnAction(s->{
            stringulCautat = tFStringCautat.getText();
            zonaTextArea.appendText("Stringul ales este: "+ stringulCautat);
        });
        btnAdaugaGrupaCautate.setOnAction(a->{
            grupeCautate= tFGrupaCautata.getText();
            zonaTextArea.appendText("\n"+grupeCautate);
        });
        btnAdaugaGrupeAcceptate.setOnAction(a->{
            if(grupeAcceptate.equals("gfjkhgdjfkghdjkf"))
            {
                grupeAcceptate= tFGrupeAcceptate.getText();
            }
            else
            {
                grupeAcceptate = grupeAcceptate + "," + tFGrupeAcceptate.getText();
            }
            zonaTextArea.appendText("\n"+grupeAcceptate);
        });
        btnExecuta.setOnAction(a->{

                if (fisierCSVCitit != null && fisierSBV != null && fisierCSVIesire != null) {
                    if (cautareDupaNumePrenumeSemigrupa && grupeCautate == null) {
                        zonaTextArea.appendText("\nVa rog sa introduceti primele trei cifre ale grupei cautate ex: 313");
                    } else if (cautareDupaUnString && stringulCautat == null) {
                        zonaTextArea.appendText("\nVa rog sa introduceti Stringul dupa care doriti sa faceti cautarea\n");
                    }
                    else if (!cautareDacaAScrisCeva&&!cautareDupaNumePrenume&&!cautareDupaNumePrenumeSemigrupa&&!cautareDupaUnString){
                        zonaTextArea.appendText("\nVa rog selectati un mod de filtrare");
                    }
                        else {
                        //int index = fisierSBV.lastIndexOf("\\");
                        //cale = fisierSBV.substring(0, index);
                        //System.out.println(cale);
                        System.out.println(fisierSBV);
                        System.out.println(grupeAcceptate);
                        String[] _arg = {fisierSBV, grupeCautate, fisierCSVCitit, grupeAcceptate, fisierCSVIesire, String.valueOf(cautareDacaAScrisCeva),
                                String.valueOf(cautareDupaNumePrenume), String.valueOf(cautareDupaNumePrenumeSemigrupa), String.valueOf(cautareDupaUnString), stringulCautat,fisierConfig
                        };
                        //0-cale
                        //1-grupeAcceptate
                        //2-fisierCSVcitit
                        //3-grupeAcceptate
                        //4-fisierCSVIesire
                        //5-cautaDacaAScrisCeva
                        //6-cautareDupaNumePrenume
                        //7-cautareDupaNumePrenumeSemigrupa
                        //8-cautareDupaUnString
                        //9-StringulCautat
                        //10-configFile
                        try {
                            //System.out.println(Thread.currentThread());
                            PrelucrareDate.listaAnomalii=new TreeSet<>();
                            ConsoleMain.main(_arg);
                            zonaTextArea.appendText("\n\nFISIER CREAT CU SUCCESS!!!!");
                            int in=1;
                            zonaTextArea.appendText("\n\nORDINE PRIMA ZI DIN FIECARE SAPTAMANA:");
                            for(GregorianCalendar d:ConfigDate.copy)
                            {
                                zonaTextArea.appendText("\n"+in+"->"+d.toZonedDateTime());
                                in++;
                            }
                            if(PrelucrareDate.listaAnomalii.size()>0)
                            {
                                zonaTextArea.appendText("\n\nANOMALII!!!!\nESTE NECESARA INTERVENTIE SUPLIMENTARA PENTRU URMATORII STUDENTI::");
                                for(String s:PrelucrareDate.listaAnomalii)
                                {
                                    zonaTextArea.appendText("\n"+s.toUpperCase());
                                }
                            }
                        } catch (IOException exception) {
                            zonaTextArea.appendText("Ceva nu a mers bine va rog incercati din nou");
                        }
                    }
                } else {
                    zonaTextArea.appendText("\nVa rog introduceti toate fisierele necesare");
                }


        });

        return panouSelectFiltre;
    }
    private static TextArea getTextArea(){
        TextArea ta = new TextArea( );
        ta.appendText ("Operatii:");
        ta.setPrefWidth ( 400 );
        return ta;
    }

    @Override
    public void start(Stage primaryStage) {
        /*String cale=new String();
        mainStage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

        String t=new String();

        File f= deschideFisierSBV(true);
        File inFile= deschideFisierCSV(true);
        File outFile=CreateNewOutFile(true);
        //System.out.println(outFile);

        String grupeAcceptate= "314";
        String grupaCautata ="313";

        if(f!=null)
        {
            int index=f.toString().lastIndexOf("\\");
            cale=f.toString().substring(0,index);
            System.out.println(cale);
            String[] _arg={cale,grupeAcceptate,inFile.toString(),grupaCautata,outFile.toString()};
                            //0     1               2               3           4
            ConsoleMain.main(_arg);
        }
        else
            System.out.println("Nu ati ales nici un fisier");
*/

        //pt moment ar trebui sa schimb oleaca cred in caz ca selectez intr-o anumita ordine
        grupeCautate="";
        //grupeAcceptate="";


        tFStringCautat.setDisable(true);
        tFGrupaCautata.setDisable(true);
        tFGrupeAcceptate.setDisable(true);
        btnAdaugaGrupeAcceptate.setDisable(true);
        btnAdaugaGrupaCautate.setDisable(true);
        btnAdaugaString.setDisable(true);
        mainStage=primaryStage;
        zonaTextArea=getTextArea();
        HBox panouGrafic=new HBox(20,panouButoane(),zonaTextArea,panouFiltre());
        panouGrafic.setPadding(new Insets(10));
        Scene scena = new Scene(panouGrafic, 940, 500);
        primaryStage.setScene(scena);
        primaryStage.setTitle("Prezenta studenti");
        primaryStage.show();

    }

    private void deschideFisierSBV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Alege un fisier .sbv din directorul cu toate disierele .sbv");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.sbv"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));

        List<File> selectedFile = fileChooser.showOpenMultipleDialog(mainStage);
        //File selectedFile=fileChooser.showOpenDialog(mainStage);
        System.out.println(selectedFile);
        if (selectedFile != null) {
            StringBuilder buf=new StringBuilder();
            //zonaTextArea.appendText("\n" + selectedFile);
            for(File f: selectedFile)
            {
                buf.append(f).append("DELIMITATOR");
            }
            //fisierSBV=selectedFile.toString();
            fisierSBV=buf.toString();
            //return selectedFile;
        }
        else
        {
            zonaTextArea.appendText("\nVa rog selectati un fisier .sbv din directorul cu toate acestea");
        }
        /*DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(mainStage);

        if(selectedDirectory == null){
            //No Directory selected
        }else{
            System.out.println(selectedDirectory.getAbsolutePath());
        }
        return selectedDirectory;*/
    }
    private void deschideFisierCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Alege fisierul CSV");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            //zonaTextArea.appendText("\n" + selectedFile);
            fisierCSVCitit=selectedFile.toString();
            //return selectedFile;
        }
        else
        {
            zonaTextArea.appendText("\nVa rog selectati fisierul schelet .csv");
        }
    }

    private void CreateNewOutFile()
    {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.csv"));

        chooser.setTitle("Alegeti locatia unde doriti sa creati fisierul .csv de iesire");
        File selectedFile;
        /*if(selectedFile== null){    //daca vreau mai insistent pot pune while
            selectedFile = chooser.showSaveDialog(null);
        }*/
        selectedFile = chooser.showSaveDialog(null);

        File file = new File(String.valueOf(selectedFile));
        if(selectedFile!=null) {
            fisierCSVIesire = file.toString();
        }
        else
        {
            fisierCSVIesire=null;
        }
        zonaTextArea.appendText("\nPathul selectat este: "+fisierCSVIesire);

    }
    private void deschideFisierConfig() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Alege fisierul CSV");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.config"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            //zonaTextArea.appendText("\n" + selectedFile);
            fisierConfig=selectedFile.toString();
            //return selectedFile;
        }
        else
        {
            zonaTextArea.appendText("\nVa rog selectati fisierul schelet .csv");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
