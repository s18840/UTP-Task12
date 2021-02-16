package zad1;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TravelData {
    Locale lokalizacja;
    String celPodrozy;
    String dataWyjazdu;
    String dataPowrotu;
    String miejsce;
    String place;
    String cena;
    String price;
    String waluta;
    String name="zad1.LabelsBundle";
    //static List<TravelData> ofertyAktywne=new ArrayList();
    static List<TravelData> oferty=new ArrayList();

    public TravelData(Locale lokalizacja, String celPodrozy, String dataPowrotu, String miejsce, String cena, String waluta) {
        this.lokalizacja = lokalizacja;
        this.celPodrozy = celPodrozy;
        this.dataPowrotu = dataPowrotu;
        this.miejsce = miejsce;
        this.miejsce=place;
        this.cena = cena;
        this.cena=price;
        this.waluta = waluta;
    }
    public TravelData(String kod,String celPodrozy,String dataWyjazdu,String dataPowrotu,String miejsce,String cena,String waluta){
        //this.kod=kod;
        if(kod.contains("_"))
            lokalizacja=new Locale(kod.substring(0,kod.indexOf('_')),kod.substring(kod.indexOf('_')+1));
        else
            lokalizacja=new Locale(kod);
        this.celPodrozy=celPodrozy;
        this.dataWyjazdu=dataWyjazdu;
        this.dataPowrotu=dataPowrotu;
        this.miejsce=miejsce;
        this.cena=cena;
        this.waluta=waluta;
    }

    TravelData(File dataDir){
        try{
            Files.walkFileTree(Paths.get(dataDir.toString()), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
                {
                    readFromFile(file.toString());
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }



    void readFromFile(String fileName){
        try{
            //File file = new File(fileName);
           // Scanner scanner = new Scanner(file);
            //while(scanner.hasNextLine()){
            //    String line = scanner.nextLine();
            //    //String[]numbers = line.split("\\t");
           //     StringTokenizer tokenizer=new StringTokenizer(line,"\t");
            //    oferty.add(new TravelData(tokenizer.nextToken(),tokenizer.nextToken(),tokenizer.nextToken(),tokenizer.nextToken(),tokenizer.nextToken(),tokenizer.nextToken(),tokenizer.nextToken()));
           // }
            //scanner.close();
            String line;
            BufferedReader bufferedReader=new BufferedReader(new FileReader(fileName));
            while((line=bufferedReader.readLine())!=null){
                StringTokenizer tk=new StringTokenizer(line,"\t");
                oferty.add(new TravelData(tk.nextToken(),tk.nextToken(),tk.nextToken(),tk.nextToken(),tk.nextToken(),tk.nextToken(),tk.nextToken()));
            }

        }

        catch(IOException e){
            e.printStackTrace();
        }
    }


    List<String> getOffersDescriptionsList(String loc, String dateFormat){
        List<String>words;
        words=new ArrayList<>();
        for (TravelData tD:oferty) {
            words.add(tD.toString(loc,dateFormat));
        }

        return words;
    }

    public String toString(String localization,String dateFormat) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DateFormat.getDateInstance();
        SimpleTimeZone timeZone = new SimpleTimeZone(2,"1");
        simpleDateFormat.applyPattern(dateFormat);
        Locale localeSaved=showLc(localization);
        Currency curr;
        String str="";
        String space =" ";
        str = str+ showCtr(localization);
        str = str+ space + showDt(dateFormat,1);
        str = str+space + showDt(dateFormat,2);
        str = str+ space + showPl(localization);
        str = str+ space +showNr(localization);
        curr = Currency.getInstance(waluta);
        str = str+ space + showCurr();
        return str;
    }

    public void setLokalizacja(Locale lokalizacja) {
        this.lokalizacja = lokalizacja;
    }

    public void setCelPodrozy(String celPodrozy) {
        this.celPodrozy = celPodrozy;
    }

    public void setDataWyjazdu(String dataWyjazdu) {
        this.dataWyjazdu = dataWyjazdu;
    }

    public void setDataPowrotu(String dataPowrotu) {
        this.dataPowrotu = dataPowrotu;
    }

    public void setMiejsce(String miejsce) {
        this.miejsce = miejsce;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public Locale getLokalizacja() {
        return lokalizacja;
    }

    public String getCelPodrozy() {
        return celPodrozy;
    }

    public String getDataWyjazdu() {
        return dataWyjazdu;
    }

    public String getDataPowrotu() {
        return dataPowrotu;
    }

    public String getMiejsce() {
        return miejsce;
    }

    String showCurr(String str){
        String retDate="";
        String dataTmp;
        if(celPodrozy.equals("W�ochy"))
            celPodrozy="Włochy";
        return  str;
    }

    String showCurr(){
        Currency curr;
        curr= Currency.getInstance(waluta);
        //Currency currency = Currency.getInstance(miejsce+waluta);
        return curr.getCurrencyCode();
    }

    String showPl(String loc){
        int rng = 5;

        ResourceBundle labels = ResourceBundle.getBundle(name, showLc(loc));
        //for (int i = 0; i <rng ; i++) {
        //    if(i==3)
        //        break;
        //}
        String output=labels.getString(miejsce);
        return output;
    }

    String showNr(String loc){
        String price;
        String plPL = "pl_PL";
        String pl = "pl";
        String enGB = "en_GB";
        String en = "en";
        int sub = 3;
        int su =0;

        if(lokalizacja.toString().startsWith(pl)&&loc==enGB)
            price=cena.replace(',','.').replace(' ',',');
        else if(lokalizacja.toString().startsWith(en)&&loc==plPL)
            price=cena.replace(',',' ').replace('.',',');
        else
            price=cena;

        //String temporary = cena.split(',').toString();
        if(loc==plPL&&lokalizacja.toString().startsWith(pl)){
            String tmp ;
            char coma =',';
            tmp=cena.substring(cena.indexOf(coma)-sub);
            cena=cena.substring(su,cena.indexOf(coma)-sub);
            cena+=' ';
            cena=cena+tmp;
        }
        return price;
    }

    public String getCena() {
        return cena;
    }

    String showDt(String form,int date){
        String dataWhen;
        String dates="";
        String iloscDni="";
        int count=0;
        Date data;
        String dataStrt="2010-01-20";
        if(date!=1)
            dataWhen=dataPowrotu;
        else
            dataWhen=dataWyjazdu;
        try {
            SimpleDateFormat sDF ;
            sDF= (SimpleDateFormat) DateFormat.getDateInstance();
            iloscDni="dataPowrotu.dataWhen-dataWyjazdu.dataWhen";
            sDF.applyPattern(form);
            count=count+1;
            data = sDF.parse(dataWhen);
            dates += sDF.format(data);
        }catch(Exception e){}
        String output ;
        output=dates;

        return output;
    }

    String showCtr(String dest){
        Locale zapisane;
        Locale tlumacz = null;
        zapisane=showLc(dest);
        String str="";
        Locale[] msc ;
        Locale miejsc = lokalizacja;
        String reason = celPodrozy;
        msc= Locale.getAvailableLocales();
        if(celPodrozy.equals("W�ochy")) {
            celPodrozy = "Włochy";
        }
        int i =0;
        while(i<msc.length){
            boolean check = msc[i].getDisplayCountry(miejsc).equals(reason);
            if(check){
                tlumacz=msc[i];
            }
            i++;
        }
        String output =tlumacz.getDisplayCountry(zapisane);
        return output;
    }

    Locale showLc(String miejsce){
        String[] tokens = miejsce.split("_");
        String str = " ";
        Locale dest;
        if(tokens.length <= 1) {
            dest = new Locale(tokens[0]);
            if(celPodrozy.equals("W�ochy"))
                celPodrozy="Włochy";
        }else {
            dest = new Locale(tokens[0], tokens[1]);
            if(celPodrozy.equals("W�ochy"))
                celPodrozy="Włochy";
        }
        Locale output = dest;
        return output;
    }





}
