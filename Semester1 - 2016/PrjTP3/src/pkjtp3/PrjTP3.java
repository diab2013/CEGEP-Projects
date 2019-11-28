
package pkjtp3;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class PrjTP3 
{

    public static int année, nbrJoursInf1, nbrJoursInf2, nbrJoursSup1, nbrJoursSup2;
    public static BufferedReader lire;
    public static PrintWriter écrire;
    public static double Moyenne[][];
    public static double TmoyMesAnnée, TmoyCalAnnée;
    public static double max, maxC, min, minC;
    //public static double minM, maxM, minMC, maxMC;
    
    public static void main(String[] args) throws Exception{
        boolean restart = true;
        do{
            int  nbrJours;
            char r;
            String rep;
            System.out.println("Entrez qu'elle année vous souhaitez traiter.");
            Scanner year = new Scanner(System.in);
            année = year.nextInt();
            nbrJours = checkBis();
            String Matrice[][] = new String[nbrJours+1][4];
            lire = new BufferedReader (new FileReader(année +".txt"));
            boolean continué = checkDonnées(nbrJours);
            if (continué == false){
                System.out.println("Le fichier est invalide ...");
                Scanner recommencer = new Scanner (System.in);
                System.out.println("Souhaitez-vous recommencer le choix de l'année?");
                rep = recommencer.nextLine();
                if ((r = rep.charAt(0)) == 'n' || (r = rep.charAt(0)) == 'N'){
                    restart = false;
                }
                if (restart == false){
                    return;
                }
            }
            else{
                lireFichier(Matrice);
                calculTmoyenne(Matrice);
                calculTminmax();
                calculAnnuel(nbrJours); 
                calculÉolien();         
                calculHumidex();        
                écrireStats(nbrJours);
                calculMensuel(nbrJours);
            }
            Scanner recommencer = new Scanner (System.in);
            System.out.println("Souhaitez-vous recommencer le choix de l'année?");
            rep = recommencer.nextLine();
            if ((r = rep.charAt(0)) == 'n' || (r = rep.charAt(0)) == 'N'){
                restart = false;
            }
            
        } while(restart == true);
        System.out.println("IL RESTE UN MESSAGE, VÉRIFIER LE POPUP!!!");
    //Rien de complexe, juste un petit truc :D
        JOptionPane.showMessageDialog(null, "Bonjour Sabri, j'espère que vous passerais des belles vacances !!");
        System.exit(0);
    }
    
    public static void vérifierRecord(double max, double maxC, double min, double minC, int numM) throws Exception{
        lire = new BufferedReader (new FileReader("RECORDS.txt"));
        String ligne;
        ligne = lire.readLine();
        while((ligne = lire.readLine()) != null){
            String[] line = ligne.split("\t");
            if ("Record_max".equals(line[0])){
                if(max > Double.parseDouble(line[numM]) || maxC > Double.parseDouble(line[numM])){
                    ligne = lire.readLine();
                    String[] yearR = ligne.split("\t");
                    int yearRecord = Integer.parseInt(yearR[1]);
                    System.out.println("Le record max de température de l'année " +yearRecord +" avec le record de " +line[0] +" et maintenant de " +max +"°C");
                }
            }
            if ("Record_min".equals(line[0])){
                if (min < Double.parseDouble(line[numM]) || minC < Double.parseDouble(line[numM])){
                    ligne = lire.readLine();
                    String[] yearR = ligne.split("\t");
                    int yearRecord = Integer.parseInt(yearR[1]);
                    System.out.println("Le record min de température de l'année " +yearRecord +" avec le record de " +line[0] +" et maintenant de " +min +"°C");
                }
            }
        }

        
        lire.close();
    }
    
    public static void calculMensuel (int nbrJours) throws Exception{
    //Le calcul pour chaque mois
    //Même démarche qui se répéte pour chaque année
    // Oui .... elle est gigantesque pour rien je le sais ...........
    // Ca me tentait vraiment plus d'optimiser à se stade ci ......
    
        int d = 0;
        if (nbrJours == 366)
        {
            d = 1;
        }
        for (int i=0; i<=12; i++){
            double somme = 0;
            double sommeC = 0;
            switch(i){
                case 1: écrire.println("Janvier");
                    int début = 0, fin = 30, numM = 1;
                    double maxM = -50, minM = 0, maxMC = -50, minMC = 0;
                    for (int j = début; j<=fin; j++)
                    {
                        somme = somme + Moyenne[j][0];
                        sommeC = sommeC + Moyenne[j][1];
                        if (maxM < Moyenne[j][0])
                        {
                            maxM = Moyenne[j][0];
                        }
                        if (minM > Moyenne[j][0])
                        {
                            minM = Moyenne[j][0];
                        }
                        if (maxMC < Moyenne[j][1])
                        {
                            maxMC = Moyenne[j][1];
                        }
                        if (minMC > Moyenne[j][1])
                        {
                            minMC = Moyenne[j][1];
                        }
                    }
                    écrire.println("    Minimale mesurée/calculée: " +arrondi(minM, 2) +" / " +arrondi(minMC, 2));
                    écrire.println("    Maximale mesurée/calculée: " +arrondi(maxM, 2) +" / " +arrondi(maxMC, 2));
                    écrire.println("    Moyenne mesurée/calculé: " +(arrondi((somme/31), 2)) + " / " +(arrondi((sommeC/31), 2)));
                    vérifierRecord(maxM, minM, maxMC, minMC, numM);
                    break;
                case 2: écrire.println("Février");
                    début = 31; fin = 58+d; numM = 2;
                    maxM = -50; minM = 0; maxMC = -50; minMC = 0;
                    for (int j = début; j<=fin; j++)
                    {
                        somme = somme + Moyenne[j][0];
                        sommeC = sommeC + Moyenne[j][1];
                        if (maxM < Moyenne[j][0])
                        {
                            maxM = Moyenne[j][0];
                        }
                        if (minM > Moyenne[j][0])
                        {
                            minM = Moyenne[j][0];
                        }
                        if (maxMC < Moyenne[j][1])
                        {
                            maxMC = Moyenne[j][1];
                        }
                        if (minMC > Moyenne[j][1])
                        {
                            minMC = Moyenne[j][1];
                        }
                    }
                    écrire.println("    Minimale mesurée/calculée: " +arrondi(minM, 2) +" / " +arrondi(minMC, 2));
                    écrire.println("    Maximale mesurée/calculée: " +arrondi(maxM, 2) +" / " +arrondi(maxMC, 2));
                    écrire.println("    Moyenne mesurée/calculé: " +(arrondi((somme/(28+d)), 2)) + " / " +(arrondi((sommeC/(28+d)), 2)));
                    vérifierRecord(maxM, minM, maxMC, minMC, numM);
                    break;
                case 3: écrire.println("Mars");
                    début = 59+d; fin = 89+d; numM = 3;
                    maxM = -50; minM = 0; maxMC = -50; minMC = 0;
                    for (int j = début; j<=fin; j++)
                    {
                        somme = somme + Moyenne[j][0];
                        sommeC = sommeC + Moyenne[j][1];
                        if (maxM < Moyenne[j][0])
                        {
                            maxM = Moyenne[j][0];
                        }
                        if (minM > Moyenne[j][0])
                        {
                            minM = Moyenne[j][0];
                        }
                        if (maxMC < Moyenne[j][1])
                        {
                            maxMC = Moyenne[j][1];
                        }
                        if (minMC > Moyenne[j][1])
                        {
                            minMC = Moyenne[j][1];
                        }
                    }
                    écrire.println("    Minimale mesurée/calculée: " +arrondi(minM, 2) +" / " +arrondi(minMC, 2));
                    écrire.println("    Maximale mesurée/calculée: " +arrondi(maxM, 2) +" / " +arrondi(maxMC, 2));
                    écrire.println("    Moyenne mesurée/calculé: " +(arrondi((somme/31), 2)) + " / " +(arrondi((sommeC/31), 2)));
                    vérifierRecord(maxM, minM, maxMC, minMC, numM);
                    break;
                case 4: écrire.println("Avril");
                    début = 90+d; fin = 119+d; numM = 4;
                    maxM = -50; minM = 0; maxMC = -50; minMC = 0;
                    for (int j = début; j<=fin; j++)
                    {
                        somme = somme + Moyenne[j][0];
                        sommeC = sommeC + Moyenne[j][1];
                        if (maxM < Moyenne[j][0])
                        {
                            maxM = Moyenne[j][0];
                        }
                        if (minM > Moyenne[j][0])
                        {
                            minM = Moyenne[j][0];
                        }
                        if (maxMC < Moyenne[j][1])
                        {
                            maxMC = Moyenne[j][1];
                        }
                        if (minMC > Moyenne[j][1])
                        {
                            minMC = Moyenne[j][1];
                        }
                    }
                    écrire.println("    Minimale mesurée/calculée: " +arrondi(minM, 2) +" / " +arrondi(minMC, 2));
                    écrire.println("    Maximale mesurée/calculée: " +arrondi(maxM, 2) +" / " +arrondi(maxMC, 2));
                    écrire.println("    Moyenne mesurée/calculé: " +(arrondi((somme/30), 2)) + " / " +(arrondi((sommeC/30), 2)));
                    vérifierRecord(maxM, minM, maxMC, minMC, numM);
                    break;
                case 5: écrire.println("Mai");
                    début = 120+d; fin = 150+d; numM = 5;
                    maxM = -50; minM = 50; maxMC = -50; minMC = 50;
                    for (int j = début; j<=fin; j++)
                    {
                        somme = somme + Moyenne[j][0];
                        sommeC = sommeC + Moyenne[j][1];
                        if (maxM < Moyenne[j][0])
                        {
                            maxM = Moyenne[j][0];
                        }
                        if (minM > Moyenne[j][0])
                        {
                            minM = Moyenne[j][0];
                        }
                        if (maxMC < Moyenne[j][1])
                        {
                            maxMC = Moyenne[j][1];
                        }
                        if (minMC > Moyenne[j][1])
                        {
                            minMC = Moyenne[j][1];
                        }
                    }
                    écrire.println("    Minimale mesurée/calculée: " +arrondi(minM, 2) +" / " +arrondi(minMC, 2));
                    écrire.println("    Maximale mesurée/calculée: " +arrondi(maxM, 2) +" / " +arrondi(maxMC, 2));
                    écrire.println("    Moyenne mesurée/calculé: " +(arrondi((somme/31), 2)) + " / " +(arrondi((sommeC/31), 2)));
                    vérifierRecord(maxM, minM, maxMC, minMC, numM);
                    break;
                case 6: écrire.println("Juin");
                    début =  151+d; fin = 180+d; numM = 6;
                    maxM = -50; minM = 50; maxMC = -50; minMC = 50;
                    for (int j = début; j<=fin; j++)
                    {
                        somme = somme + Moyenne[j][0];
                        sommeC = sommeC + Moyenne[j][1];
                        if (maxM < Moyenne[j][0])
                        {
                            maxM = Moyenne[j][0];
                        }
                        if (minM > Moyenne[j][0])
                        {
                            minM = Moyenne[j][0];
                        }
                        if (maxMC < Moyenne[j][1])
                        {
                            maxMC = Moyenne[j][1];
                        }
                        if (minMC > Moyenne[j][1])
                        {
                            minMC = Moyenne[j][1];
                        }
                    }
                    écrire.println("    Minimale mesurée/calculée: " +arrondi(minM, 2) +" / " +arrondi(minMC, 2));
                    écrire.println("    Maximale mesurée/calculée: " +arrondi(maxM, 2) +" / " +arrondi(maxMC, 2));
                    écrire.println("    Moyenne mesurée/calculé: " +(arrondi((somme/30), 2)) + " / " +(arrondi((sommeC/30), 2)));
                    vérifierRecord(maxM, minM, maxMC, minMC, numM);
                    break;
                case 7: écrire.println("Juillet");
                    début = 181+d; fin = 211+d; numM = 7;
                    maxM = -50; minM = 50; maxMC = -50; minMC = 50;
                    for (int j = début; j<=fin; j++)
                    {
                        somme = somme + Moyenne[j][0];
                        sommeC = sommeC + Moyenne[j][1];
                        if (maxM < Moyenne[j][0])
                        {
                            maxM = Moyenne[j][0];
                        }
                        if (minM > Moyenne[j][0])
                        {
                            minM = Moyenne[j][0];
                        }
                        if (maxMC < Moyenne[j][1])
                        {
                            maxMC = Moyenne[j][1];
                        }
                        if (minMC > Moyenne[j][1])
                        {
                            minMC = Moyenne[j][1];
                        }
                    }
                    écrire.println("    Minimale mesurée/calculée: " +arrondi(minM, 2) +" / " +arrondi(minMC, 2));
                    écrire.println("    Maximale mesurée/calculée: " +arrondi(maxM, 2) +" / " +arrondi(maxMC, 2));
                    écrire.println("    Moyenne mesurée/calculé: " +(arrondi((somme/31), 2)) + " / " +(arrondi((sommeC/31), 2)));
                    vérifierRecord(maxM, minM, maxMC, minMC, numM);
                    break;
                case 8: écrire.println("Août");
                    début = 212+d; fin = 242+d; numM = 8;
                    maxM = -50; minM = 50; maxMC = -50; minMC = 50;
                    for (int j = début; j<=fin; j++)
                    {
                        somme = somme + Moyenne[j][0];
                        sommeC = sommeC + Moyenne[j][1];
                        if (maxM < Moyenne[j][0])
                        {
                            maxM = Moyenne[j][0];
                        }
                        if (minM > Moyenne[j][0])
                        {
                            minM = Moyenne[j][0];
                        }
                        if (maxMC < Moyenne[j][1])
                        {
                            maxMC = Moyenne[j][1];
                        }
                        if (minMC > Moyenne[j][1])
                        {
                            minMC = Moyenne[j][1];
                        }
                    }
                    écrire.println("    Minimale mesurée/calculée: " +arrondi(minM, 2) +" / " +arrondi(minMC, 2));
                    écrire.println("    Maximale mesurée/calculée: " +arrondi(maxM, 2) +" / " +arrondi(maxMC, 2));
                    écrire.println("    Moyenne mesurée/calculé: " +(arrondi((somme/31), 2)) + " / " +(arrondi((sommeC/31), 2)));
                    vérifierRecord(maxM, minM, maxMC, minMC, numM);
                    break;
                case 9: écrire.println("Septembre");
                    début = 243+d; fin = 272+d; numM = 9;
                    maxM = -50; minM = 50; maxMC = -50; minMC = 50;
                    for (int j = début; j<=fin; j++)
                    {
                        somme = somme + Moyenne[j][0];
                        sommeC = sommeC + Moyenne[j][1];
                        if (maxM < Moyenne[j][0])
                        {
                            maxM = Moyenne[j][0];
                        }
                        if (minM > Moyenne[j][0])
                        {
                            minM = Moyenne[j][0];
                        }
                        if (maxMC < Moyenne[j][1])
                        {
                            maxMC = Moyenne[j][1];
                        }
                        if (minMC > Moyenne[j][1])
                        {
                            minMC = Moyenne[j][1];
                        }
                    }
                    écrire.println("    Minimale mesurée/calculée: " +arrondi(minM, 2) +" / " +arrondi(minMC, 2));
                    écrire.println("    Maximale mesurée/calculée: " +arrondi(maxM, 2) +" / " +arrondi(maxMC, 2));
                    écrire.println("    Moyenne mesurée/calculé: " +(arrondi((somme/30), 2)) + " / " +(arrondi((sommeC/30), 2)));
                    vérifierRecord(maxM, minM, maxMC, minMC, numM);
                    break;
                case 10: écrire.println("Octobre");
                    début = 273+d; fin = 303+d; numM = 10;
                    maxM = -50; minM = 50; maxMC = -50; minMC = 0;
                    for (int j = début; j<=fin; j++)
                    {
                        somme = somme + Moyenne[j][0];
                        sommeC = sommeC + Moyenne[j][1];
                        if (maxM < Moyenne[j][0])
                        {
                            maxM = Moyenne[j][0];
                        }
                        if (minM > Moyenne[j][0])
                        {
                            minM = Moyenne[j][0];
                        }
                        if (maxMC < Moyenne[j][1])
                        {
                            maxMC = Moyenne[j][1];
                        }
                        if (minMC > Moyenne[j][1])
                        {
                            minMC = Moyenne[j][1];
                        }
                    }
                    écrire.println("    Minimale mesurée/calculée: " +arrondi(minM, 2) +" / " +arrondi(minMC, 2));
                    écrire.println("    Maximale mesurée/calculée: " +arrondi(maxM, 2) +" / " +arrondi(maxMC, 2));
                    écrire.println("    Moyenne mesurée/calculé: " +(arrondi((somme/31), 2)) + " / " +(arrondi((sommeC/31), 2)));
                    vérifierRecord(maxM, minM, maxMC, minMC, numM);
                    break;
                case 11: écrire.println("Novembre");
                    début = 304+d; fin = 333+d; numM = 11;
                    maxM = -50; minM = 0; maxMC = -50; minMC = 0;
                    for (int j = début; j<=fin; j++)
                    {
                        somme = somme + Moyenne[j][0];
                        sommeC = sommeC + Moyenne[j][1];
                        if (maxM < Moyenne[j][0])
                        {
                            maxM = Moyenne[j][0];
                        }
                        if (minM > Moyenne[j][0])
                        {
                            minM = Moyenne[j][0];
                        }
                        if (maxMC < Moyenne[j][1])
                        {
                            maxMC = Moyenne[j][1];
                        }
                        if (minMC > Moyenne[j][1])
                        {
                            minMC = Moyenne[j][1];
                        }
                    }
                    écrire.println("    Minimale mesurée/calculée: " +arrondi(minM, 2) +" / " +arrondi(minMC, 2));
                    écrire.println("    Maximale mesurée/calculée: " +arrondi(maxM, 2) +" / " +arrondi(maxMC, 2));
                    écrire.println("    Moyenne mesurée/calculé: " +(arrondi((somme/30), 2)) + " / " +(arrondi((sommeC/30), 2)));
                    vérifierRecord(maxM, minM, maxMC, minMC, numM);
                    break;
                case 12: écrire.println("Décembre");
                    début = 334; fin = 364+d; numM = 12;
                    maxM = -50; minM = 0; maxMC = -50; minMC = 0;
                    for (int j = début; j<=fin; j++)
                    {
                        somme = somme + Moyenne[j][0];
                        sommeC = sommeC + Moyenne[j][1];
                        if (maxM < Moyenne[j][0])
                        {
                            maxM = Moyenne[j][0];
                        }
                        if (minM > Moyenne[j][0])
                        {
                            minM = Moyenne[j][0];
                        }
                        if (maxMC < Moyenne[j][1])
                        {
                            maxMC = Moyenne[j][1];
                        }
                        if (minMC > Moyenne[j][1])
                        {
                            minMC = Moyenne[j][1];
                        }
                    }
                    écrire.println("    Minimale mesurée/calculée: " +arrondi(minM, 2) +" / " +arrondi(minMC, 2));
                    écrire.println("    Maximale mesurée/calculée: " +arrondi(maxM, 2) +" / " +arrondi(maxMC, 2));
                    écrire.println("    Moyenne mesurée/calculé: " +(arrondi((somme/31),2)) + " / " +(arrondi((sommeC/31),2)));
                    vérifierRecord(maxM, minM, maxMC, minMC, numM);
                    break;
            }
        }   
    }
    
    public static void calculTminmax(){
    //Le calcul pour le Tmax et Tmin dans tout le fichier
        max = 0; maxC = 0; min = 0; minC = 0;
        for(int i=0; i<Moyenne.length; i++)
        {
            if (max < Moyenne[i][0])
            {
                max = Moyenne[i][0];
            }
            if (min > Moyenne[i][0])
            {
                min = Moyenne[i][0];
            }
            if (maxC < Moyenne[i][1])
            {
                maxC = Moyenne[i][1];
            }
            if (minC > Moyenne[i][1])
            {
                minC = Moyenne[i][1];
            }
        }
    }
    
    public static void écrireStats (int nbrJours) throws Exception{
    //La méthodes pour écrire tout les données dans le fichier _StatGlobales.txt
        écrire = new PrintWriter (new FileOutputStream(année +"_StatGlobales.txt"), true);
        écrire.println("T° Annuelle minimale mesurée/calculée: " +min +"°C / " +minC +"°C");
        écrire.println("T° Annuelle maximale mesurée/calculée: " +max +"°C / " +maxC +"°C");
        écrire.println("Moyenne annuelle: " +TmoyMesAnnée +"°C / " +TmoyCalAnnée +"°C");
        écrire.println("Nombres de jours avec refroidissement éolien inférieur à -20°C: " +nbrJoursInf1);
        écrire.println("Nombres de jours avec refroidissement éolien inférieur à -25°C: " +nbrJoursInf2);
        écrire.println("Nombres de jours avec réchauffement humidex supérieur à 25°C: " +nbrJoursSup1);
        écrire.println("Nombres de jours avec réchauffement humidex supérieur à 30°C: " +nbrJoursSup2);
        écrire.println();
    //J'ai appeler la méthode içi car je n'arriver pas à la mettre 
    //dans le main et écrire sans que ca ne delete le texte dans le document ....
        calculMensuel(nbrJours);
        écrire.close();
    }
    
    public static void calculHumidex (){
    //Le calcul du nombre de jours avec réchauffement humidex 
        for (int i = 0; i<Moyenne.length; i++){
            if (Moyenne[i][1] > 25){
                nbrJoursSup1++;
            }
        }
        for (int i = 0; i<Moyenne.length; i++){
            if (Moyenne[i][1] > 30){
                nbrJoursSup2++;
            }
        }
    }
    
    public static void calculÉolien (){
    //Le calcul du nombre de jours avec refroidissement éolien
        for (int i = 0; i<Moyenne.length; i++){
            if (Moyenne[i][1] < -20){
                nbrJoursInf1++;
            }
        }
        for (int i = 0; i<Moyenne.length; i++){
            if (Moyenne[i][1] < -25){
                nbrJoursInf2++;
            }
        }
    }
    
    public static void calculAnnuel(int nbrJours){
    //Calcul des données pour l'année donnée
        double sommeMes = 0;
        double sommeCal = 0;
        for (int i = 0; i<Moyenne.length; i++){
            sommeMes = sommeMes + Moyenne[i][0];
            sommeCal = sommeCal + Moyenne[i][1];
        }
        TmoyMesAnnée = sommeMes / nbrJours;
        TmoyCalAnnée = sommeCal / nbrJours;
        TmoyMesAnnée = arrondi(TmoyMesAnnée, 2);
        TmoyCalAnnée = arrondi(TmoyCalAnnée, 2);
    }
    
    public static double calculTcalculé (String Matrice[][], double Tmoyenne, int i){
    //Calcul de Tcalculé pour l'écrire dans le document _calcul
        double Tcalculé = 0;
        if (Tmoyenne > 20){
            double humidex = Tmoyenne + ((5.0 / 9.0) * ((Double.parseDouble(Matrice[i][3]) * 10) - 10));
            Tcalculé = humidex;
        }
        else if(Tmoyenne < 15){
            double Vvent = Double.parseDouble(Matrice[i][2]);
            double éolien = 13.12 + (0.6215 * Tmoyenne) - (11.37 * java.lang.Math.pow(Vvent, 0.16)) + (0.3965 * Tmoyenne * java.lang.Math.pow(Vvent, 0.16));
            Tcalculé = éolien;
        }   
        else {
            Tcalculé = Tmoyenne;
        }
        return Tcalculé;
    }
    
    public static void calculTmoyenne(String Matrice[][]) throws Exception{
    //Calcul de la moyenne et sauvegarde dans la matrice moyenne pour plus tard
        écrire = new PrintWriter (new FileOutputStream (année +"_calcul.txt"));
        écrire.println("Tmoyenne(°C)" +"\t" +"Tcalculé(°C)");
        Moyenne = new double [Matrice.length+1][2];
        for (int i = 1; i < Matrice.length; i++){
            double moyenne = ((Double.parseDouble(Matrice[i][0]) + Double.parseDouble(Matrice[i][1])) /2);
            écrire.print(arrondi(moyenne, 2) +"\t" +"\t");
            Moyenne[i-1][0] = moyenne;
            écrire.println(arrondi(calculTcalculé(Matrice, moyenne, i), 2));
            Moyenne[i-1][1] = arrondi(calculTcalculé(Matrice, moyenne, i), 2);
        }
        écrire.close();
    }
    
    public static void lireFichier(String Matrice[][]) throws Exception{
    //Méthode pour lire le fichier et remplissage de la matrice comme demandé
        lire = new BufferedReader (new FileReader(année +".txt"));
        String ligne;
        int i = 0;
        while ((ligne = lire.readLine()) != null) {
                String[] tab = ligne.split("\t");
                for (int P = 0; P < tab.length; P++) {
                    Matrice[i][P] = tab[P];
                }
                i++;
            }
        lire.close();
    }
    
    public static boolean checkDonnées (int nbrJours) throws Exception{
    //Méthode pour vérifier le nombre de données disponible dans le fichier selon l'année entrée
        String ligne;
        int counter = 0;
        while ((ligne = lire.readLine()) != null){
            counter++;
        }
        lire.close();
        System.out.println("Il manques " +(nbrJours - (counter-1)) +" données");
        return (counter-1) == nbrJours;
    }
    
    public static int checkBis (){
    //Méthode pour vérifier si l'année est bisextille ou non
        if (année%4 == 0 && année%100 != 0 || année%400 == 0){
            return 366;
        }
        else{
            return 365;     
        }  
    }
    
    public static double arrondi(double valeur, int dec){
    //La méthode pour arrondir donnée dans le document
        double mult = Math.pow (10.0, (double)dec); 
        return Math.round(valeur * mult) / mult;
    }

}
