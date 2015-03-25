package fr.ippon.contest.puissance4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Puissance4Impl implements Puissance4 {

	private String joueurCourant;
	/**
	 * Au moment de jouer une pièce ( ou pour chaque pièce au chargement d'une
	 * grille) on met a jour cette map, elle contient un clef hashé en fonction
	 * des coordonnées de la piece courante et une objet de type piece ( voir @see
	 * {@link Piece} )
	 */
	private Map<Integer, Piece> LigneEnCours = new HashMap<Integer, Piece>();

	@Override
	public void nouveauJeu() {
		joueurCourant = (Math.random() % 2 == 1 ? Puissance4.ROUGE
				: Puissance4.JAUNE);
		LigneEnCours = new HashMap<Integer, Piece>();

	}

	@Override
	public void chargerJeu(char[][] grille, char tour) {
		int lignesDansGrille = grille.length;
		int colonnesDansGrille = grille[0].length;

		// init
		nouveauJeu();
		majJoueur(tour);

		if (isNotTailleCorrecte(lignesDansGrille, colonnesDansGrille)) {
			throw new IllegalArgumentException("grille invalide");
		}

		if (isNotJoueurValide(tour)) {
			throw new IllegalArgumentException("Joueur invalide");
		}

		for (int ligne = 0; ligne < grille.length; ligne++) {
			for (int colonne = 0; colonne < grille[ligne].length; colonne++) {

				if (isCaseVide(grille, ligne, colonne))
					continue;
				Piece piece = new Piece(colonne, ligne, grille[ligne][colonne]);
				majLignesPourPieceJouees(piece);
				LigneEnCours.put(piece.hashCode(), piece);
			}
		}

	}

	private boolean isCaseVide(char[][] grille, int ligne, int colonne) {
		return grille[ligne][colonne] == Puissance4.CASE_VIDE;
	}

	private void majJoueur(char tour) {
		joueurCourant = "" + tour;
	}

	private void majLignesPourPieceJouees(Piece piece) {
		List<Integer> casesAdjacentes = piece.getCoordonneesAdjacents();
		casesAdjacentes
				.stream()
				.filter(coordonne -> this.LigneEnCours.containsKey(coordonne)
						&& this.LigneEnCours.get(coordonne).getData()
								.getJoueur() == piece.getData().getJoueur())
				.forEach(caseAdjacente -> {
					maJLignes(piece, caseAdjacente);
				});
	}

	private void maJLignes(Piece piece, Integer caseAdjacente) {
		Piece pieceAdj = this.LigneEnCours.get(caseAdjacente);

		piece.ajouter(pieceAdj.getDirectionAdj(piece), pieceAdj);
		pieceAdj.ajouter(piece.getDirectionAdj(pieceAdj), piece);
	}

	private boolean isNotJoueurValide(char tour) {
		return tour != Puissance4.JAUNE.charAt(0)
				&& tour != Puissance4.ROUGE.charAt(0);
	}

	private boolean isNotTailleCorrecte(int ligneDansGrille,
			int colonneDansGrille) {
		return ligneDansGrille != Puissance4.MAX_LIGNE
				|| colonneDansGrille != Puissance4.MAX_COLONNE
				|| colonneDansGrille == 0 || ligneDansGrille == 0;
	}

	@Override
	public EtatJeu getEtatJeu() {
		int nbLignesRouge = 0;
		int nbLignesJaune = 0;

		for (Iterator it_ligneEnCours = LigneEnCours.entrySet().iterator(); it_ligneEnCours
				.hasNext();) {
			Entry<Integer, Piece> x = (Entry<Integer, Piece>) it_ligneEnCours
					.next();
			List<Integer> longueursLignes = x.getValue().getLongueursLignes();
			for (Integer y : longueursLignes) {
				if (y > 3) {
					if (x.getValue().getData().getJoueur() == Puissance4.ROUGE
							.charAt(0)) {
						nbLignesRouge++;
					} else if (x.getValue().getData().getJoueur() == Puissance4.JAUNE
							.charAt(0)) {
						nbLignesJaune++;
					}
				}
			}

		}
		;
		boolean partieFinie = LigneEnCours.keySet().size() == Puissance4.MAX_COLONNE
				* Puissance4.MAX_LIGNE;

		if (partieFinie && nbLignesJaune == 0 && nbLignesRouge == 0) {
			return EtatJeu.MATCH_NUL;
		}

		if (!partieFinie) {
			if (nbLignesJaune > 0 && nbLignesRouge == 0) {
				return EtatJeu.JAUNE_GAGNE;
			} else if (nbLignesJaune == 0 && nbLignesRouge > 0) {
				return EtatJeu.ROUGE_GAGNE;
			} else {
				return EtatJeu.EN_COURS;
			}
		}
		return null;
	}

	@Override
	public char getTour() {
		return this.joueurCourant.charAt(0);
	}

	@Override
	public char getOccupant(int ligne, int colonne) {
		return this.LigneEnCours.get(Piece.genererIdLigne(ligne, colonne)).getData()
				.getJoueur();
	}

	@Override
	public void jouer(int colonne) {
		int ligne = 5;
		boolean pasDeCaseLibre = true;
		if (colonneInvalide(colonne)) {
			throw new IllegalArgumentException("numéros de colonne invalide "
					+ colonne);
		}

		if (colonnePleine(colonne))
			throw new IllegalStateException("plus de place dans la colonne "
					+ colonne);

		while (pasDeCaseLibre) {

			if (!LigneEnCours.containsKey(Piece.genererIdLigne(colonne, ligne))) {
				Piece piece = new Piece(colonne, ligne, getTour());
				LigneEnCours.put(Piece.genererIdLigne(colonne, ligne), piece);
				majLignesPourPieceJouees(piece);
				pasDeCaseLibre = false;
			} else {
				ligne--;
			}
		}
		if (Puissance4.ROUGE.equals(joueurCourant)) {
			majJoueur(Puissance4.JAUNE.charAt(0));
		} else {
			majJoueur(Puissance4.ROUGE.charAt(0));
		}
	}


	private boolean colonneInvalide(int colonne) {
		return colonne < Puissance4.MIN_COLONNE
				|| colonne >= Puissance4.MAX_COLONNE;
	}

	private boolean colonnePleine(int colonne) {
		return LigneEnCours.containsKey(Piece.genererIdLigne(colonne, 0));
	}

	private boolean isColonneValide(int colonne) {
		return (colonne < Puissance4.MAX_COLONNE && colonne > Puissance4.MIN_COLONNE);
	}

}
