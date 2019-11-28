/*
 * Version 1.0 : Version non optimisé, ne contient pas d'écriture du fichier
 * Version 1.1 : Version optimisé minimalement
 * Version 1.2 ; Version optimisé finale
 */

/*
 * Nom fichier: TP2.java
 * Description: Minijeu de poker
 * Auteur: Diab Khanafer
 * Version: 1.2
 * Date: 16 novembre 2016
 */

package pkjtp2;
import java.io.*;
import java.util.*;

public class PrjTP2 {

    public static void main(String [] args) throws Exception {
        final int montantDepart = 100; 
        int tabCartes[] = new int [52];
        int tabMain[] = new int [5];
        int montantActuel[] = {100};
        boolean stop = true;
        boolean restart = true;
        boolean tabCartesValue[] = new boolean[52];
        char tabMainAfficherCartes[] = new char [5];
        String tabMainAfficherValeur[] = new String [5];
        String playerName = MainMenu();
    //Les deux boucles permettent de recommencer dès le debut, ou de continuer
        do{
            GameStart(montantDepart, playerName);
            do{
                IniDeck(tabCartes);
                IniValue(tabCartesValue);
                RandomCartes(tabMain, tabCartesValue, tabMainAfficherCartes, tabMainAfficherValeur);
                SwitchCartes(tabMain, tabCartesValue, tabMainAfficherCartes, tabMainAfficherValeur);
                Trier(tabMain);
                VerifierMain(tabMain, montantActuel);
                stop = StopContinue(montantActuel);
            } while(stop == false);
            char s;
            Scanner read = new Scanner(System.in);
            String lecture;
            System.out.print("  Souhaitez-vous recommencez (Entrez O ou N)? ");
            lecture=read.nextLine();
            s=lecture.charAt(0);
                if (s == 'n' || s == 'N') {
                    restart = false;
                }
            System.out.println();    
        } while (restart == true);
        
    //Juste pour encore plus de fun!!
    //Le scoreboard est totalement inutile pour le moment.    
        PrintWriter ecrirefichier= new PrintWriter(new FileOutputStream("Scoreboard.txt",true ));
        ecrirefichier.println("Nom du joueur: " +playerName);
        ecrirefichier.println("HighestScore: " +montantActuel[0] +"$");
        ecrirefichier.close();
    }
    
    public static String MainMenu (){
    //Sera utilisé un jour pour pouvoir save le nom du jouer dans le document de highscore    
        System.out.print("Entrez votre nom: ");
        Scanner name = new Scanner(System.in);
        String playerName = name.nextLine();
        System.out.println("(Un système de highscore sera introduit dans une prochaine version .... un jour ...)"); //Ça serait le fun de savoir comment ... :D
        System.out.println("-----------------------------------------------------------------------------------------");
    //Entrée du jeu et informartions présentées aux joueurs
        System.out.println("*********************************");    
        System.out.println("*    Bienvenue au Poker Game    *");
        System.out.println("* Version 1.2 --- Fait Par Diab *");
        System.out.println("*********************************");
        return playerName;
    }
    
    public static void GameStart (int montantDepart, String playerName){   
        //Les cas où le joueur gagne ou perd de l'argent
        //Les règles sont présentées au joueur
        String tabRegles[][] = new String [9][2];
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("                                     Vous jouez en tant que " +playerName +".");
        System.out.println("                                        Voici les règles du jeu :");
        System.out.println("                                     ------------------------------");
        System.out.print(tabRegles[0][0] = "                                         Rien" +"\t");
        System.out.println(tabRegles[0][1] = "         -10$");
        System.out.print(tabRegles[1][0] = "                                         1 paire" +"\t");
        System.out.println(tabRegles[1][1] = " 0$");
        System.out.print(tabRegles[2][0] = "                                         2 paires" +"\t");
        System.out.println(tabRegles[2][1] = " +20$");
        System.out.print(tabRegles[3][0] = "                                         Brelan (3)" +"\t");
        System.out.println(tabRegles[3][1] = " +35$");
        System.out.print(tabRegles[4][0] = "                                         Suite" +"\t");
        System.out.println(tabRegles[4][1] = "         +50$");
        System.out.print(tabRegles[5][0] = "                                         Full (3 + 2)" +"\t");
        System.out.println(tabRegles[5][1] = " +75$");
        System.out.print(tabRegles[6][0] = "                                         Couleur" +"\t");
        System.out.println(tabRegles[6][1] = " +100$");
        System.out.print(tabRegles[7][0] = "                                         Carré" +"\t");
        System.out.println(tabRegles[7][1] = "         +150$");
        System.out.print(tabRegles[8][0] = "                                         Quinte Royale" +"\t");
        System.out.println(tabRegles[8][1] = " +500$"); 
        System.out.println();
        //La présentation du montant de départ avec lequel le joueur commence
        System.out.println("                                      ////////////////////////////");
        System.out.println("                                      / Vous commencez avec " +montantDepart +"$ /");
        System.out.println("                                      ////////////////////////////");
        
    }
    
    //Méthode pour attribuer 5 valeurs de cartes pour les déterminer avec une autre méthode
    public static void RandomCartes (int tabMain[], boolean tabCartesValue[], char tabMainAfficherCartes[], String tabMainAfficherValeur[]){
    //Cette méthode me fournie 5 numéros random qui sont afficher par la suite par une autre méthode
        Random cartes = new Random();
        for (int l = 0; l < tabMain.length; l++)
        {
            tabMain[l] = -1;
        }
        for (int j = 0; j < tabMain.length ; j++)
        {
            do {
                int R = cartes.nextInt(52);
                if (tabCartesValue[R] == true)
                {
                    tabMain[j] = R;
                    tabCartesValue[R] = false;
                }
            } while (tabMain[j] == -1);
        }
    //Utiliser pour vérifier si le code fonctionnne, elle affiche les numéros obtenus par le random
        /*for (int k = 0; k < tabMain.length; k++)      
        {
            System.out.print(tabMain[k] + "\t");
        } 
        System.out.println();*/
        System.out.println("|---------------------------------------------------------------------|");
     //Appel de la méthode pour déterminer les cartes dans la main
        DeterminerCartes(tabMain, tabMainAfficherCartes, tabMainAfficherValeur);
    }
    
    public static void DeterminerCartes (int tabMain[], char tabMainAfficherCartes[], String tabMainAfficherValeur[]){
        //La méthode reçoit les cartes générées aléatoirement par une autre méthode et détermine la couleur/le numéro
        System.out.println("    Voici les cartes qui sont dans vos mains.");
        for (int i = 0; i < tabMain.length; i++)
        {
            switch (tabMain[i] / 13)
            {
                //J'ai découvert que les codes de couleur, ça marcher bien pour le visuel .... :D
                case 0: tabMainAfficherCartes[i] = '♥'; 
                System.out.print("    " +"\033[31m" +tabMainAfficherCartes[i] +"\033[0m" + "   ");    
                    break;
                case 1: tabMainAfficherCartes[i] = '♦';
                System.out.print("    " +"\033[31m" +tabMainAfficherCartes[i] +"\033[0m" + "   ");    
                    break;
                case 2: tabMainAfficherCartes[i] = '♣';
                System.out.print("    " +tabMainAfficherCartes[i] + "   ");    
                    break;
                case 3: tabMainAfficherCartes[i] = '♠'; 
                System.out.print("    " +tabMainAfficherCartes[i] + "   ");
                    break;
            }
            //La façon original de les afficher avant de découvrir les couleurs
                //System.out.print("    " +tabMainAfficherCartes[i] + "   ");
        }
        System.out.println();
    //Ici, les valeurs des 5 random sont déterminer et afficher au joueur
        for (int i = 0; i < tabMain.length; i++)
        {
            switch (tabMain[i] % 13 )
            {
                case 0: tabMainAfficherValeur[i] = "A";
                    break;
                case 1: tabMainAfficherValeur[i] = "2";
                    break;
                case 2: tabMainAfficherValeur[i] = "3";
                    break;
                case 3: tabMainAfficherValeur[i] = "4";
                    break;
                case 4: tabMainAfficherValeur[i] = "5";
                    break;
                case 5: tabMainAfficherValeur[i] = "6";
                    break;
                case 6: tabMainAfficherValeur[i] = "7";
                    break;
                case 7: tabMainAfficherValeur[i] = "8";
                    break;
                case 8: tabMainAfficherValeur[i] = "9";
                    break;
                case 9: tabMainAfficherValeur[i] = "10";
                    break;
                case 10: tabMainAfficherValeur[i] = "V";
                    break;
                case 11: tabMainAfficherValeur[i] = "D";
                    break;
                case 12: tabMainAfficherValeur[i] = "R";
                    break;
            }
                System.out.print("    " +tabMainAfficherValeur[i] + "   ");        
        }
        System.out.println("\n");
    }
    
    public static void SwitchCartes (int tabMain[], boolean tabCartesValue[], char tabMainAfficherCartes[], String tabMainAfficherValeur[]){
        int oldValeur;
        int counter = 0;
        boolean posSwitchCartes[] = new boolean [5];
            for (int i=0; i<posSwitchCartes.length; i++){
                posSwitchCartes[i] = true;
            }
        Random cartes = new Random();
        Scanner posCartes = new Scanner(System.in);
        System.out.println("  Vous pouvez changer de 0 à 4 cartes de votre main.");
        System.out.println("  Cepedant, vous ne pouvez changer la même position de carte plus d'une fois.");
    //La boucle while s'assure que le joueur ne change pas plus de 4 cartes    
        while (counter != 4){
            System.out.print("  Entrez la position de la carte à changer (de 1 à 5, 0 ne changera rien). ");
            int nbrCartesSwitch = posCartes.nextInt();
                if (nbrCartesSwitch == 0)
                {
                    System.out.println("  Vous avez changer " +counter +" carte(s).");
                    System.out.println();
                    DeterminerCartes(tabMain, tabMainAfficherCartes, tabMainAfficherValeur);
                    return;
                }
                while (nbrCartesSwitch < 0 || nbrCartesSwitch >= 6)
                {
                    System.out.println("  Veuillez entrez une position entre 1 et 5.");
                    nbrCartesSwitch = posCartes.nextInt(); 
                }
                //Modification de ma façon de procéder au changement pour répondre aux attentes
                    while (posSwitchCartes[nbrCartesSwitch-1] == false){
                        System.out.print("  Vous ne pouvez pas rechanger la même position. Veuillez choisir une autre carte. ");
                        nbrCartesSwitch = posCartes.nextInt();
                        if (nbrCartesSwitch == 0)
                        {
                            System.out.println("  Vous avez changer " +counter +" carte(s).");
                            System.out.println();
                            DeterminerCartes(tabMain, tabMainAfficherCartes, tabMainAfficherValeur);
                            return;
                        }
                    }
            //Vérification pour ne pas ravoir une carte que l'on n'a pas voulue/deja obtenue
                posSwitchCartes[nbrCartesSwitch-1] = false;
                oldValeur = tabMain[nbrCartesSwitch - 1];
                do {
                    int R = cartes.nextInt(52);
                    if (tabCartesValue[R] == true)
                    {
                        tabMain[nbrCartesSwitch - 1] = R;
                        tabCartesValue[R] = false;
                    }
                } while (tabMain[nbrCartesSwitch - 1] == oldValeur);
            counter++;
            System.out.println("  Vous avez changer " +counter +" carte(s).");
        //Retour à la méthode d'affichage des cartes pour représenter au joueur les cartes 
        //qu'il a maintenant et à chaque fois qu'il en change une
            DeterminerCartes(tabMain, tabMainAfficherCartes, tabMainAfficherValeur);
        }
    }
    
    public static void VerifierMain (int tabMain[], int montantActuel[]){   
    //Vérifier une quinte royal (5 cartes qui se suivent dans la même sorte)
        int counterRS = 0; //Counter pour la sorte de cartes
        int counterRV = 0; //Counter pour les valeurs de cartes
        for (int i = 0; i<tabMain.length-1; i++){
            if (tabMain[i] / 13 == tabMain[i+1] / 13){
                counterRS++;
                if (tabMain[i] % 13 == (tabMain[i+1] % 13)-1){
                    counterRV++;
                }
            }
            if (counterRS == 4 && counterRV == 4){
                int moneyCase = 1;
                System.out.println("  Tu a une quinte royale!!");    
                Money(montantActuel, moneyCase);
                return;
            }
        }
    //Vérifier un carré, 4 cartes identiques en valeur
        int counterC = 0;
        for (int i = 0; i<tabMain.length; i++){
            for (int j = 0; j<tabMain.length; j++)
            {
                if(tabMain[i]%13 == tabMain[j]%13)
                {
                    counterC++;
                }
            }
            if (counterC == 17){
                int moneyCase = 2;
                System.out.println("  Tu a un carré!!");
                Money(montantActuel, moneyCase);
                return;
            }
        }
    //Vérifier une couleur, 5 cartes de la même sorte
        int counterCO = 0;
        for (int i=0; i<tabMain.length-1; i++){
            if (tabMain[i] / 13 == tabMain[i+1] / 13){
                counterCO++;
            }
            if (counterCO == 4){
                int moneyCase = 3;
                System.out.println("  Tu a une couleur!!");    
                Money(montantActuel, moneyCase);
                return;
            }
        }
    //Vérifier un full: une brelan (3 cartes de mêmes valeurs) + paire 
        int counterF = 0;
        for (int i = 0; i<tabMain.length; i++){
            for (int j = 0; j<tabMain.length; j++)
            {
                if(tabMain[i]%13 == tabMain[j]%13)
                {
                    counterF++;
                }
            }
            if (counterF == 13){
                int moneyCase = 4;
                System.out.println("  Tu a un full!!");
                Money(montantActuel, moneyCase);
                return;
            }
        }
    //Vérifier une suite(Quinte), 5 cartes qui se suivent
        int counterS = 0;
        int tabMainReduit[] = new int [5];
            for (int i = 0; i<tabMain.length; i++){
                tabMainReduit[i] = tabMain[i];
            }
        for (int i = 0; i<tabMain.length-1; i++){
            TrierRéduit(tabMainReduit);
            if (tabMainReduit[i] % 13 == (tabMainReduit[i+1] % 13)-1){
                    counterS++;
            }
            if (counterS == 4){
                int moneyCase = 5;
                System.out.println("  Tu a une suite!!");    
                Money(montantActuel, moneyCase);
                return;
            }
        }
    //Vérifier un brelan, 3 cartes de même valeurs
        int counterB = 0;
        for (int i = 0; i<tabMain.length; i++){
            for (int j = 0; j<tabMain.length; j++)
            {
                if(tabMain[i]%13 == tabMain[j]%13)
                {
                    counterB++;
                }
            }
            if (counterB == 11){
                int moneyCase = 6;
                System.out.println("  Tu a un brelan!!");
                Money(montantActuel, moneyCase);
                return;
            }
        }
    //Vérifier 2 paires
        int counterPS = 0;
        for (int i = 0; i<tabMain.length; i++){
            for (int j = 0; j<tabMain.length; j++)
            {
                if(tabMain[i]%13 == tabMain[j]%13)
                {
                    counterPS++;
                }
            }
            if (counterPS == 9){
                int moneyCase = 7;
                System.out.println("  Tu a deux paires!!");
                Money(montantActuel, moneyCase);
                return;
            }
        }
    //Vérifier une paire
        int counterP = 0;
        for (int i = 0; i<tabMain.length; i++){
            for (int j = 0; j<tabMain.length; j++)
            {
                if(tabMain[i]%13 == tabMain[j]%13)
                {
                    counterP++;
                }
            }
            if (counterP == 7){
                int moneyCase = 8;
                System.out.println("  Tu a une paire!!");
                Money(montantActuel, moneyCase);
                return;
            }
        }
    //Si aucun des cas précéfents, la méthode continuera jusqu'aux lignes suivantes
        System.out.println("  Vous n'avez rien.");
        int moneyCase = 0;
        Money(montantActuel, moneyCase);
        
    }
    
    public static void TrierRéduit (int tabMainReduit[]){
    //Le trie des cartes dans les mains des joueurs selon leur valeurs afficher, facilite la recherche d'une couleur    
        int temp;
        for (int j = 0; j<tabMainReduit.length; j++){
            tabMainReduit[j] = tabMainReduit[j]%13;
        }
        for (int i = 0; i <= tabMainReduit.length-1; i++)
        {  
            int m = i; 
            for (int j = i+1; j <= tabMainReduit.length-1; j++)    
                if (tabMainReduit[j] < tabMainReduit[m]) 
                m = j ; 
                temp = tabMainReduit[m];
                tabMainReduit[m] = tabMainReduit[i];
                tabMainReduit[i] = temp;
	}
    }
    
    public static void Trier (int tabMain[]){
    //Le trie général pour pouvoir faire la vérification des cartes du joueur
        int temp;
        /*for (int j = 0; j<tabMain.length; j++){
            tabMain[j] = tabMain[j]%13;
        }*/
        for (int i = 0; i <= tabMain.length-1; i++)
        {  
            int m = i; 
            for (int j = i+1; j <= tabMain.length-1; j++)    
                if (tabMain[j] < tabMain[m]) 
                m = j ; 
                temp = tabMain[m];
                tabMain[m] = tabMain[i];
                tabMain[i] = temp;
	}
    }
    
    public static void Money (int montantActuel[], int moneyCase){
    //La méthode pour ajouter/enlever de l'argent au joueur 
        final int rien = -10, paire = 0, paires = 20, brelan = 35, suite = 50;
        final int full = 75, couleur = 100, carre = 150, royale = 500;
        int gain = 0;
        switch (moneyCase)
        {
            case 1: montantActuel[0] = montantActuel[0] + royale;
                gain = royale;
                break;
            case 2: montantActuel[0] = montantActuel[0] + carre;
                gain = carre;
                break;
            case 3: montantActuel[0] = montantActuel[0] + couleur ;
                gain = couleur;
                break; 
            case 4: montantActuel[0] = montantActuel[0] + full;
                gain = full;
                break;
            case 5: montantActuel[0] = montantActuel[0] + suite;
                gain = suite;
                break;
            case 6: montantActuel[0] = montantActuel[0] + brelan;
                gain = brelan;
                break;
            case 7: montantActuel[0] = montantActuel[0] + paires;
                gain = paires;
                break;
            case 8: montantActuel[0] = montantActuel[0] + paire;
                gain = paire;
                break;
            default : montantActuel[0] = montantActuel[0] + rien;
                gain = rien;
        }
        System.out.println("                                 //////////////////////////////////");
        System.out.println("                                 / Vous avez fait un gain de " +gain +" $  /" );
        System.out.println("                                 /   Vous avez actuellement " +montantActuel[0] +"$  /");
        System.out.println("                                 //////////////////////////////////");
    }
    
    public static boolean StopContinue (int montantActuel[]){
    //Içi, je demande au joueur s'il veut continuer de jouer ou arrêter.
    //S'il a 0$ ou inférieur, il ne peut que recommencez.
        char s; boolean stop = false;
        Scanner read = new Scanner(System.in);
        String lecture;
        System.out.print("  Entrez 'c' pour continuer ou 'q' pour quitter. ");
        lecture=read.nextLine();
        s=lecture.charAt(0);
        if (montantActuel[0] <= 0)
        {
            System.out.println("  /////////////////////////////////////////////////////////////////////");
            System.out.println("  / Vous avez atteint 0$ ou inférieur, vous ne pouvez plus continuer. /");
            System.out.println("  /////////////////////////////////////////////////////////////////////");
            stop = true;
        }
        if (s == 'q' || s == 'Q')
        {
            stop = true;
            return stop;
        }
        System.out.println();
        return stop;
    }
    
    public static void IniValue (boolean tabCartesValue[]){     //Reset les valeurs des cartes à true
        for (int i = 0; i < tabCartesValue.length; i++)
        {
            tabCartesValue[i]=true;
        }
    }
    
    public static void IniDeck (int tabCartes[]){               //Reset le deck des cartes de 0 à 51
        for (int i = 0; i < tabCartes.length; i++)
        {
            tabCartes[i]=i;
        }
    }
}