/*
 * Nom fichier: TP1.java
 * Description: Calcul d'un voyage
 * Auteur: Diab Khanafer
 * Version: 1.0
 * Date: 
 */
package pkjtp1;
import java.util.Scanner;

public class PrjTP1 {

    public static void main(String[] args) {
        //--------------/Toutes les variables//--------------//
        int dest_finale, nbr_pers=0, nbr_jours = 0, nbr_sejour, categ, nbr_chambre=0, type_location, prix_chambre=0;                
        int age, groupe_choisie, type_famille, nbr_enfant, reste_enfant, type_chambre, nbr_jour_location=0;
        double prix_TPS, prix_TVQ;
        double prix_avion=0, rabais_applicable=0, prix_avTaxes=0, prix_apTaxes=0, prix_voyage_total=0, prix_chambre_total, prix_location=0;
        char s; boolean marcher = true;
        String nom, restart;
        String.format("%1$.2f", prix_apTaxes);
        //-------------//Toutes les constantes//-------------//
        int avion_SI=310, avion_HF=350, avion_TO=460, avion_VA=790;
        int prix_chambre_reg=60, prix_chambre_dou=100, prix_chambre_fam=300, prix_chambre_sui=400;
        int prix_voiture_cit=30, prix_voiture_4x4=50, prix_voiture_luxe=100, prix_bus=150;
        double taxe_TPS = 0.05, taxe_TVQ = 0.09975;        
        //----------//Tous les rabais applicables//----------//
        double rabais_AA_seul=0.95, rabais_groupe_sco=0.96, rabais_groupe_ret=0.98;
        double rabais_famille1=0.94, rabais_famille2=0.93;
        double rabais_famille3=0.92, rabais_famille4=0.90;           
    
    do{
        //------------------------------------------------------------------------//
        //Menu 1 (Nom de la personne)    

        Scanner name=new Scanner(System.in);
        System.out.println("Entrez votre nom.");
        nom=name.nextLine();

        //------------------------------------------------------------------------//
        System.out.println("-----------------------------------------");
        //------------------------------------------------------------------------//

        //Menu 2 (Choix de la destination)    
            Scanner avion=new Scanner(System.in);
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("|Veuillez choisir la destination de votre voyage. |");     //Ici, je demande à l'utilisateur quelle va être sa destination.
            System.out.println("|1- Sept-Îles  (310$/personne)                    |");
            System.out.println("|2- Halifax    (350$/personne)                    |");
            System.out.println("|3- Toronto    (460$/personne)                    |");
            System.out.println("|4- Vancouver  (790$/personne)                    |");
            System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.print("Entrez votre choix: ");

        do {
            dest_finale=avion.nextInt();
            switch (dest_finale)
            {
                case 1: System.out.println("Vous avez choisis le choix " +dest_finale +" qui veut dire Sept-Îes.");
                    prix_avion=avion_SI;
                    break;
                case 2: System.out.println("Vous avez choisis le choix  " +dest_finale +" qui veut dire Halifax.");
                    prix_avion=avion_HF;
                    break;
                case 3: System.out.println("Vous avez choisis le choix " +dest_finale +" qui veut dire Toronto.");
                    prix_avion=avion_TO;
                    break;
                case 4: System.out.println("Vous avez choisis le choix " +dest_finale +" qui veut dire Vancouver.");
                    prix_avion=avion_VA;
                    break;
                default: System.out.println("Vous avez entrée une destination non valide.");
                    System.out.print("Rentrez votre choix: ");
                    break;
            }
        } while ((dest_finale > 4) || (dest_finale==0));

         //------------------------------------------------------------------------//
        System.out.println("-----------------------------------------");
         //------------------------------------------------------------------------//

        //Menu 3 (Choix du séjour)
            Scanner sejour=new Scanner(System.in);
            System.out.println("Quel est votre type de séjour? Choisissez parmis les choix suivants.");
            System.out.println("1- Une fin de semaine (2 jours)");
            System.out.println("2- Une semaine complète (7 jours)");
            System.out.println("3- Autre ... (Personnalisé)");
            System.out.print("Entrez votre choix: ");

        do {
            nbr_sejour=sejour.nextInt();
            switch (nbr_sejour)
            {
                case 1: System.out.println("Vous avez choisis le choix 1 qui veut dire que votre séjour sera de 2 jours.");
                    nbr_sejour=1;
                    nbr_jours=2;
                    break;
                case 2: System.out.println("Vous avez chosis le choix 2 qui veut dire que votre séjour sera de 7 jours.");
                    nbr_sejour=2;
                    nbr_jours=7;
                    break; 
                case 3: System.out.println("Vous avez chosis le choix 3. Veuillez entrez le nombre de jours de votre séjour.");
                    nbr_jours=sejour.nextInt();
                    System.out.println("Votre séjour sera de " +nbr_jours +" jours.");
                    break;
                default: System.out.println("Vous avez entrée un choix non valide.");
                    System.out.print("Rentrez votre choix à nouveau: ");
                    break;
            }  
        } while (nbr_sejour > 3 || nbr_sejour == 0);

        //------------------------------------------------------------------------//
        System.out.println("-------------------------------");
        //------------------------------------------------------------------------//

        //Menu 4 (Choix de la catégorie)

            System.out.println("Êtes-vous dans l'une de ces catégories?");
            System.out.println("1- Une personne seul");
            System.out.println("2- Une groupe");
            System.out.println("3- Une famille");
            System.out.print("Choisissez votre catégorie: ");
            Scanner categorie=new Scanner(System.in);
            categ=categorie.nextInt();
            do {
                switch (categ)
                {
                    case 1: System.out.println("||||||||||||||||||||||||||||||");
                        System.out.println("|            Âge             |");
                        System.out.println("||||||||||||||||||||||||||||||");
                        Scanner pers_age=new Scanner(System.in);
                        System.out.println("Quel âge avez vous?");
                        categ=1;
                        age=pers_age.nextInt();
                        nbr_pers=1;
                            if (age > 65) 
                                rabais_applicable=rabais_AA_seul;
                            else if (13 < age && age < 18)
                                rabais_applicable=rabais_AA_seul;
                            else 
                                rabais_applicable=1;
                        break;
                    case 2: System.out.println("|||||||||||||||||||||||||||||||||||||");
                        System.out.println("|Vous avez fait le choix de groupe  |");
                        System.out.println("|Quel type de groupe êtes-vous?     |");
                        System.out.println("|1- Groupe d'ainé                   |");
                        System.out.println("|2- Groupe d'enfant                 |");
                        System.out.println("|||||||||||||||||||||||||||||||||||||");
                        System.out.println("*Les groupes doivent être de plus de 10 personnes pour un rabais.");
                        System.out.print("Entrez le type: ");
                        categ=2;
                        Scanner groupe=new Scanner(System.in);
                        groupe_choisie=groupe.nextInt();
                        if (groupe_choisie > 2 || groupe_choisie == 0)
                            do{
                                System.out.println("Vous avez entrée un choix non valide.");
                                System.out.println("Veuillez resaisir votre type de groupe.");
                                System.out.print("Entrez le type: ");
                                groupe_choisie=groupe.nextInt();
                            } while (groupe_choisie>2 || groupe_choisie==0);
                        System.out.print("Entrez le nombre de personne dans le groupe: ");
                        Scanner groupe_perso=new Scanner(System.in);
                        nbr_pers=groupe_perso.nextInt();
                        if (nbr_pers > 10 && (groupe_choisie == 1 || groupe_choisie==2))
                                switch (groupe_choisie)
                                {
                                    case 1: System.out.println("Vous êtes un groupe d'ainé avec " +nbr_pers +" personnes.");
                                        rabais_applicable=rabais_groupe_ret;
                                        break;
                                    case 2: System.out.println("Vous êtes un groupe scolaire avec " +nbr_pers +" personnes.");
                                        rabais_applicable=rabais_groupe_sco;
                                        break;
                                }
                        else
                        {
                            if (groupe_choisie==1)
                            {
                                System.out.println("Vous êtes un groupe d'ainé avec " +nbr_pers +" personnes.");
                                rabais_applicable=1;
                            }
                            else if (groupe_choisie==2)
                            {
                                System.out.println("Vous êtes un groupe scolaire avec " +nbr_pers +" personnes.");
                                rabais_applicable=1;
                            }
                        }  
                        break;
                    case 3: System.out.println("||||||||||||||||||||||||||||||||||||||||||||||");
                        System.out.println("|Vous avez fait le choix de famille          |");
                        System.out.println("|Quel type de groupe êtes-vous?              |");
                        System.out.println("|1- Deux adultes et un enfant                |");
                        System.out.println("|2- Deux adultes et deux enfants             |");
                        System.out.println("|3- Deux adultes et trois enfants            |");
                        System.out.println("|4- Deux adultes et plus de trois enfants    |");
                        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||");
                        System.out.print("Entrez le type:");
                        categ=3;
                        Scanner famille=new Scanner(System.in);
                        type_famille=famille.nextInt();
                        do {
                            switch (type_famille)
                            {
                                case 1: System.out.println("Vous êtes une famille de 2 adultes et 1 enfants");
                                    rabais_applicable=rabais_famille1;
                                    break;
                                case 2: System.out.println("Vous êtes une famille de 2 adultes et 2 enfants");
                                    rabais_applicable=rabais_famille2;
                                    break;
                                case 3: System.out.println("Vous êtes une famille de 2 adultes et 3 enfants");
                                    rabais_applicable=rabais_famille3;
                                    break;
                                case 4: System.out.println("Combien d'enfants avez vous?");
                                    Scanner enfant=new Scanner(System.in);
                                    nbr_enfant=enfant.nextInt();
                                    while (nbr_enfant < 3)
                                    {
                                        System.out.println("Vous devez donner un nombre d'enfant plus grand que 3.");
                                        System.out.print("Rentrez le nombre d'enfants ");
                                        nbr_enfant=enfant.nextInt();
                                    } 
                                    System.out.println("Vous êtes une famille de 2 adultes et " +nbr_enfant +" enfants");
                                    reste_enfant=nbr_enfant-3;
                                    nbr_pers=nbr_enfant+2;
                                    rabais_applicable=((((5 * (prix_avion*rabais_famille3))+((reste_enfant*(prix_avion*rabais_famille4)))) / ((nbr_pers*prix_avion))));
                                    break;
                                default :  System.out.println("Vous avez entrée un choix non valide.");
                                    System.out.println("Veuillez resaisir votre type de famille.");
                                    System.out.print("Entrez le type: ");
                                    type_famille=famille.nextInt();
                                    break;
                            }
                        } while (type_famille == 0 || type_famille > 4);
                        break;
                    default: System.out.println("Vous avez entrée un choix non valide.");
                        System.out.println("Veuillez resaisir votre type de catégorie.");
                        System.out.print("Entrez le type: ");
                        categ=categorie.nextInt();
                        break;
                }
            } while (categ == 0 || categ > 3);
        //------------------------------------------------------------------------//
        System.out.println("-------------------------------");
        //------------------------------------------------------------------------// 

        //Menu 4 (choix des chambres)
            if (categ == 2)
            {
                System.out.println("||||||||||||||||||||||||||||||||||||||||||||||");
                System.out.println("|Quel type de chambres voulez-vous?          |");
                System.out.println("|1- Chambre régulière                        |");
                System.out.println("|2- Chambre double                           |");
                System.out.println("|3- Chambre familiale                        |");
                System.out.println("|4- Une/Des suite(s)                         |");
                System.out.println("||||||||||||||||||||||||||||||||||||||||||||||");
                System.out.print("Entrez le type de chambre: ");
                Scanner famille=new Scanner(System.in);
                Scanner chambre=new Scanner(System.in);
                type_chambre=famille.nextInt();
                do {
                    switch (type_chambre)
                    {
                        case 1: System.out.println("Entrez le nombre de chambres souhaité: ");
                            nbr_chambre=chambre.nextInt();
                            prix_chambre=prix_chambre_reg;
                            break;
                        case 2: System.out.println("Entrez le nombre de chambres souhaité: ");
                            nbr_chambre=chambre.nextInt();
                            prix_chambre=prix_chambre_dou;
                            break;
                        case 3: System.out.println("Entrez le nombre de chambres souhaité: ");
                            nbr_chambre=chambre.nextInt();
                            prix_chambre=prix_chambre_fam;
                            break;
                        case 4: System.out.println("Entrez le nombre de suite souhaité: ");
                            nbr_chambre=chambre.nextInt();
                            prix_chambre=prix_chambre_sui;
                            break;
                        default: System.out.println("Vous avez entrez un nombre de chambre invalide");
                            System.out.println("Entrez le nombre de chambres souhaité: ");
                            nbr_chambre=chambre.nextInt();
                            break;
                    }
                } while (type_chambre==0 || type_chambre >4);
                System.out.println("Vous avez choisis "+nbr_chambre +" chambre(s) de type " +type_chambre);
            }
            else if (categ == 3)
            {
                System.out.println("||||||||||||||||||||||||||||||||||||||||||||||");
                System.out.println("|Quel type de chambres voulez-vous?          |");
                System.out.println("|1- Chambre régulière                        |");
                System.out.println("|2- Chambre double                           |");
                System.out.println("|3- Chambre familiale                        |");
                System.out.println("|4- Une/Des suite(s)                         |");
                System.out.println("||||||||||||||||||||||||||||||||||||||||||||||");
                System.out.print("Entrez le type de chambre: ");
                Scanner famille=new Scanner(System.in);
                Scanner chambre=new Scanner(System.in);
                type_chambre=famille.nextInt();
                switch (type_chambre)
                {
                    case 1: System.out.println("Entrez le nombre de chambres souhaité: ");
                        nbr_chambre=chambre.nextInt();
                        prix_chambre=prix_chambre_reg;
                        break;
                    case 2: System.out.println("Entrez le nombre de chambres souhaité: ");
                        nbr_chambre=chambre.nextInt();
                        prix_chambre=prix_chambre_dou;
                        break;
                    case 3: System.out.println("Entrez le nombre de chambres souhaité: ");
                        nbr_chambre=chambre.nextInt();
                        prix_chambre=prix_chambre_fam;
                        break;
                    case 4: System.out.println("Entrez le nombre de suite souhaité: ");
                        nbr_chambre=chambre.nextInt();
                        prix_chambre=prix_chambre_sui;
                        break;
                }
                System.out.println("Vous avez choisis "+nbr_chambre +" chambre(s) de type " +type_chambre);
            }
            else if(categ == 1){
                System.out.println("||||||||||||||||||||||||||||||||||||||||||||||");
                System.out.println("|1- Chambre régulière                        |");
                System.out.println("|2- Chambre double                           |");
                System.out.println("|3- Chambre familiale                        |");
                System.out.println("|4- Une/Des suite(s)                         |");
                System.out.println("||||||||||||||||||||||||||||||||||||||||||||||");
                prix_chambre=prix_chambre_reg;
                nbr_chambre=1;
                type_chambre=1;
                System.out.println("Vous avez automatiquement obtenu " +nbr_chambre +" chambre de type " +type_chambre +", car vous êtes seul(e).");
            }
        //------------------------------------------------------------------------//
        System.out.println("-------------------------------");
        //------------------------------------------------------------------------//

        //Menu 5 (choix de location du transport)

            System.out.println("||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("|Quel type de location de voiture?           |");
            System.out.println("|1- Voiture familiale                        |");
            System.out.println("|2- Voiture 4x4                              |");
            System.out.println("|3- Voiture de luxe                          |");
            System.out.println("|4- Un autobus                               |");
            System.out.println("|5- Aucun type de locution                   |");
            System.out.println("||||||||||||||||||||||||||||||||||||||||||||||");
            System.out.print("Entrez le type de location: ");
            Scanner nbr_location=new Scanner(System.in);
            Scanner location=new Scanner(System.in);
            type_location=location.nextInt();
            do{
                switch (type_location)
                {
                    case 1: System.out.println("Combien de jours voulait vous la louer?");
                        nbr_jour_location=nbr_location.nextInt();
                        prix_location=nbr_jour_location*prix_voiture_cit;
                        break;
                    case 2: System.out.println("Combien de jours voulait vous la louer?");
                        nbr_jour_location=nbr_location.nextInt();
                        prix_location=nbr_jour_location*prix_voiture_4x4;
                        break;
                    case 3: System.out.println("Combien de jours voulait vous la louer?");
                        nbr_jour_location=nbr_location.nextInt();
                        prix_location=nbr_jour_location*prix_voiture_luxe;
                        break;
                    case 4: System.out.println("Combien de jours voulait vous le louer?");
                        nbr_jour_location=nbr_location.nextInt();
                        prix_location=nbr_jour_location*prix_bus;
                        break;
                    case 5: prix_location=0;
                        break;
                    default: System.out.println("Vous avez entrée un choix non valide.");
                        System.out.println("Veuillez resaisir votre type de location.");
                        System.out.print("Entrez le type: ");
                        type_location=location.nextInt();       
                }
                System.out.println("Vous ne pouvez louer une moyen de transport plus que le nombre de jour de votre séjour.");
            } while (type_location > 5 || type_location == 0);
            System.out.println("Au final, vous avez choisis le choix "+type_location +" pour "+nbr_jour_location +" jours.");

        //------------------------------------------------------------------------//
        System.out.println("-------------------------------");
        //------------------------------------------------------------------------//

        //Menu 6 (les calculs finals)
        taxe_TPS = 0.05; taxe_TVQ = 0.09975;
        prix_voyage_total = nbr_pers*(prix_avion*rabais_applicable);
        prix_chambre_total = (prix_chambre*nbr_chambre)*nbr_jours;
        prix_avTaxes = prix_voyage_total + prix_location + prix_chambre_total;
        prix_TPS = prix_avTaxes * taxe_TPS;
        prix_TVQ = prix_avTaxes * taxe_TVQ;
        prix_apTaxes= (prix_avTaxes + prix_TPS + prix_TVQ);

        System.out.println("Organisateur: " +nom);
        System.out.println("Le coût des billets pour le voyage est de : " +prix_voyage_total +"$.");
        System.out.println("Le prix de la location est de : " +prix_location +"$.");
        System.out.println("Le prix des chambres est de : " +prix_chambre_total +"$.");
        System.out.println("Le prix total avant les taxes est de : " +prix_avTaxes +"$.");
        System.out.println("TPS : " +prix_TPS +"$.");
        System.out.println("TPS : " +prix_TVQ +"$.");
        System.out.println("Finalement, le prix total avez taxes et de : " +prix_apTaxes +"$.");
        
        //------------------------------------------------------------------------//
        System.out.println("-------------------------------");
        //------------------------------------------------------------------------//
        
        System.out.println("Souhaitez-vous faire une nouvelle reservation?");           //L'option de recommencer ou non.
        System.out.println("Écriver 'r' pour recommencer ou 'q' pour quitter");
        Scanner menu=new Scanner(System.in);
        restart=menu.nextLine();
        s=restart.charAt(0);
        if (s == 'q')
        {
            marcher = false;
        }
    } while (marcher == true);
    }    
} 

