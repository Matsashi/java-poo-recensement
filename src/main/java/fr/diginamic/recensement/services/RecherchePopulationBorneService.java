package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.CodeDptUnknownException;
import fr.diginamic.recensement.exceptions.CodeLetterException;
import fr.diginamic.recensement.exceptions.CodePopMinMaxInvertedException;
import fr.diginamic.recensement.exceptions.CodePopNegativeException;

/**
 * Recherche et affichage de toutes les villes d'un département dont la
 * population est comprise entre une valeur min et une valeur max renseignées
 * par l'utilisateur.
 * 
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationBorneService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) throws CodeLetterException, CodeDptUnknownException,
			CodePopNegativeException, CodePopMinMaxInvertedException {

		System.out.println("Quel est le code du département recherché ? ");
		String choix = scanner.nextLine();
		if ((Character.isAlphabetic(choix.charAt(0)) || Integer.parseInt(choix) < 1 || 95 < Integer.parseInt(choix))
				&& !(choix == "2A" && choix == "2B" && 970 < Integer.parseInt(choix)
						&& Integer.parseInt(choix) < 975)) {
			throw new CodeDptUnknownException("Veuillez renseigner un code département valide");
			this.traiter(rec, scanner);
		} else {
			System.out.println("Choississez une population minimum (en milliers d'habitants): ");
			String saisieMin = scanner.nextLine();
			if (Integer.parseInt(saisieMin)<0){
				throw new CodePopNegativeException("Veuillez renseigner un nombre supérieur à 0");
			}else {
				for (int i = 0; i < saisieMin.length(); i++) {
					if (Character.isLetter(i)) {
						throw new CodeLetterException("Veuillez saisir un nombre et non des lettres");
					}
				}
				System.out.println("Choississez une population maximum (en milliers d'habitants): ");
				String saisieMax = scanner.nextLine();
				if (Integer.parseInt(saisieMax) < 0) {
					throw new CodePopNegativeException("Veuillez renseigner un nombre supérieur à 0");
				}else if(Integer.parseInt(saisieMin)>Integer.parseInt(saisieMax)){
					 throw new CodePopMinMaxInvertedException("Veuillez renseigner un nombre maximum supérieur au nombre minimum");
				}else {
					for (int i = 0; i < saisieMax.length(); i++) {
						if (Character.isLetter(i)) {
							throw new CodeLetterException("Veuillez saisir un nombre et non des lettres");
						}
					}
					int min = Integer.parseInt(saisieMin) * 1000;
					int max = Integer.parseInt(saisieMax) * 1000;

					List<Ville> villes = rec.getVilles();
					for (Ville ville : villes) {
						if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
							if (ville.getPopulation() >= min && ville.getPopulation() <= max) {
								System.out.println(ville);
							}
						}
					}
				}
			}
		}
	}
}
